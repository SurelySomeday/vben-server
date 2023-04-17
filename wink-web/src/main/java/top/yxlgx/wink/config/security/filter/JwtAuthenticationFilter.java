package top.yxlgx.wink.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.yxlgx.wink.config.security.SecurityProperties;
import top.yxlgx.wink.config.security.service.JwtService;

import java.io.IOException;

/**
 * @author yanxin
 * @Description:
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * 需要每次收到请求的时候，过滤器都处于活动状态
     * 因此每次用户发送请求时希望过滤器被触发并完成要做的所有工作
     */
    private final JwtService jwtService;
    /**
     * 加载用户特定数据的核心接口。
     * 它作为用户DAO在整个框架中使用，并且是DaoAuthenticationProvider使用的策略
     */
    private final UserDetailsService userDetailsService;
    private final SecurityProperties securityProperties;

    /**
     * 总体流程：
     * 如果我们有我们的用户电子邮箱并且用户未通过身份验证，我们会从数据库中获取用户详细信息（loadUserByUsername --> UserDetails）
     * 然后我们需要做的是检查用户是否有效，如果用户和令牌有效，我们创建一个UsernamePasswordAuthenticationToken对象，传递UserDetails & 凭证 & 权限信息
     * 扩展上面生成的authToken，包含我们请求的详细信息，然后更新安全上下文中的身份验证令牌
     * 最后一步执行过滤器chain，别忘记放行
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header=securityProperties.getHeader();
        String tokenPrefix=securityProperties.getTokenPrefix();
        // 从请求头中获取认证信息
        final String authHeader = request.getHeader(header);
        final String jwt;
        final String username;
        if(authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(tokenPrefix.length());
        // 从token中解析出username
        username = jwtService.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // 根据jwt解析出来的username，获取数据库中的用户信息，封装UserDetails对象
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // TODO 此处token有效性可以从redis｜数据库中获取
            Boolean isTokenValid = true;
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                // TODO 如果令牌有效，封装一个UsernamePasswordAuthenticationToken对象
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        // 用户凭证
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                // 更新安全上下文的持有用户
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        }
    }
}
