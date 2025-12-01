package ui.Main;

import reservation.ReservationManager;
import resource.RentableResource;
import ui.Main.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RentItemPanel extends JPanel {

  private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm");
  private static final SimpleDateFormat DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public RentItemPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    // =========================
    // 1) 물품 목록
    // =========================
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);

    for (RentableResource r : manager.getRentables()) {
      model.addElement(
          "%s (대여기간 %s, 보증금 %d원)"
              .formatted(r.getName(), r.getRentalPeriod(), r.getDeposit())
      );
    }

    add(new JScrollPane(list), BorderLayout.CENTER);


    // =========================
    // 2) 날짜/시간 입력
    // =========================
    JPanel bottom = new JPanel(new GridLayout(5,1,10,10));
    bottom.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    JTextField dateField = new JTextField(DATE.format(new Date()));
    JTextField timeField = new JTextField("14:00");

    JButton rentBtn = new JButton("대여하기");
    JButton backBtn = new JButton("뒤로");

    bottom.add(new JLabel("대여 시작 날짜 (yyyy-MM-dd):"));
    bottom.add(dateField);

    bottom.add(new JLabel("대여 시작 시각 (HH:mm):"));
    bottom.add(timeField);

    bottom.add(rentBtn);
    bottom.add(backBtn);

    add(bottom, BorderLayout.SOUTH);


    // =========================
    // 3) 대여하기 버튼
    // =========================
    rentBtn.addActionListener(e -> {

      int idx = list.getSelectedIndex();
      if (idx < 0) {
        JOptionPane.showMessageDialog(frame, "물품을 선택하세요.");
        return;
      }

      RentableResource item = manager.getRentables().get(idx);

      // 날짜 + 시간 조합
      String dateText = dateField.getText().trim();
      String timeText = timeField.getText().trim();

      Date start;
      try {
        start = DATETIME.parse(dateText + " " + timeText);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "날짜/시간 형식이 올바르지 않습니다.");
        return;
      }

      // 대여기간 (예: "3일") -> 숫자만 추출
      String periodStr = item.getRentalPeriod();  // "3일"
      int days = extractDays(periodStr);

      // start + days 계산
      Calendar cal = Calendar.getInstance();
      cal.setTime(start);
      cal.add(Calendar.DATE, days);
      Date expectedReturn = cal.getTime();

      // 실제 예약 생성
      manager.createItemReservation(
          LoginPanel.currentUserId,
          LoginPanel.currentUserName,
          item,
          start,
          expectedReturn
      );

      // 팝업으로 정보 표시
      JOptionPane.showMessageDialog(frame,
          """
          [대여 완료]
          물품: %s
          대여 시작: %s
          반납 예정: %s
          보증금: %d원
          """
              .formatted(
                  item.getName(),
                  DATETIME.format(start),
                  DATETIME.format(expectedReturn),
                  item.getDeposit()
              ));

      frame.showPanel("MENU");
    });


    // =========================
    // 4) 뒤로가기
    // =========================
    backBtn.addActionListener(e -> frame.showPanel("MENU"));
  }


  // "3일" → 3 숫자 추출
  private int extractDays(String period) {
    try {
      return Integer.parseInt(period.replaceAll("[^0-9]", ""));
    } catch (Exception e) {
      return 1;
    }
  }
}
