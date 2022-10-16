package com.devsuperior.movieflix.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private JwtTokenStore jwtTokenStore;
	
	private static final String[] PUBLIC = {"/oauth2/token", "/h2-console/**"};
	
	private static final String[] MEMBER = {"/users/**","/reviews/**"};
	
	private static final String[] VISITOR = {};   	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(jwtTokenStore);
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		// Liberando frames do Console do H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		};
		
		http.authorizeRequests()
		    .antMatchers(PUBLIC).permitAll()
 	    	.antMatchers(MEMBER).hasAnyRole("MEMBER")
 	    	.antMatchers(VISITOR).hasAnyRole("VISITOR")
   	    	.anyRequest().authenticated();
	
	}
}
