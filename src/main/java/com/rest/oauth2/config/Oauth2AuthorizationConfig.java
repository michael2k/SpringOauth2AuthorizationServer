package com.rest.oauth2.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.rest.oauth2.service.security.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

/**
 * id/password 기반 Oauth2 인증을 담당하는 서버
 * 다음 endpont가 자동 생성 된다.
 * /oauth/authorize
 * /oauth/token
 */
@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
	
	private final DataSource dataSource;
	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailService userDetailsService;
	
	@Value("${security.oauth2.jwt.signkey}")
	private String signKey;
	
	/*
	 * In Memory
	 */
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()
//			.withClient("testClientId")
//			.secret("testSecret")
//			.redirectUris("http://localhost:8081/oauth2/callback")
//			.authorizedGrantTypes("authorization_code")
//			.scopes("read", "write")
//			.accessTokenValiditySeconds(30000);
//	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	
	/**
	 * 토큰 정보를 DB를 통해 관리한다.
	 * @return
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(new JdbcTokenStore(dataSource)).userDetailsService(userDetailsService);
	}
	
	/**
	 * 토큰 발급 방식을 JWT 토큰 방식으로 변경한다. 이렇게 하면 토큰 저정하는 DB Table은 필요가 없다.
	 * @return
	 */
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		super.configure(endpoints);
//		endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailsService);
//	}
	
	/**
	 * jwt converter를 등록.
	 * 
	 * @return
	 */
//	@Bean
//	public JwtAccessTokenConverter jwtAccessTokenConverter() {
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		converter.setSigningKey(signKey);
//		return converter;
//	}
	
}
