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
 * Servlet implementation class GetNotEnabled
 */
@WebServlet("/servlet/ws/GetNotEnabled")
public class GetNotEnabled extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNotEnabled() {
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
			
			sql   = "select * from v_dnotenabled_2 ";
			
			sql   = "select a.* , d.id_store from v_dnotenabled_2 a, devices d "
					+ "where a.id = d.id "
					+ "and id_store in(select idstore::integer "
					+ "from rel_user_stores where idusr = '"+session.getAttribute("username").toString().trim().toUpperCase()+"') ";
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				/*
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("id"));
				jsonObj.put("customer_descr", rs.getString("customer_descr"));
				jsonObj.put("brand_id", rs.getString("brand_id"));
				jsonObj.put("customer_country_id", rs.getString("customer_country_id"));
				jsonObj.put("store_descr", rs.getString("store_descr"));
				jsonObj.put("device_ip", rs.getString("device_ip"));
				jsonObj.put("device_descr", rs.getString("device_descr"));
				jsonObj.put("tstamp", rs.getString("tstamp"));
				jsonObj.put("reason", rs.getString("reason"));
				jsonArray.add(jsonObj);
				*/
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add(rs.getString("id"));
				jsonRecord.add(rs.getString("customer_descr"));
				jsonRecord.add(rs.getString("id_store"));
				jsonRecord.add(rs.getString("store_descr"));
				jsonRecord.add(rs.getString("device_ip"));
				jsonRecord.add(rs.getString("device_descr"));
				jsonRecord.add(rs.getString("tstamp"));
				jsonRecord.add(rs.getString("reason"));
				jsonRecord.add("");
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
