package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;

@WebServlet("/smcheckdevicespreregistration")
public class smcheckdevicespreregistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public smcheckdevicespreregistration() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**/
		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int Hh = cal.get(Calendar.HOUR_OF_DAY);
		
		month = (month + 1);

		String idStore = "";
		String flags = "KO";
		String sname = "";
		String snote = "";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String sql_ = "";
		conn=Utility.getConnControl();
		String resp = "";
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		sname = Utility.noApex(request.getParameter("sname"));
		snote = Utility.noApex(request.getParameter("snote"));
		
		
		try {
				
				sql = "INSERT INTO preregistrazione(accountid, storeid, doorid, storename, storenotes) "+
					  " VALUES ("+request.getParameter("account")+","+request.getParameter("store")+","+request.getParameter("door")+",'"+sname+"','"+snote+"');";
					
				//System.out.println("SQL INSert = "+sql);
				st.executeUpdate(sql);
				flags = "OK";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		resp = flags;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
			
	/**/	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
