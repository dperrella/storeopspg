package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Category implements CRUD {
	private int idCategory;
	private String name;
	
	public Category() {	}

	public Category(int idCategory, String name) {
		this.idCategory = idCategory;
		this.name = name;
	}
	
	public int getIdCategory() {
		return idCategory;
	}
	
	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
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
			sql = "select * from ats.category where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCategory);
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
	
	public static ArrayList<Category> getCategory(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Category> categoryList = null;
		try {
			categoryList = new ArrayList<>();
			sql = "select * from ats.category ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				categoryList.add(new Category(rs.getInt("id"), rs.getString("name")));
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
		return categoryList;
	}
	
	public static String getCategoryNameById(Connection conn, int id){
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
		  String sql = "update ats.category set name = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setInt(2, this.idCategory);
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
			String sql = " insert into ats.category (name)" + 
					" values (?) returning id ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
	        // execute the java preparedstatement
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				this.setIdCategory(rs.getInt("id"));
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
		  String sql = "delete from ats.category where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idCategory);
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
