package com.trabalhoads6.oauth2.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class RetornoDados extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User usuario = (OAuth2User) authentication.getPrincipal();
        
        String nome  = URLEncoder.encode(usuario.getAttribute("name"), StandardCharsets.UTF_8);
        String email = URLEncoder.encode(usuario.getAttribute("email"), StandardCharsets.UTF_8);
        String foto  = URLEncoder.encode(usuario.getAttribute("picture"), StandardCharsets.UTF_8);
        
        String redirectUrl ="http://localhost:8080/visualizacao.html?nome="+ nome +"&email="+ email +"&foto="+ foto;
        
        getRedirectStrategy().sendRedirect(request, response, redirectUrl); // Redirecionamento
    }
}
