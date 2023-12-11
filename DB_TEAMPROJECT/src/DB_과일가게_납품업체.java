import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DB_과일가게_납품업체 extends JFrame {

   private static final long serialVersionUID = 1L;
   private JPanel contentPane;

   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               DB_과일가게_납품업체 frame = new DB_과일가게_납품업체();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   public DB_과일가게_납품업체() {
      setTitle("납품업체");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 950, 544);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);

      DefaultTableModel model = new DefaultTableModel();
      model.setColumnIdentifiers(new Object[] { "업체번호", "납품업체명", "전화번호", "위치" });
      contentPane.setLayout(null);
      JTable table = new JTable(model);

      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(22, 100, 885, 150);
      contentPane.add(scrollPane);
      
      DefaultTableModel model1 = new DefaultTableModel();
      model1.setColumnIdentifiers(new Object[] { "업체번호", "납품업체명", "과일이름" });
      contentPane.setLayout(null);
      JTable table_1 = new JTable(model1);

      JScrollPane scrollPane_1 = new JScrollPane(table_1);
      scrollPane_1.setSize(885, 150);
      scrollPane_1.setLocation(22, 280);
      contentPane.add(scrollPane_1);
      
      JLabel lblNewLabel_1 = new JLabel("업체번호");
      contentPane.add(lblNewLabel_1);

      JLabel lblNewLabel_2 = new JLabel("납품업체명");
      contentPane.add(lblNewLabel_2);

      JLabel lblNewLabel_3 = new JLabel("전화번호");
      contentPane.add(lblNewLabel_3);

      JLabel lblNewLabel_4 = new JLabel("위치");
      contentPane.add(lblNewLabel_4);

      JButton btnNewButton = new JButton("조회");
      btnNewButton.setBounds(632, 33, 100, 33);
      contentPane.add(btnNewButton);
      btnNewButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             try {
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit",
                      "1234");
                Statement stmt = conn.createStatement();

                String query = "SELECT * FROM 납품업체";
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

      JButton btnNewButton_1 = new JButton("업체별 최다 판매 조회");
      btnNewButton_1.setBounds(747, 32, 160, 34);
      contentPane.add(btnNewButton_1);
      btnNewButton_1.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        try {
    	            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit", "1234");
    	            Statement stmt = conn.createStatement();

    	            String query = "SELECT 납품업체.업체번호, 납품업체.납품업체명, MAX(과일.과일이름) AS 최대주문과일이름 " +
    	                    "FROM 납품업체 " +
    	                    "JOIN 과일 ON 납품업체.업체번호 = 과일.업체번호 " +
    	                    "JOIN 예약 ON 과일.예약번호 = 예약.예약번호 " +
    	                    "GROUP BY 납품업체.업체번호, 납품업체.납품업체명 " +
    	                    "ORDER BY 납품업체.업체번호 ASC";
    	            ResultSet rs = stmt.executeQuery(query);

    	            model1.setRowCount(0);

    	            ResultSetMetaData metaData = rs.getMetaData();
    	            int columnCount = metaData.getColumnCount();

    	         
    	            Vector<String> columnNames = new Vector<>();
    	            for (int column = 1; column <= columnCount; column++) {
    	                columnNames.add(metaData.getColumnName(column));
    	            }
    	            model1.setColumnIdentifiers(columnNames);

    	            while (rs.next()) {
    	                Vector<Object> rowData = new Vector<>();
    	                for (int i = 1; i <= columnCount; i++) {
    	                    rowData.add(rs.getObject(i));
    	                }
    	                model1.addRow(rowData);
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