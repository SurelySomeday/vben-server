/*
 * Copyright (c) 2020 learner4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.yxlgx.common.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import top.yxlgx.common.security.component.CustomAuthenticationEntryPoint;
import top.yxlgx.common.security.component.CustomBearerTokenResolver;
import top.yxlgx.common.security.component.CustomOpaqueTokenIntrospector;

/**
 * @author lengleng
 * @date 2022-06-02
 */
@RequiredArgsConstructor
public class CustomResourceServerAutoConfiguration {

	/**
	 * 请求令牌的抽取逻辑
	 * @return CustomBearerTokenResolver
	 */
	@Bean
	public CustomBearerTokenResolver customBearerTokenResolver() {
		return new CustomBearerTokenResolver();
	}

	/**
	 * 资源服务器异常处理
	 * @param objectMapper jackson 输出对象
	 * @param securityMessageSource 自定义国际化处理器
	 * @return ResourceAuthExceptionEntryPoint
	 */
	@Bean
	public CustomAuthenticationEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper,
																		  MessageSource securityMessageSource) {
		return new CustomAuthenticationEntryPoint(objectMapper, securityMessageSource);
	}

	/**
	 * 资源服务器toke内省处理器
	 * @param authorizationService token 存储实现
	 * @return OpaqueTokenIntrospector
	 */
	@Bean
	public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService,
														   ApplicationContext applicationContext) {
		return new CustomOpaqueTokenIntrospector(authorizationService,applicationContext);
	}


}
