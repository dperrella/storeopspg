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
@WebServlet("/servlet/ws/GetSitesNodes")
public class GetSitesNodes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSitesNodes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Connection connAdmin = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		String sql = "";
		
		String idcus = request.getParameter("idcus");
		String username = request.getParameter("username");
		String idsites = ""+request.getParameter("idsites");
		idsites = Utility.noNull(idsites);
		String tmp[] = idsites.split(",");
		
		List sites = new ArrayList<String>();
		for(int i=0;i<tmp.length;i++) {
			sites.add(tmp[i]);
		}
		
		try {
			conn=Utility.getConnRfid();
			connAdmin=Utility.getConnAdmin();
			st=connAdmin.createStatement();
			st2=conn.createStatement();
			
			/*
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "select c.id, c.description from customers c "+
					"where c.id = "+idcus+" ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("id").trim());
				jsonObj.put("type", "CAT1");
				jsonObj.put("text", rs.getString("description").trim());
				jsonObj.put("selectable", true);
				
				// ---------------------->
				// Legge Region per xcdel 
				JSONArray jsonArray2 = new JSONArray();
				sql   = "select id, description from sites "+
						"where id_customer = "+rs.getString("id")+" ";
				System.out.println(sql);
				rs2=st2.executeQuery(sql);
				while(rs2.next()) {
					JSONObject jsonObj2 = new JSONObject();
					jsonObj2.put("id", rs2.getString("id").trim());
					jsonObj2.put("type", "SITE");
					jsonObj2.put("text", rs2.getString("description").trim());
					jsonObj2.put("selectable", true);
					jsonArray2.add(jsonObj2);
				}
				rs2.close();
				// <----------------------
				
				jsonObj.put("nodes", jsonArray2);
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			//responseJson.put("listings", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonArray.toString());
			*/
			
			
			
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "select c.id, c.description from customers c "+
					"where c.id = "+idcus+" ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("id").trim());
				jsonObj.put("type", "CAT1");
				jsonObj.put("text", rs.getString("description").trim());
				jsonObj.put("selectable", true);
				
				
						
						// ---------------------->
						// Legge Stores per xcdel 
						JSONArray jsonArray4 = new JSONArray();
						sql   = "select id, description from sites "+
								"where id_customer = "+rs.getString("id")+" ";
						System.out.println(sql);
						rs2=st2.executeQuery(sql);
						while(rs2.next()) {
							JSONObject jsonObj4 = new JSONObject();
							jsonObj4.put("type", "SITE");
							jsonObj4.put("id", rs2.getString("id").trim());
							jsonObj4.put("text", rs2.getString("description").trim());
							jsonObj4.put("selectable", true);
							
							if(sites.contains(rs2.getString("id").trim())) {
								JSONObject jsonObj5 = new JSONObject();
								jsonObj5.put("checked", true);
								jsonObj4.put("state", jsonObj5);
							}
							
							jsonArray4.add(jsonObj4);
						}
						rs2.close();
						// <----------------------
						
				jsonObj.put("nodes", jsonArray4);
				
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
				if(st2!=null)
					st2.close();
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
				if(connAdmin!=null)
					connAdmin.close();
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
