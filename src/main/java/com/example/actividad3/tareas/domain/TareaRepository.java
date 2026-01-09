package com.example.actividad3.tareas.domain;

import java.util.List;

public interface TareaRepository {

    public Tarea getDetalleTarea(String id);
    public List<Tarea> getTareas(String emailPropietario);
    public void crearTarea(Tarea tarea);
}
