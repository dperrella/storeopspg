package pkg.disp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Account_details {
	private int id_file;
	private String account_id;
	private String store_id;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Account_details() {}

	public Account_details(int id_file, String account_id, String store_id, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_file=id_file;
		this.account_id=account_id;
		this.store_id=store_id;
		
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
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getStore_id() {
		return store_id;
	}
	public void setStore_id(String store_id) {
		this.store_id = store_id;
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
	
	public List<Account_details> selectAll(Connection conn, String q) {
		
		List<Account_details> tab = new ArrayList<Account_details>();
		tab.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from account_details where id_file = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_file);
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				tab.add(new Account_details(rs.getInt("id_file"),rs.getString("account_id"),	rs.getString("store_id"),""+count,"","",""));
				
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
			
			sql = "select count(*) num from account_details where id_file = ? ";
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
