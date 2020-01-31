package pkg.beans.HR;

public class Users {
	private String idusr;
	private String pwd;
	private String nome;
	private String cogn;
	private String tipo;
	private String foto;
	private String mail;
	private String db;
	private String style;
	private String logo;
	private String sysinf;
	private String language;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Users() {}

	public Users(String idusr, String pwd, String nome, String cogn, String tipo,
			String foto, String mail, String db, String style, String logo, String sysinf, String language,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		this.idusr=idusr;
		this.pwd=pwd;
		this.nome=nome;
		this.cogn=cogn;
		this.tipo=tipo;
		this.foto=foto;
		this.mail=mail;
		this.db=db;
		this.style=style;
		this.logo=logo;
		this.sysinf=sysinf;
		this.language=language;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getIdusr() {
		return idusr;
	}
	public void setidusr(String idusr) {
		this.idusr = idusr;
	}

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCogn() {
		return cogn;
	}
	public void set(String cogn) {
		this.cogn = cogn;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getSysinf() {
		return sysinf;
	}
	public void setSysinf(String sysinf) {
		this.sysinf = sysinf;
	}
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
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
