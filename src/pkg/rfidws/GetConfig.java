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
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/GetConfig")
public class GetConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetConfig() {
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
		Statement st2= null;
		ResultSet rs = null;
		ResultSet rs2= null;
		String sql = "";
		
		String mac = ""+request.getParameter("mac");
		mac = Utility.noNull(mac);
		String iddevice = ""+request.getParameter("iddevice");
		iddevice = Utility.noNull(iddevice);
		
		
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			st2=conn.createStatement();
			
			Rfid_devices dev = new Rfid_devices();
			
			if(mac.length()>0) {
				dev.setMac(mac);
				dev.selectRowFromMac(conn);
			}
			else {
				dev.setIddevice(Integer.parseInt(iddevice));
				dev.selectRow(conn);
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("iddevice", dev.getIddevice());
			jsonObj.put("name", dev.getName());
			jsonObj.put("description", dev.getDescription());
			jsonObj.put("ip", dev.getIp());
			jsonObj.put("port", dev.getPort());
			jsonObj.put("devicetype", dev.getDevicetype());
			jsonObj.put("enabled", dev.getEnabled());
			jsonObj.put("grouped", dev.getGrouped());
			jsonObj.put("program_name", dev.getProgram_name());
			
			String config = dev.getConfig_device();
			
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
				    	jsonObj.put(name, (String) jsonObject2.get("value"));
				    else
				    	jsonObj.put(name, jsonObject2.get("value"));
				    
				}
			}
			
			// Se il device esiste
			if(dev.getIddevice()>0) {
				
				JSONArray jsonArray = new JSONArray();
				sql   = "select f.description as flow_description, a.* "
						+ "from rel_dev_step a , flow f "
						+ "where id_device = "+dev.getIddevice()+" "
						+ "and a.idflow = f.idflow order by f.idflow";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					JSONObject jsonFlow = new JSONObject();
					jsonFlow.put("idflow", rs.getInt("idflow"));
					jsonFlow.put("description", rs.getString("flow_description"));
					
					JSONArray jsonArray2 = new JSONArray();
					sql   = "select s.description as step_description, a.* "
							+ "from rel_dev_step a , step s "
							+ "where id_device = "+dev.getIddevice()+" and a.idflow = "+rs.getInt("idflow")+" "
							+ "and a.idstep = s.idstep order by s.idstep";
					rs2=st2.executeQuery(sql);
					while(rs2.next()) {
						JSONObject jsonStep = new JSONObject();
						jsonStep.put("idstep", rs2.getInt("idstep"));
						jsonStep.put("description", rs2.getString("step_description"));
						jsonArray2.add(jsonStep);
					}
					rs2.close();
					jsonFlow.put("steps", jsonArray2);
					
					jsonArray.add(jsonFlow);
				}
				rs.close();
				jsonObj.put("flow", jsonArray);
				
				
				
				
				
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
				if(st2!=null)
					st2.close();
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
