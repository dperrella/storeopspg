package pkg.beans;

public class DeviceSlaves {
	private String id_slave;
	private String enabled;
	private String id_device;
	private String slavename;
	private String parent_serial_no;
	private String device_type_id;
	private String address;
	private String door_description;
	private String serial_no;
	private String door_type_id;
	private String id_slave_char;
	private String ip;
	private String eventcode;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public DeviceSlaves() {}

	public DeviceSlaves(String id_slave, String enabled, String id_device, 
			String slavename, String parent_serial_no, String device_type_id, String address, String door_description,
			String serial_no, String door_type_id, String id_slave_char, String ip, String eventcode,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_slave=id_slave;
		this.enabled=enabled;
		this.id_device=id_device;
		this.slavename=slavename;
		this.parent_serial_no=parent_serial_no;
		this.device_type_id=device_type_id;
		this.address=address;
		this.door_description=door_description;
		this.serial_no=serial_no;
		this.door_type_id=door_type_id;
		this.id_slave_char=id_slave_char;
		this.ip=ip;
		this.eventcode=eventcode;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId_slave() {
		return id_slave;
	}
	public void setId_slave(String id_slave) {
		this.id_slave = id_slave;
	}

	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public String getId_device() {
		return id_device;
	}
	public void setId_device(String id_device) {
		this.id_device = id_device;
	}
	
	public String getSlavename() {
		return slavename;
	}
	public void setSlavename(String slavename) {
		this.slavename = slavename;
	}
	
	public String getParent_serial_no() {
		return parent_serial_no;
	}
	public void setParent_serial_no(String parent_serial_no) {
		this.parent_serial_no = parent_serial_no;
	}
	
	public String getDevice_type_id() {
		return device_type_id;
	}
	public void setDevice_type_id(String device_type_id) {
		this.device_type_id = device_type_id;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDoor_description() {
		return door_description;
	}
	public void setDoor_description(String door_description) {
		this.door_description = door_description;
	}
	
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	
	public String getDoor_type_id() {
		return door_type_id;
	}
	public void setDoor_type_id(String door_type_id) {
		this.door_type_id = door_type_id;
	}
	
	public String getId_slave_char() {
		return id_slave_char;
	}
	public void setId_slave_char(String id_slave_char) {
		this.id_slave_char = id_slave_char;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getEventcode() {
		return eventcode;
	}
	public void setEventcode(String eventcode) {
		this.eventcode = eventcode;
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
