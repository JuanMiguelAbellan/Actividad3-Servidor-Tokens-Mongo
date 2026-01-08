package com.example.actividad3.tareas.domain;

import com.example.actividad3.tareas.domain.context.Estado;
import com.example.actividad3.tareas.domain.context.Prioridad;
import com.example.actividad3.usuarios.domain.Usuario;
import com.example.actividad3.usuarios.domain.UsuarioRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tarea {
    private String texto;
    private Prioridad prioridad;
    private Estado estado;
    private Date fechaCreacion;
    private Date fechaFinalizacion;
    private Usuario propietario;
    private List<Usuario> usuariosAsignados = new ArrayList<>();


}
