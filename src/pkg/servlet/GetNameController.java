package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;


public class GetNameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetNameController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("CONTROLLO NOME");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String resp = "";
		String resp_app = "";
		
		String xcdel = request.getParameter("xcdel");	
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
					
			sql   = "select xcdel from public.ztables where upper(xcdel) = upper('"+xcdel+"') ";
			rs=st.executeQuery(sql);
			
			if(rs.next()) {
				resp_app = rs.getString("xcdel");
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

		if ("".equals(resp_app)) {
			resp = "OK";
		}else {
			resp = "KO";
		}
		
		System.out.println("sql = " + sql);
		
		
		System.out.println("Resp = " + resp);
		
		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
