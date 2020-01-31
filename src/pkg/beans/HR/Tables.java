package pkg.beans.HR;

public class Tables {
	private String xcdtb;
	private String xcdel;
	private String xdtab;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Tables() {}

	public Tables(String xcdtb, String xcdel, String xdtab, String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.xcdtb=xcdtb;
		this.xcdel=xcdel;
		this.xdtab=xdtab;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getXcdtb() {
		return xcdtb;
	}
	public void setXcdtb(String xcdtb) {
		this.xcdtb = xcdtb;
	}

	public String getXcdel() {
		return xcdel;
	}
	public void setXcdel(String xcdel) {
		this.xcdel = xcdel;
	}
	
	public String getXdtab() {
		return xdtab;
	}
	public void setXdtab(String xdtab) {
		this.xdtab = xdtab;
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
