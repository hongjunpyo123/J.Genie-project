package com.project.JGenie.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/coverletter/example", "/coverletter/ai", "/coverletter"
                        , "/companies", "/companies/**", "/career/register", "/careers", "/career/**"
                        , "/careers/**", "/valid/**", "/user", "/server/status", "/coverletters/user", "/coverletters/**"
                        , "/", "/login", "/main/**", "/signup", "/user/signup", "/user/logout", "/user/delete-account"
                        , "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/favicon.ico"
                        /*, "/swagger-ui/**", "/v3/api-docs/**"*/)
                        .permitAll()
                        .anyRequest().denyAll());
        return http.build();
    }
}
