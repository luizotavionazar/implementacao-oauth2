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

    // HOME
    /*@GetMapping("/api")
    public Map<String, Object> root() {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("message", "Aplicação OAuth2 em execução...");
        resposta.put("rota_login", "Utilize '/login' para logar com o Google");
        resposta.put("rota_informacoes", "Utilize '/userinfo' para visualizar as informações do usuário");
        return resposta;
    }*/

    // Redireciona para o provedor OAuth2 do Google para realizar o login
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    // Devolve em um JSON, as informações básicas do usuário autenticado
    @GetMapping("/userinfo")
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User dados, OAuth2AuthenticationToken tokenAcesso) {
        Map<String, Object> resposta = new HashMap<>();

        resposta.put("authenticated", true);
        resposta.put("name", dados.getAttribute("name"));
        resposta.put("email", dados.getAttribute("email"));
        resposta.put("picture", dados.getAttribute("picture"));

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient( // Captura o token recebido do Google
                tokenAcesso.getAuthorizedClientRegistrationId(),
                tokenAcesso.getName()
        );

        if (client != null && client.getAccessToken() != null) {
            resposta.put("token_acesso", client.getAccessToken().getTokenValue());
            resposta.put("token_expiracao", client.getAccessToken().getExpiresAt());
        }

        return resposta;
    }
}
