package com.trabalhoads6.oauth2.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public AuthController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    // Só pra saber se a API está no ar
    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "API OAuth2 está rodando");
        resp.put("login_url", "/login");
        resp.put("userinfo_url", "/userinfo");
        return resp;
    }

    // 1 e 2) /login -> redireciona para o provedor OAuth2 (Google)
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        // Redireciona manualmente para o endpoint do Spring Security
        // que inicia o fluxo OAuth2 com o Google
        response.sendRedirect("/oauth2/authorization/google");
    }

    // 4) /userinfo -> devolve dados básicos do usuário autenticado em JSON
    @GetMapping("/userinfo")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User principal,
                                        OAuth2AuthenticationToken authToken) {

        Map<String, Object> resposta = new HashMap<>();

        if (principal == null) {
            resposta.put("authenticated", false);
            resposta.put("message", "Usuário não está autenticado.");
            return resposta;
        }

        resposta.put("authenticated", true);
        resposta.put("name", principal.getAttribute("name"));
        resposta.put("email", principal.getAttribute("email"));
        resposta.put("picture", principal.getAttribute("picture"));

        // 3) Exemplo: pegar access token recebido do Google
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authToken.getAuthorizedClientRegistrationId(),
                authToken.getName()
        );

        if (client != null && client.getAccessToken() != null) {
            resposta.put("access_token", client.getAccessToken().getTokenValue());
            resposta.put("access_token_expires_at", client.getAccessToken().getExpiresAt());
        }

        // Informação se tem refresh token ou não
        if (client != null && client.getRefreshToken() != null) {
            resposta.put("has_refresh_token", true);
        } else {
            resposta.put("has_refresh_token", false);
        }

        return resposta;
    }
}
