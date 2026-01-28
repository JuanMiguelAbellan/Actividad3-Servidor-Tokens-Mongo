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

        /*Para insertar el array solo con los campos que queremos
        List<Document> asistentesList = new ArrayList<>();
        for(Usuario u : tarea.getUsuariosAsignados){
         if (u != null && u.getId() != null) {
            asistentesList.add(new Document()
                    .append("id", u.getId())
                    .append("email", u.getEmail())
            );
         }
        }
        document.append("asistentes", asistentesList);

        Para hacer el select habria que crear una nueva clase UsuarioResumenDTO solo con los campos que se quieran mostrar y en lugar de new Ususario() seria new UsuarioResumenDTO()
        Y para que la tarea tenga una unica lista hacer una interfaz Usuario y los DTO que la implementen y la lista es de la interfaz
        O 2 DTO de tareas, una con lista de Usuarios normales y otra con lista de UsuariosResumen

        List<Document> asistentesDocs = (List<Document>) doc.get("asistentes");
        List<UsuarioResumenDTO> usuariosAsignados = new ArrayList<>();

        if (asistentesDocs != null) {
            for (Document asistenteDoc : asistentesDocs) {
                UsuarioResumenDTO usuario = new UsuarioRsumenDTO()
                        .setId(asistenteDoc.getString("id"))
                        .setEmail(asistenteDoc.getString("email"));

                usuariosAsignados.add(usuario);
            }
        }
        tarea.setUsuariosAsignados(usuariosAsignados);
        tareas.add(tarea);

        Para filtrar por id del ususario que este dentro de asistentes
        Document filtro = new Document("asistentes.id", usuarioId);

        FindIterable<Document> iterable = collection.find(filtro);

        O
        FindIterable<Document> iterable = collection.find(Filters.eq("asistentes.id", usuarioId));
        */


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
        if(tarea.getEstado() != "" && tarea.getEstado() != null && tarea.getEstado()== "Finalizado"){
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
        //Para borrar los datos sin dropear toda la coleccion, por que al poner un  documento vacio significa todos los documentos si quisieramos borrar todos los de un usuario deleteMany({propietario: "id"}) o deleteMany(Filters.eq("anfitrion", usuarioId))
        // MongoDBConnector.getDatabase().getCollection("tareas").deleteMany(new Document());
        MongoDBConnector.getDatabase().getCollection("tareas").drop();
    }
}
