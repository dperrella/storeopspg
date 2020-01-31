package eu.mcsistemi.model;
import java.util.List;


public class User {
	
	private String userId;
	private String name;
	private String surname;
	private enum Role{storeManager,taggingSpecialist};
	private Role role;
	private String customers;
	private String items;
	
	public User(String userId, String name, String surname, String role, String customers, String items) {
		this.userId = userId;
		this.name = name;
		this.surname = surname;
		this.role = Role.valueOf(role);
		this.customers = customers;
		this.items = items;
	}
	

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setRole(String role) {
		this.role = Role.valueOf(role);
	}
	public String getRole() {
		return role.toString();
	}
	public String getCustomers() {
		return customers;
	}


	public void setCustomers(String customers) {
		this.customers = customers;
	}


	public String getItems() {
		return items;
	}


	public void setItems(String items) {
		this.items = items;	
	}
	
	


}

