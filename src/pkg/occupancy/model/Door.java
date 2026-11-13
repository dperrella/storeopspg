package pkg.occupancy.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Door {

	private int idCustomer;
	private String accountId;
	private String doorDescription;
	private int doorTypeId;
	private int aisleType;
	private boolean enabled;
	private boolean imported;
	
	public Door() {}
	
	public Door(int idCustomer, String accountId, String doorDescription, int doorTypeId, int aisleType, boolean enabled, boolean imported) {
		this.idCustomer=idCustomer;
		this.accountId=accountId;
		this.doorDescription=doorDescription;
		this.doorTypeId=doorTypeId;
		this.aisleType=aisleType;
		this.enabled=enabled;
		this.imported=imported;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDoorDescription() {
		return doorDescription;
	}

	public void setDoorDescription(String doorDescription) {
		this.doorDescription = doorDescription;
	}

	public int getDoorTypeId() {
		return doorTypeId;
	}

	public void setDoorTypeId(int doorTypeId) {
		this.doorTypeId = doorTypeId;
	}

	public int getAisleType() {
		return aisleType;
	}

	public void setAisleType(int aisleType) {
		this.aisleType = aisleType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}
	
	public static ArrayList<Door> getDoors(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Door> list = null;
		try {
			list = new ArrayList<>();
			sql = "select * from occ_doors ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				list.add(new Door(rs.getInt("id_customer"), rs.getString("account_id"),
						rs.getString("door_description"), rs.getInt("door_type_id"), rs.getInt("aisle_type"),
						rs.getBoolean("enabled"), rs.getBoolean("imported")));
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
