package pkg.beans.nrf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Linechart {
	private String name;
	private int index;
	private long value;
	private int serie;
	
	
	public Linechart() {}
	
	public Linechart(String name, int index, long value, int serie) {
		this.name=name;
		this.index=index;
		this.value=value;
		this.serie=serie;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public int getSerie() {
		return serie;
	}

	public void setSerie(int serie) {
		this.serie = serie;
	}
	
	public static ArrayList<Linechart> getLinechart(Connection conn, String year, String month, String country, 
			String continent, String area, String kpi) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Linechart> l = null;
		
		try {
			l = new ArrayList<>();
			sql = "select * from demo.linechart(?,?,?,?,?,?) ";
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
				
				l.add(new Linechart(
						rs.getString("_tipo"),
						rs.getInt("indice"),
						rs.getLong("_val"),
						rs.getInt("serie")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return l;
		
	}
	
}
