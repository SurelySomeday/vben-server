package top.yxlgx.common.security.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import top.yxlgx.common.security.config.OAuth2AuthorizationServerSecurityConfiguration;

import java.lang.annotation.*;

/**
 * @author yanxin
 * @description
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableMethodSecurity
@Import({ OAuth2AuthorizationServerSecurityConfiguration.class })
public @interface EnableResourceServer {

}
