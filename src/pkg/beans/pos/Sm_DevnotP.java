package pkg.beans.pos;

public class Sm_DevnotP{
	
	private String id;
	private String dids;
	private String dna;
	private String sit;
	private String sitn;
	private String tims;
	private String usr;
	private String ip;
	private String devt;
	private String maca;
	
	public Sm_DevnotP() {}

	public Sm_DevnotP(String id, String dids, String dna, String sit, String sitn, String tims, String usr, String ip, String devt, String maca) {
		this.id=id;
		this.dids=dids;
		this.dna=dna;
		this.sit=sit;
		this.sitn=sitn;
		this.tims=tims;
		this.usr=usr;
		this.ip=ip;
		this.devt=devt;
		this.maca=maca;		
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getdids() {
		return dids;
	}
	public void setdids(String dids) {
		this.dids = dids;
	}	

	public String getdna() {
		return dna;
	}
	public void setdna(String dna) {
		this.dna = dna;
	}
	
	public String getsit() {
		return sit;
	}
	public void setsit(String sit) {
		this.sit = sit;
	}
	
	public String getsitn() {
		return sitn;
	}
	public void setsitn(String sitn) {
		this.sitn = sitn;
	}
	
	public String gettims() {
		return tims;
	}
	public void settims(String tims) {
		this.tims = tims;
	}
	
	public String getusr() {
		return usr;
	}
	public void setusr(String usr) {
		this.usr = usr;
	}
	
	public String getip() {
		return ip;
	}
	public void setip(String ip) {
		this.ip = ip;
	}	
	
	public String getdevt() {
		return devt;
	}
	public void setdevt(String devt) {
		this.devt = devt;
	}	
	
	public String getmaca() {
		return maca;
	}
	public void setmaca(String maca) {
		this.maca = maca;
	}		
}
