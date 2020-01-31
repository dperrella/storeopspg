package pkg.beans;

public class Warehouse {
	private String id;
	private String description;
	private String is_origin;
	private String is_destination;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Warehouse() {}

	public Warehouse(String id, String description, String is_origin, String is_destination, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.description=description;
		this.is_origin=is_origin;
		this.is_destination=is_destination;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIs_origin() {
		return is_origin;
	}
	public void setIs_origin(String is_origin) {
		this.is_origin = is_origin;
	}
	
	public String getIs_destination() {
		return is_destination;
	}
	public void setIs_destination(String is_destination) {
		this.is_destination = is_destination;
	}
	
	public String getCmp1() {
		return cmp1;
	}
	public void setCmp1(String cmp1) {
		this.cmp1 = cmp1;
	}
	
	public String getCmp2() {
		return cmp2;
	}
	public void setCmp2(String cmp2) {
		this.cmp2 = cmp2;
	}
	
	public String getCmp3() {
		return cmp3;
	}
	public void setCmp3(String cmp3) {
		this.cmp3 = cmp3;
	}
	
	public String getCmp4() {
		return cmp4;
	}
	public void setCmp4(String cmp4) {
		this.cmp4 = cmp4;
	}
}
