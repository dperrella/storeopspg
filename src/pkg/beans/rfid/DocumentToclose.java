package pkg.beans.rfid;


public class DocumentToclose {

	private Integer iddoc;
	private String epc;
	private String sku;
	
	public DocumentToclose() {}
	
	public DocumentToclose(Integer iddoc, String epc, String sku) {
		this.iddoc=iddoc;
		this.epc=epc;
		this.sku=sku;
	}

	public Integer getIddoc() {
		return iddoc;
	}

	public void setIddoc(Integer iddoc) {
		this.iddoc = iddoc;
	}

	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
}
