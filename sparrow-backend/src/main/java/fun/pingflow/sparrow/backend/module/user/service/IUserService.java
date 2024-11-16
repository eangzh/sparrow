package fun.pingflow.sparrow.backend.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.pingflow.sparrow.backend.module.user.entity.UserEntity;

/**
 * 系统用户 服务类
 *
 * @author eang-zh
 * @since 2024-11-15 22:41
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 验证密码
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean true-认证
     * @author eang-zh at 2024-11-15 23:10
     */
    boolean verifyPassword(String username, String password);

}
