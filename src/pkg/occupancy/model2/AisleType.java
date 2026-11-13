package pkg.occupancy.model2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AisleType {

	private int id;
	private String description;
	private boolean isEntrance;
	
	public AisleType() {}
	
	public AisleType(int id, String description, boolean isEntrance) {
		this.id=id;
		this.description=description;
		this.isEntrance=isEntrance;
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

	public boolean isEntrance() {
		return isEntrance;
	}

	public void setEntrance(boolean isEntrance) {
		this.isEntrance = isEntrance;
	}
	
	public static ArrayList<AisleType> getAisleType(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<AisleType> list = null;
		try {
			list = new ArrayList<>();
			sql = "select * from _aisle_type ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				list.add(new AisleType(rs.getInt("id"), rs.getString("description"), 
						rs.getBoolean("is_entrance")));
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
	
}
