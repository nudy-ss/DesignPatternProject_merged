package resource;

import java.util.Date;
import user.User;

public class SimpleLectureRoom implements ReservableResource {
  private final String name;
  private int deposit;
  private boolean available = true;

  public SimpleLectureRoom(String name, int deposit) {
    this.name = name;
    this.deposit = deposit;
  }

  @Override public boolean reserve(Date date, TimeSlot slot, User user) { return available; }
  @Override public void cancel(Date date, TimeSlot slot, User user) {}
  @Override public void showAvailability(Date date) {}

  @Override public String getName() { return name; }
  @Override public int getDeposit() { return deposit; }
  @Override
  public void setDeposit(int deposit) {
    this.deposit = deposit;
  }
  @Override public boolean isAvailable() { return available; }
  public void setAvailable(boolean available) {
    this.available = available;
  }

}
