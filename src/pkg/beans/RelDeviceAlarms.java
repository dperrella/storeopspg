package pkg.beans;

public class RelDeviceAlarms {
	private String id;
	private String description;
	private String brandname;
	private String ip;
	private String type;
	private String device_type;
	private String alarm_code;
	private String alarm_descr;
	private String alarm_range;
	private String aut;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public RelDeviceAlarms() {}

	public RelDeviceAlarms(String id, String description, String brandname, 
			String ip, String type, String device_type,
			String alarm_code, String alarm_descr, String alarm_range, String aut,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.description=description;
		this.brandname=brandname;
		this.ip=ip;
		this.type=type;
		this.device_type=device_type;
		this.alarm_code=alarm_code;
		this.alarm_descr=alarm_descr;
		this.alarm_range=alarm_range;
		this.aut=aut;
		
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
	
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	
	public String getAlarm_code() {
		return alarm_code;
	}
	public void setAlarm_code(String alarm_code) {
		this.alarm_code = alarm_code;
	}
	
	public String getAlarm_descr() {
		return alarm_descr;
	}
	public void setAlarm_descr(String alarm_descr) {
		this.alarm_descr = alarm_descr;
	}
	
	public String getAlarm_range() {
		return alarm_range;
	}
	public void setAlarm_range(String alarm_range) {
		this.alarm_range = alarm_range;
	}
	
	public String getAut() {
		return aut;
	}
	public void setAut(String aut) {
		this.aut = aut;
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
