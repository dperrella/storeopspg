package pkg.beans;

public class FinancialCalendar {
	private String caltype;
	private int year;
	private int calperiod;
	private String fromdate;
	private String todate;
	private int customer_id;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public FinancialCalendar() {}

	public FinancialCalendar(String caltype, int year, int calperiod, 
			String fromdate, String todate, int customer_id, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.caltype=caltype;
		this.year=year;
		this.calperiod=calperiod;
		this.fromdate=fromdate;
		this.todate=todate;
		this.customer_id=customer_id;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getCaltype() {
		return caltype;
	}
	public void setCaltype(String caltype) {
		this.caltype = caltype;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getCalperiod() {
		return calperiod;
	}
	public void setCalperiod(int calperiod) {
		this.calperiod = calperiod;
	}

	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
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
