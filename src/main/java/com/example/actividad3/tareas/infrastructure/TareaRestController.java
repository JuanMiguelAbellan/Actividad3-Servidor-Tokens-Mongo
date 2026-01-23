package com.example.actividad3.tareas.infrastructure;

import com.example.actividad3.tareas.application.TareaUseCases;
import com.example.actividad3.tareas.domain.Tarea;
import com.example.actividad3.usuarios.domain.Usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
public class TareaRestController {

    private TareaUseCases tareaUseCases;

    public TareaRestController(){
        this.tareaUseCases= new TareaUseCases(new TareaRepositoryMongo());
    }

    @GetMapping("/api/tareas")
    public List<Tarea> verTareas(Authentication auth ){
        Usuario usuario = new Usuario().setEmail(auth.getName());
        return  this.tareaUseCases.getTareas(usuario.getEmail());
    }


    @GetMapping("/api/tareas/{id}")
    public Tarea tareaDetalle(Authentication auth,
                                @PathVariable String id){
        Usuario usuario = new Usuario().setEmail(auth.getName());
        System.out.println("Ha entrado");
        return  this.tareaUseCases.getDetalleTarea(id);
    }

    @PostMapping("/api/tareas")
    public void crearTarea(Authentication auth,
                            @RequestBody Tarea tarea){
        Usuario usuario = new Usuario().setEmail(auth.getName());
        tarea.setPropietario(usuario);
        List<String> usuariosAsignados= new ArrayList<>();
        usuariosAsignados.add(usuario.getEmail());
        tarea.setUsuariosAsignados(usuariosAsignados);
        tarea.setEstado("Pendiente");
        tarea.setFechaCreacion(new Date().toString());
        this.tareaUseCases.crearTarea(tarea);
    }

    @PutMapping("/api/tareas/{id}")
    public Tarea asignarUsuarioTarea(Authentication auth,
                                     @RequestBody Usuario user,
                                     @PathVariable String id){
        Usuario usuario = new Usuario().setEmail(auth.getName());
        return this.tareaUseCases.asignar(usuario, user, id);
    }

    @PutMapping("/api/tareas/{id}/estado")
    public Tarea cambiarEstado(Authentication auth,
                               @RequestBody String estado,
                               @PathVariable String id){
        Usuario usuario = new Usuario().setEmail(auth.getName());
        return this.tareaUseCases.cambiarEstado(usuario, id, estado);
    }

    @PutMapping("/api/tareas/{id}/datos")
    public Tarea cambiarDatos(Authentication auth,
                               @RequestBody Tarea tarea,
                               @PathVariable String id){
        Usuario usuario = new Usuario().setEmail(auth.getName());
        return this.tareaUseCases.cambiarDatos(usuario, id, tarea);
    }
}
