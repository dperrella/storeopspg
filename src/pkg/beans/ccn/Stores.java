package pkg.beans.ccn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Stores {
	private int idStore;
	private boolean inError;
	private String description;
	private String country;
	private String diffDate;
	private String eventCode;
	private String latitude;
	private String longitude;
	private int nrDevices;
	private int idCustomer;
	private String google_uid;
	private String types;
	
	private String color;
	
	private String customerIdStore;
	
	public Stores() {	}

	public Stores(int idStore, boolean inError, String description, String country,
			String diffDate, String eventCode, String latitude, String longitude, 
			int nrDevices, int idCustomer, String google_uid, String types, String color, String customerIdStore) {
		this.idStore = idStore;
		this.inError = inError;
		this.description = description;
		this.country = country;
		this.diffDate = diffDate;
		this.eventCode = eventCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.nrDevices = nrDevices;
		this.idCustomer = idCustomer;
		this.google_uid=google_uid;
		this.types=types;
		this.color=color;
		this.customerIdStore=customerIdStore;
		
	}
	
	
	
	
	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public boolean isInError() {
		return inError;
	}

	public void setInError(boolean inError) {
		this.inError = inError;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDiffDate() {
		return diffDate;
	}

	public void setDiffDate(String diffDate) {
		this.diffDate = diffDate;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getNrDevices() {
		return nrDevices;
	}

	public void setNrDevices(int nrDevices) {
		this.nrDevices = nrDevices;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public String getGoogle_uid() {
		return google_uid;
	}

	public void setGoogleUid(String google_uid) {
		this.google_uid = google_uid;
	}
	
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getCustomerIdStore() {
		return customerIdStore;
	}

	public void setCustomerIdStore(String customerIdStore) {
		this.customerIdStore = customerIdStore;
	}

	public static ArrayList<Stores> getStoresByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Stores> storeList = null;
		try {
			storeList = new ArrayList<>();
			
			sql = "select s.id, s.id_customer, case when eventcode is not null then true else false end in_error,\n" + 
				"s.description, nome_nazione, \n" + 
				"case when diffdate is null then '0 seconds' else diffdate end diffdate, \n" + 
				"eventcode, s.latitude, s.longitude,\n" + 
				"(select count(b.id) from devices b where b.id_store=s.id) nrdevices, google_uid, google_places_types, control_color, s.customeridstore\n" + 
				"from f_stores s\n" + 
				"left join v_dashlog_2 l on l.id::numeric = s.id\n" + 
				"join nazioni on id_naz = country::numeric\n" + 
				"join vw_stores_control_color wv on id_store = s.id\n" + 
				"where id_customer = ? and flagclose = false\n" + 
				"order by diffdate desc ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			
			int ids = -1;
		
			ArrayList<Integer> sto = new ArrayList<>();
			
			while(rs.next()) {
				
				if(!sto.contains(rs.getInt("id"))) {
					System.out.println(rs.getInt("id"));
					
					Stores s = new Stores(rs.getInt("id"),  
								rs.getBoolean("in_error"), rs.getString("description"), 
								rs.getString("nome_nazione"), rs.getString("diffdate"),
								rs.getString("eventcode"), rs.getString("latitude"),
								rs.getString("longitude"), rs.getInt("nrdevices"), rs.getInt("id_customer"), 
								rs.getString("google_uid"), rs.getString("google_places_types"),
								rs.getString("control_color"), rs.getString("customeridstore"));
					storeList.add(s);
				}
				sto.add(rs.getInt("id"));
				
				
				
				/*
				storeList.add(new Stores(rs.getInt("id"),  
						rs.getBoolean("in_error"), rs.getString("description"), 
						rs.getString("nome_nazione"), rs.getString("diffdate"),
						rs.getString("eventcode"), rs.getString("latitude"),
						rs.getString("longitude"), rs.getInt("nrdevices"), rs.getInt("id_customer"), 
						rs.getString("google_uid"), rs.getString("google_places_types")));
				*/
			
				
				
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
		return storeList;
	}
	
	
	public static ArrayList<Stores> getStoresByIdCustomerAndUsername(Connection conn, int idCustomer, String username){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Stores> storeList = null;
		try {
			storeList = new ArrayList<>();
			
			sql = "select s.id, s.id_customer, case when eventcode is not null then true else false end in_error,\n" + 
				"s.description, nome_nazione, \n" + 
				"case when diffdate is null then '0 seconds' else diffdate end diffdate, \n" + 
				"eventcode, s.latitude, s.longitude,\n" + 
				"(select count(b.id) from devices b where b.id_store=s.id) nrdevices, google_uid, google_places_types, control_color, s.customeridstore\n" + 
				"from f_stores s\n" + 
				"join rel_user_stores r on r.idstore = s.id and idusr = '"+username+"' "+
				"left join v_dashlog_2 l on l.id::numeric = s.id\n" + 
				"join nazioni on id_naz = country::numeric\n" + 
				"join vw_stores_control_color wv on id_store = s.id\n" + 
				"where id_customer = ? and flagclose = false\n" + 
				//"where flagclose = false\n" + 
				//"and google_uid is not null "+
				"order by control_color, diffdate desc ";
			
			System.out.println(sql);
			
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			
			int ids = -1;
		
			ArrayList<Integer> sto = new ArrayList<>();
			
			while(rs.next()) {
				
				
				
				if(!sto.contains(rs.getInt("id"))) {
					//System.out.println(rs.getInt("id"));
					
					Stores s = new Stores(rs.getInt("id"),  
								rs.getBoolean("in_error"), rs.getString("description").replaceAll("'", "").replaceAll("\"", ""), 
								rs.getString("nome_nazione").replaceAll("'", "").replaceAll("\"", ""), rs.getString("diffdate"),
								rs.getString("eventcode"), rs.getString("latitude"),
								rs.getString("longitude"), rs.getInt("nrdevices"), rs.getInt("id_customer"), 
								rs.getString("google_uid"), rs.getString("google_places_types"), 
								rs.getString("control_color"), rs.getString("customeridstore"));
					storeList.add(s);
				}
				sto.add(rs.getInt("id"));
				
				
				
				/*
				storeList.add(new Stores(rs.getInt("id"),  
						rs.getBoolean("in_error"), rs.getString("description"), 
						rs.getString("nome_nazione"), rs.getString("diffdate"),
						rs.getString("eventcode"), rs.getString("latitude"),
						rs.getString("longitude"), rs.getInt("nrdevices"), rs.getInt("id_customer"), 
						rs.getString("google_uid"), rs.getString("google_places_types")));
				*/
			
				
				
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
		return storeList;
	}
	
	public static String getIdsByIdCustomerAndUsername(Connection conn, int idCustomer, String username){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		String ids = "";
		try {
			
			sql = "select s.id \n" + 
				"from stores_counter s\n" + 
				"join rel_user_stores r on r.idstore = s.id and idusr = '"+username+"' "+
				"where id_customer = ? and flagclose = false\n" + 
				//"where flagclose = false\n" + 
				//"and google_uid is not null "+
				"";
			System.out.println(sql);
			
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
				
			while(rs.next()) {
				
				ids+=rs.getInt("id")+"*";
				
			}
			rs.close();
			if(ids.length()>0)
				ids = ids.substring(0,ids.length()-1);
			
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
		return ids;
	}
	
	
	
	public static ArrayList<Stores> getStoresByIdCustomerAndUsernameAndre(Connection conn, int idCustomer, String username){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Stores> storeList = null;
		try {
			storeList = new ArrayList<>();
			
//			sql = "select s.id, s.id_customer, case when eventcode is not null then true else false end in_error,\n" + 
//				"s.description, nome_nazione, \n" + 
//				"case when diffdate is null then '0 seconds' else diffdate end diffdate, \n" + 
//				"eventcode, s.latitude, s.longitude,\n" + 
//				"(select count(b.id) from devices b where b.id_store=s.id) nrdevices, google_uid, google_places_types, control_color, s.customeridstore\n" + 
//				"from f_stores s\n" + 
//				"join rel_user_stores r on r.idstore = s.id and idusr = '"+username+"' "+
//				"left join v_dashlog_2 l on l.id::numeric = s.id\n" + 
//				"join nazioni on id_naz = country::numeric\n" + 
//				"join vw_stores_control_color wv on id_store = s.id\n" + 
//				"where id_customer = ? and flagclose = false\n" + 
//				//"where flagclose = false\n" + 
//				//"and google_uid is not null "+
//				"order by control_color, diffdate desc ";	
			
			sql = "select * from vw_control_stores_customer s " +
					"join rel_user_stores r on r.idstore = s.id and idusr = '"+username+"' " + 
					"where id_customer = ? " + 
					"and google_uid is not null " + 
					"order by control_color, diffdate desc ";
					
			System.out.println(sql);
			
				st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			
			int ids = -1;
		
			ArrayList<Integer> sto = new ArrayList<>();
			
			while(rs.next()) {
				
				
				
				if(!sto.contains(rs.getInt("id"))) {
					//System.out.println(rs.getInt("id"));
					
					String diffdate = rs.getString("diffdate");
					String temp[] = diffdate.split(":");
					try {
						diffdate = temp[0]+":"+temp[1];
					}
					catch(Exception e) {}
					
					Stores s = new Stores(rs.getInt("id"),  
								rs.getBoolean("in_error"), rs.getString("description").replaceAll("'", "").replaceAll("\"", ""), 
								rs.getString("nome_nazione").replaceAll("'", "").replaceAll("\"", ""), diffdate,
								rs.getString("eventcode"), rs.getString("latitude"),
								rs.getString("longitude"), rs.getInt("nrdevices"), rs.getInt("id_customer"), 
								rs.getString("google_uid"), rs.getString("google_places_types"), 
								rs.getString("control_color"), rs.getString("customeridstore"));
					storeList.add(s);
				}
				sto.add(rs.getInt("id"));
				
				
				
				/*
				storeList.add(new Stores(rs.getInt("id"),  
						rs.getBoolean("in_error"), rs.getString("description"), 
						rs.getString("nome_nazione"), rs.getString("diffdate"),
						rs.getString("eventcode"), rs.getString("latitude"),
						rs.getString("longitude"), rs.getInt("nrdevices"), rs.getInt("id_customer"), 
						rs.getString("google_uid"), rs.getString("google_places_types")));
				*/
			
				
				
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
		return storeList;
	}
}
