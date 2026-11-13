package pkg.occupancy.model2.firebase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DailyOperationStore {

	private int day;
	private String fromhour;
	private String tohour;
	private boolean h24;
	private int idStore;
	
	public DailyOperationStore() {}
	public DailyOperationStore(int day, String fromhour, String tohour, boolean h24, int idStore) {
		this.day=day;
		this.fromhour=fromhour;
		this.tohour=tohour;
		this.h24=h24;
		this.idStore=idStore;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getFromhour() {
		return fromhour;
	}
	public void setFromhour(String fromhour) {
		this.fromhour = fromhour;
	}
	public String getTohour() {
		return tohour;
	}
	public void setTohour(String tohour) {
		this.tohour = tohour;
	}
	public boolean isH24() {
		return h24;
	}
	public void setH24(boolean h24) {
		this.h24 = h24;
	}
	public int getIdStore() {
		return idStore;
	}
	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}
	
	public static ArrayList<DailyOperationStore> getOpeningHoursPure(Connection conn, int idStore) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		ArrayList<DailyOperationStore> list = null;
		
		try {
			list = new ArrayList<>();
			
			sql = "select * from dailyoperation_stores where id_stores = ? order by days ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			
			while(rs.next()) {
			
				
				list.add(new DailyOperationStore(rs.getInt("days"), rs.getString("fromhour"),
						rs.getString("tohour"), false,	idStore));
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}	
		return list;
		
	}
	
	
	
}
