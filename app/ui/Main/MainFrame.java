package ui.Main;

import reservation.ReservationManager;
import ui.Main.LoginPanel;
import ui.Main.admin.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

  private CardLayout card = new CardLayout();
  private JPanel root = new JPanel(card);

  private ReservationManager manager;

  public MainFrame(ReservationManager manager) {
    this.manager = manager;
    setTitle("학교 시설/시설물 대여 시스템");
    setSize(900, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // 패널 등록
    root.add(new LoginPanel(this, manager), "LOGIN");
    root.add(new MenuPanel(this, manager), "MENU");
    root.add(new ReserveRoomPanel(this, manager), "RESERVE_ROOM");
    root.add(new RoomTimelinePanel(this, manager), "ROOM_TIMELINE");
    root.add(new RentItemPanel(this, manager), "RENT_ITEM");
    root.add(new MyReservationPanel(this, manager), "MY_RES");
    root.add(new AdminPanelMain(this, manager), "ADMIN");
    root.add(new AdminAddPanel(this, manager), "ADMIN_ADD");
    root.add(new AdminDeletePanel(this, manager), "ADMIN_DELETE");
    root.add(new AdminListPanel(this, manager), "ADMIN_LIST");
    root.add(new AdminEditPanel(this, manager), "ADMIN_EDIT");

    setContentPane(root);
    show("LOGIN");
  }

  public void show(String name) {
    card.show(root, name);
  }

  public void showPanel(String name) {
    show(name);
  }
}







