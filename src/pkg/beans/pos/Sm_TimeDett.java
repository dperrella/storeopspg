package pkg.beans.pos;

public class Sm_TimeDett {
	private String id;
	private String day;
	private String fromh;
	private String toh;	
	
	public Sm_TimeDett() {}

	public Sm_TimeDett(String id, String day, String fromh, String toh) {
		this.id=id;
		this.day=day;
		this.fromh=fromh;
		this.toh=toh;		
	
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

	public String getfromh() {
		return fromh;
	}
	public void setfromh(String fromh) {
		this.fromh = fromh;
	}
	
	public String gettoh() {
		return toh;
	}
	public void settoh(String toh) {
		this.toh = toh;
	}	

}
