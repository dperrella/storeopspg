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

import pkg.beans.Tables;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/GetCustomersCategory2")
public class GetCustomersCategory2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCustomersCategory2() {
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
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		String sql = "";
		
		String idcus = request.getParameter("idcus");
		String category = request.getParameter("category");
		String parent_category = request.getParameter("parent_category");
		
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
			st2=conn.createStatement();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "select category, parent_category, case when value is null then '[]'::json else value end from customers_category "+
					"where id_customer = "+idcus+" and category = '"+category.replaceAll("'", "''")+"' and parent_category = '"+parent_category.replaceAll("'", "''")+"' ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("category").trim());
				jsonObj.put("name", rs.getString("category").trim());
				jsonObj.put("text", rs.getString("category").trim());
				jsonObj.put("value", rs.getObject("value"));
				jsonObj.put("parent_id", rs.getString("parent_category"));
				
				// ---------------------->
				// Legge Region per xcdel 
				JSONArray jsonArray2 = new JSONArray();
				sql   = "select category, parent_category, case when value is null then '[]'::json else value end from customers_category "+
						"where id_customer = "+idcus+" and parent_category = '"+rs.getString("category")+"' ";
				System.out.println(sql);
				rs2=st2.executeQuery(sql);
				while(rs2.next()) {
					
					jsonObj.put("hasChild", true);
					
					JSONObject jsonObj2 = new JSONObject();
					jsonObj2.put("id", rs2.getString("category").trim());
					jsonObj2.put("name", rs2.getString("category").trim());
					jsonObj2.put("text", rs2.getString("category").trim());
					jsonObj2.put("value", rs2.getObject("value"));
					jsonObj2.put("parent_id", rs.getString("category"));
					
					jsonArray2.add(jsonObj2);
				}
				rs2.close();
				// <----------------------
				
				if(jsonArray2.size()>0)
					jsonObj.put("nodes", jsonArray2);
				
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			//responseJson.put("listings", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonArray.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st2!=null)
					st2.close();
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
