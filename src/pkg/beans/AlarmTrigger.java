package pkg.beans;

public class AlarmTrigger {
	private String id;
	private String code;
	private String description;
	private String threshold;
	private String timeunit;
	private String alarm1;
	private String alarm2;
	private String adaptive;
	private String lastrun;
	private String controlfrequency;
	private String sign;
	private String hour_from;
	private String hour_to;
	private String formula;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public AlarmTrigger() {}

	public AlarmTrigger(String id, String code, String description, 
			String threshold, String timeunit, 
			String alarm1, String alarm2, String adaptive, 
			String lastrun, String controlfrequency, String sign,
			String hour_from, String hour_to, String formula,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.code=code;
		this.description=description;
		this.threshold=threshold;
		this.timeunit=timeunit;
		this.alarm1=alarm1;
		this.alarm2=alarm2;
		this.adaptive=adaptive;
		this.lastrun=lastrun;
		this.controlfrequency=controlfrequency;
		this.sign=sign;
		this.hour_from=hour_from;
		this.hour_to=hour_to;
		this.formula=formula;
		
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
	
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	
	public String getTimeunit() {
		return timeunit;
	}
	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
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
	
	public String getAdaptive() {
		return adaptive;
	}
	public void setAdaptive(String adaptive) {
		this.adaptive = adaptive;
	}
	
	public String getLastrun() {
		return lastrun;
	}
	public void setLastrun(String lastrun) {
		this.lastrun = lastrun;
	}
	
	public String getControlfrequency() {
		return controlfrequency;
	}
	public void setControlfrequency(String controlfrequency) {
		this.controlfrequency = controlfrequency;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getHour_from() {
		return hour_from;
	}
	public void setHour_from(String hour_from) {
		this.hour_from = hour_from;
	}
	
	public String getHour_to() {
		return hour_to;
	}
	public void setHour_to(String hour_to) {
		this.hour_to = hour_to;
	}
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
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
