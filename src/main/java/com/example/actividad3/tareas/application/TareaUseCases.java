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

    public Tarea asignar(Usuario propietario, Usuario usuario, String id){
        if(tareaRepository.comprobarPropietario(propietario.getEmail(), id)) {
            return this.tareaRepository.asignar(usuario, id);
        }
        else return null;
    }

    public Tarea cambiarEstado(Usuario propietario, String id, String estado) {

        if(this.tareaRepository.comprobarPropietario(propietario.getEmail(), id)) {
            System.out.println(estado+id+propietario.getEmail());
            return this.tareaRepository.cambiarEstado(id, estado);
        }else {
            return null;
        }
    }

    public Tarea cambiarDatos(Usuario propietario, String id, Tarea tarea) {
        if(this.tareaRepository.comprobarPropietario(propietario.getEmail(), id)){
            return this.tareaRepository.cambiarDatos(id, tarea);
        }else {
            return null;
        }
    }

    public boolean comprobarPropietario(String eamil, String id){
        return this.tareaRepository.comprobarPropietario(eamil, id);
    }

    public void reset(){
        this.tareaRepository.reset();
    }
}
