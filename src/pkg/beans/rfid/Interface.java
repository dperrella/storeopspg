package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Interface {
	private int idInterface;
	private String description;
	
	public Interface() { }
	
	public Interface(int idInterface, String description) {
		super();
		this.idInterface = idInterface;
		this.description = description;
	}
	
	public int getIdInterface() {
		return idInterface;
	}
	
	public void setIdInterface(int idInterface) {
		this.idInterface = idInterface;
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
			sql = "select * from interfaces where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idInterface);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));
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
	
	public static ArrayList<Interface> getAllInterfaces(Connection conn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Interface> interfaces = null;
		try {
			interfaces = new ArrayList<Interface>();
			sql = "select * from interfaces";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				interfaces.add(new Interface(
						rs.getInt("id"), 
						rs.getString("description")
						));
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
		return interfaces;
	}
}
