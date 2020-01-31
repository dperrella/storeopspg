package eu.mcsistemi.Entity;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import eu.mcsistemi.PlaceAddress;
import eu.mcsistemi.Utility.Constants;
import eu.mcsistemi.Utility.Utility;

import org.json.JSONArray;

public class Place {
	
	public String formattedAddress;
	public Coordinate position;
	public String name;
	public String icon;
	public String placeId;
	public Double rating;
	public ArrayList<Photo> photos;
	public ArrayList<String> types;
	public PlaceAddress address;
	public String internationalPhoneNumber;
	public WeekOpeningPlan weekOpeningPlan;
	public String url;
	
	public Place(String placeID){
		try {
			System.out.println(placeID);
			JSONObject placesAnswer = Utility.getPlaceAnswer(placeID);
			System.out.println(placesAnswer.toString());
			String oFormattedAddress = Utility.getStringFromJson(placesAnswer, "formatted_address");
			this.formattedAddress = oFormattedAddress.equals(null) ? "" : oFormattedAddress;
	        

			if (placesAnswer.has("geometry") && placesAnswer.getJSONObject("geometry").has("location"))
				this.position = new Coordinate(placesAnswer.getJSONObject("geometry").getJSONObject("location").getDouble("lat"), placesAnswer.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
			else
				this.position = null;
			
			String oName = Utility.getStringFromJson(placesAnswer, "name");
			this.name = oName.equals(null) ? "" : oName;
			
			String oIcon = Utility.getStringFromJson(placesAnswer, "icon");
			this.icon = oIcon.equals(null) ? "" : oIcon;
			
			this.placeId = placeID;
			
			Object oRating = Utility.getDoubleFromJson(placesAnswer, "rating");
			try {
				this.rating = oRating.equals(null) ? -1.0 : (Double)oRating;
			}
			catch(Exception e) {
				this.rating = 0.0;
			}
			if (placesAnswer.has("photos"))
				this.photos = Photo.getPhotos(placesAnswer.getJSONArray("photos"));
			else
				this.photos = new ArrayList<>();
			
			this.types = Utility.getStringArrayFromJson(placesAnswer, "types");
			
			JSONArray jsonAddress = Utility.getJsonArrayFromJson(placesAnswer, "address_components");
			this.address = jsonAddress == null ? new PlaceAddress() : new PlaceAddress(jsonAddress);
			
			String oInternationalPhoneNumber = Utility.getStringFromJson(placesAnswer, "international_phone_number");
			try {
				this.internationalPhoneNumber = oInternationalPhoneNumber.equals(null) ? "" : oInternationalPhoneNumber;
			}
			catch(Exception e) {
				this.internationalPhoneNumber = "-";
			}
			JSONObject oJsonObject = Utility.getJsonObjectFromJson(placesAnswer, "opening_hours");
			JSONArray oJsonArray = oJsonObject == null ? null : Utility.getJsonArrayFromJson(oJsonObject, "periods");
			weekOpeningPlan = oJsonArray == null ? new WeekOpeningPlan() : new WeekOpeningPlan(oJsonArray);
			
			String oUrl = Utility.getStringFromJson(placesAnswer, "url");
			this.url = oUrl.equals(null) ? "" : oUrl;
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String toReturn = "";
		
		toReturn = toReturn + "Name: " + this.name + "\n" +
				"Formatted Address: " + this.formattedAddress + "\n" +
				"Position:\n" + this.position.toString() + "\n" +
				"Icon: " + this.icon + "\n" +
				"Place ID: " + this.placeId + "\n" +
				"Rating: " + this.rating + "\n" +
				"Photos:\n" + this.photosToString() +
				"Types:\n" + this.typesToString() +
				"Address:\n" + this.address.toString() + "\n" +
				"Phone number: " + this.internationalPhoneNumber + "\n" +
				"Weekly Opening Plan:\n" + this.weekOpeningPlan.toString() +
				"Url: " + this.url + "\n";		
		return toReturn;
	}
	
	public String photosToString() {
		String toReturn = "";
		int i = 0;
		for (Photo photo : photos) {
			toReturn = toReturn + "   Photo " + i + ":\n" + photo.toString();
			i++;
		}
		return toReturn;
	}
	
	public String typesToString() {
		String toReturn = "";
		int i = 0;
		for (String type : types) {
			toReturn = toReturn + "   Type " + i + ": " + type.toString() + "\n";
			i++;
		}
		return toReturn;
	}
	
	public String name() {
		return this.name;
	}
	
	public String formattedAddress() {
		return this.formattedAddress;
	}
	
	public double latitude() {
		return this.position.getLatitude();
	}
	
	public double longitude() {
		return this.position.getLongitude();
	}
	
	public String icon() {
		return this.icon;
	}
	
	public double rating() {
		return this.rating;
	}
	
	public String longCountry() {
		return this.address.getLongCountry();
	}
	
	public String shortCountry() {
		return this.address.getShortCountry();
	}
	
	public String region() {
		return this.address.getRegion();
	}
	
	public String longProvince() {
		return this.address.getLongProvince();
	}
	
	public String shortProvince() {
		return this.address.getShortProvince();
	}
	
	public String zipCode() {
		return this.address.getZipCode();
	}
	
	public String postalCode() {
		return this.address.getPostalCode();
	}
	
	public String longMunicipality() {
		return this.address.getLongMunicipality();
	}
	
	public String shortMunicipality() {
		return this.address.getShortMunicipality();
	}
	
	public String longRoute() {
		return this.address.getLongRoute();
	}
	public String shortRoute() {
		return this.address.getShortRoute();
	}
	
	public String phone() {
		return this.internationalPhoneNumber;
	}
	public String url() {
		return this.url;
	}
	
	public ArrayList<String> tipi() {
		return this.types;
	}
	
}
