package pkg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;

import java.sql.*;
/**
 * Servlet implementation class GetSms
 */
@WebServlet("/GetNot")
public class GetNot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNot() {
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
		
		String username = request.getParameter("username");
		String resp = "";
		
		/*
		try {
			
			
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			
			sql   = "select count(idnota) count from znotif where idusr = '"+username+"' and (stato <>'A' or stato is null) ";
			//System.out.println(sql);
			rs=st.executeQuery(sql);
			int count = 0;
			if(rs.next()) {
				count = rs.getInt("count");
			}
			rs.close();
			
			if(count>0)
				resp = "<span class=\"badge badge-sm up bg-danger count\" style=\"display: inline-block;\">"+count+"</span>";
			
			
		}
		catch(Exception e) {
			//e.printStackTrace();
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
		*/
		
		response.setContentType("text/plain");
		response.getWriter().write(resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
