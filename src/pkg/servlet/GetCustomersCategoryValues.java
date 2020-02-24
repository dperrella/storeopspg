package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Tables;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/GetCustomersCategoryValues")
public class GetCustomersCategoryValues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCustomersCategoryValues() {
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
		
		String idcus = request.getParameter("idcus");
		String category = request.getParameter("category");
		
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
			
			JSONObject responseJson = new JSONObject();
			
			sql   = "select id_customer, category, parent_category, required, "
					+ "case when value is null then '[]'::json else value end from customers_category "
					+ "where id_customer = "+idcus+" and category = '"+category.replaceAll("'", "''")+"' ";
			rs = st.executeQuery(sql);
			if(rs.next()) {
				responseJson.put("id_customer", rs.getString("id_customer"));
				responseJson.put("category", rs.getString("category"));
				responseJson.put("parent_category", rs.getString("parent_category"));
				responseJson.put("required", rs.getString("required"));
				
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(rs.getString("value"));
				responseJson.put("value", jsonArray);
				
			}
			rs.close();
			
			//responseJson.put("listings", jsonArray);
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
