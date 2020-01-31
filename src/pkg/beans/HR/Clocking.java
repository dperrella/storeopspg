package pkg.beans.HR;

public class Clocking {
	private String id_employee;
	private String des_employee;
	private String badge;
	private String number;
	private String reason;
	private String timestamp;
	private String note;
	private String approved;
	private String exported;
	private String id_departments;
	private String des_departments;
	private String autoid;
	private String timeins;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Clocking() {}

	public Clocking(String id_employee, String des_employee, String badge, String number,
			String reason, String timestamp, String note, String approved,
			String exported, String id_departments, String des_departments,
			String autoid, String timeins,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_employee=id_employee;
		this.des_employee=des_employee;
		this.badge=badge;
		this.number=number;
		this.reason=reason;
		this.timestamp=timestamp;
		this.note=note;
		this.approved=approved;
		this.exported=exported;
		this.id_departments=id_departments;
		this.des_departments=des_departments;
		this.autoid=autoid;
		this.timeins=timeins;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId_employee() {
		return id_employee;
	}
	public void setId_employee(String id_employee) {
		this.id_employee = id_employee;
	}

	public String getDes_employee() {
		return des_employee;
	}
	public void setDes_Employee(String des_employee) {
		this.des_employee = des_employee;
	}
	
	public String getBadge() {
		return badge;
	}
	public void setBadge(String badge) {
		this.badge = badge;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	public String getExported() {
		return exported;
	}
	public void setExported(String exported) {
		this.exported = exported;
	}
	
	public String getId_departments() {
		return id_departments;
	}
	public void setId_departments(String id_departments) {
		this.id_departments = id_departments;
	}
	
	public String getDes_departments() {
		return des_departments;
	}
	public void setDes_departments(String des_departments) {
		this.des_departments = des_departments;
	}
	
	public String getAutoid() {
		return autoid;
	}
	public void setAutoid(String autoid) {
		this.autoid = autoid;
	}
	
	public String getTimeins() {
		return timeins;
	}
	public void setTimeins(String timeins) {
		this.timeins = timeins;
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
