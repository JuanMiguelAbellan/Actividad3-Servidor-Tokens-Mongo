package com.example.actividad3.usuarios.application;

import com.example.actividad3.usuarios.domain.Usuario;
import com.example.actividad3.usuarios.domain.UsuarioRepository;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class UsuarioUseCases {

    private UsuarioRepository usuarioRepository;

    public UsuarioUseCases(UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }
    public Boolean registro(Usuario usuario){
        String password = Hashing.sha256()
                .hashString(usuario.getPassword(), StandardCharsets.UTF_8)
                .toString();
        Usuario cifrado = new Usuario().setEmail(usuario.getEmail()).setPassword(password);
        return this.usuarioRepository.registro(cifrado);
    }

    public Usuario login(Usuario usuario){
        Usuario usuarioBD = this.usuarioRepository.login(usuario);
        if(usuarioBD == null) return null;
        String password = Hashing.sha256()
                .hashString(usuario.getPassword(), StandardCharsets.UTF_8)
                .toString();
        if(usuarioBD.getPassword().equals(password)){
            return usuario;
        }
        else return null;
    }
}
