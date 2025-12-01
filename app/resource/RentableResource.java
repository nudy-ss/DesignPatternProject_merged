package resource;

import java.util.Date;
import user.User;

public interface RentableResource extends Resource {
  boolean isAvailable(Date date);
  boolean canRent(User user, Date start);
  boolean rent(User user, Date start);

  boolean checkStock();

  int getRentalPeriod();

  void setRentalPeriod(int period);
  void returnItem(User user);

  void setRentalPeriod(String period);
}

