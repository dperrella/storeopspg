package pkg.servlet.pos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetLog
 */
@WebServlet("/pos/GetStockFilter")
public class GetStockFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStockFilter() {
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
		
		HttpSession session = request.getSession();
		
		try {
			conn=Utility.getConnPos();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			
			
			
			JSONArray jsonArray = new JSONArray();
			
			
			sql   = "select v_dashlog_2.*,stores.store_code, stores.id from v_dashlog_2 "
					+ "left join stores on v_dashlog_2.id = stores.id::text "
					+ "where stores.id in(select idstore::integer from rel_user_stores where idusr = '"+session.getAttribute("username").toString().trim().toUpperCase()+"') "
					+ "order by diffdate desc ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			int num = 0;
			while(rs.next()) {
				num+=1;
				
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add(rs.getString("diffdate"));
				jsonRecord.add(rs.getString("tstamp"));
				jsonRecord.add(rs.getString("brandname"));
				jsonRecord.add(rs.getString("store"));
				jsonRecord.add(rs.getString("eventcode"));
				jsonRecord.add(rs.getString("store_code"));
				jsonRecord.add(rs.getString("id"));
				jsonRecord.add("");
				jsonRecord.add("");
				jsonRecord.add(Utility.noNull(rs.getString("notes")));
				
				jsonArray.add(jsonRecord);
				
			}
			rs.close();
			
			responseJson.put("data", jsonArray);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
