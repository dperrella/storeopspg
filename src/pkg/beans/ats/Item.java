package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import pkg.utility.Utility;

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
	
	private String photos;
	private int editable;
	
	private int idwishlist;
	private String wishlistName;
	
	private String note;
	
	public Item() {	}

	public Item(int idItem, String name, String description, int idVendor, int idBrand,
			int idCategory, int country, int itemProtected, String evReportLink,
			String vendorName, String brandName, String categoryName, String countryName, 
			String idusr, String barcodes, String retailers, String photos, int editable) {
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
		
		this.photos = photos;
		
		this.editable = editable;
	}
	
	public Item(int idItem, String name, String description, int idVendor, int idBrand,
			int idCategory, int country, int itemProtected, String evReportLink,
			String vendorName, String brandName, String categoryName, String countryName, 
			String idusr, String barcodes, String retailers, String photos, int editable, int idwishlist, String wishlistName) {
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
		
		this.photos = photos;
		
		this.editable = editable;
		
		this.idwishlist=idwishlist;
		this.wishlistName=wishlistName;
		
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
	
	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}
	
	public int getEditable() {
		return editable;
	}

	public void setEditable(int editable) {
		this.editable = editable;
	}

	public int getIdwishlist() {
		return idwishlist;
	}

	public void setIdwishlist(int idwishlist) {
		this.idwishlist = idwishlist;
	}
	
	public String getWishlistName() {
		return wishlistName;
	}

	public void setWishlistName(String wishlistName) {
		this.wishlistName = wishlistName;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select i.*, v.name as vendorname, "
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, w.name as wishlistname "
					+ "from ats.items i "
					+ "left join ats.vendor v on v.id = i.idvendor "
					+ "left join ats.brand b on b.id = i.idbrand "
					+ "left join ats.category c on v.id = i.idcategory "
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
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
				
				setIdwishlist(rs.getInt("idwishlist"));
				setWishlistName(rs.getString("wishlistname"));
				
				setNote(Utility.noNull(rs.getString("note")));
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
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos, w.name as wish "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
					+ "where protected = "+isProtected+" "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name, w.name "
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
				
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, 1, rs.getInt("idwishlist"), rs.getString("wish")));
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
	
	public static ArrayList<Item> getUserRoleItems(Connection conn, int isProtected, String username, String role){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Item> itemsList = null;
		
		String cond = "";
		if(role.trim().equals("3")||role.trim().equals("8")) {
			cond = " and i.idusr = '"+username.replaceAll("'", "''")+"' ";
		}
		
		try {
			itemsList = new ArrayList<>();
			sql = "select i.*, v.name as vendorname, " 
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos, w.name as wish "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
					+ "where protected = "+isProtected+" "+cond+" "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name, w.name "
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
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, 1,rs.getInt("idwishlist"), rs.getString("wish")));
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
	
	public static ArrayList<Item> getUserRoleItemsWish(Connection conn, int isProtected, String username, String role, String idwishlist){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Item> itemsList = null;
		
		String cond = "";
		if(role.trim().equals("3")||role.trim().equals("8")) {
			cond = " and i.idusr = '"+username.replaceAll("'", "''")+"' ";
		}
		
		if(idwishlist.length()>0)
			cond+= " and i.idwishlist = "+idwishlist+" ";
		
		try {
			itemsList = new ArrayList<>();
			sql = "select i.*, v.name as vendorname, " 
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos, w.name as wish "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
					+ "where protected = "+isProtected+" "+cond+" "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name, w.name "
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
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, 1, rs.getInt("idwishlist"), rs.getString("wish")));
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
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos, w.name as wish "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
					+ "where protected = "+isProtected+" "
					+ "and i.id in(select iditem from ats.rel_item_customer r1 " 
					+ "join rel_user_customers r2 on r1.idcustomer = r2.idcustomer " 
					+ "where r2.idusr = '"+idusr+"' "+cond+" ) "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name, w.wish "
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
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, 1, rs.getInt("idwishlist"), rs.getString("wish")));
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
	
	public static ArrayList<Item> getItemsByUserRole(Connection conn, int isProtected, String idusr, String role){
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
				cond = "and r1.idcustomer in ("+cus+") ";
			
			System.out.println(cond);
			
			String roleCond =  "";
			if(role.trim().equals("3")) {
				roleCond = "join rel_user_customers r2 on r1.idcustomer = r2.idcustomer where r2.idusr = '"+idusr+"' "+cond;
			}
			else {
				roleCond = cond.replaceAll("and", "where");
			}
			
			String sqlEditable = "";
			if(role.trim().equals("3")) {
				sqlEditable = " , 0 as editable ";
			}
			else if(role.trim().equals("8")) {
				sqlEditable = " , 0 as editable ";
			}
			else if(role.trim().equals("5")) {
				sqlEditable = ",(select case when count(iditem) > 0 then 1 else 0 end from rel_user_customers r2 " + 
						"join ats.rel_item_customer r1 on r1.idcustomer = r2.idcustomer " + 
						" where r2.idusr = '"+idusr+"' and iditem = i.id) enabled ";
			}
			else {
				sqlEditable = " , 1 as editable ";
			}
			
				
			itemsList = new ArrayList<>();
			sql = "select i.*, v.name as vendorname, " 
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos, w.name as wish "
					+ sqlEditable
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
					+ "where protected = "+isProtected+" "
					+ "and i.id in(select iditem from ats.rel_item_customer r1 " 
					+ roleCond+" ) "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name, w.name "
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
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, rs.getInt("editable"), rs.getInt("idwishlist"), rs.getString("wish")));
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
	
	
	public static ArrayList<Item> getItemsByUserRole2(Connection conn, String isProtected, String idusr, String role){
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
				cond = "and r1.idcustomer in ("+cus+") ";
			
			System.out.println(cond);
			
			String roleCond =  "";
			if(role.trim().equals("3")) {
				roleCond = "join rel_user_customers r2 on r1.idcustomer = r2.idcustomer where r2.idusr = '"+idusr+"' "+cond;
			}
			else {
				roleCond = cond.replaceAll("and", "where");
			}
			
			String sqlEditable = "";
			if(role.trim().equals("3")) {
				sqlEditable = " , 0 as editable ";
			}
			else if(role.trim().equals("8")) {
				sqlEditable = " , 0 as editable ";
			}
			else if(role.trim().equals("5")) {
//				sqlEditable = ",(select case when count(iditem) > 0 then 1 else 0 end from rel_user_customers r2 " + 
//						"join ats.rel_item_customer r1 on r1.idcustomer = r2.idcustomer " + 
//						" where r2.idusr = '"+idusr+"' and iditem = i.id) editable ";
				sqlEditable = " , 1 as editable ";
			}
			else {
				sqlEditable = " , 1 as editable ";
			}
			
				
			itemsList = new ArrayList<>();
			sql = "select i.*, v.name as vendorname, " 
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos, w.name as wish "
					+ sqlEditable
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
					+ "where protected in( "+isProtected+" ) "
					+ "and i.id in(select iditem from ats.rel_item_customer r1 " 
					+ roleCond+" ) "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name, w.name "
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
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, rs.getInt("editable"), rs.getInt("idwishlist"), rs.getString("wish")));
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
	
	public static ArrayList<Item> getItemsByUserRole2(Connection conn, String isProtected, String idusr, String role, String page){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Item> itemsList = null;
		
		int limit = 10;
		
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
				cond = "and r1.idcustomer in ("+cus+") ";
			
			System.out.println(cond);
			
			String roleCond =  "";
			if(role.trim().equals("3")) {
				roleCond = "join rel_user_customers r2 on r1.idcustomer = r2.idcustomer where r2.idusr = '"+idusr+"' "+cond;
			}
			else {
				roleCond = cond.replaceAll("and", "where");
			}
			
			String sqlEditable = "";
			if(role.trim().equals("3")) {
				sqlEditable = " , 0 as editable ";
			}
			else if(role.trim().equals("8")) {
				sqlEditable = " , 0 as editable ";
			}
			else if(role.trim().equals("5")) {
//				sqlEditable = ",(select case when count(iditem) > 0 then 1 else 0 end from rel_user_customers r2 " + 
//						"join ats.rel_item_customer r1 on r1.idcustomer = r2.idcustomer " + 
//						" where r2.idusr = '"+idusr+"' and iditem = i.id) editable ";
				sqlEditable = " , 1 as editable ";
			}
			else {
				sqlEditable = " , 1 as editable ";
			}
			
				
			itemsList = new ArrayList<>();
			sql = "select i.*, v.name as vendorname, " 
					+ "b.name as brandname, c.name as categoryname, n.name as countryname, "
					+ "json_agg((x.barcode)) AS barcodes, "
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos, w.name as wish "
					+ sqlEditable
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
					+ "left join ats.vendor v on v.id = i.idvendor " 
					+ "left join ats.brand b on b.id = i.idbrand " 
					+ "left join ats.category c on c.id = i.idcategory " 
					+ "left join ats.country n on n.id = i.country "
					+ "left join ats.wishlist w on w.id = i.idwishlist "
					+ "where protected in( "+isProtected+" ) "
					+ "and i.id in(select iditem from ats.rel_item_customer r1 " 
					+ roleCond+" ) "
					+ "group by i.id, i.name, i.description, i.idvendor, i.idbrand, "  
					+ "i.idcategory, i.country, i.protected, i.evolution_report_link, "
					+ "i.color, i.idusr, v.name, b.name, c.name, n.name, w.name "
					+ "order by id desc "
					+ "limit "+limit+" offset "+((Integer.parseInt(page)-1)*limit)+" ";
					
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
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, rs.getInt("editable"), rs.getInt("idwishlist"), rs.getString("wish")));
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
					+ "json_agg((y.description)) AS customers, "
					+ "json_agg((p.url)) AS photos "
					+ "from ats.items i "
					+ "left JOIN ats.barcode x ON x.iditem = i.id "
					+ "left JOIN ats.vw_rel_item_customer y ON y.iditem = i.id "
					+ "left JOIN ats.photo p ON p.iditem = i.id "
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
				
				String pp = rs.getString("photos").replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("null", "");
				String p[] = pp.split(",");
				ArrayList<String> p2 = new ArrayList<String>();
				String ppp = "";
				for(int i=0;i<p.length;i++) {
					if(!p2.contains(p[i].trim())) {
						p2.add(p[i].trim());
						ppp+=p[i]+",";
					}
				}
				
				if(ppp.length()>0)
					ppp = ppp.substring(0,ppp.length()-1);
				
				
				
				itemsList.add(new Item(rs.getInt("id"), rs.getString("name"),rs.getString("description"),
						rs.getInt("idvendor"),rs.getInt("idbrand"),rs.getInt("idcategory"),rs.getInt("country"),
						rs.getInt("protected"),rs.getString("evolution_report_link"),
						rs.getString("vendorname"),rs.getString("brandname"),rs.getString("categoryname"),
						rs.getString("countryname"), rs.getString("idusr"),bbb,rs.getString("customers"),ppp, 1));
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
		  		+ "country = ?, protected = ?, evolution_report_link = ?, idwishlist = ?, note = ? where id = ?";
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
	      st.setInt(9, this.idwishlist);
	      st.setString(10, this.note);
	      st.setInt(11, this.idItem);
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
	
	public void updateStatus(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.items set protected = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.itemProtected);
	      st.setInt(2, this.idItem);
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
			String sql = " insert into ats.items (name, description, idvendor, idbrand, idcategory, country, protected, evolution_report_link, idusr, idwishlist, note)" + 
					" values (?,?,?,?,?,?,?,?,?,?,?) returning id ";
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
			st.setInt(10, this.idwishlist);
			st.setString(11, this.note);
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
