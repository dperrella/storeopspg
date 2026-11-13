package pkg.occupancy.model2.firebase;

import java.sql.Connection;

import pkg.occupancy.model2.firebase.Settings;
import pkg.utility.Utility;

public class FirebaseSettings implements Runnable{
	
	private int idStore;
	
	public FirebaseSettings(int idStore) {
		this.idStore=idStore;
	}
	
	@Override
	public void run() {
		
		Connection conn = null;
		try {
			
			conn = Utility.getConnData();
			Settings.setSettings(conn, idStore);
			
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
	