package fun.pingflow.sparrow.backend.module.user.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册VO
 *
 * @author eang-zh at 2024-11-16 00:19
 * @version 1.0.0
 */
@Data
public class RegisterVO {

    /** 用户名 */
    @NotNull
    @Size(min = 2, max = 10)
    private String username;

    /** 密码哈希 */
    @NotNull
    @Size(min = 8, max = 16)
    private String passwordHash;

    /** 电子邮件 */
    private String email;

}
