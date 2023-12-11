import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.sql.PreparedStatement;


public class DB_과일가게_손님 extends JFrame {

   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JTextField textField;
   private JTextField textField_6;
   private JTextField textField_7;
   private JTextField textField_8;
   private JTextField textField_9;
   private JTextField textField_10;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               DB_과일가게_손님 frame = new DB_과일가게_손님();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public DB_과일가게_손님() {
      setTitle("손님확인");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 950, 544);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

      setContentPane(contentPane);
      
      DefaultTableModel model = new DefaultTableModel();
      model.setColumnIdentifiers(new Object[] {"손님번호", "이름", "주소", "전화번호", "계좌번호", "적립금"});
      contentPane.setLayout(null);
      JTable table = new JTable(model);

      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(18, 84, 898, 400);
      contentPane.add(scrollPane);
      
      JLabel lblNewLabel = new JLabel("손님번호");
      lblNewLabel.setBounds(18, 23, 62, 15);
      contentPane.add(lblNewLabel);
      
      textField = new JTextField();
      textField.setBounds(75, 20, 62, 21);
      contentPane.add(textField);
      textField.setColumns(10);
      
      JLabel lblNewLabel_1 = new JLabel("이름");
      lblNewLabel_1.setBounds(146, 23, 37, 15);
      contentPane.add(lblNewLabel_1);
      
      textField_6 = new JTextField();
      textField_6.setBounds(184, 20, 81, 21);
      contentPane.add(textField_6);
      textField_6.setColumns(10);
      
      JLabel lblNewLabel_2 = new JLabel("주소");
      lblNewLabel_2.setBounds(290, 23, 62, 15);
      contentPane.add(lblNewLabel_2);
      
      textField_7 = new JTextField();
      textField_7.setBounds(330, 20, 96, 21);
      contentPane.add(textField_7);
      textField_7.setColumns(10);
      
      JLabel lblNewLabel_3 = new JLabel("계좌번호");
      lblNewLabel_3.setBounds(438, 23, 62, 15);
      contentPane.add(lblNewLabel_3);
      
      textField_8 = new JTextField();
      textField_8.setBounds(494, 20, 96, 21);
      contentPane.add(textField_8);
      textField_8.setColumns(10);
      
      JLabel lblNewLabel_4 = new JLabel("전화번호");
      lblNewLabel_4.setBounds(602, 23, 62, 15);
      contentPane.add(lblNewLabel_4);
      
      textField_9 = new JTextField();
      textField_9.setBounds(659, 20, 96, 21);
      contentPane.add(textField_9);
      textField_9.setColumns(10);
      
      JLabel lblNewLabel_5 = new JLabel("적립금");
      lblNewLabel_5.setBounds(767, 23, 50, 15);
      contentPane.add(lblNewLabel_5);
      
      textField_10 = new JTextField();
      textField_10.setBounds(813, 21, 96, 21);
      contentPane.add(textField_10);
      textField_10.setColumns(10);
      
      JButton btnNewButton = new JButton("조회");
      btnNewButton.setBounds(523, 51, 91, 23);
      contentPane.add(btnNewButton);
      btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","fruit", "1234");
                    Statement stmt = conn.createStatement();

                    String query = "SELECT * FROM 손님";
                    ResultSet rs = stmt.executeQuery(query);

                    model.setRowCount(0);

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    Vector<String> columnNames = new Vector<>();
                    for (int column = 1; column <= columnCount; column++) {
                        columnNames.add(metaData.getColumnName(column));
                    }
                    model.setColumnIdentifiers(columnNames);

