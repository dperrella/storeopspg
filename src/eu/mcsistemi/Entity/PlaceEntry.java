package eu.mcsistemi.Entity;

import java.util.ArrayList;
import org.json.JSONObject;
import eu.mcsistemi.Utility.Utility;

public class PlaceEntry {
	
	private String formattedAddress;
	private Coordinate position;
	public String name;
	private String placeId;
	private ArrayList<Photo> photos;
	
	public PlaceEntry(JSONObject placeEntryJson) {
		
		String oFormattedAddress = Utility.getStringFromJson(placeEntryJson, "formatted_address");
		this.formattedAddress = oFormattedAddress.equals(null) ? "" : oFormattedAddress;
        
		if (placeEntryJson.has("geometry") && placeEntryJson.getJSONObject("geometry").has("location"))
			this.position = new Coordinate(placeEntryJson.getJSONObject("geometry").getJSONObject("location").getDouble("lat"), placeEntryJson.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
		else
			this.position = null;
		
		String oName = Utility.getStringFromJson(placeEntryJson, "name");
		this.name = oName.equals(null) ? "" : oName;
		
		String oPlaceId = Utility.getStringFromJson(placeEntryJson, "place_id");
		this.placeId = oPlaceId.equals(null) ? "" : oPlaceId;
		
		if (placeEntryJson.has("photos"))
			this.photos = Photo.getPhotos(placeEntryJson.getJSONArray("photos"));
		else
			this.photos = new ArrayList<>();
		
	}
	
	public String toString() {
		String toReturn = "";
		
		toReturn = toReturn + "Name: " + this.name + "\n" +
				"Formatted Address: " + this.formattedAddress + "\n" +
				"Position:\n" + this.position.toString() + "\n" +
				"Place ID: " + this.placeId + "\n" +
				"Photos:\n" + this.photosToString();		
		return toReturn;
	}
	
	public String photosToString() {
		String toReturn = "";
		int i = 0;
		for (Photo photo : this.photos) {
			toReturn = toReturn + "   Photo " + i + ":\n" + photo.toString();
			i++;
		}
		return toReturn;
	}
	
	public String name() {
		return this.name;	
	}
	public String address() {
		return this.formattedAddress;	
	}
	public double latitude() {
		return this.position.getLatitude();	
	}
	public double longitude() {
		return this.position.getLongitude();	
	}
	public String uid() {
		return this.placeId;	
	}
}
