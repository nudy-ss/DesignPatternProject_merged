package ui.Main;

import reservation.Reservation;
import reservation.ReservationManager;
import resource.RentableResource;
import resource.ReservableResource;
import resource.TimeSlot;
import ui.Main.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyReservationPanel extends JPanel {

  private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public MyReservationPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new BorderLayout());

    JLabel title = new JLabel("[내 예약 현황]", SwingConstants.CENTER);
    title.setFont(new Font("Dialog", Font.BOLD, 18));

    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> list = new JList<>(model);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JButton refreshBtn = new JButton("새로고침");
    JButton backBtn = new JButton("뒤로 가기");

    JPanel bottom = new JPanel(new FlowLayout());
    bottom.add(refreshBtn);
    bottom.add(backBtn);

    add(title, BorderLayout.NORTH);
    add(new JScrollPane(list), BorderLayout.CENTER);
    add(bottom, BorderLayout.SOUTH);

    // 화면 열릴 때 자동 로드
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentShown(java.awt.event.ComponentEvent evt) {
        reload(model, manager);
      }
    });

    refreshBtn.addActionListener(e -> reload(model, manager));
    backBtn.addActionListener(e -> frame.showPanel("MENU"));


    // 더블클릭 → 상세보기/반납
    list.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {

          int idx = list.getSelectedIndex();
          if (idx < 0) return;

          List<Reservation> myList = manager.getUserReservations(LoginPanel.currentUserId);
          Reservation r = myList.get(idx);

          if (r.getResource() instanceof ReservableResource) {
            showFacilityDetail(frame, r);
          } else if (r.getResource() instanceof RentableResource) {
            showItemDetail(frame, manager, r);
          }
        }
      }
    });
  }


  // ===========================
  //  목록 갱신
  // ===========================
  private void reload(DefaultListModel<String> model, ReservationManager manager) {

    model.clear();

    List<Reservation> list = manager.getUserReservations(LoginPanel.currentUserId);

    for (Reservation r : list) {

      String text;

      if (r.getResource() instanceof ReservableResource) {
        // 시설 예약
        text = "%s | %s | %s"
            .formatted(
                r.getResource().getName(),
                DATE.format(r.getStartDate()),
                (r.getTimeSlot() != null ? r.getTimeSlot().toString() : "")
            );

      } else {
        // 물품 대여
        text = "%s | 대여: %s | 반납 예정: %s"
            .formatted(
                r.getResource().getName(),
                DATETIME.format(r.getStartDate()),
                DATETIME.format(r.getEndDate())
            );
      }

      model.addElement(text);
    }
  }


  // ===========================
  //  시설 예약 상세
  // ===========================
  private void showFacilityDetail(MainFrame frame, Reservation r) {

    String msg = """
                [시설 예약 상세]
                신청자: %s
                시설: %s
                날짜: %s
                시간대: %s
                """.formatted(
        r.getUser().getName(),
        r.getResource().getName(),
        DATE.format(r.getStartDate()),
        (r.getTimeSlot() != null ? r.getTimeSlot().toString() : "")
    );

    JOptionPane.showMessageDialog(frame, msg);
  }


  // ===========================
  //  물품 대여 상세 + 반납
  // ===========================
  private void showItemDetail(MainFrame frame, ReservationManager manager, Reservation r) {

    int option = JOptionPane.showConfirmDialog(
        frame,
        """
        [물품 대여 상세]
        신청자: %s
        물품: %s
        대여 시작: %s
        반납 예정: %s
        
        지금 반납하시겠습니까?
        """.formatted(
            r.getUser().getName(),
            r.getResource().getName(),
            DATETIME.format(r.getStartDate()),
            DATETIME.format(r.getEndDate())
        ),
        "반납 확인",
        JOptionPane.YES_NO_OPTION
    );

    if (option == JOptionPane.YES_OPTION) {
      manager.returnItemReservation(r);
      JOptionPane.showMessageDialog(frame, "반납이 완료되었습니다.");

      // 새로고침
      reload((DefaultListModel<String>) ((JList)getComponent(1)).getModel(), manager);
    }
  }
}
