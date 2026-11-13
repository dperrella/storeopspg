package pkg.utility;
import java.sql.*;

public class TestAddDocumentsDetail {

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
					+ "(sku, iddoc, package_id) "
					+ "values"
					+ "(?, ?, ?) ";
			prstm = conn.prepareStatement(sql);
			rs = st.executeQuery("select ean, pack, qty from teststampa where ean is not null ");
			while(rs.next()) {
				
				for(int i=0;i<rs.getInt("qty");i++) {
					prstm.setString(1, rs.getString("ean"));
					prstm.setInt(2, 85963);
					prstm.setString(3, rs.getString("pack"));
					
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
			      System.out.println("Execution " + i + " "+ 
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
