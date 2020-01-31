package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

public class Rfid_devices {
	private int iddevice;
	private String name;
	private String description;
	private String ip;
	private int port;
	private String mac;
	private int devicetype;
	private String config_device;
	private boolean enabled;
	private boolean grouped;
	private String program_name;
	private String extra;
	private String extra_language;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Rfid_devices() {}

	public Rfid_devices(int iddevice, String name, String description, String ip, 
			int port, String mac, int devicetype, String config_device,
			boolean enabled, boolean grouped, String program_name,
			String extra, String extra_language, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.iddevice=iddevice;
		this.name=name;
		this.description=description;
		this.ip=ip;
		this.port=port;
		this.mac=mac;
		this.devicetype=devicetype;
		this.config_device=config_device;
		this.enabled=enabled;
		this.grouped=grouped;
		this.program_name=program_name;
		this.extra=extra;
		this.extra_language=extra_language;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getIddevice() {
		return iddevice;
	}
	public void setIddevice(int iddevice) {
		this.iddevice = iddevice;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public int getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(int devicetype) {
		this.devicetype = devicetype;
	}
	
	public String getConfig_device() {
		return config_device;
	}
	public void setConfig_device(String config_device) {
		this.config_device = config_device;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean getGrouped() {
		return grouped;
	}
	public void setGrouped(boolean grouped) {
		this.grouped = grouped;
	}
	
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public String getExtra_language() {
		return extra_language;
	}
	public void setExtra_language(String extra_language) {
		this.extra_language = extra_language;
	}
	
	public String getCmp1() {
		return cmp1;
	}
	public void setCmp1(String cmp1) {
		this.cmp1 = cmp1;
	}
	
	public String getCmp2() {
		return cmp2;
	}
	public void setCmp2(String cmp2) {
		this.cmp2 = cmp2;
	}
	
	public String getCmp3() {
		return cmp3;
	}
	public void setCmp3(String cmp3) {
		this.cmp3 = cmp3;
	}
	
	public String getCmp4() {
		return cmp4;
	}
	public void setCmp4(String cmp4) {
		this.cmp4 = cmp4;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from devices where iddevice = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, iddevice);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setName(rs.getString("name"));
				setDescription(rs.getString("description"));
				setIp(rs.getString("ip"));
				setPort(rs.getInt("port"));
				setMac(rs.getString("mac"));
				setDevicetype(rs.getInt("devicetype"));
				setConfig_device(rs.getString("config_device"));
				setEnabled(rs.getBoolean("enabled"));
				setGrouped(rs.getBoolean("grouped"));
				setProgram_name(rs.getString("program_name"));
				setExtra(rs.getString("extra"));
				setExtra_language(rs.getString("extra_language"));
				
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
	
	public void selectRowFromMac(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from devices where mac = ? ";
			System.out.println(mac);
			st = conn.prepareStatement(sql);
			st.setString(1, mac);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setIddevice(rs.getInt("iddevice"));
				setName(rs.getString("name"));
				setDescription(rs.getString("description"));
				setIp(rs.getString("ip"));
				setPort(rs.getInt("port"));
				setMac(rs.getString("mac"));
				setDevicetype(rs.getInt("devicetype"));
				setConfig_device(rs.getString("config_device"));
				setEnabled(rs.getBoolean("enabled"));
				setGrouped(rs.getBoolean("grouped"));
				setProgram_name(rs.getString("program_name"));
				setExtra(rs.getString("extra"));
				setExtra_language(rs.getString("extra_language"));
				
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
	
	public List<Rfid_devices> selectAll(Connection conn, String q) {
		
		List<Rfid_devices> dev = new ArrayList<Rfid_devices>();
		dev.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from devices ";
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" where name||description like ? ";
			}
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				dev.add(new Rfid_devices(rs.getInt("iddevice"),rs.getString("name"),rs.getString("description"),
						rs.getString("ip"),rs.getInt("port"),rs.getString("mac"),
						rs.getInt("devicetype"),rs.getString("config_device"),rs.getBoolean("enabled"),
						rs.getBoolean("grouped"),rs.getString("program_name"),
						rs.getString("extra"),rs.getString("extra_language"),""+count,"","",""));
				
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
		
		return dev;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
	
			sql   = "insert into devices "+
					"(name, description, ip, port, mac, "+
					"devicetype, config_device, enabled, grouped, "+
					"program_name, "+
					"extra, extra_language) "+
					"values "+
					"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING iddevice ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, name);
			st.setString(2, description);
			st.setString(3, ip);
			st.setInt(4, port);
			st.setString(5, mac);
			st.setInt(6, devicetype);
			
			System.out.println(config_device);
			
			if(config_device.length()>0) {
				
				System.out.println("1");
				
				PGobject jsonConfig_device = new PGobject();
				jsonConfig_device.setType("json");
				jsonConfig_device.setValue(config_device);
				st.setObject(7, jsonConfig_device);
			}
			else {
				st.setObject(7, null);
			}
			
			st.setBoolean(8, enabled);
			st.setBoolean(9, grouped);
			st.setString(10, program_name);
			
			System.out.println(extra);
			
			if(extra.length()>0) {
				System.out.println("2");
				
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(11, jsonExtra);
			}
			else {
				st.setObject(11, null);
			}
			
			System.out.println(extra_language);
			
			if(extra_language.length()>0) {
				System.out.println("3");
				
				PGobject jsonExtraLanguage = new PGobject();
				jsonExtraLanguage.setType("json");
				jsonExtraLanguage.setValue(extra_language);
				st.setObject(12, jsonExtraLanguage);
			}
			else {
				st.setObject(12, null);
			}
			// Esegue l'sql
			rs = st.executeQuery();
			
			if(rs.next()) {
				iddevice = rs.getInt(1);
			}
			else {
				iddevice = -1;
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
	
	public void update(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
	
			sql   = "update devices "+
					"set name = ?, description = ?, ip = ?, port = ?, mac = ?, "+
					"devicetype = ?, config_device = ?, enabled = ?, grouped = ?, "+
					"program_name = ?, extra = ?, extra_language = ? "+
					"where iddevice = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, name);
			st.setString(2, description);
			st.setString(3, ip);
			st.setInt(4, port);
			st.setString(5, mac);
			st.setInt(6, devicetype);
			
			
			System.out.println(config_device);
			
			if(config_device.length()>0) {
				PGobject jsonConfig_device = new PGobject();
				jsonConfig_device.setType("json");
				jsonConfig_device.setValue(config_device);
				st.setObject(7, jsonConfig_device);
			}
			else {
				st.setObject(7, null);
			}
			
			
			System.out.println(iddevice+" enabled:"+enabled);

			st.setBoolean(8, enabled);
			st.setBoolean(9, grouped);
			st.setString(10, program_name);
			
			if(extra.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(11, jsonExtra);
			}
			else {
				st.setObject(11, null);
			}
			
			if(extra_language.length()>0) {
				PGobject jsonExtraLanguage = new PGobject();
				jsonExtraLanguage.setType("json");
				jsonExtraLanguage.setValue(extra_language);
				st.setObject(12, jsonExtraLanguage);
			}
			else {
				st.setObject(12, null);
			}
			
			st.setInt(13, iddevice);
			
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
		String sql = "";
	    
		try {
			
			sql   = "delete from devices "+
					"where iddevice = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, iddevice);
			
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
