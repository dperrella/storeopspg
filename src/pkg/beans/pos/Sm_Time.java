package pkg.beans.pos;

public class Sm_Time {
	private String id;
	private String desc;
	private String templ;
	
	public Sm_Time() {}

	public Sm_Time(String id, String desc, String templ) {
		this.id=id;
		this.desc=desc;
		this.templ=templ;
	
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

	public String gettempl() {
		return templ;
	}
	public void settempl(String templ) {
		this.templ = templ;
	}
	

}
