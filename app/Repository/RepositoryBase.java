package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.AdminEntity;
import org.bson.codecs.configuration.CodecRegistry;
import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.eq;

public class RepositoryBase<T> {

    protected final MongoCollection<T> collection;

    public RepositoryBase(MongoDatabase db,
                          CodecRegistry codecRegistry,
                          String collectionName,
                          Class<T> clazz) {

        this.collection = (MongoCollection<T>) db
            .getCollection(collectionName, clazz)
            .withCodecRegistry(codecRegistry);
    }

    public void save(T entity) {
        collection.insertOne(entity);
    }

    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    public T findByName(String name) {
        return collection.find(eq("name", name)).first();
    }

    public T findByField(String fieldName, String value) {
        return collection.find(eq(fieldName, value)).first();
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
