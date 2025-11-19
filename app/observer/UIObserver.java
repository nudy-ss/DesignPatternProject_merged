package observer;

import javax.swing.*;

public class UIObserver implements Observer {

  private final JFrame frame; // 필요시 패널로 교체

  public UIObserver(JFrame frame) {
    this.frame = frame;
  }

  @Override
  public void update(Subject subject) {

  }

  @Override
  public void update(Subject subject, EventType type) {
    // Swing UI 갱신 로직
    frame.repaint();
  }
}

