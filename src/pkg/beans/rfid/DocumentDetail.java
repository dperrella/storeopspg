package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DocumentDetail {
	private int id;
	private String epc;
	private String sku;
	private int idDoc;
	private String packageId;
	private String description;
	private String code;
	private int qty_theo;
	private int qty_read;
	
	public DocumentDetail() { }

	public DocumentDetail(int id, String epc, String sku, int idDoc, String packageId, String description, String code,
			int qty_theo, int qty_read) {
		super();
		this.id = id;
		this.epc = epc;
		this.sku = sku;
		this.idDoc = idDoc;
		this.packageId = packageId;
		this.description = description;
		this.code = code;
		this.qty_theo = qty_theo;
		this.qty_read = qty_read;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(int idDoc) {
		this.idDoc = idDoc;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public int getQty_theo() {
		return qty_theo;
	}
	public void setQty_theo(int qty_theo) {
		this.qty_theo = qty_theo;
	}
	
	public int getQty_read() {
		return qty_read;
	}
	public void setQty_read(int qty_read) {
		this.qty_read = qty_read;
	}
	
	
	public void selectRow(Connection conn, int idCustomer) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from documents_detail d "
					+ "join products p on p.sku = d.sku and p.id_customer = ? "
					+ "where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			st.setInt(2, id);
			rs = st.executeQuery();
			if(rs.next()) {
				setEpc(rs.getString("epc"));
				setSku(rs.getString("sku"));
				setIdDoc(rs.getInt("iddoc"));
				setPackageId(rs.getString("package_id"));
				setCode(rs.getString("code"));
				setDescription(rs.getString("description"));
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
	
	public static ArrayList<DocumentDetail> getDocumentsDetailByIdDocument(Connection conn, int idDocument, 
			boolean onlyDiff, String type){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<DocumentDetail> documentDetails = null;
		
		String cond = "";
		try {
			
			/*
			if(onlyDiff) {
		        cond+= " and qty_read <> qty_theo ";
		    }
		    */
			
			switch (type) {
            case "EPC":
            	sql = "select sku, epc, sum(qty_read) qty_read, sum(qty_theo) qty_theo, description, code, max(package_id) package_id \n" + 
        			"from (select d.epc,d.sku, 0 qty_read, count(distinct(d.epc)) qty_theo , \n" + 
        			"	coalesce(p.description, null, '-') description, \n" + 
        			"	coalesce(p.code, null, '-') code, package_id \n" + 
        			"from documents_detail d "+
        			"LEFT JOIN LATERAL(select p.description , p.code from products p "+
        			"WHERE d.sku = p.sku limit 1) p on TRUE "+
        			"--left join products p on d.sku = p.sku \n" + 
        			"where iddoc = ? group by d.epc, d.sku , p.description , p.code, package_id \n" + 
        			"union all \n" + 
        			"select s.epc, s.sku, count(distinct(s.epc)) qty_read, 0 as qty_theo , \n" + 
        			"coalesce(x.description, null, '-') description, \n" + 
        			"coalesce(x.code, null, '-') code, '' \n" + 
        			"from scanner_detail s "+
        			"LEFT JOIN LATERAL(select x.description , x.code from products x "+
        			"WHERE s.sku = x.sku limit 1) x on TRUE "+
        			"--left join products x on s.sku = x.sku \n" + 
        			"where iddoc = ? group by s.epc, s.sku, x.description, x.code) a where sku<>'' "+cond+" \n" + 
        			"group by epc, sku, description, code ";
            	
            	
            	break;
            	
            case "SKU":
            default: 
            	sql = "select sku, sum(qty_read) qty_read, sum(qty_theo) qty_theo, description, code, max(package_id) package_id \n" + 
        			"from (select d.sku, 0 qty_read, count((d.sku)) qty_theo , \n" + 
        			"	coalesce(p.description, null, '-') description, \n" + 
        			"	coalesce(p.code, null, '-') code, package_id \n" + 
        			"from documents_detail d "+
        			"LEFT JOIN LATERAL(select p.description , p.code from products p "+
        			"WHERE d.sku = p.sku limit 1) p on TRUE "+
        			"--left join products p on d.sku = p.sku \n" + 
        			"where iddoc = ? group by d.sku , p.description , p.code, package_id \n" + 
        			"union all \n" + 
        			"select s.sku, count(distinct(s.epc)) qty_read, 0 as qty_theo , \n" + 
        			"coalesce(x.description, null, '-') description, \n" + 
        			"coalesce(x.code, null, '-') code , '' \n" + 
        			"from scanner_detail s "+
        			"LEFT JOIN LATERAL(select x.description , x.code from products x "+
        			"WHERE s.sku = x.sku limit 1) x on TRUE "+
        			"--left join products x on s.sku = x.sku \n" + 
        			"where iddoc = ? group by s.sku, x.description, x.code) a where sku<>'' "+cond+" \n" + 
        			"group by sku, description, code";
            	
            	
            	
            	
            	break;
            	
			}
			System.out.println(sql);
			
			documentDetails = new ArrayList<DocumentDetail>();
			
			st = conn.prepareStatement(sql);
			st.setInt(1, idDocument);
			st.setInt(2, idDocument);
			rs = st.executeQuery();
			while(rs.next()) {
				
				if(onlyDiff) {
					if(rs.getInt("qty_theo")!=rs.getInt("qty_read")) {
						documentDetails.add(new DocumentDetail(
								0, 
								(type.equals("SKU")) ? "" : rs.getString("epc"), 
								rs.getString("sku"),
								0, 
								rs.getString("package_id"),
								rs.getString("description"),
								rs.getString("code"),
								rs.getInt("qty_theo"),
								rs.getInt("qty_read")
								));
					}
				}
				else {
					documentDetails.add(new DocumentDetail(
							0, 
							(type.equals("SKU")) ? "" : rs.getString("epc"), 
							rs.getString("sku"),
							0, 
							rs.getString("package_id"),
							rs.getString("description"),
							rs.getString("code"),
							rs.getInt("qty_theo"),
							rs.getInt("qty_read")
							));
				}
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
		return documentDetails;
	}
	
	
	
	
	public static void setExpected(Connection conn, int idDocument) {
		
		PreparedStatement st = null;
		PreparedStatement in = null;
		ResultSet rs = null;
		
		String sql = "";
		try {
			
			sql = "delete from documents_detail where iddoc = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idDocument);
			st.executeUpdate();
			st.close();
			
			
			sql = "select z.sku, qta from giacenze\n" + 
				"join products a on taglia = size\n" + 
				"and variant = variante and code = prodotto \n" + 
				"join (select distinct(sku) from scanner_detail) z\n" + 
				"on z.sku = a.sku ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			sql = "insert into documents_detail (sku, iddoc) values (?,?)";
			in = conn.prepareStatement(sql);
			while(rs.next()) {
				
				for(int i=0;i<rs.getInt("qta");i++) {
				
					in.setString(1,rs.getString("sku"));
			    	in.setInt(2,idDocument);
			    	in.addBatch();
			    	
				}
			}
			rs.close();
			
			int[] result = in.executeBatch();
		    System.out.println("The number of rows inserted: "+ result.length);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
