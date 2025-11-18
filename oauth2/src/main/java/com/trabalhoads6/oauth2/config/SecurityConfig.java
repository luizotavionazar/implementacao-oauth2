package com.trabalhoads6.oauth2.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> {
                csrf.ignoringRequestMatchers("/csrf-token"); // Ignora CSRF para o endpoint que fornece o token CSRF
            })

            // Regras de autorização
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/csrf-token").permitAll() // Rotas públicas
                .anyRequest().authenticated()
            )

            // Login via OAuth2 (Google)
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
            )

            // Logout via POST /logout
            .logout(logout -> logout
                .logoutUrl("/logout")          
                .invalidateHttpSession(true)   // Invalida a sessão
                .clearAuthentication(true)     // Limpa a autenticação
                .deleteCookies("JSESSIONID")   // Apaga o cookie
                .logoutSuccessUrl("/")         // Volta pra home
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
