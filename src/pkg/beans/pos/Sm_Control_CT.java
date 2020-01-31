package pkg.beans.pos;

public class Sm_Control_CT {
	
	private String id;
	private String day;
	private String from;
	private String to;
	
	public Sm_Control_CT() {}

	public Sm_Control_CT(String id, String day,String from, String to) {
		this.id=id;
		this.day=day;
		this.from=from;
		this.to=to;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
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
		
}
