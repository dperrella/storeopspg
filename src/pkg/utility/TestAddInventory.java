package pkg.utility;
import java.sql.*;

public class TestAddInventory {

	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		PreparedStatement prstm = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			conn = Utility.getConnRfid();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			
			sql = "insert into documents_detail "
					+ "(sku, iddoc) "
					+ "values"
					+ "(?, ?) ";
			prstm = conn.prepareStatement(sql);
//			rs = st.executeQuery("select * from inv_20210630 where sku is not null");
//			while(rs.next()) {
//				
//				for(int i=0;i<rs.getInt("units");i++) {
//					prstm.setString(1, rs.getString("sku"));
//					prstm.setInt(2, 116178);
//					prstm.addBatch();
//				}
//			}
//			rs.close();
			
			rs = st.executeQuery("select sku, qty from fiege_stock where sku is not null");
			//rs = st.executeQuery("select sku, qty from invx where sku is not null and doc = 1");
			//rs = st.executeQuery("select sku, qty from invx where sku is not null");
			while(rs.next()) {
				
				for(int i=0;i<rs.getInt("qty");i++) {
					prstm.setString(1, rs.getString("sku"));
					prstm.setInt(2, 534593);
					prstm.addBatch();
				}
			}
			rs.close();
			st.close();
			
			int [] numUpdates=prstm.executeBatch();
			for (int i=0; i < numUpdates.length; i++) {
			    if (numUpdates[i] == -2)
			      System.out.println("Execution " + i + 
			        ": unknown number of rows updated");
			    else
			      System.out.println("Execution " + i + 
			        "successful: " + numUpdates[i] + " rows updated");
			}
			conn.commit();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		finally {
			try {
				prstm.close();
				conn.close();
			}
			catch(Exception e) {}
		}
	}

}
