package Repository;

import com.mongodb.client.MongoDatabase;
import entity.ReservationEntity;
import org.bson.codecs.configuration.CodecRegistry;

public class ReservationRepository extends RepositoryBase<ReservationEntity> {
    public ReservationRepository(MongoDatabase db, CodecRegistry codecRegistry) {
        super(db, codecRegistry, "reservations", ReservationEntity.class);
    }
}