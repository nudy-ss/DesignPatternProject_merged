package entity;

import org.bson.types.ObjectId;
import resource.Resource;
import resource.TimeSlot;
import user.User;

import java.util.Date;

public class ReservationEntity {

    private ObjectId id;

    private String userId;
    private String userName;

    private String resourceName;
    private String resourceType;

    private Date startDate;
    private Date endDate;

    private String timeSlot;
    private String eventName;

    private boolean returned;

    public ReservationEntity(User user, Resource resource, Date start, Date end, TimeSlot slot, String s) {}

    public ReservationEntity(String userId, String userName,
                             String resourceName, String resourceType,
                             Date startDate, Date endDate,
                             String timeSlot, String eventName) {

        this.userId = userId;
        this.userName = userName;

        this.resourceName = resourceName;
        this.resourceType = resourceType;

        this.startDate = startDate;
        this.endDate = endDate;

        this.timeSlot = timeSlot;
        this.eventName = eventName;

        this.returned = false;
    }

    // ========= Getter =========
    public ObjectId getId() { return id; }

    public String getUserId() { return userId; }
    public String getUserName() { return userName; }

    public String getResourceName() { return resourceName; }
    public String getResourceType() { return resourceType; }

    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }

    public String getTimeSlot() { return timeSlot; }
    public String getEventName() { return eventName; }

    public boolean isReturned() { return returned; }

    // ========= Setter =========
    public void setId(ObjectId id) { this.id = id; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setUserName(String userName) { this.userName = userName; }

    public void setResourceName(String resourceName) { this.resourceName = resourceName; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public void setReturned(boolean returned) { this.returned = returned; }
}
