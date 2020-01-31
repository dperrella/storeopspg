package pkg.beans.pos;

public class Sm_Control_Devices {
	
	private String id;
	private String ida;
	private String dn;
	private String dids;
	private String cdn;
	private String ids;
	private String ids_;
	
	public Sm_Control_Devices() {}

	public Sm_Control_Devices(String id, String ida,String dn, String dids, String cdn, String ids, String ids_) {
		this.id=id;
		this.ida=ida;
		this.dn=dn;
		this.dids=dids;
		this.cdn=cdn;
		this.ids=ids;
		this.ids_=ids_;
	}
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	
	public String getida() {
		return ida;
	}
	public void setida(String ida) {
		this.ida = ida;
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
	
	public String getids_() {
		return ids_;
	}
	public void setids_(String ids_) {
		this.ids_ = ids_;
	}
	
}
