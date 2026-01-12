package com.example.actividad3.tareas.domain;

import com.example.actividad3.usuarios.domain.Usuario;

import java.util.List;

public interface TareaRepository {

    public Tarea getDetalleTarea(String id);
    public List<Tarea> getTareas(String emailPropietario);
    public void crearTarea(Tarea tarea);
    public Tarea asignar(Usuario usuario, String id);
    public Tarea cambiarEstado(String id,String estado);
    public Tarea cambiarDatos(String id, Tarea tarea);
    public boolean comprobarPropietario(String email, String id);
}
