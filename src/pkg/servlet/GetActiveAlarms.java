package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetActiveAlarms
 */
@WebServlet("/servlet/ws/GetActiveAlarms")
public class GetActiveAlarms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetActiveAlarms() {
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
		
		HttpSession session = request.getSession();
		
		try {
			conn=Utility.getConnControl();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "select vw_active_alarm.id,vw_active_alarm.devicename,vw_active_alarm.store,"
					+ "vw_active_alarm.code,vw_active_alarm.description,vw_active_alarm.threshold,"
					+ "vw_active_alarm.timeunit,vw_active_alarm.control_time,vw_active_alarm.from_time,"
					+ "vw_active_alarm.to_time,vw_active_alarm.value,vw_active_alarm.alarm_id,"
					+ "vw_active_alarm.store_code,vw_active_alarm.accountid,vw_active_alarm.doorid "
					+ "from vw_active_alarm, devices d "
					+ "where vw_active_alarm.id = d.id "
					+ "and id_store in(select idstore::integer from rel_user_stores where idusr = '"+session.getAttribute("username").toString().trim().toUpperCase()+"') ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				/*
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("id"));
				jsonObj.put("devicename", rs.getString("devicename"));
				jsonObj.put("store", rs.getString("store"));
				jsonObj.put("code", rs.getString("code"));
				jsonObj.put("description", rs.getString("description"));
				jsonObj.put("threshold", rs.getString("threshold"));
				jsonObj.put("timeunit", rs.getString("timeunit"));
				jsonObj.put("control_time", rs.getString("control_time"));
				jsonObj.put("from_time", rs.getString("from_time"));
				jsonObj.put("to_time", rs.getString("to_time"));
				jsonObj.put("value", rs.getString("value"));
				jsonObj.put("alarm_id", rs.getString("alarm_id"));
				jsonObj.put("store_code", rs.getString("store_code"));
				jsonObj.put("accountid", rs.getString("accountid"));
				jsonObj.put("doorid", rs.getString("doorid"));
				jsonArray.add(jsonObj);
				*/
				JSONArray jsonRecord = new JSONArray();
				//jsonRecord.add(rs.getString("id"));
				jsonRecord.add(rs.getString("devicename"));
				jsonRecord.add(rs.getString("store"));
				jsonRecord.add(rs.getString("code"));
				jsonRecord.add(rs.getString("description"));
				jsonRecord.add(rs.getString("threshold"));
				jsonRecord.add(rs.getString("timeunit"));
				jsonRecord.add(rs.getString("control_time"));
				jsonRecord.add(rs.getString("from_time"));
				jsonRecord.add(rs.getString("to_time"));
				jsonRecord.add(rs.getString("value"));
				jsonRecord.add(rs.getString("store_code"));
				jsonRecord.add(rs.getString("accountid"));
				jsonRecord.add(rs.getString("doorid"));
				jsonRecord.add("");
				jsonRecord.add(rs.getString("alarm_id"));
				jsonArray.add(jsonRecord);
			}
			rs.close();
			
			responseJson.put("data", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
