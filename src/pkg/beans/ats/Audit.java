package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Audit implements CRUD {
	private int idAudit;
	private String name;
	private int idType;
	private String creationDate;
	private String closingDate;
	private int state;
	private int idStore;
	private String typeDescription;
	private String storeDescription;
	private int idCustomer;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	public Audit() {	}

	public Audit(int idAudit, String name, int idType, String creationDate, String closingDate,
			int state, int idStore, String typeDescription, String storeDescription, int idCustomer) {
		this.idAudit = idAudit;
		this.name = name;
		this.idType = idType;
		this.creationDate = creationDate;
		this.closingDate = closingDate;
		this.state = state;
		this.idStore = idStore;
		this.typeDescription = typeDescription;
		this.storeDescription = storeDescription;
		this.idCustomer = idCustomer;
	}
	
	public int getIdAudit() {
		return idAudit;
	}

	public void setIdAudit(int idAudit) {
		this.idAudit = idAudit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIdStore() {
		return idStore;
	}

	public void setIdStore(int idStore) {
		this.idStore = idStore;
	}
	
	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getStoreDescription() {
		return storeDescription;
	}

	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select a.*, s.description as storedescription, t.name as typedescription from ats.audit a "
					+ "join ats.audit_type t on t.id = a.idtype "
					+ "left join stores s on s.id = a.idstore "
					+ " where a.id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idAudit);
			System.out.println(st.toString());
			
			rs = st.executeQuery();
			if(rs.next()) {
				setName(rs.getString("name"));	
				setIdType(rs.getInt("idtype"));
				setCreationDate(df.format(rs.getDate("creation_date")));
				setClosingDate(df.format(rs.getDate("closing_date")));
				setState(rs.getInt("state"));
				setIdStore(rs.getInt("idstore"));
				
				setTypeDescription(rs.getString("typedescription"));	
				setStoreDescription(rs.getString("storedescription"));	
				
				setIdCustomer(rs.getInt("idcustomer"));
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
	
	public static ArrayList<Audit> getAudits(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Audit> auditList = null;
		try {
			auditList = new ArrayList<>();
			sql = "select a.*, s.description as storedescription, t.name as typedescription from ats.audit a " + 
					"join ats.audit_type t on t.id = a.idtype " + 
					"left join stores s on s.id = a.idstore where a.state = 0 order by a.id desc";
			System.out.println(sql);
			
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				auditList.add(new Audit(rs.getInt("id"), rs.getString("name"), rs.getInt("idtype"),df2.format(rs.getDate("creation_date")),
					df2.format(rs.getDate("closing_date")),rs.getInt("state"),rs.getInt("idstore"), rs.getString("typedescription"), 
					rs.getString("storedescription"), rs.getInt("idcustomer")));
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
		return auditList;
	}
	
	public static ArrayList<Audit> getAuditsByUser(Connection conn, String idusr, String state){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Audit> auditList = null;
		try {
			auditList = new ArrayList<>();
			String cond = "";
			if(state.equals("0")||state.equals("1")) {
				cond = "and a.state = "+state+" ";
			}
			sql = "select a.*, s.description as storedescription, t.name as typedescription from ats.audit a " + 
					"join ats.audit_type t on t.id = a.idtype " + 
					"left join stores s on s.id = a.idstore "+
					"where a.idcustomer in(select idcustomer from rel_user_customers where idusr = '"+idusr+"') "+cond+" "+
					"order by a.state";
			System.out.println(sql);
			
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				auditList.add(new Audit(rs.getInt("id"), rs.getString("name"), rs.getInt("idtype"),df2.format(rs.getDate("creation_date")),
					df2.format(rs.getDate("closing_date")),rs.getInt("state"),rs.getInt("idstore"), rs.getString("typedescription"), 
					rs.getString("storedescription"), rs.getInt("idcustomer")));
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
		return auditList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.audit set name = ?, idtype = ?, creation_date = ?, idcustomer = ?, idstore = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setInt(2, this.idType);
	      st.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
	      st.setInt(4, this.idCustomer);
	      st.setInt(5, this.idStore);
	      
	      st.setInt(6, this.idAudit);
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
			Date date = null;
			if(this.closingDate.length()==10)
				date=df.parse(this.closingDate);  
			
			String sql = " insert into ats.audit (name, idtype, creation_date, closing_date, state, idstore, idcustomer)" + 
					" values (?,?,?,?,?,?,?) returning id ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
			st.setInt(2, this.idType);
			st.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
			st.setTimestamp(4, new java.sql.Timestamp(date.getTime()));
			st.setInt(5, this.state);
			st.setInt(6, this.idStore);
			st.setInt(7, this.idCustomer);
	        // execute the java preparedstatement
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				this.setIdAudit(rs.getInt("id"));
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

	
	public void close(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.audit set state = ?, closing_date = ? where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.state);
	      st.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
	      st.setInt(3, this.idAudit);
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
			
			String sql = "delete from ats.audit_item where idaudit = ? ";
		      st = conn.prepareStatement(sql);
		      st.setInt(1, this.idAudit);
		      // execute the java preparedstatement
		      st.executeUpdate();
		      st.close();
		  
		      sql = "delete from ats.audit where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idAudit);
	      // execute the java preparedstatement
	      st.executeUpdate();
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

	
}
