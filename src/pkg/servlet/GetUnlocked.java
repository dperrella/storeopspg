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

import com.jcraft.jsch.Session;

import pkg.utility.Utility;

public class GetUnlocked extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetUnlocked() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		System.out.println("IN GET-nlock = ");
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String founds = "N";
		String bod = "";
		
		String flag = request.getParameter("flag");
		String body = request.getParameter("body");
		String pwds = request.getParameter("pwd");
		
		String resp = "";
		try {
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			
			if (flag.equals("U")) {
			
				sql   = "select * from zutenti where idusr = '"+session.getAttribute("username")+"' and pwd = '"+pwds+"' ";
				System.out.println("sql_unlock = " + sql);
				rs=st.executeQuery(sql);
				int count = 0;
				if(rs.next()) {
					founds = "Y";
				}
				rs.close();
				
				session.setAttribute("Flags", flag);
				
			}else {
				session.setAttribute("Body", body);
				session.setAttribute("Flags", flag);
			}

			
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
		
		resp = founds;
		
		response.setContentType("text/plain");
		response.getWriter().write(resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
