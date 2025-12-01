package main;

import reservation.ReservationManager;
import ui.Main.MainFrame;

import javax.swing.*;

public class SwingMain {
  public static void main(String[] args) {
    ReservationManager manager = new ReservationManager();
    manager.loadResourcesFromDB();
    SwingUtilities.invokeLater(() -> new MainFrame(manager).setVisible(true));
  }
}
