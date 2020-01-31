package eu.mcsistemi.model;

import java.util.List;

public class Store {
	private int storeId;
	private String description;
	private List <Audit> audits;
	private List <String> barcodes;
	private int idCustomer;
	private int itemCount;
	
	
	public Store(int storeId, String description, int idCustomer, int itemCount) {
		this.storeId = storeId;
		this.description = description;
		this.idCustomer = idCustomer;
		this.itemCount = itemCount;
	}
	
	
	public Store(int storeId, String description, List<Audit> audits, List<String> barcodes, int idCustomer) {
		this.storeId = storeId;
		this.description = description;
		this.audits = audits;
		this.barcodes = barcodes;
		this.idCustomer = idCustomer;
	}
	
	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getIdCustomer() {
		return idCustomer;
	}


	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Audit> getAudits() {
		return audits;
	}

	public void setAudits(List<Audit> audits) {
		this.audits = audits;
	}



	public List<String> getItems() {
		return barcodes;
	}



	public void setItems(List<String> items) {
		this.barcodes = items;
	}

	
	public int getItemCount() {
		return itemCount;
	}


	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

}
	
