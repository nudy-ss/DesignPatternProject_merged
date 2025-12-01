package ui.Main;

import Repository.RepositoryManager;
import entity.ResourceEntity;
import reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReserveRoomPanel extends JPanel {

  public static String selectedRoomName;   // 강의실 이름
  public static LocalDate selectedDate;

  public ReserveRoomPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    // ==========================
    // 🔥 DB에서 강의실(LECTURE) 목록 불러오기
    // ==========================
    RepositoryManager repo = RepositoryManager.getInstance();
    List<ResourceEntity> rooms = repo.resources.findByType("LECTURE");

    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);

    for (ResourceEntity r : rooms) {
      model.addElement("%s (보증금 %d원)"
          .formatted(r.getName(), r.getDeposit()));
    }

    add(new JScrollPane(list), BorderLayout.CENTER);


    // ==========================
    // 날짜 입력
    // ==========================
    JTextField dateField = new JTextField(LocalDate.now().toString());
    JButton nextBtn = new JButton("7일 예약표 보기");
    JButton backBtn = new JButton("뒤로");

    JPanel south = new JPanel(new GridLayout(3,1));
    south.add(new JLabel("시작 날짜(yyyy-MM-dd):"));
    south.add(dateField);
    south.add(nextBtn);
    south.add(backBtn);

    add(south, BorderLayout.SOUTH);


    // ==========================
    // 7일 예약표 보기 버튼
    // ==========================
    nextBtn.addActionListener(e -> {
      int idx = list.getSelectedIndex();
      if (idx < 0) {
        JOptionPane.showMessageDialog(frame, "강의실을 선택하세요.");
        return;
      }

      selectedRoomName = rooms.get(idx).getName();  // 강의실 이름 저장
      selectedDate = LocalDate.parse(dateField.getText());

      frame.showPanel("ROOM_TIMELINE");
    });

    backBtn.addActionListener(e -> frame.showPanel("MENU"));
  }
}
