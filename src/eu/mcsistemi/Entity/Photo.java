package eu.mcsistemi.Entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.mcsistemi.Utility.Utility;

public class Photo {
	private int height;
	private int width;
	private String reference;
	
	public Photo(int height, int width, String reference) {
		super();
		this.height = height;
		this.width = width;
		this.reference = reference;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public static ArrayList<Photo> getPhotos(JSONArray photos){
		ArrayList<Photo> photosToReturn  = new ArrayList<>();
		for (int i = 0 ; i < photos.length(); i++) {
	        JSONObject photo = photos.getJSONObject(i);
	        Object height = Utility.getIntFromJson(photo, "height");
	        Object width = Utility.getIntFromJson(photo, "width");
	        String photoReference = Utility.getStringFromJson(photo, "photo_reference");
	        int iHeight = height.equals(null) ? -1 : (int)height;
	        int iWidth = width.equals(null) ? -1 : (int)width;
	        photoReference = photoReference.equals(null) ? "" : photoReference;
	        photosToReturn.add(new Photo(iHeight, iWidth, photoReference));
		}
		return photosToReturn;
	}
	
	public String toString() {
		return "      height: " + this.height + "\n" +
			   "      width: " + this.width + "\n" +
			   "      reference: " + this.reference + "\n";
	}
}
