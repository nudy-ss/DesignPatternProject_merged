package ui.Main;

import Repository.RepositoryManager;
import entity.ReservationEntity;
import reservation.ReservationManager;
import resource.ReservableResource;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ReservationPopup {

  private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");

  // ===============================
  //   시설 예약 팝업 (DB 저장)
  // ===============================
  public static void reserve(MainFrame frame,
                             String roomName,       // 강의실 이름
                             LocalDate localDate,
                             String slotText) {      // "09:00~11:00"

    String event = JOptionPane.showInputDialog(
        frame,
        "행사명을 입력하세요:",
        "예약 신청",
        JOptionPane.PLAIN_MESSAGE
    );

    if (event == null || event.isBlank()) return;

    // LocalDate → Date 변환
    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    // ===============================
    // 🔥 ReservationEntity 생성 + DB 저장
    // ===============================
    ReservationEntity r = new ReservationEntity(
        LoginPanel.currentUserId,
        LoginPanel.currentUserName,
        roomName,
        "LECTURE",
        date,
        date,            // endDate = same day
        slotText,
        event
    );

    RepositoryManager repo = RepositoryManager.getInstance();
    repo.reservations.save(r);

    JOptionPane.showMessageDialog(frame, "예약이 완료되었습니다!");
    frame.showPanel("ROOM_TIMELINE");
  }


  // ===============================
  //   예약 상세 팝업 (DB 조회 기반)
  // ===============================
  public static void detail(MainFrame frame, ReservationEntity r) {

    String msg = """
                [예약 상세 정보]
                신청자: %s
                자원: %s
                행사명: %s
                날짜: %s
                시간대: %s
                """
        .formatted(
            r.getUserId(),
            r.getResourceName(),
            r.getEventName() == null ? "미입력" : r.getEventName(),
            DATE.format(r.getStartDate()),
            r.getTimeSlot()
        );

    JOptionPane.showMessageDialog(frame, msg);
  }

  public static void reserve(MainFrame frame, ReservationManager manager, ReservableResource room, LocalDate date, String slot) {
  }
}
