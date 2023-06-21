package top.yxlgx.common.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import top.yxlgx.common.security.config.support.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import top.yxlgx.common.security.config.support.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import top.yxlgx.common.security.config.token.CustomeOAuth2TokenCustomizer;
import top.yxlgx.common.security.config.token.OAuth2TokenSettings;
import top.yxlgx.common.security.service.CustomUserDetailsService;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.UUID;

/**
 * OAuth Authorization Server Configuration.
 *
 * @author Steve Riesenberg
 */
@Configuration
@EnableWebSecurity(debug = true)
public class OAuth2AuthorizationServerSecurityConfiguration {

    @Resource
    private OAuth2AuthorizationService authorizationService;

    @Resource
    private OAuth2TokenSettings oAuth2TokenSettings;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Bean
    public AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    @Bean
    public OAuth2TokenGenerator<? extends OAuth2Token> oAuth2TokenGenerator() {
        CustomeOAuth2TokenCustomizer customeOAuth2TokenCustomizer = new CustomeOAuth2TokenCustomizer();
        OAuth2AccessTokenGenerator oAuth2AccessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator oAuth2RefreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        oAuth2AccessTokenGenerator.setAccessTokenCustomizer(customeOAuth2TokenCustomizer);
        DelegatingOAuth2TokenGenerator delegatingOAuth2TokenGenerator = new DelegatingOAuth2TokenGenerator(
                oAuth2AccessTokenGenerator,
                oAuth2RefreshTokenGenerator
        );
        return delegatingOAuth2TokenGenerator;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class);
        http.userDetailsService(userDetailsService)
                .apply(authorizationServerConfigurer
                        .tokenEndpoint(
                                (tokenEndpoint) ->
                                        tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
                        )
                        .tokenGenerator(oAuth2TokenGenerator()).authorizationService(authorizationService)
                );


        SecurityFilterChain securityFilterChain = http.formLogin(Customizer.withDefaults()).build();
        /**
         *
         * Custom configuration for Resource Owner Password grant type. Current implementation has no support for Resource Owner
         * Password grant type
         */
        addCustomOAuth2ResourceOwnerPasswordAuthenticationProvider(http);
        return securityFilterChain;
    }

    @SuppressWarnings("unchecked")
    private void addCustomOAuth2ResourceOwnerPasswordAuthenticationProvider(HttpSecurity http) {

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = http.getSharedObject(OAuth2TokenGenerator.class);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider =
                new OAuth2ResourceOwnerPasswordAuthenticationProvider(authenticationManager, authorizationService, tokenGenerator);

        // This will add new authentication provider in the list of existing authentication providers.
        http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);
    }

    @Bean
    @Order(2)
    public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/oauth2/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        // @formatter:on

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // @formatter:off
        RegisteredClient loginClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("login-client")
                .clientSecret("{noop}openid-connect")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:9000/login/oauth2/code/login-client")
                .redirectUri("http://127.0.0.1:9000/authorized")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("message:read")
                .scope("message:write")
                .build();
        RegisteredClient passwordClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("password-client")
                .clientSecret("{noop}password")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .tokenSettings(oAuth2TokenSettings.getTokenSettings())
                .scope("message:read")
                .scope("message:write")
                .build();
        // @formatter:on

        return new InMemoryRegisteredClientRepository(loginClient, registeredClient, passwordClient);
    }


    @Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder().issuer("http://localhost:9000").build();
    }


}
