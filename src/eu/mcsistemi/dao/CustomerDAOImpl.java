package eu.mcsistemi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.sun.mail.handlers.message_rfc822;

import eu.mcsistemi.model.Customer;

public class CustomerDAOImpl implements CustomerDAO  {

	@Override
	public List<Customer> getCustomersByUserId(Connection conn, String userId) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    String sql = "";
	    List<Customer> customerList = null;
	    try {
	        sql = "select * "+
	        	  "from vw_ats_user_customer_store_desc "+
	        	  " where idusr = ?";
	        st = conn.prepareStatement(sql);
	        st.setString(1, userId);
	        st.executeQuery();
	        rs = st.getResultSet();
	        customerList = new LinkedList<Customer>();
	        while(rs.next()) {
	            customerList.add(new Customer(rs.getString("description"), rs.getInt("idcustomer")));
	        }
	        rs.close();

	    }
	    catch(Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    finally {
	        try {
	            if(st!=null)
	                st.close();
	        }
	        catch(Exception e) {}
	    }

	    return customerList;
	}
	
	public List<Customer> getFullCustomersByUserId(Connection conn, String userId) {
		PreparedStatement st = null;
	    ResultSet rs = null;
	    String sql = "";
	    List<Customer> customerList = null;
	    StoreDAO storeDAO = new StoreDAOImpl();
	    try {
	        sql ="select idcustomer,customerdescription "+
		        	  "from atsource.vw_ats_user_customer_store_desc "+ 
		        	  "where idusr = ? "+
					  "group by idcustomer, customerdescription ";
	        System.out.println(new Date()+" "+sql);
	        st = conn.prepareStatement(sql);
	        st.setString(1, userId);
	        st.executeQuery();
	        rs = st.getResultSet();
	        customerList = new LinkedList<Customer>();
	        while(rs.next()) {
	            customerList.add(new Customer(rs.getString("customerdescription"), rs.getInt("idcustomer"),storeDAO.getFullStoresByUserAndCustomerId(conn, userId, rs.getInt("idcustomer"))));
	        }
	        rs.close();

	    }
	    catch(Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    finally {
	        try {
	            if(st!=null)
	                st.close();
	        }
	        catch(Exception e) {}
	    }

	    return customerList;
	}
}
