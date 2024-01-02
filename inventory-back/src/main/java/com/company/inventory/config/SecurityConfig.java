package com.company.inventory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf .disable())
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/api/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/category/category/**").permitAll()
                        .requestMatchers("/api/producto/productos/**").permitAll()
                        .requestMatchers("/api/producto/buscar-producto/**").permitAll()
                        .requestMatchers("/api/tipo-entidad/entidad/**").permitAll()
                        .requestMatchers("/api/user/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }
}