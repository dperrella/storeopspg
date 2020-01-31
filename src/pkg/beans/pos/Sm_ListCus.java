package pkg.beans.pos;

public class Sm_ListCus{
	
	private String ids;
	private String brn;
	private String des;
	private String log;

	
	public Sm_ListCus() {}

	public Sm_ListCus(String ids, String brn, String des, String log) {
		this.ids=ids;
		this.brn=brn;		
		this.des=des;
		this.log=log;
	}
	
	public String getids() {
		return ids;
	}
	public void setids(String ids) {
		this.ids = ids;
	}
	
	public String getbrn() {
		return brn;
	}
	public void setbrn(String brn) {
		this.brn = brn;
	}
	
	public String getdes() {
		return des;
	}
	public void setdes(String des) {
		this.des = des;
	}	
	
	public String getlog() {
		return log;
	}
	public void setlog(String log) {
		this.log = log;
	}
	
}
