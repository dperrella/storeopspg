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

import pkg.beans.ats.Barcode;
import pkg.beans.ats.Vendor;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/ClearAllPrefs")
public class ClearAllPrefs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearAllPrefs() {
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
		String sql = "";
		
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		String idusr = request.getParameter("idusr");
		
		try {
			conn=Utility.getConnData();
			st = conn.createStatement();
			
			sql = "delete from pref_user_customers where idusr = '"+idusr.toUpperCase()+"' ";
			System.out.println(sql);
			st.executeUpdate(sql);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		finally {
			try {
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
