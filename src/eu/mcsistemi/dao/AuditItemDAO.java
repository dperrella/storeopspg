package eu.mcsistemi.dao;

import java.sql.Connection;
import java.util.List;

import eu.mcsistemi.model.AuditItem;

public interface AuditItemDAO {
	public List<AuditItem> getAuditItemByAuditId(Connection conn, int auditId);
	public List<AuditItem> getFullAuditItemByAuditId(Connection conn, int auditId);
	public boolean createAuditItems(Connection conn, List<AuditItem> auditItems, int auditId);
	public boolean updateAuditItems(Connection conn, List<AuditItem> auditItems, int auditId);
	public boolean deleteAuditItems(Connection conn, int auditId);
}
