package top.yxlgx.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author yanxin
 * @description
 */
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> {
                    try {
                        authorizeRequests
                                .requestMatchers("/menu/**")
                                .authenticated()
                                .requestMatchers("/menu/**").hasAuthority("SCOPE_message.read")
                                .and()
                                .oauth2ResourceServer()
                                .jwt();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return http.build();
    }
}
