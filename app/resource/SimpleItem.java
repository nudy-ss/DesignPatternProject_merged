package resource;

import java.util.Date;
import user.User;

public class SimpleItem implements RentableResource {
  private final String name;
  private int deposit;
  private boolean inStock = true;
  private boolean available = true;


  public SimpleItem(String name, int deposit) {
    this.name = name;
    this.deposit = deposit;
  }

  @Override
  public boolean isAvailable(Date date) { return false; }

  @Override
  public boolean canRent(User user, Date start) { return false; }

  @Override public boolean rent(User user, Date startDate) { return inStock; }
  @Override public void returnItem(User user) {}
  @Override public boolean checkStock() { return inStock; }

  private int rentalPeriod;
  @Override
  public int getRentalPeriod() { return rentalPeriod; }

  @Override
  public void setRentalPeriod(int period) {
    this.rentalPeriod = period;
  }

  @Override
  public void setRentalPeriod(String period) { this.rentalPeriod = Integer.parseInt(period); }


  @Override public String getName() { return name; }
  @Override public int getDeposit() { return deposit; }
  @Override
  public void setDeposit(int deposit) { this.deposit = deposit; }
  @Override public boolean isAvailable() { return inStock; }

  @Override
  public void setAvailable(boolean available) {
    this.available = available;
  }

}
