package com.org.spring.security;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.org.spring.user.UserDetailsServiceImpl;

import static com.org.spring.security.SecurityConstants.CHANGE_PASSWORD_URL;
import static com.org.spring.security.SecurityConstants.RESET_PASSWORD_URL;
import static com.org.spring.security.SecurityConstants.SAVE_PASSWORD_URL;
import static com.org.spring.security.SecurityConstants.SIGN_IN_FACEBOOK_URL;
import static com.org.spring.security.SecurityConstants.SIGN_UP_CONFIRMATION_URL;
import static com.org.spring.security.SecurityConstants.SIGN_UP_URL;

import org.springframework.context.annotation.Bean;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder; 

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(SIGN_IN_FACEBOOK_URL,  SIGN_UP_URL,SIGN_UP_CONFIRMATION_URL,RESET_PASSWORD_URL,
                		CHANGE_PASSWORD_URL,SAVE_PASSWORD_URL,
                		"/","/bower_components/**","/translations/**","/views/**","/images/**","/images/*/**","/css/**",
                		"/js/**","/css/*/**","/js/*/**","/css/*/*/**","/js/*/*/**"
                		).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
    } 

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
       
    }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
  
  
}