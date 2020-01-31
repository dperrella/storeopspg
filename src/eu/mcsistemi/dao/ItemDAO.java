package eu.mcsistemi.dao;

import java.sql.Connection;
import java.util.List;

import eu.mcsistemi.model.Item;

public interface ItemDAO {
	
	//public List<Item> getItemList(Connection conn);
	
	public Item getFullItemById(Connection conn, String barcode);
	
	public List<Item> getItemsByUserId(Connection conn, String userId);
	
	public List<Item> getFullItemsByUserId(Connection conn, String userId);
	
	public List<Item> getItemsByStoreId(Connection conn, int storeId);
	
	public List<String> getBarcodesByStoreId(Connection conn, int storeId);
	
	public int checkExistItem(Connection conn, String barcode)throws Exception;
	
	public int checkUserItem(Connection conn, String barcode)throws Exception;
	
	public boolean createItem(Connection conn, Item item, String userId, int storeId)throws Exception;
	
	public boolean createItemUserConnection(Connection conn, String userId, String barcode)throws Exception;
	
	public boolean createItemStoreConnection(Connection conn, int storeId, String barcode)throws Exception;
	
	public boolean updateItem(Connection conn, Item item)throws Exception;
	
	public boolean deleteItemReference(Connection conn, String barcode, String userId)throws Exception;
	
	public int itemsCountByCustomer(Connection conn, int customerId)throws Exception;
	
	public List<Item> getItemsByCustomerId(Connection conn, int customerId);
}
