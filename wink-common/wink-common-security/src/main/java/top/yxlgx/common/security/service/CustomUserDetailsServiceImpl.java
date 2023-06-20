package top.yxlgx.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.yxlgx.common.security.entity.CustomUser;

import java.util.List;

/**
 * @author yanxin
 * @Description:
 */
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUser("zhangsan","123456", List.of());
    }
}
