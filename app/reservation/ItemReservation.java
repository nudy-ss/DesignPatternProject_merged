package reservation;

import resource.Resource;
import user.User;

import java.util.Date;

/**
 * 대여품(Item) 예약/대여용 Concrete Product
 * - Reservation을 상속
 * - 대여 전용 상세 정보 제공
 */
public class ItemReservation extends Reservation {

  public ItemReservation(User user, Resource resource, Date startDate, Date endDate) {
    super(user, resource, startDate, endDate);
  }

  @Override
  public String getDetails() {
    return "[대여 예약] ID=" + getReservationId()
        + ", 물품=" + getResource().getName()
        + ", 사용자=" + getUser().getName()
        + ", 대여일=" + getStartDate()
        + ((getEndDate() != null) ? " ~ 반납예정:" + getEndDate() : "");
  }
}
