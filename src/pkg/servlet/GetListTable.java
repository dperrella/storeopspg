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


public class GetListTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetListTable() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String datas = ""+request.getParameter("datas");
		datas = Utility.noNull(datas);
		
		datas = Utility.getTurnDatealt(datas);
		
		try {
			
			conn=Utility.getConnAdmin();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql  =  "select * from accesslog_details_hourly where logdate = '"+datas+"' ";
			//System.out.println("SQL = " + sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("Iduser", rs.getString("idusr"));
				jsonObj.put("Logdate", Utility.getTurnDate(rs.getString("logdate")));
				jsonObj.put("Hour", rs.getString("hour"));
				jsonObj.put("Num", rs.getString("num"));
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			responseJson.put("listings", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
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
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
