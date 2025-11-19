package reservation.factory;

import Repository.RepositoryManager;
import reservation.ItemReservation;
import reservation.Reservation;
import reservation.ReservationManager;
import resource.RentableResource;
import resource.Resource;
import resource.TimeSlot;
import user.User;

import java.util.Date;

/**
 * 대여품(Item) 예약용 Factory (Concrete Creator)
 * - 대여 가능 여부 확인
 * - 필요한 경우 보증금 정책 적용 가능
 */
public class ItemReservationFactory extends ReservationFactory {

  public ItemReservationFactory(ReservationManager manager, RepositoryManager repositoryManager) {
    super(manager,repositoryManager);
  }

  @Override
  protected Reservation build(User user, Resource resource, Date start, Date end, TimeSlot slot) {
    return null;
  }

  @Override
  protected void validate(User user, Resource resource, Date start, Date end) {
    super.validate(user, resource, start, end);

    if (!(resource instanceof RentableResource)) {
      throw new IllegalArgumentException("대여 예약에는 RentableResource만 가능합니다.");
    }

    RentableResource item = (RentableResource) resource;

    // 대여 가능한지 확인
    if (!item.checkStock()) {
      throw new IllegalStateException("해당 물품은 현재 대여 불가합니다. (재고 없음)");
    }

    // 대여는 start/end 중복 개념이 약해서 기본적으로 통과하도록 설정
    // 필요하다면 "사용자가 동시에 같은 물건 2개 대여 금지" 등 정책 추가 가능
  }

  @Override
  protected Reservation build(User user, Resource resource, Date start, Date end) {
    return new ItemReservation(user, resource, start, end);
  }

  @Override
  protected void applyPolicies(Reservation reservation) {
    super.applyPolicies(reservation);
    // 필요 시 보증금 차감 정책 추가 가능
    // 예) depositManager.payDeposit(reservation.getUser(), reservation.getResource().getDeposit());
  }
}
