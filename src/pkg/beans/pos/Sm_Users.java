package pkg.beans.pos;

public class Sm_Users {
	private String id;
	private String usr;
	private String disp;
	private String lang;
	private String role;
	private String acti;
	
	public Sm_Users() {}

	public Sm_Users(String id, String usr, String disp, String lang, String role, String acti) {
		this.id=id;
		this.usr=usr;
		this.disp=disp;
		this.lang=lang;
		this.role=role;
		this.acti=acti;
	
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getusr() {
		return usr;
	}
	public void setusr(String usr) {
		this.usr = usr;
	}	

	public String getdisp() {
		return disp;
	}
	public void setdisp(String disp) {
		this.disp = disp;
	}
	
	public String getlang() {
		return lang;
	}
	public void setlang(String lang) {
		this.lang = lang;
	}
	
	public String getrole() {
		return role;
	}
	public void setrole(String role) {
		this.role = role;
	}
	
	public String getacti() {
		return acti;
	}
	public void setTipo(String acti) {
		this.acti = acti;
	}

}
