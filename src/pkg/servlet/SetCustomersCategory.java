package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.Tables;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/SetCustomersCategory")
public class SetCustomersCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetCustomersCategory() {
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
		Statement st2 = null;
		Statement st3 = null;
		Statement st4 = null;
		Statement up = null;
		
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		
		String sql = "";
		
		String type = request.getParameter("type");
		String idcus = request.getParameter("idcus");
		String category = request.getParameter("category");
		String parent_category = request.getParameter("parent_category");
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
			st2=conn.createStatement();
			st3=conn.createStatement();
			st4=conn.createStatement();
			up=conn.createStatement();
			
			switch(type) {
			case "I":
				
				System.out.println(type);
				
				sql = "insert into customers_category (id_customer, category, parent_category) "
						+ "values ("+idcus+" , '"+category.replaceAll("'","''")+"' , '"+parent_category.replaceAll("'","''")+"') ";
				up.executeUpdate(sql);
				break;
			
			case "V":
				
				
				
				System.out.println(type);
				String value = request.getParameter("value");
				System.out.println(value);
				
				
				sql = "update customers_category set value = '"+value+"' "
						+ "where id_customer = "+idcus+" and category = '"+category.replaceAll("'","''")+"' ";
				up.executeUpdate(sql);
				break;
			
			case "R":
				
				
				
				System.out.println(type);
				String required = request.getParameter("required");
				System.out.println(required);
				
				
				sql = "update customers_category set required = "+required+" "
						+ "where id_customer = "+idcus+" and category = '"+category.replaceAll("'","''")+"' ";
				up.executeUpdate(sql);
				break;
			
				
			case "D":
				
				System.out.println(type);
				
				sql   = "select category from customers_category "+
						"where id_customer = "+idcus+" and category = '"+category.replaceAll("'","''")+"' and parent_category = '0' ";
				System.out.println(sql);
				
				rs=st.executeQuery(sql);
				
				while(rs.next()) {
					
					sql   = "select category from customers_category "+
							"where id_customer = "+idcus+" and parent_category = '"+rs.getString("category")+"' ";
					System.out.println(sql);
					
					rs2=st2.executeQuery(sql);
					while(rs2.next()) {
						
						sql   = "select category from customers_category "+
								"where id_customer = "+idcus+" and parent_category = '"+rs2.getString("category")+"' ";
						System.out.println(sql);
						
						rs3=st3.executeQuery(sql);
						while(rs3.next()) {
							
							sql   = "select category from customers_category "+
									"where id_customer = "+idcus+" and parent_category = '"+rs3.getString("category")+"' ";
							System.out.println(sql);
							rs4=st4.executeQuery(sql);
							while(rs4.next()) {
								
								up.executeUpdate("delete from customers_category "
										+ "where id_customer = "+idcus+" and category = '"+rs4.getString("category").replaceAll("'", "''")+"' ");
								System.out.println(sql);
								
							}
							rs4.close();
							// <----------------------
							
							up.executeUpdate("delete from customers_category "
									+ "where id_customer = "+idcus+" and category = '"+rs3.getString("category").replaceAll("'", "''")+"' ");
							System.out.println(sql);
							
							
						}
						rs3.close();
						// <----------------------
						
						up.executeUpdate("delete from customers_category "
								+ "where id_customer = "+idcus+" and category = '"+rs2.getString("category").replaceAll("'", "''")+"' ");
						System.out.println(sql);
						
					}
					rs2.close();
					// <----------------------
					
				}
				rs.close();
				
				up.executeUpdate("delete from customers_category "
						+ "where id_customer = "+idcus+" and category = '"+category.replaceAll("'", "''")+"' ");
				System.out.println(sql);
				
				break;
			}
			
			
			
			
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "select category, parent_category from customers_category "+
					"where id_customer = "+idcus+" and parent_category = '0' ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("category").trim());
				jsonObj.put("type", "CAT1");
				jsonObj.put("name", rs.getString("category").trim());
				jsonObj.put("text", rs.getString("category").trim());
				jsonObj.put("parent_id", "0");
				
				// ---------------------->
				// Legge Region per xcdel 
				JSONArray jsonArray2 = new JSONArray();
				sql   = "select category, parent_category from customers_category "+
						"where id_customer = "+idcus+" and parent_category = '"+rs.getString("category")+"' ";
				System.out.println(sql);
				rs2=st2.executeQuery(sql);
				while(rs2.next()) {
					
					jsonObj.put("hasChild", true);
					
					JSONObject jsonObj2 = new JSONObject();
					jsonObj2.put("id", rs2.getString("category").trim());
					jsonObj2.put("type", "CAT2");
					jsonObj2.put("name", rs2.getString("category").trim());
					jsonObj2.put("text", rs2.getString("category").trim());
					jsonObj2.put("parent_id", rs.getString("category"));
					
					// ---------------------->
					// Legge Area per xcdel 
					JSONArray jsonArray3 = new JSONArray();
					sql   = "select category, parent_category from customers_category "+
							"where id_customer = "+idcus+" and parent_category = '"+rs2.getString("category")+"' ";
					System.out.println(sql);
					rs3=st3.executeQuery(sql);
					while(rs3.next()) {
						
						jsonObj2.put("hasChild", true);
						
						JSONObject jsonObj3 = new JSONObject();
						jsonObj3.put("id", rs3.getString("category").trim());
						jsonObj3.put("type", "CAT3");
						jsonObj3.put("name", rs3.getString("category").trim());
						jsonObj3.put("text", rs3.getString("category").trim());
						jsonObj3.put("parent_id", rs2.getString("category"));
						
						// ---------------------->
						// Legge Stores per xcdel 
						JSONArray jsonArray4 = new JSONArray();
						sql   = "select category, parent_category from customers_category "+
								"where id_customer = "+idcus+" and parent_category = '"+rs3.getString("category")+"' ";
						System.out.println(sql);
						rs4=st4.executeQuery(sql);
						while(rs4.next()) {
							
							jsonObj3.put("hasChild", true);
							
							JSONObject jsonObj4 = new JSONObject();
							jsonObj4.put("id", rs4.getString("category").trim());
							jsonObj4.put("type", "CAT4");
							jsonObj4.put("name", rs4.getString("category").trim());
							jsonObj4.put("text", rs4.getString("category").trim());
							jsonObj4.put("parent_id", rs3.getString("category"));
							
							jsonArray4.add(jsonObj4);
						}
						rs4.close();
						// <----------------------
						
						if(jsonArray4.size()>0)
							jsonObj3.put("nodes", jsonArray4);
						
						jsonArray3.add(jsonObj3);
					}
					rs3.close();
					// <----------------------
					
					if(jsonArray3.size()>0)
						jsonObj2.put("nodes", jsonArray3);
					
					jsonArray2.add(jsonObj2);
				}
				rs2.close();
				// <----------------------
				
				if(jsonArray2.size()>0)
					jsonObj.put("nodes", jsonArray2);
				
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			//responseJson.put("listings", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonArray.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(up!=null)
					up.close();
				if(st4!=null)
					st4.close();
				if(st3!=null)
					st3.close();
				if(st2!=null)
					st2.close();
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
		doGet(request, response);
	}

}
