package pkg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

import java.sql.*;
/**
 * Servlet implementation class GetProductDetail
 */
@WebServlet("/servlet/ws/GetSeason")
public class RFIDGetSeason extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RFIDGetSeason() {
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
		
		String id_customer = request.getParameter("id_customer");
		String category = ""+request.getParameter("category");
		category = Utility.noNull(category);
		
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			conn = Utility.getConnRfid();
			st=conn.createStatement();
			
			String cond = "";
			if(category.length()>0)
				cond+=" and category = '"+category+"' ";
			
			sql   = "select distinct trim(season) season from products\n" + 
					"where id_customer = "+id_customer+" and season is not null and trim(season) <> '' "+cond;
			rs=st.executeQuery(sql);
			
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("season", rs.getString("season"));
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			
			
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
		
		responseJson.put("listings", jsonArray);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
