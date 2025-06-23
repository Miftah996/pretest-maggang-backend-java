package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Nonaktifkan CSRF (aman untuk pengujian lokal)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 🔓 Semua endpoint bebas diakses
            )
            .formLogin(form -> form.disable()) // ❌ Nonaktifkan form login
            .logout(logout -> logout.disable()); // ❌ Nonaktifkan logout

        return http.build();
    }
}
