package entity;

import manager.ResourceFileWriter;
import manager.ResourceType;
import org.bson.types.ObjectId;
import reservation.ReservationManager;
//사용 X
public class AdminEntity extends UserEntity {

    private ObjectId id;

    private String adminId;

    public AdminEntity() {}

    public AdminEntity(String studentId, String name, String adminId) {
        super(studentId, name);
        this.adminId = adminId;
    }

    public ObjectId getId() {
        return id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    // 문자열 버전
    public void registerResource(ReservationManager manager, String type, String name, int deposit) {
        manager.registerResource(type, name, deposit);
    }

    // enum 버전
    public boolean registerResource(ReservationManager manager, ResourceType type, String name, int deposit) {


//        boolean saved = ResourceFileWriter.appendResource(type.name(), name, deposit);
//        if (!saved) {
//            System.out.println("[실패] 동일한 이름의 자원이 이미 존재합니다: " + name);
//            return false;
//        }

//        manager.registerResource(type.name(), name, deposit);
//        System.out.println("[성공] 자원 등록 및 파일 저장 완료!");
        return true;
    }

    public void deleteResource(resource.Resource resource) {}
    public void viewAllResources() {}
    public void modifyResource(resource.Resource resource) {}
}
