import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DB_과일가게_GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 * @return 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DB_과일가게_GUI frame = new DB_과일가게_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	 String url = "jdbc:oracle:thin:@localhost:1521:XE";
	 String id = "fruit";      String password = "1234";

	/**
	 * Create the frame.
	 */
	public DB_과일가게_GUI() {
		setTitle("과일가게");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 867, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("과일");
		btnNewButton.setBounds(347, 63, 119, 38);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        DB_과일가게_과일 fruitWindow = new DB_과일가게_과일();
		        fruitWindow.setVisible(true);
		        setVisible(false);
		    }
		});
		
		JButton btnNewButton_1 = new JButton("손님 조회");
		btnNewButton_1.setBounds(347, 128, 119, 38);
		contentPane.add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DB_과일가게_손님 customerWindow = new DB_과일가게_손님();
                customerWindow.setVisible(true);
                setVisible(false);
            }
        });
        
		JButton btnNewButton_2 = new JButton("예약");
		btnNewButton_2.setBounds(347, 193, 119, 38);;
		contentPane.add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DB_과일가게_예약 reservationWindow = new DB_과일가게_예약();
                reservationWindow.setVisible(true);
                setVisible(false);
            }
		});
		
		JButton btnNewButton_3 = new JButton("손상과일");
		btnNewButton_3.setBounds(347, 261, 119, 38);
		contentPane.add(btnNewButton_3);
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DB_과일가게_손상과일 damagedfruitWindow = new DB_과일가게_손상과일();
                damagedfruitWindow.setVisible(true);
                setVisible(false);
            }
		});
        
		JButton btnNewButton_4 = new JButton("납품업체 조회");
		btnNewButton_4.setBounds(347, 325, 119, 38);
		contentPane.add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DB_과일가게_납품업체 supplierWindow = new DB_과일가게_납품업체();
                supplierWindow.setVisible(true);
                setVisible(false);
                }
		});
        
		JButton btnNewButton_5 = new JButton("매출통계");
		btnNewButton_5.setBounds(347, 395, 119, 38);
		contentPane.add(btnNewButton_5);
		 btnNewButton_5.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                DB_과일가게_매출 salesWindow = new DB_과일가게_매출();
	                salesWindow.setVisible(true);
	                setVisible(false);
	                }
			});
	}
}

