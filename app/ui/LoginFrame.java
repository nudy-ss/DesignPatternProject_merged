package ui;

import auth.RoleService;
import reservation.ReservationManager;
import user.Admin;
import user.User;

import javax.swing.*;
import java.awt.*;

/*public class LoginFrame extends JFrame {
  private final ReservationManager manager;
  private final RoleService roleService = new RoleService();

  public LoginFrame(ReservationManager manager) {
    this.manager = manager;
    setTitle("예약 시스템 로그인");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(360, 180);
    setLocationRelativeTo(null);

    var idField = new JTextField();
    var nameField = new JTextField();
    var loginBtn = new JButton("로그인");

    var panel = new JPanel(new GridLayout(3, 2, 8, 8));
    panel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
    panel.add(new JLabel("학번:"));
    panel.add(idField);
    panel.add(new JLabel("이름:"));
    panel.add(nameField);
    panel.add(new JLabel());
    panel.add(loginBtn);

    setContentPane(panel);

    loginBtn.addActionListener(e -> {
      String id = idField.getText().trim();
      String name = nameField.getText().trim();
      if (id.isEmpty() || name.isEmpty()) {
        JOptionPane.showMessageDialog(this, "학번/이름을 입력하세요.");
        return;
      }
      if (roleService.isAdmin(id)) {
        new AdminFrame(manager, new Admin(id, name, "ADMIN")).setVisible(true);
      } else {
        new UserFrame(manager, new User(id, name)).setVisible(true);
      }
      dispose();
    });
  }
}
*/