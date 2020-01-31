package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

public class Rfid_documents_details {
	private int id_flow;
	private int id_step;
	private int docnum;
	private int numline;
	private String epc;
	private String barcode;
	private int qty_theoretical;
	private int qty_readed;
	private String loadunits_id;
	private String package_id;
	private String pallet_id;
	private int status;
	private String extra;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Rfid_documents_details() {}

	public Rfid_documents_details(int id_flow, int id_step, int docnum,
			int numline, String epc, String barcode, int qty_theoretical, int qty_readed, 
			String loadunits_id, String package_id, String pallet_id, int status, String extra, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_flow=id_flow;
		this.id_step=id_step;
		this.docnum=docnum;
		this.numline=numline;
		this.epc=epc;
		this.barcode=barcode;
		this.qty_theoretical=qty_theoretical;
		this.qty_readed=qty_readed;
		this.loadunits_id=loadunits_id;
		this.package_id=package_id;
		this.pallet_id=pallet_id;
		this.status=status;
		this.extra=extra;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	
	public int getId_flow() {
		return id_flow;
	}
	public void setId_flow(int id_flow) {
		this.id_flow = id_flow;
	}
	
	public int getId_step() {
		return id_step;
	}
	public void setId_step(int id_step) {
		this.id_step = id_step;
	}
	
	public int getDocnum() {
		return docnum;
	}
	public void setDocnum(int docnum) {
		this.docnum = docnum;
	}
	
	public int getNumline() {
		return numline;
	}
	public void setNumline(int numline) {
		this.numline = numline;
	}
	
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public int getQty_theoretical() {
		return qty_theoretical;
	}
	public void setQty_theoretical(int qty_theoretical) {
		this.qty_theoretical = qty_theoretical;
	}
	
	public int getQty_readed() {
		return qty_readed;
	}
	public void setQty_readed(int qty_readed) {
		this.qty_readed = qty_readed;
	}
	
	public String getLoadunits_id() {
		return loadunits_id;
	}
	public void setLoadunits_id(String loadunits_id) {
		this.loadunits_id = loadunits_id;
	}
	
	public String getPackage_id() {
		return package_id;
	}
	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}
	
	public String getpallet_id() {
		return pallet_id;
	}
	public void setPallet_id(String pallet_id) {
		this.pallet_id = pallet_id;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
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
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from documents_details "
					+ "where id_flow = ? "
					+ "and id_step = ? "
					+ "and docnum = ? "
					+ "and numline = ? ";
			
			st = conn.prepareStatement(sql);
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			st.setInt(4, numline);
			
			rs = st.executeQuery();
			if(rs.next()) {
				
				setId_flow(rs.getInt("id_flow"));
				setId_step(rs.getInt("id_step"));
				setDocnum(rs.getInt("docnum"));
				setNumline(rs.getInt("numline"));
				setEpc(rs.getString("epc"));
				setBarcode(rs.getString("barcode"));
				setQty_theoretical(rs.getInt("qty_theoretical"));
				setQty_readed(rs.getInt("qty_readed"));
				setLoadunits_id(rs.getString("loadunits_id"));
				setPackage_id(rs.getString("package_id"));
				setPallet_id(rs.getString("pallet_id"));
				setStatus(rs.getInt("status"));
				setExtra(rs.getString("extra"));
				
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
	
	public List<Rfid_documents_details> selectAll(Connection conn, String q) {
		
		List<Rfid_documents_details> doc = new ArrayList<Rfid_documents_details>();
		doc.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from documents_details "
					+ "where id_flow = ? "
					+ "and id_step = ? "
					+ "and docnum = ? ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				doc.add(new Rfid_documents_details(rs.getInt("id_flow"),rs.getInt("id_step"),
						rs.getInt("docnum"),rs.getInt("numline"),rs.getString("epc"), rs.getString("barcode"),
						rs.getInt("qty_theoretical"),rs.getInt("qty_readed"),
						rs.getString("loadunits_id"),rs.getString("package_id"),rs.getString("pallet_id"),
						rs.getInt("status"),rs.getString("extra"),""+count,"","",""));
				
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
		
		return doc;
		
	}
	
	public List<Rfid_documents_details> selectAllGrouped(Connection conn, String group, String package_id) {
		
		List<Rfid_documents_details> doc = new ArrayList<Rfid_documents_details>();
		doc.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		String cond = "";
		boolean hasCond = false;
		
		try {
			
			if(package_id.length()>0) {
				
				System.out.println(package_id);
				
				hasCond = true;
				cond = " and package_id = ? ";
			}
			
			if(group.equals("E")) {
				sql = "select d.*, p.reference, p.name from documents_details d "
					+ "left join products p on trim(code) = trim(barcode) "
					+ "where id_flow = ? "
					+ "and id_step = ? "
					+ "and docnum = ? "+cond;
			}
			else if(group.equals("B")) {
				
				sql = "select id_flow, id_step, docnum, 0 as numline, '' as epc, "
					+ "barcode, sum(qty_theoretical) qty_theoretical, "
					+ "sum(qty_readed) qty_readed, loadunits_id, "
					+ "package_id, pallet_id, status, null as extra, "
					+ "p.reference, p.name "
					+ "from documents_details d "
					+ "left join products p on trim(code) = trim(barcode) "
					+ "where id_flow = ? "
					+ "and id_step = ? "
					+ "and docnum = ? "+cond
					+ "group by id_flow, id_step, docnum, "
					+ "barcode, loadunits_id, package_id, pallet_id, status,  p.reference, p.name ";
			}
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			
			if(hasCond) {
				st.setString(4, package_id);
			}
			
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				if(group.equals("B"))
					count+=1;
				else
					count=rs.getInt("numline");

				System.out.println(rs.getString("reference"));
				
				doc.add(new Rfid_documents_details(rs.getInt("id_flow"),rs.getInt("id_step"),
						rs.getInt("docnum"),count,rs.getString("epc"), rs.getString("barcode"),
						rs.getInt("qty_theoretical"),rs.getInt("qty_readed"),
						rs.getString("loadunits_id"),rs.getString("package_id"),rs.getString("pallet_id"),
						rs.getInt("status"),rs.getString("extra"),""+count,rs.getString("reference"),rs.getString("name"),""));
				
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
		
		return doc;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
	
			sql   = "insert into documents_details "
					+ "(id_flow, id_step, docnum, numline, epc, barcode, "
					+ "qty_theoretical, qty_readed, "
					+ "loadunits_id, package_id, pallet_id, "
					+ "status, extra) "+
					"values "+
					"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			st.setInt(4, numline);
			st.setString(5, epc);
			st.setString(6, barcode);
			st.setInt(7, qty_theoretical);
			st.setInt(8, qty_readed);
			st.setString(9, loadunits_id);
			st.setString(10, package_id);
			st.setString(11, pallet_id);
			st.setInt(12, status);
			if(extra.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(13, jsonExtra);
			}
			else {
				st.setObject(13, null);
			}
			
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
	
	public void delete(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "delete from documents_details "
					+ "where id_flow = ? "
					+ "and id_step = ? "
					+ "and docnum = ? "
					+ "and numline = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			st.setInt(4, numline);
			
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
	
	public void updatePackageId(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "update documents_details "
					+ "set package_id = ? "
					+ "where id_flow = ? "
					+ "and id_step = ? "
					+ "and docnum = ? ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setString(1, package_id);
			
			st.setInt(2, id_flow);
			st.setInt(3, id_step);
			st.setInt(4, docnum);
			
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
