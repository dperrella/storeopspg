package pkg.beans;

public class Products {
	private String codpro;
	private String ds1pro;
	private String edipro;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Products() {}

	public Products(String codpro, String ds1pro, String edipro, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.codpro=codpro;
		this.ds1pro=ds1pro;
		this.edipro=edipro;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getCodpro() {
		return codpro;
	}
	public void setCodpro(String codpro) {
		this.codpro = codpro;
	}

	public String getDs1pro() {
		return ds1pro;
	}
	public void setDs1pro(String ds1pro) {
		this.ds1pro = ds1pro;
	}
	
	public String getEdipro() {
		return edipro;
	}
	public void setEdipro(String edipro) {
		this.edipro = edipro;
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
