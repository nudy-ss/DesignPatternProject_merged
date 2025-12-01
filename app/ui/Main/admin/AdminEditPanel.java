package ui.Main.admin;

import ui.Main.MainFrame;
import reservation.ReservationManager;
import resource.RentableResource;

import javax.swing.*;
import java.awt.*;

public class AdminEditPanel extends JPanel {

  public AdminEditPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    JLabel title = new JLabel("[자원 속성 관리]", SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    add(new JScrollPane(list), BorderLayout.CENTER);

    JPanel bottom = new JPanel(new GridLayout(5,1,10,10));
    JTextField depositField = new JTextField();
    JTextField rentalDaysField = new JTextField();
    JButton saveBtn = new JButton("수정 저장");
    JButton backBtn = new JButton("뒤로");

    bottom.add(new JLabel("보증금:"));
    bottom.add(depositField);
    bottom.add(new JLabel("대여기간(물품만):"));
    bottom.add(rentalDaysField);
    bottom.add(saveBtn);
    bottom.add(backBtn);

    add(bottom, BorderLayout.SOUTH);

    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {
        model.clear();
        manager.getAllResources().forEach(r ->
            model.addElement("%s (%s) | 보증금 %d"
                .formatted(r.getName(), r.getClass().getSimpleName(), r.getDeposit()))
        );
      }
    });

    saveBtn.addActionListener(e -> {
      int idx = list.getSelectedIndex();
      if (idx < 0) return;

      var res = manager.getAllResources().get(idx);

      try {
        int dep = Integer.parseInt(depositField.getText().trim());
        res.setDeposit(dep);   // 정상

        if (res instanceof RentableResource rental) {
          int days = Integer.parseInt(rentalDaysField.getText().trim());
          rental.setRentalPeriod(days + "일");   // ← 여기로 수정!!!
        }

        JOptionPane.showMessageDialog(frame, "수정 완료!");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "값을 다시 확인하세요.");
      }
    });

    backBtn.addActionListener(e -> frame.showPanel("ADMIN"));
  }
}
