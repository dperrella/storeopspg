package pkg.servlet.ats;

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

import pkg.beans.ats.Wishlist;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetWishlist")
public class SetWishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetWishlist() {
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
		
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		try {
			conn=Utility.getConnData();
			st = conn.createStatement();
			
			Wishlist v = new Wishlist();
		
			switch (type) {
            
            case "I": 
            	
            	v.setName(name);
            	v.add(conn);
            	
            	break;
            	
            case "E": 
            	
            	v.setIdWishlist(Integer.parseInt(id));
            	v.setName(name);
            	v.update(conn);
            	
            	break;
            	
            case "D": 
            	
            	v.setIdWishlist(Integer.parseInt(id));
            	v.delete(conn);
	
            	break;
			}
			
			
			
			
			
			
			if(type.equals("D")) {
				
				String role = request.getParameter("role");
				String username = request.getParameter("username");
				
				
				String cond = "";
				if(role.trim().equals("3")||role.trim().equals("8")) {
					cond = " and i.idusr = '"+username.replaceAll("'", "''")+"' ";
				}
				
				JSONArray jsonArray = new JSONArray();
				
				String sql   = "select w.*, \n" + 
						"(select count(i.id) from ats.items i where i.idwishlist = w.id and protected = 0 "+cond+" ) num\n" + 
						"from ats.wishlist w\n" + 
						"order by w.name; ";
				System.out.println(sql);
				ResultSet rs=st.executeQuery(sql);
				
				while(rs.next()) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", rs.getInt("id"));
					jsonObj.put("name", rs.getString("name"));
					jsonObj.put("num", rs.getString("num"));
					jsonArray.add(jsonObj);
				}
				rs.close();
			
				responseJson.put("listings", jsonArray);
				//response.setContentType("application/json;charset=UTF-8");
				//response.getWriter().write(responseJson.toString());
				
				
			}
			else {
				
				JSONArray jsonArray = new JSONArray();
				
				
				String sql   = "select * from ats.wishlist ";
				ResultSet rs=st.executeQuery(sql);
				while(rs.next()) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("key", rs.getInt("id"));
					jsonObj.put("value", rs.getInt("id"));
					jsonObj.put("label", rs.getString("name"));
					jsonArray.add(jsonObj);
				}
				rs.close();
				
				responseJson.put("wishlistList", jsonArray);
				responseJson.put("wishlist", v.getIdWishlist());
				
			}
			
			
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
		responseJson.put("status", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
