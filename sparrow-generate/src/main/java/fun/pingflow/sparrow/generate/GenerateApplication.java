package fun.pingflow.sparrow.generate;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基础代码生成
 *
 * @author eang-zh at 2024-11-06 23:50
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEncryptableProperties
public class GenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenerateApplication.class, args);
    }

}
