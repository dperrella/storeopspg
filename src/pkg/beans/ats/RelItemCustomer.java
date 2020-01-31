package pkg.beans.ats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RelItemCustomer{
	private int idItem;
	private int idCustomer;
	
	public RelItemCustomer() {	}

	public RelItemCustomer(int idItem, int idCustomer) {
		this.idItem = idItem;
		this.idCustomer = idCustomer;
	}
	
	public int getIdItem() {
		return idItem;
	}
	
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	
	public int getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public static ArrayList<RelItemCustomer> getItemByCustomer(Connection conn, int idcus){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<RelItemCustomer> relList = null;
		try {
			relList = new ArrayList<>();
			sql = "select * from ats.rel_item_customer where iditem = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idcus);
			rs = st.executeQuery();
			while(rs.next()) {
				relList.add(new RelItemCustomer(rs.getInt("iditem"), rs.getInt("idcustomer")));
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
		return relList;
	}
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into ats.rel_item_customer (iditem, idcustomer)" + 
					" values (?, ?)";
			st = conn.prepareStatement(sql);
			st.setInt(1, this.idItem);
			st.setInt(2, this.idCustomer);
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

	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = "delete from ats.rel_item_customer where iditem = ? and idcustomer = ? ";
		    st = conn.prepareStatement(sql);
		    st.setInt(1, this.idItem);
			st.setInt(2, this.idCustomer);
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

	
}
