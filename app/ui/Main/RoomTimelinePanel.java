package ui.Main;

import Repository.RepositoryManager;
import entity.ReservationEntity;
import reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RoomTimelinePanel extends JPanel {

  private final String[] timeSlots = {"09:00-11:00", "11:00-13:00", "13:00-15:00"};

  public RoomTimelinePanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    JLabel title = new JLabel("7일 예약 현황", SwingConstants.CENTER);
    add(title, BorderLayout.NORTH);

    JTable table = new JTable(3, 7);
    table.setRowHeight(40);

    add(new JScrollPane(table), BorderLayout.CENTER);

    JButton backBtn = new JButton("뒤로");
    add(backBtn, BorderLayout.SOUTH);


    // ============================================
    //   화면 열릴 때 데이터 로드
    // ============================================
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {

        RepositoryManager repo = RepositoryManager.getInstance();
        String roomName = ReserveRoomPanel.selectedRoomName;
        LocalDate startDate = ReserveRoomPanel.selectedDate;

        // 날짜 헤더
        for (int col = 0; col < 7; col++) {
          LocalDate d = startDate.plusDays(col);
          table.getColumnModel().getColumn(col).setHeaderValue(d.toString());
        }

        // DB 전체 예약
        List<ReservationEntity> reservations = repo.reservations.findAll();

        // 표 채우기
        for (int row = 0; row < 3; row++) {
          for (int col = 0; col < 7; col++) {

            LocalDate ld = startDate.plusDays(col);
            Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String slot = timeSlots[row];

            // DB에서 예약된 칸 찾기
            boolean reserved = reservations.stream()
                .anyMatch(r ->
                    r.getResourceType().equals("LECTURE") &&
                        r.getResourceName().equals(roomName) &&
                        sameDate(r.getStartDate(), date) &&
                        slot.equals(r.getTimeSlot())
                );

            table.setValueAt(reserved ? "■" : "□", row, col);
          }
        }

        table.getTableHeader().repaint();
      }
    });


    // ============================================
    //   칸 클릭 이벤트
    // ============================================
    table.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {

        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        if (row < 0 || col < 0) return;

        RepositoryManager repo = RepositoryManager.getInstance();
        String roomName = ReserveRoomPanel.selectedRoomName;

        LocalDate ld = ReserveRoomPanel.selectedDate.plusDays(col);
        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String slot = timeSlots[row];

        // DB에서 예약 검색
        ReservationEntity found =
            repo.reservations.findAll().stream()
                .filter(r -> r.getResourceType().equals("LECTURE"))
                .filter(r -> r.getResourceName().equals(roomName))
                .filter(r -> sameDate(r.getStartDate(), date))
                .filter(r -> slot.equals(r.getTimeSlot()))
                .findFirst()
                .orElse(null);

        if (found == null) {
          // 빈 칸 → 예약 새로 만들기
          ReservationPopup.reserve(frame, roomName, ld, slot);
        } else {
          // 이미 예약 → 상세 팝업
          ReservationPopup.detail(frame, found);
        }
      }
    });

    backBtn.addActionListener(e -> frame.showPanel("RESERVE_ROOM"));
  }


  // =======================
  // 날짜 비교 유틸
  // =======================
  private boolean sameDate(Date a, Date b) {
    return a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .equals(b.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
  }
}
