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
@WebServlet("/rfid/ws/GetDevices")
public class GetDevices extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDevices() {
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
		Statement st2 = null;
		Statement st3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		
		String sql = "";
		
		String idwarehouse = request.getParameter("idwarehouse");
		String username = request.getParameter("username");
		
		try {
			
			conn = Utility.getConnRfid();
			st = conn.createStatement();
			st2 = conn.createStatement();
			st3 = conn.createStatement();
			
			JSONObject responseJson = new JSONObject();
			
			
			
			JSONArray jsonArray = new JSONArray();
			sql   = "select idwarehouse, a.iddevice, name, description, ip, port, mac, "
					+ "devicetype, config_device, serial_num "
					+ "from rel_ware_device a , devices d "
					+ "where a.iddevice = d.iddevice "
					+ "and a.idwarehouse = "+idwarehouse+" ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				JSONObject jsonDevice = new JSONObject();
				jsonDevice.put("idwarehouse", rs.getInt("idwarehouse"));
				jsonDevice.put("iddevice", rs.getString("iddevice"));
				jsonDevice.put("name", rs.getString("name"));
				jsonDevice.put("description", rs.getString("description"));
				jsonDevice.put("ip", rs.getString("ip"));
				jsonDevice.put("port", rs.getString("port"));
				jsonDevice.put("mac", rs.getString("mac"));
				jsonDevice.put("devicetype", rs.getInt("devicetype"));
				
				String config_device = rs.getString("config_device");
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(config_device);
				JSONObject jsonConfig = (JSONObject) _obj;
				
				jsonDevice.put("config", jsonConfig);
				jsonDevice.put("serial_num", rs.getString("serial_num"));
				
				JSONArray jsonArray2 = new JSONArray();
				sql   = "select f.description as flow_description, a.idflow "
						+ "from rel_usr_step a , flow f "
						+ "where idusr = '"+username+"' "
						+ "and a.idflow = f.idflow "
						+ "group by a.idflow, f.description "
						+ "order by a.idflow ";
				System.out.println(sql);
				rs2=st2.executeQuery(sql);
				while(rs2.next()) {
					JSONObject jsonFlow = new JSONObject();
					jsonFlow.put("idflow", rs2.getInt("idflow"));
					jsonFlow.put("description", rs2.getString("flow_description"));
					
					JSONArray jsonArray3 = new JSONArray();
					sql   = "select s.step_type, s.description as step_description, a.* "
							+ "from rel_usr_step a , step s  "
							+ "where idusr = '"+username+"' and a.idflow = "+rs2.getInt("idflow")+" "
							+ "and a.idstep = s.idstep order by s.idstep";
					System.out.println(sql);
					rs3=st3.executeQuery(sql);
					while(rs3.next()) {
						JSONObject jsonStep = new JSONObject();
						jsonStep.put("idstep", rs3.getInt("idstep"));
						jsonStep.put("description", rs3.getString("step_description"));
						jsonStep.put("idsteptype", rs3.getInt("step_type"));
						jsonArray3.add(jsonStep);
					}
					rs3.close();
					jsonFlow.put("step", jsonArray3);
					
					jsonArray2.add(jsonFlow);
				}
				rs2.close();
				
				jsonDevice.put("flow", jsonArray2);
				
				jsonArray.add(jsonDevice);
				
			}
			rs.close();
			responseJson.put("device", jsonArray);
			
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
				if(st2!=null)
					st.close();
				if(st3!=null)
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
