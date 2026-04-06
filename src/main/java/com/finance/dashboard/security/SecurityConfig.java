package com.finance.dashboard.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth

            // Auth open
            .requestMatchers("/api/auth/**").permitAll()

            // Viewer access
            .requestMatchers("/api/records/summary").hasAnyRole("VIEWER","ANALYST","ADMIN")

            // Analyst access
            .requestMatchers("/api/records/**").hasAnyRole("ANALYST","ADMIN")

            // Admin access
            .requestMatchers("/api/users/**").hasRole("ADMIN")

            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
