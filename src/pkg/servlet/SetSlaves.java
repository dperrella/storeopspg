package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;


public class SetSlaves extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SetSlaves() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String flag = request.getParameter("flag");
		String dev = request.getParameter("dev");
		String slav = request.getParameter("slav");

		String idNew = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "", sql1 = "";
		conn=Utility.getConnControl();
		String resp = "";
		String flags_ = "OK";
		
		
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (flag.equals("U")) {
				sql = "update deviceslaves set provissorio = false, eventcode = '' where id_device = '"+dev+"' and id_slave = '"+slav+"' ";	
			}else {
				sql = "delete from deviceslaves where id_device = '"+dev+"' and id_slave = '"+slav+"' ";
			}
			
			System.out.println("sql = " + sql);
			st.executeUpdate(sql);
			
			//conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flags_ = "KO";
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

		resp = flags_;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
				
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
