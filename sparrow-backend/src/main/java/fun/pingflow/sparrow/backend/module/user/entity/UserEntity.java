package fun.pingflow.sparrow.backend.module.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户
 *
 * @author eang-zh
 * @since 2024-11-15 22:41
 */
@Data
@Accessors(chain = true)
@TableName("sparrow_user")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** 用户名，用于登录 */
    private String username;

    /** 用户密码的哈希值 */
    private String passwordHash;

    /** 用户昵称 */
    private String nickname;

    /** 用户邮箱 */
    private String email;

    /** 用户锁定状态：0-禁用，1-激活 */
    private String lockStatus;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 创建人 */
    private String createBy;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 更新人 */
    private String updateBy;

    /** 逻辑删除：0-未删除，1-已删除 */
    @TableLogic
    private String deletedFlag;

    /** 乐观锁版本号，默认0 */
    private Byte version;

    /** 扩展字段 */
    private String extend1;

    /** 扩展字段 */
    private String extend2;

    /** 扩展字段 */
    private String extend3;
}
