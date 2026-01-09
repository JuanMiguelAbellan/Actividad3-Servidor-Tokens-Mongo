package com.example.actividad3.usuarios.domain;

public interface UsuarioRepository {
    public Boolean registro(Usuario usuario);
    public Usuario login(Usuario usuario);
}
