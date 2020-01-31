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
@WebServlet("/servlet/ws/GetProducts2")
public class RFIDGetProducts2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RFIDGetProducts2() {
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
		String name = ""+request.getParameter("term");
		name = Utility.noNull(name);
		
		
		
		
		String category = ""+request.getParameter("category");
		category = Utility.noNull(category);
		String season = ""+request.getParameter("season");
		season = Utility.noNull(season);
		String itemcode = ""+request.getParameter("itemcode");
		itemcode = Utility.noNull(itemcode);
		String colourcode = ""+request.getParameter("colourcode");
		colourcode = Utility.noNull(colourcode);
		String size = ""+request.getParameter("size");
		size = Utility.noNull(size);
		
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			conn = Utility.getConnRfid();
			st=conn.createStatement();
			
			String cond = "";
			if(name.length()>0)
				cond+=" and upper(code||name) like '%"+name.toUpperCase()+"%' ";
			if(category.length()>0)
				cond+=" and category = '"+category+"' ";
			if(season.length()>0)
				cond+=" and season = '"+season+"' ";
			if(itemcode.length()>0)
				cond+=" and itemcode = '"+itemcode+"' ";
			if(colourcode.length()>0)
				cond+=" and colourcode = '"+colourcode+"' ";
			if(size.length()>0)
				cond+=" and size = '"+size+"' ";
			
			
			sql   = "select distinct code, name " +
					"from products\n" + 
					"where id_customer = "+id_customer+" and sku is not null "+cond+" group by code, name limit 1000 ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("code", rs.getString("code"));
				jsonObj.put("name", rs.getString("name"));
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
