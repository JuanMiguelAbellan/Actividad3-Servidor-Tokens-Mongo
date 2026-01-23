package com.example.actividad3;

import com.example.actividad3.usuarios.application.UsuarioUseCases;
import com.example.actividad3.usuarios.domain.Usuario;
import com.example.actividad3.usuarios.infrastructure.db.UsuarioRepositoryMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioUsesCasesTest {

    private static UsuarioUseCases usuarioUseCases;

    public UsuarioUsesCasesTest(){
        usuarioUseCases= new UsuarioUseCases(new UsuarioRepositoryMongo());
    }

    @Test
    void saveUser(){
        Usuario usuario = new Usuario().setEmail("usuarioTest@gmail.com").setPassword("123456");
        usuarioUseCases.registro(usuario);
        assertEquals(usuario, usuarioUseCases.login(usuario));
    }

    @AfterEach
    public void reset(){
        usuarioUseCases.reset();
    }

    @AfterAll
    public static void addUsuarios(){
        Usuario usuario = new Usuario().setEmail("pepito.pepe@gmail.com").setPassword("123456");
        usuarioUseCases.registro(usuario);
        Usuario usuario2 = new Usuario().setEmail("pepito2.pepe@gmail.com").setPassword("123456");
        usuarioUseCases.registro(usuario);
        Usuario usuario3 = new Usuario().setEmail("pepito3.pepe@gmail.com").setPassword("123456");
        usuarioUseCases.registro(usuario);
    }
}
