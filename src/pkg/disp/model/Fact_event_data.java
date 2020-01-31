package pkg.disp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Fact_event_data {
	private int id_file;
	private int door_type_id;
	private String event_date;
	private int event_hour;
	private int event_type_id;
	private int event_value;
	private int id;
	private int reason_code_id;
	private int response_user_id;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Fact_event_data() {}

	public Fact_event_data(int id_file, int door_type_id, String event_date, 
			int event_hour,int event_type_id,int event_value,int id,int reason_code_id,int response_user_id,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_file=id_file;
		this.door_type_id=door_type_id;
		this.event_date=event_date;
		this.event_hour=event_hour;
		this.event_type_id=event_type_id;
		this.event_value=event_value;
		this.id=id;
		this.reason_code_id=reason_code_id;
		this.response_user_id=response_user_id;
		
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
	
	public int getDoor_type_id() {
		return door_type_id;
	}
	public void setDoor_type_id(int door_type_id) {
		this.door_type_id = door_type_id;
	}
	
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	
	public int getEvent_hour() {
		return event_hour;
	}
	public void setEvent_hour(int event_hour) {
		this.event_hour = event_hour;
	}
	
	public int getEvent_type_id() {
		return event_type_id;
	}
	public void setEvent_type_id(int event_type_id) {
		this.event_type_id = event_type_id;
	}
	
	public int getEvent_value() {
		return event_value;
	}
	public void setEvent_value(int event_value) {
		this.event_value = event_value;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getReason_code_id() {
		return reason_code_id;
	}
	public void setReason_code_id(int reason_code_id) {
		this.reason_code_id = reason_code_id;
	}
	
	public int getResponse_user_id() {
		return response_user_id;
	}
	public void setResponse_user_id(int response_user_id) {
		this.response_user_id = response_user_id;
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
	
	public List<Fact_event_data> selectAll(Connection conn, String q) {
		
		List<Fact_event_data> tab = new ArrayList<Fact_event_data>();
		tab.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from fact_event_data where id_file = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_file);
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				tab.add(new Fact_event_data(rs.getInt("id_file"),rs.getInt("door_type_id"),rs.getString("event_date"),
						rs.getInt("event_hour"),rs.getInt("event_type_id"),rs.getInt("event_value"),rs.getInt("id"),
						rs.getInt("reason_code_id"),rs.getInt("response_user_id"),
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
			
			sql = "select count(*) num from fact_event_data where id_file = ? ";
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
