package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/EASPushService")
public class EASPushService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EASPushService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		String sql = "";
		
		String accountid = request.getParameter("accountid");
		String storeid = request.getParameter("storeid");
		String deviceid = request.getParameter("deviceid");
		String val = request.getParameter("val");
		
		
		System.out.println(accountid);
		System.out.println(storeid);
		System.out.println(deviceid);
		System.out.println(val);
		
		JSONObject responseJson = new JSONObject();
		String message = "KO";
		
		try {
			conn=Utility.getConnDispatcher();
			
			JSONParser parser = new JSONParser();
			Object _obj = parser.parse(val);
		
			JSONObject jsonObject = (JSONObject) _obj;
			
			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("whiteList");
			Iterator<String> iterator = msg.iterator();
			
			sql = "insert into eas.whitelist(accountid, storeid, deviceid, epc, ts, type, cpid, antenna, sku) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			st = conn.prepareStatement(sql);
			
			while (iterator.hasNext()) {
			    
				Object obj = iterator.next();
				
				JSONObject jsonObject2 = (JSONObject) obj;
			    //System.out.println(jsonObject2);
			    
			    String epc = (String) jsonObject2.get("epc");
			    String timestamp = (String) jsonObject2.get("timestamp");
			    String type = (String) jsonObject2.get("type");
			    String cpid = (String) jsonObject2.get("cpid");
			    String antenna = (String) jsonObject2.get("antenna");
			    
			    String sku = "";
			    try {
			    	sku = (String) jsonObject2.get("sku");
			    }
			    catch(Exception e) {}
			    
			    
			    st.setString(1, accountid);
			    st.setString(2, storeid);
			    st.setString(3, deviceid);
			    st.setString(4, epc);
			    st.setString(5, timestamp);
			    st.setString(6, type);
			    st.setString(7, cpid);
			    st.setString(8, antenna);
			    st.setString(9, sku);
			    st.addBatch();
			    
			}
			st.executeBatch();
			st.close();
			
			// loop array
			JSONArray msg2 = (JSONArray) jsonObject.get("events");
			Iterator<String> iterator2 = msg2.iterator();
			
			sql = "insert into eas.events(accountid, storeid, deviceid, epc, ts, response, sku) values (?, ?, ?, ?, ?, ?, ?) ";
			st = conn.prepareStatement(sql);
			
			while (iterator2.hasNext()) {
			    
				Object obj = iterator2.next();
				
				JSONObject jsonObject2 = (JSONObject) obj;
			    //System.out.println(jsonObject2);
			    
			    String resp = (String) jsonObject2.get("response");
			    String epc = (String) jsonObject2.get("epc");
			    String timestamp = (String) jsonObject2.get("timestamp");
			    
			    String sku = "";
			    try {
			    	sku = (String) jsonObject2.get("sku");
			    }
			    catch(Exception e) {}
			    
			    
			    st.setString(1, accountid);
			    st.setString(2, storeid);
			    st.setString(3, deviceid);
			    st.setString(4, epc);
			    st.setString(5, timestamp);
			    st.setString(6, resp);
			    st.setString(7, sku);
			    
			    st.addBatch();
			    
			}
			st.executeBatch();
			st.close();
			
			message = "OK";
			
		}
		catch(Exception e) {
			e.printStackTrace();
			message = "KO";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		
		responseJson.put("message", message);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
		
		
	}

}
