package ui.Main.admin;

import ui.Main.MainFrame;
import reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;

public class AdminPanelMain extends JPanel {

  public AdminPanelMain(MainFrame frame, ReservationManager manager) {

    setLayout(new GridLayout(6,1,15,15));

    add(new JLabel("=== 관리자 메뉴 ===", SwingConstants.CENTER));

    JButton b1 = new JButton("1. 자원 등록");
    JButton b2 = new JButton("2. 자원 삭제 (비활성화)");
    JButton b3 = new JButton("3. 자원 현황 조회");
    JButton b4 = new JButton("4. 자원 속성 관리");
    JButton b5 = new JButton("5. 메인 화면으로");

    b1.addActionListener(e -> frame.showPanel("ADMIN_ADD"));
    b2.addActionListener(e -> frame.showPanel("ADMIN_DELETE"));
    b3.addActionListener(e -> frame.showPanel("ADMIN_LIST"));
    b4.addActionListener(e -> frame.showPanel("ADMIN_EDIT"));
    b5.addActionListener(e -> frame.showPanel("LOGIN"));

    add(b1); add(b2); add(b3); add(b4); add(b5);
  }
}
