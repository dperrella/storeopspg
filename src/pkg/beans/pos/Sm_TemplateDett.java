package pkg.beans.pos;

public class Sm_TemplateDett {
	private String id;
	private String desc;
	private String shdesc;
	private String fromh;
	private String toh;
	
	public Sm_TemplateDett() {}

	public Sm_TemplateDett(String id, String desc, String shdesc, String fromh, String toh) {
		this.id=id;
		this.desc=desc;
		this.shdesc=shdesc;
		this.fromh=fromh;
		this.toh=toh;		
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getdesc() {
		return desc;
	}
	public void setdesc(String desc) {
		this.desc = desc;
	}	
	
	public String getshdesc() {
		return shdesc;
	}
	public void setshdesc(String shdesc) {
		this.shdesc = shdesc;
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
