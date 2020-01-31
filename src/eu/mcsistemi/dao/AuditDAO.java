package eu.mcsistemi.dao;

import java.sql.Connection;
import java.util.List;

import eu.mcsistemi.model.Audit;

public interface AuditDAO {
	
	public List<Audit> getAuditList(Connection conn);
	
	public Audit getAuditById(Connection conn, int auditId);
	
	public List<Audit> getAuditsByStoreId(Connection conn, int storeId);
	
	public List<Audit> getFullAuditsByStoreId(Connection conn, int storeId);
	
	public int createAudit(Connection conn, Audit audit)throws Exception;
	
	public boolean updateAudit(Connection conn, Audit audit)throws Exception;
	
	public boolean deleteAudit(Connection conn, Audit audit)throws Exception;

}
