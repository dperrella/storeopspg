package pkg.beans.HR;

public class RelUserEmp {
	private String id;
	private String id_employee;
	private String id_user;
	private String des_employee;
	private String des_user;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public RelUserEmp() {}

	public RelUserEmp(String id, String id_employee, String id_user,
			String des_employee, String des_user,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.id_employee=id_employee;
		this.id_user=id_user;
		this.des_employee=des_employee;
		this.des_user=des_user;
		
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
	
	public String getId_user() {
		return id_user;
	}
	public void setId_user(String id_user) {
		this.id_user = id_user;
	}
	
	public String getDes_employee() {
		return des_employee;
	}
	public void setDes_employee(String des_employee) {
		this.des_employee = des_employee;
	}
	
	public String getDes_user() {
		return des_user;
	}
	public void setDes_user(String des_user) {
		this.des_user = des_user;
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
