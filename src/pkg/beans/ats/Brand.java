package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Brand implements CRUD {
	private int idBrand;
	private String name;
	
	public Brand() {	}

	public Brand(int idBrand, String name) {
		this.idBrand = idBrand;
		this.name = name;
	}
	
	public int getIdBrand() {
		return idBrand;
	}
	
	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
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
			sql = "select * from ats.brand where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idBrand);
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
	
	public static ArrayList<Brand> getBrand(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Brand> brandList = null;
		try {
			brandList = new ArrayList<>();
			sql = "select * from ats.brand ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				brandList.add(new Brand(rs.getInt("id"), rs.getString("name")));
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
		return brandList;
	}
	
	public static String getBrandNameById(Connection conn, int id){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		String name = "";
		try {
			
			sql = "select name from ats.brand where id = ? ";
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
		  String sql = "update ats.brand set name = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setInt(2, this.idBrand);
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
			String sql = " insert into ats.brand (name)" + 
					" values (?) returning id ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
	        // execute the java preparedstatement
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				this.setIdBrand(rs.getInt("id"));
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
		  String sql = "delete from ats.brand where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idBrand);
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
