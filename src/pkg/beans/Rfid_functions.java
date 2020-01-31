package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;


public class Rfid_functions {
	private int idfunctions;
	private String description;
	private int type;
	private String name;
	private String parameters;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Rfid_functions() {}

	public Rfid_functions(int idfunctions, String description, int type, String name, String parameters, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.idfunctions=idfunctions;
		this.description=description;
		this.type=type;
		this.name=name;
		this.parameters=parameters;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getIdfunctions() {
		return idfunctions;
	}
	public void setIdfunctions(int idfunctions) {
		this.idfunctions = idfunctions;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
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
			sql = "select * from functions where idfunctions = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idfunctions);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setDescription(rs.getString("description"));
				setType(rs.getInt("type"));
				setName(rs.getString("name"));
				setParameters(rs.getString("parameters"));
				
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
	
	public void selectFunctionByName(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from functions where name = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, name);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setIdfunctions(rs.getInt("idfunctions"));
				setDescription(rs.getString("description"));
				setType(rs.getInt("type"));
				setParameters(rs.getString("parameters"));
				
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
	
	public List<Rfid_functions> selectAll(Connection conn, String q) {
		
		List<Rfid_functions> fun = new ArrayList<Rfid_functions>();
		fun.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from functions ";
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" where nama||description like ? ";
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
				
				fun.add(new Rfid_functions(rs.getInt("idfunctions"),rs.getString("description"),rs.getInt("type"),
						rs.getString("name"),rs.getString("parameters"),""+count,"","",""));
				
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
		
		return fun;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
	
			sql   = "insert into functions "+
					"(idfunctions,description, type, name, parameters) "+
					"values "+
					"(?, ?, ?, ?, ?) RETURNING idfunctions ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idfunctions);
			st.setString(2, description);
			st.setInt(3, type);
			st.setString(4, name);
			
			if(parameters.length()>0) {
				PGobject jsonPar = new PGobject();
				jsonPar.setType("json");
				jsonPar.setValue(parameters);
				st.setObject(5, jsonPar);
			}
			else {
				st.setObject(5, null);
			}
			
			// Esegue l'sql
			rs = st.executeQuery();
			
			if(rs.next()) {
				idfunctions = rs.getInt(1);
			}
			else {
				idfunctions = -1;
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
			
			sql   = "update functions "+
					"set description = ?, type = ?, name = ?, parameters = ? "+
					"where idfunctions = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, description);
			st.setInt(2, type);
			st.setString(3, name);
			
			if(parameters.length()>0) {
				PGobject jsonPar = new PGobject();
				jsonPar.setType("json");
				jsonPar.setValue(parameters);
				st.setObject(4, jsonPar);
			}
			else {
				st.setObject(4, null);
			}
			
			st.setInt(5, idfunctions);
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
			
			sql   = "delete from functions "+
					"where idfunctions = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idfunctions);
			
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