                    while (rs.next()) {
                        Vector<Object> rowData = new Vector<>();
                        for (int i = 1; i <= columnCount; i++) {
                            rowData.add(rs.getObject(i));
                        }
                        model.addRow(rowData);
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
      
      JButton btnNewButton_1 = new JButton("추가");
      btnNewButton_1.setBounds(723, 51, 91, 23);
      contentPane.add(btnNewButton_1);
      btnNewButton_1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             String 손님번호 = textField.getText();
               String 이름 = textField_6.getText();
               String 주소 = textField_7.getText();
               String 계좌번호 = textField_8.getText();
               String 전화번호 = textField_9.getText();
               String 적립금 = textField_10.getText();

               if (손님번호.isEmpty() || 이름.isEmpty() || 주소.isEmpty() || 계좌번호.isEmpty() || 전화번호.isEmpty()) {
                   JOptionPane.showMessageDialog(contentPane, "입력해주세요", "알림", JOptionPane.WARNING_MESSAGE);
               } else {
               DefaultTableModel model = (DefaultTableModel) table.getModel();
               model.addRow(new Object[] { 손님번호, 이름, 주소, 계좌번호, 전화번호, 적립금 });
              JOptionPane.showMessageDialog(contentPane, "손님이 추가되었습니다.", "추가 완료", JOptionPane.INFORMATION_MESSAGE);
                   Connection conn;
               try {
                  conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit", "1234");
                  Statement stmt = conn.createStatement();
                 
                      String query = "INSERT INTO 손님 (손님번호, 이름, 주소, 계좌번호, 전화번호, 적립금) VALUES ('" + 손님번호 + "', '" + 이름 + "', '" + 주소 + "', '" + 계좌번호 + "', '" + 전화번호 + "', '" + 적립금 + "')";
                      stmt.executeUpdate(query);

                      stmt.close();
                      conn.close();
                      
               } catch (SQLException e1) {
                  e1.printStackTrace();
               }
               }
       }
   });
      
      JButton btnNewButton_2 = new JButton("삭제");
      btnNewButton_2.setBounds(823, 52, 91, 23);
      contentPane.add(btnNewButton_2);
      btnNewButton_2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              int selectedRow = table.getSelectedRow();
              if (selectedRow == -1) {
                  JOptionPane.showMessageDialog(contentPane, "삭제할 항목을 선택해주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
              } else {
                  try {              
                  String customerToDelete = table.getValueAt(selectedRow, 0).toString();           
                  DefaultTableModel model = (DefaultTableModel) table.getModel();
                  model.removeRow(selectedRow);
                  Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit", "1234");
                  
                  Statement stmt = conn.createStatement();               
                  String deleteQuery = "DELETE FROM 손님 WHERE 손님번호 = " + customerToDelete;     
                  stmt.executeUpdate(deleteQuery);         
                  stmt.close();
                  conn.close();

                      JOptionPane.showMessageDialog(contentPane, "삭제되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
                  } catch (SQLException ex) {
                      ex.printStackTrace();
                  }
              }
          }
      });
      
      JButton btnNewButton_4 = new JButton("갱신");
      btnNewButton_4.setBounds(623, 51, 91, 23);
      contentPane.add(btnNewButton_4);
      btnNewButton_4.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              try {
                  Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit", "1234");
                  Statement stmt = conn.createStatement();

                  String updateAddressQuery = "UPDATE 손님 SET 주소 = REPLACE(주소, '부산진구', '동의구') WHERE 주소 LIKE ?";
                  try (PreparedStatement updateAddressStmt = conn.prepareStatement(updateAddressQuery)) {
                      updateAddressStmt.setString(1, "%부산진구%");
                      int updatedRows = updateAddressStmt.executeUpdate();
                      System.out.println("주소가 '부산진구'인 " + updatedRows + "개의 행이 '동의구'로 업데이트되었습니다.");
                  }

                  String query = "SELECT * FROM 손님";
                  ResultSet rs = stmt.executeQuery(query);

                  model.setRowCount(0);

                  ResultSetMetaData metaData = rs.getMetaData();
                  int columnCount = metaData.getColumnCount();

                  Vector<String> columnNames = new Vector<>();
                  for (int column = 1; column <= columnCount; column++) {
                      columnNames.add(metaData.getColumnName(column));
                  }
                  model.setColumnIdentifiers(columnNames);

                  while (rs.next()) {
                      Vector<Object> rowData = new Vector<>();
                      for (int i = 1; i <= columnCount; i++) {
                          rowData.add(rs.getObject(i));
                      }
                      model.addRow(rowData);
                  }
                  rs.close();
                  stmt.close();
                  conn.close();
              } catch (SQLException ex) {
                  ex.printStackTrace();
              }
          }
      });
      
   }

   public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
      ResultSetMetaData metaData = rs.getMetaData();

      int columnCount = metaData.getColumnCount();
      Vector<String> columnNames = new Vector<>();
      for (int column = 1; column <= columnCount; column++) {
         columnNames.add(metaData.getColumnName(column));
      }

      Vector<Vector<Object>> data = new Vector<>();
      while (rs.next()) {
         Vector<Object> row = new Vector<>();
         for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            row.add(rs.getObject(columnIndex));
         }
         data.add(row);
      }

      return new DefaultTableModel(data, columnNames);
   }
}