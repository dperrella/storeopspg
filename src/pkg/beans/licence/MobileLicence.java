package pkg.beans.licence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MobileLicence implements CRUD {
	private int idApp;
	private String appName;
	private int idCustomer;
	private String accountCode;
	private String description;
	private String fromDate;
	private String macAddr;
	
	public MobileLicence() {	}

	public MobileLicence(int idApp, int idCustomer, String fromDate, String macAddr) {
		this.idApp = idApp;
		this.idCustomer = idCustomer;
		this.fromDate = fromDate;
		this.macAddr = macAddr;
	}
	
	public MobileLicence(int idApp, String appName, int idCustomer, String accountCode, String description, String fromDate, String macAddr) {
		this.idApp = idApp;
		this.appName = appName;
		this.idCustomer = idCustomer;
		this.accountCode = accountCode;
		this.description = description;
		this.fromDate = fromDate;
		this.macAddr = macAddr;
	}
	
	public int getIdApp() {
		return idApp;
	}

	public void setIdApp(int idApp) {
		this.idApp = idApp;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select l.*, c.account_code, c.description, a.name from zmobile_licence l "
					+ "join zmobile_app a on a.id = l.idapp "
					+ "join customers c on c.id = l.idcustomer "
					+ "where idapp = ? and idcustomer = ? and macaddr = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idApp);
			st.setInt(2, idCustomer);
			st.setString(3, macAddr);
			
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setAccountCode(rs.getString("account_code"));
				setFromDate(rs.getString("from_date"));
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
	
	public static ArrayList<MobileLicence> getLicences(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<MobileLicence> licenceList = null;
		try {
			licenceList = new ArrayList<>();
			sql = "select l.*, c.account_code, c.description, a.name from zmobile_licence l "
					+ "join zmobile_app a on a.id = l.idapp "
					+ "join customers c on c.id = l.idcustomer ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				licenceList.add(new MobileLicence(rs.getInt("idapp"), rs.getString("name"), 
						rs.getInt("idcustomer"), rs.getString("account_code"), 
						rs.getString("description"), rs.getString("from_date"), rs.getString("macaddr")));
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
		return licenceList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update zmobile_licence set from_date = ? where idapp = ? and idcustomer = ? and mac = ? ";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setDate(1, java.sql.Date.valueOf(this.getFromDate()));
	      st.setInt(2, this.idApp);
	      st.setInt(3, this.idCustomer);
	      st.setString(4, this.macAddr);
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
			String sql = " insert into zmobile_licence (idapp, idcustomer, from_date, macaddr)" + 
					" values (?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idApp);
			st.setInt(2, this.idCustomer);
			st.setDate(3, java.sql.Date.valueOf(this.getFromDate()));
			st.setString(4, this.macAddr);
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
		  String sql = "delete from zmobile_licence where idapp = ? and idcustomer = ? and macaddr = ?  ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idApp);
	      st.setInt(2, this.idCustomer);
	      st.setString(3, this.macAddr);
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
