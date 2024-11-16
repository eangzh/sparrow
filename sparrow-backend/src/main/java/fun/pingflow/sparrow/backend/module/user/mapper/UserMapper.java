package fun.pingflow.sparrow.backend.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.pingflow.sparrow.backend.module.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 Mapper 接口
 *
 * @author eang-zh
 * @since 2024-11-15 22:41
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
