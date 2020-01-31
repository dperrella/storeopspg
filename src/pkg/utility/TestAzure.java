package pkg.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import pkg.properties.ReadProperties;

public class TestAzure {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement st = null;
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = "ckmanage@smartpgsrv";
			String psw = "Aprtssm$1234";
			String url = "smartpgsrv.postgres.database.azure.com";
			String drv = prop.get("drv");
			String database  = "postgres";
			
			//System.out.println(url+database+","+usr+","+psw);
			
			Class.forName(drv);
			conn = DriverManager.getConnection("jdbc:postgresql://"+url+":5432/"+database+"?user="+usr+"&password="+psw+"&ssl=true");
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
	}

}
