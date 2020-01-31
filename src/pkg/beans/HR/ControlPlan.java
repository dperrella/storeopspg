package pkg.beans.HR;

public class ControlPlan {
	private String year;
	private String week;
	private String dtda;
	private String dtal;
	private String id_departments;
	private String des_departments;
	private String status;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public ControlPlan() {}

	public ControlPlan(String year, String week, String dtda, String dtal,
			String id_departments, String des_departments, String status,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.year=year;
		this.week=week;
		this.dtda=dtda;
		this.dtal=dtal;
		this.id_departments=id_departments;
		this.des_departments=des_departments;
		this.status=status;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	
	public String getDtda() {
		return dtda;
	}
	public void setDtda(String dtda) {
		this.dtda = dtda;
	}
	
	public String getDtal() {
		return dtal;
	}
	public void setDtal(String dtal) {
		this.dtal = dtal;
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
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
