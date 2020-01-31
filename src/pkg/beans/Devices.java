package pkg.beans;

public class Devices {
	
	private String ip;
	private String port;
	private String macaddr;
	private String lasttime;
	private String enabled;
	private String accountid;
	private String storeid;
	private String timezone;
	private String lin;
	private String lout;
	private String device_type;
	private String readevery;
	private String id;
	private String id_store;
	private String updating;
	private String fromdate;
	private String deviceid;
	private String devicename;
	private String siteid;
	private String sitename;
	private String doorid;
	private String logenabled;
	private String notes;
	private String update_time;
	private String unit_time;
	private String cause;
	private String date_disabled;
	private String adjustment_factor;
	private String checktime;
	private String lasteventcode;
	private String diff_file_time;
	private String customer_devicename;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Devices() {}

	public Devices(String ip, String port, String macaddr, String lasttime, String enabled,
			String accountid, String storeid, String timezone, String lin, String lout, String device_type,
			String readevery, String id, String id_store, String updating, String fromdate, String deviceid,
			String devicename, String siteid, String sitename, String doorid, String logenabled,
			String notes, String update_time, String unit_time, String cause, String date_disabled,
			String adjustment_factor, String checktime, String lasteventcode, String diff_file_time, String customer_devicename,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.ip=ip;
		this.port=port;
		this.macaddr=macaddr;
		this.lasttime=lasttime;
		this.enabled=enabled;
		this.accountid=accountid;
		this.storeid=storeid;
		this.timezone=timezone;
		this.lin=lin;
		this.lout=lout;
		this.device_type=device_type;
		this.readevery=readevery;
		this.id=id;
		this.id_store=id_store;
		this.updating=updating;
		this.fromdate=fromdate;
		this.deviceid=deviceid;
		this.devicename=devicename;
		this.siteid=siteid;
		this.sitename=sitename;
		this.doorid=doorid;
		this.logenabled=logenabled;
		this.notes=notes;
		this.update_time=update_time;
		this.unit_time=unit_time;
		this.cause=cause;
		this.date_disabled=date_disabled;
		this.adjustment_factor=adjustment_factor;
		this.checktime=checktime;
		this.lasteventcode=lasteventcode;
		this.diff_file_time=diff_file_time;
		this.customer_devicename=customer_devicename;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getMacaddr() {
		return macaddr;
	}
	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}
	
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
	public String getLin() {
		return lin;
	}
	public void setLin(String lin) {
		this.lin = lin;
	}
	
	public String getLout() {
		return lout;
	}
	public void setLout(String lout) {
		this.lout = lout;
	}

	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	
	public String getReadevery() {
		return readevery;
	}
	public void setReadevery(String readevery) {
		this.readevery = readevery;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId_store() {
		return id_store;
	}
	public void setId_store(String id_store) {
		this.id_store = id_store;
	}
	
	public String getUpdating() {
		return updating;
	}
	public void setUpdating(String updating) {
		this.updating = updating;
	}
	
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	
	public String getSiteid() {
		return siteid;
	}
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	
	public String getDoorid() {
		return doorid;
	}
	public void setDoorid(String doorid) {
		this.doorid = doorid;
	}
	
	public String getLogenabled() {
		return logenabled;
	}
	public void setLogenabled(String logenabled) {
		this.logenabled = logenabled;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	public String getUnit_time() {
		return unit_time;
	}
	public void setUnit_time(String unit_time) {
		this.unit_time = unit_time;
	}
	
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public String getDate_disabled() {
		return date_disabled;
	}
	public void setDate_disabled(String date_disabled) {
		this.date_disabled = date_disabled;
	}
	
	public String getAdjustment_factor() {
		return adjustment_factor;
	}
	public void setAdjustment_factor(String adjustment_factor) {
		this.adjustment_factor = adjustment_factor;
	}
	
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	
	public String getLasteventcode() {
		return lasteventcode;
	}
	public void setLasteventcode(String lasteventcode) {
		this.lasteventcode = lasteventcode;
	}
	
	public String getDiff_file_time() {
		return diff_file_time;
	}
	public void setDiff_file_time(String diff_file_time) {
		this.diff_file_time = diff_file_time;
	}
	
	public String getCustomer_devicename() {
		return customer_devicename;
	}
	public void setCustomer_devicename(String customer_devicename) {
		this.customer_devicename = customer_devicename;
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
