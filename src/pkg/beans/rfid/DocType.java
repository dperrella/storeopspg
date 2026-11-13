package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DocType implements CRUD {
	private int idDoctype;
	private String description;
	private int idCustomer;
	private boolean canChangeLocation;
	private boolean asExpected;
	private boolean canOverload;
	
	
	public DocType() {	}

	public DocType(int idDoctype, String description, int idCustomer) {
		this.idDoctype = idDoctype;
		this.description = description;
		this.idCustomer = idCustomer;
	}
	
	public DocType(int idDoctype, String description, int idCustomer, 
			boolean canChangeLocation,boolean asExpected,boolean canOverload) {
		this.idDoctype = idDoctype;
		this.description = description;
		this.idCustomer = idCustomer;
		this.canChangeLocation = canChangeLocation;
		this.asExpected = asExpected;
		this.canOverload = canOverload;
	}
	
	public int getIdDoctype() {
		return idDoctype;
	}
	
	public void setIdDoctype(int idDoctype) {
		this.idDoctype = idDoctype;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	
	
	public boolean isCanChangeLocation() {
		return canChangeLocation;
	}

	public void setCanChangeLocation(boolean canChangeLocation) {
		this.canChangeLocation = canChangeLocation;
	}

	public boolean isAsExpected() {
		return asExpected;
	}

	public void setAsExpected(boolean asExpected) {
		this.asExpected = asExpected;
	}

	public boolean isCanOverload() {
		return canOverload;
	}

	public void setCanOverload(boolean canOverload) {
		this.canOverload = canOverload;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from documents_type where id_doctype = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idDoctype);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setIdCustomer(rs.getInt("id_customer"));
				
				setCanChangeLocation(rs.getBoolean("can_change_location"));
				setAsExpected(rs.getBoolean("as_expected"));
				setCanOverload(rs.getBoolean("can_overload"));
				
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
	
	public static int getNrDocTypeByIdCustomer(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int nrDoctype = 0;
		try {
			sql = "select count(*) nr from documents_type where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				nrDoctype = rs.getInt("nr");
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
		return nrDoctype;
	}
	
	public static ArrayList<DocType> getDoctypeByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<DocType> doctypeList = null;
		try {
			doctypeList = new ArrayList<>();
			sql = "select * from documents_type where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				//doctypeList.add(new DocType(rs.getInt("id_doctype"), rs.getString("description"), rs.getInt("id_customer")));
				
				doctypeList.add(new DocType(rs.getInt("id_doctype"), rs.getString("description"), rs.getInt("id_customer"),
						rs.getBoolean("can_change_location"), rs.getBoolean("as_expected"), rs.getBoolean("can_overload")));
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
		return doctypeList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update documents_type set description = ?, id_customer = ? where id_doctype = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.idCustomer);
	      st.setInt(3, this.idDoctype);
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
			String sql = " insert into documents_type (description, id_customer)" + 
					" values (?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
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

	
	@Override
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from documents_type where id_doctype = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idDoctype);
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

	public static ArrayList<DocType> getDoctypeByReason(Connection conn, int idReason){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<DocType> doctypeList = null;
		try {
			doctypeList = new ArrayList<>();
			sql = "select id_doctype, description, id_customer from documents_type t "
					+ "join rel_doctype_reason r on r.id_doctype = t.id_doctype where id_reason = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idReason);
			rs = st.executeQuery();
			while(rs.next()) {
				doctypeList.add(new DocType(rs.getInt("id_doctype"), rs.getString("description"), rs.getInt("id_customer")));
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
		return doctypeList;
	}
	
	public static ArrayList<DocType> getDoctypeByCustomerReason(Connection conn, String idCustomerReason){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<DocType> doctypeList = null;
		try {
			doctypeList = new ArrayList<>();
			sql = "select id_doctype, description, id_customer from documents_type t "
					+ "join rel_doctype_customer_reason r on r.id_doctype = t.id_doctype where id_customer_reason = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, idCustomerReason);
			rs = st.executeQuery();
			while(rs.next()) {
				doctypeList.add(new DocType(rs.getInt("id_doctype"), rs.getString("description"), rs.getInt("id_customer")));
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
		return doctypeList;
	}
	
	
	
	public void insertRelCusrea(Connection conn, String id_customer_reason) {
		PreparedStatement st = null;
		try{
		  String sql = "insert into rel_doctype_customer_reason (id_doctype, id_customer_reason) values (?, ?)";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idDoctype);
	      st.setString(2, id_customer_reason);
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
	
	public void insertRelRea(Connection conn, int id_reason) {
		PreparedStatement st = null;
		try{
		  String sql = "insert into rel_doctype_reason (id_doctype, id_reason) values (?, ?)";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idDoctype);
	      st.setInt(2, id_reason);
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
	
	
	public void deleteRelCusrea(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from rel_doctype_customer_reason where id_doctype = ?";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idDoctype);
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
	
	public void deleteRelRea(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from rel_doctype_reason where id_doctype = ?";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idDoctype);
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
	
	public void addDoctype(Connection conn) {
		PreparedStatement st = null;
		ResultSet rs = null;
		int doctype = -1;
		try{
			String sql = " insert into documents_type (description, id_customer)" + 
					" values (?, ?) returning id_doctype ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
	        st.setInt(2, this.idCustomer);
			// execute the java preparedstatement
			rs = st.executeQuery();
			if(rs.next()) {
				doctype = rs.getInt(1);
			}
			rs.close();
			
			setIdDoctype(doctype);
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
	
	public static String selectCusreaByDoctype(Connection conn, int idDoctype) {
		
		String ids = "";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from rel_doctype_customer_reason " + 
					"where id_doctype = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idDoctype);
			rs = st.executeQuery();
			while(rs.next()) {
				ids+=rs.getString("id_customer_reason")+",";
			}
			rs.close();
			if(ids.length()>0)
				ids = ids.substring(0, ids.length()-1);
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
		return ids;
		
	}
	
	public static String selectReaByDoctype(Connection conn, int idDoctype) {
		
		String ids = "";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from rel_doctype_reason " + 
					"where id_doctype = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idDoctype);
			rs = st.executeQuery();
			while(rs.next()) {
				ids+=rs.getString("id_reason")+",";
			}
			rs.close();
			if(ids.length()>0)
				ids = ids.substring(0, ids.length()-1);
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
		return ids;
		
	}
}
