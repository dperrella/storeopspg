package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Readers implements CRUD {
	private int id;
	private String description;
	private int readerType;
	private String ip;
	private int port;
	private boolean enabled;
	private int idCustomer;
	
	public Readers() {	}

	public Readers(int id, String description, int readerType, String ip, int port, boolean enabled, int idCustomer) {
		this.id = id;
		this.description = description;
		this.readerType = readerType;
		this.ip = ip;
		this.port = port;
		this.enabled = enabled;
		this.idCustomer = idCustomer;
		
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
	
	public int getReaderType() {
		return readerType;
	}
	public void setReaderType(int readerType) {
		this.readerType = readerType;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
			sql = "select * from readers where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));
				setReaderType(rs.getInt("reader_type"));
				setIp(rs.getString("ip"));
				setPort(rs.getInt("port"));
				setEnabled(rs.getBoolean("enabled"));
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
	
	
	
	public static ArrayList<Readers> getReadersByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Readers> readersList = null;
		try {
			readersList = new ArrayList<>();
			sql = "select * from readers where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				readersList.add(new Readers(rs.getInt("id"), rs.getString("description"),
						rs.getInt("reader_type"),rs.getString("ip"),rs.getInt("port"),rs.getBoolean("enabled"),
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
		return readersList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update readers set description = ?, reader_type = ?, ip = ?, port = ?, enabled = ?, id_customer = ? where id = ?";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.readerType);
	      st.setString(3, ip);
	      st.setInt(4, this.port);
	      st.setBoolean(5, this.enabled);
	      st.setInt(6, idCustomer);
	      
	      st.setInt(7, this.id);
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
			String sql = " insert into readers (description, reader_type, ip, port, enabled, id_customer)" + 
					" values (?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
			st.setInt(2, this.readerType);
		    st.setString(3, ip);
		    st.setInt(4, this.port);
		    st.setBoolean(5, this.enabled);
		    st.setInt(6, idCustomer);
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
		  String sql = "delete from readers where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.id);
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
