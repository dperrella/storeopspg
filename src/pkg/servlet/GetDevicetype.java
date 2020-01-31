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
 * Servlet implementation class GetComuni
 */
@WebServlet("/servlet/ws/GetDevicetype")
public class GetDevicetype extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDevicetype() {
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
		
		String iddevicetype = ""+request.getParameter("iddevicetype");
		iddevicetype = Utility.noNull(iddevicetype);
		if(iddevicetype.length()==0)
			iddevicetype = "0";
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			JSONObject jsonObj = new JSONObject();
			
			sql   = "select * from device_type where iddevicetype = "+iddevicetype;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				jsonObj.put("iddevicetype", rs.getInt("iddevicetype"));
				jsonObj.put("description", rs.getString("description"));
				jsonObj.put("config_devicetype", rs.getString("config_devicetype"));
				jsonObj.put("extra", rs.getString("extra"));
				jsonObj.put("extra_language", rs.getString("extra_language"));	
			}
			rs.close();
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObj.toString());
			
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
