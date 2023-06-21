package top.yxlgx.common.security.service;

import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.yxlgx.common.security.entity.CustomUser;

/**
 * @author yanxin
 * @description
 */
public interface CustomUserDetailsService extends UserDetailsService, Ordered {

    default boolean support(String clientId, String grantType) {
        return true;
    }

    @Override
    default int getOrder() {
        return 0;
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    default UserDetails loadUserByUser(UserDetails userDetails) {
        return this.loadUserByUsername(userDetails.getUsername());
    }
}
