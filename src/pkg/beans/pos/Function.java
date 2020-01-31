package pkg.beans.pos;

public class Function {
	private String cdfun;
	private String descr;
	private String tipo;
	private String percorso;
	private String icon;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Function() {}

	public Function(String cdfun, String descr, String tipo, String percorso, String icon,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		this.cdfun=cdfun;
		this.descr=descr;
		this.tipo=tipo;
		this.percorso=percorso;
		this.icon=icon;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getCdfun() {
		return cdfun;
	}
	public void setCdfun(String cdfun) {
		this.cdfun = cdfun;
	}

	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getPercorso() {
		return percorso;
	}
	public void setPercorso(String percorso) {
		this.percorso = percorso;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
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
