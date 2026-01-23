package com.example.actividad3.tareas.infrastructure;

import com.example.actividad3.context.db.MongoDBConnector;
import com.example.actividad3.tareas.domain.Tarea;
import com.example.actividad3.tareas.domain.TareaRepository;
import com.example.actividad3.usuarios.domain.Usuario;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TareaRepositoryMongo implements TareaRepository {
    @Override
    public Tarea getDetalleTarea(String id) {
        Tarea tarea = new Tarea();
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        FindIterable<Document> iterable = collection.find();

        for(Document document : iterable){
            if(document.getString("id").equals(id)){
                tarea.setId(document.getString("id"))
                        .setTexto(document.getString("texto"))
                        .setPrioridad(document.getString("prioridad"))
                        .setFechaCreacion(document.getString("fecha_creacion"))
                        .setFechaFinalizacion(document.getString("fecha_finalizacion"))
                        .setEstado(document.getString("estado"))
                        .setPropietario(new Usuario().setEmail(document.getString("usuario_propietario")));

                List<String> usuariosAignados = (ArrayList)document.get("usuarios_asignados");
                tarea.setUsuariosAsignados(usuariosAignados);
            }
        }
        return tarea;
    }

    @Override
    public List<Tarea> getTareas(String emailPropietario) {
        List<Tarea> listaTareas = new ArrayList<>();
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        FindIterable<Document> iterable = collection.find();

        for(Document document : iterable){
            if(document.getString("usuario_propietario").toString().equals(emailPropietario)){
                Tarea tarea = new Tarea();
                tarea.setId(document.getString("id"))
                                .setTexto(document.getString("texto"))
                                .setPrioridad(document.getString("prioridad"))
                                .setFechaCreacion(document.getString("fecha_creacion"))
                                .setFechaFinalizacion(document.getString("fecha_finalizacion"))
                                .setEstado(document.getString("estado"))
                                .setPropietario(emailPropietario);
                List<String> usuariosAignados = (ArrayList)document.get("usuarios_asignados");
                tarea.setUsuariosAsignados(usuariosAignados);
                listaTareas.add(tarea);
            }
        }
        return listaTareas;
    }

    @Override
    public void crearTarea(Tarea tarea) {
        Document document = new Document();
        document.append("id", tarea.getId());
        System.out.println(tarea.getId());
        document.append("texto", tarea.getTexto());
        document.append("prioridad", tarea.getPrioridad().toString());
        document.append("fecha_creacion", tarea.getFechaCreacion());
        document.append("fecha_finalizacion", tarea.getFechaFinalizacion());
        document.append("estado", tarea.getEstado().toString());
        document.append("usuario_propietario", tarea.getPropietario().getEmail());
        document.append("usuarios_asignados", tarea.getUsuariosAsignados());

        MongoDBConnector.getDatabase().getCollection("tareas").insertOne(document);
    }

    @Override
    public Tarea asignar(Usuario usuario, String id) {
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        collection.updateOne(Filters.eq("id", id), Updates.push("usuarios_asignados", usuario.getEmail()));

        return getDetalleTarea(id);
    }

    @Override
    public Tarea cambiarEstado(String id, String estado) {
        System.out.println(estado+id);
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        collection.updateOne(Filters.eq("id", id), Updates.set("estado", estado));

        return getDetalleTarea(id);
    }

    @Override
    public Tarea cambiarDatos(String id, Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");

        if(tarea.getTexto() != "" && tarea.getTexto() != null){
            collection.updateOne(Filters.eq("id", id), Updates.set("texto", tarea.getTexto()));
        }
        if(tarea.getPrioridad() != "" && tarea.getPrioridad() != null){
            collection.updateOne(Filters.eq("id", id), Updates.set("prioridad", tarea.getPrioridad().toString()));
        }
        if(tarea.getEstado() != "" && tarea.getEstado() != null){
            //Si cambia es estado a finalizado poner la fecha de finalizacion y si esta finalizada no se puede cambiar
            collection.updateOne(Filters.eq("id", id), Updates.set("estado", tarea.getEstado().toString()));
            collection.updateOne(Filters.eq("id", id), Updates.set("fecha_finalizacion", new Date().toString()));
        }
        if(tarea.getUsuariosAsignados() != null || !tarea.getUsuariosAsignados().isEmpty()){
            collection.updateOne(Filters.eq("id", id), Updates.set("usuarios_asignados", tarea.getUsuariosAsignados()));
        }

        return getDetalleTarea(id);
    }

    @Override
    public boolean comprobarPropietario(String email, String id) {

        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        FindIterable<Document> iterable = collection.find();
        for(Document document: iterable){
            if(document.getString("id").equals(id) && document.getString("usuario_propietario").equals(email)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        MongoDBConnector.getDatabase().getCollection("tareas").drop();
    }
}
