package eu.mcsistemi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.mcsistemi.Utility.Utility;

public class PlaceAddress {
	
	private String longCountry;
	private String shortCountry;
	private String postalCode;
	private String zipCode;
	private String region;
	private String longProvince;
	private String shortProvince;
	private String longMunicipality;
	private String shortMunicipality;
	private String longRoute;
	private String shortRoute;
	
	public PlaceAddress() {
		this.longCountry = "";
		this.shortCountry = "";
		this.postalCode = "";
		this.zipCode = "";
		this.region = "";
		this.longProvince = "";
		this.shortProvince = "";
		this.longMunicipality = "";
		this.shortMunicipality = "";
		this.longRoute = "";
		this.shortRoute = "";
	}
	
	public PlaceAddress(String longCountry, String shortCountry, String postalCode, String zipCode, String region,
			String longProvince, String shortProvince, String longMunicipality, String shortMunicipality,
			String longRoute, String shortRoute) {
		super();
		this.longCountry = longCountry;
		this.shortCountry = shortCountry;
		this.postalCode = postalCode;
		this.zipCode = zipCode;
		this.region = region;
		this.longProvince = longProvince;
		this.shortProvince = shortProvince;
		this.longMunicipality = longMunicipality;
		this.shortMunicipality = shortMunicipality;
		this.longRoute = longRoute;
		this.shortRoute = shortRoute;
	}
	
	public PlaceAddress(JSONArray jsonAddress) {
		this.longCountry = "";
		this.shortCountry = "";
		this.postalCode = "";
		this.zipCode = "";
		this.region = "";
		this.longProvince = "";
		this.shortProvince = "";
		this.longMunicipality = "";
		this.shortMunicipality = "";
		this.longRoute = "";
		this.shortRoute = "";
		for (int i = 0 ; i < jsonAddress.length(); i++) {
			String oLongName = null;
			String oShortName = null;
			JSONObject addressComponent = jsonAddress.getJSONObject(i);
	        ArrayList<String> addressTypes = Utility.getStringArrayFromJson(addressComponent, "types");
	        oLongName = Utility.getStringFromJson(addressComponent, "long_name");
			oShortName = Utility.getStringFromJson(addressComponent, "short_name");
			oLongName = oLongName == null ? "" : oLongName;
			oShortName = oShortName == null ? "" : oShortName;
	        for (String addressType : addressTypes) {
				switch (addressType) {
				case "postal_code":
					this.postalCode = oLongName;
					break;
				case "country":
					this.longCountry = oLongName;
					this.shortCountry = oShortName;
					break;
				case "administrative_area_level_1":
					this.region = oLongName;
					break;
				case "administrative_area_level_2":
					this.longProvince = oLongName;
					this.shortProvince = oShortName;
					break;
				case "administrative_area_level_3":
					this.longMunicipality = oLongName;
					this.shortMunicipality = oShortName;
					break;
				case "route":
					this.longRoute = oLongName;
					this.shortRoute = oShortName;
					break;
				default:
					break;
				}
			}
	    }
	}

	public String getLongCountry() {
		return longCountry;
	}

	public void setLongCountry(String longCountry) {
		this.longCountry = longCountry;
	}

	public String getShortCountry() {
		return shortCountry;
	}

	public void setShortCountry(String shortCountry) {
		this.shortCountry = shortCountry;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLongProvince() {
		return longProvince;
	}

	public void setLongProvince(String longProvince) {
		this.longProvince = longProvince;
	}

	public String getShortProvince() {
		return shortProvince;
	}

	public void setShortProvince(String shortProvince) {
		this.shortProvince = shortProvince;
	}

	public String getLongMunicipality() {
		return longMunicipality;
	}

	public void setLongMunicipality(String longMunicipality) {
		this.longMunicipality = longMunicipality;
	}

	public String getShortMunicipality() {
		return shortMunicipality;
	}

	public void setShortMunicipality(String shortMunicipality) {
		this.shortMunicipality = shortMunicipality;
	}

	public String getLongRoute() {
		return longRoute;
	}

	public void setLongRoute(String longRoute) {
		this.longRoute = longRoute;
	}

	public String getShortRoute() {
		return shortRoute;
	}

	public void setShortRoute(String shortRoute) {
		this.shortRoute = shortRoute;
	}
	
	public String toString() {		
		return "   Long country: " + this.longCountry + "\n" +
			   "   Short country: " + this.shortCountry + "\n" +
			   "   Region: " + this.region + "\n" +
			   "   Long Province: " + this.longProvince + "\n" +
			   "   Short Province: " + this.shortProvince + "\n" +
			   "   Zip Code: " + this.zipCode + "\n" +
			   "   Postal Code: " + this.postalCode + "\n" +
			   "   Long Municipality: " + this.longMunicipality + "\n" +
			   "   Short Municipality: " + this.shortMunicipality + "\n" +
			   "   Long Route: " + this.longRoute + "\n" +
			   "   Short Route: " + this.shortRoute;
	}
		
}
