package resource;

import java.util.Date;
import user.User;

public abstract class RentableDecorator implements RentableResource {
  protected RentableResource decorated;

  public RentableDecorator(RentableResource decorated) {
    this.decorated = decorated;
  }

  public String getName() { return decorated.getName(); }
  public int getDeposit() { return decorated.getDeposit(); }
  public boolean isAvailable() { return decorated.isAvailable(); }

  public boolean rent(User user, Date startDate) { return decorated.rent(user, startDate); }
  public void returnItem(User user) { decorated.returnItem(user); }
  public boolean checkStock() { return decorated.checkStock(); }
}
