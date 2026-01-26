package com.example.actividad3;

import com.example.actividad3.tareas.application.TareaUseCases;
import com.example.actividad3.tareas.domain.Tarea;
import com.example.actividad3.tareas.infrastructure.TareaRepositoryMongo;
import com.example.actividad3.usuarios.domain.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

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
        tareaUseCases.crearTarea(tarea);
        List<Tarea> list = tareaUseCases.getTareas("pepito.pepe@gmail.com");
        assertEquals(1, list.size());
    }

    @Test
    void asignar(){
        Usuario usuarioAsignado = new Usuario().setEmail("pepito2@gmail.com");
        Tarea tarea = new Tarea()
                .setId("1")
                .setPropietario("pepito.pepe@gmail.com")
                .setTexto("Test")
                .setEstado("Pendiente")
                .setFechaCreacion(new Date().toString())
                .setPrioridad("Alta");
        tareaUseCases.crearTarea(tarea);
        tareaUseCases.asignar(new Usuario().setEmail("pepito.pepe@gmail.com"), usuarioAsignado, "1");
        List<Tarea> list= tareaUseCases.getTareas("pepito.pepe@gmail.com");
        assertEquals(1, list.get(0).getUsuariosAsignados().size());
    }

    @Test
    void cambiaDatosYEstado(){
        Tarea tarea = new Tarea()
                .setId("1")
                .setPropietario("pepito.pepe@gmail.com")
                .setTexto("Test")
                .setEstado("Pendiente")
                .setFechaCreacion(new Date().toString())
                .setPrioridad("Alta");
        tareaUseCases.crearTarea(tarea);
        Tarea tareaCambiada = new Tarea()
                .setTexto("Nuevo texto")
                .setEstado("Finalizado")
                .setPrioridad("Baja");
        tareaUseCases.cambiarDatos(new Usuario().setEmail("pepito.pepe@gmail.com"), "1", tareaCambiada);
        tareaUseCases.cambiarEstado(new Usuario().setEmail("pepito.pepe@gmail.com"), "1", tareaCambiada.getEstado());
        assertEquals(tareaUseCases.getDetalleTarea("1").getTexto(), tareaCambiada.getTexto());
        assertEquals(tareaUseCases.getDetalleTarea("1").getEstado(), tareaCambiada.getEstado());
    }

    @AfterEach
    void reset(){
        tareaUseCases.reset();
    }
}
