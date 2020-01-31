package eu.mcsistemi.dao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import eu.mcsistemi.model.Customer;
import eu.mcsistemi.model.Item;
import eu.mcsistemi.model.User;

public class UserDAOImpl implements UserDAO {
	
//	public List<User> getUsersList(Connection conn) {
//
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        String sql = "";
//        List<User> userList = null;
//        try {
//            sql = "select * from zutenti ";
//            st = conn.prepareStatement(sql);
//            rs = st.executeQuery();
//            userList = new LinkedList<User>();
//            while(rs.next()) {
//                userList.add(new User(rs.getString("idusr"),rs.getString("nome"),rs.getString("cogn"),"storeManager"
//                					 ));
//            }
//            rs.close();
//
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        finally {
//            try {
//                if(st!=null)
//                    st.close();
//            }
//            catch(Exception e) {}
//        }
//
//        return userList;
//	}
//	
//	public User getUserById(Connection conn, String userId){
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        String sql = "";
//        User user = null;
//        try {
//            sql = "select * from zutenti where idusr = ? ";
//            st = conn.prepareStatement(sql);
//            st.setString(1, userId);
//            st.executeQuery();
//            rs = st.getResultSet();
//            if(rs.next())
//            	user = new User(rs.getString("idusr"),rs.getString("nome"),rs.getString("cogn"),"storeManager"
//					  			);
//            rs.close();
//
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        finally {
//            try {
//                if(st!=null)
//                    st.close();
//            }
//            catch(Exception e) {}
//        }
//        return user;
//	}

	public User getFullUserById(Connection conn, String userId, String password)throws Exception{
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        User user = null;
        CustomerDAO customerDAO = new CustomerDAOImpl();
        ItemDAO itemDAO = new ItemDAOImpl();
        try {
        	// Trasformo la password in MD5
    		MessageDigest md = MessageDigest.getInstance("MD5");
    		md.update(password.getBytes());
    		byte[] digest = md.digest();
    		StringBuffer sb = new StringBuffer();
    		for (byte b : digest) {
    			sb.append(String.format("%02x", b & 0xff));
    		}
    		
            sql = "select *"
            	+ "from atsource.vw_users "
            	+ "where userId = upper(?) and pwd = ?";
            System.out.println(new Date()+" "+sql);
            st = conn.prepareStatement(sql);
            st.setString(1, userId);
            st.setString(2, sb.toString());
            st.executeQuery();
            System.out.println(st.toString());
            rs = st.getResultSet();
            if(rs.next()) {
//            	Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
//            	List<Customer> customers = gson.fromJson(rs.getString("customers"), new TypeToken<List<Customer>>(){}.getType());
//            	List<Item> items = gson.fromJson(rs.getString("items"), new TypeToken<List<Item>>(){}.getType());
            	
            	user = new User(rs.getString("userId"),rs.getString("name"),rs.getString("surname"), rs.getString("role"), rs.getString("customers"),rs.getString("items"));
            	
            }else
            	throw new Exception();
            rs.close();

        }
        catch(Exception e) {
        	System.out.println("entro");
        	e.printStackTrace();
            throw e;
           // return null;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }
        
//        if(user!=null) {
//	       user.setCustomers(customerDAO.getFullCustomersByUserId(conn, user.getUserId()));
//	       user.setItems(itemDAO.getFullItemsByUserId(conn, user.getUserId()));
//        }
       return user;
	}
	
//	public boolean createUser(Connection conn, User user){
//		  PreparedStatement st = null;
//	        String sql = "";
//	        try {
//	            sql = "insert into atsource.ats_user (name,surname,role,customer) values (?,?,?,?)";
//	            st = conn.prepareStatement(sql);
//	            st.setString(1, user.getName());
//	            st.setString(2, user.getSurname());
//	            st.setString(3, user.getRole());
//	            st.setInt(4, user.getCustomer());
//	            
//	            st.executeUpdate();
//	            st.close();
//
//	        }
//	        catch(Exception e) {
//	            e.printStackTrace();
//	            return false;
//	        }
//	        finally {
//	            try {
//	                if(st!=null)
//	                    st.close();
//	            }
//	            catch(Exception e) {}
//	        }
//	        return true;
//	}
	
//	public boolean updateUser(Connection conn, User user){
//		  PreparedStatement st = null;
//	        String sql = "";
//	        try {
//	            sql = "update atsource.ats_user "+
//	            	  "set name = ?, surname = ?, role = ?, customer = ? "+
//	            	  "where user_id = ?";
//	            st = conn.prepareStatement(sql);
//	            st.setString(1, user.getName());
//	            st.setString(2, user.getSurname());
//	            st.setString(3, user.getRole());
//	            st.setInt(4, user.getCustomer());
//	            st.setInt(5, user.getUserId());
//	            st.executeUpdate();
//	            st.close();
//
//	        }
//	        catch(Exception e) {
//	            e.printStackTrace();
//	            return false;
//	        }
//	        finally {
//	            try {
//	                if(st!=null)
//	                    st.close();
//	            }
//	            catch(Exception e) {}
//	        }
//	        return true;
//	}
//
//	public boolean deleteUser(Connection conn,User user){
//		  PreparedStatement st = null;
//	        String sql = "";
//	        try {
//	            sql = "delete from atsource.ats_user "+
//	            	  "where user_id = ?";
//	            st = conn.prepareStatement(sql);
//	            st.setInt(1, user.getUserId());
//	            st.executeUpdate();
//	            st.close();
//
//	        }
//	        catch(Exception e) {
//	            e.printStackTrace();
//	            return false;
//	        }
//	        finally {
//	            try {
//	                if(st!=null)
//	                    st.close();
//	            }
//	            catch(Exception e) {}
//	        }
//	        return true;
//	}
}
