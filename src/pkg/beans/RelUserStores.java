package pkg.beans;

public class RelUserStores {
	private String idusr;
	private String id;
	private String description;
	private String brandname;
	private String aut;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public RelUserStores() {}

	public RelUserStores(String idusr, String id, String description, 
			String brandname, String aut,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.idusr=idusr;
		this.id=id;
		this.description=description;
		this.brandname=brandname;
		this.aut=aut;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getIdusr() {
		return idusr;
	}
	public void setIdusr(String idusr) {
		this.idusr = idusr;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
	public String getAut() {
		return aut;
	}
	public void setAut(String aut) {
		this.aut = aut;
	}
	
	public String getCmp1() {
		return cmp1;
	}
	public void setCmp1(String cmp1) {
		this.cmp1 = cmp1;
	}
	
	public String getCmp2() {
		return cmp2;
	}
	public void setCmp2(String cmp2) {
		this.cmp2 = cmp2;
	}
	
	public String getCmp3() {
		return cmp3;
	}
	public void setCmp3(String cmp3) {
		this.cmp3 = cmp3;
	}
	
	public String getCmp4() {
		return cmp4;
	}
	public void setCmp4(String cmp4) {
		this.cmp4 = cmp4;
	}
}
