package pkg.utility;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SainsburysImport {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		importa();
	}
	
	private static void importa() throws SQLException {
		
		Connection conn = null;
		Statement st = null;
		Statement up = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			conn=Utility.getConnControl();
			conn.setAutoCommit(false);

			st=conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
			up=conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
			
			sql   = "select ipaddr, 8081 as port, " + 
					"4573 as accountid, 'Sainsburys'  as customer_Description, " + 
					"store::integer as storeid, s.description as store_description, " + 
					"'GMT' as timezone,2 as device_type, 60 as readevery, " + 
					"s.id as id_store, true as updating," + 
					"1464732000 as fromdate, 'DSM' as devicename, " + 
					"999 as doorid, router " + 
					"from sainsburys_import, stores s " + 
					"where store_code = store";
			rs=st.executeQuery(sql);
			
			
			while(rs.next()) {
				
				String $id_device = Utility.getNewProgr(conn, "devices", "id", "");
				int $id_store = rs.getInt("id_store");
				String $ip = rs.getString("ipaddr");
				String $port = "8081";
				String $readevery = "60";
				String $fromdate = "1464732000";
				String $storeid = rs.getString("storeid");
				
				// Insert devices
				sql   = "insert into devices ( "
						+ "id, id_store, ip, port, readevery, fromdate, enabled, accountid, storeid, timezone, "
						+ "device_type, minute_not_update, updating, doorid, notes, log, lin, lout, logenabled, devicename, macaddr, line_antenna1, line_antenna2, cause  "
						+ ", userid, pwd) "
						+ "values ( "
						+ $id_device+" , "+$id_store+" , '"+Utility.noApex($ip)+"' , '"+$port+"' , "
						+ $readevery+" , "+$fromdate+" , true , '4573' , "
						+ "'"+$storeid+"' , 'GMT' , 2 , 1440 , "
						+ "true , '999' , '' , false, 0 , 0 , false,'DSM', '', null, null, '', "
						+ "'', '')"; 
				System.out.println(sql);
				up.executeUpdate(sql);
				
				// Insert router
				sql = "INSERT INTO routers(" + 
						"            routers_type, customer, customer_description, account_id, " + 
						"            store_id, store_description,  " + 
						"            ip_address_router, sim, router_code, router_serial_number, imsi_number, " + 
						"            master_antenna, slave_antenna, notes, closes, date_closes) " + 
						"    VALUES ('d8fa2f50-84e0-4b92-bda1-eeb2eb15069a', '4573', 'Sainsburys', '173', " + 
						"            '"+$storeid+"', '"+rs.getString("store_description").replaceAll("'", "''")+"', " + 
						"            '"+$ip+"', '', '"+Utility.noApex(rs.getString("router"))+"', '"+Utility.noApex(rs.getString("router"))+"', '', " + 
						"            '', '', '', false, null);";
				// Prepara il cursore
				System.out.println(sql);
				up.executeUpdate(sql);
				
			}
			rs.close();
			
			
			conn.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		finally {
			try {
				if(up!=null)
					up.close();
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
	}
	

}
