package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;

public class RepositoryBase<T> {

    private final MongoCollection<T> collection;

    public RepositoryBase(MongoDatabase db,
                          CodecRegistry codecRegistry,
                          String collectionName,
                          Class<T> clazz) {

        this.collection = db.getCollection(collectionName, clazz)
                .withCodecRegistry(codecRegistry);
    }
    public T findByName(String name) {
        return getCollection()
                .find(eq("name", name))
                .first();
    }


    public void save(T entity) {
        collection.insertOne(entity);
    }

    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    public T findFirst() {
        return collection.find().first();
    }

    public void deleteAll() {
        collection.deleteMany(new org.bson.Document());
    }

    public MongoCollection<T> getCollection() {
        return collection;
    }
}
