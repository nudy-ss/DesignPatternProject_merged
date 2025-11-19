package resource;

import java.util.Date;
import user.User;

public class SimpleItem implements RentableResource {
  private final String name;
  private final int deposit;
  private boolean inStock = true;

  public SimpleItem(String name, int deposit) {
    this.name = name;
    this.deposit = deposit;
  }

  @Override public boolean rent(User user, Date startDate) { return inStock; }
  @Override public void returnItem(User user) {}
  @Override public boolean checkStock() { return inStock; }

  @Override public String getName() { return name; }
  @Override public int getDeposit() { return deposit; }
  @Override public boolean isAvailable() { return inStock; }
}
