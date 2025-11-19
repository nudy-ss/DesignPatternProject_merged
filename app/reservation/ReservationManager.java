package reservation;

import manager.ResourceFactory;

import observer.Subject;
import observer.EventType;

import reservation.factory.LectureReservationFactory;
import resource.ReservableResource;
import resource.RentableResource;
import resource.Resource;

import resource.SimpleItem;
import resource.SimpleLectureRoom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationManager extends Subject {

  private final List<Reservation> reservations = new ArrayList<>();
  private final List<ReservableResource> reservableResources = new ArrayList<>();
  private final List<RentableResource> rentableResources = new ArrayList<>();


  // ======================== 자원 등록 ========================

  /** 문자열 기반 등록 (ResourceBatchLoader에서 호출됨) */
  public void registerResource(String typeStr, String name, int deposit) {

    Resource resource;

    switch (typeStr.toUpperCase()) {
      case "LECTURE":
        resource = new SimpleLectureRoom(name, deposit);
        break;

      case "ITEM":
        resource = new SimpleItem(name, deposit);
        break;

      default:
        throw new IllegalArgumentException("Unknown resource type: " + typeStr);
    }

    if (resource instanceof ReservableResource reservable) {
      addReservableResource(reservable);
    } else if (resource instanceof RentableResource rentable) {
      addRentableResource(rentable);
    }

    System.out.println("[자원 등록 완료] " + resource.getName());
    notifyObservers(EventType.RESOURCE_ADDED);
  }


  /** Factory 기반 등록 (UI나 코드 내부에서 직접 호출 시 사용) */
  public void registerResource(ResourceFactory factory, String name, int deposit) {

    Resource resource = factory.createResource(name, deposit);

    if (resource instanceof ReservableResource reservable) {
      addReservableResource(reservable);
    } else if (resource instanceof RentableResource rentable) {
      addRentableResource(rentable);
    }

    System.out.println("[자원 등록 완료] " + resource.getName());
    notifyObservers(EventType.RESOURCE_ADDED);
  }


  public void addReservableResource(ReservableResource r) {
    reservableResources.add(r);
  }

  public void addRentableResource(RentableResource r) {
    rentableResources.add(r);
  }

  public List<ReservableResource> getReservables() {
    return reservableResources;
  }

  public List<RentableResource> getRentables() {
    return rentableResources;
  }


  // ======================== 예약 관리 ========================

  public void addReservation(Reservation res) {
    reservations.add(res);
    notifyObservers(EventType.RESERVATION_CREATED);
  }

  public List<Reservation> getReservationsByUser(String userId) {
    List<Reservation> list = new ArrayList<>();
    for (Reservation r : reservations) {
      if (r.getUser().getStudentId().equals(userId)) {
        list.add(r);
      }
    }
    return list;
  }


  public boolean hasTimeConflict(Resource resource, Date start, Date end) {
    for (Reservation r : reservations) {
      if (!r.getResource().equals(resource)) continue;
      if (r.isOverlapping(start, end)) return true;
    }
    return false;
  }


  // ======================== 출력 ========================

  public void showAllReservations() {
    System.out.println("=== 전체 예약 목록 ===");
    if (reservations.isEmpty()) {
      System.out.println("(예약 없음)");
      return;
    }
    for (Reservation r : reservations) {
      System.out.println(r.getDetails());
    }
  }


  // ======================== 기타 ========================

  public List<Reservation> getAllReservations() {
    return reservations;
  }
}
