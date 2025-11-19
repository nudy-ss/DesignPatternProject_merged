package entity.db;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.*;

public class DBConnection {

    public static MongoClient client;
    public static MongoDatabase database;
    public static CodecRegistry codecRegistry;

    static {
        String uri = "mongodb+srv://staran1227:7Gg8Ss1KNaouG1SE@cluster0.f7p4vnt.mongodb.net";

        client = MongoClients.create(uri);
        database = client.getDatabase("DesignPattern");

        CodecRegistry pojo = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojo);

        System.out.println("[MongoDB] Connected to DesignPattern");
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }
}
