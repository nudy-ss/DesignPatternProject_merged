package main;

import reservation.ReservationManager;
import ui.LoginFrame;

import javax.swing.*;

public class SwingMain {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      var manager = new ReservationManager();
      new LoginFrame(manager).setVisible(true);
    });
  }
}
