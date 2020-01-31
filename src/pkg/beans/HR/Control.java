package pkg.beans.HR;

public class Control {
	private String id_employee;
	private String des_employee;
	private String id_departments;
	private String des_departments;
	private String day_ref;
	private String type;
	private String des_type;
	private String ingresso;
	private String inizio_pausa;
	private String fine_pausa;
	private String uscita;
	private String totale;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Control() {}

	public Control(String id_employee, String des_employee, String id_departments, String des_departments,
			String day_ref, String type, String des_type, String ingresso, String inizio_pausa, String fine_pausa, 
			String uscita, String totale, String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_employee=id_employee;
		this.des_employee=des_employee;
		this.id_departments=id_departments;
		this.des_departments=des_departments;
		this.day_ref=day_ref;
		this.type=type;
		this.des_type=des_type;
		this.ingresso=ingresso;
		this.inizio_pausa=inizio_pausa;
		this.fine_pausa=fine_pausa;
		this.uscita=uscita;
		this.totale=totale;
		
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
	
	public String getDay_ref() {
		return day_ref;
	}
	public void setday_ref(String day_ref) {
		this.day_ref = day_ref;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDes_type() {
		return des_type;
	}
	public void setDes_type(String des_type) {
		this.des_type = des_type;
	}
	
	public String getIngresso() {
		return ingresso;
	}
	public void setIngresso(String ingresso) {
		this.ingresso = ingresso;
	}
	
	public String getInizio_pausa() {
		return inizio_pausa;
	}
	public void setInizio_pausa(String inizio_pausa) {
		this.inizio_pausa = inizio_pausa;
	}
	
	public String getFine_pausa() {
		return fine_pausa;
	}
	public void setFine_pausa(String fine_pausa) {
		this.fine_pausa = fine_pausa;
	}
	
	public String getUscita() {
		return uscita;
	}
	public void setUscita(String uscita) {
		this.uscita = uscita;
	}
	
	public String getTotale() {
		return totale;
	}
	public void setTotale(String totale) {
		this.totale = totale;
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
