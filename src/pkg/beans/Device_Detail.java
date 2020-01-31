package pkg.beans;

public class Device_Detail {
	private String id;
	private String id_device;
	private String id_line;
	private String id_area_from;
	private String id_area_to;
	private String note;
	private String device_description;
	private String area_from_description;
	private String area_to_description;
	private String status;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Device_Detail() {}

	public Device_Detail(String id, String id_device, String id_line, 
			String id_area_from, String id_area_to, String note,
			String device_description, String area_from_description, String area_to_description, String status,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.id_device=id_device;
		this.id_line=id_line;
		this.id_area_from=id_area_from;
		this.id_area_to=id_area_to;
		this.note=note;
		this.device_description=device_description;
		this.area_from_description=area_from_description;
		this.area_to_description=area_to_description;
		this.status=status;
		
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

	public String getId_device() {
		return id_device;
	}
	public void setId_device(String id_device) {
		this.id_device = id_device;
	}
	
	public String getId_line() {
		return id_line;
	}
	public void setId_line(String id_line) {
		this.id_line = id_line;
	}
	
	public String getId_area_from() {
		return id_area_from;
	}
	public void setId_area_from(String id_area_from) {
		this.id_area_from = id_area_from;
	}
	
	public String getId_area_to() {
		return id_area_to;
	}
	public void setId_area_to(String id_area_to) {
		this.id_area_to = id_area_to;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getDevice_description() {
		return device_description;
	}
	public void setDevice_description(String device_description) {
		this.device_description = device_description;
	}
	
	public String getArea_from_description() {
		return area_from_description;
	}
	public void setArea_from_description(String area_from_description) {
		this.area_from_description = area_from_description;
	}
	
	public String getArea_to_description() {
		return area_to_description;
	}
	public void setArea_to_description(String area_to_description) {
		this.area_to_description = area_to_description;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
