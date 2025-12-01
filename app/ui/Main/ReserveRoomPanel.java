package ui.Main;

import reservation.ReservationManager;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

  public class ReserveRoomPanel extends JPanel {

    public static int selectedRoomIndex;
    public static LocalDate selectedDate;

    public ReserveRoomPanel(MainFrame frame, ReservationManager manager) {

      setLayout(new BorderLayout());

      DefaultListModel<String> model = new DefaultListModel<>();
      JList<String> list = new JList<>(model);

      manager.getReservables().forEach(r ->
          model.addElement(r.getName() + " (보증금 " + r.getDeposit() + ")")
      );

      JTextField dateField = new JTextField(LocalDate.now().toString());
      JButton nextBtn = new JButton("7일 예약표 보기");
      JButton backBtn = new JButton("뒤로");

      JPanel south = new JPanel(new GridLayout(3,1));
      south.add(new JLabel("시작 날짜(yyyy-MM-dd):"));
      south.add(dateField);
      south.add(nextBtn);
      south.add(backBtn);

      add(new JScrollPane(list), BorderLayout.CENTER);
      add(south, BorderLayout.SOUTH);

      nextBtn.addActionListener(e -> {
        int idx = list.getSelectedIndex();
        if (idx < 0) return;

        selectedRoomIndex = idx;
        selectedDate = LocalDate.parse(dateField.getText());
        frame.showPanel("ROOM_TIMELINE");
      });

      backBtn.addActionListener(e -> frame.showPanel("MENU"));
    }
  }
