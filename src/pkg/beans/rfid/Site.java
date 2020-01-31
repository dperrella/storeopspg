package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Site implements CRUD {
	private int idSite;
	private String description;
	private int idCustomer;
	private boolean automatic;
	private String warehouseCode;
	
	public Site() {	}

	public Site(int idSite, String description, int idCustomer, boolean automatic, String warehouseCode) {
		this.idSite = idSite;
		this.description = description;
		this.idCustomer = idCustomer;
		this.automatic = automatic;
		this.warehouseCode = warehouseCode;
	}
	
	public int getIdSite() {
		return idSite;
	}
	
	public void setIdSite(int idSite) {
		this.idSite = idSite;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from sites where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idSite);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setIdCustomer(rs.getInt("id_customer"));
				setAutomatic(rs.getBoolean("automatic"));
				setWarehouseCode(rs.getString("warehouse_code"));
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
	
	public static int getNrSitesByIdCustomer(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int nrSites = 0;
		try {
			sql = "select count(*) nr from sites where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				nrSites = rs.getInt("nr");
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
		return nrSites;
	}
	
	public static ArrayList<Site> getSitesByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Site> siteList = null;
		try {
			siteList = new ArrayList<>();
			sql = "select * from sites where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				siteList.add(new Site(rs.getInt("id"), rs.getString("description"), 
						rs.getInt("id_customer"), rs.getBoolean("automatic"), rs.getString("warehouse_code")));
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
		return siteList;
	}
	
	public static String getSiteDescriptionById(Connection conn, int id){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		String description = "";
		try {
			
			sql = "select description from sites where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				description = rs.getString("description");
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
		return description;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update sites set description = ?, id_customer = ? , automatic = ?, warehouse_code = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.idCustomer);
	      st.setBoolean(3, this.automatic);
	      st.setString(4, this.warehouseCode);
	      st.setInt(5, this.idSite);
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
			String sql = " insert into sites (description, id_customer, automatic, warehouse_code)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
	        st.setInt(2, this.idCustomer);
	        st.setBoolean(3, this.automatic);
	        st.setString(4, this.warehouseCode);
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
		  String sql = "delete from sites where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idSite);
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
