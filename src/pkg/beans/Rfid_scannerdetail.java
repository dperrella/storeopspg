package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Rfid_scannerdetail {
	private int idscan;
	private int nrscan;
	private String epc;
	private String barcode;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Rfid_scannerdetail() {}

	public Rfid_scannerdetail(int idscan, int nrscan, String epc, String barcode, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.idscan=idscan;
		this.nrscan=nrscan;
		this.epc=epc;
		this.barcode=barcode;
		
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

	public int getNrscan() {
		return nrscan;
	}
	public void setNrscan(int nrscan) {
		this.nrscan = nrscan;
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
	
	public List<Rfid_scannerdetail> selectAll(Connection conn) {
		
		List<Rfid_scannerdetail> scan = new ArrayList<Rfid_scannerdetail>();
		scan.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from scanner where idscan = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idscan);
			
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				scan.add(new Rfid_scannerdetail(rs.getInt("idscan"),rs.getInt("nrscan"),rs.getString("epc"),
						rs.getString("barcode"),""+count,"","",""));
				
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
	
	public List<Rfid_scannerdetail> selectAllFromNrscan(Connection conn) {
		
		List<Rfid_scannerdetail> scan = new ArrayList<Rfid_scannerdetail>();
		scan.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql = "select * from scanner where idscan = ? and nrscan = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idscan);
			st.setInt(2, nrscan);
			
			rs = st.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				scan.add(new Rfid_scannerdetail(rs.getInt("idscan"),rs.getInt("nrscan"),rs.getString("epc"),
						rs.getString("barcode"),""+count,"","",""));
				
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
		String sql = "";
	    
		try {
			
			sql   = "insert into scannerdetail "+
					"(idscan, nrscan, epc, barcode) "+
					"values "+
					"(?, ?, ?, ?)  ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idscan);
			st.setInt(2, nrscan);
			st.setString(3, epc);
			st.setString(4, barcode);
			
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
	
	public void deleteAllNrscan(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "delete from scannerdetail "+
					"where idscan = ? and nrscan = ?";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Passa i parametri
			st.setInt(1, idscan);
			st.setInt(2, nrscan);
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
	
	public void deleteAll(Connection conn) {
		
		PreparedStatement st = null;
		String sql = "";
	    
		try {
			
			sql   = "delete from scannerdetail "+
					"where idscan = ?";
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
