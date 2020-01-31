package pkg.beans.licence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MobileApp implements CRUD {
	private int id;
	private String name;
	private String description;
	
	public MobileApp() {	}

	public MobileApp(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from zmobile_app "
					+ "where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				setName(rs.getString("name"));				
				setDescription(rs.getString("description"));
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
	
	public static ArrayList<MobileApp> getApps(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<MobileApp> appList = null;
		try {
			appList = new ArrayList<>();
			sql = "select * from zmobile_app ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				appList.add(new MobileApp(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
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
		return appList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update zmobile_app set name = ?, descriptiopn = ? where id = ? ";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(2, this.name);
	      st.setString(3, this.description);
	      st.setInt(4, this.id);
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
			String sql = " insert into zmobile_app (id, name, description)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.id);
			st.setString(2, this.name);
			st.setString(3, this.description);
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
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from zmobile_app where id = ? ";
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
