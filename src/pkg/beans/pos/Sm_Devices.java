package pkg.beans.pos;

public class Sm_Devices {
	
	private String id;
	private String dn;
	private String dids;
	private String cdn;
	private String ids;
	
	public Sm_Devices() {}

	public Sm_Devices(String id, String dn, String dids, String cdn, String ids) {
		this.id=id;
		this.dn=dn;
		this.dids=dids;
		this.cdn=cdn;
		this.ids=ids;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getdn() {
		return dn;
	}
	public void setdn(String dn) {
		this.dn = dn;
	}	

	public String getdids() {
		return dids;
	}
	public void setdids(String dids) {
		this.dids = dids;
	}
	
	public String getcdn() {
		return cdn;
	}
	public void setcdn(String cdn) {
		this.cdn = cdn;
	}
	
	public String getids() {
		return ids;
	}
	public void setids(String ids) {
		this.ids = ids;
	}
	
}
