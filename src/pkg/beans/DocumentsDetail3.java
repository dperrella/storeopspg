package pkg.beans;

public class DocumentsDetail3 {
	private String id_documents;
	private String idflow;
	private String idstep;
	private String epc;
	private String qty_theoretical;
	private String qty_phisycal;
	private String id_loadunits;
	private String id_package;
	private String id_pallet;
	private String status;
	private String barcode;
	
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	private String pkg;
	private String css;
	private String indoc;
	
	
	
	public DocumentsDetail3() {}

	public DocumentsDetail3(String id_documents, String idflow, String idstep, String epc,
			String qty_theoretical, String qty_phisycal, String id_loadunits,
			String id_package, String id_pallet,String status,String barcode,
			String cmp1, String cmp2, String cmp3, String cmp4, String pkg, String css, String indoc) {
		
		this.id_documents=id_documents;
		this.idflow=idflow;
		this.idstep=idstep;
		this.epc=epc;
		this.qty_theoretical=qty_theoretical;
		this.qty_phisycal=qty_phisycal;
		this.id_loadunits=id_loadunits;
		this.id_package=id_package;
		this.id_pallet=id_pallet;
		this.status=status;
		this.barcode=barcode;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
		this.pkg=pkg;
		this.css=css;
		this.indoc=indoc;
		
	}
	
	public String getId_documents() {
		return id_documents;
	}
	public void setId_documents(String id_documents) {
		this.id_documents = id_documents;
	}

	public String getIdflow() {
		return idflow;
	}
	public void setIdflow(String idflow) {
		this.idflow = idflow;
	}
	
	public String getIdstep() {
		return idstep;
	}
	public void setIdstep(String idstep) {
		this.idstep = idstep;
	}
	
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	public String getQty_theoretical() {
		return qty_theoretical;
	}
	public void setQty_theoretical(String qty_theoretical) {
		this.qty_theoretical = qty_theoretical;
	}
	
	public String getQty_phisycal() {
		return qty_phisycal;
	}
	public void setQty_phisycal(String qty_phisycal) {
		this.qty_phisycal = qty_phisycal;
	}
	
	public String getId_loadunits() {
		return id_loadunits;
	}
	public void setId_loadunits(String id_loadunits) {
		this.id_loadunits = id_loadunits;
	}
	
	public String getId_package() {
		return id_package;
	}
	public void setId_package(String id_package) {
		this.id_package = id_package;
	}
	
	public String getId_pallet() {
		return id_pallet;
	}
	public void setId_pallet(String id_pallet) {
		this.id_pallet = id_pallet;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	
	public String getpkg() {
		return pkg;
	}
	public void setpkg(String pkg) {
		this.pkg = pkg;
	}
	
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	
	public String getIndoc() {
		return indoc;
	}
	public void setIndoc(String indoc) {
		this.indoc = indoc;
	}
	
}
