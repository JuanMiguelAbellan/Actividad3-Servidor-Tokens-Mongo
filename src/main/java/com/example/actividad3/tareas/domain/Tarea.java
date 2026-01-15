package com.example.actividad3.tareas.domain;

import com.example.actividad3.usuarios.domain.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tarea {
    private String _id;
    private String texto;
    private String prioridad;
    private String estado;
    private String fecha_creacion, fecha_finalizacion;
    private Usuario propietario;
    private List<String> usuariosAsignados = new ArrayList<>();

    public Tarea() {
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

    public String getPrioridad() {
        return prioridad;
    }

    public Tarea setPrioridad(String prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public String getEstado() {
        return estado;
    }

    public Tarea setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public String getFechaCreacion() {
        return fecha_creacion;
    }

    public Tarea setFechaCreacion(String fechaCreacion) {
        this.fecha_creacion = fechaCreacion;
        return this;
    }

    public String getFechaFinalizacion() {
        return fecha_finalizacion;
    }

    public Tarea setFechaFinalizacion(String fechaFinalizacion) {
        this.fecha_finalizacion = fechaFinalizacion;
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
