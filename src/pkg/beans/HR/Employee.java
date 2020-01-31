package pkg.beans.HR;

public class Employee {
	private String id;
	private String name;
	private String surname;
	private String address;
	private String locality;
	private String province;
	private String zipcode;
	private String birthdate;
	private String sex;
	private String birthlocality;
	private String birthprovince;
	private String state;
	private String fcode;
	private String phone;
	private String email;
	private String id_customer;
	private String matricola;
	private String disabled;
	private String max_departments;
	
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Employee() {}

	public Employee(String id, String name, String surname, String address, String locality, String province, String zipcode,
			String birthdate, String sex, String birthlocality, String birthprovince, String state, String fcode, String phone, String email,
			String id_customer, String matricola, String disabled, String max_departments,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		this.id=id;
		this.name=name;
		this.surname=surname;
		this.address=address;
		this.locality=locality;
		this.province=province;
		this.zipcode=zipcode;
		this.birthdate=birthdate;
		this.sex=sex;
		this.birthlocality=birthlocality;
		this.birthprovince=birthprovince;
		this.state=state;
		this.fcode=fcode;
		this.phone=phone;
		this.email=email;
		this.id_customer=id_customer;
		this.matricola=matricola;
		this.disabled=disabled;
		this.max_departments=max_departments;
				
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getBirthlocality() {
		return birthlocality;
	}
	public void setBirthlocality(String birthlocality) {
		this.birthlocality = birthlocality;
	}
	
	public String getBirthprovincey() {
		return birthprovince;
	}
	public void setBirthprovince(String birthprovince) {
		this.birthprovince = birthprovince;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getFcode() {
		return fcode;
	}
	public void setFcode(String fcode) {
		this.fcode = fcode;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getId_customer() {
		return id_customer;
	}
	public void setId_customer(String id_customer) {
		this.id_customer = id_customer;
	}
	
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	public String getMax_departments() {
		return max_departments;
	}
	public void setMax_departments(String max_departments) {
		this.max_departments = max_departments;
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
