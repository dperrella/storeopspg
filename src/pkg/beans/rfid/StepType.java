package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StepType implements CRUD{
	private int idStepType;
	private String description;
	private int idDoctypeDefault;
	private String dsDoctype;
	private int idDoctypeDefault2;
	private String dsDoctype2;
	private int idCustomer;
	private int power;
	private boolean inventory;
	
	public StepType() {	}
	
	public StepType(int idStepType, String description, int idDoctypeDefault, String dsDoctype, 
			int idCustomer, int idDoctypeDefault2, String dsDoctype2, int power, boolean inventory) {
		this.idStepType = idStepType;
		this.description = description;
		this.idDoctypeDefault = idDoctypeDefault;
		this.dsDoctype = dsDoctype;
		this.idCustomer = idCustomer;
		this.idDoctypeDefault2 = idDoctypeDefault2;
		this.dsDoctype2 = dsDoctype2;
		this.power = power;
		this.inventory = inventory;
	}
	
	public int getIdStepType() {
		return idStepType;
	}
	public void setIdStepType(int idStepType) {
		this.idStepType = idStepType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIdDoctypeDefault() {
		return idDoctypeDefault;
	}
	public void setIdDoctypeDefault(int idDoctypeDefault) {
		this.idDoctypeDefault = idDoctypeDefault;
	}
	public String getDsDoctype() {
		return dsDoctype;
	}
	public void setDsDoctype(String dsDoctype) {
		this.dsDoctype = dsDoctype;
	}
	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	public int getIdDoctypeDefault2() {
		return idDoctypeDefault2;
	}
	public void setIdDoctypeDefault2(int idDoctypeDefault2) {
		this.idDoctypeDefault2 = idDoctypeDefault2;
	}
	public String getDsDoctype2() {
		return dsDoctype2;
	}
	public void setDsDoctype2(String dsDoctype2) {
		this.dsDoctype2 = dsDoctype2;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public boolean getInventory() {
		return inventory;
	}
	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}
	
	public void selectRow(Connection conn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from step_type "
					+ "where idstep_type = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idStepType);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setIdDoctypeDefault(rs.getInt("id_doctype_default"));
				setIdDoctypeDefault2(rs.getInt("id_doctype_default2"));
				setPower(rs.getInt("power"));
				setInventory(rs.getBoolean("inventory"));
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
	
	public static ArrayList<StepType> getStepType(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<StepType> stepTypeList = null;
		try {
			stepTypeList = new ArrayList<>();
			sql = "select s.* , d.description as dsdoctype, d2.description as dsdoctype2 from step_type s "
					+ "join documents_type d on d.id_doctype = id_doctype_default "
					+ "left join documents_type d2 on d2.id_doctype = id_doctype_default2 "
					+ "where s.id_customer = ? ";
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				stepTypeList.add(new StepType(rs.getInt("idstep_type"), rs.getString("description"), 
						rs.getInt("id_doctype_default"), rs.getString("dsdoctype"), rs.getInt("id_customer"),
						rs.getInt("id_doctype_default2"), rs.getString("dsdoctype2"), rs.getInt("power"),rs.getBoolean("inventory")));
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
		return stepTypeList;
	}

	@Override
	public void add(Connection conn) {
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		try{
			String sql = " insert into step_type (description, id_doctype_default, id_customer, id_doctype_default2, power, inventory)" + 
					" values (?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
			st.setInt(2, this.idDoctypeDefault);
	        st.setInt(3, this.idCustomer);
	        st.setInt(4, this.idDoctypeDefault2);
	        st.setInt(5, this.power);
	        st.setBoolean(6, this.inventory);
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
	public void update(Connection conn) {
		
		PreparedStatement st = null;
		try{
		  String sql = "update step_type set description = ?, id_doctype_default = ?, id_customer = ?, "
		  		+ "id_doctype_default2 = ?, power = ?, inventory = ? where idstep_type = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.idDoctypeDefault);
	      st.setInt(3, this.idCustomer);
	      st.setInt(4, this.idDoctypeDefault2);
	      st.setInt(5, this.power);
	      st.setBoolean(6, this.inventory);
	      st.setInt(7, this.idStepType);
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
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		try{
		  String sql = "delete from step_type where idstep_type = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idStepType);
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
