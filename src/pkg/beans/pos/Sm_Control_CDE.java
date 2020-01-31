package pkg.beans.pos;

public class Sm_Control_CDE {
	
	private String id;
	private String typ;
	private String ip;
	private String upd;
	private String ena;
	
	public Sm_Control_CDE() {}

	public Sm_Control_CDE(String id, String typ, String ip,String upd, String ena) {
		this.id=id;
		this.typ=typ;
		this.ip=ip;
		this.upd=upd;
		this.ena=ena;
	}

	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String gettyp() {
		return typ;
	}
	public void settyp(String typ) {
		this.typ = typ;
	}
	
	public String getip() {
		return ip;
	}
	public void setip(String ip) {
		this.ip = ip;
	}	
	
	public String getupd() {
		return upd;
	}
	public void setupd(String upd) {
		this.upd = upd;
	}	

	public String getena() {
		return ena;
	}
	public void setena(String ena) {
		this.ena = ena;
	}
		
}
