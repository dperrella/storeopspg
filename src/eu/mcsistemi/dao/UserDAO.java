package eu.mcsistemi.dao;


import java.sql.Connection;
import java.util.List;

import eu.mcsistemi.model.User;

public interface UserDAO {
		
		//public List<User> getUsersList(Connection conn);
		
		//public User getUserById(Connection conn, String userId);
		public User getFullUserById(Connection conn, String userId, String password)throws Exception;
		
		//public boolean createUser(Connection conn, User user);
		
		//public boolean updateUser(Connection conn, User user);
		
		//public boolean deleteUser(Connection conn, User user);

}

