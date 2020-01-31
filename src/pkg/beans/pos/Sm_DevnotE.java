package pkg.beans.pos;

public class Sm_DevnotE{
	
	private String dids;
	private String cus;
	private String sto;
	private String ipa;
	private String devt;
	private String datd;
	private String reas;
	
	public Sm_DevnotE() {}

	public Sm_DevnotE(String dids, String cus, String sto, String ipa, String devt, String datd, String reas) {
		this.dids=dids;
		this.cus=cus;
		this.sto=sto;
		this.ipa=ipa;
		this.devt=devt;
		this.datd=datd;
		this.reas=reas;
	}
		
	public String getdids() {
		return dids;
	}
	public void setdids(String dids) {
		this.dids = dids;
	}	

	public String getcus() {
		return cus;
	}
	public void setcus(String cus) {
		this.cus = cus;
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
	
	public String getdevt() {
		return devt;
	}
	public void setdevt(String devt) {
		this.devt = devt;
	}
	
	public String getdatd() {
		return datd;
	}
	public void setdatd(String datd) {
		this.datd = datd;
	}
	
	public String getreas() {
		return reas;
	}
	public void setreas(String reas) {
		this.reas = reas;
	}	

	
}
