package com.example.cafe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

//    private final CustomUserDetailsService userDetailsService;
//
//    public SecurityConfig(CustomUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true); // Обязательно для работы с credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .cors().and()
//                .authorizeRequests()
//                .requestMatchers("/api/users/**").hasRole("ADMIN")
//                .requestMatchers("/api/**", "/sign-in").permitAll()
//                .requestMatchers("/api/menu-items/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/sign-in") // Обрабатываем POST запрос на этом URL
//                .successHandler((request, response, authentication) -> {
//                    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//                    response.setStatus(200);
//                    response.getWriter().write(String.format(
//                            "{\"id\": \"%s\", \"username\": \"%s\", \"password\": \"%s\", \"authorities\": \"%s\"}",
//                            userDetails.getUser().getId(),
//                            userDetails.getUsername(),
//                            userDetails.getPassword(),
//                            userDetails.getAuthorities()
//                    ));
//                })
//
//
//                .failureHandler((request, response, exception) -> {
//                    response.setStatus(401); // Неудачный логин возвращает 401 Unauthorized
//                    response.getWriter().write("{\"error\":\"Invalid credentials\"}");
//                })
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    response.setStatus(200); // Успешный логаут возвращает 200 OK
//                    response.getWriter().write("{\"message\":\"Logout successful\"}");
//                });
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
