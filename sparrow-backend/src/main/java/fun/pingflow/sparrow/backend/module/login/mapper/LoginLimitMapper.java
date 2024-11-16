package fun.pingflow.sparrow.backend.module.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.pingflow.sparrow.backend.module.login.entity.LoginLimitEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录限制 Mapper 接口
 *
 * @author eang-zh
 * @since 2024-11-15 22:40
 */
@Mapper
public interface LoginLimitMapper extends BaseMapper<LoginLimitEntity> {

}
