package pkg.occupancy.model2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pkg.utility.Utility;

/**
 * @author danieleperrella
 *
 */
public class Aree {
	private int id;
	private int idCustomer;
	private String description;
	private int idStore;
	private int percWarning;
	private String normalColor;
	private String warningColor;
	private String overthresholdColor;
	private int threshold;
	private String active;
	private boolean automaticAdjust;
	private int percThreshold;
	private int averageTime;
	
	private String iprelay;
	private boolean useRelay;
	private String relayCmd;
	
	private String relayType;
	
	
	private int hour;
	private int d1;
	private int d2;
	private int d3;
	private int d4;
	private int d5;
	private int d6;
	private int d7;
	private boolean ml;
	private String chkMl;
	
	private boolean kiosk;
	private boolean subArea;
	private boolean zone;
	private boolean areaMl;
	private boolean areaSm;
	
	
	
	
	
	private boolean ml1;
	private boolean ml2;
	private boolean ml3;
	private boolean ml4;
	private boolean ml5;
	private boolean ml6;
	private boolean ml7;
	private String chkMl1;
	private String chkMl2;
	private String chkMl3;
	private String chkMl4;
	private String chkMl5;
	private String chkMl6;
	private String chkMl7;
	private String sts1;
	private String sts2;
	private String sts3;
	private String sts4;
	private String sts5;
	private String sts6;
	private String sts7;
	
	
	
	
	
	public Aree() {}
	
	public Aree(int id, int idCustomer, String description, int idStore, int percWarning, 
			String normalColor, String warningColor, String overthresholdColor, int threshold, String active, 
			boolean kiosk, boolean subArea, boolean zone, boolean areaMl, boolean areaSm) {
		this.id=id;
		this.idCustomer=idCustomer;
		this.description=description;
		this.idStore=idStore;
		this.percWarning=percWarning;
		this.normalColor=normalColor;
		this.warningColor=warningColor;
		this.overthresholdColor=overthresholdColor;
		this.threshold=threshold;		
		this.active=active;
		this.kiosk=kiosk;
		this.subArea=subArea;
		this.zone=zone;
		this.areaMl=areaMl;
		this.areaSm=areaSm;
	}
	
	public Aree(int id, int hour, int d1, int d2, int d3, int d4, int d5, int d6, int d7, String chkMl) {
		this.id=id;
		this.hour=hour;
		this.d1=d1;
		this.d2=d2;
		this.d3=d3;
		this.d4=d4;
		this.d5=d5;
		this.d6=d6;
		this.d7=d7;
		this.chkMl=chkMl;
		
	}
	
	
	public Aree(int id, int hour, int d1, int d2, int d3, int d4, int d5, int d6, int d7, 
			String sts1,String sts2,String sts3,String sts4,String sts5,String sts6,String sts7) {
		this.id=id;
		this.hour=hour;
		this.d1=d1;
		this.d2=d2;
		this.d3=d3;
		this.d4=d4;
		this.d5=d5;
		this.d6=d6;
		this.d7=d7;
		this.sts1=sts1;
		this.sts2=sts2;
		this.sts3=sts3;
		this.sts4=sts4;
		this.sts5=sts5;
		this.sts6=sts6;
		this.sts7=sts7;
		
	}
	
	public Aree(int id, int hour, boolean ml1, boolean ml2, boolean ml3, boolean ml4, boolean ml5, boolean ml6, boolean ml7, 
			String chkMl1,String chkMl2,String chkMl3,String chkMl4,String chkMl5,String chkMl6,String chkMl7,
			String sts1,String sts2,String sts3,String sts4,String sts5,String sts6,String sts7) {
		this.id=id;
		this.hour=hour;
		this.ml1=ml1;
		this.ml2=ml2;
		this.ml3=ml3;
		this.ml4=ml4;
		this.ml5=ml5;
		this.ml6=ml6;
		this.ml7=ml7;
		this.chkMl1=chkMl1;
		this.chkMl2=chkMl2;
		this.chkMl3=chkMl3;
		this.chkMl4=chkMl4;
		this.chkMl5=chkMl5;
		this.chkMl6=chkMl6;
		this.chkMl7=chkMl7;
		this.sts1=sts1;
		this.sts2=sts2;
		this.sts3=sts3;
		this.sts4=sts4;
		this.sts5=sts5;
		this.sts6=sts6;
		this.sts7=sts7;
		
	}
	
	public Aree(int id, String description, String iprelay, String relayType, String relayCmd) {
		this.id=id;
		this.description=description;
		this.iprelay=iprelay;
		this.relayType=relayType;
		this.relayCmd=relayCmd;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}

