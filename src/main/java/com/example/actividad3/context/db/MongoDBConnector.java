package com.example.actividad3.context.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoDBConnector {
    private static MongoDBConnector mongoConnection;
    private MongoClient mongoClient;
    private  MongoDatabase database;


    private MongoDBConnector(){
        this.mongoClient = MongoClients.create("mongodb+srv://240023_db_user:Qzmpwxno1029.@cluster1.0thd0jq.mongodb.net");
        this.database = mongoClient.getDatabase("Todo");
    }

    public static MongoDatabase getDatabase() {
        if(mongoConnection == null){
            mongoConnection = new MongoDBConnector();
        }
        return mongoConnection.database;
    }
}
