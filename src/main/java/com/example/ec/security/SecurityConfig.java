package com.example.ec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private ExploreCaliUserDetailsService userDetailsService;
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Entry points
    	return http
    	.csrf(csrf -> csrf.disable())
        //.requestMatchers("/packages/**","/tours/**","/ratings/**","/users/signin").permitAll()
        .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/packages/**","/tours/**","/ratings/**","/users/signin").permitAll()
                .anyRequest().authenticated())
        		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        		.addFilterBefore(new JwtTokenFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
        		.build();
        
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Allow swagger to be accessed without authentication
    	return (web) -> web.ignoring().requestMatchers("/v2/api-docs", "/swagger-resources/**", "/configuration/**", "/webjars/**", "/public");
    }


    @Bean
    public AuthenticationManager authenticationManager	(AuthenticationConfiguration authConfig) throws Exception {
    	return authConfig.getAuthenticationManager();
    }

}