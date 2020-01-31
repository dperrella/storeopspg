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


public class GetLogsCharts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetLogsCharts() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String resp = "";
		String hour = "";
		String cont = "";
		
		String datas = Utility.noNull((""+request.getParameter("datas")));
		
		
		datas = Utility.getTurnDatealt(datas);
		
		try {
			conn=Utility.getConnAdmin();
			st=conn.createStatement();
				
			sql   = "select i::date logdates, extract(hour from i) as hour, coalesce(num,null,0) num " + 
					"from generate_series('"+datas+" 00:00'::timestamp,'"+datas+" 23:59'::timestamp,'1 hour'::interval) i left join accesslog_hourly " + 
					"on i::date = logdate and extract(hour from i) = hour where i::date = '"+datas+"'";
			
			//System.out.println("SQL = " + sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				if (hour == "") {
					hour = "\""+rs.getString("hour")+"\"";
				}else {
					hour = hour + ",\"" + rs.getString("hour")+"\"";
				}
				
				if (cont == "") {
					cont = rs.getString("num");
				}else {
					cont = cont + "," + rs.getString("num");
				}
				
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
		
		
		resp = hour + "-" + cont;

		System.out.println("resp = " + resp);
		
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
