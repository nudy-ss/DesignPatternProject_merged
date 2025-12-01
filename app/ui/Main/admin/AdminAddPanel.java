package ui.Main.admin;

import ui.Main.LoginPanel;
import ui.Main.MainFrame;
import reservation.ReservationManager;
import manager.ResourceType;

import javax.swing.*;
import java.awt.*;

public class AdminAddPanel extends JPanel {

  public AdminAddPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

    JLabel title = new JLabel("[자원 등록]");
    title.setFont(new Font("Dialog", Font.BOLD, 20));
    title.setAlignmentX(CENTER_ALIGNMENT);

    add(title);
    add(Box.createVerticalStrut(15));

    JComboBox<String> typeBox = new JComboBox<>(new String[]{"LECTURE", "ITEM"});
    JTextField nameField = new JTextField(15);
    JTextField depositField = new JTextField(15);

    add(makeRow("타입 선택:", typeBox));
    add(makeRow("자원 이름:", nameField));
    add(makeRow("보증금:", depositField));
    add(Box.createVerticalStrut(15));

    JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    JButton addBtn = new JButton("등록하기");
    JButton backBtn = new JButton("뒤로");
    addBtn.setPreferredSize(new Dimension(120, 35));
    backBtn.setPreferredSize(new Dimension(120, 35));

    btnRow.add(addBtn);
    btnRow.add(backBtn);
    add(btnRow);

    // 등록 버튼
    addBtn.addActionListener(e -> {
      String type = (String) typeBox.getSelectedItem();
      String name = nameField.getText().trim();
      String dep = depositField.getText().trim();

      if (name.isEmpty() || dep.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "값을 모두 입력하세요.");
        return;
      }

      int deposit;
      try { deposit = Integer.parseInt(dep); }
      catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "보증금은 숫자로 입력하세요.");
        return;
      }

      boolean ok = LoginPanel.currentAdmin.registerResource(
          manager,
          ResourceType.valueOf(type),
          name,
          deposit
      );

      if (!ok) JOptionPane.showMessageDialog(frame, "이미 존재하는 자원입니다.");
      else {
        JOptionPane.showMessageDialog(frame, "등록 성공!");
        nameField.setText("");
        depositField.setText("");
      }
    });

    // 뒤로가기
    backBtn.addActionListener(e -> frame.showPanel("ADMIN"));
  }

  /** 레이블 + 입력칸을 나란히 배치하는 UI 헬퍼 */
  private JPanel makeRow(String label, JComponent comp) {
    JPanel row = new JPanel(new BorderLayout(10, 10));
    row.add(new JLabel(label), BorderLayout.WEST);
    row.add(comp, BorderLayout.CENTER);
    row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
    return row;
  }
}
