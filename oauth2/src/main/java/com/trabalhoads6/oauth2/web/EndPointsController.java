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
public class EndPointsController {

    private final OAuth2AuthorizedClientService servicoAutorizacao;

    public EndPointsController(OAuth2AuthorizedClientService servicoAutorizacao) {
        this.servicoAutorizacao = servicoAutorizacao;
    }

    @GetMapping("/login") // Endpoint para facilidar o redirecionamento para o login do Google
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/usuario") // Endpoint para obter JSON das informações do usuário logado
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User dados, OAuth2AuthenticationToken tokenAcesso) {
        Map<String, Object> resposta = new HashMap<>();

        resposta.put("nome", dados.getAttribute("name"));
        resposta.put("email", dados.getAttribute("email"));
        resposta.put("foto", dados.getAttribute("picture"));

        OAuth2AuthorizedClient cliente = servicoAutorizacao.loadAuthorizedClient( // Captura o token recebido do Google
                tokenAcesso.getAuthorizedClientRegistrationId(),
                tokenAcesso.getName()
        );

        if (cliente != null && cliente.getAccessToken() != null) {
            resposta.put("token_acesso", cliente.getAccessToken().getTokenValue());
            resposta.put("expiracao_token", cliente.getAccessToken().getExpiresAt());
        }

        return resposta;
    }
}
