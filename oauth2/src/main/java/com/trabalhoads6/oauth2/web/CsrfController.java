package com.trabalhoads6.oauth2.web;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

    @GetMapping("/csrf-token")
    public CsrfToken csrf(CsrfToken token) {
        // O Spring injeta o CsrfToken da sessão atual aqui.
        // Devolver isso em JSON permite que um client (Postman, Flutter, etc)
        // recupere o token e use em requisições POST.
        return token;
    }
}
