package pkg.beans.HR;

public class Minnum_employee {
	private String id;
	private String id_departments;
	private String description;
	private String plan_year;
	private String plan_week;
	private String plan_day;
	private String plan_hour;
	private String value;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Minnum_employee() {}

	public Minnum_employee(String id, String id_departments, String description, String plan_year,
			String plan_week, String plan_day, String plan_hour, String value, String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.id_departments=id_departments;
		this.description=description;
		this.plan_year=plan_year;
		this.plan_week=plan_week;
		this.plan_day=plan_day;
		this.plan_hour=plan_hour;
		this.value=value;
		
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

	public String getId_departments() {
		return id_departments;
	}
	public void setId_departments(String id_departments) {
		this.id_departments = id_departments;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPlan_year() {
		return plan_year;
	}
	public void setPlan_year(String plan_year) {
		this.plan_year = plan_year;
	}
	
	public String getPlan_week() {
		return plan_week;
	}
	public void setPlan_week(String plan_week) {
		this.plan_week = plan_week;
	}
	
	public String getPlan_day() {
		return plan_day;
	}
	public void setPlan_day(String plan_day) {
		this.plan_day = plan_day;
	}
	
	public String getPlan_hour() {
		return plan_hour;
	}
	public void setPlan_hour(String plan_hour) {
		this.plan_hour = plan_hour;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
