package pkg.beans.pos;

public class Sm_Listupdat{
	
	private String cli;
	private String neg;
	private String ipa;
	private String idd;
	
	public Sm_Listupdat() {}

	public Sm_Listupdat(String cli, String neg, String ipa, String idd) {
		this.cli=cli;
		this.neg=neg;
		this.ipa=ipa;
		this.idd=idd;
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
	
	public String getipa() {
		return ipa;
	}
	public void setipa(String ipa) {
		this.ipa = ipa;
	}
	
	public String getidd() {
		return idd;
	}
	public void setidd(String idd) {
		this.idd = idd;
	}
}
