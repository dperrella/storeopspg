package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Tables {
	private String xcdtb;
	private String xcdel;
	private String xdtab;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Tables() {}

	public Tables(String xcdtb, String xcdel, String xdtab, String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.xcdtb=xcdtb;
		this.xcdel=xcdel;
		this.xdtab=xdtab;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getXcdtb() {
		return xcdtb;
	}
	public void setXcdtb(String xcdtb) {
		this.xcdtb = xcdtb;
	}

	public String getXcdel() {
		return xcdel;
	}
	public void setXcdel(String xcdel) {
		this.xcdel = xcdel;
	}
	
	public String getXdtab() {
		return xdtab;
	}
	public void setXdtab(String xdtab) {
		this.xdtab = xdtab;
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
	
	public List<Tables> selectCustomers(Connection conn, String username) {
		
		List<Tables> t = new ArrayList<Tables>();
		t.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			// dichiariamo l'sql
			sql = "select id, description, account_code from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = ?) ";
			
			// passiamo sql a statement
			st = conn.prepareStatement(sql);
			
			st.setString(1, username);
			
			// leggiamo il resultset
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				t.add(new Tables("CUS",rs.getString("id"),rs.getString("description"),rs.getString("account_code"),"","",""));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return t;
	}
}
