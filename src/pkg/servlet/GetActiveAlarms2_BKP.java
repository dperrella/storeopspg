package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.Tables2;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetActiveAlarms
 */
@WebServlet("/servlet/ws/GetActiveAlarms2_BKP")
public class GetActiveAlarms2_BKP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetActiveAlarms2_BKP() {
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
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql  =  "select x.id, x.customer, x.store, x.code, l.description, "
					+ "x.threshold, x.timeunit, x.control_time, x.from_time, x.to_time, x.value, "
					+ "case when s.id_trigger is null then true else false end btn,"
					+ "s.fromdate, s.todate, x.id_store, x.alarm_id, x.kpi "
					+ "from vw_active_alarm x "
					+ "left join lang_alarm_report l on alarm_id = id_alarm_report and lang = '"+session.getAttribute("language").toString()+"' "
					+ "left join silenced_trigger s on s.id_store = x.id_store and s.id_trigger = x.alarm_id "
					+ "where x.id_store in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "order by x.from_time desc ";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String $al_id = "";
			String $al_customer = "";
			String $al_store = "";
			String $al_code = "";
			String $al_description = "";
			String $al_threshold = "";
			String $al_timeunit = "";
			String $al_control_time = "";
			String $al_from_time = "";
			String $al_to_time = "";
			String $al_value = "";
			boolean btn = false;
			String button = "";
			String kpi = "";
			while(rs.next()) {
				
				$al_id = rs.getString("id");
				$al_customer = rs.getString("customer");
				$al_store = rs.getString("store");
				$al_code = rs.getString("code");
				$al_description = rs.getString("description");
				$al_threshold = rs.getString("threshold");
				$al_timeunit = rs.getString("timeunit");
				$al_control_time = rs.getString("control_time");
				$al_from_time = rs.getString("from_time");
				$al_to_time = rs.getString("to_time");
				$al_value = df.format(rs.getDouble("value"));
				btn = rs.getBoolean("btn");
				kpi = rs.getString("kpi");
				
				
				if(btn) {
					
					/*
					button = "<div class=\"checkbox i-checks\">\r\n" + 
							"                              <label>\r\n" + 
							"                                <input type=\"checkbox\" name=\"chk\" value=\""+rs.getString("alarm_id")+";"+rs.getString("id_store")+"\"><i></i>\r\n" + 
							"                              </label>\r\n" + 
							"                            </div>";
					*/
					button = "<i class=\"fa fa-circle text-success\"><i>";
				}
				else {
					
					button = "<i class=\"fa fa-power-off text-danger\"><i>";
					
				}
				
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add(button);
				jsonRecord.add($al_customer);
				jsonRecord.add($al_store);
				jsonRecord.add($al_code);
				jsonRecord.add($al_description);
				jsonRecord.add($al_threshold);
				jsonRecord.add($al_timeunit);
				jsonRecord.add($al_control_time);
				jsonRecord.add($al_from_time);
				jsonRecord.add($al_to_time);
				jsonRecord.add($al_value);
				jsonRecord.add("");
				jsonRecord.add($al_id);
				jsonRecord.add(rs.getString("alarm_id"));
				jsonRecord.add(rs.getString("id_store"));
				jsonRecord.add(rs.getString("kpi"));
				jsonRecord.add(sdf.format(rs.getDate("from_time")));
				jsonRecord.add(sdf.format(rs.getDate("to_time")));
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
		doGet(request, response);
	}

}
