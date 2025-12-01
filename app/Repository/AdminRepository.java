package Repository;

import com.mongodb.client.MongoDatabase;
import entity.AdminEntity;
import org.bson.codecs.configuration.CodecRegistry;

public class AdminRepository extends RepositoryBase<AdminEntity> {

    public AdminRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "admins", AdminEntity.class);
    }

    public AdminEntity findByStudentId(String studentId) {
        return findByField("studentId", studentId);
    }
}

