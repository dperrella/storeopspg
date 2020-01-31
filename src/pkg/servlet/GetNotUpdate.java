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
 * Servlet implementation class GetNotUpdate
 */
@WebServlet("/servlet/ws/GetNotUpdate")
public class GetNotUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNotUpdate() {
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
			
			sql   = "select v_pcounters_not_update_suite.*,stores.store_code from v_pcounters_not_update_suite "
					+ "left join stores on v_pcounters_not_update_suite.id_store = stores.id "
					+ "where stores.id in(select idstore::integer from rel_user_stores where idusr = '"+session.getAttribute("username").toString().trim().toUpperCase()+"') ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				/*
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id_store", rs.getString("id_store"));
				jsonObj.put("customer_description", rs.getString("customer_description"));
				jsonObj.put("description", rs.getString("description"));
				jsonObj.put("lasttime", rs.getString("lasttime"));
				jsonObj.put("customer_country_id", rs.getString("customer_country_id"));
				jsonObj.put("store_code", rs.getString("store_code"));
				jsonArray.add(jsonObj);
				*/
				JSONArray jsonRecord = new JSONArray();
				//jsonRecord.add(rs.getString("id_store"));
				jsonRecord.add(rs.getString("accountid"));
				jsonRecord.add(rs.getString("storeid"));
				jsonRecord.add(rs.getString("doorid"));
				jsonRecord.add(rs.getString("customer_description"));
				jsonRecord.add(rs.getString("description"));
				jsonRecord.add(rs.getString("lasttime"));
				
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
