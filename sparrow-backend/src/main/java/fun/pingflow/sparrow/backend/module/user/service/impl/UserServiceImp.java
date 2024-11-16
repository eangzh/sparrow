package fun.pingflow.sparrow.backend.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.pingflow.sparrow.backend.module.user.entity.UserEntity;
import fun.pingflow.sparrow.backend.module.user.mapper.UserMapper;
import fun.pingflow.sparrow.backend.module.user.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 系统用户 服务实现类
 *
 * @author eang-zh
 * @since 2024-11-15 22:41
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 验证密码
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean true-认证
     * @author eang-zh at 2024-11-15 23:10
     */
    @Override
    public boolean verifyPassword(String username, String password) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername, username);
        queryWrapper.last("LIMIT 1");
        UserEntity userEntity = userMapper.selectOne(queryWrapper);

        return Objects.equals(password, userEntity.getPasswordHash());
    }
}
