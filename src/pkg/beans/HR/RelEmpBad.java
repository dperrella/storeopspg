package pkg.beans.HR;

public class RelEmpBad {
	private String id_relempbadge;
	private String id_employee;
	private String id_badge;
	private String bad_number;
	private String dateend;
	private String emp_name;
	private String emp_surname;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public RelEmpBad() {}

	public RelEmpBad(String id_relempbadge, String id_employee, String id_badge, String bad_number,
			String dateend, String emp_name, String emp_surname,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_relempbadge=id_relempbadge;
		this.id_employee=id_employee;
		this.id_badge=id_badge;
		this.bad_number=bad_number;
		this.dateend=dateend;
		this.emp_name=emp_name;
		this.emp_surname=emp_surname;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId_relempbadge() {
		return id_relempbadge;
	}
	public void setId_relempbadge(String id_relempbadge) {
		this.id_relempbadge = id_relempbadge;
	}

	public String getId_employee() {
		return id_employee;
	}
	public void setId_employee(String id_employee) {
		this.id_employee = id_employee;
	}
	
	public String getId_badge() {
		return id_badge;
	}
	public void setId_badge(String id_badge) {
		this.id_badge = id_badge;
	}
	
	public String getBad_number() {
		return bad_number;
	}
	public void setBad_number(String bad_number) {
		this.bad_number = bad_number;
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
