package ui.Main.admin;

import ui.Main.MainFrame;
import reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;

public class AdminListPanel extends JPanel {

  public AdminListPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    JLabel title = new JLabel("[자원 현황 조회]", SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    JTextArea area = new JTextArea();
    area.setEditable(false);
    add(new JScrollPane(area), BorderLayout.CENTER);

    JButton back = new JButton("뒤로");
    add(back, BorderLayout.SOUTH);

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {

        StringBuilder sb = new StringBuilder();

        sb.append("=== 시설 자원 ===\n");
        manager.getReservables().forEach(r ->
            sb.append("- %s | 보증금 %d | 활성화=%s\n"
                .formatted(r.getName(), r.getDeposit(), r.isAvailable()))
        );

        sb.append("\n=== 대여 물품 ===\n");
        manager.getRentables().forEach(r ->
            sb.append("- %s | 보증금 %d | 기간 %d일 | 활성화=%s\n"
                .formatted(r.getName(), r.getDeposit(),
                    r.getRentalPeriod(), r.isAvailable()))
        );

        area.setText(sb.toString());
      }
    });

    back.addActionListener(e -> frame.showPanel("ADMIN"));
  }
}
