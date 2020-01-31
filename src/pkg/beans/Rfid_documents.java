package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

public class Rfid_documents {
	private String iddoc;
	private int id_flow;
	private int id_step;
	private int docnum;
	private int doctype;
	private String docdate;
	private int origin_warehouse;
	private int destination_warehouse;
	private int destination_location;
	private boolean update_diary;
	private int status;
	private String docref_origin;
	private String docref_destination;
	private String extra;
	private String doc_note;
	private String id_doc_prec;
	private String doc_exchange;
	private int tot_theo;
	private int tot_read;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Rfid_documents() {}

	public Rfid_documents(String iddoc, int id_flow, int id_step, int docnum,
			int doctype, String docdate, int origin_warehouse, int destination_warehouse, int destination_location,
			boolean update_diary, int status, String docref_origin, String docref_destination, String extra, 
			String doc_note, String id_doc_prec, String doc_exchange, int tot_theo, int tot_read,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.iddoc=iddoc;
		this.id_flow=id_flow;
		this.id_step=id_step;
		this.docnum=docnum;
		this.doctype=doctype;
		this.docdate=docdate;
		this.origin_warehouse=origin_warehouse;
		this.destination_warehouse=destination_warehouse;
		this.destination_location=destination_location;
		this.update_diary=update_diary;
		this.status=status;
		this.docref_origin=docref_origin;
		this.docref_destination=docref_destination;
		this.extra=extra;
		this.doc_note=doc_note;
		this.id_doc_prec=id_doc_prec;
		this.doc_exchange=doc_exchange;
		
		this.tot_theo=tot_theo;
		this.tot_read=tot_read;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getIddoc() {
		return iddoc;
	}
	public void setIddoc(String iddoc) {
		this.iddoc = iddoc;
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
	
	public int getDoctype() {
		return doctype;
	}
	public void setDoctype(int doctype) {
		this.doctype = doctype;
	}
	
	public String getDocdate() {
		return docdate;
	}
	public void setDocdate(String docdate) {
		this.docdate = docdate;
	}
	
	public int getOrigin_warehouse() {
		return origin_warehouse;
	}
	public void setOrigin_warehouse(int origin_warehouse) {
		this.origin_warehouse = origin_warehouse;
	}
	
	public int getDestination_warehouse() {
		return destination_warehouse;
	}
	public void setDestination_warehouse(int destination_warehouse) {
		this.destination_warehouse = destination_warehouse;
	}
	
	public int getDestination_location() {
		return destination_location;
	}
	public void setDestination_location(int destination_location) {
		this.destination_location = destination_location;
	}
	
	
	
	public boolean getUpdate_diary() {
		return update_diary;
	}
	public void setUpdate_diary(boolean update_diary) {
		this.update_diary = update_diary;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getDocref_origin() {
		return docref_origin;
	}
	public void setDocref_origin(String docref_origin) {
		this.docref_origin = docref_origin;
	}
	
	public String getDocref_destination() {
		return docref_destination;
	}
	public void setDocref_destination(String docref_destination) {
		this.docref_destination = docref_destination;
	}
	
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public String getDoc_note() {
		return doc_note;
	}
	public void setDoc_note(String doc_note) {
		this.doc_note = doc_note;
	}
	
	public String getId_doc_prec() {
		return id_doc_prec;
	}
	public void setId_doc_prec(String id_doc_prec) {
		this.id_doc_prec = id_doc_prec;
	}
	
	public String getDoc_exchange() {
		return doc_exchange;
	}
	public void setDoc_exchange(String doc_exchange) {
		this.doc_exchange = doc_exchange;
	}
	
	public int getTot_theo() {
		return tot_theo;
	}
	public void setTot_theo(int tot_theo) {
		this.tot_theo = tot_theo;
	}
	
	public int getTot_read() {
		return tot_read;
	}
	public void setTot_read(int tot_read) {
		this.tot_read = tot_read;
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
			sql = "select * from documents where iddoc = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, iddoc);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setId_flow(rs.getInt("id_flow"));
				setId_step(rs.getInt("id_step"));
				setDocnum(rs.getInt("docnum"));
				setDoctype(rs.getInt("doctype"));
				setDocdate(rs.getString("docdate"));
				setOrigin_warehouse(rs.getInt("origin_warehouse"));
				setDestination_warehouse(rs.getInt("destination_warehouse"));
				setDestination_location(rs.getInt("destination_location"));
				setUpdate_diary(rs.getBoolean("update_diary"));
				setStatus(rs.getInt("status"));
				setDocref_origin(rs.getString("docref_origin"));
				setDocref_destination(rs.getString("docref_destination"));
				setExtra(rs.getString("extra"));
				setDoc_note(rs.getString("doc_note"));
				setId_doc_prec(rs.getString("id_doc_prec"));
				
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
	
	public void selectRowFromDocnum(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			//System.out.println(id_flow+" "+id_step+" "+docnum);
			sql = "select * from documents where id_flow = ? and id_step = ? and docnum = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			
			rs = st.executeQuery();
			if(rs.next()) {
				
				setIddoc(rs.getString("iddoc"));
				setId_flow(rs.getInt("id_flow"));
				setId_step(rs.getInt("id_step"));
				setDocnum(rs.getInt("docnum"));
				setDoctype(rs.getInt("doctype"));
				setDocdate(rs.getString("docdate"));
				setOrigin_warehouse(rs.getInt("origin_warehouse"));
				setDestination_warehouse(rs.getInt("destination_warehouse"));
				setDestination_location(rs.getInt("destination_location"));
				setUpdate_diary(rs.getBoolean("update_diary"));
				setStatus(rs.getInt("status"));
				setDocref_origin(rs.getString("docref_origin"));
				setDocref_destination(rs.getString("docref_destination"));
				setExtra(rs.getString("extra"));
				setDoc_note(rs.getString("doc_note"));
				setId_doc_prec(rs.getString("id_doc_prec"));
				
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
	
	public List<Rfid_documents> selectAll(Connection conn, String cond) {
		
		List<Rfid_documents> doc = new ArrayList<Rfid_documents>();
		doc.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select a.*, t.description type_description, "
					+ "o.description origin_description, "
					+ "d.description destination_description, "
					+ "(select sum(qty_theoretical) from documents_details x "
					+ "where x.docnum = a.docnum and x.id_flow = a.id_flow and x.id_step = a.id_step) qty_theo,"
					+ "(select sum(qty_readed) from documents_details x "
					+ "where x.docnum = a.docnum and x.id_flow = a.id_flow and x.id_step = a.id_step) qty_read "
					+ "from documents a, "
					+ "doctype t, "
					+ "warehouse o, "
					+ "warehouse d "
					+ "where a.id_flow = ? and a.id_step = ? "+cond+" "
					+ "and a.doctype = t.id "
					+ "and a.origin_warehouse = o.idwarehouse "
					+ "and a.destination_warehouse = d.idwarehouse ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				doc.add(new Rfid_documents(rs.getString("iddoc"),rs.getInt("id_flow"),rs.getInt("id_step"),
						rs.getInt("docnum"),rs.getInt("doctype"),rs.getString("docdate"),
						rs.getInt("origin_warehouse"),rs.getInt("destination_warehouse"),rs.getInt("destination_location"),
						rs.getBoolean("update_diary"),rs.getInt("status"),rs.getString("docref_origin"),rs.getString("docref_destination"),
						rs.getString("extra"),rs.getString("doc_note"),rs.getString("id_doc_prec"),rs.getString("doc_exchange"),
						rs.getInt("qty_theo"), rs.getInt("qty_read"),
						""+count,rs.getString("type_description"),rs.getString("origin_description"),rs.getString("destination_description")));
				
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
		ResultSet rs = null;
		String sql = "";
	    
		try {
	
			sql   = "insert into documents "
					+ "(id_flow, id_step, docnum, doctype, docdate, "
					+ "origin_warehouse, destination_warehouse, destination_location, "
					+ "update_diary, status, docref_origin, docref_destination, "
					+ "extra, doc_note, id_doc_prec) "+
					"values "+
					"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING iddoc ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			st.setInt(4, doctype);
			st.setDate(5, java.sql.Date.valueOf(docdate));
			st.setInt(6, origin_warehouse);
			st.setInt(7, destination_warehouse);
			st.setInt(8, destination_location);
			st.setBoolean(9, update_diary);
			st.setInt(10, status);
			st.setString(11, docref_origin);
			st.setString(12, docref_destination);
			
			
			if(extra.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(13, jsonExtra);
			}
			else {
				st.setObject(13, null);
			}
			
			st.setString(14, doc_note);
			st.setString(15, id_doc_prec);
			
			// Esegue l'sql
			rs = st.executeQuery();
			
			if(rs.next()) {
				iddoc = rs.getString(1);
			}
			else {
				iddoc = "";
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
	
	public void update(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "update documents "+
					"set origin_warehouse = ?, destination_warehouse = ?, destination_location = ?, extra = ?, doc_note = ? "+
					"where iddoc = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, origin_warehouse);
			st.setInt(2, destination_warehouse);
			st.setInt(3, destination_location);
			if(extra.length()>0) {
				PGobject jsonExtra = new PGobject();
				jsonExtra.setType("json");
				jsonExtra.setValue(extra);
				st.setObject(4, jsonExtra);
			}
			else {
				st.setObject(4, null);
			}
			st.setString(5, doc_note);
			st.setString(6, iddoc);
			
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
	
	public void updateDocref_destination(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "update documents "+
					"set docref_destination = ? "+
					"where docnum = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, docref_destination);
			st.setInt(2, docnum);
			
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
	
	public void updateDoc_exchange(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "update documents "+
					"set doc_exchange = ? "+
					"where docnum = ? and id_flow = ? and id_step = ? ";
			
			System.out.println(sql);
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, doc_exchange);
			st.setInt(2, docnum);
			st.setInt(3, id_flow);
			st.setInt(4, id_step);
			
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
		String sql = "";
	    
		try {
			
			sql   = "update documents "+
					"set status = ? "+
					"where iddoc = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, status);
			st.setString(2, iddoc);
			
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
			
			sql   = "delete from documents "+
					"where iddoc = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, iddoc);
			
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
	
	public boolean isDocref_origin(Connection conn) {
		
		boolean is = false;
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select count(iddoc) num from documents "+
					"where docref_origin = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, docref_origin);
			rs = st.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num")>0)
					is=true;
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
		
		
		return is;
		
	}
	
	public boolean isDocref_destination(Connection conn) {
		
		boolean is = false;
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select count(iddoc) num from documents "+
					"where docref_destination = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, docref_destination);
			rs = st.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num")>0)
					is=true;
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
		
		
		return is;
		
	}
	
	
	public boolean checkDocRef_destination(Connection conn) {
		
		boolean is = false;
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select count(iddoc) num from documents "+
					"where id_flow = ? and id_step = ? and docnum = ? and docref_destination is null ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			
			rs = st.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num")>0)
					is=true;
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
		
		
		return is;
		
	}
	
	public boolean checkDocExchange(Connection conn) {
		
		boolean is = false;
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select count(iddoc) num from documents "+
					"where id_flow = ? and id_step = ? and docnum = ? and doc_exchange is null ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			st.setInt(3, docnum);
			
			rs = st.executeQuery();
			if(rs.next()) {
				if(rs.getInt("num")>0)
					is=true;
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
		
		
		return is;
		
	}
}
