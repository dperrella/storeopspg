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
 * Servlet implementation class GetUpdating
 */
@WebServlet("/servlet/ws/GetUpdating2")
public class GetUpdating2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUpdating2() {
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
			
			sql   = "select * from v_dupdating_2 ";
			
			sql   = "select a.customer, a.brand_id, a.customer_country_id, a.id_store, a.store, "
					+ "a.ip_address, d.accountid, d.storeid, d.doorid, a.attempts, updating_from, status  "
					+ "from v_dupdating_3 a, devices d "
					+ "where id_device = d.id "
					+ "and a.id_store in(select idstore::integer from rel_user_stores where idusr = '"+session.getAttribute("username").toString().trim().toUpperCase()+"') ";
			System.out.println(sql);
			
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				/*
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("customer", rs.getString("customer"));
				jsonObj.put("brand_id", rs.getString("brand_id"));
				jsonObj.put("customer_country_id", rs.getString("customer_country_id"));
				jsonObj.put("store", rs.getString("store"));
				jsonObj.put("ip_address", rs.getString("ip_address"));
				jsonObj.put("id_device", rs.getString("id_device"));
				jsonArray.add(jsonObj);
				*/
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add(rs.getString("customer"));
				jsonRecord.add(rs.getString("store"));
				jsonRecord.add(rs.getString("ip_address"));
				jsonRecord.add(rs.getString("attempts"));
				jsonRecord.add(rs.getString("updating_from"));
				jsonRecord.add(rs.getString("status"));
				jsonRecord.add(rs.getString("accountid"));
				jsonRecord.add(rs.getString("storeid"));
				jsonRecord.add(rs.getString("doorid"));
				
				jsonRecord.add("");
				jsonRecord.add(rs.getString("id_store"));
				
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
