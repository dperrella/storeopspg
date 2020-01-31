package pkg.disp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dim_store_hours {
	private int id_file;
	private String closing_hour;
	private int day_of_week;
	private String opening_hour;
	private int store_hours_id;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Dim_store_hours() {}

	public Dim_store_hours(int id_file, String closing_hour, int day_of_week, 
			String opening_hour, int store_hours_id, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_file=id_file;
		this.closing_hour=closing_hour;
		this.day_of_week=day_of_week;
		this.opening_hour=opening_hour;
		this.store_hours_id=store_hours_id;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getId_file() {
		return id_file;
	}
	public void setId_file(int id_file) {
		this.id_file = id_file;
	}
	
	public String getClosing_hour() {
		return closing_hour;
	}
	public void setClosing_hour(String closing_hour) {
		this.closing_hour = closing_hour;
	}
	
	public int getDay_of_week() {
		return day_of_week;
	}
	public void setDay_of_week(int day_of_week) {
		this.day_of_week = day_of_week;
	}
	
	public String getOpening_hour() {
		return opening_hour;
	}
	public void setOpening_hour(String opening_hour) {
		this.opening_hour = opening_hour;
	}
	
	public int getStore_hours_id() {
		return store_hours_id;
	}
	public void setStore_hours_id(int store_hours_id) {
		this.store_hours_id = store_hours_id;
	}
	
	public String getCmp1() {
		return cmp1;
	}
	public void setCmp1(String cmp1) {
		this.cmp1 = cmp1;
	}
	
	public String getCmp2() {
		return cmp2;
	}
	public void setCmp2(String cmp2) {
		this.cmp2 = cmp2;
	}
	
	public String getCmp3() {
		return cmp3;
	}
	public void setCmp3(String cmp3) {
		this.cmp3 = cmp3;
	}
	
	public String getCmp4() {
		return cmp4;
	}
	public void setCmp4(String cmp4) {
		this.cmp4 = cmp4;
	}
	
	public List<Dim_store_hours> selectAll(Connection conn, String q) {
		
		List<Dim_store_hours> tab = new ArrayList<Dim_store_hours>();
		tab.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from dim_store_hours where id_file = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_file);
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				tab.add(new Dim_store_hours(rs.getInt("id_file"),rs.getString("closing_hour"),rs.getInt("day_of_week"),
						rs.getString("opening_hour"),rs.getInt("store_hours_id"),
						""+count,"","",""));
				
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
		
		return tab;
		
	}
	
	public int getRowNum(Connection conn) {
		
		int rowNum = 0;
		

		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select count(*) num from dim_store_hours where id_file = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_file);
			rs = st.executeQuery();
			while(rs.next()) {
				
				rowNum=rs.getInt("num");
				
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
		
		return rowNum;
		
	}
}
