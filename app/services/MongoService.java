package services;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.typesafe.config.ConfigFactory;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class MongoService {
    private static Datastore datastore;

    public static  Datastore datastore(){
        if(datastore == null) {
            initDatastore();
        }
        return datastore;
    }

    private static void initDatastore(){

        final Morphia morphia = new Morphia();
        morphia.mapPackage("com.example.models");
        MongoClient mongoClient =  new MongoClient(new MongoClientURI(ConfigFactory.load().getString("mongodb.uri")));

        datastore = morphia.createDatastore(
                mongoClient, ConfigFactory.load().getString("mongodb.database")
        );
        datastore().ensureIndexes();
    }


}
