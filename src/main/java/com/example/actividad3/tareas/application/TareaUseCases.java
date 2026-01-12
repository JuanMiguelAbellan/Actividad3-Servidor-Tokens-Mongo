package com.example.actividad3.tareas.application;

import com.example.actividad3.tareas.domain.Tarea;
import com.example.actividad3.tareas.domain.TareaRepository;
import com.example.actividad3.usuarios.domain.Usuario;

import java.util.List;

public class TareaUseCases {

    private TareaRepository tareaRepository;

    public TareaUseCases(TareaRepository tareaRepository){
        this.tareaRepository=tareaRepository;
    }

    public Tarea getDetalleTarea(String id) {
        return  this.tareaRepository.getDetalleTarea(id);
    }

    public List<Tarea> getTareas(String emailPropietario) {
        return this.tareaRepository.getTareas(emailPropietario);
    }

    public void crearTarea(Tarea tarea) {
        this.tareaRepository.crearTarea(tarea);
    }

    public Tarea asignar(Usuario usuario, String id){
        return this.tareaRepository.asignar(usuario, id);
    }

    public Tarea cambiarEstado(String id, String estado) {
        return this.tareaRepository.cambiarEstado(id, estado);
    }

    public Tarea cambiarDatos(String id, Tarea tarea) {
        return this.tareaRepository.cambiarDatos(id, tarea);
    }

    public boolean comprobarPropietario(String eamil, String id){
        return this.tareaRepository.comprobarPropietario(eamil, id);
    }
}
