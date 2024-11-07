package fun.pingflow.sparrow.gateway;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关服务入口
 *
 * @author eang-zh at 2024-11-06 22:28
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEncryptableProperties
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
