package eu.mcsistemi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import eu.mcsistemi.model.Store;

public class StoreDAOImpl implements StoreDAO {
	
	public List<Store> getStoresByUserAndCustomerId(Connection conn, String userId, int customerId){
		PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        List<Store> storeList = null;
        AuditDAO auditDAO = new AuditDAOImpl();
        ItemDAO itemDAO = new ItemDAOImpl();
        try {
            sql = "select idstore, storedescription,idcustomer, \"count\" "+
            	  "from atsource.vw_ats_user_customer_store_desc "+
            	  "where idusr = ? AND idcustomer = ? ";
            st = conn.prepareStatement(sql);
            st.setString(1, userId);
            st.setInt(2, customerId);
            st.executeQuery();
            rs = st.getResultSet();
            storeList = new LinkedList<Store>();
            while(rs.next()) {
                storeList.add(new Store(rs.getInt("idstore"),rs.getString("storedescription"), rs.getInt("idcustomer"), rs.getInt("count")));
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
        
        return storeList;
}

	public List<Store> getFullStoresByUserAndCustomerId(Connection conn, String userId, int customerId){
		PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        List<Store> storeList = null;
        AuditDAO auditDAO = new AuditDAOImpl();
        ItemDAO itemDAO = new ItemDAOImpl();
        try {
            sql = "select idstore, storedescription,idcustomer "+
            	  "from atsource.vw_ats_user_customer_store_desc "+
            	  "where idusr = ? AND idcustomer = ?";
            System.out.println(new Date()+" "+sql);
            st = conn.prepareStatement(sql);
            st.setString(1, userId);
            st.setInt(2, customerId);
            st.executeQuery();
            rs = st.getResultSet();
            storeList = new LinkedList<Store>();
            while(rs.next()) {
                storeList.add(new Store(rs.getInt("idstore"),rs.getString("storedescription"),auditDAO.getFullAuditsByStoreId(conn, rs.getInt("idstore")), itemDAO.getBarcodesByStoreId(conn, rs.getInt("idstore")), rs.getInt("idcustomer")));
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
        
        return storeList;
}
//	public boolean createStore(Connection conn, Store store){
//		PreparedStatement st = null;
//	      String sql = "";
//	      Calendar cal = Calendar.getInstance();
//	     
//	      try {
//	          sql = "insert into atsource.ats_store (name,description) values (?,?)";
//	          st = conn.prepareStatement(sql);
//	          st.setString(1, store.getName());
//	          st.setString(2, store.getDescription());
//	          st.executeUpdate();
//	          st.close();
//
//	      }
//	      catch(Exception e) {
//	          e.printStackTrace();
//	          return false;
//	      }
//	      finally {
//	          try {
//	              if(st!=null)
//	                  st.close();
//	          }
//	          catch(Exception e) {}
//	      }
//	      return true;
//		}
//	
//	public boolean updateStore(Connection conn, Store store){
//		PreparedStatement st = null;
//	     String sql = "";
//	     try {
//	         sql = "update atsource.ats_store "+
//	         	  "set name = ?,description = ?, store_id = ? "+
//	         	  "where store_id = ?";
//	         st = conn.prepareStatement(sql);
//	         st.setString(1, store.getName());
//	         st.setString(2, store.getDescription());
//	         st.setInt(3, store.getStoreId());
//	         st.executeUpdate();
//	         st.close();
//
//	     }
//	     catch(Exception e) {
//	         e.printStackTrace();
//	         return false;
//	     }
//	     finally {
//	         try {
//	             if(st!=null)
//	                 st.close();
//	         }
//	         catch(Exception e) {}
//	     }
//	     return true;
//	}
//	
//	public boolean deleteStore(Connection conn, Store store){
//		PreparedStatement st = null;
//        String sql = "";
//        try {
//            sql = "delete from atsource.ats_store "+
//            	  "where store_id = ?";
//            st = conn.prepareStatement(sql);
//            st.setInt(1, store.getStoreId());
//            st.executeUpdate();
//            st.close();
//
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        finally {
//            try {
//                if(st!=null)
//                    st.close();
//            }
//            catch(Exception e) {}
//        }
//        return true;
//}
}
