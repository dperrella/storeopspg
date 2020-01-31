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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/GetCustomerConfiguration")
public class GetCustomerConfiguration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCustomerConfiguration() {
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
		
		try {
			conn=Utility.getConnAdmin();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			String username = request.getParameter("username");
			System.out.println(username);
			
			sql   = "select c.id, c.account_code, c.description, "
					+ "r.id as ruleid, ip1, ip2, ip3, ip4, range_from, range_to, size, tunneling "
					+ "from customers c "
					+ "join customers_device_rule r on r.idcustomer = c.id "
					+ "where c.id in(select x.idcustomer from rel_user_customers x "
					+ "where idusr = '"+username+"') ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getInt("id"));
				jsonObj.put("accountid", rs.getInt("account_code"));
				jsonObj.put("description", rs.getString("description").trim());
				jsonObj.put("ruleid", rs.getInt("ruleid"));
				jsonObj.put("ip1", rs.getString("ip1").trim());
				jsonObj.put("ip2", rs.getString("ip2").trim());
				jsonObj.put("ip3", rs.getString("ip3").trim());
				jsonObj.put("ip4", rs.getString("ip4").trim());
				jsonObj.put("range_from", rs.getString("range_from").trim());
				jsonObj.put("range_to", rs.getString("range_to").trim());
				jsonObj.put("size", rs.getInt("size"));
				jsonObj.put("tunneling", rs.getBoolean("tunneling"));
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			responseJson.put("listings", jsonArray);
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
