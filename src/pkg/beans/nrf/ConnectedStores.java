package pkg.beans.nrf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ConnectedStores {

	private int idStore;
	private String color;
	private String uid;
	private String lat;
	private String lon;
	
	public ConnectedStores() {}
	
	public ConnectedStores(int idStore, String color, String uid, String lat, String lon) {
		this.idStore=idStore;
		this.color=color;
		this.uid=uid;
		this.lat=lat;
		this.lon=lon;
	}

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}
	
	public static ArrayList<ConnectedStores> getStores(Connection conn, String country, String continent, String area) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<ConnectedStores> storesList = null;
		
		try {
			storesList = new ArrayList<>();
			sql = "select * from demo.connected_stores(? , ? , ?) ";
			st = conn.prepareStatement(sql);
			if(country==null)
				st.setNull(1, java.sql.Types.INTEGER);
			else
				st.setInt(1, Integer.parseInt(country));
			
			if(continent==null)
				st.setNull(2, java.sql.Types.VARCHAR);
			else
				st.setString(2, continent);
			
			if(area==null)
				st.setNull(3, java.sql.Types.VARCHAR);
			else
				st.setString(3, area);
			
			System.out.println(st.toString());
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				storesList.add(new ConnectedStores(
						rs.getInt("_id_store"),
						rs.getString("_color"),
						rs.getString("_guid"),
						rs.getString("_lat"),
						rs.getString("_long")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return storesList;
		
	}
	
}
