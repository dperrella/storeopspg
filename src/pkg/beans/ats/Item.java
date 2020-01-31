package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Item implements CRUD {
	private int idItem;
	private String name;
	private String description;
	private int idVendor;
	private int idBrand;
	private int idCategory;
	private int country;
	private int itemProtected;
	private String evReportLink;
	
	private String vendorName;
	private String brandName;
	private String categoryName;
	private String countryName;
	
	private String idusr;
	
	private String barcodes;
	private String retailers;
	
	
	public Item() {	}

	public Item(int idItem, String name, String description, int idVendor, int idBrand,
			int idCategory, int country, int itemProtected, String evReportLink,
			String vendorName, String brandName, String categoryName, String countryName, 
			String idusr, String barcodes, String retailers) {
		this.idItem = idItem;
		this.name = name;
		this.description = description;
		this.idVendor = idVendor;
		this.idBrand = idBrand;
		this.idCategory = idCategory;
		this.country = country;
		this.itemProtected = itemProtected;
		this.evReportLink = evReportLink;
		
		this.vendorName = vendorName;
		this.brandName = brandName;
		this.categoryName = categoryName;
		this.countryName = countryName;
		
		this.idusr = idusr;
		this.barcodes = barcodes;
		this.retailers = retailers;
	}
	
	
	
	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdVendor() {
		return idVendor;
	}

	public void setIdVendor(int idVendor) {
		this.idVendor = idVendor;
	}

	public int getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public int getItemProtected() {
		return itemProtected;
	}

	public void setItemProtected(int itemProtected) {
		this.itemProtected = itemProtected;
	}

	public String getEvReportLink() {
		return evReportLink;
	}

	public void setEvReportLink(String evReportLink) {
		this.evReportLink = evReportLink;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getIdusr() {
		return idusr;
	}

	public void setIdusr(String idusr) {
		this.idusr = idusr;
	}
	
	public String getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(String barcodes) {
		this.barcodes = barcodes;
	}

	public String getRetailers() {
		return retailers;
	}

	public void setRetailers(String retailers) {
		this.retailers = retailers;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select i.*, v.name as vendorname, "
					+ "b.name as brandname, c.name as categoryname, n.name as countryname "
					+ "from ats.items i "
					+ "left join ats.vendor v on v.id = i.idvendor "
					+ "left join ats.brand b on b.id = i.idbrand "
					+ "left join ats.category c on v.id = i.idcategory "
					+ "left join ats.country n on n.id = i.country "
					+ " where i.id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idItem);
			rs = st.executeQuery();
			if(rs.next()) {
				setName(rs.getString("name"));	
				setDescription(rs.getString("description"));
				setIdVendor(rs.getInt("idvendor"));
				setIdBrand(rs.getInt("idbrand"));
				setIdCategory(rs.getInt("idcategory"));
				setCountry(rs.getInt("country"));
				setItemProtected(rs.getInt("protected"));
				setEvReportLink(rs.getString("evolution_report_link"));
				
				setVendorName(rs.getString("vendorname"));
				setBrandName(rs.getString("brandname"));
				setCategoryName(rs.getString("categoryname"));
				setCountryName(rs.getString("countryname"));
				
				setIdusr(rs.getString("idusr"));
				
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
	
	public static ArrayList<Item> getItems(Connection conn, int isProtected){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Item> itemsList = null;
		try {
			itemsList = new ArrayList<>();
			sql = "select i.*, v.name as vendorname, " 
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "where protected = "+isProtected+" "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name "
					+ "order by id desc ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				
				String bb = rs.getString("barcodes").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String b[] = bb.split(",");
				ArrayList<String> b2 = new ArrayList<String>();
				String bbb = "";
				for(int i=0;i<b.length;i++) {
					if(!b2.contains(b[i].trim())) {
						b2.add(b[i].trim());
						bbb+=b[i]+",";
					}
				}
				
				if(bbb.length()>0)
					bbb = bbb.substring(0,bbb.length()-1);
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers")));
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
		return itemsList;
	}
	
	public static ArrayList<Item> getItemsByUser(Connection conn, int isProtected, String idusr){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Item> itemsList = null;
		try {
			
			// read prefs
			sql = "select idcustomer from pref_user_customers where idusr = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, idusr);
			rs = st.executeQuery();
			int count=0;
			String cus = "";
			String cond = "";
			while(rs.next()) {
				count++;
				cus+=rs.getString("idcustomer")+",";
			}
			rs.close();
			st.close();
			if(cus.length()>0)
				cus = cus.substring(0,cus.length()-1);
			
			if(cus.length()>0)
				cond = "and r2.idcustomer in ("+cus+") ";
			
			System.out.println(cond);
			
			itemsList = new ArrayList<>();
			sql = "select i.*, v.name as vendorname, " 
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "where protected = "+isProtected+" "
					+ "and i.id in(select iditem from ats.rel_item_customer r1 " 
					+ "join rel_user_customers r2 on r1.idcustomer = r2.idcustomer " 
					+ "where r2.idusr = '"+idusr+"' "+cond+" ) "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name "
					+ "order by id desc ";
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				
				String bb = rs.getString("barcodes").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String b[] = bb.split(",");
				ArrayList<String> b2 = new ArrayList<String>();
				String bbb = "";
				for(int i=0;i<b.length;i++) {
					if(!b2.contains(b[i].trim())) {
						b2.add(b[i].trim());
						bbb+=b[i]+",";
					}
				}
				
				if(bbb.length()>0)
					bbb = bbb.substring(0,bbb.length()-1);
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers")));
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
		return itemsList;
	}
	
	public static ArrayList<Item> getItemsByCustomer(Connection conn, int isProtected, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Item> itemsList = null;
		try {
			itemsList = new ArrayList<>();
			sql = "select i.*, "
					+ "v.name as vendorname, " 
					+ "b.name as brandname, "
					+ "c.name as categoryname, "
					+ "n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "where protected = "+isProtected+" "
					+ "and i.id in(select iditem from ats.rel_item_customer where idcustomer = ?) "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name "
					+ "order by id desc ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				
				String bb = rs.getString("barcodes").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String b[] = bb.split(",");
				ArrayList<String> b2 = new ArrayList<String>();
				String bbb = "";
				for(int i=0;i<b.length;i++) {
					if(!b2.contains(b[i].trim())) {
						b2.add(b[i].trim());
						bbb+=b[i]+",";
					}
				}
				
				if(bbb.length()>0)
					bbb = bbb.substring(0,bbb.length()-1);
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers")));
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
		return itemsList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.items set name = ?, description = ?, idvendor = ?, idbrand = ?, idcategory = ?, "
		  		+ "country = ?, protected = ?, evolution_report_link = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.name);
	      st.setString(2, this.description);
	      st.setInt(3, this.idVendor);
	      st.setInt(4, this.idBrand);
	      st.setInt(5, this.idCategory);
	      st.setInt(6, this.country);
	      st.setInt(7, this.itemProtected);
	      st.setString(8, this.evReportLink);
	      st.setInt(9, this.idItem);
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
			String sql = " insert into ats.items (name, description, idvendor, idbrand, idcategory, country, protected, evolution_report_link, idusr)" + 
					" values (?,?,?,?,?,?,?,?,?) returning id ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.name);
			st.setString(2, this.description);
			st.setInt(3, this.idVendor);
			st.setInt(4, this.idBrand);
			st.setInt(5, idCategory);
			st.setInt(6, this.country);
			st.setInt(7, this.itemProtected);
			st.setString(8, this.evReportLink);
			st.setString(9,  this.idusr);
	        // execute the java preparedstatement
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				this.setIdItem(rs.getInt("id"));
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

	
	@Override
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  	 String sql = "delete from ats.barcode where iditem = ? ";
		     st = conn.prepareStatement(sql);
		     st.setInt(1, this.idItem);
		     // execute the java preparedstatement
		     st.executeUpdate();
		     st.close();
		     
		     sql = "delete from ats.photo where iditem = ? ";
		     st = conn.prepareStatement(sql);
		     st.setInt(1, this.idItem);
		     // execute the java preparedstatement
		     st.executeUpdate();
		     st.close();
		     
			 sql = "delete from ats.items where id = ? ";
		     st = conn.prepareStatement(sql);
		     st.setInt(1, this.idItem);
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

	
	public static void setCustomerItems(Connection conn, int idItem, ArrayList<Integer> customers) {
		
		PreparedStatement st = null;
		try{
		  	 String sql = "delete from ats.rel_item_customer where iditem = ? ";
		     st = conn.prepareStatement(sql);
		     st.setInt(1, idItem);
		     // execute the java preparedstatement
		     st.executeUpdate();
		     st.close();
		     
		     for(Integer idcus : customers) {
		    	 
		    	 sql = "insert into ats.rel_item_customer (iditem, idcustomer) values (?, ?) ";
		    	 st = conn.prepareStatement(sql);
			     st.setInt(1, idItem);
			     st.setInt(2, idcus);
			     st.executeUpdate();
			     st.close();
		    	 
		     }
		}
	    catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
