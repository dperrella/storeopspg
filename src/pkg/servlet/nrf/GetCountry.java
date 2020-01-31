package pkg.servlet.nrf;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/nrf/GetCountry")
public class GetCountry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCountry() {
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
		
		try {
			
			String id_continent = request.getParameter("id_continent");
			
			conn=Utility.getConnData();
			st=conn.createStatement();
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "Select * from demo.arealist\n" + 
					"where id_continent = '"+id_continent+"' and id_area is null and id_naz is not null\n" + 
					"order by country  ";
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("area", rs.getString("area"));
				jsonObj.put("country", rs.getString("country"));
				jsonObj.put("nrStores", rs.getInt("nstores"));
				jsonObj.put("idContinent", rs.getString("id_continent"));
				jsonObj.put("idNaz", rs.getString("id_naz"));
				jsonObj.put("idArea", rs.getString("id_area"));
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
