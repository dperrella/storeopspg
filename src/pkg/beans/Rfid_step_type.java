package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

public class Rfid_step_type {
	private int idsteptype;
	private String description;
	private String config_steptype;
	private String extra;
	private String extra_language;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Rfid_step_type() {}

	public Rfid_step_type(int idsteptype, String description, String config_steptype, String extra, String extra_language, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.idsteptype=idsteptype;
		this.description=description;
		this.config_steptype=config_steptype;
		this.extra=extra;
		this.extra_language=extra_language;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getIdsteptype() {
		return idsteptype;
	}
	public void setIdsteptype(int idsteptype) {
		this.idsteptype = idsteptype;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getConfig_steptype() {
		return config_steptype;
	}
	public void setConfig_steptype(String config_steptype) {
		this.config_steptype = config_steptype;
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
			sql = "select * from step_type where idsteptype = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idsteptype);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setDescription(rs.getString("description"));
				setConfig_steptype(rs.getString("config_steptype"));
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
	
	public List<Rfid_step_type> selectAll(Connection conn, String q) {
		
		List<Rfid_step_type> sty = new ArrayList<Rfid_step_type>();
		sty.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from step_type ";
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
				
				sty.add(new Rfid_step_type(rs.getInt("idsteptype"),rs.getString("description"),rs.getString("config_steptype"),
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
		
		return sty;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "insert into step_type "+
					"(description, config_steptype, extra, extra_language) "+
					"values "+
					"(?, ?, ?, ?) RETURNING idsteptype ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, description);
			
			if(config_steptype.length()>0) {
				PGobject jsonConfig_steptype = new PGobject();
				jsonConfig_steptype.setType("json");
				jsonConfig_steptype.setValue(config_steptype);
				st.setObject(2, jsonConfig_steptype);
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
				idsteptype = rs.getInt(1);
			}
			else {
				idsteptype = -1;
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
					"set description = ?, config_steptype = ?, extra = ?, extra_language = ? "+
					"where idsteptype = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, description);
			
			if(config_steptype.length()>0) {
				PGobject jsonConfig_steptype = new PGobject();
				jsonConfig_steptype.setType("json");
				jsonConfig_steptype.setValue(config_steptype);
				st.setObject(2, jsonConfig_steptype);
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
			
			st.setInt(5, idsteptype);
			
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
			
			sql   = "delete from step_type "+
					"where iddevicetype = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idsteptype);
			
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
