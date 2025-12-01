package Repository;

import com.mongodb.client.MongoDatabase;
import entity.ReservationEntity;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.client.model.Filters.eq;

public class ReservationRepository extends RepositoryBase<ReservationEntity> {

    public ReservationRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "reservations", ReservationEntity.class);
    }

    // 🔥 예약 수정(반납 등)
    public void update(ReservationEntity entity) {
        collection.replaceOne(eq("_id", entity.getId()), entity);
    }
}
