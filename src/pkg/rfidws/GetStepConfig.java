package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_config;
import pkg.beans.Rfid_devices;
import pkg.beans.Rfid_step;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/GetStepConfig")
public class GetStepConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStepConfig() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String idflow = request.getParameter("idflow");
		String idstep = request.getParameter("idstep");
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			
			Rfid_step step = new Rfid_step();
			step.setIdflow(Integer.parseInt(idflow));
			step.setIdstep(Integer.parseInt(idstep));
			step.selectRow(conn);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("idflow", step.getIdflow());
			jsonObj.put("idstep", step.getIdstep());
			jsonObj.put("levelnum", step.getLevelnum());
			jsonObj.put("description", step.getDescription());
			
			// origin / destination
			String origin_location = step.getOrigin_location();
			if(origin_location!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(origin_location);
				JSONObject jsonObject = (JSONObject) _obj;
				JSONArray msg = (JSONArray) jsonObject.get("origin_location_type");
				jsonObj.put("origin_location_type", msg);
				
				Iterator<String> iterator = msg.iterator();
				String id = "";
				while (iterator.hasNext()) {
					
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    id+= ""+jsonObject2.get("id")+",";
				    
				}
				if(id.length()>0)
					id=id.substring(0,id.length()-1);
				
				jsonObj.put("origin_warehouse", getWarehouse(conn,id));
				
			}
			
			String destination_location = step.getDestination_location();
			if(destination_location!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(destination_location);
				JSONObject jsonObject = (JSONObject) _obj;
				JSONArray msg = (JSONArray) jsonObject.get("destination_location_type");
				jsonObj.put("destination_location_type", msg);
				
				Iterator<String> iterator = msg.iterator();
				String id = "";
				while (iterator.hasNext()) {
					
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    id+= ""+ jsonObject2.get("id")+",";
				    
				}
				if(id.length()>0)
					id=id.substring(0,id.length()-1);
				
				jsonObj.put("destination_warehouse", getWarehouse(conn,id));
			    
			}
			
			
			// --------
			
			jsonObj.put("enabled", step.getEnabled());
			
			// Extra_doc_top / Extra_doc_detail
			String extra_doc_top = step.getExtra_doc_top();
			if(extra_doc_top!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(extra_doc_top);		
				JSONObject jsonObject = (JSONObject) _obj;
				JSONArray ext = (JSONArray) jsonObject.get("extra_doc_top");
				jsonObj.put("extra_doc_top", ext);
			    
			}
			
			String extra_doc_detail = step.getExtra_doc_detail();
			if(extra_doc_detail!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(extra_doc_detail);		
				JSONObject jsonObject = (JSONObject) _obj;
				JSONArray ext = (JSONArray) jsonObject.get("extra_doc_detail");
				jsonObj.put("extra_doc_detail", ext);
			    
			}
			
			// --------
			
			// Config before
			String config_before = step.getConfig_before();
			if(config_before!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(config_before);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("config");
				Iterator<String> iterator = msg.iterator();
				
				JSONObject cfg_bef = new JSONObject();
				
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    String name = (String) jsonObject2.get("name");
				    String type = (String) jsonObject2.get("type");
				    
				    
				    if(type.equalsIgnoreCase("Text"))
				    	cfg_bef.put(name, (String) jsonObject2.get("value"));
				    else
				    	cfg_bef.put(name, jsonObject2.get("value"));
				    
				}
				jsonObj.put("config_before", cfg_bef);
			    
			}
			
			
			// Config during
			String config_during = step.getConfig_during();
			if(config_during!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(config_during);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("config");
				Iterator<String> iterator = msg.iterator();
				
				JSONObject cfg_bef = new JSONObject();
				
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    String name = (String) jsonObject2.get("name");
				    String type = (String) jsonObject2.get("type");
				    
				    
				    if(type.equalsIgnoreCase("Text"))
				    	cfg_bef.put(name, (String) jsonObject2.get("value"));
				    else
				    	cfg_bef.put(name, jsonObject2.get("value"));
				    
				}
				jsonObj.put("config_during", cfg_bef);
			    
			}
			
			// Config after
			String config_after = step.getConfig_after();
			if(config_after!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(config_after);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("config");
				Iterator<String> iterator = msg.iterator();
				
				JSONObject cfg_bef = new JSONObject();
				
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    String name = (String) jsonObject2.get("name");
				    String type = (String) jsonObject2.get("type");
				    
				    
				    if(type.equalsIgnoreCase("Text"))
				    	cfg_bef.put(name, (String) jsonObject2.get("value"));
				    else
				    	cfg_bef.put(name, jsonObject2.get("value"));
				    
				}
				jsonObj.put("config_after", cfg_bef);
			    
			}
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObj.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}
	
	private static String getWarehouse(Connection conn, String id_loctype) {
		
		String warehouse = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "select l.id_warehouse, w.description from location l, warehouse w "
					+ "where l.id_warehouse = w.idwarehouse "
					+ "and id_loctype in( "+id_loctype+" ) "
					+ "group by l.id_warehouse, w.description ";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id_warehouse", rs.getInt("id_warehouse"));
				jsonObj.put("description", rs.getString("description"));
				jsonArray.add(jsonObj);
				
			}
			rs.close();
			
			responseJson.put("listings", jsonArray);
			warehouse = responseJson.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		return warehouse;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
