package eu.mcsistemi.model;

import java.util.Date;
import java.util.List;

import net.sf.jasperreports.charts.type.EdgeEnum;

public class AuditItem  extends Item{
	private enum AuditResult{found, notFound, note};
	private AuditResult auditResult;
	private String note;
	private String lot;
	private Date expiry;
	


public AuditItem(String barcode,String name, String description, String vendor, String brand, String country,
				  String protectionType,String evolutionReportLink, String auditResult, String note, String lot, Date expiry){
	 super(barcode,name,description, vendor, brand, country,
		   protectionType,evolutionReportLink);
	 if(auditResult != null && !auditResult.equals(""))
			this.auditResult = AuditResult.valueOf(auditResult);
	 this.note = note;
	 this.lot = lot;
	 this.expiry = expiry;
 }

 public AuditItem(String barcode,String name, String description, String vendor, String brand, String country,
		  String protectionType,String evolutionReportLink, List<Photo> photos, String auditResult, String note,String lot, Date expiry){
	super(barcode,name,description, vendor, brand, country,
	  protectionType,evolutionReportLink, photos);
	if(auditResult != null && !auditResult.equals(""))
		this.auditResult = AuditResult.valueOf(auditResult);
	this.note = note;
	this.lot = lot;
	this.expiry = expiry;
 }
 
public String getLot() {
	return lot;
}

public void setLot(String lot) {
	this.lot = lot;
}

public Date getExpiry() {
	return expiry;
}

public void setExpiry(Date expiry) {
	this.expiry = expiry;
}

public String getAuditResult() {
	if(auditResult != null)
		return auditResult.toString();
	return null;
}

public void setAuditResult(String auditResult) {
	this.auditResult = AuditResult.valueOf(auditResult);
}

public String getNote() {
	return note;
}

public void setNote(String note) {
	this.note = note;
}

}
