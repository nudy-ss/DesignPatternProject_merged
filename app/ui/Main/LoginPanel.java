package ui.Main;

import ui.Main.MainFrame;
import reservation.ReservationManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

  public static String currentUserId;
  public static String currentUserName;
  public static user.Admin currentAdmin;

  public LoginPanel(MainFrame frame, ReservationManager manager) {

    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 10, 10);
    c.fill = GridBagConstraints.HORIZONTAL;

    JLabel title = new JLabel("학교 시설/시설물 대여 시스템");
    title.setFont(new Font("Dialog", Font.BOLD, 22));
    title.setHorizontalAlignment(SwingConstants.CENTER);

    JTextField idField = new JTextField(15);
    JTextField nameField = new JTextField(15);

    JButton loginBtn = new JButton("로그인");
    loginBtn.setFont(new Font("Dialog", Font.BOLD, 16));

    c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
    add(title, c);

    c.gridy = 1; c.gridwidth = 1;
    add(new JLabel("학번:"), c);

    c.gridx = 1;
    add(idField, c);

    c.gridx = 0; c.gridy = 2;
    add(new JLabel("이름:"), c);

    c.gridx = 1;
    add(nameField, c);

    c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
    add(loginBtn, c);

    // 로그인 버튼 기능
    loginBtn.addActionListener(e -> {
      String id = idField.getText().trim();
      String name = nameField.getText().trim();

      if (id.isEmpty() || name.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "학번과 이름을 모두 입력하세요.");
        return;
      }

      currentUserId = id;
      currentUserName = name;

      // 관리자 로그인
      if (id.equals("99999999")) {
        currentAdmin = new user.Admin(id, name, id);
        frame.showPanel("ADMIN");
      } else {
        frame.showPanel("MENU");
      }
    });
  }
}
