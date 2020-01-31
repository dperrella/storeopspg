package pkg.beans.HR;

public class Contracts {
	private String id_contract;
	private String id_employee;
	private String datada;
	private String dataal;
	private String monteore;
	private String saldoore;
	private String datasaldo;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Contracts() {}

	public Contracts(String id_contract, String id_employee, String datada, String dataal,
			String monteore, String saldoore, String datasaldo,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_contract=id_contract;
		this.id_employee=id_employee;
		this.datada=datada;
		this.dataal=dataal;
		this.monteore=monteore;
		this.saldoore=saldoore;
		this.datasaldo=datasaldo;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId_contract() {
		return id_contract;
	}
	public void setId_contract(String id_contract) {
		this.id_contract = id_contract;
	}

	public String getId_employee() {
		return id_employee;
	}
	public void setId_Employee(String id_employee) {
		this.id_employee = id_employee;
	}
	
	public String getDatada() {
		return datada;
	}
	public void setDatada(String datada) {
		this.datada = datada;
	}
	
	public String getDataal() {
		return dataal;
	}
	public void setDataal(String dataal) {
		this.dataal = dataal;
	}
	
	public String getMonteore() {
		return monteore;
	}
	public void setMonteore(String monteore) {
		this.monteore = monteore;
	}
	
	public String getSaldoore() {
		return saldoore;
	}
	public void setSaldoore(String saldoore) {
		this.saldoore = saldoore;
	}
	
	public String getDatasaldo() {
		return datasaldo;
	}
	public void setDatasaldo(String datasaldo) {
		this.datasaldo = datasaldo;
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
