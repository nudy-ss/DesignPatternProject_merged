package Repository;

import com.mongodb.client.MongoDatabase;
import entity.UserEntity;
import org.bson.codecs.configuration.CodecRegistry;

public class UserRepository extends RepositoryBase<UserEntity> {
    public UserRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "users", UserEntity.class);
    }
}