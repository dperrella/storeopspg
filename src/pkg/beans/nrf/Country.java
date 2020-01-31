package pkg.beans.nrf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pkg.utility.Utility;

public class Country {

	private String area;
	private String country;
	private int nrStores;
	private String idContinent;
	private String idNaz;
	private String idArea;
	
	public Country() {}
	
	public Country(String area, String country, int nrStores,
			String idContinent, String idNaz, String idArea) {
		this.area=area;
		this.country=country;
		this.nrStores=nrStores;
		this.idContinent=idContinent;
		this.idNaz=idNaz;
		this.idArea=idArea;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getNrStores() {
		return nrStores;
	}

	public void setNrStores(int nrStores) {
		this.nrStores = nrStores;
	}

	public String getIdContinent() {
		return idContinent;
	}

	public void setIdContinent(String idContinent) {
		this.idContinent = idContinent;
	}

	public String getIdNaz() {
		return idNaz;
	}

	public void setIdNaz(String idNaz) {
		this.idNaz = idNaz;
	}

	public String getIdArea() {
		return idArea;
	}

	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	
	public static ArrayList<Country> getContinent(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Country> countryList = null;
		
		try {
			countryList = new ArrayList<>();
			sql = "Select * from demo.arealist\n" + 
					"where country is null and id_area is null\n" + 
					"order by area ";
			
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				countryList.add(new Country(
						rs.getString("area"),
						rs.getString("country"),
						rs.getInt("nstores"),
						rs.getString("id_continent"),
						rs.getString("id_naz"),
						rs.getString("id_area")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return countryList;
	}
	
	public static ArrayList<Country> getAree(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Country> countryList = null;
		
		try {
			countryList = new ArrayList<>();
			sql = "Select * from demo.arealist\n" + 
					"where country is null and id_area is not null\n" + 
					"order by id_area desc ";
			
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				countryList.add(new Country(
						rs.getString("area"),
						rs.getString("country"),
						rs.getInt("nstores"),
						rs.getString("id_continent"),
						rs.getString("id_naz"),
						rs.getString("id_area")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return countryList;
	}
	
	public static ArrayList<Country> getCountry(Connection conn, String id_continent) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Country> countryList = null;
		
		id_continent = Utility.noNull(id_continent);
		
		try {
			countryList = new ArrayList<>();
			sql = "Select * from demo.arealist\n" + 
					"where id_continent like ? and id_area is null and id_naz is not null\n" + 
					"order by country  ";
			
			st = conn.prepareStatement(sql);
			st.setString(1, "%"+id_continent+"%");
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				countryList.add(new Country(
						rs.getString("area"),
						rs.getString("country"),
						rs.getInt("nstores"),
						rs.getString("id_continent"),
						rs.getString("id_naz"),
						rs.getString("id_area")
						));
				
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return countryList;
	}
	
}
