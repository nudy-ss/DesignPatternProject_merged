package resource;

import java.util.Date;
import user.User;

public interface RentableResource extends Resource {
  boolean rent(User user, Date startDate);
  void returnItem(User user);
  boolean checkStock();
}
