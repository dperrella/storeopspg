package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Wishlist implements CRUD {
	private int idWishlist;
	private String name;
	
	public Wishlist() {	}

	public Wishlist(int idWishlist, String name) {
		this.idWishlist = idWishlist;
		this.name = name;
	}
	
	public int getIdWishlist() {
		return idWishlist;
	}
	
	public void setIdWishlist(int idWishlist) {
		this.idWishlist = idWishlist;
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
			sql = "select * from ats.wishlist where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idWishlist);
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
	
	public static ArrayList<Wishlist> getWishlist(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Wishlist> wishList = null;
		try {
			wishList = new ArrayList<>();
			sql = "select * from ats.wishlist ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				wishList.add(new Wishlist(rs.getInt("id"), rs.getString("name")));
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
		return wishList;
	}
	
	public static String getWishlistNameById(Connection conn, int id){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		String name = "";
		try {
			
			sql = "select name from ats.wishlist where id = ? ";
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
		  String sql = "update ats.wishlist set name = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setInt(2, this.idWishlist);
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
			String sql = " insert into ats.wishlist (name)" + 
					" values (?) returning id ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
	        // execute the java preparedstatement
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				this.setIdWishlist(rs.getInt("id"));
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
			
		  String sql = "";
		  sql = "update ats.items set idwishlist = -1 where idwishlist = ? ";
		  st = conn.prepareStatement(sql);
	      st.setInt(1, this.idWishlist);
	      st.executeUpdate();
	      st.close();
		  
		  sql = "delete from ats.wishlist where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idWishlist);
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
