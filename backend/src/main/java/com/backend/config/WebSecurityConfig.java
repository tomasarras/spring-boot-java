package com.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.filter.JWTAuthorizationFilter;
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/resources/**","/static/**").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/registrar").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/productos").permitAll()
			.antMatchers("/admin").permitAll()
			.antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll();

		http.csrf().disable()
			.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
			.antMatchers(HttpMethod.POST, "/api/users/login").permitAll()
			.antMatchers(HttpMethod.GET, "/api/users/username/*").permitAll()
			.antMatchers(HttpMethod.GET, "/api/categories", "/api/categories/*", "/api/categories/**").permitAll()
			.antMatchers(
		            "/swagger-resources/**",  
		            "/swagger-ui/*",
		            "/v3/api-docs",
		            "/v3/api-docs/*",
		            "/webjars/**" ,
		             "/swagger.json")
		        .permitAll()
			.anyRequest().authenticated();
		
		
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
    }
	
	
}