package pkg.beans.HR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pkg.utility.Utility;


public class Departments {
	private String id;
	private String description;
	private String parentid;
	private String address;
	private String locality;
	private String province;
	private String zipcode;
	private String state;
	private String phone;
	private String email;
	private String department_code;
	private String minnum_intervals;
	private String tollerance;
	private String nocturne;
	private int id_store;
	private int department_type;
	private int id_customer;
	private boolean hidetrasf;
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Departments() {}

	public Departments(String id, String description, String parentid, String address, String locality, 
			String province, String zipcode, String state, String phone, String email, String department_code,
			String minnum_intervals, String tollerance, String nocturne,
			int id_store, int department_type, int id_customer, boolean hidetrasf,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		this.id=id;
		this.description=description;
		this.parentid=parentid;
		this.address=address;
		this.locality=locality;
		this.province=province;
		this.zipcode=zipcode;
		this.state=state;
		this.phone=phone;
		this.email=email;
		this.department_code=department_code;
		this.minnum_intervals=minnum_intervals;
		this.tollerance=tollerance;
		this.nocturne=nocturne;
		
		this.id_store=id_store;
		this.department_type=department_type;
		this.id_customer=id_customer;
		this.hidetrasf=hidetrasf;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public boolean isHidetrasf() {
		return hidetrasf;
	}

	public void setHidetrasf(boolean hidetrasf) {
		this.hidetrasf = hidetrasf;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDepartment_code() {
		return department_code;
	}
	public void setDepartment_code(String department_code) {
		this.department_code = department_code;
	}
	
	public String getMinnum_intervals() {
		return minnum_intervals;
	}
	public void setMinnum_intervals(String minnum_intervals) {
		this.minnum_intervals = minnum_intervals;
	}
	
	public String getTollerance() {
		return tollerance;
	}
	public void setTollerance(String tollerance) {
		this.tollerance = tollerance;
	}
	
	public String getNocturne() {
		return nocturne;
	}
	public void setNocturne(String nocturne) {
		this.nocturne = nocturne;
	}
	
	public int getId_store() {
		return id_store;
	}
	public void setId_store(int id_store) {
		this.id_store = id_store;
	}
	
	public int getDepartment_type() {
		return department_type;
	}
	public void setDepartment_type(int department_type) {
		this.department_type = department_type;
	}
	
	public int getId_customer() {
		return id_customer;
	}
	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
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
	
	public void insert() {
		
		Connection conn = null;
		PreparedStatement st = null;
		String sql = "";
	    
		try {
	
			conn = Utility.getConnHR();
			
			sql = "INSERT INTO public.hr_departments("
					+ "description, department_code, minnum_intervals, tollerance, nocturne, "
					+ "id_store, department_type, id_customer) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, description);	
			st.setString(2, department_code);
			st.setInt(3, Integer.parseInt(minnum_intervals));
			st.setInt(4, Integer.parseInt(tollerance));
			st.setInt(5, Integer.parseInt(nocturne));
			st.setInt(6, id_store);
			st.setInt(7, department_type);
			st.setInt(8, id_customer);
			
			// Esegue l'sql
			st.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
	}
	
	public void update() {
		
		Connection conn = null;
		PreparedStatement st = null;
		String sql = "";
	    
		try {
	
			conn = Utility.getConnHR();
			
			sql = "update public.hr_departments set description = ?, department_code = ? where id_store = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, description);
			st.setString(2, department_code);
			st.setInt(3, id_store);
			
			// Esegue l'sql
			st.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
	}
	
	public void delete() {
		
		Connection conn = null;
		PreparedStatement st = null;
		String sql = "";
	    
		try {
	
			conn = Utility.getConnHR();
			
			sql = "delete from public.hr_departments where id_store = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_store);
			
			// Esegue l'sql
			st.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
	}
}
