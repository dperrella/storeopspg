package eu.mcsistemi.model;

import java.util.Date;
import java.util.List;

public class Audit {
	
	private int auditId;
	private String name;
	private enum Type{ST,Alpha,InStore};
	private Type type;
	private Date creationDate;
	private Date closingDate;
	private String state;
	private int storeId;
	private List<AuditItem> auditItems;
	
	
	public Audit(int auditId, String name, String type, Date creationDate, Date closingDate, String state, int storeId,
			List<AuditItem> auditItems) {
		this.auditId = auditId;
		this.name = name;
		this.type = Type.valueOf(type);
		this.creationDate = creationDate;
		this.closingDate = closingDate;
		this.state = state;
		this.storeId = storeId;
		this.auditItems = auditItems;
	}


	public String getType() {
		return type.toString();
	}


	public void setType(String type) {
		this.type = Type.valueOf(type);
	}


	public int getAuditId() {
		return auditId;
	}


	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getClosingDate() {
		return closingDate;
	}


	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public int getStoreId() {
		return storeId;
	}


	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}


	public List<AuditItem> getAuditItems() {
		return auditItems;
	}


	public void setAuditItem(List<AuditItem> auditItems) {
		this.auditItems = auditItems;
	}


	public Audit(int auditId, String name, String type,Date creationDate, Date closingDate, String state, int storeId) {
		this.auditId = auditId;
		this.name = name;
		this.type = Type.valueOf(type);
		this.creationDate = creationDate;
		this.closingDate = closingDate;
		this.state = state;
		this.storeId = storeId;
	}


	public Audit(String name, String type, Date creationDate, Date closingDate, String state, int storeId) {
		this.name = name;
		this.type = Type.valueOf(type);
		this.creationDate = creationDate;
		this.closingDate = closingDate;
		this.state = state;
		this.storeId = storeId;
	}
	
	public Audit(String name, String type, Date creationDate, String state, int storeId, List<AuditItem> auditItems) {
		this.name = name;
		this.type = Type.valueOf(type);
		this.creationDate = creationDate;
		this.state = state;
		this.storeId = storeId;
		this.auditItems = auditItems;
	}
	
}
