package top.yxlgx.common.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.yxlgx.common.security.entity.CustomUser;

import java.util.List;

/**
 * @author yanxin
 * @Description:
 */
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{
    PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUser("zhangsan",delegatingPasswordEncoder.encode("123456"), List.of());
    }

}
