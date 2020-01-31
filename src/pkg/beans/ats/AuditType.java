package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AuditType implements CRUD {
	private int idType;
	private String name;
	
	public AuditType() {	}

	public AuditType(int idType, String name) {
		this.idType = idType;
		this.name = name;
	}
	
	public int getIdType() {
		return idType;
	}
	
	public void setIdType(int idType) {
		this.idType = idType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from ats.audit_type where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idType);
			rs = st.executeQuery();
			if(rs.next()) {
				setName(rs.getString("name"));	
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
	
	public static ArrayList<AuditType> getAuditType(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<AuditType> typeList = null;
		try {
			typeList = new ArrayList<>();
			sql = "select * from ats.audit_type ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				typeList.add(new AuditType(rs.getInt("id"), rs.getString("name")));
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
		return typeList;
	}
	
	public static String getTypeNameById(Connection conn, int id){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		String name = "";
		try {
			
			sql = "select name from ats.audit_type where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
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
		return name;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.audit_type set name = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setInt(2, this.idType);
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

	
	@Override
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into ats.audit_type (name)" + 
					" values (?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
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

	
	@Override
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from ats.audit_type where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idType);
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
