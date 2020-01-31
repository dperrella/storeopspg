package pkg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

import java.sql.*;
/**
 * Servlet implementation class GetProductDetail
 */
@WebServlet("/servlet/ws/GetSize")
public class RFIDGetSize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RFIDGetSize() {
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
		
		String id_customer = request.getParameter("id_customer");
		String category = ""+request.getParameter("category");
		category = Utility.noNull(category);
		String season = ""+request.getParameter("season");
		season = Utility.noNull(season);
		String itemcode = ""+request.getParameter("itemcode");
		itemcode = Utility.noNull(itemcode);
		String colourcode = ""+request.getParameter("colourcode");
		colourcode = Utility.noNull(colourcode);
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			conn = Utility.getConnRfid();
			st=conn.createStatement();
			
			String cond = "";
			if(category.length()>0)
				cond+=" and category = '"+category+"' ";
			if(season.length()>0)
				cond+=" and season = '"+season+"' ";
			if(itemcode.length()>0)
				cond+=" and itemcode = '"+itemcode+"' ";
			if(colourcode.length()>0)
				cond+=" and colourcode = '"+colourcode+"' ";
			
			sql   = "select distinct trim(size) size from products\n" + 
					"where id_customer = "+id_customer+" and size is not null "+cond;
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("size", rs.getString("size"));
				jsonArray.add(jsonObj);
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
		
		responseJson.put("listings", jsonArray);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
