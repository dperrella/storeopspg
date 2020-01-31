package eu.mcsistemi.model;

import java.util.List;

public class Customer {
	String description;
	int customerId;
	List<Store> stores;
	
	
	public Customer(String description, int customerId, List<Store> stores) {
		this.description = description;
		this.customerId = customerId;
		this.stores = stores;
	}


	public List<Store> getStores() {
		return stores;
	}


	public void setStores(List<Store> stores) {
		this.stores = stores;
	}


	public Customer(String description, int customerId) {
		this.description = description;
		this.customerId = customerId;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
}
