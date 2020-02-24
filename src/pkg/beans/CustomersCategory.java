package pkg.beans;

import java.util.ArrayList;

public class CustomersCategory {
	
	private int idCustomer;
	private String category;
	private ArrayList<String> value;
	private boolean required;
	private String parentCategory;
	
	public CustomersCategory() {}

	public CustomersCategory(int idCustomer, String category, ArrayList<String> value, boolean required, String parentCategory) {
		
		this.idCustomer=idCustomer;
		this.category=category;
		this.value=value;
		this.required=required;
		this.parentCategory=parentCategory;
		
	
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<String> getValue() {
		return value;
	}

	public void setValue(ArrayList<String> value) {
		this.value = value;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}
	
	
	
	
}
