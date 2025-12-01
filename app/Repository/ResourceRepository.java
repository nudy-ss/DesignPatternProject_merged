package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.ResourceEntity;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ResourceRepository extends RepositoryBase<ResourceEntity> {

    private final MongoCollection<ResourceEntity> collection;

    public ResourceRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "resources", ResourceEntity.class);
        this.collection = db.getCollection("resources", ResourceEntity.class)
            .withCodecRegistry(codecRegistry);
    }

    // CREATE
    public void save(ResourceEntity entity) {
        collection.insertOne(entity);
    }

    // READ
    @Override
    public ResourceEntity findByName(String name) {
        return collection.find(eq("name", name)).first();
    }

    public List<ResourceEntity> findByType(String type) {
        return collection.find(eq("type", type)).into(new ArrayList<>());
    }

    @Override
    public List<ResourceEntity> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    // UPDATE
    public void update(ResourceEntity entity) {
        collection.replaceOne(eq("name", entity.getName()), entity);
    }

    // DELETE
    public void deleteByName(String name) {
        collection.deleteOne(eq("name", name));
    }
}
