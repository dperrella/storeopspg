package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Photo implements CRUD {
	private int id;
	private int idItem;
	private String url;
	private String base;
	
	
	public Photo() {	}

	public Photo(int id, int idItem, String url, String base) {
		this.id = id;
		this.idItem = idItem;
		this.url = url;
		this.base = base;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdItem() {
		return idItem;
	}
	
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from ats.photo where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				setIdItem(rs.getInt("iditem"));
				setUrl(rs.getString("url"));	
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
	
	public static ArrayList<Photo> getPhotoByItem(Connection conn, int idItem){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Photo> photoList = null;
		try {
			photoList = new ArrayList<>();
			sql = "select * from ats.photo where iditem = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idItem);
			rs = st.executeQuery();
			while(rs.next()) {
				photoList.add(new Photo(rs.getInt("id"), rs.getInt("iditem"),rs.getString("url"),""));
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
		return photoList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.photo set url = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.url);
	      st.setInt(2, this.id);
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
			String sql = " insert into ats.photo (iditem, url)" + 
					" values (?, ?)";
			
			
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idItem);
			st.setString(2, this.url);
	        // execute the java preparedstatement
			System.out.println(st.toString());
			
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
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from ats.photo where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.id);
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
