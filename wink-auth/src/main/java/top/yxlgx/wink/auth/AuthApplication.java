package top.yxlgx.wink.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import top.yxlgx.wink.feign.annotation.EnableWinkFeignClients;

@EnableWinkFeignClients
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("\n------ Sa-Token-SSO 认证中心启动成功");
    }

}
