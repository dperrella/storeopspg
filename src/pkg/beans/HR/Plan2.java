package pkg.beans.HR;

public class Plan2 {
	private String id_employee;
	private String name;
	private String surname;
	
	private String eyeClass;
	private String ruler;
	private String day0;
	private String inputs;
	private String totday1;
	private String totday2;
	private String totday3;
	private String totday4;
	private String totday5;
	private String totday6;
	private String id_dep;
	private String ds_dep;
	private String tot_day_emp;
	
	public Plan2() {}

	public Plan2(String id_employee, String name, String surname, 
			String eyeClass, String ruler, String day0, String inputs, String totday1, String totday2,
			String totday3, String totday4, String totday5, String totday6,
			String id_dep, String ds_dep, String tot_day_emp) {
		
		this.id_employee=id_employee;
		this.name=name;
		this.surname=surname;
		
		this.eyeClass=eyeClass;
		this.ruler=ruler;
		this.day0=day0;
		this.inputs=inputs;
		this.totday1=totday1;
		this.totday2=totday2;
		this.totday3=totday3;
		this.totday4=totday4;
		this.totday5=totday5;
		this.totday6=totday6;
		
		this.id_dep=id_dep;
		this.ds_dep=ds_dep;
		
		this.tot_day_emp=tot_day_emp;
		
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
	
	public String getEyeClass() {
		return eyeClass;
	}
	public void setEyeClass(String eyeClass) {
		this.eyeClass = eyeClass;
	}
	
	public String getRuler() {
		return ruler;
	}
	public void setRuler(String ruler) {
		this.ruler = ruler;
	}
	
	public String getDay0() {
		return day0;
	}
	public void setDay0(String day0) {
		this.day0 = day0;
	}
	
	public String getInputs() {
		return inputs;
	}
	public void setInputs(String inputs) {
		this.inputs = inputs;
	}
	
	public String getTotday1() {
		return totday1;
	}
	public void setHTotday1(String totday1) {
		this.totday1 = totday1;
	}
	
	public String getTotday2() {
		return totday2;
	}
	public void setHTotday2(String totday2) {
		this.totday2 = totday2;
	}
	
	public String getTotday3() {
		return totday3;
	}
	public void setHTotday3(String totday3) {
		this.totday3 = totday3;
	}
	
	public String getTotday4() {
		return totday4;
	}
	public void setHTotday4(String totday4) {
		this.totday4 = totday4;
	}
	
	public String getTotday5() {
		return totday5;
	}
	public void setHTotday5(String totday5) {
		this.totday5 = totday5;
	}
	
	public String getTotday6() {
		return totday6;
	}
	public void setHTotday6(String totday6) {
		this.totday6 = totday6;
	}
	
	public String getId_dep() {
		return id_dep;
	}
	public void setId_Dep(String id_dep) {
		this.id_dep = id_dep;
	}
	
	public String getDs_dep() {
		return ds_dep;
	}
	public void setDs_Dep(String ds_dep) {
		this.ds_dep = ds_dep;
	}
	
	public String getTot_day_emp() {
		return tot_day_emp;
	}
	public void setTot_day_emp(String tot_day_emp) {
		this.tot_day_emp = tot_day_emp;
	}
	
	
}
