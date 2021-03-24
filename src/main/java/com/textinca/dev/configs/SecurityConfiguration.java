package com.textinca.dev.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.textinca.dev.filters.JwtTokenValidatorFilter;



import static com.textinca.dev.configs.SecurityConstants.ALL_END_POINTS;
import static com.textinca.dev.configs.SecurityConstants.URL_TRANSACTIONAL_BACKEND;

import java.util.*;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
		

	@Override
	protected void configure(HttpSecurity http) throws Exception {	

        http
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(new JwtTokenValidatorFilter(),
                UsernamePasswordAuthenticationFilter.class)
 
        //This disables session creation on Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.cors().and().csrf().disable();
	}

	//Cross origins configuration
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping(ALL_END_POINTS).allowedOrigins(URL_TRANSACTIONAL_BACKEND);
			}
		};
	}
	
	

	
}
