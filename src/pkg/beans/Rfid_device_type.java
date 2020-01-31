package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

public class Rfid_device_type {
	private int iddevicetype;
	private String description;
	private String config_devicetype;
	private String extra;
	private String extra_language;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Rfid_device_type() {}

	public Rfid_device_type(int iddevicetype, String description, String config_devicetype, String extra, String extra_language, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.iddevicetype=iddevicetype;
		this.description=description;
		this.config_devicetype=config_devicetype;
		this.extra=extra;
		this.extra_language=extra_language;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getIddevicetype() {
		return iddevicetype;
	}
	public void setIddevicetype(int iddevicetype) {
		this.iddevicetype = iddevicetype;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getConfig_devicetype() {
		return config_devicetype;
	}
	public void setConfig_devicetype(String config_devicetype) {
		this.config_devicetype = config_devicetype;
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
			sql = "select * from device_type where iddevicetype = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, iddevicetype);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setDescription(rs.getString("description"));
				setConfig_devicetype(rs.getString("config_devicetype"));
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
	
	public List<Rfid_device_type> selectAll(Connection conn, String q) {
		
		List<Rfid_device_type> dt = new ArrayList<Rfid_device_type>();
		dt.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from device_type ";
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" where description like ? ";
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
				
				dt.add(new Rfid_device_type(rs.getInt("iddevicetype"),rs.getString("description"),rs.getString("config_devicetype"),
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
		
		return dt;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "insert into device_type "+
					"(description, config_devicetype, extra, extra_language) "+
					"values "+
					"(?, ?, ?, ?) RETURNING iddevicetype ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, description);
			
			if(config_devicetype.length()>0) {
				PGobject jsonConfig_devicetype = new PGobject();
				jsonConfig_devicetype.setType("json");
				jsonConfig_devicetype.setValue(config_devicetype);
				st.setObject(2, jsonConfig_devicetype);
			}
			else {
				st.setObject(2, null);
			}
			
			if(extra.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(3, jsonExtra);
			}
			else {
				st.setObject(3, null);
			}
			
			if(extra_language.length()>0) {
				PGobject jsonExtraLanguage = new PGobject();
				jsonExtraLanguage.setType("json");
				jsonExtraLanguage.setValue(extra_language);
				st.setObject(4, jsonExtraLanguage);
			}
			else {
				st.setObject(4, null);
			}
			// Esegue l'sql
			rs = st.executeQuery();
			
			if(rs.next()) {
				iddevicetype = rs.getInt(1);
			}
			else {
				iddevicetype = -1;
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
			
			sql   = "update device_type "+
					"set description = ?, config_devicetype = ?, extra = ?, extra_language = ? "+
					"where iddevicetype = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, description);
			
			if(config_devicetype.length()>0) {
				PGobject jsonConfig_devicetype = new PGobject();
				jsonConfig_devicetype.setType("json");
				jsonConfig_devicetype.setValue(config_devicetype);
				st.setObject(2, jsonConfig_devicetype);
			}
			else {
				st.setObject(2, null);
			}
			
			if(extra.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(3, jsonExtra);
			}
			else {
				st.setObject(3, null);
			}
			
			if(extra_language.length()>0) {
				PGobject jsonExtraLanguage = new PGobject();
				jsonExtraLanguage.setType("json");
				jsonExtraLanguage.setValue(extra_language);
				st.setObject(4, jsonExtraLanguage);
			}
			else {
				st.setObject(4, null);
			}
			
			st.setInt(5, iddevicetype);
			
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
			
			sql   = "delete from device_type "+
					"where iddevicetype = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, iddevicetype);
			
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
