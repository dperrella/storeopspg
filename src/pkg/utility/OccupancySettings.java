package pkg.utility;

import java.sql.Connection;

import pkg.beans.ccn.Stores;
import pkg.occupancy.model2.firebase.Settings;

public class OccupancySettings implements Runnable {

	private String accountId;
    public OccupancySettings(String accountId) {
    	this.accountId = accountId;
    }
	@Override
	public void run() {
		
		Connection conn = null;
		try {
			conn = Utility.getConnData();
			
			for(Integer id : Stores.getStoresByIAccountCode(conn, accountId)) {
				System.out.println("Store id: "+id);
				Settings.setSettings(conn, id);
			}
			
			
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
