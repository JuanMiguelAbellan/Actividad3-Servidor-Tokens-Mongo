package com.example.actividad3;

import com.example.actividad3.tareas.application.TareaUseCases;
import com.example.actividad3.tareas.domain.Tarea;
import com.example.actividad3.tareas.infrastructure.TareaRepositoryMongo;
import com.example.actividad3.usuarios.domain.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TareaUsesCasesTest {

    public static TareaUseCases tareaUseCases;

    public TareaUsesCasesTest(){
        tareaUseCases = new TareaUseCases(new TareaRepositoryMongo());
    }

    @Test
    void saveAndList(){
        List<String> usuariosAsignados = new ArrayList<>();
        usuariosAsignados.add("pepito2.pepe@gmail.com");
        Tarea tarea = new Tarea()
                .setPropietario("pepito.pepe@gmail.com")
                .setTexto("Test")
                .setEstado("Pendiente")
                .setFechaCreacion(new Date().toString())
                .setPrioridad("Alta")
                .setUsuariosAsignados(usuariosAsignados);
    }

    @AfterEach
    void reset(){
        tareaUseCases.reset();
    }
}
