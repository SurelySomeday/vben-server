package top.yxlgx.wink.config.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.yxlgx.wink.config.security.SecurityProperties;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * @author yanxin
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecurityProperties securityProperties;

    public String generateToken(String username){
        Date date = new Date(System.currentTimeMillis()+60*1000*30);
        return Jwts.builder().setSubject(username).setExpiration(date).signWith(getSignInKey()).compact();
    }



    /**
     * 1、解析token字符串中的加密信息【加密算法&加密密钥】, 提取所有声明的方法
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                // 获取alg开头的信息
                .setSigningKey(getSignInKey())
                .build()
                // 解析token字符串
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 2、获取签名密钥的方法
     * @return 基于指定的密钥字节数组创建用于HMAC-SHA算法的新SecretKey实例
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 3、解析token字符串中的权限信息
     * @param token
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 4、从token中解析出username
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 5、判断token是否过期
     * @param
     * @return
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // 从token中获取用户名
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) &&!isTokenExpired(token);
    }

    /**
     * 6、验证token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /**
     * 6.1、从授权信息中获取token过期时间
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
