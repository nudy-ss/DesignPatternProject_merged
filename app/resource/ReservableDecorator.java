package resource;

import java.util.Date;
import user.User;

public abstract class ReservableDecorator implements ReservableResource {
  protected ReservableResource decorated;

  public ReservableDecorator(ReservableResource decorated) {
    this.decorated = decorated;
  }

  public String getName() { return decorated.getName(); }
  public int getDeposit() { return decorated.getDeposit(); }
  public boolean isAvailable() { return decorated.isAvailable(); }

  public boolean reserve(Date date, TimeSlot slot, User user) { return decorated.reserve(date, slot, user); }
  public void cancel(Date date, TimeSlot slot, User user) { decorated.cancel(date, slot, user); }
  public void showAvailability(Date date) { decorated.showAvailability(date); }
}
