package ui.Main;

import reservation.ReservationManager;
import ui.Main.MainFrame;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

  public MenuPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new GridLayout(6, 1, 10, 10));

    add(new JLabel("[메인 메뉴]", SwingConstants.CENTER));

    JButton b1 = new JButton("1. 시설 대여");
    JButton b2 = new JButton("2. 시설물 대여");
    JButton b3 = new JButton("3. 내 예약 현황 조회");
    JButton exit = new JButton("4. 종료");

    b1.addActionListener(e -> frame.show("RESERVE_ROOM"));
    b2.addActionListener(e -> frame.show("RENT_ITEM"));
    b3.addActionListener(e -> frame.show("MY_RES"));
    exit.addActionListener(e -> System.exit(0));

    add(b1);
    add(b2);
    add(b3);
    add(exit);
  }
}
