package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import pkg.beans.ats.Barcode;
import pkg.beans.ats.Vendor;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/DelPhoto")
public class DelPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelPhoto() {
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
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		String msg = "OK";
		
		String id = request.getParameter("id");
		
		try {
			conn=Utility.getConnData();
			
			sql = "delete from ats.photo where id = "+id+" ";
			st = conn.prepareStatement(sql);
			System.out.println(sql);
			st.executeUpdate();
			st.close();
			
			String idItem = request.getParameter("idItem");
			
			sql   = "select p.*, name, description from ats.photo p "
					+ "join ats.items i on i.id = p.iditem "
					+ "where iditem = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(idItem));
			
			rs=st.executeQuery();
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getInt("id"));
				jsonObj.put("iditem", rs.getInt("iditem"));
				jsonObj.put("title", rs.getString("name"));
				jsonObj.put("capition", rs.getString("description"));
				jsonObj.put("url", rs.getString("url"));
				jsonArray.add(jsonObj);
			}
			rs.close();
			st.close();
			
			responseJson.put("listings", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
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
