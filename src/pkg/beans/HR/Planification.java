package pkg.beans.HR;

public class Planification {
	private String id;
	private String id_employee;
	private String name;
	private String surname;
	private String id_departments;
	private String department_description;
	private String datetime_start;
	private String datetime_end;
	private String id_planification_type;
	private String planification_type_description;
	private String plan_year;
	private String plan_week;
	private String plan_day;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Planification() {}

	public Planification(String id, String id_employee, String name, String surname, 
			String id_departments, String department_description,
			String datetime_start, String datetime_end, 
			String id_planification_type, String planification_type_description,
			String plan_year, String plan_week, String plan_day,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.id_employee=id_employee;
		this.name=name;
		this.surname=surname;
		this.id_departments=id_departments;
		this.department_description=department_description;
		this.datetime_start=datetime_start;
		this.datetime_end=datetime_end;
		this.id_planification_type=id_planification_type;
		this.planification_type_description=planification_type_description;
		this.plan_year=plan_year;
		this.plan_week=plan_week;
		this.plan_day=plan_day;
		
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

	public String getId_employee() {
		return id_employee;
	}
	public void setId_employee(String id_employee) {
		this.id_employee = id_employee;
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
	
	public String getId_departments() {
		return id_departments;
	}
	public void setId_departments(String id_departments) {
		this.id_departments = id_departments;
	}
	
	public String getDepartment_description() {
		return department_description;
	}
	public void setDepartment_description(String department_description) {
		this.department_description = department_description;
	}
	
	public String getDatetime_start() {
		return datetime_start;
	}
	public void setDatetime_start(String datetime_start) {
		this.datetime_start = datetime_start;
	}
	
	public String getDatetime_end() {
		return datetime_end;
	}
	public void setDatetime_end(String datetime_end) {
		this.datetime_end = datetime_end;
	}
	
	public String getId_planification_type() {
		return id_planification_type;
	}
	public void setId_planification_type(String id_planification_type) {
		this.id_planification_type = id_planification_type;
	}
	
	public String getPlanification_type_description() {
		return planification_type_description;
	}
	public void setPlanification_type_description(String planification_type_description) {
		this.planification_type_description = planification_type_description;
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
