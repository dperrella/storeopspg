package eu.mcsistemi.model;

public class Photo {
	private int photoId;
	private String name;
	private String url;
	private String reference;
	private String barcode;
	
	public Photo(int photoId, String name, String url, String reference, String barcode) {
		this.photoId = photoId;
		this.name = name;
		this.url = url;
		this.reference = reference;
		this.barcode = barcode;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	

}
