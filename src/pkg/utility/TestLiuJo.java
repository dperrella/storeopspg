package pkg.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestLiuJo implements Runnable{

	private static final String apickp_url = "https://apickp.azurewebsites.net/liujo/";
    private static final String apickp_username = "liujo";
    private static final String apickp_password = "yX6exW96E7$F";
    
    
    
    private static final String rfid_url = "https://liujo.smartrfid.it/smartrfidbe/";
    private static final String rfid_username = "apiliujo@smartrfid";
    private static final String rfid_password = "Sft33.z89";
    
    
    private static final String codify_url = "https://web.codi-fy.com/codifybe/";
    private static final String codify_username = "dperrella@mcsistemi.net";
    private static final String codify_password = "Matti160214!";
    
    
    
    
	public static void main(String[] args) throws SQLException {
	
		start();
	}


	@Override
	public void run() {
		
		start();
		
	}
	
	public static void start() {
		
		Connection conn = null;
		try {
//			String jsonParm = "{\n"
//					+ "	\"username\": \""+apickp_username+"\",\n"
//					+ "	\"password\": \""+apickp_password+"\"\n"
//					+ "}";
//			
//			JSONObject obj = Utility.consumeRest(apickp_url+"api/auth/signin", "POST", jsonParm, "");
//			String token = obj.getString("accessToken");
//			
//			System.out.println(token);
//			
//			conn = Utility.getConnRfid();
//			PreparedStatement st = null;
//			ResultSet rs = null;
//			
//			String sql = "select \n"
//					+ "  distinct purchase_order_number \n"
//					+ "from \n"
//					+ "  liujo.als_json \n"
//					+ "where \n"
//					+ "  checknet = 'X' \n"
//					+ "  and export_checknet_flag is null";
//			st=conn.prepareStatement(sql);
//			rs=st.executeQuery();
//			while(rs.next()) {
//				try {
//					System.out.println(rs.getString("purchase_order_number")+"...");
//					obj = Utility.consumeRest(apickp_url+"api/v1/Als/"+rs.getString("purchase_order_number").trim(), "GET", "", token);
//					System.out.println(obj.getString("code")+" "+obj.getString("message"));
//				}
//				catch(Exception err) {
//					System.err.println(err.getMessage());
//				}
//			}
//			rs.close();
//			st.close();
			
			// --------------
			
			///
			conn = Utility.getConnRfid();
			String jsonParm = "";
			String token = "";
			String sql = "";
			PreparedStatement st = null;
			ResultSet rs = null;
			JSONObject obj = null;
			///
			
			jsonParm = "{\n"
					+ "	\"email\": \""+rfid_username+"\",\n"
					+ "	\"password\": \""+rfid_password+"\"\n"
					+ "}";
			
			obj = Utility.consumeRest(rfid_url+"api/auth/signin", "POST", jsonParm, "");
			token = obj.getString("accessToken");
			
			
			jsonParm = "{\n"
					+ "	\"email\": \""+codify_username+"\",\n"
					+ "	\"password\": \""+codify_password+"\"\n"
					+ "}";
			
			obj = Utility.consumeRest(codify_url+"api/auth/signin", "POST", jsonParm, "");
			String codifyToken = obj.getString("accessToken");
				
			sql = "select iddoc from smartrfid2_export where exported = false";
			st=conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("iddoc"));
				//exportTask(conn, token, rs.getInt("iddoc"));
				exportTaskToCodify(conn, codifyToken,rs.getInt("iddoc"));
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
	}
	
	private static void exportTask(Connection conn, String token, Integer iddoc) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			JSONObject obj = new JSONObject();
			
			sql = "select * from documents\n"
				+ "join sites on sites.id = idsite\n"
				+ "where documents.id = ?";
			st=conn.prepareStatement(sql);
			st.setInt(1, iddoc);
			System.out.println(st.toString());
			rs=st.executeQuery();
			boolean exists = false;
			if(rs.next()) {
				exists = true;
				// write task
				obj.put("docRef", rs.getString("doc_ref"));
				obj.put("originRef", ""+iddoc);
				obj.put("filterType", "2");
				obj.put("departmentCode", rs.getString("warehouse_code"));
				obj.put("documentTypeCode", "PRT");
				obj.put("operation", "replace");
			}
			rs.close();
			st.close();
			
			if(exists) {
			
				sql = "select * from documents_detail where iddoc = ?";
				st=conn.prepareStatement(sql);
				st.setInt(1, iddoc);
				rs=st.executeQuery();
				JSONArray expList = new JSONArray();
				while(rs.next()) {
					// write task_details
					JSONObject exp = new JSONObject();
					exp.put("barcode", rs.getString("sku"));
					expList.put(exp);
				}
				obj.put("expected", expList);
				
				System.out.println(obj.toString());
				
				Utility.consumeRest(rfid_url+"ext/v1/document", "POST", obj.toString(), token);
				
			
			}
			
			sql = "update smartrfid2_export set exported = true where iddoc = ? ";
			st=conn.prepareStatement(sql);
			st.setInt(1, iddoc);
			st.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private static final String customerId = "240657b6-063d-4859-837e-830eeeccf128";
	private static final String materialId = "10e6ffda-a255-4a56-9013-a2cbca032aa7";
	private static final String templateId = "b1b209e7-38c7-4c29-a345-1f89053fd280";
	private static final String serviceBureauId = "b092fad6-67cd-461a-9c36-e966ff2e0fca";
	
	
	private static void exportTaskToCodify(Connection conn, String codifyToken, Integer iddoc) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			sql = "select \n"
					+ "products.sku,5 \"partition\",1 \"filter\" ,count(0) quantity,\n"
					+ "itemcode,products.sku,name,size,colordesc,art2,documents.doc_ref,documents.id\n"
					+ "from documents \n"
					+ "join documents_detail on documents.id = documents_detail.iddoc\n"
					+ "join products on documents_detail.sku = products.sku\n"
					+ "where idsite = 275 and documents.id = "+iddoc+" "
					+ "group by documents.doc_ref,products.sku,itemcode,products.sku,name,size,colordesc,art2, documents.id";
			st=conn.prepareStatement(sql);
			System.out.println(st.toString());
			rs=st.executeQuery();
			boolean first =true;
			
			JSONObject order = new JSONObject();
			order.put("customerId", customerId);
			order.put("materialId", materialId);
			order.put("templateId", templateId);
			
			JSONArray items = new JSONArray();
			while(rs.next()) {
				if(first) {
					order.put("orderNo", rs.getString("doc_ref"));
					order.put("orderRef", rs.getString("id"));
					first=false;
				}
				// Write items
				JSONObject item = new JSONObject();
				item.put("ean", rs.getString("sku"));
				item.put("qty", rs.getInt("quantity"));
				item.put("filterValue", rs.getInt("filter"));
				item.put("companyPrefixLength", rs.getInt("partition"));
				
				
				JSONArray attributes = new JSONArray();
				JSONObject attribute = new JSONObject();
				attribute.put("key","itemcode");
				attribute.put("value", rs.getString("itemcode"));
				attributes.put(attribute);
				attribute = new JSONObject();
				attribute.put("key","sku");
				attribute.put("value", rs.getString("sku"));
				attributes.put(attribute);
				attribute = new JSONObject();
				attribute.put("key","name");
				attribute.put("value", rs.getString("name"));
				attributes.put(attribute);
				attribute = new JSONObject();
				attribute.put("key","size");
				attribute.put("value", rs.getString("size"));
				attributes.put(attribute);
				attribute = new JSONObject();
				attribute.put("key","colordesc");
				attribute.put("value", rs.getString("colordesc"));
				attributes.put(attribute);
				attribute = new JSONObject();
				attribute.put("key","art2");
				attribute.put("value", rs.getString("art2"));
				attributes.put(attribute);
				item.put("attributes", attributes);
				items.put(item);
			}
			rs.close();
			st.close();
			
			order.put("items", items);
			
			System.out.println(order);
			
			Utility.consumeRest(codify_url+"api/v1/orderComplete", "POST", order.toString(), codifyToken);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}

}
