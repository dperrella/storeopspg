package pkg.occupancy.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
			String normalSound, String warningSound, String errorSound, String dataType, String df, int idCustomer) {
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
			
			sql = "select * from occ_settings where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idCustomer);
			
			System.out.println(st.toString());
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
	
	public static void setSettingsDft(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		String sql = "";
		try {
			
			sql = "insert into occ_settings select "+idCustomer+" , * from occ_settings_dft ";
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
	
	public static void setPropertiesDft(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		String sql = "";
		try {
			
			sql = "insert into occ_properties select "+idCustomer+" , * from occ_properties_dft ";
			st = conn.prepareStatement(sql);
			st.executeUpdate();
			
			String accountId = getAccountId(conn, idCustomer);
			
			
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
	
	public void getSettingsDft(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql = "select * from occ_settings_dft ";
			st = conn.prepareStatement(sql);
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
		String sql = "";
		
		try {
			if(this.mailsend.equals("D")) {
				
				setMailfrom("donotreply@smart-control.it");
				setMailsmtp("smtps.aruba.it");
				setMailport("465");
				setMailuser("donotreply@smart-control.it");
				setMailpass("5m@rtC0ntr01");
				
			}
			
			sql = "update occ_settings set mailfrom = ?, mailto = ?, mailsmtp = ?, mailport = ?, "
					+ "mailuser = ?, mailpass = ?, template = ?, mailsend = ?, mailwrn = ?, "
					+ "mailover = ?, mailtype = ?, hhopen = ?, hhclose = ?, msgwrn = ?, "
					+ "machine_learning = ?, enable_normal = ?, enable_warning = ?, enable_error = ?, "
					+ "data_type = ? , df = ? where id_customer = ? ";
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
			st.setInt(21, this.idCustomer);
			
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
			sql = "select * from occ_properties where id_customer = ? order by id ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				list.add(new Properties(rs.getInt("id"), rs.getString("key"),
						rs.getString("description"), rs.getString("value"),  rs.getInt("tile"),rs.getInt("id_customer")));
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
			sql = "select * from occ_properties where tile = ? and id_customer = ? order by id ";
			st = conn.prepareStatement(sql);
			st.setInt(1, tile);
			st.setInt(2, idCustomer);
			rs = st.executeQuery();
			
		
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
			String sql = " insert into occ_properties (key, description, value, tile, id_customer, id)" + 
					" values (?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.key);
		    st.setString(2, this.description);
		    st.setString(3, this.value);
			st.setInt(4, this.tile);
			st.setInt(5, this.idCustomer);
			st.setInt(6, this.id);
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
		  String sql = "delete from occ_properties where id_customer = ? ";
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
	
	public static String getAccountId(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String accountId = "";
		try{
		  String sql = "select account_code from customers where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, idCustomer);
	      rs = st.executeQuery();
	      if(rs.next()) {
	    	  accountId = rs.getString("account_code");
	      }
	      rs.close();
	      st.close();
	      
	      sql = "update occ_properties set value = ? where key = ? and id_customer = ? ";
	      st = conn.prepareStatement(sql);
	      st.setString(1, accountId);
	      st.setString(2, "ACCOUNTID");
	      st.setInt(3, idCustomer);
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
		return accountId;
	}
	
}
