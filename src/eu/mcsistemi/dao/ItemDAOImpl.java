package eu.mcsistemi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import eu.mcsistemi.model.Item;
import eu.mcsistemi.model.Photo;

public class ItemDAOImpl implements ItemDAO {
	
//	public List<Item> getItemList(Connection conn){
//		 PreparedStatement st = null;
//	        ResultSet rs = null;
//	        String sql = "";
//	        List<Item> itemList = null;
//	        try {
//	            sql = "select * from atsource.ats_item_with_lot_count ";
//	            st = conn.prepareStatement(sql);
//	            st.executeQuery();
//	            rs = st.getResultSet();
//	            itemList = new LinkedList<Item>();
//	            while(rs.next()) {
//	                itemList.add(new Item(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
//              	  			              rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link"),rs.getInt("lots_number")
//              	  			              ));
//	            }
//	            rs.close();
//
//	        }
//	        catch(Exception e) {
//	            e.printStackTrace();
//	            return null;
//	        }
//	        finally {
//	            try {
//	                if(st!=null)
//	                    st.close();
//	            }
//	            catch(Exception e) {}
//	        }
//
//	        return itemList;
//	}
	
	public Item getFullItemById(Connection conn, String barcode){
		PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        Item item = null;
        PhotoDAO photoDAO = new PhotoDAOImpl();
        try {
            sql = "select * "+
            	  "from atsource.ats_item "+
            	  "where barcode = ? ";
            st = conn.prepareStatement(sql);
            st.setString(1, barcode);
            st.executeQuery();
            rs = st.getResultSet();
            if(rs.next())
            	item = new Item(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
			              		rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link"), photoDAO.getPhotosByBarcode(conn, barcode)
			              		);
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
        return item;
	}
	
	public int checkExistItem(Connection conn, String barcode)throws Exception{
		PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        int tot = 0;
        try {
            sql = "select count(1) as tot "+
            	  "from atsource.ats_item "+
            	  "where barcode = ? ";
            st = conn.prepareStatement(sql);
            st.setString(1, barcode);
            st.executeQuery();
            rs = st.getResultSet();
            if(rs.next())
            	tot = rs.getInt("tot");
            rs.close();

        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
            //return 0;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }
        return tot;
	}
	
	public int checkUserItem(Connection conn, String barcode)throws Exception{
		PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        int tot = 0;
        try {
            sql = "select count(1) as tot "+
            	  "from atsource.ats_zutenti_item "+
            	  "where barcode = ? ";
            st = conn.prepareStatement(sql);
            st.setString(1, barcode);
            st.executeQuery();
            rs = st.getResultSet();
            if(rs.next())
            	tot = rs.getInt("tot");
            rs.close();

        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
            //return 0;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }
        return tot;
	}
	
	public List<Item> getItemsByUserId(Connection conn, String userId){
	PreparedStatement st = null;
    ResultSet rs = null;
    String sql = "";
    List<Item> itemList = null;
    try {
        sql = "select barcode,name,description,vendor,brand,country,protected,evolution_report_link,lots_number "+
        	  "from atsource.vw_ats_items_with_lot_count_for_users"+
        	  " where idusr = ?";
        st = conn.prepareStatement(sql);
        st.setString(1, userId);
        st.executeQuery();
        rs = st.getResultSet();
        itemList = new LinkedList<Item>();
        while(rs.next()) {
            itemList.add(new Item(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
      	  			              rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link"),rs.getInt("lots_number")
      	  			              ));
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

    return itemList;
}
	public List<Item> getFullItemsByUserId(Connection conn, String userId){
		PreparedStatement st = null;
	    ResultSet rs = null;
	    String sql = "";
	    List<Item> itemList = null;
	    PhotoDAO photoDAO = new PhotoDAOImpl();
	    try {
	        sql = "select barcode,name,description,vendor,brand,country,protected,evolution_report_link,lots_number "+
	          	  "from atsource.vw_ats_items_with_lot_count_for_users"+
	        	  " where idusr = ?";
	        st = conn.prepareStatement(sql);
	        st.setString(1, userId);
	        st.executeQuery();
	        rs = st.getResultSet();
	        itemList = new LinkedList<Item>();
	        while(rs.next()) {
	            itemList.add(new Item(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
	      	  			              rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link"),rs.getInt("lots_number"), photoDAO.getPhotosByBarcode(conn, rs.getString("barcode")) 
	      	  			              ));
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

	    return itemList;
	}
	
	public List<Item> getItemsByStoreId(Connection conn, int storeId){
		PreparedStatement st = null;
	    ResultSet rs = null;
	    String sql = "";
	    List<Item> itemList = null;
	    JsonParser jsonParser = null;
	    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
	    try {
	        sql = "select * "+
	        	  "from atsource.ats_item_store s join atsource.vw_items_with_photos i on i.barcode = s.barcode "+
	        	  "where s.store_id = ?";
	        st = conn.prepareStatement(sql);
	        st.setInt(1, storeId);
	        st.executeQuery();
	        rs = st.getResultSet();
	        jsonParser = new JsonParser();
	       itemList = new LinkedList<Item>();
	        while(rs.next()) {
	        	//PREDISPOSIZIONE PER LE FOTO
	        	JsonArray photoArray = jsonParser.parse(rs.getString("photos")).getAsJsonArray();
	        	TypeToken<List<Photo>> token = new TypeToken<List<Photo>>() {};
	        	List<Photo> photos = gson.fromJson(photoArray.toString(), token.getType());
	        	/////////////////////////////////////////////////////
	        	 itemList.add(new Item(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
	  			              rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link")
	  			              ));
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

	    return itemList;
	}
	
	public List<String> getBarcodesByStoreId(Connection conn, int storeId){
		PreparedStatement st = null;
	    ResultSet rs = null;
	    String sql = "";
	    List<String> barcodeList = null;
	    try {
	        sql = "select barcode "+
	        	  "from atsource.ats_item_store "+
	        	  "where store_id = ?";
	        System.out.println(new Date()+" "+sql);
	        st = conn.prepareStatement(sql);
	        st.setInt(1, storeId);
	        st.executeQuery();
	        rs = st.getResultSet();
	       barcodeList = new LinkedList<String>();
	        while(rs.next()) {
	            barcodeList.add(rs.getString("barcode"));
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

	    return barcodeList;
	}
	
	public boolean createItem(Connection conn, Item item,String userId, int storeId)throws Exception{
		PreparedStatement st = null;
	      String sql = "";
	      try {
	    	 
		          sql = "insert into atsource.ats_item (barcode,name,description,vendor,brand,country,protected,evolution_report_link) "+
		          		"values (?,?,?,?,?,?,?,?);";
		          st = conn.prepareStatement(sql);
		          
		          if(item.getBarcode().equals(""))
		        	  return false;
		          st.setString(1, item.getBarcode());
		          
		          if(item.getName() != null)
		        	  st.setString(2, item.getName());
		          else
		        	  st.setNull(2, Types.VARCHAR);
		          
		          if(item.getDescription() != null)
		        	  st.setString(3, item.getDescription());
		          else
		        	  st.setNull(3, Types.VARCHAR);
		          
		          if(item.getVendor() != null)
		        	  st.setString(4, item.getVendor());
		          else
		        	  st.setNull(4, Types.VARCHAR);
		          
		          if(item.getBrand() != null)
		        	  st.setString(5, item.getBrand());
		          else
		        	  st.setNull(5, Types.VARCHAR);
		          
		          if(item.getCountry() != null)
		        	  st.setString(6, item.getCountry());
		          else
		        	  st.setNull(6, Types.VARCHAR);
		          
		          if(item.getProtectionType() != null)
		        	  st.setString(7, item.getProtectionType());
		          else
		        	  st.setNull(7, Types.VARCHAR);
		          
		          if(item.getEvolutionReportLink() != null)
		        	  st.setString(8, item.getEvolutionReportLink());
		          else
		        	  st.setNull(8, Types.VARCHAR);
		          st.executeUpdate();
		          st.close();
	          
	      }
	      catch(Exception e) {
	          e.printStackTrace();
	          throw e;
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
	
	public boolean createItemUserConnection(Connection conn, String userId, String barcode)throws Exception{
		PreparedStatement st = null;
	    String sql = "";
	    try{
	    	  sql = "insert into atsource.ats_zutenti_item (user_id,barcode) "+
		            "values(?,?) ";
	          st = conn.prepareStatement(sql);
	          st.setString(1, userId);
	          st.setString(2, barcode);
	          st.executeUpdate();
	          st.close();
	    }catch(Exception e){
	    	 e.printStackTrace();
	    	 throw e;
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
	
	public boolean createItemStoreConnection(Connection conn, int storeId, String barcode)throws Exception{
		PreparedStatement st = null;
	    String sql = "";
	    try{
	    	sql = "insert into atsource.ats_item_store(store_id,barcode) "+
	        	  "values (?,?)";
	          st = conn.prepareStatement(sql);
	          st.setInt(1,storeId);
	          st.setString(2, barcode);
	          st.executeUpdate();
	          st.close();
	    }catch(Exception e){
	    	 e.printStackTrace();
	    	 throw e;
	         // return false;
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
	
	public boolean updateItem(Connection conn, Item item)throws Exception{
		PreparedStatement st = null;
	     String sql = "";
	     try {
	         sql = "update atsource.ats_item "+
	         	   "set name = ?, description = ?, vendor = ?, brand = ?, country = ?, protected = ?, evolution_report_link = ? "+
	         	   "where barcode = ?";
	         st = conn.prepareStatement(sql);
	         
	         if(item.getName() != null)
	        	  st.setString(1, item.getName());
	          else
	        	  st.setNull(1, Types.VARCHAR);
	          
	          if(item.getDescription() != null)
	        	  st.setString(2, item.getDescription());
	          else
	        	  st.setNull(2, Types.VARCHAR);
	          
	          if(item.getVendor() != null)
	        	  st.setString(3, item.getVendor());
	          else
	        	  st.setNull(3, Types.VARCHAR);
	          
	          if(item.getBrand() != null)
	        	  st.setString(4, item.getBrand());
	          else
	        	  st.setNull(4, Types.VARCHAR);
	          
	          if(item.getCountry() != null)
	        	  st.setString(5, item.getCountry());
	          else
	        	  st.setNull(5, Types.VARCHAR);
	          
	          if(item.getProtectionType() != null)
	        	  st.setString(6, item.getProtectionType());
	          else
	        	  st.setNull(6, Types.VARCHAR);
	          
	          if(item.getEvolutionReportLink() != null)
	        	  st.setString(7, item.getEvolutionReportLink());
	          else
	        	  st.setNull(7, Types.VARCHAR);
	          st.setString(8, item.getBarcode());
	         st.executeUpdate();
	         st.close();

	     }
	     catch(Exception e) {
	         e.printStackTrace();
	         throw e;
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
	
	public boolean deleteItemReference(Connection conn, String barcode, String userId)throws Exception{
		PreparedStatement st = null;
        String sql = "";
        String sql2 = "";
        try {
            sql = "delete from atsource.ats_zutenti_item "+
            	  "where barcode = ? AND user_id = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, barcode);
            st.setString(2, userId);
            st.executeUpdate();
            st.close();
            

        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
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
	
	
	public int itemsCountByCustomer(Connection conn, int id_customer)throws Exception{
		PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        int tot = 0;
        try {
            sql = "select count(1) tot from atsource.ats_item a\n" + 
            		"join atsource.ats_item_store b on a.barcode = b.barcode\n" + 
            		"where store_id in(select id from f_stores where id_customer = ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, id_customer);
            st.executeQuery();
            rs = st.getResultSet();
            if(rs.next())
            	tot = rs.getInt("tot");
            rs.close();

        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
            //return 0;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }
        return tot;
	}
	
	public List<Item> getItemsByCustomerId(Connection conn, int customerId){
		PreparedStatement st = null;
	    ResultSet rs = null;
	    String sql = "";
	    List<Item> itemList = null;
	    JsonParser jsonParser = null;
	    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
	    try {
	    	sql = "select * from atsource.vw_items_with_photos a\n" + 
            		"join atsource.ats_item_store b on a.barcode = b.barcode\n" + 
            		"where store_id in(select id from f_stores where id_customer = ?)";
	        st = conn.prepareStatement(sql);
	        st.setInt(1, customerId);
	        st.executeQuery();
	        rs = st.getResultSet();
	        jsonParser = new JsonParser();
	       itemList = new LinkedList<Item>();
	        while(rs.next()) {
	        	//PREDISPOSIZIONE PER LE FOTO
	        	JsonArray photoArray = jsonParser.parse(rs.getString("photos")).getAsJsonArray();
	        	TypeToken<List<Photo>> token = new TypeToken<List<Photo>>() {};
	        	List<Photo> photos = gson.fromJson(photoArray.toString(), token.getType());
	        	/////////////////////////////////////////////////////
	        	 itemList.add(new Item(rs.getString("barcode"),rs.getString("name"),rs.getString("description"),rs.getString("vendor"),rs.getString("brand"),
	  			              rs.getString("country"),rs.getString("protected"),rs.getString("evolution_report_link") 
	  			              ));
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

	    return itemList;
	}

}
