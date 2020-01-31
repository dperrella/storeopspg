package pkg.rfidws;

import java.io.IOException;
import java.security.MessageDigest;
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
@WebServlet("/rfid/ws/GetConfigNew")
public class GetConfigNew extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetConfigNew() {
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
		
		String iddevice = request.getParameter("iddevice");
		String idstep = request.getParameter("idstep");
		
		try {
			
			conn = Utility.getConnRfid();
			st = conn.createStatement();
			
			JSONObject responseJson = new JSONObject();
			
			sql   = "select d.devicetype, s.step_type, r.config, t.id_interface_type, i.description, "
					+ "d.config_device, s.description step_descr, s.config_during "
					+ "from rel_devicetype_step r , devices d, step s, device_type t, interface_type i "
					+ "where d.devicetype=r.id_devicetype and d.devicetype = t.iddevicetype "
					+ "and r.id_step = s.idstep and t.id_interface_type = i.id_inteface "
					+ "and d.iddevice = "+iddevice+" and idstep = "+idstep;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				responseJson.put("config_exist", true);
				responseJson.put("devicetype", rs.getInt("devicetype"));
				responseJson.put("steptype", rs.getInt("step_type"));
				responseJson.put("interfacetype", rs.getInt("id_interface_type"));
				responseJson.put("interfacetype_descr", rs.getString("description"));
				responseJson.put("step_descr", rs.getString("step_descr"));
				
				
				String config = rs.getString("config_device");
				
				JSONObject responseJson2 = new JSONObject();
				
				if(config!=null) {
					
					JSONParser parser = new JSONParser();
					Object _obj = parser.parse(config);
			
					JSONObject jsonObject = (JSONObject) _obj;
					System.out.println(jsonObject);
			
					// loop array
					JSONArray msg = (JSONArray) jsonObject.get("config");
					Iterator<String> iterator = msg.iterator();
					while (iterator.hasNext()) {
					    
						Object obj2 = iterator.next();
					    JSONObject jsonObject2 = (JSONObject) obj2;
					    
					    String name = (String) jsonObject2.get("name");
					    String type = (String) jsonObject2.get("type");
					    
					    if(type.equalsIgnoreCase("Text"))
					    	responseJson2.put(name, (String) jsonObject2.get("value"));
					    else
					    	responseJson2.put(name, jsonObject2.get("value"));
					    
					}
				}
				responseJson.put("config_device", responseJson2);
				
				
				
				
				config = rs.getString("config");
				
				responseJson2 = new JSONObject();
				
				if(config!=null) {
					
					JSONParser parser = new JSONParser();
					Object _obj = parser.parse(config);
			
					JSONObject jsonObject = (JSONObject) _obj;
					System.out.println(jsonObject);
			
					// loop array
					JSONArray msg = (JSONArray) jsonObject.get("config");
					Iterator<String> iterator = msg.iterator();
					while (iterator.hasNext()) {
					    
						Object obj2 = iterator.next();
					    JSONObject jsonObject2 = (JSONObject) obj2;
					    
					    String name = (String) jsonObject2.get("name");
					    String type = (String) jsonObject2.get("type");
					    
					    if(type.equalsIgnoreCase("Text"))
					    	responseJson2.put(name, (String) jsonObject2.get("value"));
					    else
					    	responseJson2.put(name, jsonObject2.get("value"));
					    
					}
				}
				responseJson.put("config", responseJson2);
				
				config = rs.getString("config_during");
				
				responseJson2 = new JSONObject();
				
				if(config!=null) {
					
					JSONParser parser = new JSONParser();
					Object _obj = parser.parse(config);
			
					JSONObject jsonObject = (JSONObject) _obj;
					System.out.println(jsonObject);
			
					// loop array
					JSONArray msg = (JSONArray) jsonObject.get("config");
					Iterator<String> iterator = msg.iterator();
					while (iterator.hasNext()) {
					    
						Object obj2 = iterator.next();
					    JSONObject jsonObject2 = (JSONObject) obj2;
					    
					    String name = (String) jsonObject2.get("name");
					    String type = (String) jsonObject2.get("type");
					    
					    if(type.equalsIgnoreCase("Text"))
					    	responseJson2.put(name, (String) jsonObject2.get("value"));
					    else
					    	responseJson2.put(name, jsonObject2.get("value"));
					    
					}
				}
				responseJson.put("config_step", responseJson2);
				
				
			}
			else {
				responseJson.put("config_exist", false);
				
				responseJson.put("devicetype", 0);
				responseJson.put("steptype", 0);
				responseJson.put("interfacetype", 0);
				responseJson.put("interfacetype_descr", "");
				responseJson.put("step_descr", "");
				
				
			}
			rs.close();
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
