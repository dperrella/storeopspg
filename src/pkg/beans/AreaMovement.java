package pkg.beans;

public class AreaMovement {
	private String id_store;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String uscite;
	private String ingressi;
	private String id_area_from;
	private String id_area_to;
	private String areaFrom;
	private String areaTo;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public AreaMovement() {}

	public AreaMovement(String id_store, String year, String month, String day, String hour, 
			String uscite, String ingressi, String id_area_from, String id_area_to, String areaFrom, String areaTo,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_store=id_store;
		this.year=year;
		this.month=month;
		this.day=day;
		this.hour=hour;
		this.uscite=uscite;
		this.ingressi=ingressi;
		this.id_area_from=id_area_from;
		this.id_area_to=id_area_to;
		this.areaFrom=areaFrom;
		this.areaTo=areaTo;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId_store() {
		return id_store;
	}
	public void setId_store(String id_store) {
		this.id_store = id_store;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public String getUscite() {
		return uscite;
	}
	public void setUscite(String uscite) {
		this.uscite = uscite;
	}
	
	public String getIngressi() {
		return ingressi;
	}
	public void setIngressi(String ingressi) {
		this.ingressi = ingressi;
	}
	
	public String getId_area_from() {
		return id_area_from;
	}
	public void setId_area_from(String id_area_from) {
		this.id_area_from = id_area_from;
	}
	
	public String getId_area_to() {
		return id_area_to;
	}
	public void setId_area_to(String id_area_to) {
		this.id_area_to = id_area_to;
	}
	
	public String getAreaFrom() {
		return areaFrom;
	}
	public void setAreaFrom(String areaFrom) {
		this.areaFrom = areaFrom;
	}
	
	public String getAreaTo() {
		return areaTo;
	}
	public void setAreaTo(String areaTo) {
		this.areaTo = areaTo;
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
