package Repository;

import com.mongodb.client.MongoDatabase;
import entity.AdminEntity;
import entity.LectureEntity;
import org.bson.codecs.configuration.CodecRegistry;

public class LectureRepository extends RepositoryBase<LectureEntity> {
    public LectureRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "lectures",LectureEntity.class);
    }
}