package pkg.beans;

public class Documents {
	private String id;
	private String docnumber;
	private String doctype;
	private String docdate;
	private String idstep;
	private String origin_warehouse;
	private String destination_warehouse;
	private String idflow;
	private String status;
	private String docref;
	private String id_prec;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	private String cmp5;
	private String cmp6;
	private String cmp7;
	private String cmp8;
	
	
	
	public Documents() {}

	public Documents(String id, String docnumber, String doctype, String docdate, 
			String idstep, String origin_warehouse, String destination_warehouse,
			String idflow, String status, String docref, String id_prec,
			String cmp1, String cmp2, String cmp3, String cmp4,
			String cmp5, String cmp6, String cmp7, String cmp8) {
		
		this.id=id;
		this.docnumber=docnumber;
		this.doctype=doctype;
		this.docdate=docdate;
		this.idstep=idstep;
		this.origin_warehouse=origin_warehouse;
		this.destination_warehouse=destination_warehouse;
		this.idflow=idflow;
		this.status=status;
		this.docref=docref;
		this.id_prec=id_prec;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
		
		this.cmp5=cmp5;
		this.cmp6=cmp6;
		this.cmp7=cmp7;
		this.cmp8=cmp8;
	
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDocnumber() {
		return docnumber;
	}
	public void setDocnumber(String docnumber) {
		this.docnumber = docnumber;
	}
	
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	
	public String getDocdate() {
		return docdate;
	}
	public void setDocdate(String docdate) {
		this.docdate = docdate;
	}
	
	public String getIdstep() {
		return idstep;
	}
	public void setIdstep(String idstep) {
		this.idstep = idstep;
	}
	
	public String getOrigin_warehouse() {
		return origin_warehouse;
	}
	public void setOrigin_warehouse(String origin_warehouse) {
		this.origin_warehouse = origin_warehouse;
	}
	
	public String getDestination_warehouse() {
		return destination_warehouse;
	}
	public void setDestination_warehouse(String destination_warehouse) {
		this.destination_warehouse = destination_warehouse;
	}
	
	public String getIdflow() {
		return idflow;
	}
	public void setIdflow(String idflow) {
		this.idflow = idflow;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDocref() {
		return docref;
	}
	public void setDocref(String docref) {
		this.docref = docref;
	}
	
	public String getId_prec() {
		return id_prec;
	}
	public void setId_prec(String id_prec) {
		this.id_prec = id_prec;
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
	
	public String getCmp5() {
		return cmp5;
	}
	public void setCmp5(String cmp5) {
		this.cmp5 = cmp5;
	}
	
	public String getCmp6() {
		return cmp6;
	}
	public void setCmp6(String cmp6) {
		this.cmp6 = cmp6;
	}
	
	public String getCmp7() {
		return cmp7;
	}
	public void setCmp7(String cmp7) {
		this.cmp7 = cmp7;
	}
	
	public String getCmp8() {
		return cmp8;
	}
	public void setCmp8(String cmp8) {
		this.cmp8 = cmp8;
	}
}
