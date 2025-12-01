package ui.Main;

import reservation.ReservationManager;
import reservation.Reservation;
import resource.ReservableResource;
import resource.TimeSlot;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RoomTimelinePanel extends JPanel {

  private String[] timeSlots = {"09:00-11:00", "11:00-13:00", "13:00-15:00"}; // ← 네 TimeSlot 형식

  public RoomTimelinePanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    JLabel title = new JLabel("7일 예약 현황", SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    JTable table = new JTable(3, 7);
    table.setRowHeight(40);

    JButton backBtn = new JButton("뒤로");
    add(backBtn, BorderLayout.SOUTH);

    add(new JScrollPane(table), BorderLayout.CENTER);

    // 화면이 열릴 때 데이터 로드
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {

        LocalDate start = ReserveRoomPanel.selectedDate;
        int roomIdx = ReserveRoomPanel.selectedRoomIndex;
        ReservableResource room = manager.getReservables().get(roomIdx);

        // 날짜 헤더 채우기
        for (int col = 0; col < 7; col++) {
          LocalDate d = start.plusDays(col);
          table.getColumnModel().getColumn(col).setHeaderValue(d.toString());
        }

        // 예약 여부 채우기
        for (int row = 0; row < 3; row++) {
          for (int col = 0; col < 7; col++) {

            LocalDate d = start.plusDays(col);

            // LocalDate → Date
            Date realDate = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // 시간대 String → TimeSlot
            String slotText = timeSlots[row];
            TimeSlot realSlot = new TimeSlot(slotText);

            Reservation r = manager.findReservation(room, realDate, realSlot);

            table.setValueAt(r == null ? "□" : "■", row, col);
          }
        }

        table.getTableHeader().repaint();
      }
    });

    // 클릭 이벤트
    table.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();

        LocalDate date = ReserveRoomPanel.selectedDate.plusDays(col);
        String slot = timeSlots[row];

        int roomIdx = ReserveRoomPanel.selectedRoomIndex;
        ReservableResource room = manager.getReservables().get(roomIdx);

        // detail/reserve로 넘길 때는 LocalDate + String 그대로 넘기고
        // 내부에서 ReservationPopup에서 Date/TimeSlot으로 변환 처리함
        Reservation r = manager.findReservation(
            room,
            Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
            new TimeSlot(slot)
        );

        if (r == null) {
          ReservationPopup.reserve(frame, manager, room, date, slot);
        } else {
          ReservationPopup.detail(frame, r);
        }
      }
    });

    backBtn.addActionListener(e -> frame.showPanel("RESERVE_ROOM"));
  }
}
