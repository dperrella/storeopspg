package pkg.beans.pos;

public class Sm_Control_CD {
	
	private String sel;
	private String day;

	
	public Sm_Control_CD() {}

	public Sm_Control_CD(String sel, String day) {
		this.sel=sel;
		this.day=day;

	}
	
	public String getsel() {
		return sel;
	}
	public void setsel(String sel) {
		this.sel = sel;
	}
	
	public String getday() {
		return day;
	}
	public void setday(String day) {
		this.day = day;
	}	
	
}
