package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pkg.utility.Utility;

public class AuditItem implements CRUD {
	private int idAudit;
	private int idItem;
	private String itemName;
	private String itemDescription;
	private String auditResult;
	private String note;
	private String url;
	private String noteColor;
	private String urlColor;
	private String backgroundColor;
	
	private String vendorname;
	private String brandname;
	private String categoryname;
	private String barcodes;
	
	
	
	public AuditItem() {	}

	public AuditItem(int idAudit, int idItem, String itemName, String itemDescription, 
			String auditResult, String note, String url,
			String noteColor, String urlColor, String backgroundColor,
			String vendorname, String brandname, String categoryname, String barcodes) {
		this.idAudit = idAudit;
		this.idItem = idItem;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.auditResult = auditResult;
		this.note = note;
		this.url = url;
		this.noteColor = noteColor;
		this.urlColor = urlColor;
		this.backgroundColor = backgroundColor;
		
		this.vendorname = vendorname;
		this.brandname = brandname;
		this.categoryname = categoryname;
		this.barcodes = barcodes;
		
	}
	
	public int getIdAudit() {
		return idAudit;
	}

	public void setIdAudit(int idAudit) {
		this.idAudit = idAudit;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNoteColor() {
		return noteColor;
	}

	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}
	
	public String getUrlColor() {
		return urlColor;
	}

	public void setUrlColor(String urlColor) {
		this.urlColor = urlColor;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	
	/*
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from ats.audit where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idAudit);
			rs = st.executeQuery();
			if(rs.next()) {
				setName(rs.getString("name"));	
				setIdType(rs.getInt("idtype"));
				setCreationDate(df.format(rs.getDate("creation_date")));
				setClosingDate(df.format(rs.getDate("closing_date")));
				setState(rs.getInt("state"));
				setIdStore(rs.getInt("idstore"));
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
	*/
	
	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(String barcodes) {
		this.barcodes = barcodes;
	}

	public static ArrayList<AuditItem> getAuditItems(Connection conn, int idAudit){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<AuditItem> auditList = null;
		try {
			auditList = new ArrayList<>();
			sql = "select a.*, i.name, i.description, " +
					"v.name as vendorname, " +
					"b.name as brandname, " +
					"c.name as categoryname, " +
					"json_agg((x.barcode)) AS barcodes " +
					
					"from ats.audit_item a " + 
					"join ats.items i on a.iditem = i.id " + 
					"left JOIN ats.barcode x ON x.iditem = i.id " +
					"left join ats.vendor v on v.id = i.idvendor " +
					"left join ats.brand b on b.id = i.idbrand " +
					"left join ats.category c on c.id = i.idcategory " +
					
					"where a.idaudit = ? " +
					"group by a.idaudit, a.iditem, a.audit_result, a.note, a.url, " +
					"i.name, i.description, v.name, b.name, c.name " +
					"order by audit_result desc , i.name ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idAudit);
			rs = st.executeQuery();
			
			String c1 = "";
			String c2 = "";
			
			String b = "";
			String barcodes = "";
			while(rs.next()) {
				
				if(Utility.noNull(rs.getString("note")).length()>0)
					c1 = "#de5c56";
				else 
					c1 = "#ccd3da";
				
				if(Utility.noNull(rs.getString("url")).length()>0)
					c2 = "#de5c56";
				else 
					c2 = "#ccd3da";
				
				
				if(Utility.noNull(rs.getString("audit_result")).length()>0)
					b = "#f0f2f4";
				else 
					b = "#fff";
				
				
				barcodes = rs.getString("barcodes");
				barcodes = barcodes.replaceAll("\\[","").replaceAll("null", "").replaceAll("\\]", "");
				
				
				auditList.add(new AuditItem(rs.getInt("idaudit"), rs.getInt("iditem"), 
						rs.getString("name"), rs.getString("description"), 
						rs.getString("audit_result"), Utility.noNull(rs.getString("note")), 
						Utility.noNull(rs.getString("url")), c1, c2, b, 
						Utility.noNull(rs.getString("vendorname")),
						Utility.noNull(rs.getString("brandname")),
						Utility.noNull(rs.getString("categoryname")),
						barcodes));
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
			String sql = "";
			
			if(this.auditResult!=null) {
			  
				sql = "update ats.audit_item set audit_result = ? where idaudit = ? and iditem = ?";
				
				st = conn.prepareStatement(sql);
				System.out.println(st.toString());
				st.setString(1, this.auditResult);
				st.setInt(2, this.idAudit);
				st.setInt(3, this.idItem);
				// execute the java preparedstatement
				st.executeUpdate();
				
			}
			
			if(this.note!=null) {
				  
				sql = "update ats.audit_item set note = ? where idaudit = ? and iditem = ?";
				
				st = conn.prepareStatement(sql);
				System.out.println(st.toString());
				st.setString(1, this.note);
				st.setInt(2, this.idAudit);
				st.setInt(3, this.idItem);
				// execute the java preparedstatement
				st.executeUpdate();
				
			}
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
			String sql = " insert into ats.audit_item (idaudit, iditem)" + 
					" values (?,?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idAudit);
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
	public void delete(Connection conn) {
	
		
		PreparedStatement st = null;
		try{
		  String sql = "delete from ats.audit_item where idaudit = ? and iditem = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idAudit);
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
	
	public static void deleteAuditItems(Connection conn, int idAudit) {
	
		
		PreparedStatement st = null;
		try{
		  String sql = "delete from ats.audit_item where idaudit = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, idAudit);
	      
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
	
	public void addAll(Connection conn, int idCustomer) {
		
		
		PreparedStatement st = null;
		try{
			String sql = "delete from ats.audit_item where idaudit = "+this.idAudit+" "
					+ "and iditem in (select iditem from ats.rel_item_customer where idcustomer = "+idCustomer+") ";
			st = conn.prepareStatement(sql);
			
			System.out.println(st.toString());
			st.executeUpdate();
			
			
			st.close();
			
			sql = " insert into ats.audit_item (idaudit, iditem) " 
					+ "select "+this.idAudit+" , iditem "
					+ "from ats.rel_item_customer where idcustomer = "+idCustomer+" ";
			st = conn.prepareStatement(sql);
			
	        // execute the java preparedstatement
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void updateUrl(Connection conn) {
		
		PreparedStatement st = null;
		try{
			String sql = "";
			
			sql = "update ats.audit_item set url = ? where idaudit = ? and iditem = ?";
			
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.setString(1, this.url);
			st.setInt(2, this.idAudit);
			st.setInt(3, this.idItem);
			// execute the java preparedstatement
			st.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateNote(Connection conn) {
		
		PreparedStatement st = null;
		try{
			String sql = "";
			
			sql = "update ats.audit_item set note = ? where idaudit = ? and iditem = ?";
			
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.setString(1, this.note);
			st.setInt(2, this.idAudit);
			st.setInt(3, this.idItem);
			// execute the java preparedstatement
			st.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
