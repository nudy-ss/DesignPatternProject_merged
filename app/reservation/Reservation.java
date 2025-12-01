package reservation;

import resource.Resource;
import resource.TimeSlot;
import user.User;

import java.util.Date;

/**
 * 예약의 기본 클래스 (추상 Product)
 * - 팩토리 메서드 패턴의 공통 기반 클래스
 * - 날짜(Date) + 시간(TimeSlot) 기반 중복 검사 지원
 */
public class Reservation {
  private String reservationId;
  private final User user;
  private final Resource resource;
  private final Date startDate;
  private final Date endDate;
  private final TimeSlot timeSlot;
  private String eventName;
  private boolean isReturned;

  public Reservation(User user, Resource resource, Date startDate, Date endDate) {
    this(user, resource, startDate, endDate, null);
  }

  public Reservation(User user, Resource resource, Date startDate, Date endDate, TimeSlot timeSlot) {
    this.user = user;
    this.resource = resource;
    this.startDate = startDate;
    this.endDate = endDate;
    this.timeSlot = timeSlot;
  }

  // 겹침 검사 (날짜 + 시간대)
  public boolean isOverlapping(Date otherStart, Date otherEnd) {
    // 날짜 비교 (endDate가 null이면 하루 단위 예약으로 간주)
    boolean sameDay = this.startDate.equals(otherStart);

    if (!sameDay) return false; // 날짜 다르면 겹치지 않음

    // 시간 슬롯이 없는 경우(대여품) → 같은 날이면 겹침 처리
    TimeSlot otherSlot = null;
    if (this.timeSlot == null || otherSlot == null) return true;

    // 시간대 겹침 체크
    return this.timeSlot.isOverlapping(otherSlot);
  }

  // ================== Getter / Setter ==================
  public void setReservationId(String id) { this.reservationId = id; }
  public String getReservationId() { return reservationId; }
  public User getUser() { return user; }
  public Resource getResource() { return resource; }
  public Date getStartDate() { return startDate; }
  public Date getEndDate() { return endDate; }
  public TimeSlot getTimeSlot() { return timeSlot; }
  public boolean isReturned() { return isReturned; }
  public void markReturned() { this.isReturned = true; }

  public String getEventName() { return eventName; }
  public void setEventName(String eventName) { this.eventName = eventName; }
  public String getDetails() {
    return "[예약] ID=" + reservationId +
        ", 자원=" + resource.getName() +
        ", 사용자=" + user.getName() +
        ", 날짜=" + startDate +
        ((timeSlot != null) ? " " + timeSlot : "") +
        (isReturned ? " (반납 완료)" : "");
  }
}
