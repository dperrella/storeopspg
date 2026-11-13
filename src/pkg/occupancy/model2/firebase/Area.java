package pkg.occupancy.model2.firebase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pkg.occupancy.model2.firebase.Area;

public class Area {
	
	private int id;
	private String description;
	private int warningPercentage;
	private int threshold;
	private boolean subarea;
	private boolean zone;
	private boolean ml;
	private boolean sm;
	private boolean kiosk;
	private int idStore;
	
	
	private String thresholdLastUpdate;
	private String activityLastUpdate;
	
	
	
	
	public Area() {}
	public Area(int id, String description, int warningPercentage, int threshold, 
			boolean subarea, boolean zone, boolean ml, boolean sm, boolean kiosk, int idStore) {
		this.id=id;
		this.description=description;
		this.warningPercentage=warningPercentage;
		this.threshold=threshold;
		this.subarea=subarea;
		this.zone=zone;
		this.ml=ml;
		this.sm=sm;
		this.kiosk=kiosk;
		this.idStore=idStore;
	}
	
	public Area(int id, String description, int warningPercentage, int threshold, 
			boolean subarea, boolean zone, boolean ml, boolean sm, boolean kiosk, int idStore, String thresholdLastUpdate, String activityLastUpdate) {
		this.id=id;
		this.description=description;
		this.warningPercentage=warningPercentage;
		this.threshold=threshold;
		this.subarea=subarea;
		this.zone=zone;
		this.ml=ml;
		this.sm=sm;
		this.kiosk=kiosk;
		this.idStore=idStore;
		this.thresholdLastUpdate=thresholdLastUpdate;
		this.activityLastUpdate=activityLastUpdate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getWarningPercentage() {
		return warningPercentage;
	}
	public void setWarningPercentage(int warningPercentage) {
		this.warningPercentage = warningPercentage;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public boolean isSubarea() {
		return subarea;
	}
	public void setSubarea(boolean subarea) {
		this.subarea = subarea;
	}
	public boolean isZone() {
		return zone;
	}
	public void setZone(boolean zone) {
		this.zone = zone;
	}
	public boolean isMl() {
		return ml;
	}
	public void setMl(boolean ml) {
		this.ml = ml;
	}
	public boolean isSm() {
		return sm;
	}
	public void setSm(boolean sm) {
		this.sm = sm;
	}
	public boolean isKiosk() {
		return kiosk;
	}
	public void setKiosk(boolean kiosk) {
		this.kiosk = kiosk;
	}
	public int getIdStore() {
		return idStore;
	}
	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}
	
	
	
	public String getThresholdLastUpdate() {
		return thresholdLastUpdate;
	}
	public void setThresholdLastUpdate(String thresholdLastUpdate) {
		this.thresholdLastUpdate = thresholdLastUpdate;
	}
	
	public String getActivityLastUpdate() {
		return activityLastUpdate;
	}
	public void setActivityLastUpdate(String activityLastUpdate) {
		this.activityLastUpdate = activityLastUpdate;
	}
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " update occ2_storearee set description = ?, perc_warning = ?, threshold = ? "
					+ "where id = ? and id_store = ? ";
			st = conn.prepareStatement(sql);
			
		    st.setString(1, this.description);
			st.setInt(2, this.warningPercentage);
			st.setInt(3, this.threshold);
			st.setInt(4, this.id);
			st.setInt(5, this.idStore);
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

	
	public static ArrayList<Area> getAree(Connection conn, int idStore) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Area> list = null;
		try {
			list = new ArrayList<>();
			
			sql = "select occ2_storearee.* , (select last_activity "
					+ "from occupancy_last_activity where occupancy_last_activity.id_store = occ2_storearee.id_store limit 1) activity_lastupdate "
					+ "from occ2_storearee where id_store = ? order by id ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				list.add(new Area(rs.getInt("id"), rs.getString("description"),
						rs.getInt("perc_warning"), rs.getInt("threshold"), rs.getBoolean("subarea"),
						rs.getBoolean("zone"), rs.getBoolean("ml"), rs.getBoolean("sm"), rs.getBoolean("kiosk"),
						idStore, rs.getString("threshold_lastupdate"), rs.getString("activity_lastupdate")));
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
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from occ2_storearee where id = ? and id_store = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.id);
			st.setInt(2, this.idStore);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setIdStore(rs.getInt("id_store"));
				setWarningPercentage(rs.getInt("perc_warning"));
				setThreshold(rs.getInt("threshold"));
				setId(rs.getInt("id"));
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
	
}
