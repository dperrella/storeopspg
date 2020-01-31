package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import pkg.beans.ats.Category;
import pkg.beans.ats.Vendor;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetCategory")
public class SetCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetCategory() {
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
		
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		try {
			conn=Utility.getConnData();
			st = conn.createStatement();
			
			Category c = new Category();
		
			switch (type) {
            
            case "I": 
            	
            	c.setName(name);
            	c.add(conn);
            	
            	break;
            	
            case "E": 
            	
            	c.setIdCategory(Integer.parseInt(id));
            	c.setName(name);
            	c.update(conn);
            	
            	break;
            	
            case "D": 
            	
            	c.setIdCategory(Integer.parseInt(id));
            	c.delete(conn);
            	break;
			}
			
			JSONArray jsonArray = new JSONArray();
			
			String sql   = "select * from ats.category ";
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("key", rs.getInt("id"));
				jsonObj.put("value", rs.getInt("id"));
				jsonObj.put("label", rs.getString("name"));
				jsonArray.add(jsonObj);
			}
			rs.close();
			responseJson.put("categoryList", jsonArray);
			responseJson.put("category", c.getIdCategory());
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
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
		responseJson.put("status", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
