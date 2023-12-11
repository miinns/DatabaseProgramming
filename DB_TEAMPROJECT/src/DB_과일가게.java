import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import oracle.jdbc.OracleTypes;

public class DB_과일가게 {
	   Connection con = null;
	   String url = "jdbc:oracle:thin:@localhost:1521:XE";
	   String id = "fruit";      String password = "1234";
	   public DB_과일가게() {
		     try {
		    	Class.forName("oracle.jdbc.driver.OracleDriver");
		        System.out.println("드라이버 적재 성공");
		     } catch (ClassNotFoundException e) { System.out.println("No Driver."); }  
		   }
		   private void DB_Connect() {
		     try {
		         con = DriverManager.getConnection(url, id, password);
		         System.out.println("DB 연결 성공");
		     } catch (SQLException e) {         System.out.println("Connection Fail");      }
		   }
		   private void sqlRun() throws SQLException{   // 단순 검색
			    String query = "select 과일아이디, 과일이름, 수량, 가격, 보존기간, 업체번호, 예약번호 from 과일";

			    try { DB_Connect();
			    	  Statement stmt = con.createStatement();
			          ResultSet rs = stmt.executeQuery(query);
			          while (rs.next()) {
			              System.out.print("\t" + rs.getString("과일아이디"));
			              System.out.print("\t" + rs.getString("과일이름"));
			              System.out.print("\t" + rs.getString("수량"));
			              System.out.print("\t" + rs.getString("가격"));
			              System.out.print("\t" + rs.getString("보존기간"));
			              System.out.print("\t" + rs.getString("업체번호"));
			              System.out.print("\t" + rs.getString("예약번호"));
			              System.out.print("\t" + rs.getInt(3) + "\n");         
			           }
			          stmt.close();    rs.close();
			    } catch (SQLException e) { e.printStackTrace(); 
			    } finally {   con.close(); }
			    
			   }
		   
	public static void main(String arg[]) throws SQLException {
	       DB_과일가게 dbconquery = new DB_과일가게();
	       dbconquery.sqlRun();
	}
}
