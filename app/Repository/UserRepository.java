package Repository;

import com.mongodb.client.MongoDatabase;
import entity.ReservationEntity;
import entity.UserEntity;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.client.model.Filters.eq;

public class UserRepository extends RepositoryBase<UserEntity> {

    public UserRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "users", UserEntity.class);
    }

    public UserEntity findByStudentId(String studentId) {
        return findByField("studentId", studentId);
    }
}

