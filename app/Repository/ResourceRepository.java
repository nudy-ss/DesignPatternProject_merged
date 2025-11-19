package Repository;

import com.mongodb.client.MongoDatabase;
import entity.ResourceEntity;
import org.bson.codecs.configuration.CodecRegistry;

public class ResourceRepository extends RepositoryBase<ResourceEntity> {
    public ResourceRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "resources", ResourceEntity.class);
    }
}