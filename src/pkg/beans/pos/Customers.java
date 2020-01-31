package pkg.beans.pos;

public class Customers {
	
	private String companyname;
	private String description;
	private String contact;
	private String brandname;
	private String brandlogo;
	private String account_code;
	private String id;
	private String phone;
	private String mail;
	private String mobile;
	private String id_optempl;
	private String customer_country_id;
	private String agreement;
	private String wskey;
	private String wsdecrypt_key;
	private String wscrypt;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Customers() {}

	public Customers(String companyname, String description, String contact, String brandname, String brandlogo,
			String account_code, String id, String phone, String mail, String mobile, String id_optempl,
			String customer_country_id, String agreement, String wskey, String wsdecrypt_key, String wscrypt,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.companyname=companyname;
		this.description=description;
		this.contact=contact;
		this.brandname=brandname;
		this.brandlogo=brandlogo;
		this.account_code=account_code;
		this.id=id;
		this.phone=phone;
		this.mail=mail;
		this.mobile=mobile;
		this.id_optempl=id_optempl;
		this.customer_country_id=customer_country_id;
		this.agreement=agreement;
		this.wskey=wskey;
		this.wsdecrypt_key=wsdecrypt_key;
		this.wscrypt=wscrypt;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
	public String getBrandlogo() {
		return brandlogo;
	}
	public void setBrandlogo(String brandlogo) {
		this.brandlogo = brandlogo;
	}
	
	public String getAccount_code() {
		return account_code;
	}
	public void setAccount_code(String account_code) {
		this.account_code = account_code;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getId_optempl() {
		return id_optempl;
	}
	public void setId_optempl(String id_optempl) {
		this.id_optempl = id_optempl;
	}
	
	public String getCustomer_country_id() {
		return customer_country_id;
	}
	public void setCustomer_country_id(String customer_country_id) {
		this.customer_country_id = customer_country_id;
	}
	
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	
	public String getWskey() {
		return wskey;
	}
	public void setWskey(String wskey) {
		this.wskey = wskey;
	}
	
	public String getWsdecrypt_key() {
		return wsdecrypt_key;
	}
	public void setWsdecrypt_key(String wsdecrypt_key) {
		this.wsdecrypt_key = wsdecrypt_key;
	}
	
	public String getWscrypt() {
		return wscrypt;
	}
	public void setWscrypt(String wscrypt) {
		this.wscrypt = wscrypt;
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
