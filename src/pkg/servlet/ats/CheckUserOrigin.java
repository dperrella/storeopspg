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
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/ats/CheckUserOrigin")
public class CheckUserOrigin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUserOrigin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		String sql = "";
		String msg = "";


		// request
		String username = ""+request.getParameter("username");
		try {
			HttpSession session = request.getSession();
			
			conn = Utility.getConnData();
			
			sql = "select origin from zutenti where idusr = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, username.toUpperCase().trim());
			rs = st.executeQuery();
			if(rs.next()) {
				String origin = Utility.noNull(rs.getString("origin"));
				session.setAttribute("origin", origin.trim().length()==0 ? "Suite" : origin);
				msg = origin.trim().length()==0 ? "Suite" : origin;
			}
			else {
				session.setAttribute("origin", null);
				msg = "Unknown";
			}
			rs.close();
			st.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("origin", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonObj.toString());
		
	}
	

}
