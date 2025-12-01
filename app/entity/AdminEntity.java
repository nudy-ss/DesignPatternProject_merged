package entity;

import manager.ResourceType;
import org.bson.types.ObjectId;
import reservation.ReservationManager;
import resource.SimpleItem;
import resource.SimpleLectureRoom;

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

    // ====================================================
    // 문자열 버전 registerResource
    // ====================================================
    public void registerResource(ReservationManager manager, String type, String name, int deposit) {
        ResourceType t = ResourceType.valueOf(type.toUpperCase());

        switch (t) {
            case LECTURE -> manager.addResource(new SimpleLectureRoom(name, deposit));
            case ITEM -> manager.addResource(new SimpleItem(name, deposit));
        }
    }

    // ====================================================
    // enum 버전 registerResource
    // ====================================================
    public boolean registerResource(ReservationManager manager, ResourceType type, String name, int deposit) {

        switch (type) {
            case LECTURE -> {
                manager.addResource(new SimpleLectureRoom(name, deposit));
                return true;
            }
            case ITEM -> {
                manager.addResource(new SimpleItem(name, deposit));
                return true;
            }
        }

        return false;
    }

    public void deleteResource(resource.Resource resource) {}
    public void viewAllResources() {}
    public void modifyResource(resource.Resource resource) {}
}
