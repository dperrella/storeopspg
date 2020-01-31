package eu.mcsistemi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.net.ntp.TimeStamp;

import com.mysql.jdbc.Statement;

import java.sql.Types;
import eu.mcsistemi.model.Audit;
import eu.mcsistemi.Utility.DateManager;

public class AuditDAOImpl implements AuditDAO {
	
	DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd MM:ss");
	
	public List<Audit> getAuditList(Connection conn){
		 PreparedStatement st = null;
	        ResultSet rs = null;
	        String sql = "";
	        List<Audit> auditList = null;
	        try {
	            sql = "select * from atsource.ats_audit ";
	            st = conn.prepareStatement(sql);
	            st.executeQuery();
	            rs = st.getResultSet();
	            auditList = new LinkedList<Audit>();
	            while(rs.next()) {
	                auditList.add(new Audit(rs.getInt("audit_id"),rs.getString("name"),rs.getString("type"),DateManager.getDate(rs.getTimestamp("creation_date")),DateManager.getDate(rs.getTimestamp("closing_date")),
		            						rs.getString("state"),rs.getInt("store_id")));
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

	        return auditList;
	}
	
	public Audit getAuditById(Connection conn, int auditId){
		 PreparedStatement st = null;
	        ResultSet rs = null;
	        String sql = "";
	        Audit audit = null;
	        try {
	            sql = "select * from atsource.ats_audit where audit_id = ? ";
	            st = conn.prepareStatement(sql);
	            st.setInt(1, auditId);
	            st.executeQuery();
	            rs = st.getResultSet();
	            if(rs.next())
	            	audit = new Audit(rs.getInt("audit_id"),rs.getString("name"),rs.getString("type"),DateManager.getDate(rs.getTimestamp("creation_date")),DateManager.getDate(rs.getTimestamp("closing_date")),
	            		rs.getString("state"),rs.getInt("store_id"));
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
	        return audit;
		}
	
	
	public List<Audit> getAuditsByStoreId(Connection conn, int storeId){
		 PreparedStatement st = null;
	        ResultSet rs = null;
	        String sql = "";
	        List<Audit> auditList = null;
	        try {
	            sql = "select * from atsource.ats_audit where store_id = ? ";
	            st = conn.prepareStatement(sql);
	            st.setInt(1, storeId);
	            st.executeQuery();
	            rs = st.getResultSet();
	            auditList = new LinkedList<Audit>();
	            while(rs.next()) {
	                auditList.add(new Audit(rs.getInt("audit_id"),rs.getString("name"),rs.getString("type"),DateManager.getDate(rs.getTimestamp("creation_date")),DateManager.getDate(rs.getTimestamp("closing_date")),
		            						rs.getString("state"),rs.getInt("store_id")));
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

	        return auditList;
	}
	
	public List<Audit> getFullAuditsByStoreId(Connection conn, int storeId){
		 PreparedStatement st = null;
	        ResultSet rs = null;
	        String sql = "";
	        List<Audit> auditList = null;
	        AuditItemDAO auditItemDAO = new AuditItemDAOImpl();
	        try {
	            sql = "select * from atsource.ats_audit where store_id = ? ";
	            System.out.println(new Date()+" "+sql);
	            st = conn.prepareStatement(sql);
	            st.setInt(1, storeId);
	            st.executeQuery();
	            rs = st.getResultSet();
	            auditList = new LinkedList<Audit>();
	            while(rs.next()) {
	                auditList.add(new Audit(rs.getInt("audit_id"),rs.getString("name"),rs.getString("type"),DateManager.getDate(rs.getTimestamp("creation_date")),DateManager.getDate(rs.getTimestamp("closing_date")),
		            						rs.getString("state"),rs.getInt("store_id"), auditItemDAO.getFullAuditItemByAuditId(conn, rs.getInt("audit_id"))));
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

	        return auditList;
	}
	
	public int createAudit(Connection conn, Audit audit)throws Exception{
	  PreparedStatement st = null;
	  int auditId = 0;
      String sql = "";
      String sql2 = "";
      Calendar cal = Calendar.getInstance();
     
      try {
          sql = "insert into atsource.ats_audit (creation_date,type,state,store_id) "+
          		"values (?,?,?,?) "+
          		 "returning audit_id";
          st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
       
          if(audit.getCreationDate() != null)
        	  st.setTimestamp(1, new Timestamp(audit.getCreationDate().getTime()));
          else
        	  st.setNull(1, Types.TIMESTAMP);
          if(audit.getType() != null)
        	  st.setString(2, audit.getType());
          else
        	  st.setNull(2, Types.VARCHAR);
          if(audit.getState() != null)
        	  st.setString(3, audit.getState());
          else
        	  st.setNull(3, Types.VARCHAR);
          st.setInt(4,audit.getStoreId());
          st.executeUpdate();
          ResultSet rs = st.getGeneratedKeys();
          if(rs.next())
        	  auditId = rs.getInt(1);
          st.close();
          sql2 = "update atsource.ats_audit "+
        		 "set name = ? "+
        		 "where audit_id = ?";
          st = conn.prepareStatement(sql2);
          st.setString(1, auditId+"_"+DateManager.toString(new Date(), DateManager.normalDateformat));
          st.setInt(2, auditId);
          st.executeUpdate();
          st.close();
          

      }
      catch(Exception e) {
          throw e;
    	  //e.printStackTrace();
          //return 0;
      }
      finally {
          try {
              if(st!=null)
                  st.close();
          }
          catch(Exception e) {}
      }
      return auditId;
	}
	
	public boolean updateAudit(Connection conn, Audit audit)throws Exception{
	 PreparedStatement st = null;
     String sql = "";
     try {
         sql = "update atsource.ats_audit "+
         	   "set name = ?, closing_date = ?, state = ?, store_id = ? "+
         	   "where audit_id = ?";
         st = conn.prepareStatement(sql);
         if(audit.getName() != null)
       	  	st.setString(1, audit.getName());
         else
        	 st.setNull(1,Types.VARCHAR);
         
         if(audit.getClosingDate() != null)
       	  	st.setTimestamp(2, new Timestamp(audit.getClosingDate().getTime()));
         else
        	 st.setNull(2, Types.TIMESTAMP);
         if(audit.getState() != null)
       	  	st.setString(3, audit.getState());
         else
        	st.setNull(3, Types.VARCHAR);
         st.setInt(4,audit.getStoreId());
         st.setInt(5, audit.getAuditId());
         st.executeUpdate();
         st.close();

     }
     catch(Exception e) {
         throw e;
    	 //e.printStackTrace();
         //return false;
     }
     finally {
         try {
             if(st!=null)
                 st.close();
         }
         catch(Exception e) {}
     }
     return true;
}
	
	public boolean deleteAudit(Connection conn, Audit audit)throws Exception{
		 PreparedStatement st = null;
	        String sql = "";
	        try {
	            sql = "delete from atsource.ats_audit "+
	            	  "where audit_id = ?";
	            st = conn.prepareStatement(sql);
	            st.setInt(1, audit.getAuditId());
	            st.executeUpdate();
	            st.close();

	        }
	        catch(Exception e) {
	            throw e;
	       	 //e.printStackTrace();
	            //return false;
	        }
	        finally {
	            try {
	                if(st!=null)
	                    st.close();
	            }
	            catch(Exception e) {}
	        }
	        return true;
	   }
}
