package pkg.beans.pos;

public class Sm_Template {
	private String id;
	private String desc;
	private String templ;
	
	public Sm_Template() {}

	public Sm_Template(String id, String desc) {
		this.id=id;
		this.desc=desc;
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

}
