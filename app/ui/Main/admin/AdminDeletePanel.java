package ui.Main.admin;

import ui.Main.MainFrame;
import reservation.ReservationManager;
import entity.LectureEntity;
import entity.ResourceEntity;
import Repository.RepositoryManager;

import javax.swing.*;
import java.awt.*;

public class AdminDeletePanel extends JPanel {

  public AdminDeletePanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout(10,10));

    JLabel title = new JLabel("[자원 비활성화]", SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    add(new JScrollPane(list), BorderLayout.CENTER);

    JButton disableBtn = new JButton("비활성화");
    JButton backBtn = new JButton("뒤로");

    JPanel bottom = new JPanel(new FlowLayout());
    bottom.add(disableBtn);
    bottom.add(backBtn);
    add(bottom, BorderLayout.SOUTH);

    // 화면 입장 시 DB 목록 로드
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {

        model.clear();
        RepositoryManager repo = RepositoryManager.getInstance();

        for (LectureEntity le : repo.lectures.findAll()) {
          model.addElement("[LECTURE] " + le.getName() + " (avail=" + le.isAvailable() + ")");
        }
        for (ResourceEntity re : repo.resources.findAll()) {
          model.addElement("[ITEM] " + re.getName());
        }
      }
    });

    disableBtn.addActionListener(e -> {

      int idx = list.getSelectedIndex();
      if (idx < 0) return;

      String selected = model.get(idx);

      RepositoryManager repo = RepositoryManager.getInstance();

      if (selected.startsWith("[LECTURE]")) {
        String name = selected.substring(10, selected.indexOf("(")).trim();

        LectureEntity le = repo.lectures.findByName(name);
        if (le != null) {
          le.setAvailable(false);
          repo.lectures.update(le);
        }
      }

      JOptionPane.showMessageDialog(frame, "비활성화 완료!");
      frame.showPanel("ADMIN_DELETE");
    });

    backBtn.addActionListener(e -> frame.showPanel("ADMIN"));
  }
}
