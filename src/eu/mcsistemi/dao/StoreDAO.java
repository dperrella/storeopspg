package eu.mcsistemi.dao;

import java.sql.Connection;
import java.util.List;

import eu.mcsistemi.model.Audit;
import eu.mcsistemi.model.Store;

public interface StoreDAO {
	
	//public List<Store> getStoreList(Connection conn);
	
	//public Store getStoreById(Connection conn, int storeId);
	
	public List<Store> getStoresByUserAndCustomerId(Connection conn, String userId, int customerId);
	
	public List<Store> getFullStoresByUserAndCustomerId(Connection conn, String userId, int customerId);
	
	//public boolean createStore(Connection conn, Store store);
	
	//public boolean updateStore(Connection conn, Store store);
	
	//public boolean deleteStore(Connection conn, Store store);
}
