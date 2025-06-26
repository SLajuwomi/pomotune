package com.slajuwomi.pomotune.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig - Spring Security Configuration for PomoTune
 * 
 * This configuration disables authentication for development purposes. In
 * production, you'll want to implement proper OAuth or JWT authentication.
 * 
 * Current setup: ALL endpoints are accessible without authentication Future:
 * Will be replaced with OAuth (Google/GitHub) login
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure HTTP Security Settings
     * 
     * For development: Disable CSRF and allow all requests This makes testing with
     * curl/Postman much easier
     * 
     * @param http HttpSecurity configuration object
     * @return SecurityFilterChain configured for development
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection (needed for REST APIs)
                .csrf(csrf -> csrf.disable())

                // Configure authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Allow all requests to any endpoint without authentication
                        .anyRequest().permitAll());

        return http.build();
    }
}