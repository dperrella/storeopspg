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
 * Servlet implementation class GetNotPaired
 */
@WebServlet("/servlet/ws/GetNotPaired")
public class GetNotPaired extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNotPaired() {
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
		String $np = "";
		String $pre = "";
		
		Connection connAdmin = null;
		Statement stAdmin = null;
		
		HttpSession session = request.getSession();
		
		
		try {
			conn=Utility.getConnControl();
			st=conn.createStatement();
			
			/*
			// Connessione a smartretail
			connAdmin=Utility.getConnAdmin();
			stAdmin=connAdmin.createStatement();
			
			sql  =  "select id from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			rs=
			*/
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			//sql   = "select * from v_dnotpaired2 ";
			
			sql   = "select * from v_dnotpaired2 where \n" + 
					"userstr in(select account_code::text from customers where \n" + 
					"id in(select idcustomer from rel_user_customers \n" + 
					"where idusr = '"+(String)session.getAttribute("username")+"')) " + 
					"or userstr not in(select account_code::text from customers) ";
			System.out.println(sql);
			
			rs=st.executeQuery(sql);
			while(rs.next()) {
				/*
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("id"));
				jsonObj.put("deviceid", rs.getString("deviceid"));
				jsonObj.put("devicename", rs.getString("devicename"));
				jsonObj.put("siteid", rs.getString("siteid"));
				jsonObj.put("sitename", rs.getString("sitename"));
				jsonObj.put("tstamp", rs.getString("tstamp"));
				jsonObj.put("userstr", rs.getString("userstr"));
				jsonObj.put("ip", rs.getString("ip"));
				jsonObj.put("device_type", rs.getString("device_type"));
				jsonObj.put("macaddr", rs.getString("macaddr"));
				jsonArray.add(jsonObj);
				*/
				
				JSONArray jsonRecord = new JSONArray();

				jsonRecord.add(rs.getString("userstr"));
				jsonRecord.add(rs.getString("siteid"));
				jsonRecord.add(Utility.noNull(rs.getString("sitename")));
				jsonRecord.add(rs.getString("deviceid"));
				jsonRecord.add(Utility.noNull(rs.getString("devicename")));
				jsonRecord.add(rs.getString("tstamp"));
				jsonRecord.add(rs.getString("ip"));
				
				if (rs.getString("np").equals("t") || rs.getString("np").equals(true)) {
					$np = "<i class='fa fa-check' style='color: green;'></i>";
				}else {
					$np = "<i class='fa fa-ban'  style='color: red;'></i>";
				}
				
				jsonRecord.add($np);

				if (rs.getString("pre").equals("t") || rs.getString("pre").equals(true)) {
					$pre = "<i class='fa fa-check' style='color: green;'></i>";
				}else {
					$pre = "<i class='fa fa-ban'  style='color: red;'></i>";
				}
				
				
				jsonRecord.add($pre);
				jsonRecord.add(rs.getString("storename"));
				jsonRecord.add(rs.getString("storenotes"));
				jsonRecord.add("");
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
				/*
				if(stAdmin!=null)
					stAdmin.close();
				if(connAdmin!=null)
					connAdmin.close();
				*/
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
