package com.example.actividad3.usuarios.infrastructure.rest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

public class UsuarioRestController {

    @GetMapping
    public String verToken(Authentication auth) {
        return "Email del token: " + auth.getName();
    }
}
