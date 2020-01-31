package pkg.beans.HR;

public class PlanificationType {
	private String id;
	private String description;
	private String min_time;
	private String max_time;
	private String direction;
	private String approvation;
	private String color;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public PlanificationType() {}

	public PlanificationType(String id, String description, String min_time, String max_time, 
			String direction, String approvation, String color, String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id=id;
		this.description=description;
		this.min_time=min_time;
		this.max_time=max_time;
		this.direction=direction;
		this.approvation=approvation;
		this.color=color;
		
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMin_time() {
		return min_time;
	}
	public void setMin_time(String min_time) {
		this.min_time = min_time;
	}
	
	public String getMax_time() {
		return max_time;
	}
	public void setMax_time(String max_time) {
		this.max_time = max_time;
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getApprovation() {
		return approvation;
	}
	public void setApprovation(String approvation) {
		this.approvation = approvation;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
