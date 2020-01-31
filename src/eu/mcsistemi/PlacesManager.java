package eu.mcsistemi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.mcsistemi.Entity.Photo;
import eu.mcsistemi.Entity.PlaceEntry;
import eu.mcsistemi.Utility.Utility;

public class PlacesManager {
	
	private ArrayList<PlaceEntry> placesEntry;
	
	public void searchPlaces(String searchText){
		placesEntry = new ArrayList<>();
		try {
			JSONObject jsonAnswer = Utility.getPlacesFromText(searchText);
			JSONArray jsonResult = jsonAnswer.getJSONArray("results");
			for (int i = 0; i < jsonResult.length(); i++) {
				JSONObject storeEntry = jsonResult.getJSONObject(i);
				placesEntry.add(new PlaceEntry(storeEntry));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<PlaceEntry> getPlacesEntry() {
		return placesEntry;
	}

	public void setPlacesEntry(ArrayList<PlaceEntry> placesEntry) {
		this.placesEntry = placesEntry;
	}
	
	public String toString() {
		String toReturn = "Places:\n";
		int i = 0;
		for (PlaceEntry placeEntry : placesEntry) {
			toReturn = toReturn + "   Place " + i + ":\n" + placeEntry.toString();
			i++;
		}
		return toReturn;
	}
}
