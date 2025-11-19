package observer;

public class AdminNotifier implements Observer {
  @Override
  public void update(Subject subject) {

  }

  @Override
  public void update(Subject subject, EventType type) {
    System.out.println("[ADMIN NOTIFY] event: " + type);
  }
}
