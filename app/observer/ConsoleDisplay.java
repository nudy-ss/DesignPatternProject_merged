package observer;

import java.util.List;
import reservation.Reservation;

public class ConsoleDisplay implements Observer {
  @Override
  public void update(Subject subject) {}
  public void showReservationAdded(Reservation res) {}
  public void showReservationList(List<Reservation> list) {}

  @Override
  public void update(Subject subject, EventType type) {

  }
}
