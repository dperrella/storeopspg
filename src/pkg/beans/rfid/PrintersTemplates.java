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
	private boolean breakLabel;
	
	public PrintersTemplates() {	}

	public PrintersTemplates(int id, String name, String zpl, int idCustomer, boolean breakLabel) {
		this.id = id;
		this.name = name;
		this.zpl = zpl;
		this.idCustomer = idCustomer;
		this.breakLabel = breakLabel;
		
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
	
	public boolean isBreakLabel() {
		return breakLabel;
	}

	public void setBreakLabel(boolean breakLabel) {
		this.breakLabel = breakLabel;
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
				setBreakLabel(rs.getBoolean("break_label"));
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
						rs.getString("zpl"), rs.getInt("id_customer"), rs.getBoolean("break_label")));
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
	
	public static ArrayList<PrintersTemplates> getTemplatesByIdCustomer(Connection conn, int idCustomer, Boolean type){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<PrintersTemplates> readersList = null;
		try {
			readersList = new ArrayList<>();
			sql = "select * from printers_templates where id_customer = ? and break_label = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			st.setBoolean(2, type);
			rs = st.executeQuery();
			while(rs.next()) {
				readersList.add(new PrintersTemplates(rs.getInt("id"), rs.getString("name"),
						rs.getString("zpl"), rs.getInt("id_customer"), rs.getBoolean("break_label")));
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
		  String sql = "update printers_templates set name = ?, zpl = ?, id_customer = ?, break_Label = ? where id = ?";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setString(2, this.zpl);
	      st.setInt(3, this.idCustomer);
	      st.setBoolean(4, this.breakLabel);
	      
	      
	      st.setInt(5, this.id);
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
			String sql = " insert into printers_templates (name, zpl, id_customer, break_label)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
			st.setString(2, this.zpl);
		    st.setInt(3, idCustomer);
		    st.setBoolean(4,  this.breakLabel);
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
