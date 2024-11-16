package fun.pingflow.sparrow.backend.module.login.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.pingflow.sparrow.backend.module.login.entity.LoginLimitEntity;
import fun.pingflow.sparrow.backend.module.login.mapper.LoginLimitMapper;
import fun.pingflow.sparrow.backend.module.login.service.ILoginLimitService;
import fun.pingflow.sparrow.backend.module.user.service.IUserService;
import fun.pingflow.sparrow.common.SparrowConstant;
import fun.pingflow.sparrow.common.SparrowResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 登录限制 服务实现类
 *
 * @author eang-zh
 * @since 2024-11-15 22:40
 */
@Slf4j
@Service
public class LoginLimitServiceImp extends ServiceImpl<LoginLimitMapper, LoginLimitEntity> implements ILoginLimitService {

    private static final int MAX_LOGIN_COUNT = 50;
    private static final int TIME_WINDOW_MINUTES = 30;

    /** IP的可重入锁 */
    private final ConcurrentHashMap<String, Lock> ipReentrantLock = new ConcurrentHashMap<>();
    /** IP登录计数 */
    private final ConcurrentHashMap<String, AtomicInteger> ipLoginCount = new ConcurrentHashMap<>();
    /** IP锁定结果 */
    private final ConcurrentHashMap<String, AtomicBoolean> ipLockedResult = new ConcurrentHashMap<>();

    @Resource
    private LoginLimitMapper loginLimitMapper;
    @Resource
    private IUserService userService;

    @Override
    public SparrowResult<String> login(String ip, String username, String password) {
        try {

            // 根据IP 查询登录记录
            LoginLimitEntity loginLimit = getLastLoginRecord(ip);
            // 没有记录则放行去验证密码
            if (Objects.isNull(loginLimit)) {
                boolean verifyPassword = userService.verifyPassword(username, password);
                // 密码验证通过则不记录，否则记录一条失败记录
                if (verifyPassword) {
                    return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.SUCCESS);
                }
                return saveLoginRecordWithDoubleCheck(ip);
            }

            // 如果缓存过期了，初始化缓存
            ipLoginCount.putIfAbsent(ip, new AtomicInteger(loginLimit.getLoginCount()));
            ipLockedResult.putIfAbsent(ip, new AtomicBoolean(false));

            // 创建(解锁)时间不为空，且 创建(解锁)时间>当前时间，就是还在冻结中，禁止调用数据库验证密码
            Date createdAt = loginLimit.getCreatedAt();
            if (createdAt != null && createdAt.after(DateUtil.date())) {
                return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.AUTH_FREEZE);
            }

            // 验证密码是否匹配
            boolean verifyPassword = userService.verifyPassword(username, password);
            if (verifyPassword) {
                return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.SUCCESS);
            }

            return this.handleLoginFailure(ip, loginLimit);

        } catch (Exception e) {
            log.error("登录过程发生异常", e);
            return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.SERVICE_ERROR);
        }
    }

    /**
     * 处理登录失败
     *
     * @param ip         ip
     * @param loginLimit 登录限制
     * @return 结果 <字符串>
     * @author eang-zh at 2024-11-16 17:47
     */
    private SparrowResult<String> handleLoginFailure(String ip, LoginLimitEntity loginLimit) {
        // 判断时差是否在30分钟外
        long between = DateUtil.between(loginLimit.getCreatedAt(), DateUtil.date(), DateUnit.MINUTE);
        if (between > TIME_WINDOW_MINUTES) {
            ipLoginCount.put(ip, new AtomicInteger(0));
            ipLockedResult.get(loginLimit.getIp()).set(false);
            updateLoginRecord(loginLimit, 1, false);
            return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.AUTH_ERROR);
        }

        // 判断登录失败次数，是否在允许范围内，否则冻结
        int integer = ipLoginCount.get(ip).incrementAndGet();
        if (integer <= MAX_LOGIN_COUNT) {
            updateLoginRecord(loginLimit, integer, false);
            return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.AUTH_ERROR);
        }

        // 冻结该 IP
        Lock reentrantLock = ipReentrantLock.computeIfAbsent(loginLimit.getIp(), lock -> new ReentrantLock());
        reentrantLock.lock();
        try {
            // 从缓存获取IP的锁定状态，如果已经锁了，就不执行锁定的SQL了
            if (!ipLockedResult.get(loginLimit.getIp()).get()) {
                updateLoginRecord(loginLimit, integer, true);
            }
            return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.AUTH_FREEZE);
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 更新登录记录
     *
     * @param loginLimit      登录限制
     * @param count           计数
     * @param isFreezeAccount 是否冻结账户，true-冻结
     * @author eang-zh at 2024-11-16 17:12
     */
    private void updateLoginRecord(LoginLimitEntity loginLimit, int count, boolean isFreezeAccount) {
        LambdaUpdateWrapper<LoginLimitEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(!isFreezeAccount, LoginLimitEntity::getLoginCount, count)
                .set(isFreezeAccount, LoginLimitEntity::getCreatedAt, DateUtil.offsetHour(DateUtil.date(), 3))
                .eq(LoginLimitEntity::getId, loginLimit.getId());

        loginLimitMapper.update(updateWrapper);
        if (isFreezeAccount) {
            ipLockedResult.get(loginLimit.getIp()).set(true);
        }
    }

    /**
     * 使用双重检查保存登录记录
     *
     * @param ip ip
     * @return 结果 <字符串>
     * @author eang-zh at 2024-11-16 17:08
     */
    private SparrowResult<String> saveLoginRecordWithDoubleCheck(String ip) {
        Lock reentrantLock = ipReentrantLock.computeIfAbsent(ip, lock -> new ReentrantLock());
        reentrantLock.lock();
        try {
            if (Objects.isNull(this.getLastLoginRecord(ip))) {
                insertNewLoginRecord(ip);
            }
        } finally {
            reentrantLock.unlock();
        }
        return SparrowResult.<String>builder().build().buildByResponse(SparrowConstant.Response.AUTH_ERROR);
    }

    /**
     * 插入新登录记录
     *
     * @param ip ip
     * @author eang-zh at 2024-11-16 17:05
     */
    private void insertNewLoginRecord(String ip) {
        // 初始化此IP的缓存
        ipLoginCount.put(ip, new AtomicInteger(0));
        ipLockedResult.put(ip, new AtomicBoolean(false));

        LoginLimitEntity insertLogin = new LoginLimitEntity();
        insertLogin.setIp(ip);
        insertLogin.setLoginCount(1);
        insertLogin.setCreatedAt(DateUtil.date());
        loginLimitMapper.insert(insertLogin);
    }

    /**
     * 获取上次登录
     *
     * @param ip ip
     * @return 登录限制
     * @author eang-zh at 2024-11-16 17:05
     */
    private LoginLimitEntity getLastLoginRecord(String ip) {
        LambdaQueryWrapper<LoginLimitEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LoginLimitEntity::getIp, ip);
        queryWrapper.last("LIMIT 1");
        return loginLimitMapper.selectOne(queryWrapper);
    }


}
