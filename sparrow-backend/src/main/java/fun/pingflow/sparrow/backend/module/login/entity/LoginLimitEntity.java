package fun.pingflow.sparrow.backend.module.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录限制
 *
 * @author eang-zh
 * @since 2024-11-15 22:40
 */
@Data
@Accessors(chain = true)
@TableName("sparrow_login_limit")
public class LoginLimitEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** 登录手机号 */
    private String phone;

    /** 登录IP */
    private String ip;

    /** 登录次数 */
    private Integer loginCount;

    /** 创建时间（解锁时间 */
    private Date createdAt;

    /** 创建人 */
    private String createBy;

    /** 更新时间 */
    private Date updatedAt;

    /** 更新人 */
    private String updateBy;

    /** 逻辑删除：0-未删除，1-已删除 */
    @TableLogic
    private String deletedFlag;

    /** 扩展字段 */
    private String extend1;

    /** 扩展字段 */
    private String extend2;

    /** 扩展字段 */
    private String extend3;
}
