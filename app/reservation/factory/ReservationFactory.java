package reservation.factory;

import Repository.RepositoryManager;
import com.mongodb.client.MongoCollection;
import entity.ReservationEntity;
import entity.ResourceEntity;
import manager.DepositManager;
import reservation.Reservation;
import reservation.ReservationManager;
import resource.Resource;
import resource.TimeSlot;
import user.User;
import observer.EventType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static entity.db.DBConnection.codecRegistry;
import static entity.db.DBConnection.database;

public abstract class ReservationFactory {

  protected final ReservationManager manager;
  protected final DepositManager depositManager; // null 가능
    protected RepositoryManager repositoryManager;

  private static final AtomicInteger SEQ = new AtomicInteger(1);
  private static final SimpleDateFormat ID_DF = new SimpleDateFormat("yyyyMMdd-HHmmss");

  public ReservationFactory(ReservationManager manager,RepositoryManager repositoryManager) {
    this.manager = manager;
    this.depositManager = null;
    this.repositoryManager = repositoryManager;
  }

  public ReservationFactory(ReservationManager manager, DepositManager depositManager) {
    if (manager == null) throw new IllegalArgumentException("ReservationManager is required");
    this.manager = manager;
    this.depositManager = depositManager;
  }

  /**
   * Template Method
   */
  public final Reservation create(User user,
                                  Resource resource,
                                  Date start,
                                  Date end) {

    validate(user, resource, start, end);
    TimeSlot slot = null;
    Reservation reservation = build(user, resource, start, end, slot); // slot 포함
    assignId(reservation);
    applyPolicies(reservation);
    save(reservation);
    notifyObservers(reservation);

      //몽고DB
      repositoryManager.reservations.save(new ReservationEntity(
              user,
              resource,
              start,
              end,
              slot,
              "R-" + ID_DF.format(new Date())
              + "-" + String.format("%03d", SEQ.getAndIncrement())
      ));


    return reservation;
  }

  /**
   * Factory Method – 하위 클래스가 구현
   */
  protected abstract Reservation build(User user,
                                       Resource resource,
                                       Date start,
                                       Date end,
                                       TimeSlot slot);

  /**
   * 입력 검증
   */
  protected void validate(User user, Resource resource, Date start, Date end) {
    if (user == null)      throw new IllegalArgumentException("user is null");
    if (resource == null)  throw new IllegalArgumentException("resource is null");
    if (start == null)     throw new IllegalArgumentException("start date is null");

    // end와 slot은 상황 따라 null 허용 가능
  }

  /**
   * 예약 ID 생성
   */
  protected void assignId(Reservation r) {
    if (r == null) return;

    String id = "R-" + ID_DF.format(new Date())
        + "-" + String.format("%03d", SEQ.getAndIncrement());

    r.setReservationId(id);  // Reservation에 setter가 있어야 함
  }

  protected abstract Reservation build(User user, Resource resource, Date start, Date end);

  /**
   * 정책 (보증금 등) – 필요 없으면 무시
   */
  protected void applyPolicies(Reservation r) {
    // depositManager 가 있으면 여기서 처리 가능
  }

  /**
   * 저장
   */
  protected void save(Reservation r) {
    manager.getReservations().add(r);
  }


  /**
   * 옵저버 알림
   */
  protected void notifyObservers(Reservation r) {
    manager.notifyObservers(EventType.RESERVATION_CREATED);
  }
}
