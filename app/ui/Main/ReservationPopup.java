package ui.Main;

import reservation.Reservation;
import reservation.ReservationManager;
import resource.ReservableResource;
import resource.TimeSlot;
import ui.Main.LoginPanel;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ReservationPopup {

  private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");

  // ===============================
  //   시설 예약 팝업
  // ===============================
  public static void reserve(MainFrame frame, ReservationManager manager,
                             ReservableResource room, LocalDate localDate, String slotText) {

    String event = JOptionPane.showInputDialog(frame,
        "행사명을 입력하세요:",
        "예약 신청",
        JOptionPane.PLAIN_MESSAGE);

    if (event == null || event.isBlank()) return;

    // LocalDate → java.util.Date 변환
    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    // slot 문자열("09:00-11:00") → TimeSlot 객체 생성
    TimeSlot slot = new TimeSlot(slotText);

    // 실제 예약 생성
    Reservation r = manager.createLectureReservation(LoginPanel.currentUserId,
        LoginPanel.currentUserName,
        room,
        date,
        slot,
        event);
    JOptionPane.showMessageDialog(frame, "예약이 완료되었습니다!");
    frame.showPanel("ROOM_TIMELINE");
  }


  // ===============================
  //   예약 상세 보기 팝업
  // ===============================
  public static void detail(MainFrame frame, Reservation r) {

    String msg = """
                [예약 상세 정보]
                신청자: %s
                자원: %s
                행사명: %s
                날짜: %s
                시간대: %s
                """
        .formatted(
            r.getUser().getName(),
            r.getResource().getName(),
            (r.getEventName() != null ? r.getEventName() : "미입력"),
            DATE.format(r.getStartDate()),
            (r.getTimeSlot() != null ? r.getTimeSlot().toString() : "")
        );

    JOptionPane.showMessageDialog(frame, msg);
  }
}
