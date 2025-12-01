package ui;

import Repository.RepositoryManager;
import entity.LectureEntity;
import entity.ResourceEntity;
import reservation.ReservationManager;
import reservation.factory.ItemReservationFactory;
import reservation.factory.LectureReservationFactory;
import reservation.factory.ReservationFactory;
import resource.*;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*public class UserFrame extends JFrame {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final ReservationManager manager;
    private final User user;

    private final DefaultListModel<String> lectureModel = new DefaultListModel<>();
    private final DefaultListModel<String> rentalModel = new DefaultListModel<>();
    private final JList<String> lectureList = new JList<>(lectureModel);
    private final JList<String> rentalList = new JList<>(rentalModel);

    private final JTextField dateField = new JTextField(DATE_FORMAT.format(new Date()));
    private final JComboBox<String> slotBox = new JComboBox<>(buildHourlySlots());
    private final JComboBox<RoomTheme> optionBox = new JComboBox<>(RoomTheme.values());
    private final JTextField rentalDateField = new JTextField(DATE_FORMAT.format(new Date()));
    private final JComboBox<String> rentalSlotBox = new JComboBox<>(buildHourlySlots());

    private RepositoryManager repositoryManager;
    public UserFrame(ReservationManager manager, User user) {

        this.repositoryManager = RepositoryManager.getInstance();
        this.manager = manager;
        this.user = user;

        setTitle("사용자 모드 - " + user.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 500);
        setLocationRelativeTo(null);

        //강의실 목록
        ensureSampleReserves();
        setContentPane(buildContent());
        //대여물품 목록
        ensureSampleRentals();

        reloadLists();
    }

    private static String[] buildHourlySlots() {
        List<String> slots = new ArrayList<>();
        for (int hour = 9; hour < 20; hour++) {
            slots.add(String.format("%02d:00-%02d:00", hour, hour + 1));
        }
        return slots.toArray(new String[0]);
    }

    private Container buildContent() {
        var root = new JPanel(new GridLayout(1, 2, 10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        root.add(buildLecturePanel());
        root.add(buildRentalPanel());
        return root;
    }

    private JPanel buildLecturePanel() {
        var panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("강의실 예약"));

        lectureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(lectureList), BorderLayout.CENTER);
        panel.add(buildReservationForm(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildReservationForm() {
        var form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.setBorder(BorderFactory.createTitledBorder("예약 정보"));

        form.add(new JLabel("날짜 (yyyy-MM-dd)"));
        form.add(dateField);
        form.add(new JLabel("시간대"));
        form.add(slotBox);
        form.add(new JLabel("부가 옵션"));
        form.add(optionBox);

        var reserveBtn = new JButton("예약하기");
        reserveBtn.addActionListener(e -> handleReserve());
        form.add(new JLabel());
        form.add(reserveBtn);

        return form;
    }

    private JPanel buildRentalPanel() {
        var panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("비품 대여/반납"));

        rentalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(rentalList), BorderLayout.CENTER);
        panel.add(buildRentalForm(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildRentalForm() {
        var form = new JPanel(new BorderLayout(6, 6));
        form.setBorder(BorderFactory.createTitledBorder("대여 정보"));

        var fields = new JPanel(new GridLayout(0, 2, 6, 6));
        fields.add(new JLabel("날짜 (yyyy-MM-dd)"));
        fields.add(rentalDateField);
        fields.add(new JLabel("시간대"));
        fields.add(rentalSlotBox);

        var buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        var rentBtn = new JButton("대여");
        rentBtn.addActionListener(e -> handleRent());
        var returnBtn = new JButton("반납");
        returnBtn.addActionListener(e -> handleReturn());
        buttonPanel.add(rentBtn);
        buttonPanel.add(returnBtn);

        form.add(fields, BorderLayout.CENTER);
        form.add(buttonPanel, BorderLayout.SOUTH);
        return form;
    }

    // 강의실 예약 (Factory 적용)
    private void handleReserve() {
        int idx = lectureList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "강의실을 선택해 주세요.");
            return;
        }

        String dateText = dateField.getText().trim();
        if (dateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "예약 날짜를 입력해 주세요.");
            return;
        }

        Date date;
        try {
            date = DATE_FORMAT.parse(dateText);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "날짜 형식이 올바르지 않습니다. (yyyy-MM-dd)");
            return;
        }

        String slotName = (String) slotBox.getSelectedItem();
        if (slotName == null || slotName.isBlank()) {
            JOptionPane.showMessageDialog(this, "예약 시간을 선택해 주세요.");
            return;
        }

        RoomTheme option = (RoomTheme) optionBox.getSelectedItem();
        if (option == null) {
            JOptionPane.showMessageDialog(this, "부가 옵션을 선택해 주세요.");
            return;
        }
        System.out.println("idx : " + idx) ;
        ReservableResource resource = manager.getReservables().get(idx);
        TimeSlot slot = new TimeSlot(slotName);

        try {
            ReservationFactory factory = new LectureReservationFactory(manager,repositoryManager);
            factory.create(user, resource, date, null);
            JOptionPane.showMessageDialog(this,
                    "예약되었습니다.\n강의실: %s\n옵션: %s\n날짜: %s\n시간: %s"
                            .formatted(resource.getName(), option.getDisplayName(), dateText, slot.getStartTime(), slot.getEndTime()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "예약 실패", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 대여 (Factory 적용)
    private void handleRent() {
        int idx = rentalList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "비품을 선택하세요.");
            return;
        }

        String dateText = rentalDateField.getText().trim();
        if (dateText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "대여 날짜를 입력하세요.");
            return;
        }

        try {
            DATE_FORMAT.parse(dateText);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "날짜 형식이 올바르지 않습니다. (yyyy-MM-dd)");
            return;
        }

        String slotName = (String) rentalSlotBox.getSelectedItem();
        if (slotName == null || slotName.isBlank()) {
            JOptionPane.showMessageDialog(this, "대여 시간대를 선택하세요.");
            return;
        }

        RentableResource item = manager.getRentables().get(idx);

        Date startDate;
        Date endDate;
        try {
            Date[] window = parseRentalWindow(dateText, slotName);
            startDate = window[0];
            endDate = window[1];
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "시간대 형식이 올바르지 않습니다. (예: 09:00-11:00)");
            return;
        }

        ReservationFactory factory = new ItemReservationFactory(manager,repositoryManager);
        factory.create(user, item, startDate, endDate);

        JOptionPane.showMessageDialog(this, "대여 완료!\n날짜: %s\n시간대: %s".formatted(dateText, slotName));
    }

    // ❗ 반납은 팩토리 X (생성 아님)
    private void handleReturn() {
        int idx = rentalList.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "비품을 선택해 주세요.");
            return;
        }

        RentableResource item = manager.getRentables().get(idx);
        item.returnItem(user);

