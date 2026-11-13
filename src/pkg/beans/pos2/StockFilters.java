package pkg.beans.pos2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StockFilters {

	private String filter;
	
	public StockFilters() {}
	
	public StockFilters(String filter) {
		this.filter=filter;
	}
	
	
	public String getFilter() {
		return filter;
	}
	
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	
	
	public static ArrayList<StockFilters> getLocationsByIdclient(Connection conn, String idClient){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<StockFilters> locations = null;
		try {
			locations = new ArrayList<>();
			
			String cond = "";
			if(idClient.length()>0)
				cond = "where idclient = '"+idClient+"' ";
			sql = "select location from giacenze "+cond+" group by location";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				locations.add(new StockFilters(rs.getString("location")));
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
		return locations;
	}

	public static ArrayList<StockFilters> getVariantByIdclient(Connection conn, String idClient){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<StockFilters> variants = null;
		try {
			variants = new ArrayList<>();
			String cond = "";
			if(idClient.length()>0)
				cond = "where idclient = '"+idClient+"' ";
			sql = "select variante from giacenze "+cond+" group by variante";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				variants.add(new StockFilters(rs.getString("variante")));
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
		return variants;
	}
	
	public static ArrayList<StockFilters> getSupplierByIdclient(Connection conn, String idClient){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<StockFilters> suppliers = null;
		try {
			suppliers = new ArrayList<>();
			String cond = "";
			if(idClient.length()>0)
				cond = "where idclient = '"+idClient+"' ";
			sql = "select fornitore from giacenze "+cond+" group by fornitore";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				suppliers.add(new StockFilters(rs.getString("fornitore")));
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
		return suppliers;
	}
	
	public static ArrayList<StockFilters> getSizeByIdclient(Connection conn, String idClient){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<StockFilters> sizes = null;
		try {
			sizes = new ArrayList<>();
			String cond = "";
			if(idClient.length()>0)
				cond = "where idclient = '"+idClient+"' ";
			sql = "select taglia from giacenze "+cond+" group by taglia";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				sizes.add(new StockFilters(rs.getString("taglia")));
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
		return sizes;
	}

}
