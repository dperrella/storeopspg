package pkg.occupancy.model2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import pkg.utility.Utility;
import pkg.beans.Tables;

public class Properties {

	private int id;
	private String key;
	private String description;
	private String value;
	private int tile;
	
	private String mailfrom;
	private String mailto;
	private String mailsmtp;
	private String mailport;
	private String mailuser;
	private String mailpass;
	private String template;
	private String mailsend;
	private boolean mailwrn;
	private boolean mailover;
	private String mailtype;
	private String hhopen;
	private String hhclose;
	private boolean msgwrn;
	private boolean machineLearning;
	
	private boolean enableNormal;
	private boolean enableWarning;
	private boolean enableError;
	private String normalSound;
	private String warningSound;
	private String errorSound;
	
	private String dataType;
	
	private String df;
	
	private String savedLicence;
	
	private String trafficLightCountType;
	
	private String tz;
	
	private String ntp;
	private String privacy;
	
	private String logo;
	
	private boolean reset1;
	private boolean reset2;
	
	private String res;
	
	private boolean avgqueue;
	
	private boolean sm;
	
	// opening hours
	private int day;
	private String daystring;
	private String fromHour;
	private String fromMinute;
	
	private String toHour;
	private String toMinute;
	private boolean h24;
	

	private int idCustomer;
	
	public Properties() {}
	
	public Properties(int id, String key, String description, String value, int  tile, int idCustomer) {
		this.id=id;
		this.key=key;
		this.description=description;
		this.value=value;
		this.tile=tile;
		this.idCustomer=idCustomer;
	}
	
	public Properties(String mailfrom, String mailto, String mailsmtp, String mailport,
			String mailuser, String mailpass, String template, String mailsend, boolean mailwrn, 
			boolean mailover, String mailtype, String hhopen, String hhclose, boolean msgwrn, 
			boolean machineLearning, boolean enableNormal, boolean enableWarning, boolean enableError,
			String normalSound, String warningSound, String errorSound, String dataType, String df, 
			String savedLicence, String trafficLightCountType, String tz, String ntp, String privacy,
			boolean reset1, boolean reset2, String logo, String res, boolean avgqueue, boolean sm, int idCustomer) {
		this.mailfrom=mailfrom;
		this.mailto=mailto;
		this.mailsmtp=mailsmtp;
		this.mailport=mailport;
		this.mailuser=mailuser;
		this.mailpass=mailpass;
		this.template=template;
		this.mailsend=mailsend;
		this.mailwrn=mailwrn;
		this.mailover=mailover;
		this.mailtype=mailtype;
		this.hhopen=hhopen;
		this.hhclose=hhclose;
		this.msgwrn=msgwrn;
		this.machineLearning=machineLearning;
		
		this.enableNormal=enableNormal;
		this.enableWarning=enableWarning;
		this.enableError=enableError;
		this.normalSound=normalSound;
		this.warningSound=warningSound;
		this.errorSound=errorSound;
		
		this.dataType=dataType;
		
		this.df=df;
		
		this.savedLicence=savedLicence;
		
		this.trafficLightCountType=trafficLightCountType;
		
		this.tz=tz;
		
		this.ntp=ntp;
		this.privacy=privacy;
		
		this.reset1=reset1;
		this.reset2=reset2;
		
		this.logo=logo;
		
		this.res=res;
		
		this.avgqueue=avgqueue;
		
		this.sm=sm;
		
		this.idCustomer=idCustomer;
		
	}
	
