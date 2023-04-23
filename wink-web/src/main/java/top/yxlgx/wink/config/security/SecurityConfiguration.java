package top.yxlgx.wink.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import top.yxlgx.wink.config.security.filter.JwtAuthenticationFilter;
import top.yxlgx.wink.config.security.handler.JwtAccessDeniedHandler;
import top.yxlgx.wink.config.security.handler.JwtLogoutHandler;
import top.yxlgx.wink.config.security.handler.JwtAuthenticationEntryPoint;
import top.yxlgx.wink.entity.User;
import top.yxlgx.wink.repository.UserRepository;

import java.util.Optional;

/**
 * @author yanxin
 * @Description:
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final ApplicationContext applicationContext;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = applicationContext.getBean(JwtAuthenticationFilter.class);
        AuthenticationProvider authenticationProvider = applicationContext.getBean(AuthenticationProvider.class);
        LogoutHandler logoutHandler = applicationContext.getBean(JwtLogoutHandler.class);
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = applicationContext.getBean(JwtAuthenticationEntryPoint.class);
        JwtAccessDeniedHandler jwtAccessDeniedHandler = applicationContext.getBean(JwtAccessDeniedHandler.class);
        http
                //禁用csrf(防止跨站请求伪造攻击)
                .csrf()
                .disable()
                // 设置白名单
                .authorizeHttpRequests()
                .requestMatchers("/login")
                .permitAll()
                // 对于其他任何请求，都保护起来
                .anyRequest()
                .authenticated()
                .and()
                // 禁用缓存
                .sessionManagement()
                // 使用无状态session，即不使用session缓存数据
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 添加身份验证
                .and()
                .authenticationProvider(authenticationProvider)
                // 添加JWT过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                // 登出操作
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserRepository userRepository = applicationContext.getBean(UserRepository.class);
                Optional<User> userOptional = userRepository.findByUsername(username);
                if (userOptional.isPresent()) {
                    return userOptional.get();

                } else {
                    throw new UsernameNotFoundException("User not found");
                }
            }
        };
    }

    /**
     * @return 身份校验机制、身份验证提供程序
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 创建一个用户认证提供者
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 设置用户相信信息，可以从数据库中读取、或者缓存、或者配置文件
        authProvider.setUserDetailsService(userDetailsService());
        // 设置加密机制，若想要尝试对用户进行身份验证，我们需要知道使用的是什么编码
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
