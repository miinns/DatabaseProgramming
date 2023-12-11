import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class DB_과일가게_예약 extends JFrame {

   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JTextField textField;
   private JTextField textField_1;
   private JTextField textField_2;
   private JTextField textField_3;
   private JTextField textField_4;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               DB_과일가게_예약 frame = new DB_과일가게_예약();
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
   public DB_과일가게_예약() {
      setTitle("예약 확인");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 950, 544);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      DefaultTableModel model = new DefaultTableModel();
      model.setColumnIdentifiers(new Object[] {"예약번호", "손님번호", "주문과일종류", "주문수량", "주문날짜"});
      JTable table_1 = new JTable(model);

      JScrollPane scrollPane = new JScrollPane(table_1);
      scrollPane.setBounds(12, 105, 870, 380);
      contentPane.add(scrollPane);
      
      JLabel lblNewLabel = new JLabel("예약번호");
      lblNewLabel.setBounds(27, 35, 65, 15);
      contentPane.add(lblNewLabel);
      
      JLabel lblNewLabel_1 = new JLabel("손님번호");
      lblNewLabel_1.setBounds(175, 35, 65, 15);
      contentPane.add(lblNewLabel_1);
      
      JLabel lblNewLabel_2 = new JLabel("주문과일종류");
      lblNewLabel_2.setBounds(342, 35, 90, 15);
      contentPane.add(lblNewLabel_2);
      
      JLabel lblNewLabel_3 = new JLabel("주문수량");
      lblNewLabel_3.setBounds(528, 35, 55, 15);
      contentPane.add(lblNewLabel_3);
      
      JLabel lblNewLabel_4 = new JLabel("주문날짜");
      lblNewLabel_4.setBounds(703, 35, 60, 15);
      contentPane.add(lblNewLabel_4);
      
      textField = new JTextField();
      textField.setBounds(85, 32, 83, 21);
      contentPane.add(textField);
      textField.setColumns(10);
      
      textField_1 = new JTextField();
      textField_1.setBounds(234, 32, 96, 21);
      contentPane.add(textField_1);
      textField_1.setColumns(10);
      
      textField_2 = new JTextField();
      textField_2.setBounds(422, 32, 96, 21);
      contentPane.add(textField_2);
      textField_2.setColumns(10);
      
      textField_3 = new JTextField();
      textField_3.setBounds(595, 32, 96, 21);
      contentPane.add(textField_3);
      textField_3.setColumns(10);
      
      textField_4 = new JTextField();
      textField_4.setBounds(775, 32, 96, 21);
      contentPane.add(textField_4);
      textField_4.setColumns(10);
   
    
      
      JButton btnNewButton = new JButton("예약");
      btnNewButton.setBounds(780, 72, 91, 23);
      contentPane.add(btnNewButton);

      btnNewButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              String 예약번호 = textField.getText();
              String 손님번호 = textField_1.getText();
              String 주문과일종류 = textField_2.getText();
              String 주문수량 = textField_3.getText();
              String 주문날짜 = textField_4.getText();

              if (예약번호==null || 손님번호.isEmpty() || 주문과일종류.isEmpty() || 주문수량.isEmpty() || 주문날짜.isEmpty()) {
                  JOptionPane.showMessageDialog(contentPane, "입력해주세요", "알림", JOptionPane.WARNING_MESSAGE);
              } else {
                  try {
                      // 데이터베이스 연결 정보
                      String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
                      String username = "fruit";
                      String password = "1234";

                      // 데이터베이스 연결
                      Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

                      // SP_주문처리 프로시저 호출
                      CallableStatement callableStatement = connection.prepareCall("{call SP_주문처리(?, ?, ?)}");

                      // 입력 매개변수 설정
                      callableStatement.setInt(1, Integer.parseInt(주문수량)); 
                      callableStatement.setString(2, 주문과일종류);
                      callableStatement.registerOutParameter(3, Types.VARCHAR);

                      // 프로시저 실행
                      callableStatement.execute();

                      // 결과 메시지 확인
                      String 결과메시지 = callableStatement.getString(3);
                      JOptionPane.showMessageDialog(contentPane, 결과메시지, "결과", JOptionPane.INFORMATION_MESSAGE);

                      // 테이블 모델에 데이터 추가
                      DefaultTableModel model = (DefaultTableModel) table_1.getModel();
                      model.addRow(new Object[]{예약번호, 손님번호, 주문과일종류, 주문수량, 주문날짜});

                      callableStatement.close();
                      connection.close();
                  } catch (SQLException ex) {
                      ex.printStackTrace();
                      JOptionPane.showMessageDialog(contentPane, "데이터베이스 오류", "에러", JOptionPane.ERROR_MESSAGE);
                  }
              }
          }
      });


      
      JButton btnNewButton_1 = new JButton("삭제");
      btnNewButton_1.setBounds(680, 72, 91, 23);
      contentPane.add(btnNewButton_1);
      btnNewButton_1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              int selectedRow = table_1.getSelectedRow();
              if (selectedRow == -1) {
                  JOptionPane.showMessageDialog(contentPane, "삭제할 항목을 선택해주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
              } else {
                  try {              
                  String bookIdToDelete = table_1.getValueAt(selectedRow, 0).toString();           
                  DefaultTableModel model = (DefaultTableModel) table_1.getModel();
                  model.removeRow(selectedRow);
                  Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit", "1234");
                  
                  Statement stmt = conn.createStatement();               
                  String deleteQuery = "DELETE FROM 예약 WHERE 예약번호 = " + bookIdToDelete;     
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
      
      JButton btnNewButton_2 = new JButton("조회");
         btnNewButton_2.setBounds(580, 72, 91, 23);
         contentPane.add(btnNewButton_2);
         btnNewButton_2.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   try {
                       Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","fruit", "1234");
                       Statement stmt = conn.createStatement();

                       String query = "SELECT * FROM 예약";
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