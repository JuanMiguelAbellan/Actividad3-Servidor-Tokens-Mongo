package com.example.actividad3.tareas.domain;

import com.example.actividad3.tareas.domain.entities.Estado;
import com.example.actividad3.tareas.domain.entities.Prioridad;
import com.example.actividad3.usuarios.domain.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tarea {
    private String _id;
    private String texto;
    private Prioridad prioridad;
    private Estado estado;
    private Date fechaCreacion, fechaFinalizacion;
    private Usuario propietario;
    private List<String> usuariosAsignados = new ArrayList<>();

    public Tarea() {
        this.fechaCreacion = new Date();
    }

    public String getId() {
        return _id;
    }

    public Tarea setId(String id) {
        this._id = id;
        return this;
    }

    public String getTexto() {
        return texto;
    }

    public Tarea setTexto(String texto) {
        this.texto = texto;
        return this;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public Tarea setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public Estado getEstado() {
        return estado;
    }

    public Tarea setEstado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Tarea setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public Tarea setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
        return this;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public Tarea setPropietario(Usuario propietario) {
        this.propietario = propietario;
        return this;
    }

    public List<String> getUsuariosAsignados() {
        return usuariosAsignados;
    }

    public Tarea setUsuariosAsignados(List<String> usuariosAsignados) {
        this.usuariosAsignados = usuariosAsignados;
        return this;
    }
    public void asignar(Usuario usuario){
        this.usuariosAsignados.add(usuario.getEmail());
    }
}
