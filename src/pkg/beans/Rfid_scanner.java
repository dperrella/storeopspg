package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.postgresql.util.PGobject;

public class Rfid_scanner {
	private int idscan;
	private String iddoc;
	private int id_flow;
	private int id_step;
	private String ins_date;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Rfid_scanner() {}

	public Rfid_scanner(int idscan, String iddoc, int id_flow, int id_step, String ins_date, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.idscan=idscan;
		this.iddoc=iddoc;
		this.id_flow=id_flow;
		this.id_step=id_step;
		this.ins_date=ins_date;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public int getIdscan() {
		return idscan;
	}
	public void setIdscan(int idscan) {
		this.idscan = idscan;
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
	
	public String getIns_date() {
		return ins_date;
	}
	public void setIns_date(String ins_date) {
		this.ins_date = ins_date;
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
	
	public DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from scanner where idscan = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idscan);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setIddoc(rs.getString("iddoc"));
				setId_flow(rs.getInt("id_flow"));
				setId_step(rs.getInt("id_step"));
				setIns_date(dateIso.format(rs.getDate("ins_date")));
				
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
	
	public void selectRowFromIddoc(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			System.out.println(iddoc);
			sql = "select * from scanner where iddoc = ? ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setString(1, iddoc);
			rs = st.executeQuery();
			if(rs.next()) {
				
				setIdscan(rs.getInt("idscan"));
				setId_flow(rs.getInt("id_flow"));
				setId_step(rs.getInt("id_step"));
				setIns_date(dateIso.format(rs.getDate("ins_date")));
				
			}
			else {
				
				setIdscan(0);
				
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
	
	public List<Rfid_scanner> selectAll(Connection conn, String q) {
		
		List<Rfid_scanner> scan = new ArrayList<Rfid_scanner>();
		scan.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from scanner where id_flow = ? and id_step = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id_flow);
			st.setInt(2, id_step);
			
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				scan.add(new Rfid_scanner(rs.getInt("idscan"),rs.getString("iddoc"),rs.getInt("id_flow"),
						rs.getInt("id_Scan"),dateIso.format(rs.getDate("ins_date")),""+count,"","",""));
				
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
		
		return scan;
		
	}
	
	public void insert(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "insert into scanner "+
					"(iddoc, id_flow, id_step, ins_date) "+
					"values "+
					"(?, ?, ?, ?) RETURNING idscan ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setString(1, iddoc);
			st.setInt(2, id_flow);
			st.setInt(3, id_step);
			st.setDate(4, java.sql.Date.valueOf(dateIso.format(new Date())));
			
			// Esegue l'sql
			rs = st.executeQuery();
			
			if(rs.next()) {
				idscan = rs.getInt(1);
			}
			else {
				idscan = -1;
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
	
	public void delete(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "delete from scanner "+
					"where idscan = ? ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idscan);
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
