package manager;

import java.util.HashMap;
import java.util.Map;
import user.User;

public class DepositManager {
  private Map<String, Integer> balances = new HashMap<>();

  public void payDeposit(User user, int amount) {}
  public void refundDeposit(User user, int amount) {}
  public int getBalance(User user) { return balances.getOrDefault(user.getStudentId(), 0); }
}
