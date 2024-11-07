package fun.pingflow.sparrow.oss;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 存储服务入口
 *
 * @author eang-zh at 2024-11-06 22:32
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEncryptableProperties
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }

}
