package top.yxlgx.common.security.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import top.yxlgx.common.security.config.CustomResourceServerAutoConfiguration;
import top.yxlgx.common.security.config.CustomResourceServerConfiguration;

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
@Import({ CustomResourceServerAutoConfiguration.class, CustomResourceServerConfiguration.class })
public @interface EnableLearnerResourceServer {

}
