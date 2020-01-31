package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.ccn.Devices;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetProvince
 */
@WebServlet("/servlet/ws/GetDevices")
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
		String idStore = request.getParameter("idStore");
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			conn=Utility.getConnAdmin();
			
			List <Devices> dev = new ArrayList<Devices>();
			//dev = Devices.getDevicesByIdStore(conn, Integer.parseInt(idStore));
			dev = Devices.getDevicesByIdStoreAndre(conn, Integer.parseInt(idStore));
			
			for(int i=0;i<dev.size();i++) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("idDevice", dev.get(i).getIdDevice());
				jsonObj.put("inError", dev.get(i).isInError());
				jsonObj.put("deviceName", dev.get(i).getDeviceName());
				jsonObj.put("accountId", dev.get(i).getAccountId());
				jsonObj.put("storeId", dev.get(i).getStoreId());
				jsonObj.put("doorId", dev.get(i).getDoorId());
				jsonObj.put("ip", dev.get(i).getIp());
				jsonObj.put("port", dev.get(i).getPort());
				jsonObj.put("deviceType", dev.get(i).getDeviceType());
				jsonObj.put("devTypeDescription", dev.get(i).getDevTypeDescription());
				jsonObj.put("status", dev.get(i).getStatus());
				jsonObj.put("stodesc", dev.get(i).getStodesc());
				jsonObj.put("types", dev.get(i).getTypes());
				jsonObj.put("color", dev.get(i).getColor());
				
				String since = ""+dev.get(i).getStatusSince();
				since = Utility.noNull(since);
				if(since.length()==0)
					since = "Unknown";
				else {
					String temp[] = since.split(":");
					try {
						since = temp[0]+":"+temp[1];
					}
					catch(Exception e) {}
				}
				
				
				
				
				jsonObj.put("statusSince", since);
				jsonObj.put("idStore", dev.get(i).getIdStore());
				
				jsonArray.add(jsonObj);
			}
			
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
		responseJson.put("listings", jsonArray);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
