package fun.pingflow.sparrow.job;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 调度服务入口
 *
 * @author eang-zh at 2024-11-06 22:30
 * @version 1.0.0
 */
@SpringBootApplication
@EnableEncryptableProperties
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }

}
