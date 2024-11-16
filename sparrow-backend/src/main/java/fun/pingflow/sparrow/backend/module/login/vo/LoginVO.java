package fun.pingflow.sparrow.backend.module.login.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 登录对象
 *
 * @author eang-zh at 2024-11-15 23:55
 * @version 1.0.0
 */
@Data
public class LoginVO {

    @NotNull
    @Size(min = 2, max = 10)
    private String username;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;

}
