package reservation;

import resource.Resource;
import user.User;

import java.util.Date;

/**
 * 강의실 예약용 Concrete Product
 * - Reservation을 상속
 * - 필요 시 강의실 전용 필드/기능 추가 가능
 */
public class LectureReservation extends Reservation {

  public LectureReservation(User user, Resource resource, Date startDate, Date endDate) {
    super(user, resource, startDate, endDate);
  }

  @Override
  public String getDetails() {
    return "[강의실 예약] ID=" + getReservationId()
        + ", 자원=" + getResource().getName()
        + ", 예약자=" + getUser().getName()
        + ", 날짜=" + getStartDate()
        + ((getEndDate() != null) ? " ~ " + getEndDate() : "");
  }
}

