package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Products {
	private String code;
	private String description;
	private String sku;
	private String epc;
	private int idCustomer;
	
	public Products() {	}

	public Products(String code, String description, String sku, String epc, int idCustomer) {
		this.code = code;
		this.description = description;
		this.sku = sku;
		this.epc = epc;
		this.idCustomer = idCustomer;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getEpc() {
		return epc;
	}
	
	public void setEpc(String epc) {
		this.epc = epc;
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
			sql = "select * from products where sku = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, sku);
			rs = st.executeQuery();
			if(rs.next()) {
				setCode(rs.getString("code"));
				setDescription(rs.getString("description"));				
				setIdCustomer(rs.getInt("id_customer"));
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
	
	public static ArrayList<Products> getProductsByIdCustomer(Connection conn, int idCustomer, String sku){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Products> siteList = null;
		try {
			siteList = new ArrayList<>();
			sql = "select * from products where id_customer = ? and sku like '%?%' ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			st.setString(2, sku);
			
			rs = st.executeQuery();
			while(rs.next()) {
				siteList.add(new Products(rs.getString("code"), rs.getString("description"), rs.getString("sku"), "", rs.getInt("id_customer")));
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
		return siteList;
	}
	
	
	public static ArrayList<Products> getEpcProductsByIdCustomer(Connection conn, int idCustomer, String sku){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Products> siteList = null;
		try {
			siteList = new ArrayList<>();
			sql = "select p.*, epc from products p "
					+ "left join stockdiary on sku = barcode where id_customer = ? and sku = ? ";
			
			sql = "select p.*, epc from stockdiary "
					+ "left join products p on sku = barcode where barcode = ? ";
			st = conn.prepareStatement(sql);
			//st.setInt(1, idCustomer);
			st.setString(1, sku);
			
			rs = st.executeQuery();
			while(rs.next()) {
				siteList.add(new Products(rs.getString("code"), rs.getString("description"), rs.getString("sku"), rs.getString("epc"), rs.getInt("id_customer")));
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
		return siteList;
	}
	
	
	

	
}
