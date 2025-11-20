package com.trabalhoads6.oauth2.config;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Autowired
    private OAuth2SuccessHandler successHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable()) 

            // Regras de autorização
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/index.html",
                    "/visualizacao.html",
                    "/style.css",
                    "/script.js",
                    "/favicon.ico",
                    "/assets/**",
                    "/icons/**",
                    "/canvaskit/**",
                    "/login",
                    "/csrf-token"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // Login via OAuth2 (Google)
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .successHandler(successHandler)
            )

            // Logout via POST /logout
            .logout(logout -> logout
                .logoutUrl("/logout")          
                .invalidateHttpSession(true)   // Invalida a sessão
                .clearAuthentication(true)     // Limpa a autenticação
                .deleteCookies("JSESSIONID")   // Apaga o cookie
                .logoutSuccessUrl("/")
                .permitAll()
            )

            // Respostas 401 em JSON para requisições não autenticadas
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("""
                        {"authenticated": false, "message": "Usuário não está autenticado."}
                        """);
                })
            );

        return http.build();
    }
}
