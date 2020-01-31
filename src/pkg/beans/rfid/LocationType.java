package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LocationType implements CRUD {
	private int idLocationtype;
	private String description;
	private int idCustomer;
	
	public LocationType() {	}

	public LocationType(int idLocationtype, String description, int idCustomer) {
		this.idLocationtype = idLocationtype;
		this.description = description;
		this.idCustomer = idCustomer;
	}
	
	public int getIdLocationtype() {
		return idLocationtype;
	}
	
	public void setIdLocationtype(int idLocationtype) {
		this.idLocationtype = idLocationtype;
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
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from location_type where id_locationtype = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idLocationtype);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
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
		int nrDoctype = 0;
		try {
			sql = "select count(*) nr from location_type where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				nrDoctype = rs.getInt("nr");
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
		return nrDoctype;
	}
	
	public static ArrayList<LocationType> getLocationtypeByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<LocationType> loctypeList = null;
		try {
			loctypeList = new ArrayList<>();
			sql = "select * from location_type where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				loctypeList.add(new LocationType(rs.getInt("id_locationtype"), rs.getString("description"), rs.getInt("id_customer")));
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
		  String sql = "update location_type set description = ?, id_customer = ? where id_locationtype = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.idCustomer);
	      st.setInt(3, this.idLocationtype);
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
			String sql = " insert into location_type (description, id_customer)" + 
					" values (?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
	        st.setInt(2, this.idCustomer);
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
		  String sql = "delete from location_type where id_locationtype = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idLocationtype);
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
