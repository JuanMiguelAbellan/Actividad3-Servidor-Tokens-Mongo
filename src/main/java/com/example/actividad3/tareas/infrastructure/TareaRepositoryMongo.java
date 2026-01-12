package com.example.actividad3.tareas.infrastructure;

import com.example.actividad3.context.db.MongoDBConnector;
import com.example.actividad3.tareas.domain.Tarea;
import com.example.actividad3.tareas.domain.TareaRepository;
import com.example.actividad3.tareas.domain.entities.Estado;
import com.example.actividad3.tareas.domain.entities.Prioridad;
import com.example.actividad3.usuarios.domain.Usuario;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TareaRepositoryMongo implements TareaRepository {
    @Override
    public Tarea getDetalleTarea(String id) {
        Tarea tarea = new Tarea();
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        FindIterable<Document> iterable = collection.find();

        for(Document document : iterable){
            if(document.getString("_id").toString().equals(id)){
                tarea.setId(document.getString("_id"))
                        .setTexto(document.getString("texto"))
                        .setPrioridad(Prioridad.valueOf(document.getString("prioridad")))
                        .setFechaCreacion(new Date(document.getString("fecha_creacion")))
                        .setFechaFinalizacion(new Date(document.getString("fecha_finalizacion")))
                        .setEstado(Estado.valueOf(document.getString("estado")))
                        .setPropietario(new Usuario(document.getString("usuario_propietario"), null));
                List<String> usuariosAignados = new ArrayList<>();
                List<Document> usuariosDoc = document.getList("usuarios_asignados", Document.class);
                if(usuariosDoc != null){
                    for(Document usuarioDoc : usuariosDoc){
                        usuariosAignados.add(usuarioDoc.toString());
                    }
                }
                tarea.setUsuariosAsignados(usuariosAignados);
            }
            return tarea;
        }
        return null;
    }

    @Override
    public List<Tarea> getTareas(String emailPropietario) {
        List<Tarea> listaTareas = new ArrayList<>();
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        FindIterable<Document> iterable = collection.find();

        for(Document document : iterable){
            if(document.getString("usuario_propietario").toString().equals(emailPropietario)){
                Tarea tarea = new Tarea();
                tarea.setId(document.getString("_id"))
                                .setTexto(document.getString("texto"))
                                        .setPrioridad(Prioridad.valueOf(document.getString("prioridad")))
                                                .setFechaCreacion(new Date(document.getString("fecha_creacion")))
                                                        .setFechaFinalizacion(new Date(document.getString("fecha_finalizacion")))
                                                                .setEstado(Estado.valueOf(document.getString("estado")))
                                                                        .setPropietario(new Usuario(document.getString("usuario_propietario"), null));
                List<String> usuariosAignados = new ArrayList<>();
                List<Document> usuariosDoc = document.getList("usuarios_asignados", Document.class);
                if(usuariosDoc != null){
                    for(Document usuarioDoc : usuariosDoc){
                        usuariosAignados.add(usuarioDoc.toString());
                    }
                }
                tarea.setUsuariosAsignados(usuariosAignados);
                listaTareas.add(tarea);
            }
        }
        return null;
    }

    @Override
    public void crearTarea(Tarea tarea) {
        Document document = new Document();
        document.append("_id", tarea.getId());
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
        collection.updateOne(Filters.eq("_id", id), Updates.push("usuarios_asignados", usuario.getEmail()));

        return getDetalleTarea(id);
    }

    @Override
    public Tarea cambiarEstado(String id, String estado) {
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        collection.updateOne(Filters.eq("_id", id), Updates.set("estado", Estado.valueOf(estado)));

        return getDetalleTarea(id);
    }

    @Override
    public Tarea cambiarDatos(String id, Tarea tarea) {
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");

        if(tarea.getTexto() != ""){
            collection.updateOne(Filters.eq("_id", id), Updates.set("texto", tarea.getTexto()));
        }
        if(tarea.getPrioridad() != null){
            collection.updateOne(Filters.eq("_id", id), Updates.set("prioridad", tarea.getPrioridad().toString()));
        }
        if(tarea.getEstado() != null){
            collection.updateOne(Filters.eq("_id", id), Updates.set("estado", tarea.getEstado().toString()));
        }
        if(tarea.getPropietario() != null){
            collection.updateOne(Filters.eq("_id", id), Updates.set("usuario_propietario", tarea.getPropietario().getEmail()));
        }
        if(tarea.getUsuariosAsignados() != null || !tarea.getUsuariosAsignados().isEmpty()){
            collection.updateOne(Filters.eq("_id", id), Updates.set("usuarios_asigandos", tarea.getUsuariosAsignados()));
        }
        if(tarea.getFechaCreacion() != null){
            collection.updateOne(Filters.eq("_id", id), Updates.set("fecha_creacion", tarea.getFechaCreacion()));
        }
        if(tarea.getFechaFinalizacion() != null){
            collection.updateOne(Filters.eq("_id", id), Updates.set("fecha_finalizacion", tarea.getFechaFinalizacion()));
        }

        return getDetalleTarea(id);
    }

    @Override
    public boolean comprobarPropietario(String email, String id) {
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("tareas");
        FindIterable<Document> iterable = collection.find();
        for(Document document: iterable){
            if(document.getString("_id").equals(id) && document.getString("usuario_propietario").equals(email)){
                return true;
            }
        }
        return false;
    }
}
