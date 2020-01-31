package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Vendor implements CRUD {
	private int idVendor;
	private String name;
	
	public Vendor() {	}

	public Vendor(int idVendor, String name) {
		this.idVendor = idVendor;
		this.name = name;
	}
	
	public int getIdVendor() {
		return idVendor;
	}
	
	public void setIdVendor(int idVendor) {
		this.idVendor = idVendor;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from ats.vendor where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idVendor);
			rs = st.executeQuery();
			if(rs.next()) {
				setName(rs.getString("name"));	
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
	}
	
	public static ArrayList<Vendor> getVendor(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Vendor> vendorList = null;
		try {
			vendorList = new ArrayList<>();
			sql = "select * from ats.vendor ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				vendorList.add(new Vendor(rs.getInt("id"), rs.getString("name")));
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
		return vendorList;
	}
	
	public static String getVendorNameById(Connection conn, int id){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		String name = "";
		try {
			
			sql = "select name from ats.vendor where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
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
		return name;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.vendor set name = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setInt(2, this.idVendor);
	      // execute the java preparedstatement
	      st.executeUpdate();
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
	}

	
	@Override
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into ats.vendor (name)" + 
					" values (?) returning id ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
	        // execute the java preparedstatement
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				this.setIdVendor(rs.getInt("id"));
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
	}

	
	@Override
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from ats.vendor where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idVendor);
	      // execute the java preparedstatement
	      st.executeUpdate();
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
	}

	
}
