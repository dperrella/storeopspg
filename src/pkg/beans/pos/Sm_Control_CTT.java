package pkg.beans.pos;

public class Sm_Control_CTT {
	
	private String idop;
	private String day;
	private String from;
	private String to;
	private String id;
	
	public Sm_Control_CTT() {}

	public Sm_Control_CTT(String idop, String day,String from, String to,String id) {
		this.idop=idop;
		this.day=day;
		this.from=from;
		this.to=to;
		this.id=id;
	}
	
	public String getidop() {
		return idop;
	}
	public void setidop(String idop) {
		this.idop = idop;
	}
	
	public String getday() {
		return day;
	}
	public void setday(String day) {
		this.day = day;
	}	
	
	public String getfrom() {
		return from;
	}
	public void setfrom(String from) {
		this.from = from;
	}	

	public String getto() {
		return to;
	}
	public void setto(String to) {
		this.to = to;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
		
}
