package pkg.beans.nrf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Heatmap {
	private int dow;
	private int hour;
	private int value;
	
	public Heatmap() {}
	
	public Heatmap(int dow, int hour, int value) {
		this.dow=dow;
		this.hour=hour;
		this.value=value;
	}
	
	public int getDow() {
		return dow;
	}

	public void setDow(int dow) {
		this.dow = dow;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static ArrayList<Heatmap> getHeatmap(Connection conn, String from, String to, String country, 
			String continent, String area, String kpi) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Heatmap> hm = null;
		
		try {
			hm = new ArrayList<>();
			sql = "select * from demo.heatmap(?,?,?,?,?,?) ";
			st = conn.prepareStatement(sql);
			
			st.setDate(1, java.sql.Date.valueOf(from));
			st.setDate(2, java.sql.Date.valueOf(to));
			
			System.out.println(st.toString());
			
			if(continent==null)
				st.setNull(3, java.sql.Types.VARCHAR);
			else
				st.setString(3, continent);
			
			if(continent==null)
				st.setNull(4, java.sql.Types.VARCHAR);
			else
				st.setString(4, country);
			
			if(area==null)
				st.setNull(5, java.sql.Types.VARCHAR);
			else
				st.setString(5, area);
			
			if(kpi==null)
				st.setNull(6, java.sql.Types.VARCHAR);
			else
				st.setString(6, kpi);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				hm.add(new Heatmap(
						rs.getInt("_dow"),
						rs.getInt("_hour"),
						rs.getInt("_val")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return hm;
		
	}
	
}
