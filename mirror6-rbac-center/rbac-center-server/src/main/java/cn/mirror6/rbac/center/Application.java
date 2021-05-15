package cn.mirror6.rbac.center;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mirror6
 * Created on 2021/3/21 16:25
 */
@SpringBootApplication
@MapperScan("cn.mirror6.rbac.center.mapper")
@EnableDubbo(scanBasePackages = "cn.mirror6.rbac.center.impl.api")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
