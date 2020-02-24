package pkg.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pkg.beans.ats.Barcode;
import pkg.beans.ats.Item;
import pkg.beans.ats.RelItemCustomer;

public class ItemsImport {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		try {
		
			conn = Utility.getConnData();
			sql = "select * from ats.items_import ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				
				Item i = new Item();
				i.setName(rs.getString("name"));
				i.setDescription(Utility.noNull(rs.getString("description")));
				i.setIdVendor(Integer.parseInt(rs.getString("idvendor")));
				i.setIdBrand(Integer.parseInt(rs.getString("idbrand")));
				i.setIdCategory(Integer.parseInt(rs.getString("idcategory")));
				i.setCountry(Integer.parseInt(rs.getString("idcountry")));
				i.setItemProtected(Integer.parseInt(rs.getString("protected")));
				i.setIdusr(rs.getString("idusr"));
				
				System.out.println("add item "+rs.getString("name"));
				
				i.add(conn);
				
				int idItem = i.getIdItem();
				
				String barcodes = ""+rs.getString("barcodes");
				barcodes = Utility.noNull(barcodes);
				if(barcodes.length()>0) {
					
					String temp[] = barcodes.split(",");
					for(int b=0;b<temp.length;b++) {
						
						System.out.println("add barcode "+temp[b]);
						Barcode bc = new Barcode();
						bc.setIdItem(idItem);
						bc.setBarcode(temp[b]);
						bc.add(conn);
						
					}
				}
				
				String customers = ""+rs.getString("idcustomer");
				customers = Utility.noNull(customers);
				if(customers.length()>0) {
					
					String temp[] = customers.split(",");
					for(int c=0;c<temp.length;c++) {
						
						// get idcustomer from account_code
						int idCustomer = getIdCustomerFromAccountCode(conn, temp[c]);
						if(idCustomer < 0)
							idCustomer = 273; // CheckPointItalyLab
						
						System.out.println("add customer "+idCustomer+" ["+temp[c]+"]");
						
						RelItemCustomer ric = new RelItemCustomer();
						ric.setIdItem(idItem);
						ric.setIdCustomer(idCustomer);
						ric.add(conn);
						
					}
				}
				
				System.out.println("--------");
					
			}
			rs.close();
			ps.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn!=null)
				conn.close();
		}
		
	}
	
	private static int getIdCustomerFromAccountCode(Connection conn, String accountCode) {
		
		int idCustomer = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select id from customers where account_code = ? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, accountCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				idCustomer = rs.getInt("id");
			}
			rs.close();
			ps.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return idCustomer;
		
	}

}
