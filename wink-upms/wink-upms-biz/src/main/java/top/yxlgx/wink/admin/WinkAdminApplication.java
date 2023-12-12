package top.yxlgx.wink.admin;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import top.yxlgx.wink.common.security.client.annotation.EnableSaTokenClient;
import top.yxlgx.wink.feign.annotation.EnableWinkFeignClients;

@EnableWinkFeignClients
@EnableSaTokenClient
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
public class WinkAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinkAdminApplication.class, args);
    }

}
