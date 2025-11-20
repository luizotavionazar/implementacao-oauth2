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
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        // Extrair dados do Google
        String name  = URLEncoder.encode(user.getAttribute("name"), StandardCharsets.UTF_8);
        String email = URLEncoder.encode(user.getAttribute("email"), StandardCharsets.UTF_8);
        String foto  = URLEncoder.encode(user.getAttribute("picture"), StandardCharsets.UTF_8);

        // URL do Flutter Web (porta definida por você)
        String redirectUrl =
                "http://localhost:8080/visualizacao.html"
                + "?nome=" + name
                + "&email=" + email
                + "&foto=" + foto;

        // Redirecionar para o Flutter Web
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
