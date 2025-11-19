package observer;

public interface Observer {
  void update(Subject subject);

  void update(Subject subject, EventType type);
}
