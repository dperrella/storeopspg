package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Reason implements CRUD {
	private int id;
	private String description;
	private int idLoctypeIn;
	private int idLoctypeOut;
	private String dsLoctypeIn;
	private String dsLoctypeOut;
	private String sign;
	private int idCustomer;
	
	public Reason() {	}

	public Reason(int id, String description, int idLoctypeIn, int idLoctypeOut, 
			String dsLoctypeIn, String dsLoctypeOut, String sign, int idCustomer) {
		this.id = id;
		this.description = description;
		this.idLoctypeIn = idLoctypeIn;
		this.idLoctypeOut = idLoctypeOut;
		this.dsLoctypeIn = dsLoctypeIn;
		this.dsLoctypeOut = dsLoctypeOut;
		this.sign = sign;
		this.idCustomer = idCustomer;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getIdLoctypeIn() {
		return idLoctypeIn;
	}
	
	public void setIdLoctypeIn(int idLoctypeIn) {
		this.idLoctypeIn = idLoctypeIn;
	}
	
	public int getIdLoctypeOut() {
		return idLoctypeOut;
	}
	
	public void setIdLoctypeOut(int idLoctypeOut) {
		this.idLoctypeOut = idLoctypeOut;
	}
	
	public String getDsLoctypeIn() {
		return dsLoctypeIn;
	}
	public void setDsLoctypeIn(String dsLoctypeIn) {
		this.dsLoctypeIn = dsLoctypeIn;
	}
	
	public String getDsLoctypeOut() {
		return dsLoctypeOut;
	}
	public void setDsLoctypeOut(String dsLoctypeOut) {
		this.dsLoctypeOut = dsLoctypeOut;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
			sql = "select * from reason where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));
				setIdLoctypeIn(rs.getInt("idlocation_type_in"));
				setIdLoctypeOut(rs.getInt("idlocation_type_out"));
				setIdCustomer(rs.getInt("id_customer"));
				setSign(rs.getString("sign"));
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
	
	public static int getNrReasonByIdCustomer(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int nrReason = 0;
		try {
			sql = "select count(*) nr from reason where id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			if(rs.next()) {
				nrReason = rs.getInt("nr");
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
		return nrReason;
	}
	
	public static ArrayList<Reason> getReasonByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Reason> reasonList = null;
		try {
			reasonList = new ArrayList<>();
			sql = "select r.*, l1.description dsloctype_in, l2.description dsloctype_out from reason r "
					+ "left join location_type l1 on l1.id_locationtype = idlocation_type_in "
					+ "left join location_type l2 on l2.id_locationtype = idlocation_type_out "
					+ "where r.id_customer = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				reasonList.add(new Reason(rs.getInt("id"), rs.getString("description"), 
						rs.getInt("idlocation_type_in"), rs.getInt("idlocation_type_out"), 
						rs.getString("dsloctype_in"), rs.getString("dsloctype_out"), rs.getString("sign"),
						rs.getInt("id_customer")));
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
		return reasonList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update reason set description = ?, idlocation_type_in = ?, "
		  		+ "idlocation_type_out = ?, id_customer = ?, sign = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setInt(2, this.idLoctypeIn);
	      st.setInt(3, this.idLoctypeOut);
	      st.setInt(4, this.idCustomer);
	      st.setString(5, this.sign);
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

	
	@Override
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into reason (description, idlocation_type_in, idlocation_type_out, id_customer, sign)" + 
					" values (?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
			st.setInt(2, this.idLoctypeIn);
			st.setInt(3, this.idLoctypeOut);
			st.setInt(4, this.idCustomer);
			st.setString(5, this.sign);
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
		  String sql = "delete from reason where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.id);
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
