package pkg.utility;

import java.sql.*;

public class CreaBadge {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		crea();
	}
	
	private static void crea() {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			for(int i=1;i<410;i++) {
				
				String nr = "00000000000"+i;
				nr=nr.substring(nr.length()-11,nr.length());
				nr="1"+nr;		
				nr=nr+Utility.getEAN13CheckDigit(nr);
				
				sql   = "insert into hr_badge (number, id_customer) values ('"+nr+"' , 4)";
				st.executeUpdate(sql);
				
				System.out.println(nr);
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
	}

}
