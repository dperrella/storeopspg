package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

public class Rfid_step {
	private int idflow;
	private int idstep;
	private int levelnum;
	private String description;
	private String origin_location;
	private String destination_location;
	private String config_before;
	private String config_during;
	private String config_after;
	private boolean enabled;
	private String extra_doc_top;
	private String extra_doc_detail;
	private String extra;
	private String extra_language;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Rfid_step() {}

	public Rfid_step(int idflow, int idstep, int levelnum, String description, String origin_location, 
			String destination_location, String config_before,
			String config_during, String config_after,
			boolean enabled, String extra_doc_top, String extra_doc_detail, String extra, String extra_language, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.idflow=idflow;
		this.idstep=idstep;
		this.levelnum=levelnum;
		this.description=description;
		this.origin_location=origin_location;
		this.destination_location=destination_location;
		this.config_before=config_before;
		this.config_during=config_during;
		this.config_after=config_after;
		this.enabled=enabled;
		this.extra_doc_top=extra;
		this.extra_doc_detail=extra_language;
		this.extra=extra;
		this.extra_language=extra_language;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getIdflow() {
		return idflow;
	}
	public void setIdflow(int idflow) {
		this.idflow = idflow;
	}
	
	public int getIdstep() {
		return idstep;
	}
	public void setIdstep(int idstep) {
		this.idstep = idstep;
	}
	
	public int getLevelnum() {
		return levelnum;
	}
	public void setLevelnum(int levelnum) {
		this.levelnum = levelnum;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOrigin_location() {
		return origin_location;
	}
	public void setOrigin_location(String origin_location) {
		this.origin_location = origin_location;
	}
	
	public String getDestination_location() {
		return destination_location;
	}
	public void setDestination_location(String destination_location) {
		this.destination_location = destination_location;
	}
	
	public String getConfig_before() {
		return config_before;
	}
	public void setConfig_before(String config_before) {
		this.config_before = config_before;
	}
	
	public String getConfig_during() {
		return config_during;
	}
	public void setConfig_during(String config_during) {
		this.config_during = config_during;
	}
	
	public String getConfig_after() {
		return config_after;
	}
	public void setConfig_after(String config_after) {
		this.config_after = config_after;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getExtra_doc_top() {
		return extra_doc_top;
	}
	public void setExtra_doc_top(String extra_doc_top) {
		this.extra_doc_top = extra_doc_top;
	}
	
	public String getExtra_doc_detail() {
		return extra_doc_detail;
	}
	public void setExtra_doc_detail(String extra_doc_detail) {
		this.extra_doc_detail = extra_doc_detail;
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
			sql = "select * from step where idflow = ? and idstep = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idflow);
			st.setInt(2, idstep);
			
			rs = st.executeQuery();
			if(rs.next()) {
				
				setLevelnum(rs.getInt("levelnum"));
				setDescription(rs.getString("description"));
				setOrigin_location(rs.getString("origin_location"));
				setDestination_location(rs.getString("destination_location"));
				setConfig_before(rs.getString("config_before"));
				setConfig_during(rs.getString("config_during"));
				setConfig_after(rs.getString("config_after"));
				setEnabled(rs.getBoolean("enabled"));
				setExtra_doc_top(rs.getString("extra_doc_top"));
				setExtra_doc_detail(rs.getString("extra_doc_detail"));
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
	
	public List<Rfid_step> selectAll(Connection conn, String q) {
		
		List<Rfid_step> step = new ArrayList<Rfid_step>();
		step.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from step ";
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
				
				step.add(new Rfid_step(rs.getInt("idflow"),rs.getInt("idstep"), rs.getInt("levelnum"), 
						rs.getString("description"),rs.getString("origin_location"),rs.getString("destination_location"),
						rs.getString("config_before"),rs.getString("config_during"),
						rs.getString("config_after"),rs.getBoolean("enabled"),
						rs.getString("extra_doc_top"),rs.getString("extra_doc_detail"),
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
		
		return step;
		
	}
	
	public List<Rfid_step> selectFlowSteps(Connection conn, int idflow) {
		
		List<Rfid_step> step = new ArrayList<Rfid_step>();
		step.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from step where idflow = ? ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			
			st.setInt(1, idflow);
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				step.add(new Rfid_step(rs.getInt("idflow"),rs.getInt("idstep"), rs.getInt("levelnum"), 
						rs.getString("description"),rs.getString("origin_location"),rs.getString("destination_location"),
						rs.getString("config_before"),rs.getString("config_during"),
						rs.getString("config_after"),rs.getBoolean("enabled"),
						rs.getString("extra_doc_top"),rs.getString("extra_doc_detail"),
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
		
		return step;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
	
			sql   = "insert into step "
					+ "(idflow, levelnum, description, origin_location, destination_location, "
					+ "config_before, config_during, config_after, "
					+ "enabled, extra_doc_top, extra_doc_detail, extra, extra_language) "
					+ "values "+
					"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idflow);
			st.setInt(2, levelnum);
			st.setString(3, description);
			
			if(origin_location.length()>0) {
				PGobject jsonFld = new PGobject();
				jsonFld.setType("json");
				jsonFld.setValue(origin_location);
				st.setObject(4, jsonFld);
			}
			else {
				st.setObject(4, null);
			}
			
			if(destination_location.length()>0) {
				PGobject jsonFld = new PGobject();
				jsonFld.setType("json");
				jsonFld.setValue(destination_location);
				st.setObject(5, jsonFld);
			}
			else {
				st.setObject(5, null);
			}
			
			if(config_before.length()>0) {
				PGobject jsonFld = new PGobject();
				jsonFld.setType("json");
				jsonFld.setValue(config_before);
				st.setObject(6, jsonFld);
			}
			else {
				st.setObject(6, null);
			}
			
			if(config_during.length()>0) {
				PGobject jsonFld = new PGobject();
				jsonFld.setType("json");
				jsonFld.setValue(config_during);
				st.setObject(7, jsonFld);
			}
			else {
				st.setObject(7, null);
			}
			
			if(config_after.length()>0) {
				PGobject jsonFld = new PGobject();
				jsonFld.setType("json");
				jsonFld.setValue(config_after);
				st.setObject(8, jsonFld);
			}
			else {
				st.setObject(8, null);
			}
			
			
			st.setBoolean(9, enabled);
			
			if(extra_doc_top.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra_doc_top);
				st.setObject(10, jsonExtra);
			}
			else {
				st.setObject(10, null);
			}
			
			if(extra_doc_detail.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra_doc_detail);
				st.setObject(11, jsonExtra);
			}
			else {
				st.setObject(11, null);
			}
			
			if(extra.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(12, jsonExtra);
			}
			else {
				st.setObject(12, null);
			}
			
			if(extra_language.length()>0) {
				PGobject jsonExtraLanguage = new PGobject();
				jsonExtraLanguage.setType("json");
				jsonExtraLanguage.setValue(extra_language);
				st.setObject(13, jsonExtraLanguage);
			}
			else {
				st.setObject(13, null);
			}
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
	
	public void copy(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
	
			sql   = "insert into step "
					+ "(idflow, levelnum, description, origin_location, destination_location, "
					+ "config_before, config_during, config_after, "
					+ "enabled, extra_doc_top, extra_doc_detail, extra, extra_language) "
					+ "select idflow, levelnum, description, origin_location, destination_location, "
					+ "config_before, config_during, config_after, "
					+ "enabled, extra_doc_top, extra_doc_detail, extra, extra_language "
					+ "from step where idflow = ? and idstep = ? ";
			System.out.println(sql);
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idflow);
			st.setInt(2, idstep);
			
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
	
	public void update(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "update step "
					+ "set levelnum = ?, description = ?, origin_location = ?, destination_location = ?, "
					+ "config_before = ?, config_during = ?, config_after = ?, "
					+ "enabled = ?, extra_doc_top = ?, extra_doc_detail = ?, extra = ?, extra_language = ? "+
					"where idflow = ? and idstep = ?";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, levelnum);
			st.setString(2, description);
			
			if(origin_location.length()>0) {
				PGobject jsonFld = new PGobject();
				jsonFld.setType("json");
				jsonFld.setValue(origin_location);
				st.setObject(3, jsonFld);
			}
			else {
				st.setObject(3, null);
			}
			
			if(destination_location.length()>0) {
				PGobject jsonFld = new PGobject();
				jsonFld.setType("json");
				jsonFld.setValue(destination_location);
				st.setObject(4, jsonFld);
			}
			else {
				st.setObject(4, null);
			}
			
			if(config_before.length()>0) {
				PGobject jsonConfig = new PGobject();
				jsonConfig.setType("json");
				jsonConfig.setValue(config_before);
				st.setObject(5, jsonConfig);
			}
			else {
				st.setObject(5, null);
			}
			
			if(config_during.length()>0) {
				PGobject jsonConfig = new PGobject();
				jsonConfig.setType("json");
				jsonConfig.setValue(config_during);
				st.setObject(6, jsonConfig);
			}
			else {
				st.setObject(6, null);
			}
			
			if(config_after.length()>0) {
				PGobject jsonConfig = new PGobject();
				jsonConfig.setType("json");
				jsonConfig.setValue(config_after);
				st.setObject(7, jsonConfig);
			}
			else {
				st.setObject(7, null);
			}
			
			st.setBoolean(8, enabled);
			
			if(extra_doc_top.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra_doc_top);
				st.setObject(9, jsonExtra);
			}
			else {
				st.setObject(9, null);
			}
			
			if(extra_doc_detail.length()>0) {
				PGobject jsonExtraLanguage = new PGobject();
				jsonExtraLanguage.setType("json");
				jsonExtraLanguage.setValue(extra_doc_detail);
				st.setObject(10, jsonExtraLanguage);
			}
			else {
				st.setObject(10, null);
			}
			
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
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra_language);
				st.setObject(12, jsonExtra);
			}
			else {
				st.setObject(12, null);
			}
			
			st.setInt(13, idflow);
			st.setInt(14, idstep);
			
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
			
			sql   = "delete from step "+
					"where idflow = ? and idstep = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idflow);
			st.setInt(2, idstep);
			
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
		String sql = "";
	    
		try {
			
			sql   = "delete from step "+
					"where idflow = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idflow);
			
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
