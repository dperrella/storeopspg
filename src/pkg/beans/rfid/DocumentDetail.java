package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pkg.beans.Tables;
import pkg.utility.SGTIN96;
import pkg.utility.Utility;

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
	private String reference;
	
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
	
	public DocumentDetail(int id, String epc, String sku, int idDoc, String packageId, String description, String code,
			int qty_theo, int qty_read, String reference) {
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
		this.reference = reference;
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
	
	
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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
			case "PACK":
            	sql = "select sku, sum(qty_read) qty_read, sum(qty_theo) qty_theo, description, code, package_id \n" + 
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
        			"coalesce(x.code, null, '-') code , package_id \n" + 
        			"from scanner_detail s "+
        			"LEFT JOIN LATERAL(select x.description , x.code from products x "+
        			"WHERE s.sku = x.sku limit 1) x on TRUE "+
        			"--left join products x on s.sku = x.sku \n" + 
        			"where iddoc = ? group by s.sku, x.description, x.code, package_id) a where sku<>'' "+cond+" \n" + 
        			"group by sku, description, code, package_id";
            	
            	break;
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
        			"coalesce(x.code, null, '-') code, package_id \n" + 
        			"from scanner_detail s "+
        			"LEFT JOIN LATERAL(select x.description , x.code from products x "+
        			"WHERE s.sku = x.sku limit 1) x on TRUE "+
        			"--left join products x on s.sku = x.sku \n" + 
        			"where iddoc = ? group by s.epc, s.sku, x.description, x.code, package_id) a where sku<>'' "+cond+" \n" + 
        			"group by epc, sku, description, code ";
            	
            	
            	break;
            	
            case "SKU":
            default: 
            	sql = "select sku, sum(qty_read) qty_read, sum(qty_theo) qty_theo, description, code, string_agg(distinct(package_id), ', ') package_id \n" + 
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
        			"coalesce(x.code, null, '-') code , package_id \n" + 
        			"from scanner_detail s "+
        			"LEFT JOIN LATERAL(select x.description , x.code from products x "+
        			"WHERE s.sku = x.sku limit 1) x on TRUE "+
        			"--left join products x on s.sku = x.sku \n" + 
        			"where iddoc = ? group by s.sku, x.description, x.code, package_id) a where sku<>'' "+cond+" \n" + 
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
								(type.equals("SKU")||type.equals("PACK")) ? "" : rs.getString("epc"), 
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
							(type.equals("SKU")||type.equals("PACK")) ? "" : rs.getString("epc"), 
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
	
	
	public static ArrayList<DocumentDetail> getDocumentsDetailByIdDocumentEpc(Connection conn, int idDocument, 
			boolean onlyDiff){
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
				
            sql = "select sku, coalesce(epc, null, '') as epc, sum(qty_read) as qty_read, (select count(0) from documents_detail d where iddoc = ? and d.sku = a.sku) as qty_theo, description, code, max(package_id) package_id, reference \n"
            		+ "from (\n"
            		+ "select s.epc, s.sku, count(distinct(s.epc)) qty_read, 0 as qty_theo , \n"
            		+ "coalesce(x.description, null, '-') description, \n"
            		+ "coalesce(x.code, null, '-') code, coalesce(x.reference, null, '-') reference, coalesce(package_id, null, '') as package_id \n"
            		+ "from scanner_detail s \n"
            		+ "LEFT JOIN LATERAL(select x.description , x.code, x.reference from products x \n"
            		+ "WHERE s.sku = x.sku limit 1) x on TRUE \n"
            		+ "--left join products x on s.sku = x.sku \n"
            		+ "where iddoc = ? group by s.epc, s.sku, x.description, x.code, package_id, reference) a where sku<>'' "+cond+"\n"
            		+ "group by epc, sku, description, code, reference \n"
            		+ "order by reference";
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
								rs.getString("epc"), 
								rs.getString("sku"),
								0, 
								rs.getString("package_id"),
								rs.getString("description"),
								rs.getString("code"),
								rs.getInt("qty_theo"),
								rs.getInt("qty_read"),
								rs.getString("reference")
								));
					}
				}
				else {
					documentDetails.add(new DocumentDetail(
							0, 
							rs.getString("epc"), 
							rs.getString("sku"),
							0, 
							rs.getString("package_id"),
							rs.getString("description"),
							rs.getString("code"),
							rs.getInt("qty_theo"),
							rs.getInt("qty_read"),
							rs.getString("reference")
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
	
	public static List<Tables> getCegidList(Connection conn, Integer iddoc) {
		
		List<Tables> docList = new ArrayList<Tables>();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql = "select d.doc_ref, d.origin_ref, s.warehouse_code, dd.sku, sd.epc, p.reference, \n"
					+ "	 (select id_locationtype \n"
					+ "	 from location where id = \n"
					+ "	 case when d.origin_location is null then sd.idlocation \n"
					+ "	 else d.origin_location end )  location_type\n"
					+ "	from documents d\n"
					+ "	join documents_detail dd on d.id = dd.iddoc\n"
					+ "	join sites s on s.id = d.idsite\n"
					+ "	join products p on p.id_customer = 220 and p.sku = dd.sku\n"
					+ "	left join scanner_detail sd on sd.sku = dd.sku and sd.iddoc = d.id\n"
					+ "	where d.id = ? and sd.epc is not null\n"
					+ "	group by d.doc_ref, d.origin_ref, s.warehouse_code, dd.sku, sd.epc, p.reference,location_type\n"
					+ " order by s.warehouse_code, location_type ";
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, iddoc);
			
			rs = st.executeQuery();
			while(rs.next()) {
				
				Integer loctype = rs.getInt("location_type");
				String stato = "";
				switch(loctype) {
				case 108:
					stato = rs.getString("warehouse_code")+"";
					break;
				case 110:
					stato = rs.getString("warehouse_code")+"2";
					break;
				case 115:
					stato = rs.getString("warehouse_code")+"3";
					break;
				case 116:
					stato = rs.getString("warehouse_code")+"4";
					break;
				case 202:
					stato = rs.getString("warehouse_code")+"5";
					break;
				default:
					stato = rs.getString("warehouse_code")+"";
					break;
				}
				
				String ref = Utility.noNull(rs.getString("origin_ref"));
				if(ref.length()==0)
					ref = rs.getString("doc_ref");
				
				docList.add(new Tables(ref,rs.getString("warehouse_code"),rs.getString("reference"),
						rs.getString("epc").substring(6,rs.getString("epc").length()),stato,"",""));
				
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return docList;
		
		
	}
	
	public static List<Tables> getCegidList(Connection conn, Integer iddoc, String type) {
		
		List<Tables> docList = new ArrayList<Tables>();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			System.out.println(type);
			
			switch (type) {
				case "SER":
					sql = "select d.doc_ref,  d.doc_ref as origin_ref, s.warehouse_code, dd.sku, sd.epc, p.reference, \n"
							+ "	 (select id_locationtype \n"
							+ "	 from location where id = \n"
							+ "	 case when d.origin_location is null then sd.idlocation \n"
							+ "	 else d.origin_location end )  location_type\n"
							+ "	from documents d\n"
							+ "	join documents_detail dd on d.id = dd.iddoc\n"
							+ "	join sites s on s.id = d.idsite\n"
							+ "	join products p on p.id_customer = 220 and p.sku = dd.sku\n"
							+ "	left join scanner_detail sd on sd.sku = dd.sku and sd.iddoc = d.id\n"
							+ "	where d.id = ? and sd.epc is not null\n"
							+ "	group by d.doc_ref, d.origin_ref, s.warehouse_code, dd.sku, sd.epc, p.reference,location_type\n"
							+ " order by s.warehouse_code, location_type ";
					break;
				case "R31":
					sql = "select d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference,\n"
							+ "	 (select id_locationtype \n"
							+ "	 from location where id = \n"
							+ "	 case when d.origin_location is null then sd.idlocation \n"
							+ "	 else d.origin_location end )  location_type\n"
							+ "			from documents d\n"
							+ "			join scanner_detail sd on d.id = sd.iddoc\n"
							+ "			join sites s on s.id = d.idsite\n"
							+ "			join products p on p.id_customer = 220 and p.sku = sd.sku\n"
							+ "			where d.id = ? \n"
							+ "			group by d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference,location_type\n"
							+ " ";
					
				break;
				case "R32":
					sql = "select d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference,\n"
							+ "	 (select id_locationtype \n"
							+ "	 from location where id = \n"
							+ "	 case when d.origin_location is null then sd.idlocation \n"
							+ "	 else d.origin_location end )  location_type\n"
							+ "			from documents d\n"
							+ "			join scanner_detail sd on d.id = sd.iddoc\n"
							+ "			join sites s on s.id = d.idsite\n"
							+ "			join products p on p.id_customer = 220 and p.sku = sd.sku\n"
							+ "			where d.id = ? \n"
							+ "			group by d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference,location_type\n"
							+ " ";
					
				break;
				case "R30":
				default:
					sql = "select d.doc_ref,  d.doc_ref as origin_ref, s.warehouse_code, dd.sku, dd.epc, p.reference, \n"
							+ "	 (select id_locationtype \n"
							+ "	 from location where id = \n"
							+ "	 case when d.origin_location is null then sd.idlocation \n"
							+ "	 else d.origin_location end )  location_type\n"
							+ "	from documents d\n"
							+ "	join documents_detail dd on d.id = dd.iddoc\n"
							+ "	join sites s on s.id = d.idsite\n"
							+ "	join products p on p.id_customer = 220 and p.sku = dd.sku\n"
							+ "	left join scanner_detail sd on sd.sku = dd.sku and sd.iddoc = d.id\n"
							+ "	where d.id = ? and dd.epc is not null\n"
							+ "	group by d.doc_ref, d.origin_ref, s.warehouse_code, dd.sku, dd.epc, p.reference,location_type\n"
							+ " order by s.warehouse_code, location_type ";
					
				break;
				
				case "INV":
					
						
					/*
					sql = "select d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference, \n"
							+ "	 (select id_locationtype \n"
							+ "	 from location where id = \n"
							+ "	 case \n"
							+ "	  when d.origin_location is null then sd.idlocation \n"
							+ "	  when d.origin_location = -1 then sd.idlocation\n"
							+ "	 else d.origin_location end ) as location_type\n"
							+ "	from documents d\n"
							+ "	left join scanner_detail sd on sd.iddoc = d.id\n"
							+ "	join sites s on s.id = d.idsite\n"
							+ "	join products p on p.id_customer = 220 and p.sku = sd.sku\n"
							+ "	where \n"
							+ "	d.id = ? and sd.epc is not null\n"
							+ "	group by d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference,location_type\n"
							+ " order by s.warehouse_code, location_type  ";
					*/
					
					sql = "select d.idstep,d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference, \n"
							+ "	 (select id_locationtype \n"
							+ "	 from location where id = \n"
							+ "	  case when (select inventory from step_type\n"
							+ "		where idstep_type in(select idsteptype from step where idstep = d.idstep)) = true \n"
							+ "	  then(\n"
							+ "			case \n"
							+ "		  when d.origin_location is null then sd.idlocation \n"
							+ "		  when d.origin_location = -1 then sd.idlocation\n"
							+ "		 else d.origin_location end \n"
							+ "		  \n"
							+ "		  )\n"
							+ "	  else\n"
							+ "	  	  (\n"
							+ "	  	case \n"
							+ "		  when d.origin_location is null then sd.idlocation \n"
							+ "		  when d.origin_location = -1 then sd.idlocation\n"
							+ "		 else d.origin_location end \n"
							+ "		 )\n"
							+ "	  end\n"
							+ "	 ) as location_type\n"
							+ "	from documents d\n"
							+ "	left join scanner_detail sd on sd.iddoc = d.id\n"
							+ "	join sites s on s.id = d.idsite\n"
							+ "	join products p on p.id_customer = 220 and p.sku = sd.sku\n"
							+ "	where \n"
							+ "	d.id = ? and sd.epc is not null\n"
							+ "	group by d.idstep,d.doc_ref, d.origin_ref, s.warehouse_code, sd.sku, sd.epc, p.reference,location_type\n"
							+ " order by s.warehouse_code, location_type ";
					
					
				break;
			}
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, iddoc);
			
			rs = st.executeQuery();
			while(rs.next()) {
				
				Integer loctype = rs.getInt("location_type");
				String stato = "";
				switch(loctype) {
				case 108:
					stato = rs.getString("warehouse_code")+"";
					break;
				case 110:
					stato = rs.getString("warehouse_code")+"2";
					break;
				case 115:
					stato = rs.getString("warehouse_code")+"3";
					break;
				case 116:
					stato = rs.getString("warehouse_code")+"4";
					break;
				case 202:
					stato = rs.getString("warehouse_code")+"5";
					break;
				default:
					stato = rs.getString("warehouse_code")+"";
					break;
				}
				
				String ref = Utility.noNull(rs.getString("origin_ref"));
				if(ref.length()==0)
					ref = rs.getString("doc_ref");
				
				
				if(type.equals("R31")||type.equals("R32")) {
					ref = rs.getString("doc_ref");
				}
				
				docList.add(new Tables(ref,rs.getString("warehouse_code"),rs.getString("reference"),
						rs.getString("epc").substring(6,rs.getString("epc").length()),stato,"",""));
				
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return docList;
		
		
	}
	
	
	public static List<String> getP2RList(Connection conn, Integer iddoc) {
		
		List<String> eans = new ArrayList<String>();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
					
			sql = "select distinct epc from scanner_detail where iddoc = ? ";
			
		
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, iddoc);
			
			rs = st.executeQuery();
			while(rs.next()) {
				
				
				try {
					eans.add(SGTIN96.decodeEpc(rs.getString("epc")));
				}
				catch(Exception e) {}
				
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return eans;
		
		
	}
}
