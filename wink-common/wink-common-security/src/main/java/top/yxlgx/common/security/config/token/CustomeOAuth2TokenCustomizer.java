package top.yxlgx.common.security.config.token;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * token 输出增强
 *
 * @author lengleng
 * @date 2022/6/3
 */
public class CustomeOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

	/**
	 * Customize the OAuth 2.0 Token attributes.
	 * @param context the context containing the OAuth 2.0 Token attributes
	 */
	@Override
	public void customize(OAuth2TokenClaimsContext context) {
		OAuth2TokenClaimsSet.Builder claims = context.getClaims();
		String clientId = context.getAuthorizationGrant().getName();
		claims.claim(OAuth2ParameterNames.CLIENT_ID, clientId);
		// 客户端模式不返回具体用户信息
		if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(context.getAuthorizationGrantType().getValue())) {
			return;
		}

		Object principal = context.getPrincipal().getPrincipal();
		claims.claim("detail", principal);
		claims.issuer("aa");
	}

}
