package fun.pingflow.sparrow.backend.module.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.pingflow.sparrow.backend.module.login.entity.LoginLimitEntity;
import fun.pingflow.sparrow.common.SparrowResult;

/**
 * 登录限制 服务类
 *
 * @author eang-zh
 * @since 2024-11-15 22:40
 */
public interface ILoginLimitService extends IService<LoginLimitEntity> {

    SparrowResult<String> login(String ip, String username, String password);

}
