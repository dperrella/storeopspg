package pkg.posws;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;

@WebServlet("/FeedServlet")
public class FeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       

    public FeedServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		conn=Utility.getConnPos();
		String resp = "";
		
		try {
			st=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/**/		
  		String clockss = "";
		try {
			sql = "select to_char(now(), 'dd-MM-yyyy HH24:MI:SS') as clock";
			rs = st.executeQuery(sql);
				while(rs.next()) {
					clockss = rs.getString("clock");
				}	
			rs.close();
			//conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		PrintWriter out = response.getWriter();
		String json = "{ \"query\":[[\""+clockss+"\",\""+clockss+"\",\""+clockss+"\",\""+clockss+"\"],[\"First Name2\",\"Last Name2\",\"Address2\",\"Address2\"]]}";
		System.out.println("json = " + json);
		out.println(json);

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String json = "{ \"query\":[[\"First Name3\",\"Last Name3\",\"Address3\",\"Address3\"],[\"First Name4\",\"Last Name4\",\"Address4\",\"Address4\"]]}";
		out.println(json);
		
	}
	
}
