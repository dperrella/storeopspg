package pkg.beans.pos;

public class Sm_ListSto{
	
	private String ids;
	private String sto;
	private String boa;
	private String cus;

	
	public Sm_ListSto() {}

	public Sm_ListSto(String ids, String sto, String boa, String cus) {
		this.ids=ids;
		this.sto=sto;		
		this.boa=boa;
		this.cus=cus;
	}
	
	public String getids() {
		return ids;
	}
	public void setids(String ids) {
		this.ids = ids;
	}
	
	public String getsto() {
		return sto;
	}
	public void setsto(String sto) {
		this.sto = sto;
	}
	
	public String getboa() {
		return boa;
	}
	public void setboa(String boa) {
		this.boa = boa;
	}	
	
	public String getcus() {
		return cus;
	}
	public void setcus(String cus) {
		this.cus = cus;
	}
	
}
