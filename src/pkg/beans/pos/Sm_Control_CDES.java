package pkg.beans.pos;

public class Sm_Control_CDES {
	
	private String ids;
	private String bon;
	private String lss;
	private String lec;
	private String lsd;
	
	public Sm_Control_CDES() {}

	public Sm_Control_CDES(String ids, String bon,String lss, String lec, String lsd) {
		this.ids=ids;
		this.bon=bon;
		this.lss=lss;
		this.lec=lec;
		this.lsd=lsd;
	}
	
	public String getids() {
		return ids;
	}
	public void setids(String ids) {
		this.ids = ids;
	}
	
	public String getbon() {
		return bon;
	}
	public void setbon(String bon) {
		this.bon = bon;
	}	
	
	public String getlss() {
		return lss;
	}
	public void setlss(String lss) {
		this.lss = lss;
	}	

	public String getlec() {
		return lec;
	}
	public void setlec(String lec) {
		this.lec = lec;
	}
	
	public String getlsd() {
		return lsd;
	}
	public void setlsd(String lsd) {
		this.lsd = lsd;
	}	
		
}
