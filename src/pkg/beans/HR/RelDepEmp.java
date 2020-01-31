package pkg.beans.HR;

public class RelDepEmp {
	private String id_reldepemployee;
	private String id_employee;
	private String id_departments;
	private String id_roles;
	private String datestart;
	private String dateend;
	private String emp_name;
	private String emp_surname;
	private String dep_description;
	private String rol_description;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public RelDepEmp() {}

	public RelDepEmp(String id_reldepemployee, String id_employee, String id_departments, String id_roles, String datestart, 
			String dateend, String emp_name, String emp_surname, String dep_description, String rol_description,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		this.id_reldepemployee=id_reldepemployee;
		this.id_employee=id_employee;
		this.id_departments=id_departments;
		this.id_roles=id_roles;
		this.datestart=datestart;
		this.dateend=dateend;
		this.emp_name=emp_name;
		this.emp_surname=emp_surname;
		this.dep_description=dep_description;
		this.rol_description=rol_description;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId_reldepemployee() {
		return id_reldepemployee;
	}
	public void setId_reldepemployee(String id_reldepemployee) {
		this.id_reldepemployee = id_reldepemployee;
	}

	public String getId_employee() {
		return id_employee;
	}
	public void setId_employee(String id_employee) {
		this.id_employee = id_employee;
	}
	
	public String getId_departments() {
		return id_departments;
	}
	public void setId_departments(String id_departments) {
		this.id_departments = id_departments;
	}
	
	public String getId_roles() {
		return id_roles;
	}
	public void setId_roles(String id_roles) {
		this.id_roles = id_roles;
	}
	
	public String getDatestart() {
		return datestart;
	}
	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}
	
	public String getDateend() {
		return dateend;
	}
	public void setDateend(String dateend) {
		this.dateend = dateend;
	}
	
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	
	public String getEmp_surname() {
		return emp_surname;
	}
	public void setEmp_surname(String emp_surname) {
		this.emp_surname = emp_surname;
	}
	
	public String getDep_description() {
		return dep_description;
	}
	public void setDep_description(String dep_description) {
		this.dep_description = dep_description;
	}
	
	public String getRol_description() {
		return rol_description;
	}
	public void setRol_description(String rol_description) {
		this.rol_description = rol_description;
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