//        repositoryManager.reservations.

        JOptionPane.showMessageDialog(this, "반납 처리 완료");
    }

    private Date[] parseRentalWindow(String dateText, String slotName) throws ParseException {
        String[] parts = slotName.split("-");
        if (parts.length != 2) {
            throw new ParseException("invalid slot format", 0);
        }
        Date start = DATE_TIME_FORMAT.parse("%s %s".formatted(dateText, parts[0].trim()));
        Date end = DATE_TIME_FORMAT.parse("%s %s".formatted(dateText, parts[1].trim()));
        return new Date[]{start, end};
    }

    //대여물품
    private void ensureSampleRentals() {
        //데이터 베이스 연결

        if (manager.getRentables().size() == 0) {
            List<ResourceEntity> result = repositoryManager.resources.findAll();
            for (ResourceEntity r : result) {
                manager.addRentableResource(new SimpleItem(r.getName(), r.getDeposit()));
                System.out.println("이름: " + r.getName() +
                        ", 보증금: " + r.getDeposit());
//                  ", 재고: " + r.isAvailable());
            }
        }
//    if (manager.getRentables().size() == 0) {
        //    manager.addRentableResource(new SimpleItem("노트북", 10));
        //  manager.addRentableResource(new SimpleItem("우산", 1));
//    }

    }

    //목록 갱신
    private void reloadLists() {
        lectureModel.clear();
        manager.getReservables().forEach(r ->
                lectureModel.addElement("%s (보증금=%d)".formatted(r.getName(), r.getDeposit())));

        rentalModel.clear();
        manager.getRentables().forEach(r ->
                rentalModel.addElement("%s (보증금=%d)".formatted(r.getName(), r.getDeposit())));
    }

//강의실 목록
    private void ensureSampleReserves() {
        //데이터 베이스 연결

        if (manager.getRentables().size() == 0) {
            List<LectureEntity> result = repositoryManager.lectures.findAll();
            for (LectureEntity r : result) {
                manager.addReservableResource(new SimpleLectureRoom(r.getName(), r.getDeposit()));
                System.out.println("이름: " + r.getName() +
                        ", 보증금: " + r.getDeposit());
//                  ", 재고: " + r.isAvailable());
            }
        }
    }
}
*/