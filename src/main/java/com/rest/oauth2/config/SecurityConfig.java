package com.rest.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	private final CustomAuthenticationProvider authenticationProvider;
	@Autowired
    private CustomAuthenticationProvider authenticationProvider;
	
//	@Bean
//	public PasswordEncoder noOpPasswordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.authenticationProvider(authenticationProvider);
//    	auth.inMemoryAuthentication()
//    		.withUser("user")
//    		.password("pass")
//    		.roles("USER");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/oauth/**", "/oauth/token", "/oauth2/**", "/h2-console/*").permitAll()
                .and()
                .formLogin().and()
                .httpBasic();
    }
}