package pkg.beans;

public class Kpi {
	private String xcdtb;
	private String xcdel;
	private String xdtab;
	private String ata32;
	private String xdesc;
	private boolean calculated;
	private String color;
	private String kpi_symbol;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	private String cmp5;
	private String cmp6;
	private String cmp7;
	private String cmp8;
	
	
	
	public Kpi() {}

	public Kpi(String xcdtb, String xcdel, String xdtab,
			String ata32, String xdesc, boolean calculated, String color, String kpi_symbol,
			String cmp1, String cmp2, String cmp3, String cmp4,
			String cmp5, String cmp6, String cmp7, String cmp8) {
		
		this.xcdtb=xcdtb;
		this.xcdel=xcdel;
		this.xdtab=xdtab;
		this.ata32=ata32;
		this.xdesc=xdesc;
		this.calculated=calculated;
		this.color=color;
		this.kpi_symbol=kpi_symbol;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
		this.cmp5=cmp5;
		this.cmp6=cmp6;
		this.cmp7=cmp7;
		this.cmp8=cmp8;
	
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
	
	public String getAta32() {
		return ata32;
	}
	public void setAta32(String ata32) {
		this.ata32 = ata32;
	}
	
	public String getXdesc() {
		return xdesc;
	}
	public void setXdesc(String xdesc) {
		this.xdesc = xdesc;
	}
	
	public boolean getCalculated() {
		return calculated;
	}
	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getKpi_symbol() {
		return kpi_symbol;
	}
	public void setKpi_symbol(String kpi_symbol) {
		this.kpi_symbol = kpi_symbol;
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
	
	public String getCmp5() {
		return cmp5;
	}
	public void setCmp5(String cmp5) {
		this.cmp5 = cmp5;
	}
	
	public String getCmp6() {
		return cmp6;
	}
	public void setCmp6(String cmp6) {
		this.cmp6 = cmp6;
	}
	
	public String getCmp7() {
		return cmp7;
	}
	public void setCmp7(String cmp7) {
		this.cmp7 = cmp7;
	}
	
	public String getCmp8() {
		return cmp8;
	}
	public void setCmp8(String cmp8) {
		this.cmp8 = cmp8;
	}
}
