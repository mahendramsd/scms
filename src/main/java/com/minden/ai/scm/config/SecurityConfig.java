package com.minden.ai.scm.config;

import com.minden.ai.scm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.Customizer;

/**
 * @author mahendrasridayarathna
 * @created 29/04/2024 - 12:23 pm
 * @project IntelliJ IDEA
 */

@Configuration
public class SecurityConfig  {

    @Autowired
    private UserServiceImpl userDetailsService;

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails user = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username("user")
                .password("user@123")
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);
        return userDetailsManager;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.authorizeRequests().requestMatchers("/swagger-ui/**").permitAll();
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests.requestMatchers(EndpointRequest.to("info", "health")).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}

