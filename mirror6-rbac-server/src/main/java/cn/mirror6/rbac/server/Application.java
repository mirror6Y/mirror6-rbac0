package cn.mirror6.rbac.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mirror6
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "cn.mirror6.rbac.server.controller")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
