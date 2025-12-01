package ui.Main.admin;

import ui.Main.LoginPanel;
import ui.Main.MainFrame;
import reservation.ReservationManager;
import manager.ResourceType;

import javax.swing.*;
import java.awt.*;

public class AdminAddPanel extends JPanel {

  public AdminAddPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new GridLayout(6,1,10,10));
    setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

    add(new JLabel("[자원 등록]", SwingConstants.CENTER));

    JComboBox<String> typeBox = new JComboBox<>(new String[]{"LECTURE", "ITEM"});
    JTextField nameField = new JTextField();
    JTextField depositField = new JTextField();
    JButton addBtn = new JButton("등록하기");
    JButton backBtn = new JButton("뒤로");

    add(new JLabel("타입 선택:"));
    add(typeBox);
    add(new JLabel("자원 이름:"));
    add(nameField);
    add(new JLabel("보증금:"));
    add(depositField);
    add(addBtn);
    add(backBtn);

    // ===========================
    // 🔥 자원 등록 버튼 (DB 저장)
    // ===========================
    addBtn.addActionListener(e -> {

      String type = (String) typeBox.getSelectedItem();
      String name = nameField.getText().trim();
      String dep = depositField.getText().trim();

      if (name.isEmpty() || dep.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "값을 모두 입력하세요.");
        return;
      }

      int deposit;
      try {
        deposit = Integer.parseInt(dep);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "보증금은 숫자로 입력하세요.");
        return;
      }

      // 🔥 핵심: DB 기반 Admin 사용!
      boolean ok = LoginPanel.currentAdmin.registerResource(
          manager,
          ResourceType.valueOf(type),
          name,
          deposit
      );

      if (!ok) {
        JOptionPane.showMessageDialog(frame, "이미 존재하는 자원입니다.");
      } else {
        JOptionPane.showMessageDialog(frame, "DB 저장 + 메모리 등록 성공!");
        nameField.setText("");
        depositField.setText("");
      }
    });

    backBtn.addActionListener(e -> frame.showPanel("ADMIN"));
  }
}
