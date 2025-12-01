package ui.Main.admin;

import reservation.ReservationManager;
import ui.Main.MainFrame;

import javax.swing.*;
import java.awt.*;

public class AdminDeletePanel extends JPanel {

  public AdminDeletePanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    JLabel title = new JLabel("[자원 삭제/비활성화]", SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    JButton delBtn = new JButton("비활성화");
    JButton backBtn = new JButton("뒤로");

    add(new JScrollPane(list), BorderLayout.CENTER);

    JPanel bottom = new JPanel(new FlowLayout());
    bottom.add(delBtn);
    bottom.add(backBtn);
    add(bottom, BorderLayout.SOUTH);

    // 화면 열릴 때 목록 로드
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {
        model.clear();
        manager.getAllResources().forEach(r -> {
          model.addElement(
              "%s (%s) | 보증금 %d | 활성화=%s"
                  .formatted(r.getName(), r.getClass().getSimpleName(), r.getDeposit(), r.isAvailable())
          );
        });
      }
    });

    delBtn.addActionListener(e -> {
      int idx = list.getSelectedIndex();
      if (idx < 0) return;

      var res = manager.getAllResources().get(idx);
      res.setAvailable(false);

      JOptionPane.showMessageDialog(frame, "비활성화되었습니다.");
      frame.showPanel("ADMIN_DELETE");
    });

    backBtn.addActionListener(e -> frame.showPanel("ADMIN"));
  }
}