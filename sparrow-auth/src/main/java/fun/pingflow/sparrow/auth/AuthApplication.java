package fun.pingflow.sparrow.auth;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证服务入口
 *
 * @author eang-zh at 2024-11-06 22:06
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEncryptableProperties
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
