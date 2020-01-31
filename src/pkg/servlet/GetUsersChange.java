package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

public class GetUsersChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetUsersChange() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/**/
		
		
		HttpSession session = request.getSession();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String resp = "";
		String Exch = "";
		
		String username = request.getParameter("username");

		
		try {
			conn=Utility.getConnAdmin();
			st=conn.createStatement();
		
			sql   = "SELECT * FROM zutenti where upper(idusr) <> upper('"+username+"') order by cogn,nome ";	
			rs = st.executeQuery(sql);

			while(rs.next()) {
				Exch+="<option value='"+rs.getString("idusr")+"' value2='"+rs.getString("pwd")+"' >"+rs.getString("idusr")+" - "+rs.getString("nome")+" "+rs.getString("cogn") +"</option>";
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
		
		resp = Exch;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
/**/		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
