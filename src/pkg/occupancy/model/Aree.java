package pkg.occupancy.model;

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
	
	private int hour;
	private int d1;
	private int d2;
	private int d3;
	private int d4;
	private int d5;
	private int d6;
	private int d7;
	
	
	public Aree() {}
	
	public Aree(int id, int idCustomer, String description, int idStore, int percWarning, 
			String normalColor, String warningColor, String overthresholdColor, int threshold, String active) {
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
	}
	
	public Aree(int id, int hour, int d1, int d2, int d3, int d4, int d5, int d6, int d7) {
		this.id=id;
		this.hour=hour;
		this.d1=d1;
		this.d2=d2;
		this.d3=d3;
		this.d4=d4;
		this.d5=d5;
		this.d6=d6;
		this.d7=d7;
		
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

	

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from occ_aree where id = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, idCustomer);
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
			sql = "select count(*) num from occ_aree where id_customer = ? ";
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
			
			sql = "select * from occ_aree where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			
			String active = "active";
			while(rs.next()) {
				
				list.add(new Aree(rs.getInt("id"), rs.getInt("id_customer"), rs.getString("description"),
						rs.getInt("id_store"), rs.getInt("perc_warning"), rs.getString("normal_color"),
						rs.getString("warning_color"), rs.getString("overthreshold_color"), rs.getInt("threshold"), active));
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
			sql = "select * from occ_aree where id = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, idCustomer);
			rs = st.executeQuery();
			
			String active = "active";
			while(rs.next()) {
				
				list.add(new Aree(rs.getInt("id"), rs.getInt("id_customer"), rs.getString("description"),
						rs.getInt("id_store"), rs.getInt("perc_warning"), rs.getString("normal_color"),
						rs.getString("warning_color"), rs.getString("overthreshold_color"), rs.getInt("threshold"), active));
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
		try{
		  String sql = "update occ_aree set description = ?, perc_warning = ?, normal_color = ?, "
		  		+ "warning_color = ?, overthreshold_color = ?, threshold = ?, ip_relay = ?, use_relay = ?, relay_cmd = ? "
		  		+ "where id = ? and id_customer = ? ";
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
	      st.setInt(10, this.id);
	      st.setInt(11, this.idCustomer);
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
			String sql = " insert into occ_aree (id_customer, description, id_store, "
					+ "perc_warning, normal_color, warning_color, overthreshold_color, threshold, ip_relay, use_relay, relay_cmd, id)" + 
					" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		    
		    String sId = Utility.getNewProgr(conn, "occ_aree" , "id", "where id_customer = "+this.idCustomer+" ");
		    if(sId.equals("1")) {
		    	sId = "10001";
		    }
		    st.setInt(12, Integer.parseInt(sId));
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
		  String sql = "delete from occ_aree where id = ? and id_customer = ? ";
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
	
}
	