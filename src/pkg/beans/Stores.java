package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pkg.utility.Utility;

public class Stores {
	
	private String description;
	private String address;
	private String country;
	private String city;
	private String postalcode;
	private String region;
	private String contact;
	private String cphone;
	private String sphone;
	private String cmail;
	private String smail;
	private String coordinates;
	private String store_code;
	private String brandname;
	private String brandlogo;
	private String id_customer;
	private String id;
	private String cmobile;
	private String province;
	private String id_optempl;
	private String customeridstore;
	private String sapid;
	private String latitude;
	private String longitude;
	private String notes;
	private String openhh;
	private String closehh;
	
	private String googleUid;
	private String googlePlacesTypes;
	
	private String underConstruction;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Stores() {}

	public Stores(String description, String address, String country, String city, String postalcode,
			String region, String contact, String cphone, String sphone, String cmail, String smail,
			String coordinates, String store_code, String brandname, String brandlogo, String id_customer,
			String id, String cmobile, String province, String id_optempl, String customeridstore,
			String sapid, String latitude, String longitude, String notes, String openhh, String closehh,
			String googleUid, String googlePlacesTypes, String underConstruction,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.description=description;
		this.address=address;
		this.country=country;
		this.city=city;
		this.postalcode=postalcode;
		this.region=region;
		this.contact=contact;
		this.cphone=cphone;
		this.sphone=sphone;
		this.cmail=cmail;
		this.smail=smail;
		this.coordinates=coordinates;
		this.store_code=store_code;
		this.brandname=brandname;
		this.brandlogo=brandlogo;
		this.id_customer=id_customer;
		this.id=id;
		this.cmobile=cmobile;
		this.province=province;
		this.id_optempl=id_optempl;
		this.customeridstore=customeridstore;
		this.sapid=sapid;
		this.latitude=latitude;
		this.longitude=longitude;
		this.notes=notes;
		this.openhh=openhh;
		this.closehh=closehh;
		this.googleUid=googleUid;
		this.googlePlacesTypes=googlePlacesTypes;
		this.underConstruction=underConstruction;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getCphone() {
		return cphone;
	}
	public void setCphone(String cphone) {
		this.cphone = cphone;
	}
	
	public String getSphone() {
		return sphone;
	}
	public void setSphone(String sphone) {
		this.sphone = sphone;
	}
	
	public String getCmail() {
		return cmail;
	}
	public void setCmail(String cmail) {
		this.cmail = cmail;
	}

	public String getSmail() {
		return smail;
	}
	public void setSmail(String smail) {
		this.smail = smail;
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
	public String getBrandlogo() {
		return brandlogo;
	}
	public void setBrandlogo(String brandlogo) {
		this.brandlogo = brandlogo;
	}
	
	public String getId_customer() {
		return id_customer;
	}
	public void setId_customer(String id_customer) {
		this.id_customer = id_customer;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCmobile() {
		return cmobile;
	}
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getId_optempl() {
		return id_optempl;
	}
	public void setId_optempl(String id_optempl) {
		this.id_optempl = id_optempl;
	}
	
	public String getCustomeridstore() {
		return customeridstore;
	}
	public void setCustomeridstore(String customeridstore) {
		this.customeridstore = customeridstore;
	}
	
	public String getSapid() {
		return sapid;
	}
	public void setSapid(String sapid) {
		this.sapid = sapid;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getOpenhh() {
		return openhh;
	}
	public void setOpenhh(String openhh) {
		this.openhh = openhh;
	}
	
	public String getClosehh() {
		return closehh;
	}
	public void setClosehh(String closehh) {
		this.closehh = closehh;
	}
	
	public String getGoogleUid() {
		return googleUid;
	}
	public void setGoogleUid(String googleUid) {
		this.googleUid = googleUid;
	}
	
	public String getGooglePlacesTypes() {
		return googlePlacesTypes;
	}
	public void setGooglePlacesTypes(String googlePlacesTypes) {
		this.googlePlacesTypes = googlePlacesTypes;
	}
	
	public String getUnderConstruction() {
		return underConstruction;
	}
	public void setUnderConstruction(String underConstruction) {
		this.underConstruction = underConstruction;
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
			
			sql = "select description, address, \n" + 
					"(select description from customers where customers.id = s.id_customer) customer ,\n" + 
					"(select nome_nazione from nazioni where id_naz = country::numeric) country ,\n" + 
					"(select nome_regione from regioni where id_reg = region::numeric) region,\n" + 
					"(select nome_provincia from province where id_pro = province::numeric) province,\n" + 
					"case when country::numeric = 3 then\n" + 
					"(select comune from comuni where id_com = s.city::numeric) \n" + 
					"else\n" + 
					"(select city from city_est where id_city = s.city::numeric) \n" + 
					"end city, postalcode\n" + 
					"from f_stores s where id = ? ";
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(id));
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));
				setCountry(rs.getString("country"));
				setCity(Utility.noNull(rs.getString("city")));
				setProvince(rs.getString("province"));
				setRegion(rs.getString("region"));
				setPostalcode(Utility.noNull(rs.getString("postalcode")));
				setAddress(rs.getString("address"));
				
				setCmp4(rs.getString("customer"));
				
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
}
