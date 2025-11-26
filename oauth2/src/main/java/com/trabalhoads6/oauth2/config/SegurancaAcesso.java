package com.trabalhoads6.oauth2.config;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SegurancaAcesso {
    @Autowired
    private RetornoDados dadosUsuario;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers( // Onde pode acessar sem fazer login
                    "/", 
                    "/index.html",
                    "/style.css",
                    "/script.js",
                    "/assets/**",
                    "/login"
                ).permitAll()
                .anyRequest().authenticated()
            )

            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .successHandler(dadosUsuario)
            )

            .exceptionHandling(ex -> ex // Quando não autenticado
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("""
                        {"mensagem": "Usuário não está autenticado, realize o login primeiro!"}
                        """);
                })
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)  // Invalida a sessão
                .clearAuthentication(true)    // Limpa a autenticação
                .deleteCookies("JSESSIONID")  // Apaga o cookie
                .logoutSuccessUrl("/")        // Redireciona após logout
                .permitAll()
            );

        return http.build();
    }
}
