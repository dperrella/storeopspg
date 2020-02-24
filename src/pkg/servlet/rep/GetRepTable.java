package pkg.servlet.rep;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/rep/GetRepTable")
public class GetRepTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRepTable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String schema = request.getParameter("schema");
		String table = request.getParameter("table");
		String val = "";
		try {
			conn=Utility.getConnRfid();
			
			
			sql  =  "SELECT json_agg(t) val FROM "+schema+"."+table+" t where rep_exported = false ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			if(rs.next()) {
				val=rs.getString("val");
			}
			rs.close();
			st.close();
			
			JSONParser parser = new JSONParser();
			JSONArray jsonArray = (JSONArray) parser.parse(val);
			JSONObject responseJson = new JSONObject();
			responseJson.put("schema", schema);
			responseJson.put("table", table);
			responseJson.put("code", 201);
			responseJson.put("message", "OK");
			responseJson.put("data", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			JSONArray jsonArray = new JSONArray();
			JSONObject responseJson = new JSONObject();
			responseJson.put("schema", schema);
			responseJson.put("table", table);
			responseJson.put("code", 500);
			responseJson.put("message", "Table "+schema+"/"+table+" does not exists.");
			responseJson.put("data", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
		}
		finally {
			try {
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
