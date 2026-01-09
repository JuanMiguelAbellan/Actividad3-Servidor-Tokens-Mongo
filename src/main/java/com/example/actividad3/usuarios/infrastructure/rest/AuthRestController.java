package com.example.actividad3.usuarios.infrastructure.rest;

import com.example.actividad3.context.security.JwtService;
import com.example.actividad3.usuarios.application.UsuarioUseCases;
import com.example.actividad3.usuarios.domain.Usuario;
import com.example.actividad3.usuarios.infrastructure.db.UsuarioRepositoryMongo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController {

    private final JwtService jwtService;
    private UsuarioUseCases usuarioUseCases;

    public AuthRestController(JwtService jwtService) {
        this.jwtService = jwtService;
        this.usuarioUseCases = new UsuarioUseCases(new UsuarioRepositoryMongo());
    }

    @PostMapping("/api/usuarios/registro")
    public String registro(@RequestBody Usuario usuario){
        Boolean correcto = this.usuarioUseCases.registro(usuario);
        if(correcto){
            return "Registrado correctamente";
        }
        else return "No se pudo realizar el registro";
    }

    @PostMapping("/api/usuarios/login")
    public String login(@RequestBody Usuario usuario){
        Usuario usuarioLogin = this.usuarioUseCases.login(usuario);
        if(usuarioLogin != null){
            return  this.jwtService.generateToken(usuarioLogin.getEmail());
        }else{
            return "Usuario y/o contraseña incorrectos";
        }
    }
}
