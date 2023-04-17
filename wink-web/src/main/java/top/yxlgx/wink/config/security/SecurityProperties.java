package top.yxlgx.wink.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yanxin
 * @Description:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "project.security")
public class SecurityProperties {
    private String secretKey="3F4428472B4B6250655368566D5971337336763979244226452948404D635166";
    private String header="Authorization";
    private String tokenPrefix="Bearer ";
}
