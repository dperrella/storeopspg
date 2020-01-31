package pkg.beans;

public class ActiveInactiveAlarms {
	private String id;
	private String devicename;
	private String store;
	private String code;
	private String description;
	private String threshold;
	private String timeunit;
	private String control_time;
	private String from_time;
	private String to_time;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public ActiveInactiveAlarms() {}

	public ActiveInactiveAlarms(String id, String devicename, String store,
			String code, String description, String threshold,
			String timeunit, String control_time, String from_time, String to_time,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.devicename=devicename;
		this.store=store;
		this.code=code;
		this.description=description;
		this.threshold=threshold;
		this.timeunit=timeunit;
		this.control_time=control_time;
		this.from_time=from_time;
		this.to_time=to_time;
		
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

	public String geteDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	
	public String geteStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	
	public String geteCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	
	public String getTimeunit() {
		return timeunit;
	}
	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
	}
	
	public String getControl_time() {
		return control_time;
	}
	public void setControl_time(String control_time) {
		this.control_time = control_time;
	}
	
	public String getFrom_time() {
		return from_time;
	}
	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}
	
	public String getTo_time() {
		return to_time;
	}
	public void setTo_time(String to_time) {
		this.to_time = to_time;
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
