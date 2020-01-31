package pkg.beans.pos;

public class Sm_Listlogs{
	
	private String diff;
	private String date;
	private String cli;
	private String neg;
	private String code;
	private String idst;
	
	public Sm_Listlogs() {}

	public Sm_Listlogs(String diff, String date, String cli, String neg, String code, String idst) {
		this.diff=diff;
		this.date=date;
		this.cli=cli;
		this.neg=neg;
		this.code=code;
		this.idst=idst;
	}
	
	public String getdiff() {
		return diff;
	}
	public void setdiff(String diff) {
		this.diff = diff;
	}
	
	public String getdate() {
		return date;
	}
	public void setdate(String date) {
		this.date = date;
	}	

	public String getcli() {
		return cli;
	}
	public void setcli(String cli) {
		this.cli = cli;
	}
	
	public String getneg() {
		return neg;
	}
	public void setneg(String neg) {
		this.neg = neg;
	}
	
	public String getcode() {
		return code;
	}
	public void setcode(String code) {
		this.code = code;
	}
	
	public String getidst() {
		return idst;
	}
	public void setidst(String idst) {
		this.idst = idst;
	}
}
