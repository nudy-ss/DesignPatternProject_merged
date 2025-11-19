package resource;

import java.time.LocalTime;

/**
 * 시간대(슬롯)를 표현하는 클래스
 * 예: "09:00-11:00"
 * - 시작/종료 시간을 LocalTime으로 저장
 * - TimeSlot 간 겹침 여부 비교 기능 제공
 */
public class TimeSlot {

  private final LocalTime startTime;
  private final LocalTime endTime;

  /**
   * 문자열 형식 ("HH:mm-HH:mm")을 받아 TimeSlot 생성
   * 예: new TimeSlot("09:00-11:00");
   */
  public TimeSlot(String range) {
    if (range == null || !range.contains("-")) {
      throw new IllegalArgumentException("형식이 잘못되었습니다. 예: 09:00-11:00");
    }
    try {
      String[] parts = range.split("-");
      this.startTime = LocalTime.parse(parts[0].trim());
      this.endTime   = LocalTime.parse(parts[1].trim());
    } catch (Exception e) {
      throw new IllegalArgumentException("시간 형식이 잘못되었습니다. 예: 09:00-11:00");
    }

    if (!startTime.isBefore(endTime)) {
      throw new IllegalArgumentException("시작 시간은 종료 시간보다 빨라야 합니다.");
    }
  }

  public LocalTime getStartTime() { return startTime; }
  public LocalTime getEndTime()   { return endTime; }

  /**
   * 두 TimeSlot(시간대)이 겹치는지 확인
   * 예:
   * 09:00-11:00 vs 10:00-12:00 → true
   * 09:00-11:00 vs 11:00-13:00 → false
   */
  public boolean isOverlapping(TimeSlot other) {
    if (other == null) return false;

    // 겹침 조건: A.start < B.end && A.end > B.start
    return startTime.isBefore(other.endTime)
        && endTime.isAfter(other.startTime);
  }

  @Override
  public String toString() {
    return startTime + "-" + endTime;
  }
}
