package com.trabalhoads6.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Regras de autorização
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/css/**").permitAll() // Publico
                .anyRequest().authenticated() // Necessario autenticar
            )

            // Login via OAuth2 (Google) – integrado ao Spring Security
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
            )

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)   // Invalida a sessão
                .clearAuthentication(true)     // Limpa a autenticação
                .deleteCookies("JSESSIONID")   // Apaga os cookies da sessão
            );

        return http.build();
    }
}
