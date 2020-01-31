package pkg.beans.nrf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Trend {
	private String name;
	private String value;
	
	public Trend() {}
	
	public Trend(String name, String value) {
		this.name=name;
		this.value=value;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	public static ArrayList<Trend> getTrend(Connection conn, String year, String month, String country, 
			String continent, String area) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Trend> t = null;
		
		try {
			t = new ArrayList<>();
			sql = "select * from demo.total(?,?,?,?,?) ";
			st = conn.prepareStatement(sql);
			
			if(year==null)
				st.setNull(1, java.sql.Types.INTEGER);
			else
				st.setInt(1, Integer.parseInt(year));
			
			if(month==null)
				st.setNull(2, java.sql.Types.INTEGER);
			else
				st.setInt(2, Integer.parseInt(month));
			
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
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				t.add(new Trend(
						rs.getString("_tipo"),
						rs.getString("_val")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return t;
		
	}
	
}
