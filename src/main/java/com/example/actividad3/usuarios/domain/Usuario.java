package com.example.actividad3.usuarios.domain;

public class Usuario {
    private String email;
    private String password;

    public Usuario() {
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    public Usuario setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
}
