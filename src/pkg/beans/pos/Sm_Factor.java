package pkg.beans.pos;

public class Sm_Factor {
	private String id;
	private String descr;
	private String valper;
	private String valmat;
	private String valser;
	
	public Sm_Factor() {}

	public Sm_Factor(String id, String descr, String valper, String valmat, String valser) {
		this.id=id;
		this.descr=descr;
		this.valper=valper;
		this.valmat=valmat;
		this.valser=valser;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getdescr() {
		return descr;
	}
	public void setdescr(String descr) {
		this.descr = descr;
	}
	
	public String getvalper() {
		return valper;
	}
	public void setvalper(String valper) {
		this.valper = valper;
	}	
	
	public String getvalmat() {
		return valmat;
	}
	public void setvalmat(String valmat) {
		this.valmat = valmat;
	}		

	public String getvalser() {
		return valser;
	}
	public void setvalser(String valser) {
		this.valser = valser;
	}
	
	
}
