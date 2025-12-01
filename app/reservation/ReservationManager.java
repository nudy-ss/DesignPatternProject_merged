package reservation;

import Repository.RepositoryManager;
import entity.LectureEntity;
import entity.ResourceEntity;
import observer.EventType;
import observer.Subject;
import resource.*;
import user.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ✔ 시설 예약 = Date(날짜) + TimeSlot
 * ✔ 물품 대여 = Date(start) + Date(end)
 * ✔ 모든 Reservation은 reservations 리스트에 저장
 * ✔ UI와 100% 호환되는 구조
 */
public class ReservationManager extends Subject {

  private final List<Reservation> reservations = new ArrayList<>();
  private final List<ReservableResource> reservableResources = new ArrayList<>();
  private final List<RentableResource> rentableResources = new ArrayList<>();


  // =====================================================
  //  자원 조회 (관리자 / UI 공통)
  // =====================================================
  public List<Reservation> getReservations() {
    return reservations;
  }

  public List<ReservableResource> getReservables() {
    return reservableResources;
  }

  public List<RentableResource> getRentables() {
    return rentableResources;
  }

  public List<Resource> getAllResources() {
    List<Resource> all = new ArrayList<>();
    all.addAll(reservableResources);
    all.addAll(rentableResources);
    return all;
  }


  // =====================================================
  //  자원 추가 (관리자)
  // =====================================================
  public boolean addResource(Resource r) {
    boolean exists = getAllResources().stream()
        .anyMatch(x -> x.getName().equals(r.getName()));

    if (exists) return false;

    if (r instanceof ReservableResource rr) reservableResources.add(rr);
    else if (r instanceof RentableResource ir) rentableResources.add(ir);

    notifyObservers(EventType.RESOURCE_ADDED);
    return true;
  }


  // =====================================================
  //  시설 예약 검색
  // =====================================================
  public Reservation findReservation(ReservableResource room, Date date, TimeSlot slot) {

    return reservations.stream()
        .filter(r -> r.getResource() instanceof ReservableResource)
        .filter(r -> r.getResource().equals(room))
        .filter(r -> sameDay(r.getStartDate(), date))
        .filter(r -> r.getTimeSlot() != null &&
            r.getTimeSlot().isOverlapping(slot))
        .findFirst()
        .orElse(null);
  }

  private boolean sameDay(Date a, Date b) {
    return a.toInstant().equals(b.toInstant());
  }


  // =====================================================
  //  시설 예약 생성
  // =====================================================
  public Reservation createLectureReservation(
      String userId, String userName,
      ReservableResource room,
      Date date,
      TimeSlot slot,
      String eventName
  ) {
    // 중복 예약 체크
    Reservation exists = findReservation(room, date, slot);
    if (exists != null) throw new RuntimeException("이미 예약된 시간입니다.");

    User u = new User(userId, userName);

    Reservation r = new Reservation(u, room, date, null, slot);
    r.setReservationId(UUID.randomUUID().toString());
    r.setEventName(eventName);
    reservations.add(r);

    notifyObservers(EventType.RESERVATION_CREATED);
    return exists;
  }


  // =====================================================
  //  물품 대여 생성
  // =====================================================
  public void createItemReservation(
      String userId, String userName,
      RentableResource item,
      Date start, Date end
  ) {
    User u = new User(userId, userName);

    if (!item.checkStock())
      throw new RuntimeException("재고가 없습니다.");

    item.rent(u, start); // 실제 구현은 boolean 반환하지만 여기선 그대로 사용

    Reservation r = new Reservation(u, item, start, end);
    r.setReservationId(UUID.randomUUID().toString());

    reservations.add(r);

    notifyObservers(EventType.ITEM_RENTED);
  }


  // =====================================================
  //  물품 반납
  // =====================================================
  public void returnItemReservation(Reservation r) {

    if (!(r.getResource() instanceof RentableResource item)) return;

    item.returnItem(r.getUser()); // 자원 반납
    r.markReturned();             // Reservation 상태 변경

    reservations.remove(r);       // 사용 완료 → 제거 (UI 설계 기준)

    notifyObservers(EventType.ITEM_RETURNED);
  }


  // =====================================================
  //  사용자 예약 조회
  // =====================================================
  public List<Reservation> getUserReservations(String userId) {
    return reservations.stream()
        .filter(r -> r.getUser().getStudentId().equals(userId))
        .collect(Collectors.toList());
  }

  public boolean hasTimeConflict(Resource resource, Date start, Date end) {
    for (Reservation r : reservations) {
      // 같은 자원인가?
      if (!r.getResource().getName().equals(resource.getName())) continue;

      // 시간 겹침 검사
      if (r.isOverlapping(start, end)) {
        return true;
      }
    }
    return false;
  }

  public boolean hasTimeConflict(Resource resource, Date date, TimeSlot slot) {
    for (Reservation r : reservations) {
      if (!r.getResource().getName().equals(resource.getName())) continue;

      if (r.getStartDate().equals(date) && r.getTimeSlot() != null) {
        if (r.getTimeSlot().isOverlapping(slot)) {
          return true;
        }
      }
    }
    return false;
  }
  public void loadResourcesFromDB() {
    RepositoryManager repo = RepositoryManager.getInstance();

    // 강의실 불러오기
    for (LectureEntity e : repo.lectures.findAll()) {
      this.addResource(new SimpleLectureRoom(e.getName(), e.getDeposit()));
    }

    // 대여 품목 불러오기
    for (ResourceEntity e : repo.resources.findAll()) {
      this.addResource(new SimpleItem(e.getName(), e.getDeposit()));
    }
  }

}
