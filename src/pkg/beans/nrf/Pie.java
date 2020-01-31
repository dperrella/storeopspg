package pkg.beans.nrf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Pie {
	private String name;
	private long value;
	
	public Pie() {}
	
	public Pie(String name, long value) {
		this.name=name;
		this.value=value;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public static ArrayList<Pie> getPie(Connection conn, String year, String month, String country, 
			String continent, String area, String kpi) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Pie> p = null;
		
		try {
			p = new ArrayList<>();
			sql = "select * from demo.pie(?,?,?,?,?,?) ";
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
			
			if(kpi==null)
				st.setNull(6, java.sql.Types.VARCHAR);
			else
				st.setString(6, kpi);
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				p.add(new Pie(
						rs.getString("_nome"),
						rs.getLong("_val")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return p;
		
	}
	
}
