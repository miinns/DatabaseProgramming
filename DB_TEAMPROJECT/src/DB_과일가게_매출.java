
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DB_과일가게_매출 extends JFrame {

    private JPanel contentPane;
    private JComboBox<String> comboBox;
    private JComboBox<String> comboBox_1;
    private DefaultTableModel model;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DB_과일가게_매출 frame = new DB_과일가게_매출();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DB_과일가게_매출() {
        setTitle("매출");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 544);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("시작 날짜");
        lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
        lblNewLabel.setBounds(265, 17, 71, 20);
        contentPane.add(lblNewLabel);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[] { "날짜", "판매량", "매출" });
        JTable table_1 = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(18, 95, 900, 387);
        contentPane.add(scrollPane);

        JComboBox comboBox = new JComboBox();
        comboBox.setFont(new Font("굴림", Font.PLAIN, 15));
        comboBox.setBounds(388, 17, 154, 23);
        contentPane.add(comboBox);

        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setFont(new Font("굴림", Font.PLAIN, 15));
        comboBox_1.setBounds(388, 63, 154, 23);
        contentPane.add(comboBox_1);
        // 시작 날짜 콤보박스 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.of(2023, 9, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        while (!startDate.isAfter(endDate)) {
            comboBox.addItem(startDate.format(formatter));
            comboBox_1.addItem(startDate.format(formatter));
            startDate = startDate.plusDays(1);
        }

        JLabel lblNewLabel_1 = new JLabel("종료 날짜");
        lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(265, 63, 71, 20);
        contentPane.add(lblNewLabel_1);

        JButton btnNewButton = new JButton("매출확인");
        btnNewButton.setBounds(637, 35, 91, 25);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedStartDate = (String) comboBox.getSelectedItem();
                String selectedEndDate = (String) comboBox_1.getSelectedItem();

                if (selectedStartDate == null || selectedStartDate.isEmpty() || selectedEndDate == null
                        || selectedEndDate.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "날짜를 선택해주세요", "알림", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
                        String username = "fruit";
                        String password = "1234";
                        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                        CallableStatement callableStatement = connection.prepareCall("{call SP_주문통계(?, ?, ?, ?)}");
                        callableStatement.setDate(1, java.sql.Date.valueOf(selectedStartDate));
                        callableStatement.setDate(2, java.sql.Date.valueOf(selectedEndDate));
                        callableStatement.registerOutParameter(3, Types.NUMERIC); // P_판매량
                        callableStatement.registerOutParameter(4, Types.NUMERIC); // P_매출
                        callableStatement.execute();//프로시저실행

                        int 판매량 = 0; // 기본값은 0으로 설정

                        // getInt() 호출 전에 값이 NULL이 아닌지 확인
                        if (callableStatement.getObject(3) != null) {
                            판매량 = callableStatement.getInt(3);
                        }
                        int 매출 = 0; // 기본값은 0으로 설정

                        // getInt() 호출 전에 값이 NULL이 아닌지 확인
                        if (callableStatement.getObject(4) != null) {
                            매출 = callableStatement.getInt(4);
                        }

                        // 쿠폰 발급 여부 확인
                        if (매출 > 100000) {
                            JOptionPane.showMessageDialog(contentPane, "매출이 10만원을 넘어 쿠폰이 발급되었습니다!", "쿠폰 발급",
                                    JOptionPane.INFORMATION_MESSAGE);
                            // 여기에 쿠폰 발급 로직을 추가할 수 있습니다.
                        } else {
                            JOptionPane.showMessageDialog(contentPane, "매출: " + 매출, "매출 확인",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        // JTable에 데이터 추가
                        model.addRow(new Object[] { selectedStartDate + " ~ " + selectedEndDate, 판매량, 매출 });

                        callableStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(contentPane, "데이터베이스 오류", "에러", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}