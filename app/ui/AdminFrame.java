package ui;

import entity.ResourceEntity;
import entity.db.DBConnection;
import manager.ResourceType;
import reservation.ReservationManager;
import resource.RentableResource;
import resource.ReservableResource;
import user.Admin;
import manager.ResourceFileReader;

import javax.swing.*;
import java.awt.*;

import static entity.db.DBConnection.codecRegistry;
/*
public class AdminFrame extends JFrame {
  private final ReservationManager manager;
  private final Admin admin;
  private final JTextArea output = new JTextArea(12, 40);

  public AdminFrame(ReservationManager manager, Admin admin) {
    this.manager = manager;
    this.admin = admin;

    setTitle("관리자 모드");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(560, 420);
    setLocationRelativeTo(null);

    // 입력 영역
    var typeBox = new JComboBox<>(new String[]{"LECTURE", "ITEM"});
    var nameField = new JTextField();
    var depositField = new JTextField();
    var addBtn = new JButton("자원 등록");
    var listBtn = new JButton("자원 목록 보기");

    var form = new JPanel(new GridLayout(4, 2, 8, 8));
    form.setBorder(BorderFactory.createTitledBorder("자원 등록"));
    form.add(new JLabel("타입:"));
    form.add(typeBox);
    form.add(new JLabel("이름:"));
    form.add(nameField);
    form.add(new JLabel("보증금:"));
    form.add(depositField);
    form.add(new JLabel());
    form.add(addBtn);

    // 출력 영역
    output.setEditable(false);
    var scroll = new JScrollPane(output);

    var top = new JPanel(new BorderLayout(8, 8));
    top.add(form, BorderLayout.CENTER);
    top.add(listBtn, BorderLayout.SOUTH);

    var root = new JPanel(new BorderLayout(8, 8));
    root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
    root.add(top, BorderLayout.NORTH);
    root.add(scroll, BorderLayout.CENTER);
    setContentPane(root);
    /*
    var loadBtn = new JButton("파일에서 불러오기");
    top.add(loadBtn, BorderLayout.EAST);
    loadBtn.addActionListener(e -> {
      var loaded = ResourceFileReader.loadResources();
      int count = 0;

      for (Object obj : loaded) {
        if (obj instanceof ReservableResource r) {
          manager.addReservableResource(r);
          count++;
        } else if (obj instanceof RentableResource r) {
          manager.addRentableResource(r);
          count++;
        }
      }

      JOptionPane.showMessageDialog(this, count + "개의 자원을 불러왔습니다!");
    });
*/

 /*     // ✅ 이벤트 등록
    addBtn.addActionListener(e -> {
      String type = (String) typeBox.getSelectedItem();
      String name = nameField.getText().trim();
      String dep = depositField.getText().trim();

      if (name.isEmpty() || dep.isEmpty()) {
        JOptionPane.showMessageDialog(this, "이름/보증금을 입력하세요.");
        return;
      }

      int deposit;
      try {
        deposit = Integer.parseInt(dep);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "보증금은 정수로 입력하세요.");
        return;
      }

      // ✅ 팩토리 + 파일 저장 + 중복 체크 적용 부분
        //무조건 true로 만들어 놓음 지금
      boolean ok = admin.registerResource(manager, ResourceType.valueOf(type), name, deposit);

      if (!ok) {
        JOptionPane.showMessageDialog(this, "이미 존재하는 이름입니다!", "등록 실패", JOptionPane.ERROR_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(this, "등록 성공!");
        output.append("[등록] %s, %s, %d\n".formatted(type, name, deposit));
        nameField.setText("");
        depositField.setText("");
      }
    });

    listBtn.addActionListener(e -> {
      var sb = new StringBuilder();
      sb.append("== 예약형 자원 ==\n");
      manager.getReservables().forEach(r ->
          sb.append("- %s (deposit=%d, avail=%s)\n".formatted(r.getName(), r.getDeposit(), r.isAvailable())));

      sb.append("\n== 대여형 자원 ==\n");
      manager.getRentables().forEach(r ->
          sb.append("- %s (deposit=%d, avail=%s)\n".formatted(r.getName(), r.getDeposit(), r.isAvailable())));

      output.setText(sb.toString());
    });
  }
}
*/
