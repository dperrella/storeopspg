package pkg.beans.pos;

public class Sm_Peoplnu{
	
	private String ids;
	private String cus;
	private String devn;
	private String lastt;
	private String lastt2;
	
	public Sm_Peoplnu() {}

	public Sm_Peoplnu(String ids, String cus, String devn, String lastt, String lastt2) {
		this.ids=ids;
		this.cus=cus;
		this.devn=devn;
		this.lastt=lastt;
		this.lastt2=lastt2;
	}
	
	public String getids() {
		return ids;
	}
	public void setids(String ids) {
		this.ids = ids;
	}
	
	public String getcus() {
		return cus;
	}
	public void setcus(String cus) {
		this.cus = cus;
	}
	
	public String getdevn() {
		return devn;
	}
	public void setdevn(String devn) {
		this.devn = devn;
	}
	
	public String getlastt() {
		return lastt;
	}
	public void setlastt(String lastt) {
		this.lastt = lastt;
	}
	
	public String getlastt2() {
		return lastt2;
	}
	public void setlastt2(String lastt2) {
		this.lastt = lastt2;
	}
}
