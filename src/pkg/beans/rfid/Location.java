package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Location implements CRUD {
	private int idLocation;
	private String description;
	private int idSite;
	private int idLocationtype;
	private int idCustomer;
	
	public Location() {	}

	public Location(int idLocation, String description, int idSite, int idLocationtype, int idCustomer) {
		this.idLocation = idLocation;
		this.description = description;
		this.idSite = idSite;
		this.idLocationtype = idLocationtype;
		this.idCustomer = idCustomer;
	}
	
	
	public int getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(int idLocation) {
		this.idLocation = idLocation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdSite() {
		return idSite;
	}

	public void setIdSite(int idSite) {
		this.idSite = idSite;
	}

	public int getIdLocationtype() {
		return idLocationtype;
	}

	public void setIdLocationtype(int idLocationtype) {
		this.idLocationtype = idLocationtype;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from location where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idLocation);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setIdSite(rs.getInt("idsite"));
				setIdLocationtype(rs.getInt("id_locationtype"));
				setIdCustomer(rs.getInt("id_customer"));
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
	
	public static int getNrLocationtypeByIdCustomer(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int nrLocation = 0;
		try {
			sql = "select count(*) nr from location where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				nrLocation = rs.getInt("nr");
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
		return nrLocation;
	}
	
	public static ArrayList<Location> getLocationByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Location> loctypeList = null;
		try {
			loctypeList = new ArrayList<>();
			sql = "select * from location where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				loctypeList.add(new Location(rs.getInt("id"), rs.getString("description"), 
						rs.getInt("idsite"), rs.getInt("id_locationtype"), rs.getInt("id_customer")));
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
		return loctypeList;
	}
	
	public static ArrayList<Location> getLocationByIdsite(Connection conn, int idSite){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Location> loctypeList = null;
		try {
			loctypeList = new ArrayList<>();
			sql = "select * from location where idsite = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idSite);
			rs = st.executeQuery();
			while(rs.next()) {
				loctypeList.add(new Location(rs.getInt("id"), rs.getString("description"), 
						rs.getInt("idsite"), rs.getInt("id_locationtype"), rs.getInt("id_customer")));
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
		return loctypeList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update location set description = ?, idsite = ?, "
		  		+ "id_locationtype = ?, id_customer = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.idSite);
	      st.setInt(3, this.idLocationtype);
	      st.setInt(4, this.idCustomer);
	      st.setInt(5, this.idLocation);
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
			String sql = " insert into location (description, idsite, id_locationtype, id_customer)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
			st.setInt(2, this.idSite);
			st.setInt(3, this.idLocationtype);
			st.setInt(4, this.idCustomer);
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
		  String sql = "delete from location where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idLocation);
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
