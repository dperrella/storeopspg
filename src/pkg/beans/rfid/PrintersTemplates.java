package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PrintersTemplates implements CRUD {
	private int id;
	private String name;
	private String zpl;
	private int idCustomer;
	
	public PrintersTemplates() {	}

	public PrintersTemplates(int id, String name, String zpl, int idCustomer) {
		this.id = id;
		this.name = name;
		this.zpl = zpl;
		this.idCustomer = idCustomer;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getZpl() {
		return zpl;
	}
	public void setZpl(String zpl) {
		this.zpl = zpl;
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
			sql = "select * from printers_templates where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				setName(rs.getString("name"));
				setZpl(rs.getString("zpl"));
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
	
	
	
	public static ArrayList<PrintersTemplates> getTemplatesByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<PrintersTemplates> readersList = null;
		try {
			readersList = new ArrayList<>();
			sql = "select * from printers_templates where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				readersList.add(new PrintersTemplates(rs.getInt("id"), rs.getString("name"),
						rs.getString("zpl"), rs.getInt("id_customer")));
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
		  String sql = "update printers_templates set name = ?, zpl = ?, id_customer = ? where id = ?";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setString(2, this.zpl);
	      st.setInt(3, this.idCustomer);
	      
	      st.setInt(4, this.id);
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
			String sql = " insert into printers_templates (name, zpl, id_customer)" + 
					" values (?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
			st.setString(2, this.zpl);
		    st.setInt(3, idCustomer);
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
		  String sql = "delete from printers_templates where id = ? ";
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
