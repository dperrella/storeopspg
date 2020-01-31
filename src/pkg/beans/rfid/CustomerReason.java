package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerReason implements CRUD {
	private String idReason;
	private String description;
	private int idCustomer;
	
	public CustomerReason() {	}

	public CustomerReason(String idReason, String description, int idCustomer) {
		this.idReason = idReason;
		this.description = description;
		this.idCustomer = idCustomer;
	}
	
	public String getIdReason() {
		return idReason;
	}
	
	public void setIdReason(String idReason) {
		this.idReason = idReason;
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
			sql = "select * from customer_reason where id_reason = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, idReason);
			st.setInt(2, idCustomer);
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
	
	public static int getNrCustomerReasonByIdCustomer(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int nrReason = 0;
		try {
			sql = "select count(*) nr from customer_reason where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				nrReason = rs.getInt("nr");
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
		return nrReason;
	}
	
	public static ArrayList<CustomerReason> getCustomerReasonByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<CustomerReason> customerReasonList = null;
		try {
			customerReasonList = new ArrayList<>();
			sql = "select * from customer_reason where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				customerReasonList.add(new CustomerReason(rs.getString("id_reason"), rs.getString("description"), rs.getInt("id_customer")));
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
		return customerReasonList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update customer_reason set description = ? where id_reason = ? and id_customer = ? ";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setString(2, this.idReason);
	      st.setInt(3, this.idCustomer);
	      
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
			String sql = " insert into customer_reason (id_reason, description, id_customer)" + 
					" values (?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.idReason);
	        st.setString(2, this.description);
	        st.setInt(3, this.idCustomer);
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
		  String sql = "delete from customer_reason where id_reason = ? and id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.idReason);
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

	
}
