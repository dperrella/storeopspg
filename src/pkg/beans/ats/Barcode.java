package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Barcode implements CRUD {
	private int id;
	private int idItem;
	private String barcode;
	
	public Barcode() {	}

	public Barcode(int id, int idItem, String barcode) {
		this.id = id;
		this.idItem = idItem;
		this.barcode = barcode;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdItem() {
		return idItem;
	}
	
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from ats.barcode where id = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				setIdItem(rs.getInt("iditem"));
				setBarcode(rs.getString("barcode"));	
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
	
	public static ArrayList<Barcode> getBarcodeByItem(Connection conn, int idItem){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Barcode> barcodeList = null;
		try {
			barcodeList = new ArrayList<>();
			sql = "select * from ats.barcode where iditem = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idItem);
			rs = st.executeQuery();
			while(rs.next()) {
				barcodeList.add(new Barcode(rs.getInt("id"), rs.getInt("iditem"),rs.getString("barcode")));
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
		return barcodeList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update ats.barcode set barcode = ? where id = ?";
		  System.out.println(sql);
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.barcode);
	      st.setInt(2, this.id);
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
			String sql = "insert into ats.barcode (iditem, barcode)" + 
					" values (?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idItem);
			st.setString(2, this.barcode);
	        // execute the java preparedstatement
			
			System.out.println(st.toString());
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
		  String sql = "delete from ats.barcode where id = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.id);
	      // execute the java preparedstatement
	      System.out.println(st.toString());
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
