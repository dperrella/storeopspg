package pkg.beans;

public class Report {
	private String id;
	private String code;
	private String description;
	private String id_report_type;
	private String ds_report_type;
	private String id_subreport_type;
	private String ds_subreport_type;
	private String alarm1;
	private String alarm2;
	private String historic;
	private String benchmark;
	private String icon;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public Report() {}

	public Report(String id, String code, String description, 
			String id_report_type, String ds_report_type, 
			String id_subreport_type, String ds_subreport_type,
			String alarm1, String alarm2, String historic, 
			String benchmark, String icon,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.code=code;
		this.description=description;
		this.id_report_type=id_report_type;
		this.ds_report_type=ds_report_type;
		this.id_subreport_type=id_subreport_type;
		this.ds_subreport_type=ds_subreport_type;
		this.alarm1=alarm1;
		this.alarm2=alarm2;
		this.historic=historic;
		this.benchmark=benchmark;
		this.icon=icon;
		
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

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getId_report_type() {
		return id_report_type;
	}
	public void setId_report_type(String id_report_type) {
		this.id_report_type = id_report_type;
	}
	
	public String getDs_report_type() {
		return ds_report_type;
	}
	public void setDs_report_type(String ds_report_type) {
		this.ds_report_type = ds_report_type;
	}
	
	public String getId_subreport_type() {
		return id_subreport_type;
	}
	public void setId_subreport_type(String id_subreport_type) {
		this.id_subreport_type = id_subreport_type;
	}
	
	public String getDs_subreport_type() {
		return ds_subreport_type;
	}
	public void setDs_subreport_type(String ds_subreport_type) {
		this.ds_subreport_type = ds_subreport_type;
	}
	
	public String getAlarm1() {
		return alarm1;
	}
	public void setAlarm1(String alarm1) {
		this.alarm1 = alarm1;
	}
	
	public String getAlarm2() {
		return alarm2;
	}
	public void setAlarm2(String alarm2) {
		this.alarm2 = alarm2;
	}
	
	public String getHistoric() {
		return historic;
	}
	public void setHistoric(String historic) {
		this.historic = historic;
	}
	
	public String getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
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
