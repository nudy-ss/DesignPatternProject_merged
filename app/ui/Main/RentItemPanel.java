package ui.Main;

import Repository.RepositoryManager;
import entity.ReservationEntity;
import entity.ResourceEntity;
import reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RentItemPanel extends JPanel {

  private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm");
  private static final SimpleDateFormat DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public RentItemPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    // =========================
    // 1) DB에서 물품 목록 불러오기
    // =========================
    RepositoryManager repo = RepositoryManager.getInstance();
    List<ResourceEntity> items =
        repo.resources.findByType("ITEM");   // DB 기반

    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);

    for (ResourceEntity r : items) {
      model.addElement(
          "%s (대여기간 %d일, 보증금 %d원)"
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

      ResourceEntity item = items.get(idx);

      // 날짜 + 시간 조합
      Date start;
      try {
        start = DATETIME.parse(dateField.getText().trim() + " " + timeField.getText().trim());
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "날짜/시간 형식이 올바르지 않습니다.");
        return;
      }

      // 반납 예정일 = start + rentalDays
      Calendar cal = Calendar.getInstance();
      cal.setTime(start);
      cal.add(Calendar.DATE, item.getRentalPeriod());
      Date expectedReturn = cal.getTime();

      // ===============================
      // 🔥 DB에 ReservationEntity 저장
      // ===============================
      ReservationEntity r = new ReservationEntity(
          LoginPanel.currentUserId,
          LoginPanel.currentUserName,
          item.getName(),
          "ITEM",
          start,
          expectedReturn,
          null,
          null  // 물품은 행사명 없음
      );

      repo.reservations.save(r);

      // 팝업 표시
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
              )
      );

      frame.showPanel("MENU");
    });

    // =========================
    // 4) 뒤로가기
    // =========================
    backBtn.addActionListener(e -> frame.showPanel("MENU"));
  }
}
