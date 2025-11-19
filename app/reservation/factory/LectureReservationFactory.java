package reservation.factory;

import Repository.RepositoryManager;
import reservation.LectureReservation;
import reservation.Reservation;
import reservation.ReservationManager;
import resource.ReservableResource;
import resource.Resource;
import resource.TimeSlot;
import user.User;

import java.util.Date;

public class LectureReservationFactory extends ReservationFactory {

  public LectureReservationFactory(ReservationManager manager, RepositoryManager repositoryManager) {
    super(manager,repositoryManager);
  }

  @Override
  protected Reservation build(User user, Resource resource, Date start, Date end, TimeSlot slot) {
    return null;
  }


  @Override
  protected void validate(User user, Resource resource, Date start, Date end) {
    super.validate(user, resource, start, end);

    if (!(resource instanceof ReservableResource)) {
      throw new IllegalArgumentException("강의실 예약에는 ReservableResource만 가능합니다.");
    }

    // 중복 예약 정책 (B: 시간이 겹치면 금지)
    boolean overlaps = manager.hasTimeConflict(resource, start, end);
    if (overlaps) {
      throw new IllegalStateException("해당 시간에는 이미 예약이 존재합니다.");
    }
  }

  @Override
  protected Reservation build(User user, Resource resource, Date start, Date end) {
    return new LectureReservation(user, resource, start, end);
  }

  @Override
  protected void applyPolicies(Reservation r) {
    super.applyPolicies(r);
    // 필요 시 보증금 차감 로직 추가 가능
    // 예: depositManager.payDeposit(r.getUser(), r.getResource().getDeposit());
  }
}
