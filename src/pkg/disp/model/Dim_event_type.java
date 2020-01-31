package pkg.disp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dim_event_type {
	private int id_file;
	private String event_description;
	private int event_type_id;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Dim_event_type() {}

	public Dim_event_type(int id_file, String event_description, int event_type_id, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_file=id_file;
		this.event_description=event_description;
		this.event_type_id=event_type_id;
		
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
	
	public String getEvent_description() {
		return event_description;
	}
	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	
	public int getEvent_type_id() {
		return event_type_id;
	}
	public void setEvent_type_id(int event_type_id) {
		this.event_type_id = event_type_id;
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
	
	public List<Dim_event_type> selectAll(Connection conn, String q) {
		
		List<Dim_event_type> tab = new ArrayList<Dim_event_type>();
		tab.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from dim_event_type where id_file = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_file);
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				tab.add(new Dim_event_type(rs.getInt("id_file"),rs.getString("event_description"),rs.getInt("event_type_id"),""+count,"","",""));
				
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
			
			sql = "select count(*) num from dim_event_type where id_file = ? ";
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
