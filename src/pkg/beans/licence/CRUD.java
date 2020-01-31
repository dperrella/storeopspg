package pkg.beans.licence;

import java.sql.Connection;

public interface CRUD {
	
	void add(Connection conn);
	void update(Connection conn);
	void delete(Connection conn);
	
}
