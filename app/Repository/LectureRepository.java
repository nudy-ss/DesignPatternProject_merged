package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import entity.LectureEntity;

import static com.mongodb.client.model.Filters.eq;

public class LectureRepository {

    private final MongoCollection<LectureEntity> collection;

    public LectureRepository(MongoDatabase db, org.bson.codecs.configuration.CodecRegistry codecRegistry) {
        this.collection = db.getCollection("lectures", LectureEntity.class)
            .withCodecRegistry(codecRegistry);
    }

    // CREATE
    public void save(LectureEntity entity) {
        collection.insertOne(entity);
    }

    // READ
    public LectureEntity findByName(String name) {
        return collection.find(eq("name", name)).first();
    }

    public Iterable<LectureEntity> findAll() {
        return collection.find();
    }

    // UPDATE
    public void update(LectureEntity entity) {
        collection.replaceOne(eq("name", entity.getName()), entity);
    }

    // DELETE or Soft Delete (available=false)
    public void deleteByName(String name) {
        collection.deleteOne(eq("name", name));
    }
}
