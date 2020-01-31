package pkg.disp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dim_kpi_type {
	private int id_file;
	private String kpi_description;
	private String kpi_name;
	private int kpi_type_id;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Dim_kpi_type() {}

	public Dim_kpi_type(int id_file, String kpi_description, String kpi_name, int kpi_type_id, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_file=id_file;
		this.kpi_description=kpi_description;
		this.kpi_name=kpi_name;
		this.kpi_type_id=kpi_type_id;
		
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
	
	public String getKpi_description() {
		return kpi_description;
	}
	public void setKpi_description(String kpi_description) {
		this.kpi_description = kpi_description;
	}
	
	public String getKpi_name() {
		return kpi_name;
	}
	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
	}
	
	public int getKpi_type_id() {
		return kpi_type_id;
	}
	public void setKpi_type_id(int kpi_type_id) {
		this.kpi_type_id = kpi_type_id;
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
	
	public List<Dim_kpi_type> selectAll(Connection conn, String q) {
		
		List<Dim_kpi_type> tab = new ArrayList<Dim_kpi_type>();
		tab.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from dim_kpi_type where id_file = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_file);
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				tab.add(new Dim_kpi_type(rs.getInt("id_file"),rs.getString("kpi_description"),rs.getString("kpi_name"),rs.getInt("kpi_type_id"),""+count,"","",""));
				
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
			
			sql = "select count(*) num from dim_kpi_type where id_file = ? ";
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
