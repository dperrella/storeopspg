package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Client {
	private int idClient;
	private String description;
	private String macAddress;
	private boolean isEnabled;
	private int idCustomer;
	
	public Client() {}

	public Client(int idClient, String description, String macAddress, boolean isEnabled, int idCustomer) {
		this.idClient = idClient;
		this.description = description;
		this.macAddress = macAddress;
		this.isEnabled = isEnabled;
		this.idCustomer = idCustomer;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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
			sql = "select * from clients where id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, idClient);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setMacAddress(rs.getString("mac"));
				setIsEnabled(rs.getBoolean("enabled"));
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

	public static ArrayList<Client> getClientsByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Client> clientList = null;
		try {
			clientList = new ArrayList<>();
			sql = "select * from clients where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				clientList.add(new Client(
						rs.getInt("id"), 
						rs.getString("description"), 
						rs.getString("mac"),
						rs.getBoolean("enabled"), 
						rs.getInt("id_customer")));
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
		return clientList;
	}
	
	public static ArrayList<Client> getClientsByIdCustomerAndNull(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Client> clientList = null;
		try {
			clientList = new ArrayList<>();
			sql = "select * from clients where id_customer = ? or id_customer is null ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				clientList.add(new Client(
						rs.getInt("id"), 
						rs.getString("description"), 
						rs.getString("mac"),
						rs.getBoolean("enabled"), 
						rs.getInt("id_customer")));
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
		return clientList;
	}
	
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update clients set description = ? , enabled = ? where id = ?";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setBoolean(2, this.isEnabled);
	      st.setInt(3, this.idClient);
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
		  String sql = "delete from clients where id = ?";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idClient);
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
	
	public void setAsCustomerClient(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update clients set id_customer = ? where id = ?";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idCustomer);
	      st.setInt(2, this.idClient);
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
	
	public static String selectSiteByClient(Connection conn, int idClient) {
		
		String ids = "";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from rel_client_site " + 
					"where idclient = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idClient);
			rs = st.executeQuery();
			while(rs.next()) {
				ids+=rs.getString("idsite")+",";
			}
			rs.close();
			if(ids.length()>0)
				ids = ids.substring(0, ids.length()-1);
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
		return ids;
		
	}

	public static String selectStepByClient(Connection conn, int idClient) {
		
		String ids = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from rel_client_step " + 
					"where idclient = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idClient);
			rs = st.executeQuery();
			while(rs.next()) {
				ids+=rs.getString("idstep")+",";
			}
			rs.close();
			if(ids.length()>0)
				ids = ids.substring(0, ids.length()-1);
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
		return ids;
		
	}
	
	public void deleteRelSite(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from rel_client_site where idclient = ?";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idClient);
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
	
	public void insertRelSite(Connection conn, int idsite) {
		PreparedStatement st = null;
		try{
		  String sql = "insert into rel_client_site (idclient, idsite) values (?, ?)";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idClient);
	      st.setInt(2, idsite);
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
	
	public void deleteRelStep(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from rel_client_step where idclient = ?";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idClient);
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
	
	public void insertRelStep(Connection conn, int idstep) {
		PreparedStatement st = null;
		try{
		  String sql = "insert into rel_client_step (idclient, idstep) values (?, ?)";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idClient);
	      st.setInt(2, idstep);
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
