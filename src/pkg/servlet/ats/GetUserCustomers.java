package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/GetUserCustomers")
public class GetUserCustomers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserCustomers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			
			conn=Utility.getConnData();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			String username = request.getParameter("username");
			System.out.println(username);
			
			sql   = "select id, account_code, description, "
					+ "case when (select idcustomer "
					+ "from pref_user_customers where idusr = '"+username.toUpperCase()+"' and idcustomer = id) "
					+ "is null then 0 else 1 end checked "
					+ "from customers "
					+ "where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+username.toUpperCase()+"') "
					+ "and id in(select idcustomer from ats.rel_item_customer) ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			
			rs=st.executeQuery();
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getInt("id"));
				jsonObj.put("accountid", rs.getInt("account_code"));
				jsonObj.put("description", rs.getString("description"));
				jsonObj.put("checked", rs.getInt("checked"));
				jsonArray.add(jsonObj);
			}
			rs.close();
			st.close();
			
			responseJson.put("customers", jsonArray);
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
		doGet(request,response);
	}

}
