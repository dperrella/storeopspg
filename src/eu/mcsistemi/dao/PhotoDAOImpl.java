package eu.mcsistemi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import eu.mcsistemi.model.Photo;

public class PhotoDAOImpl implements PhotoDAO {
	
	public List<Photo> getPhotosByBarcode(Connection conn, String barcode){
		 PreparedStatement st = null;
	        ResultSet rs = null;
	        String sql = "";
	        List<Photo> photoList = null;
	        try {
	            sql = "select * from atsource.ats_photo where barcode = ? ";
	            st = conn.prepareStatement(sql);
	            st.setString(1,barcode);
	            st.executeQuery();
	            rs = st.getResultSet();
	            photoList = new LinkedList<Photo>();
	            while(rs.next()) {
	                photoList.add(new Photo(rs.getInt("photo_id"),rs.getString("name"),
		            						rs.getString("url"), rs.getString("reference"),rs.getString("barcode")));
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

	        return photoList;
	}
	public boolean createPhotos(Connection conn, List<Photo> photos){
		PreparedStatement st = null;
	      String sql = "";
	      String sql2 = "";
	      try {
	          sql = "insert into atsource.ats_photo (name,url,reference,barcode) "+
	          		"values (?,?,?,?);";
	          st = conn.prepareCall(sql);
	          
	          for(Photo p: photos){
	        	  if(p.getName() != null)
		        	  st.setString(1, p.getName());
		          else
		        	  st.setNull(1, Types.VARCHAR);
		          
		          if(p.getUrl() != null)
		        	  st.setString(2, p.getUrl());
		          else
		        	  st.setNull(2, Types.VARCHAR);
		          
		          if(p.getReference() != null)
		        	  st.setString(3, p.getReference());
		          else
		        	  st.setNull(3, Types.VARCHAR);
		          
		          st.setString(4, p.getBarcode());
		          st.addBatch();
	          }
	        
	          st.executeBatch();
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
	
	public boolean updatePhotos(Connection conn, List<Photo> photos, String barcode){
		PreparedStatement st = null;
	      String sql = "";
	      try {
	          sql = "delete from atsource.ats_photo "+
	          		"where barcode = ?";
	          st = conn.prepareStatement(sql);
	          st.setString(1, barcode);
	          st.executeUpdate();
	          st.close();
	          createPhotos(conn, photos);
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
