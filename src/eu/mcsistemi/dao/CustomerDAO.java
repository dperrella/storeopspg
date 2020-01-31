package eu.mcsistemi.dao;

import java.sql.Connection;
import java.util.List;

import eu.mcsistemi.model.Customer;

public interface CustomerDAO {
	public List<Customer> getCustomersByUserId(Connection conn, String userId);
	public List<Customer> getFullCustomersByUserId(Connection conn, String userId);
}
