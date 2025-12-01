package ui.Main.admin;

import ui.Main.MainFrame;
import reservation.ReservationManager;
import entity.LectureEntity;
import entity.ResourceEntity;
import Repository.RepositoryManager;

import javax.swing.*;
import java.awt.*;

public class AdminListPanel extends JPanel {

  public AdminListPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout(10,10));
    setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

    JLabel title = new JLabel("[자원 목록 보기]", SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    JTextArea area = new JTextArea();
    area.setEditable(false);
    JScrollPane scroll = new JScrollPane(area);
    add(scroll, BorderLayout.CENTER);

    JButton back = new JButton("뒤로");
    add(back, BorderLayout.SOUTH);

    // 화면 나타날 때 자동 갱신
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {

        RepositoryManager repo = RepositoryManager.getInstance();
        StringBuilder sb = new StringBuilder();

        sb.append("=== 강의실 목록 ===\n");
        for (LectureEntity le : repo.lectures.findAll()) {
          sb.append("- ")
              .append(le.getName())
              .append(" / deposit=")
              .append(le.getDeposit())
              .append(" / available=")
              .append(le.isAvailable())
              .append("\n");
        }

        sb.append("\n=== 대여 품목 목록 ===\n");
        for (ResourceEntity re : repo.resources.findAll()) {
          sb.append("- ")
              .append(re.getName())
              .append(" / deposit=")
              .append(re.getDeposit())
              .append("\n");
        }

        area.setText(sb.toString());
      }
    });

    back.addActionListener(e -> frame.showPanel("ADMIN"));
  }
}
