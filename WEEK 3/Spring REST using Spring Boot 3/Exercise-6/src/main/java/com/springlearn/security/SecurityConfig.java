package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration marks this as a Spring configuration class.
// @EnableWebSecurity activates Spring Security's web support.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    // SecurityFilterChain defines the security rules for HTTP requests.
    // Every HTTP request passes through this filter chain before reaching a controller.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        LOGGER.debug("Configuring Security Filter Chain");

        http
            // Disable CSRF protection - not needed for stateless REST APIs
            // CSRF is only relevant for browser-based form submissions with session cookies
            .csrf(csrf -> csrf.disable())

            // Define which URLs need authentication and which are open
            .authorizeHttpRequests(auth -> auth
                // /authenticate is the endpoint that generates the JWT.
                // It must be open (permitAll) so unauthenticated clients can call it.
                // The controller itself reads credentials from the Authorization header manually.
                .requestMatchers("/authenticate").permitAll()
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )

            // Enable HTTP Basic Authentication.
            // This means Spring will accept "Authorization: Basic <base64(user:pwd)>"
            // headers on requests. The curl -u option sends exactly this format.
            .httpBasic(basic -> {});

        return http.build();
    }
}
