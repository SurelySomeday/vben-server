package top.yxlgx.wink.admin;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import top.yxlgx.wink.common.security.client.annotation.EnableSaTokenClient;
import top.yxlgx.wink.feign.annotation.EnableWinkFeignClients;

/**
 * @author yanxin
 * @description 基础配置，与启动类隔离
 */
@EnableWinkFeignClients
@EnableSaTokenClient
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@Configuration
public class BootstrapConfig {
}
