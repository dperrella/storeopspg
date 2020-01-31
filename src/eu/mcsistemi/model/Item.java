package eu.mcsistemi.model;

import java.util.Date;
import java.util.List;

public class Item {
	protected String barcode;
	protected String name;
	protected String description;
	protected String vendor;
	protected String brand;
	protected String country;
	protected String protectionType;
	protected String evolutionReportLink;
	protected Integer lotsNumber;
	protected List<Photo> photos;
	
	
	
	public Item(String barcode, String name, String description, String vendor, String brand, String country,
			String protectionType, String evolutionReportLink, Integer lotsNumber) {
		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.vendor = vendor;
		this.brand = brand;
		this.country = country;
		this.protectionType = protectionType;
		this.evolutionReportLink = evolutionReportLink;
		this.lotsNumber = lotsNumber;
	}
	
	public Item(String barcode, String name, String description, String vendor, String brand, String country,
			String protectionType, String evolutionReportLink) {
		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.vendor = vendor;
		this.brand = brand;
		this.country = country;
		this.protectionType = protectionType;
		this.evolutionReportLink = evolutionReportLink;
	}
	
	public Item(String barcode, String name, String description, String vendor, String brand, String country,
			String protectionType, String evolutionReportLink, List<Photo> photos) {
		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.vendor = vendor;
		this.brand = brand;
		this.country = country;
		this.protectionType = protectionType;
		this.evolutionReportLink = evolutionReportLink;
		this.photos = photos;
	}

	public Item(String barcode, String name, String description, String vendor, String brand, String country,
			String protectionType, String evolutionReportLink, Integer lotsNumber, List<Photo> photos) {

		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.vendor = vendor;
		this.brand = brand;
		this.country = country;
		this.protectionType = protectionType;
		this.evolutionReportLink = evolutionReportLink;
		this.lotsNumber = lotsNumber;
		this.photos = photos;
	}

	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProtectionType() {
		return protectionType;
	}
	public void setProtectionType(String protectionType) {
		this.protectionType = protectionType;
	}
	public String getEvolutionReportLink() {
		return evolutionReportLink;
	}
	public void setEvolutionReportLink(String evolutionReportLink) {
		this.evolutionReportLink = evolutionReportLink;
	}
	public Integer getLotsNumber() {
		return this.lotsNumber;
	}


	public void setLotsNumber(Integer lotsNumber) {
		this.lotsNumber = lotsNumber;
	}



	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

}
