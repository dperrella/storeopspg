package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;


public class Rfid_location {
	private int idlocation;
	private int id_warehouse;
	private int id_loctype;
	private String description;
	private String extra;
	private String extra_language;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Rfid_location() {}

	public Rfid_location(int idlocation, int id_warehouse, int id_loctype, String description, String extra, String extra_language, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.idlocation=idlocation;
		this.id_warehouse=id_warehouse;
		this.id_loctype=id_loctype;
		this.description=description;
		this.extra=extra;
		this.extra_language=extra_language;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getIdlocation() {
		return idlocation;
	}
	public void setIdlocation(int idlocation) {
		this.idlocation = idlocation;
	}
	
	public int getId_warehouse() {
		return id_warehouse;
	}
	public void setId_warehouse(int id_warehouse) {
		this.id_warehouse = id_warehouse;
	}

	public int getId_loctype() {
		return id_loctype;
	}
	public void setId_loctype(int id_loctype) {
		this.id_loctype = id_loctype;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
			sql = "select * from location where idlocation = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idlocation);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setId_warehouse(rs.getInt("id_warehouse"));
				setId_loctype(rs.getInt("id_loctype"));
				setDescription(rs.getString("description"));
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
	
	public List<Rfid_location> selectAll(Connection conn, String q) {
		
		List<Rfid_location> loc = new ArrayList<Rfid_location>();
		loc.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from location ";
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
				
				loc.add(new Rfid_location(rs.getInt("idlocation"),rs.getInt("id_warehouse"),rs.getInt("id_loctype"),rs.getString("description"),
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
		
		return loc;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
	
			sql   = "insert into location "+
					"(id_warehouse,description, extra, extra_language,id_loctype) "+
					"values "+
					"(?, ?, ?, ?, ?) RETURNING idlocation ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_warehouse);
			st.setString(2, description);
			
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
			st.setInt(5, id_loctype);
			// Esegue l'sql
			rs = st.executeQuery();
			
			if(rs.next()) {
				idlocation = rs.getInt(1);
			}
			else {
				idlocation = -1;
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
			
			sql   = "update location "+
					"set id_warehouse = ?, description = ?, extra = ?, extra_language = ?, id_loctype = ? "+
					"where idlocation = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_warehouse);
			st.setString(2, description);
			
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
			st.setInt(5, id_loctype);
			
			st.setInt(6, idlocation);
			
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
			
			sql   = "delete from location "+
					"where idlocation = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idlocation);
			
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
