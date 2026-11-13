package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Document {
	private int idDocument;
	private int docNumber;
	private Date docDate;
	private String docRef;
	private String docOrigin;
	private int idFlow;
	private int idStep;
	private int idSite;
	private int previousIdDoc;
	private String status;
	private int idDocumentType;
	
	private String dsStep;
	private String dsSite;
	private String dsType;
	
	private boolean expected;
	
	private int idSiteDest;
	private String docType;
	
	private Integer filterType;
	
	public Document() { }

	public Document(int idDocument, int docNumber, Date docDate, String docRef, String docOrigin, int idFlow,
			int idStep, int idSite, int previousIdDoc, String status, int idDocumentType,
			String dsStep, String dsSite, String dsType, boolean expected, int idSiteDest, String docType) {
		super();
		this.idDocument = idDocument;
		this.docNumber = docNumber;
		this.docDate = docDate;
		this.docRef = docRef;
		this.docOrigin = docOrigin;
		this.idFlow = idFlow;
		this.idStep = idStep;
		this.idSite = idSite;
		this.previousIdDoc = previousIdDoc;
		this.status = status;
		this.idDocumentType = idDocumentType;
		
		this.dsStep = dsStep;
		this.dsSite = dsSite;
		this.dsType = dsType;
		
		this.expected = expected;
		
		this.idSiteDest = idSiteDest;
		this.docType = docType;
	}

	private enum DocumentSelector{
		flow,
		step,
		site,
		flowInDateRange,
		stepInDateRange,
		siteInDateRange,
	}
	
	public int getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}

	public int getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(int docNumber) {
		this.docNumber = docNumber;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getDocRef() {
		return docRef;
	}

	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}

	public String getDocOrigin() {
		return docOrigin;
	}

	public void setDocOrigin(String docOrigin) {
		this.docOrigin = docOrigin;
	}

	public int getIdFlow() {
		return idFlow;
	}

	public void setIdFlow(int idFlow) {
		this.idFlow = idFlow;
	}

	public int getIdStep() {
		return idStep;
	}

	public void setIdStep(int idStep) {
		this.idStep = idStep;
	}

	public int getIdSite() {
		return idSite;
	}

	public void setIdSite(int idSite) {
		this.idSite = idSite;
	}

	public int getPreviousIdDoc() {
		return previousIdDoc;
	}

	public void setPreviousIdDoc(int previousIdDoc) {
		this.previousIdDoc = previousIdDoc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getIdDocumentType() {
		return idDocumentType;
	}

	public void setIdDocumentType(int idDocumentType) {
		this.idDocumentType = idDocumentType;
	}
	
	public String getDsStep() {
		return dsStep;
	}

	public void setDsStep(String dsStep) {
		this.dsStep = dsStep;
	}
	
	public String getDsSite() {
		return dsSite;
	}

	public void setDsSite(String dsSite) {
		this.dsSite = dsSite;
	}
	
	public String getDsType() {
		return dsType;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}
	
	public boolean getExpected() {
		return expected;
	}

	public void setExpected(boolean expected) {
		this.expected = expected;
	}
	
	public int getIdSiteDest() {
		return idSiteDest;
	}
	
	public void setIdSiteDest(int idSiteDest) {
		this.idSiteDest = idSiteDest;
	}
	
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select d.*, step.description dsstep,"
					+ "sites.description dssite, documents_type.description dstype "
					+ "from documents d "
					+ "join step on step.idstep = d.idstep "
					+ "join sites on sites.id = d.idsite "
					+ "join documents_type on documents_type.id_doctype = d.id_document_type "
					+ "where d.id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idDocument);
			rs = st.executeQuery();
			if(rs.next()) {
				setDocNumber(rs.getInt("doc_number"));			
				String docDate = (rs.getString("doc_date"));
				setDocDate(DateManager.normalizeDate(docDate));
				setDocRef(rs.getString("doc_ref"));
				setDocOrigin(rs.getString("doc_origin"));
				setIdFlow(rs.getInt("idflow"));			
				setIdStep(rs.getInt("idstep"));			
				setIdSite(rs.getInt("idsite"));			
				setPreviousIdDoc(rs.getInt("previous_iddoc"));			
				setStatus(rs.getString("status"));			
				setIdDocumentType(rs.getInt("id_document_type"));		
				
				setDsStep(rs.getString("dsstep"));
				setDsSite(rs.getString("dssite"));
				setDsType(rs.getString("dstype"));
				
				setExpected(rs.getBoolean("expected"));
				
				setIdSiteDest(rs.getInt("idsite_dest"));
				setDocType(rs.getString("doc_type"));
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
	
	
	
	public Integer getFilterType() {
		return filterType;
	}

	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}

	public static ArrayList<Document> getDocumentsBySelector(Connection conn, DocumentSelector documentSelector, int id, Date from, Date to){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		switch (documentSelector) {
		case flow:
			sql = "select * from clients where idflow = ? ";
			break;
		case step:
			sql = "select * from clients where idstep = ? ";
			break;
		case site:
			sql = "select * from clients where idsite = ? ";
			break;
		case flowInDateRange:
			if (from == null || to == null)
				return new ArrayList<Document>();
			sql = "select * from documents where idflow = ? and doc_date::date between ? and ?";
			break;
		case stepInDateRange:
			if (from == null || to == null)
				return new ArrayList<Document>();
			sql = "select * from documents where idstep = ? and doc_date::date between ? and ?";
			break;
		case siteInDateRange:
			if (from == null || to == null)
				return new ArrayList<Document>();
			sql = "select * from documents where idsite = ? and doc_date::date between ? and ?";
			break;
		}
		switch (documentSelector) {
		case flow: 
		case step: 
		case site:
			try {
				st = conn.prepareStatement(sql);
				st.setInt(1, id);
			}catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<Document>();
			}
			break;
		case flowInDateRange:
		case siteInDateRange:
		case stepInDateRange:
			try {
				st = conn.prepareStatement(sql);
				st.setInt(1, id);
				st.setString(2, DateManager.toString(from, DateManager.yMdDateFormat));
				st.setString(3, DateManager.toString(to, DateManager.yMdDateFormat));
			}catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<Document>();
			}
			break;
		}
		ArrayList<Document> documentsList = null;
		try {
			documentsList = new ArrayList<>();
			rs = st.executeQuery();
			while(rs.next()) {
				documentsList.add(new Document(
						rs.getInt("id"), 
						rs.getInt("doc_number"),
						DateManager.normalizeDate(rs.getString("doc_date")),
						rs.getString("doc_ref"), 
						rs.getString("doc_origin"),
						rs.getInt("idflow"),
						rs.getInt("idstep"),
						rs.getInt("idsite"),
						rs.getInt("previous_iddoc"),
						rs.getString("status"),
						rs.getInt("id_document_type"),"","","",true, rs.getInt("idsite_dest"), rs.getString("doc_type")
						));
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
		return documentsList;
	}
	
	
	public static ArrayList<Document> getDocumentsByIdCustomer(Connection conn, int idCustomer, String idflow, String idstep, String idsite){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Document> docsList = null;
		try {
			docsList = new ArrayList<>();
			
			String cond = "";
			if(idflow.length()>0) {
				cond+=" and d.idflow = "+idflow+" ";
			}
			if(idstep.length()>0) {
				cond+=" and d.idstep = "+idstep+" ";
			}
			if(idsite.length()>0) {
				cond+=" and d.idsite = "+idsite+" ";
			}
			
			sql = "select d.*, step.description dsstep, " + 
					"sites.description dssite, documents_type.description dstype " + 
					"from documents d " + 
					"join step on step.idstep = d.idstep " + 
					"join sites on sites.id = d.idsite " + 
					"join documents_type on documents_type.id_doctype = d.id_document_type "+
					"where d.idflow in(select idflow from flow where id_customer = ?) "+cond;
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				docsList.add(new Document(
						rs.getInt("id"), 
						rs.getInt("doc_number"),
						DateManager.normalizeDate(rs.getString("doc_date")),
						rs.getString("doc_ref"), 
						rs.getString("doc_origin"),
						rs.getInt("idflow"),
						rs.getInt("idstep"),
						rs.getInt("idsite"),
						rs.getInt("previous_iddoc"),
						rs.getString("status"),
						rs.getInt("id_document_type"),
						rs.getString("dsstep"),
						rs.getString("dssite"),
						rs.getString("dstype"),
						rs.getBoolean("expected"),
						rs.getInt("idsite_dest"),
						rs.getString("doc_type")
						));
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
		return docsList;
		
	}
	
	public int insert(Connection conn) {
		
		int iddoc = -1;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			sql = "insert into documents " + 
					"(doc_date, doc_ref, doc_origin, idflow, idstep, idsite, id_document_type, filter_type, idsite_dest, doc_type) " + 
					"values " + 
					"(now() , ?, 'USER', (select idflow from step where idstep = ?), ?, ?, (select id_doctype_default " + 
					"from step s join step_type t on s.idsteptype = t.idstep_type " + 
					"where s.idstep = ?) , ?, ?, ?) returning id ";
			
			st = conn.prepareStatement(sql);
			st.setString(1, docRef);
			st.setInt(2, idStep);
			st.setInt(3, idStep);
			st.setInt(4, idSite);
			st.setInt(5, idStep);
			
			if(filterType<0)
				st.setNull(6, java.sql.Types.INTEGER);
			else
				st.setInt(6, filterType);
			st.setInt(7, idSiteDest);
			st.setString(8, docType);
			
			System.out.println(st.toString());
			rs = st.executeQuery();
			if(rs.next()) {
				iddoc = rs.getInt("id");
			}
			rs.close();
			st.close();
			
			sql = "update documents set doc_number = "+iddoc+" where id = "+iddoc;
			st = conn.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return iddoc;
	}
	
	
	public static void setOriginRef(Connection conn, int iddoc) {
		
		PreparedStatement st = null;
		String sql = "";
		
		try {
			sql = "update documents set origin_ref = 'VELASCA' where id = "+iddoc+" "
					+ "and (select write_file from documents_type where id_doctype = id_document_type) = true";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void insertFromField(Connection conn, int idcus, int iddoc, String category, String season, String itemcode, String colourcode, String size, String code) {
		
		PreparedStatement st = null;
		String sql = "";
		
		try {
			
			String cond = "";
			if(code.length()>0)
				cond+=" and code = '"+code+"' ";
			
			if(category.length()>0)
				cond+=" and category = '"+category+"' ";
			if(season.length()>0)
				cond+=" and season = '"+season+"' ";
			if(itemcode.length()>0)
				cond+=" and itemcode = '"+itemcode+"' ";
			if(colourcode.length()>0)
				cond+=" and colourcode = '"+colourcode+"' ";
			if(size.length()>0)
				cond+=" and size = '"+size+"' ";
			
			sql = "delete from documents_detail where iddoc = "+iddoc+" and sku in "
					+ "(select sku from products where id_customer = "+idcus+" "+cond+")";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "insert into documents_detail " + 
					"(sku, iddoc) " +
					"select sku, "+iddoc+" from products where id_customer = "+idcus+" "+cond;
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "update documents set filter_type = 2 where id = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void deleteDoc(Connection conn, int iddoc) {
		
		PreparedStatement st = null;
		String sql = "";
		
		try {
			
			sql = "delete from scanner_detail where iddoc = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "delete from scanner where iddoc = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "delete from documents_detail where iddoc = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "delete from documents where id = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void gammaStored(Connection conn, int iddoc) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql = "select export_gamma_docevasione_web("+iddoc+")";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void closeDoc(Connection conn, int iddoc, ArrayList<String> epc, String closeType) {
		
		PreparedStatement st = null;
		String sql = "";
		
		try {
			
			sql = "delete from documents_toclose where iddoc = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "insert into documents_toclose (iddoc, epc) values (?, ?) ";
			st = conn.prepareStatement(sql);
			for(int i=0;i<epc.size();i++) {
				
				System.out.println(epc.get(i));
				st.setInt(1, iddoc);
				st.setString(2, epc.get(i));
				st.addBatch();
			}
			st.executeBatch();
			st.close();
			
//			// call function...
//			sql = "select * from closeinventory("+iddoc+" , '"+closeType+"') ";
//			st = conn.prepareStatement(sql);
//			System.out.println(st.toString());
//			st.executeUpdate();
//			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void closeDoc(Connection conn, Integer iddoc, List<DocumentToclose> rows) {
		
		PreparedStatement st = null;
		String sql = "";
		
		try {
			
			sql = "delete from documents_toclose where iddoc = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "insert into documents_toclose (iddoc, epc, sku) values (?, ?, ?) ";
			st = conn.prepareStatement(sql);
			for(DocumentToclose row : rows) {
				
				System.out.println(row.getEpc()+" "+row.getSku());
				st.setInt(1, iddoc);
				st.setString(2, row.getEpc());
				st.setString(3, row.getSku());
				st.addBatch();
			}
			st.executeBatch();
			st.close();
			
//			// call function...
			sql = "select * from closeinventory_dan("+iddoc+") ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			ResultSet rs = st.executeQuery();
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static String getDocEpcFromSku(Connection conn, int iddoc, String sku) {
		
		String epcs = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql = "select distinct epc from scanner_detail where iddoc = ? and sku = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, iddoc);
			st.setString(2, sku);
			rs=st.executeQuery();
			while(rs.next()) {
				epcs+=rs.getString("epc")+",";
			}
			rs.close();
			
			if(epcs.length()>0)
				epcs=epcs.substring(0, epcs.length()-1);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return epcs;
		
	}
	
	
	
	
	public static void openDoc(Connection conn, int iddoc) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		
		try {
			
			int iddoc2=-1;
			sql = "select id from documents where previous_iddoc = "+iddoc;
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			if(rs.next()) {
				iddoc2=rs.getInt("id");
			}
			rs.close();
			
			// cancella i riferimenti del doc successivo a quello da riaprire
			sql = "delete from scanner_detail where iddoc = "+iddoc2+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "delete from scanner where iddoc = "+iddoc2+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "delete from documents_detail where iddoc = "+iddoc2+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "delete from documents where id = "+iddoc2+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			sql = "update documents set status = null, doc_destination = null , previous_iddoc = null, fiege_export=false where id = "+iddoc+" ";
			st = conn.prepareStatement(sql);
			System.out.println(st.toString());
			st.executeUpdate();
			st.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void closeDoc(Connection conn, int iddoc) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from close_step2("+iddoc+");";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
