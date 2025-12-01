package resource;

public interface Resource {
  String getName();
  int getDeposit();
  boolean isAvailable();
  void setAvailable(boolean available);
  void setDeposit(int deposit);
}
