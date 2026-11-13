package pkg.occupancy.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pkg.utility.Utility;

/**
 * @author danieleperrella
 *
 */
public class StoresSettings {
	private int idStore;
	private String hhopen;
	private String hhclose;
	
	public StoresSettings() {}
	
	public StoresSettings(int idStore, String hhopen, String hhclose) {
		this.idStore=idStore;
		this.hhopen=hhopen;
		this.hhclose=hhclose;
	}
	
	

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public String getHhopen() {
		return hhopen;
	}

	public void setHhopen(String hhopen) {
		this.hhopen = hhopen;
	}

	public String getHhclose() {
		return hhclose;
	}

	public void setHhclose(String hhclose) {
		this.hhclose = hhclose;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from occ_store_settings where idstore = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			boolean exists = true;
			if(rs.next()) {
				setHhopen(rs.getString("hhopen"));
				setHhclose(rs.getString("hhclose"));
			}
			else {
				exists = false;
			}
			rs.close();
			st.close();
			
			if(!exists) {
				
				sql = "insert into occ_store_settings (idstore, hhopen, hhclose)" + 
						" values (?, ?, ?)";
				st = conn.prepareStatement(sql);
				st.setInt(1, this.idStore);
			    st.setString(2, "09:00:00");
			    st.setString(3, "21:00:00");
			    // execute the java preparedstatement
				st.executeUpdate();
				
				setHhopen("09:00:00");
				setHhclose("21:00:00");
			}
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
	
	
	
	public static ArrayList<StoresSettings> getAree(Connection conn, int idStore) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<StoresSettings> list = null;
		try {
			list = new ArrayList<>();
			
			sql = "select * from occ_store_settings where idstore = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				list.add(new StoresSettings(rs.getInt("idStore"), rs.getString("hhopen"), rs.getString("hhclose")));
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
		return list;
		
	}
	
	public void update(Connection conn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			
			String sql = "";
			String oldHO = "";
			String oldHC = "";
			
			sql = "select * from occ_store_settings where idstore = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idStore);
			System.out.println(st.toString());
			rs = st.executeQuery();
			if(rs.next()) {
				oldHO = rs.getString("hhopen");
				oldHC = rs.getString("hhclose");
			}
			rs.close();
			st.close();
			
			String ts = "";
			if(!oldHO.equals(this.hhopen))
				ts+=", hhopen_lastupdate = now() ";
			if(!oldHC.equals(this.hhclose))
				ts+=", hhclose_lastupdate = now() ";
			
			
			sql = "update occ_store_settings set hhopen = ?, hhclose = ? "+ts+" "
			  		+ "where idstore = ? ";
		    st = conn.prepareStatement(sql);
		    st.setString(1, this.hhopen);
		    st.setString(2, this.hhclose);
		    st.setInt(3, this.idStore);
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
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = "insert into occ_store_settings (idstore, hhopen, hhclose)" + 
					" values (?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idStore);
		    st.setString(2, this.hhopen);
		    st.setString(3, this.hhclose);
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

	
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from occ_store_settings where idstore = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idStore);
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
	