	public Properties(int day, String daystring, String fromHour, String fromMinute, String toHour, String toMinute, boolean h24, int idCustomer) {
		this.day=day;
		this.daystring=daystring;
		this.fromHour=fromHour;
		this.fromMinute=fromMinute;
		this.toHour=toHour;
		this.toMinute=toMinute;
		this.h24=h24;
		this.idCustomer=idCustomer;
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
	
	/////

	public String getMailfrom() {
		return mailfrom;
	}

	public void setMailfrom(String mailfrom) {
		this.mailfrom = mailfrom;
	}

	public String getMailto() {
		return mailto;
	}

	public void setMailto(String mailto) {
		this.mailto = mailto;
	}

	public String getMailsmtp() {
		return mailsmtp;
	}

	public void setMailsmtp(String mailsmtp) {
		this.mailsmtp = mailsmtp;
	}

	public String getMailport() {
		return mailport;
	}

	public void setMailport(String mailport) {
		this.mailport = mailport;
	}

	public String getMailuser() {
		return mailuser;
	}

	public void setMailuser(String mailuser) {
		this.mailuser = mailuser;
	}

	public String getMailpass() {
		return mailpass;
	}

	public void setMailpass(String mailpass) {
		this.mailpass = mailpass;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getMailsend() {
		return mailsend;
	}

	public void setMailsend(String mailsend) {
		this.mailsend = mailsend;
	}
	
	public boolean isMailwrn() {
		return mailwrn;
	}

	public void setMailwrn(boolean mailwrn) {
		this.mailwrn = mailwrn;
	}

	public boolean isMailover() {
		return mailover;
	}

	public void setMailover(boolean mailover) {
		this.mailover = mailover;
	}

	public String getMailtype() {
		return mailtype;
	}

	public void setMailtype(String mailtype) {
		this.mailtype = mailtype;
	}
	
	public String getHhopen() {
		return hhopen;
	}

	public void setHhopen(String hhopen) {
		this.hhopen = hhopen;
	}

	public String getHhclose() {
		return hhclose;
	}

	public void setHhclose(String hhclose) {
		this.hhclose = hhclose;
	}

	public boolean isMsgwrn() {
		return msgwrn;
	}

	public void setMsgwrn(boolean msgwrn) {
		this.msgwrn = msgwrn;
	}
	
	public boolean isMachineLearning() {
		return machineLearning;
	}

	public void setMachineLearning(boolean machineLearning) {
		this.machineLearning = machineLearning;
	}

	
	public boolean isEnableNormal() {
		return enableNormal;
	}

	public void setEnableNormal(boolean enableNormal) {
		this.enableNormal = enableNormal;
	}

	public boolean isEnableWarning() {
		return enableWarning;
	}

	public void setEnableWarning(boolean enableWarning) {
		this.enableWarning = enableWarning;
	}

	public boolean isEnableError() {
		return enableError;
	}

	public void setEnableError(boolean enableError) {
		this.enableError = enableError;
	}

	public String getNormalSound() {
		return normalSound;
	}

	public void setNormalSound(String normalSound) {
		this.normalSound = normalSound;
	}

	public String getWarningSound() {
		return warningSound;
	}

	public void setWarningSound(String warningSound) {
		this.warningSound = warningSound;
	}

	public String getErrorSound() {
		return errorSound;
	}

	public void setErrorSound(String errorSound) {
		this.errorSound = errorSound;
	}
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getDf() {
		return df;
	}

	public void setDf(String df) {
		this.df = df;
	}

	public String getSavedLicence() {
		return savedLicence;
	}

	public void setSavedLicence(String savedLicence) {
		this.savedLicence = savedLicence;
	}
	
	public String getTrafficLightCountType() {
		return trafficLightCountType;
	}

	public void setTrafficLightCountType(String trafficLightCountType) {
		this.trafficLightCountType = trafficLightCountType;
	}
	
	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}
	
	

	public String getNtp() {
		return ntp;
	}

	public void setNtp(String ntp) {
		this.ntp = ntp;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	
	

	public boolean isReset1() {
		return reset1;
	}

	public void setReset1(boolean reset1) {
		this.reset1 = reset1;
	}

	public boolean isReset2() {
		return reset2;
	}

	public void setReset2(boolean reset2) {
		this.reset2 = reset2;
	}
	
	

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}
	
	public boolean isAvgqueue() {
		return avgqueue;
	}

	public void setAvgqueue(boolean avgqueue) {
		this.avgqueue = avgqueue;
	}
	
	public boolean isSm() {
		return sm;
	}

	public void setSm(boolean sm) {
		this.sm = sm;
	}
	
	
	

	

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getDaystring() {
		return daystring;
	}

	public void setDaystring(String daystring) {
		this.daystring = daystring;
	}

	public String getFromHour() {
		return fromHour;
	}

	public void setFromHour(String fromHour) {
		this.fromHour = fromHour;
	}

	public String getFromMinute() {
		return fromMinute;
	}

	public void setFromMinute(String fromMinute) {
		this.fromMinute = fromMinute;
	}

	public String getToHour() {
		return toHour;
	}

	public void setToHour(String toHour) {
		this.toHour = toHour;
	}

	public String getToMinute() {
		return toMinute;
	}

	public void setToMinute(String toMinute) {
		this.toMinute = toMinute;
	}
	
	

	public boolean isH24() {
		return h24;
	}

	public void setH24(boolean h24) {
		this.h24 = h24;
	}

	
	
	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public void getSettings(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql = "select * from occ2_settings where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				setMailfrom(rs.getString("mailfrom"));
				setMailto(rs.getString("mailto"));
				setMailsmtp(rs.getString("mailsmtp"));
				setMailport(rs.getString("mailport"));
				setMailuser(rs.getString("mailuser"));
				setMailpass(rs.getString("mailpass"));
				setTemplate(rs.getString("template"));
				setMailsend(rs.getString("mailsend"));
				setMailwrn(rs.getBoolean("mailwrn"));
				setMailover(rs.getBoolean("mailover"));
				setMailtype(rs.getString("mailtype"));
				setHhopen(rs.getString("hhopen"));
				setHhclose(rs.getString("hhclose"));
				setMsgwrn(rs.getBoolean("msgwrn"));
				setMachineLearning(rs.getBoolean("machine_learning"));
				setEnableNormal(rs.getBoolean("enable_normal"));
				setEnableWarning(rs.getBoolean("enable_warning"));
				setEnableError(rs.getBoolean("enable_error"));
				setNormalSound(rs.getString("normal_sound"));
				setWarningSound(rs.getString("warning_sound"));
				setErrorSound(rs.getString("error_sound"));
				setDataType(rs.getString("data_type"));
				setDf(rs.getString("df"));
				setSavedLicence(rs.getString("saved_licence"));
				setTrafficLightCountType(rs.getString("traffic_light_count_type"));
				setTz(rs.getString("tz"));
				setNtp(rs.getString("ntp"));
				setPrivacy(rs.getString("privacy"));
				setReset1(rs.getBoolean("reset1"));
				setReset2(rs.getBoolean("reset2"));
				setLogo(rs.getString("logo"));
				setRes(rs.getString("res"));
				setAvgqueue(rs.getBoolean("avgqueue"));
				setSm(rs.getBoolean("sm"));
				
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
	
	

	public void setSettings(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			if(this.mailsend.equals("D")) {
				
				setMailfrom("donotreply@smart-control.it");
				setMailsmtp("smtps.aruba.it");
				setMailport("465");
				setMailuser("donotreply@smart-control.it");
				setMailpass("5m@rtC0ntr01");
				
			}
			
			sql = "update occ2_settings set mailfrom = ?, mailto = ?, mailsmtp = ?, mailport = ?, "
					+ "mailuser = ?, mailpass = ?, template = ?, mailsend = ?, mailwrn = ?, "
					+ "mailover = ?, mailtype = ?, hhopen = ?, hhclose = ?, msgwrn = ?, "
					+ "machine_learning = ?, enable_normal = ?, enable_warning = ?, enable_error = ?, "
					+ "data_type = ? , df = ?, traffic_light_count_type = ?, tz = ?, ntp = ?, privacy = ?, "
					+ "reset1 = ?, reset2 = ?, res = ?, avgqueue = ?, sm = ? where id_customer = ? " ;
			st = conn.prepareStatement(sql);
			st.setString(1, this.mailfrom);
			st.setString(2, this.mailto);
			st.setString(3, this.mailsmtp);
			st.setString(4, this.mailport);
			st.setString(5, this.mailuser);
			st.setString(6, this.mailpass);
			st.setString(7, this.template);
			st.setString(8, this.mailsend);
			st.setBoolean(9, this.mailwrn);
			st.setBoolean(10, this.mailover);
			st.setString(11, this.mailtype);
			st.setString(12, this.hhopen);
			st.setString(13, this.hhclose);
			st.setBoolean(14, this.msgwrn);
			st.setBoolean(15, this.machineLearning);
			st.setBoolean(16, this.enableNormal);
			st.setBoolean(17, this.enableWarning);
			st.setBoolean(18, this.enableError);
			st.setString(19, this.dataType);
			st.setString(20, this.df);
			st.setString(21, this.trafficLightCountType);
			st.setString(22, this.tz);
			st.setString(23, this.ntp);
			st.setString(24, this.privacy);
			st.setBoolean(25, this.reset1);
			st.setBoolean(26, this.reset2);
			st.setString(27, this.res);
			st.setBoolean(28, this.avgqueue);
			st.setBoolean(29, this.sm);
			st.setInt(30, this.idCustomer);
			
			
			st.executeUpdate();
			
			System.out.println(st.toString());
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
	}
	
	public static ArrayList<Properties> getProperties(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Properties> list = null;
		try {
			list = new ArrayList<>();
			sql = "select * from occ2_properties where id_customer = ? order by id ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				list.add(new Properties(rs.getInt("id"), rs.getString("key"),
						rs.getString("description"), rs.getString("value"),  rs.getInt("tile"), rs.getInt("idCustomer")));
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
	
	public static ArrayList<Properties> getPropertiesByTile(Connection conn,  int tile, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Properties> list = null;
		try {
			list = new ArrayList<>();
			sql = "select * from occ2_properties where tile = ? and id_customer = ? order by id ";
			st = conn.prepareStatement(sql);
			st.setInt(1, tile);
			st.setInt(2, idCustomer);
			rs = st.executeQuery();
			
			boolean hasLabel = false;
			boolean hasLabel2 = false;
			boolean hasLabel3 = false;
			
			while(rs.next()) {
				list.add(new Properties(rs.getInt("id"), rs.getString("key"),
						rs.getString("description"), rs.getString("value"), rs.getInt("tile"), rs.getInt("id_customer")));
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
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into occ2_properties (key, description, value, tile, id_customer)" + 
					" values (?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.key);
		    st.setString(2, this.description);
		    st.setString(3, this.value);
			st.setInt(4, this.tile);
			st.setInt(5, this.idCustomer);
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
	
	
	
	public void addByTile(Connection conn, int tile) {
		PreparedStatement st = null;
		try{
			String sql = " insert into occ2_properties (key, description, value, tile, int id_customer) " + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.key);
		    st.setString(2, this.description);
		    st.setString(3, this.value);
		    st.setInt(4, tile);
		    st.setInt(5, this.idCustomer);
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
		  String sql = "delete from occ2_properties where key = ? and id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.key);
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
		  String sql = "delete from occ2_properties where id_customer = ? and tile <> 1 ";
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
	
	public void deleteSet(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from occ2_settings where id_customer = ? ";
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
	
	public void deleteByTile(Connection conn, int tile, int idCustomer) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from occ2_properties where tile = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, tile);
	      st.setInt(2, idCustomer);
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
	
	public static void setOpeningHours(Connection conn, String hhopen, String hhclose, int idCustomer) {
		PreparedStatement st = null;
		try{
		  String sql = "update occ2_settings set hhopen = ?, hhclose = ? where id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setString(1, hhopen);
	      st.setString(2, hhclose);
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
	
	public static String getPropByKey(Connection conn, String key, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String value = "";
		try{
		  String sql = "select value from occ2_properties where key = ? and id_customer = ? ";
		  st = conn.prepareStatement(sql);
		  st.setString(1, key);
		  st.setInt(2, idCustomer);
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
	
	public static ArrayList<Tables> getTmz(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Tables> list = null;
		try {
			list = new ArrayList<>();
			sql = "select name, utc_offset from pg_timezone_names " + 
					"group by name,utc_offset " + 
					"order by utc_offset, name ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				list.add(new Tables("TMZ",rs.getString("name"),rs.getString("name"),rs.getString("utc_offset"),"","",""));
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
	
	public static void setPropertiesDft(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		String sql = "";
		try {
			
			sql = "insert into occ2_properties select "+idCustomer+" , * from occ2_properties_dft ";
			st = conn.prepareStatement(sql);
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
	
	public static void setHoursDft(Connection conn, int idCustomer) {
		
		String sql = "";
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			sql = "select count(0) num from occ2_dailyoperation_stores where id_customer = "+idCustomer+" ;";
			rs = st.executeQuery(sql);
			int num = 0;
			if(rs.next()) {
				num = rs.getInt("num");
			}
			rs.close();
			if(num==0) {
				
				sql = "select hhopen, hhclose from occ2_settings where id_customer = "+idCustomer+" ";
				rs = st.executeQuery(sql);
				String hhopen = "";
				String hhclose = "";
				if(rs.next()) {
					hhopen = ""+rs.getString("hhopen");
					hhclose = ""+rs.getString("hhclose");
				}
				rs.close();
				hhopen = Utility.noNull(hhopen);
				hhclose = Utility.noNull(hhclose);
				
				if(hhopen.length()==0)
					hhopen = "09:00:00";
				if(hhclose.length()==0)
					hhclose = "22:00:00";
				
				
				sql = "insert into occ2_dailyoperation_stores (day, fromhour, tohour, id_customer) "
						+ "values "
						+ "(0, '"+hhopen+"', '"+hhclose+"', "+idCustomer+"),"
						+ "(1, '"+hhopen+"', '"+hhclose+"', "+idCustomer+"),"
						+ "(2, '"+hhopen+"', '"+hhclose+"', "+idCustomer+"),"
						+ "(3, '"+hhopen+"', '"+hhclose+"', "+idCustomer+"),"
						+ "(4, '"+hhopen+"', '"+hhclose+"', "+idCustomer+"),"
						+ "(5, '"+hhopen+"', '"+hhclose+"', "+idCustomer+"),"
						+ "(6, '"+hhopen+"', '"+hhclose+"', "+idCustomer+") ";
				st.executeUpdate(sql);		
			}
		
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
	
//	public void getSettingsDft(Connection conn) {
//		
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String sql = "";
//		
//		try {
//			
//			sql = "select * from occ_settings_dft ";
//			st = conn.prepareStatement(sql);
//			rs = st.executeQuery();
//			if(rs.next()) {
//				setMailfrom(rs.getString("mailfrom"));
//				setMailto(rs.getString("mailto"));
//				setMailsmtp(rs.getString("mailsmtp"));
//				setMailport(rs.getString("mailport"));
//				setMailuser(rs.getString("mailuser"));
//				setMailpass(rs.getString("mailpass"));
//				setTemplate(rs.getString("template"));
//				setMailsend(rs.getString("mailsend"));
//				setMailwrn(rs.getBoolean("mailwrn"));
//				setMailover(rs.getBoolean("mailover"));
//				setMailtype(rs.getString("mailtype"));
//				setHhopen(rs.getString("hhopen"));
//				setHhclose(rs.getString("hhclose"));
//				setMsgwrn(rs.getBoolean("msgwrn"));
//				setMachineLearning(rs.getBoolean("machine_learning"));
//				setEnableNormal(rs.getBoolean("enable_normal"));
//				setEnableWarning(rs.getBoolean("enable_warning"));
//				setEnableError(rs.getBoolean("enable_error"));
//				setNormalSound(rs.getString("normal_sound"));
//				setWarningSound(rs.getString("warning_sound"));
//				setErrorSound(rs.getString("error_sound"));
//				setDataType(rs.getString("data_type"));
//				setDf(rs.getString("df"));
//				
//			}
//			
//			rs.close();
//			
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				if(st!=null)
//					st.close();
//			}
//			catch(Exception e) {}
//		}
//	}
	
	public static void setSettingsDft(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		String sql = "";
		try {
			
			sql = "insert into occ2_settings select "+idCustomer+" , * from occ2_settings_dft ";
			st = conn.prepareStatement(sql);
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
	
	public static void updateOpeningHour(Connection conn, int day, String fromhour, String tohour, boolean h24, int idCustomer) {
		
		PreparedStatement st = null;
		String sql = "";
		try {
			sql = "update occ2_dailyoperation_stores set fromhour = ?, tohour = ?, h24 = ?, lastupdate = now() where day = ? and id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, fromhour);
			st.setString(2, tohour);
			st.setBoolean(3, h24);
			st.setInt(4, day);
			st.setInt(5, idCustomer);
			st.executeUpdate();
			
			System.out.println(st.toString());
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
		
	}
	
	public static ArrayList<Properties> getOpeningHours(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Properties> list = null;
		try {
			list = new ArrayList<>();
			sql = "select * from occ2_dailyoperation_stores where id_customer = ? order by day ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				String time1[] = rs.getString("fromhour").split(":");
				String time2[] = rs.getString("tohour").split(":");
				
				String sDay = "";
				switch(rs.getInt("day")) {
				case 0:
					sDay = "Monday";
					break;
				case 1:
					sDay = "Tuesday";
					break;
				case 2:
					sDay = "Wednesday";
					break;
				case 3:
					sDay = "Thursday";
					break;
				case 4:
					sDay = "Friday";
					break;
				case 5:
					sDay = "Saturday";
					break;
				case 6:
					sDay = "Sunday";
					break;
				default:
					sDay = "CAZZO!!!";
					break;
						
						
				}
				
				list.add(new Properties(rs.getInt("day"), sDay, time1[0], time1[1], time2[0], time1[2], rs.getBoolean("h24"), idCustomer));
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
}
