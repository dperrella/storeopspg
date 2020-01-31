package pkg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;

import pkg.utility.Utility;

/**
 * Servlet implementation class Clockings
 */
public class Clockings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Clockings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id_employee = request.getParameter("id_employee"); 
		String badge = request.getParameter("badge");
		String reason = request.getParameter("reason");
		String timestamp = request.getParameter("timestamp");
		String note_justificative = request.getParameter("note_justificative");
		String approved = request.getParameter("approved");
		String exported = request.getParameter("exported");
		String id_departments = request.getParameter("id_departments");
		String autoid = request.getParameter("autoid");
		String timeins = request.getParameter("timeins");
		
		Connection conn = null;
		Statement st = null;
		String sql = "";
		
		JSONObject responseJson = new JSONObject();
		
		try {
			conn=Utility.getConnHR();
			st=conn.createStatement();
			
			sql   = "INSERT into hr_clockings_app values "+
					"(id_employee, badge, reason, timestamp, note_justificative,  "+
					"approved, exported, id_departments, autoid, timeins) "+
					"values "+
					"('"+id_employee+"', '"+badge+"', '"+reason+"', '"+timestamp+"' , '"+note_justificative+"', "+
					"'"+approved+"', '"+exported+"', '"+id_departments+"', "+autoid+" , '"+timeins+"') ";
			st.executeUpdate(sql);
			
			responseJson.put("status", "OK");
			responseJson.put("message", "Record Inserted");
			responseJson.put("sql", sql);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			responseJson.put("status", "KO");
			responseJson.put("message", e.getMessage());
			responseJson.put("sql", sql);
			
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
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
