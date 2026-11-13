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
public class StoresAreaSettings {
	private int idStore;
	private int idArea;
	private int percWarning;
	private int threshold;
	private String description;
	
	public StoresAreaSettings() {}
	
	public StoresAreaSettings(int idStore, int idArea, int  percWarning, 
			int threshold, String description) {
		this.idStore=idStore;
		this.idArea=idArea;
		this.percWarning=percWarning;
		this.threshold=threshold;		
		this.description=description;
	}
	
	

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public int getIdArea() {
		return idArea;
	}

	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}

	public int getPercWarning() {
		return percWarning;
	}

	public void setPercWarning(int percWarning) {
		this.percWarning = percWarning;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
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
			sql = "select * from occ_store_area_settings where idstore = ? and idarea = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			st.setInt(2, idArea);
			rs = st.executeQuery();
			if(rs.next()) {
				setPercWarning(rs.getInt("perc_warning"));
				setThreshold(rs.getInt("threshold"));
				setDescription(rs.getString("area_description"));
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
	
	public static ArrayList<StoresAreaSettings> getAree(Connection conn, int idStore) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<StoresAreaSettings> list = null;
		try {
			list = new ArrayList<>();
			
			sql = "select * from occ_store_area_settings where idstore = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			System.out.println(st.toString());
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				list.add(new StoresAreaSettings(rs.getInt("idStore"), rs.getInt("idArea"), 
						rs.getInt("perc_warning"), rs.getInt("threshold"), rs.getString("area_description")));
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
			int oldPW = -1;
			int oldTH = -1;
			
			sql = "select * from occ_store_area_settings where idstore = ? and idarea = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idStore);
			st.setInt(2, this.idArea);
			rs = st.executeQuery();
			if(rs.next()) {
				oldPW = rs.getInt("perc_warning");
				oldTH = rs.getInt("threshold");
			}
			rs.close();
			st.close();
			
			String ts = "";
			if(oldPW!=this.percWarning)
				ts+=", perc_warning_lastupdate = now() ";
			if(oldTH!=this.threshold)
				ts+=", threshold_lastupdate = now() ";
			
			
			sql = "update occ_store_area_settings set perc_warning = ?,threshold = ? "+ts+" "
			  		+ "where idstore = ? and idarea = ? ";
		    st = conn.prepareStatement(sql);
		    st.setInt(1, this.percWarning);
		    st.setInt(2, this.threshold);
		    st.setInt(3, this.idStore);
		    st.setInt(4, this.idArea);
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
			String sql = " insert into occ_store_area_settings (idstore, idarea, "
					+ "perc_warning, threshold)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idStore);
		    st.setInt(2, this.idArea);
		    st.setInt(3, this.percWarning);
		    st.setInt(4, this.threshold);
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
		  String sql = "delete from occ_store_area_settings where idstore = ? and idarea = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idStore);
	      st.setInt(2, this.idArea);
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
	