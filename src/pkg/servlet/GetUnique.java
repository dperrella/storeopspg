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

public class GetUnique extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetUnique() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String resp = "OK";
		
		try {
			conn=Utility.getConnAdmin();
			st=conn.createStatement();
			
			if(request.getParameter("tabs").equals("D")) {
				sql   = "select id from devices where accountid = "+request.getParameter("accid")+" and storeid = "+request.getParameter("stoid")+" and doorid = " + request.getParameter("dooid");
			}else if (request.getParameter("tabs").equals("S")) {
				sql   = "select id from stores where id_customer = "+request.getParameter("accid")+" and store_code::text = '"+request.getParameter("stoid")+"'";
			}else {
				sql   = "select id from customers where account_code::text = '"+request.getParameter("accid")+"'";				
			}
			
			System.out.println("sql-unique = " + sql);
			rs=st.executeQuery(sql);
			
			if(rs.next()) {
				resp = "KO";
			}
			rs.close();
			
			System.out.println(resp);
			
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
		
		//sql-unique = select id from stores where id_customer = 155 and store_code = 9999
		response.setContentType("text/plain");
		response.getWriter().write(resp);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
