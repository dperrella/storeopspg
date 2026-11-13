package pkg.occupancy.model2.firebase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Settings {
	
	private String store;
	private String tstamp;
	private String firmware;
	private String timezone;
	private String mac;
	private ArrayList<Area> areas;
	private ArrayList<DailyOperationStore> dailyOperationStore;
	
	public Settings() {}
	public Settings(String store, String tstamp, String firmware, String timezone, String mac,
			ArrayList<Area> areas, ArrayList<DailyOperationStore> dailyOperationStore) {
		this.store=store;
		this.tstamp=tstamp;
		this.firmware=firmware;
		this.timezone=timezone;
		this.mac=mac;
		this.areas=areas;
		this.dailyOperationStore=dailyOperationStore;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getTstamp() {
		return tstamp;
	}
	public void setTstamp(String tstamp) {
		this.tstamp = tstamp;
	}
	public ArrayList<Area> getAreas() {
		return areas;
	}
	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
	public ArrayList<DailyOperationStore> getDailyOperationStore() {
		return dailyOperationStore;
	}
	public void setDailyOperationStore(ArrayList<DailyOperationStore> dailyOperationStore) {
		this.dailyOperationStore = dailyOperationStore;
	}
	public String getFirmware() {
		return firmware;
	}
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	
	public static void setSettings(Connection conn, int idStore) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			
			String firmware = "1.4.1R";
			
			String mac = "ND";
			String store = getStore(conn, idStore);
			String timezone = getTimezone(conn, idStore);
			
			ArrayList<Area> areas = new ArrayList<Area>();
			List<Area> anagAree = Area.getAree(conn, idStore);
			
			for(Area area : anagAree) {
				areas.add(new Area(area.getId(), area.getDescription(), area.getWarningPercentage(), area.getThreshold(),
						area.isSubarea(), area.isZone(), area.isMl(), area.isSm(), area.isKiosk(), idStore));
			}
			
			ArrayList<DailyOperationStore> dailyOperationStore = new ArrayList<DailyOperationStore>();
			List<DailyOperationStore> openingHours = DailyOperationStore.getOpeningHoursPure(conn, idStore);
			
			for(DailyOperationStore oh : openingHours) {
				dailyOperationStore.add(new DailyOperationStore(oh.getDay(), oh.getFromhour(), oh.getTohour(), oh.isH24(), idStore));
			}
			
			Settings settings = new Settings();
			settings.setStore(store);
			settings.setTstamp(df.format(new Date()));
			settings.setFirmware(firmware);
			settings.setTimezone(timezone);
			settings.setMac(mac);
			settings.setAreas(areas);
			settings.setDailyOperationStore(dailyOperationStore);
			
			Gson gson = new GsonBuilder().create();	
			String sGson = new String(gson.toJson(settings).getBytes(),"UTF-8");
			
			URL url = new URL("http://suitetest.azurewebsites.net/occupancySuite/firebase/settings");
			//URL url = new URL("http://localhost:8081/occupancySuite/firebase/settings");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			System.out.println("*** "+sGson);
			OutputStream os = con.getOutputStream();
			os.write(sGson.getBytes());
			os.flush();
			
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static String getStore(Connection conn, int idStore) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String store = "";
		
		try {
			
			sql = "select account_id||'-'||store_id as store from stores where id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			if(rs.next()) {
				store = rs.getString("store");
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
		
		return store;
	}
	
	public static String getTimezone(Connection conn, int idStore) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String timezone = "";
		
		try {
			
			sql = "select timezone from f_stores where id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStore);
			rs = st.executeQuery();
			if(rs.next()) {
				timezone = rs.getString("timezone");
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
		
		return timezone;
	}
	
}
