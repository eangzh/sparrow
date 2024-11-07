package fun.pingflow.sparrow.backend;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台服务入口
 *
 * @author eang-zh at 2024-11-06 22:23
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEncryptableProperties
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
