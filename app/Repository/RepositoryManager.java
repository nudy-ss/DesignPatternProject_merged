package Repository;

import entity.LectureEntity;
import entity.db.DBConnection;

public class RepositoryManager {

    // 유일한 인스턴스
    private static volatile RepositoryManager instance;

    public final LectureRepository lectures;
    public final UserRepository users;
    public final AdminRepository admins;
    public final ResourceRepository resources;
    public final ReservationRepository reservations;

    // private 생성자 (외부에서 new 금지)
    private RepositoryManager() {
        users = new UserRepository(DBConnection.getDatabase(), DBConnection.getCodecRegistry());
        admins = new AdminRepository(DBConnection.getDatabase(), DBConnection.getCodecRegistry());
        resources = new ResourceRepository(DBConnection.getDatabase(), DBConnection.getCodecRegistry());
        reservations = new ReservationRepository(DBConnection.getDatabase(), DBConnection.getCodecRegistry());
        lectures = new LectureRepository(DBConnection.getDatabase(), DBConnection.getCodecRegistry());
    }

    // Thread-safe Lazy Singleton
    public static RepositoryManager getInstance() {
        if (instance == null) {                          // 첫 체크 (lock 없음)
            synchronized (RepositoryManager.class) {
                if (instance == null) {                  // 두 번째 체크 (lock 안에서)
                    instance = new RepositoryManager();
                }
            }
        }
        return instance;
    }
}