	public int getPercWarning() {
		return percWarning;
	}

	public void setPercWarning(int percWarning) {
		this.percWarning = percWarning;
	}

	public String getNormalColor() {
		return normalColor;
	}

	public void setNormalColor(String normalColor) {
		this.normalColor = normalColor;
	}

	public String getWarningColor() {
		return warningColor;
	}

	public void setWarningColor(String warningColor) {
		this.warningColor = warningColor;
	}

	public String getOverthresholdColor() {
		return overthresholdColor;
	}

	public void setOverthresholdColor(String overthresholdColor) {
		this.overthresholdColor = overthresholdColor;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
	
	public String getIprelay() {
		return iprelay;
	}

	public void setIprelay(String iprelay) {
		this.iprelay = iprelay;
	}
	
	
	

	public boolean isUseRelay() {
		return useRelay;
	}

	public void setUseRelay(boolean useRelay) {
		this.useRelay = useRelay;
	}

	public String getRelayCmd() {
		return relayCmd;
	}

	public void setRelayCmd(String relayCmd) {
		this.relayCmd = relayCmd;
	}
	
	public String getRelayType() {
		return relayType;
	}

	public void setRelayType(String relayType) {
		this.relayType = relayType;
	}
	
	

	public boolean isAutomaticAdjust() {
		return automaticAdjust;
	}

	public void setAutomaticAdjust(boolean automaticAdjust) {
		this.automaticAdjust = automaticAdjust;
	}

	public int getPercThreshold() {
		return percThreshold;
	}

	public void setPercThreshold(int percThreshold) {
		this.percThreshold = percThreshold;
	}

	public int getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(int averageTime) {
		this.averageTime = averageTime;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getD1() {
		return d1;
	}

	public void setD1(int d1) {
		this.d1 = d1;
	}

	public int getD2() {
		return d2;
	}

	public void setD2(int d2) {
		this.d2 = d2;
	}

	public int getD3() {
		return d3;
	}

	public void setD3(int d3) {
		this.d3 = d3;
	}

	public int getD4() {
		return d4;
	}

	public void setD4(int d4) {
		this.d4 = d4;
	}

	public int getD5() {
		return d5;
	}

	public void setD5(int d5) {
		this.d5 = d5;
	}

	public int getD6() {
		return d6;
	}

	public void setD6(int d6) {
		this.d6 = d6;
	}

	public int getD7() {
		return d7;
	}

	public void setD7(int d7) {
		this.d7 = d7;
	}
	
	
	

	public boolean isMl() {
		return ml;
	}

	public void setMl(boolean ml) {
		this.ml = ml;
	}
	
	public String getChkMl() {
		return chkMl;
	}

	public void setChkMl(String chkMl) {
		this.chkMl = chkMl;
	}

	public boolean isKiosk() {
		return kiosk;
	}

	public void setKiosk(boolean kiosk) {
		this.kiosk = kiosk;
	}
	
	public boolean isSubArea() {
		return subArea;
	}

	public void setSubArea(boolean subArea) {
		this.subArea = subArea;
	}

	
	
	public boolean isZone() {
		return zone;
	}

	public void setZone(boolean zone) {
		this.zone = zone;
	}
	
	

	public boolean isAreaMl() {
		return areaMl;
	}

	public void setAreaMl(boolean areaMl) {
		this.areaMl = areaMl;
	}
	
	
	
	
	

	public boolean isAreaSm() {
		return areaSm;
	}

	public void setAreaSm(boolean areaSm) {
		this.areaSm = areaSm;
	}

	public boolean isMl1() {
		return ml1;
	}

	public void setMl1(boolean ml1) {
		this.ml1 = ml1;
	}

	public boolean isMl2() {
		return ml2;
	}

	public void setMl2(boolean ml2) {
		this.ml2 = ml2;
	}

	public boolean isMl3() {
		return ml3;
	}

	public void setMl3(boolean ml3) {
		this.ml3 = ml3;
	}

	public boolean isMl4() {
		return ml4;
	}

	public void setMl4(boolean ml4) {
		this.ml4 = ml4;
	}

	public boolean isMl5() {
		return ml5;
	}

	public void setMl5(boolean ml5) {
		this.ml5 = ml5;
	}

	public boolean isMl6() {
		return ml6;
	}

	public void setMl6(boolean ml6) {
		this.ml6 = ml6;
	}

	public boolean isMl7() {
		return ml7;
	}

	public void setMl7(boolean ml7) {
		this.ml7 = ml7;
	}

	public String getChkMl1() {
		return chkMl1;
	}

	public void setChkMl1(String chkMl1) {
		this.chkMl1 = chkMl1;
	}

	public String getChkMl2() {
		return chkMl2;
	}

	public void setChkMl2(String chkMl2) {
		this.chkMl2 = chkMl2;
	}

	public String getChkMl3() {
		return chkMl3;
	}

	public void setChkMl3(String chkMl3) {
		this.chkMl3 = chkMl3;
	}

	public String getChkMl4() {
		return chkMl4;
	}

	public void setChkMl4(String chkMl4) {
		this.chkMl4 = chkMl4;
	}

	public String getChkMl5() {
		return chkMl5;
	}

	public void setChkMl5(String chkMl5) {
		this.chkMl5 = chkMl5;
	}

	public String getChkMl6() {
		return chkMl6;
	}

	public void setChkMl6(String chkMl6) {
		this.chkMl6 = chkMl6;
	}

	public String getChkMl7() {
		return chkMl7;
	}

	public void setChkMl7(String chkMl7) {
		this.chkMl7 = chkMl7;
	}

	public String getSts1() {
		return sts1;
	}

	public void setSts1(String sts1) {
		this.sts1 = sts1;
	}

	public String getSts2() {
		return sts2;
	}

	public void setSts2(String sts2) {
		this.sts2 = sts2;
	}

	public String getSts3() {
		return sts3;
	}

	public void setSts3(String sts3) {
		this.sts3 = sts3;
	}

	public String getSts4() {
		return sts4;
	}

	public void setSts4(String sts4) {
		this.sts4 = sts4;
	}

	public String getSts5() {
		return sts5;
	}

	public void setSts5(String sts5) {
		this.sts5 = sts5;
	}

	public String getSts6() {
		return sts6;
	}

	public void setSts6(String sts6) {
		this.sts6 = sts6;
	}

	public String getSts7() {
		return sts7;
	}

	public void setSts7(String sts7) {
		this.sts7 = sts7;
	}

	

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from occ2_aree where id = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, this.idCustomer);
			
			rs = st.executeQuery();
			if(rs.next()) {
				setIdCustomer(rs.getInt("id_customer"));
				setDescription(rs.getString("description"));				
				setIdStore(rs.getInt("id_store"));
				setPercWarning(rs.getInt("perc_warning"));
				setNormalColor(rs.getString("normal_color"));
				setWarningColor(rs.getString("warning_color"));
				setOverthresholdColor(rs.getString("overthreshold_color"));
				setThreshold(rs.getInt("threshold"));
				setAutomaticAdjust(rs.getBoolean("automatic_adjust"));
				setPercThreshold(rs.getInt("perc_threshold"));
				setAverageTime(rs.getInt("average_time"));
				setIprelay(rs.getString("ip_relay"));
				setUseRelay(rs.getBoolean("use_relay"));
				setRelayCmd(rs.getString("relay_cmd"));
				setRelayType(rs.getString("relay_type"));
				setKiosk(rs.getBoolean("kiosk"));
				setSubArea(rs.getBoolean("subarea"));
				setZone(rs.getBoolean("zone"));
				setAreaMl(rs.getBoolean("ml"));
				setAreaSm(rs.getBoolean("sm"));
				
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
	}
	
	public static int getAreeNum(Connection conn, int idCustomer) {
		int num = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select count(*) num from occ2_aree where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				num = rs.getInt("num");
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
		
		return num;
	}
	
	public static ArrayList<Aree> getAree(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Aree> list = null;
		try {
			list = new ArrayList<>();
			
			sql = "select * from occ2_aree where id_customer = ? order by id ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			
			String active = "active";
			while(rs.next()) {
				
				list.add(new Aree(rs.getInt("id"), rs.getInt("id_customer"), rs.getString("description"),
						rs.getInt("id_store"), rs.getInt("perc_warning"), rs.getString("normal_color"),
						rs.getString("warning_color"), rs.getString("overthreshold_color"), rs.getInt("threshold"), active, rs.getBoolean("kiosk"), 
						rs.getBoolean("subarea"), rs.getBoolean("zone"), rs.getBoolean("ml"), rs.getBoolean("sm")));
				active = "";
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
	
	public static ArrayList<Aree> getAreeNoZone(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Aree> list = null;
		try {
			list = new ArrayList<>();
			
			
			sql = "select * from occ2_aree where zone = false and id_customer = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			
			String active = "active";
			while(rs.next()) {
				
				list.add(new Aree(rs.getInt("id"), rs.getInt("id_customer"), rs.getString("description"),
						rs.getInt("id_store"), rs.getInt("perc_warning"), rs.getString("normal_color"),
						rs.getString("warning_color"), rs.getString("overthreshold_color"), rs.getInt("threshold"), active, rs.getBoolean("kiosk"), 
						rs.getBoolean("subarea"), rs.getBoolean("zone"), rs.getBoolean("ml"), rs.getBoolean("sm")));
				active = "";
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
	
	public static ArrayList<Aree> getAreeById(Connection conn, int id, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Aree> list = null;
		try {
			list = new ArrayList<>();
			sql = "select * from occ2_aree where id = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, idCustomer);
			rs = st.executeQuery();
			
			String active = "active";
			while(rs.next()) {
				
				list.add(new Aree(rs.getInt("id"), rs.getInt("id_customer"), rs.getString("description"),
						rs.getInt("id_store"), rs.getInt("perc_warning"), rs.getString("normal_color"),
						rs.getString("warning_color"), rs.getString("overthreshold_color"), rs.getInt("threshold"), active, 
						rs.getBoolean("kiosk"),rs.getBoolean("subarea"), rs.getBoolean("zone"), rs.getBoolean("ml"), rs.getBoolean("sm")));
				active = "";
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
	
	
	public void update(Connection conn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			
			String sql = "";
			int oldPW = -1;
			int oldTH = -1;
			
			sql = "select * from occ2_aree where id = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.id);
			st.setInt(2, this.idCustomer);
			System.out.println(st.toString());
			rs = st.executeQuery();
			if(rs.next()) {
				oldPW = rs.getInt("perc_warning");
				oldTH = rs.getInt("threshold");
			}
			rs.close();
			st.close();
			
			String ts = "";
			if(oldPW==this.percWarning)
				ts+=", perc_warning_lastupdate = now() ";
			if(oldTH==this.threshold)
				ts+=", threshold_lastupdate = now() ";
			
			
			if(zone==true) {
				areaMl = false;
				areaSm = false;
			}
				
			
			sql = "update occ2_aree set description = ?, perc_warning = ?, normal_color = ?, "
		  		+ "warning_color = ?, overthreshold_color = ?, threshold = ?, ip_relay = ?, use_relay = ?, relay_cmd = ?, "
		  		+ "relay_type = ?, subarea = ?, zone = ?, ml =?, sm=? "+ts
		  		+ "where id = ? and id_customer = ?";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.percWarning);
	      st.setString(3, this.normalColor);
	      st.setString(4, this.warningColor);
	      st.setString(5, this.overthresholdColor);
	      st.setInt(6, this.threshold);
	      st.setString(7, this.iprelay);
	      st.setBoolean(8, this.useRelay);
	      st.setString(9, this.relayCmd);
	      st.setString(10, this.relayType);
	      st.setBoolean(11, this.subArea);
	      st.setBoolean(12, this.zone);
	      st.setBoolean(13, this.areaMl);
	      st.setBoolean(14, this.areaSm);
	      
	      st.setInt(15, this.id);
	      st.setInt(16, this.idCustomer);
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
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			
			if(zone==true) {
				areaMl = false;
				areaSm = false;
			}
			
			String sql = " insert into occ2_aree (id_customer, description, id_store, "
					+ "perc_warning, normal_color, warning_color, overthreshold_color, threshold, ip_relay, use_relay, "
					+ "relay_cmd, relay_type, subarea, zone, ml, sm, id)" + 
					" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idCustomer);
		    st.setString(2, this.description);
		    st.setInt(3, 1);
		    st.setInt(4, this.percWarning);
		    st.setString(5, this.normalColor);
		    st.setString(6, this.warningColor);
		    st.setString(7, this.overthresholdColor);
		    st.setInt(8, this.threshold);
		    st.setString(9, this.iprelay);
		    st.setBoolean(10, this.useRelay);
		    st.setString(11, this.relayCmd);
		    st.setString(12, this.relayType);
		    st.setBoolean(13, this.subArea);
		    st.setBoolean(14, this.zone);
		    st.setBoolean(15, this.areaMl);
		    st.setBoolean(16, areaSm);
		    
		    String sId = Utility.getNewProgr(conn, "occ2_aree" , "id", "where id_customer = "+this.idCustomer+" ");
		    if(sId.equals("1")) {
		    	sId = "10001";
		    }
		    st.setInt(17, Integer.parseInt(sId));
		    
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
		  String sql = "delete from occ2_aree where id = ? and id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.id);
	      st.setInt(2, this.idCustomer);
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
		  String sql = "delete from occ2_aree where id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idCustomer);
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
	
	public static char getChar(int i) {
	    return i<0 || i>25 ? '?' : (char)('A' + i);
	}
	
}
	