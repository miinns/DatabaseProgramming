import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Vector;

public class DB_과일가게_과일 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DB_과일가게_과일 frame = new DB_과일가게_과일();
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
	public DB_과일가게_과일() {
		setTitle("과일 확인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] {"과일아이디", "과일이름", "수량", "가격", "보존기간", "업체번호", "예약번호"});
		JTable table = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(18, 95, 900, 387);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("과일아이디");
		lblNewLabel_1.setBounds(12, 25, 76, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("과일이름");
		lblNewLabel_2.setBounds(189, 25, 60, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("수량");
		lblNewLabel_3.setBounds(348, 25, 38, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("가격");
		lblNewLabel_4.setBounds(489, 25, 31, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("보존기간");
		lblNewLabel_5.setBounds(631, 25, 62, 15);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("업체번호");
		lblNewLabel_6.setBounds(18, 67, 60, 15);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("예약번호");
		lblNewLabel_7.setBounds(189, 67, 68, 15);
		contentPane.add(lblNewLabel_7);
		
		textField = new JTextField();
		textField.setBounds(86, 22, 91, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(245, 22, 91, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(382, 22, 91, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(523, 22, 91, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(691, 22, 91, 21);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(84, 64, 91, 21);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(245, 64, 91, 21);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		JButton btnNewButton = new JButton("추가");
		btnNewButton.setBounds(810, 39, 91, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String 과일아이디 = textField.getText();
	            String 과일이름 = textField_1.getText();
	            String 수량 = textField_2.getText();
	            String 가격 = textField_3.getText();
	            String 보존기간 = textField_4.getText();
	            String 업체번호 = textField_5.getText();
	            String 예약번호 = textField_6.getText();

	            if (과일아이디.isEmpty() || 과일이름.isEmpty() || 수량.isEmpty() || 가격.isEmpty() || 보존기간.isEmpty() || 업체번호.isEmpty()) {
	                JOptionPane.showMessageDialog(contentPane, "입력해주세요", "알림", JOptionPane.WARNING_MESSAGE);
	            } else {
	                DefaultTableModel model = (DefaultTableModel) table.getModel();
	                model.addRow(new Object[] { 과일아이디, 과일이름, 수량, 가격, 보존기간, 업체번호, 예약번호 });

	                try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit", "1234")) {
	                    String query = "INSERT INTO 과일 (과일아이디, 과일이름, 수량, 가격, 보존기간, 업체번호, 예약번호) VALUES (?, ?, ?, ?, ?, ?, ?)";
	                    PreparedStatement pstmt = conn.prepareStatement(query);

	                	pstmt.setString(1, 과일아이디);
	                    pstmt.setString(2, 과일이름);
	                    pstmt.setString(3, 수량);
	                    pstmt.setString(4, 가격);
	                    pstmt.setString(5, 보존기간);
	                    pstmt.setString(6, 업체번호);
	                    pstmt.setString(7, 예약번호);
	                    pstmt.addBatch();
	                    
	                    int[] result = pstmt.executeBatch();

	                    JOptionPane.showMessageDialog(contentPane, "과일이 추가되었습니다.", "추가 완료", JOptionPane.INFORMATION_MESSAGE);

	                   } catch (SQLException ex) {
	                       ex.printStackTrace();
	                   }
	            }
		    }
		});
	            
		JButton btnNewButton_1 = new JButton("삭제");
		btnNewButton_1.setBounds(810, 66, 91, 23);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(contentPane, "삭제할 항목을 선택해주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
		        } else {
		            try {           	
		            String fruitIdToDelete = table.getValueAt(selectedRow, 0).toString();           
		            DefaultTableModel model = (DefaultTableModel) table.getModel();
		            model.removeRow(selectedRow);
		            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "fruit", "1234");
		            
		            Statement stmt = conn.createStatement();	            
		            String deleteQuery = "DELETE FROM 과일 WHERE 과일아이디 = " + fruitIdToDelete;     
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
		btnNewButton_2.setBounds(810, 12, 91, 23);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","fruit", "1234");
                    Statement stmt = conn.createStatement();

                    String query = "SELECT * FROM 과일";
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
