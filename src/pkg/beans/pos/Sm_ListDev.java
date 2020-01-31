package pkg.beans.pos;

public class Sm_ListDev{
	
	private String ids;
	private String brn;
	private String sto;
	private String ipa;
	private String ena;
	private String upd;
	private String dev;
	
	public Sm_ListDev() {}

	public Sm_ListDev(String ids, String brn, String sto, String ipa, String ena, String upd, String dev) {
		this.ids=ids;
		this.brn=brn;
		this.sto=sto;
		this.ipa=ipa;
		this.ena=ena;
		this.upd=upd;
		this.dev=dev;
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

	public String getsto() {
		return sto;
	}
	public void setsto(String sto) {
		this.sto = sto;
	}
	
	public String getipa() {
		return ipa;
	}
	public void setipa(String ipa) {
		this.ipa = ipa;
	}
	
	public String getena() {
		return ena;
	}
	public void setena(String ena) {
		this.ena = ena;
	}
	
	public String getupd() {
		return upd;
	}
	public void setupd(String upd) {
		this.upd = upd;
	}
	
	public String getdev() {
		return dev;
	}
	public void setdev(String dev) {
		this.dev = dev;
	}	
}
