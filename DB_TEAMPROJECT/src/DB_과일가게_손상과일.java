import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DB_과일가게_손상과일 extends JFrame {

   private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private DefaultTableModel model;
    private int selectedRow = -1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DB_과일가게_손상과일 frame = new DB_과일가게_손상과일();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DB_과일가게_손상과일() {
        setTitle("손상과일");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 950, 544);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[] {"과일아이디", "손상과일번호", "파손", "상함", "보존기간만료"});
        contentPane.setLayout(null);
        JTable table_1 = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(24, 100, 898, 390);
        contentPane.add(scrollPane);

        JLabel lblNewLabel = new JLabel("과일아이디");
        lblNewLabel.setBounds(36, 37, 70, 15);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(106, 34, 89, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("손상과일번호");
        lblNewLabel_1.setBounds(207, 37, 89, 15);
        contentPane.add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(294, 34, 89, 21);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("파손");
        lblNewLabel_2.setBounds(406, 37, 30, 15);
        contentPane.add(lblNewLabel_2);

        textField_2 = new JTextField();
        textField_2.setBounds(448, 34, 89, 21);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("상함");
        lblNewLabel_3.setBounds(561, 37, 43, 15);
        contentPane.add(lblNewLabel_3);

        textField_3 = new JTextField();
        textField_3.setBounds(608, 34, 89, 21);
        contentPane.add(textField_3);
        textField_3.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("보존기간만료");
        lblNewLabel_4.setBounds(716, 37, 80, 15);
        contentPane.add(lblNewLabel_4);

        textField_4 = new JTextField();
        textField_4.setBounds(808, 34, 89, 21);
        contentPane.add(textField_4);
        textField_4.setColumns(10);

        JButton btnNewButton = new JButton("추가");
        btnNewButton.setBounds(593, 69, 95, 21);
        contentPane.add(btnNewButton);

        JButton btnList = new JButton("목록 조회");
        btnList.setBounds(807, 69, 95, 21);
        contentPane.add(btnList);

        JButton btnUpdate = new JButton("수정");
        btnUpdate.setBounds(486, 69, 95, 21);
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("삭제");
        btnDelete.setBounds(700, 69, 95, 21);
        contentPane.add(btnDelete);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDamagedFruit();
            }
        });

        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDamagedFruitList();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSelectedRow();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });

        table_1.getSelectionModel().addListSelectionListener(event -> {
            selectedRow = table_1.getSelectedRow();
        });
    }

    private void addDamagedFruit() {
        String 과일아이디 = textField.getText();
        String 손상과일번호 = textField_1.getText();
        String 파손 = textField_2.getText();
        String 상함 = textField_3.getText();
        String 보존기간만료 = textField_4.getText();

        if (과일아이디.isEmpty() || 손상과일번호.isEmpty() || 파손.isEmpty() || 상함.isEmpty() || 보존기간만료.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "입력해주세요", "알림", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
                String username = "fruit";
                String password = "1234";

                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                String insertQuery = "INSERT INTO 손상과일 (과일아이디, 손상과일번호, 파손, 상함, 보존기간만료) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, 과일아이디);
                preparedStatement.setString(2, 손상과일번호);
                preparedStatement.setString(3, 파손);
                preparedStatement.setString(4, 상함);
                preparedStatement.setString(5, 보존기간만료);
                preparedStatement.executeUpdate();

                String deleteQuery = "DELETE FROM 과일 WHERE 과일아이디 = ?";
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, 과일아이디);
                preparedStatement.executeUpdate();

                String 납품업체알림 = "손상 과일 추가 - 과일아이디: " + 과일아이디 + ", 손상과일번호: " + 손상과일번호;
                System.out.println("납품업체에 알림: " + 납품업체알림);

                model.addRow(new Object[] {과일아이디, 손상과일번호, 파손, 상함, 보존기간만료});

                JOptionPane.showMessageDialog(contentPane, "손상과일이 추가되었습니다. 납품업체를 확인하세요.", "알림", JOptionPane.INFORMATION_MESSAGE);

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(contentPane, "데이터베이스 오류", "에러", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showDamagedFruitList() {
        try {
            String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
            String username = "fruit";
            String password = "1234";
            
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            String selectQuery = "SELECT * FROM 손상과일";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            model.setRowCount(0);
            while (resultSet.next()) {
                String 과일아이디 = resultSet.getString("과일아이디");
                String 손상과일번호 = resultSet.getString("손상과일번호");
                String 파손 = resultSet.getString("파손");
                String 상함 = resultSet.getString("상함");
                String 보존기간만료 = resultSet.getString("보존기간만료");

                model.addRow(new Object[] {과일아이디, 손상과일번호, 파손, 상함, 보존기간만료});
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "데이터베이스 오류", "에러", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateSelectedRow() {
        if (selectedRow != -1) {
            textField.setText(model.getValueAt(selectedRow, 0).toString());
            textField_1.setText(model.getValueAt(selectedRow, 1).toString());
            textField_2.setText(model.getValueAt(selectedRow, 2).toString());
            textField_3.setText(model.getValueAt(selectedRow, 3).toString());
            textField_4.setText(model.getValueAt(selectedRow, 4).toString());

            try {
                String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
                String username = "fruit";
                String password = "1234";

                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                String updateQuery = "UPDATE 손상과일 SET 손상과일번호=?, 파손=?, 상함=?, 보존기간만료=? WHERE 과일아이디=?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, textField_1.getText());
                preparedStatement.setString(2, textField_2.getText());
                preparedStatement.setString(3, textField_3.getText());
                preparedStatement.setString(4, textField_4.getText());
                preparedStatement.setString(5, textField.getText());
                preparedStatement.executeUpdate();

                model.setValueAt(textField_1.getText(), selectedRow, 1);
                model.setValueAt(textField_2.getText(), selectedRow, 2);
                model.setValueAt(textField_3.getText(), selectedRow, 3);
                model.setValueAt(textField_4.getText(), selectedRow, 4);
                JOptionPane.showMessageDialog(contentPane, "손상과일이 수정되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(contentPane, "데이터베이스 오류", "에러", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(contentPane, "수정할 행을 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedRow() {
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(contentPane, "선택한 행을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
                    String username = "fruit";
                    String password = "1234";

                    Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                    String deleteQuery = "DELETE FROM 손상과일 WHERE 과일아이디=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                    preparedStatement.setString(1, textField.getText());
                    preparedStatement.executeUpdate();
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(contentPane, "손상과일이 삭제되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

                    preparedStatement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(contentPane, "삭제할 행을 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
        }
    }
}