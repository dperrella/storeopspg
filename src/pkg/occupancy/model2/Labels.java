package pkg.occupancy.model2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Labels {

	private int idCustomer;
	private int id;
	private String key;
	private String description;
	private String value;
	private int tile;
	private String lang;
	
	public Labels() {}
	
	public Labels(int idCustomer,int id, String key, String description, String value, int  tile, String lang) {
		this.idCustomer=idCustomer;
		this.id=id;
		this.key=key;
		this.description=description;
		this.value=value;
		this.tile=tile;
		this.lang=lang;
	}
	
	public Labels(String lang) {
		this.lang=lang;
	}
	
	
	
	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int getTile() {
		return tile;
	}

	public void setTile(int tile) {
		this.tile = tile;
	}
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
	

	
	public static ArrayList<Labels> getLabelsGrouped(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Labels> list = null;
		try {
			list = new ArrayList<>();
			sql = "select lang from occ2_labels where id_customer = ? group by lang ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				list.add(new Labels(rs.getString("lang")));
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
		return list;
		
	}
	
	public static ArrayList<Labels> getLabelsByLang(Connection conn,  String lang, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Labels> list = null;
		try {
			list = new ArrayList<>();
			
			sql = "select * from occ2_labels where lang = ? and id_customer = ? order by id ";
			st = conn.prepareStatement(sql);
			st.setString(1, lang);
			st.setInt(2, idCustomer);
			
			System.out.println(st.toString());
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				list.add(new Labels(rs.getInt("id_customer"), rs.getInt("id"), rs.getString("key"),
						rs.getString("description"), rs.getString("value"), rs.getInt("tile"), rs.getString("lang")));
			}
			rs.close();
			st.close();
			
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
		return list;
		
	}
	
	public void addLang(Connection conn, String lang, int idCustomer) {
		PreparedStatement st = null;
		try{
			String sql = "delete from occ2_labels where lang = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1,  lang);
			st.setInt(2, idCustomer);
			st.executeUpdate();
			st.close();
			
			sql = "insert into occ2_labels (id_customer, key, description, value, tile, lang)" + 
					" select "+idCustomer+", key, description, value, tile, '"+lang+"' from occ2_properties where tile = 3 and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
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
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into occ2_labels (key, description, value, tile, lang, id_customer)" + 
					" values (?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.key);
		    st.setString(2, this.description);
		    st.setString(3, this.value);
			st.setInt(4, this.tile);
			st.setString(5,  this.lang);
			st.setInt(6, this.idCustomer);
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
	
	
	
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from occ2_labels where key = ? and lang = ? and id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.key);
	      st.setString(2, this.lang);
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
	
	public void deleteAll(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from occ2_labels where id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, idCustomer);
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
	
	public void deleteByLang(Connection conn, String lang, int idCustomer) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from occ2_labels where lang = ? and id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setString(1, lang);
	      st.setInt(2, idCustomer);
	      // execute the java preparedstatement
	      
	      System.out.println(st.toString());
	      
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
	
	public static String getLabelByKeyLang(Connection conn, String key, String lang, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String value = "";
		try{
		  String sql = "select value from occ2_labels where key = ? and lang = ? and id_customer = ? ";
		  st = conn.prepareStatement(sql);
		  st.setString(1, key);
		  st.setString(2, lang);
		  st.setInt(3, idCustomer);
		  rs = st.executeQuery();
		  if(rs.next()) {
			  value = rs.getString("value");
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
		return value;
	}
	
}
