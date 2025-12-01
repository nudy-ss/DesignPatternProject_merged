package resource;

import java.util.Date;
import user.User;

public interface ReservableResource extends Resource {
  boolean reserve(Date date, TimeSlot slot, User user);
  void cancel(Date date, TimeSlot slot, User user);
  void showAvailability(Date date);

}
