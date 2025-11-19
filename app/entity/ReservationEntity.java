package entity;

import org.bson.types.ObjectId;
import resource.Resource;
import resource.TimeSlot;
import user.User;

import java.util.Date;

/**
 * 예약의 기본 클래스 (추상 Product)
 * - 팩토리 메서드 패턴의 공통 기반 클래스
 * - 날짜(Date) + 시간(TimeSlot) 기반 중복 검사 지원
 */

public class ReservationEntity {

    private ObjectId id;

    private User user;          // DB 저장 가능한 형태의 UserEntity 필요

    private Resource resource;  // ResourceEntity 필요 (Resource 인터페이스는 저장 불가)

    private Date startDate;
    private Date endDate;

    private TimeSlot timeSlot;          // TimeSlot 은 저장 불가 → 문자열 변환 필요

    private String eventName;
    private boolean returned;

    public ReservationEntity() {}

    public ReservationEntity(User user,
                             Resource resource,
                             Date startDate,
                             Date endDate,
                             TimeSlot timeSlot,
                             String eventName) {

        this.user = user;
        this.resource = resource;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeSlot = timeSlot;
        this.eventName = eventName;
        this.returned = false;
    }


    // Getter / Setter
    public ObjectId getId() { return id; }
    public User getUser() { return user; }
    public Resource getResource() { return resource; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public boolean isReturned() { return returned; }
    public void markReturned() { this.returned = true; }
}