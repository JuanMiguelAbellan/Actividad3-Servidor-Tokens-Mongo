package com.example.actividad3.usuarios.infrastructure.db;

import com.example.actividad3.context.db.MongoDBConnector;
import com.example.actividad3.usuarios.domain.Usuario;
import com.example.actividad3.usuarios.domain.UsuarioRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;


public class UsuarioRepositoryMongo implements UsuarioRepository {
    @Override
    public Boolean registro(Usuario usuario) {
        Document document = new Document();
        document.append("email", usuario.getEmail());
        document.append("password", usuario.getPassword());

        MongoDBConnector.getDatabase().getCollection("usuarios").insertOne(document);
        return true;
    }

    @Override
    public Usuario login(Usuario usuario) {
        MongoCollection<Document> collection = MongoDBConnector.getDatabase().getCollection("usuarios");
        FindIterable<Document> iterable = collection.find();

        for(Document document : iterable){
            System.out.println();
            if(document.getString("email").toString().equals(usuario.getEmail())){
                return new Usuario().setEmail(document.getString("email")).setPassword(document.getString("password"));
            }
        }
        return null;
    }
}
