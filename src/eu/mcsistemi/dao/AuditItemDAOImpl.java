package eu.mcsistemi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import eu.mcsistemi.model.AuditItem;
import eu.mcsistemi.model.Photo;
import eu.mcsistemi.Utility.DateManager;

public class AuditItemDAOImpl implements AuditItemDAO {

	@Override
	public List<AuditItem> getAuditItemByAuditId(Connection conn, int auditId) {
		 PreparedStatement st = null;
	        ResultSet rs = null;
	        String sql = "";
	        List<AuditItem> auditItemList = null;
	        JsonParser jsonParser = null;
		    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
	        try {
	            sql = "select ai.barcode,i.name,i.description,i.vendor,i.brand,i.country,i.protected,i.evolution_report_link, ai.audit_result,ai.note,ai.lot,ai.expiry,i.photos "+
	            	  "from atsource.ats_audit_item ai join atsource.vw_items_with_photos i on ai.barcode = i.barcode "+  
	            	  "where ai.audit_id = ?";	
	            st = conn.prepareStatement(sql);
	            st.setInt(1, auditId);
	            st.executeQuery();
	            rs = st.getResultSet();
	            jsonParser = new JsonParser();
	            auditItemList = new LinkedList<AuditItem>();
	            while(rs.next()) {
	            	//PREDISPOSIZIONE PER LE FOTO
		        	JsonArray photoArray = jsonParser.parse(rs.getString("photos")).getAsJsonArray();
		        	TypeToken<List<Photo>> token = new TypeToken<List<Photo>>() {};
		        	List<Photo> photos = gson.fromJson(photoArray.toString(), token.getType());
		        	/////////////////////////////////////////////////////
	                auditItemList.add(new AuditItem(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
	              			   rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link"),
	              			   rs.getString("audit_result"), rs.getString("note"), rs.getString("lot"), DateManager.getDate(rs.getTimestamp("expiry")) ));
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

	        return auditItemList;
	}
	public List<AuditItem> getFullAuditItemByAuditId(Connection conn, int auditId) {
		 PreparedStatement st = null;
	        ResultSet rs = null;
	        String sql = "";
	        List<AuditItem> auditItemList = null;
	        PhotoDAO photoDAO = new PhotoDAOImpl();
	        try {
	            sql = "select ai.barcode,i.name,i.description,i.vendor,i.brand,i.country,i.protected,i.evolution_report_link, ai.audit_result,ai.note,ai.lot,ai.expiry "+
		              "from atsource.ats_audit_item ai join atsource.ats_item i on ai.barcode = i.barcode "+  
		              "where ai.audit_id = ?";
	            st = conn.prepareStatement(sql);
	            st.setInt(1, auditId);
	            st.executeQuery();
	            rs = st.getResultSet();
	            auditItemList = new LinkedList<AuditItem>();
	            while(rs.next()) {
	                auditItemList.add(new AuditItem(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
	  			              			   rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link"), photoDAO.getPhotosByBarcode(conn, rs.getString("barcode")),
	  			              			   rs.getString("audit_result"), rs.getString("note"), rs.getString("lot"), DateManager.getDate(rs.getTimestamp("expiry")) ));
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

	        return auditItemList;
	}
	
	public boolean createAuditItems(Connection conn, List<AuditItem> auditItems, int auditId){
		PreparedStatement st = null;
	      String sql = "";
	      try {
	          sql = "insert into atsource.ats_audit_item (audit_id,barcode,audit_result,note,lot,expiry) "+
	          		"values (?,?,?,?,?,?);";
	          st = conn.prepareCall(sql);
	          conn.setAutoCommit(false);
	          for(AuditItem ai: auditItems){
	        	 st.setInt(1, auditId);
		         st.setString(2, ai.getBarcode());
		          if(ai.getAuditResult() != null)
		        	  st.setString(3, ai.getAuditResult());
		          else
		        	  st.setNull(3, Types.VARCHAR);
		          
		          if(ai.getNote() != null)
		        	  st.setString(4, ai.getNote());
		          else
		        	  st.setNull(4, Types.VARCHAR);
		          if(ai.getLot() != null)
		        	  st.setString(5, ai.getLot());
		          else
		        	  st.setNull(5, Types.VARCHAR);
		          if(ai.getExpiry() != null)
		        	  st.setTimestamp(6, new Timestamp(ai.getExpiry().getTime()));
		          else
		        	  st.setNull(6, Types.TIMESTAMP);
		          
		          
		          st.addBatch();
	          }
	        
	          st.executeBatch();
	          conn.commit();
	          st.close();
	      }
	      catch(Exception e) {
	          e.printStackTrace();
	          try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	          return false;
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
	
	public boolean updateAuditItems(Connection conn, List<AuditItem> auditItems, int auditId){
		PreparedStatement st = null;
	     String sql = "";
	     try {
	         sql = "delete from atsource.ats_audit_item "+
	        		"where audit_id = ?";
	         st = conn.prepareStatement(sql);
	         st.setInt(1, auditId);
	         //st.setInt(2, auditId);
	         st.executeUpdate();
	         st.close();
	         createAuditItems(conn, auditItems, auditId);
	     }
	     catch(Exception e) {
	         e.printStackTrace();
	         return false;
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
	
	public boolean deleteAuditItems(Connection conn, int auditId){
		PreparedStatement st = null;
	     String sql = "";
	     try {
	         sql = "delete from atsource.ats_audit_item "+
		           "where audit_id = ? AND barcode in (select barcode from atsource.ats_audit_item where audit_id = ?)";
	         st = conn.prepareStatement(sql);
	         st.setInt(1, auditId);
	         st.setInt(2, auditId);
	         st.executeUpdate();
	         st.close();
	     }
	     catch(Exception e) {
	         e.printStackTrace();
	         return false;
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

//sql = "select i.*, ai.audit_result,ai.note "+
//		  "from atsource.ats_item_with_lot_count i join atsource.ats_audit_item ai on (i.barcode = ai.barcode) "+
//		  "where i.barcode in "+
//		  "(select ai.barcode from atsource.ats_audit_item where ai.audit_id = ?)";


