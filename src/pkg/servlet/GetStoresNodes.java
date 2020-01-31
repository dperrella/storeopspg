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
@WebServlet("/servlet/ws/GetStoresNodes")
public class GetStoresNodes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStoresNodes() {
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
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		
		String sql = "";
		
		String idcus = request.getParameter("idcus");
		String username = request.getParameter("username");
		String idstores = ""+request.getParameter("idstores");
		idstores = Utility.noNull(idstores);
		String tmp[] = idstores.split(",");
		
		List stores = new ArrayList<String>();
		for(int i=0;i<tmp.length;i++) {
			stores.add(tmp[i]);
		}
		
		try {
			conn=Utility.getConnData();
			st=conn.createStatement();
			st2=conn.createStatement();
			st3=conn.createStatement();
			st4=conn.createStatement();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			sql   = "select category1, c.description as xdtab from stores s, customers c "+
					"where category1 = c.id::text and s.id_customer = c.id "+
					"and s.id in(select idstore from rel_user_stores "+ 
					"where idusr = '"+username+"' ) "+
					"and c.id = "+idcus+" "+
					"and closed = false "+
					"group by category1, c.description ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", rs.getString("category1").trim());
				jsonObj.put("type", "CAT1");
				jsonObj.put("text", rs.getString("xdtab").trim());
				jsonObj.put("selectable", true);
				
				// ---------------------->
				// Legge Region per xcdel 
				JSONArray jsonArray2 = new JSONArray();
				sql   = "select category2, nome_nazione as xdtab from stores, nazioni "+
						"where category2 = id_naz::text "+
						"and category1 = '"+rs.getString("category1")+"' "+
						"and id in(select idstore from rel_user_stores "+
						"where idusr = '"+username+"') "+
						"and id_customer = "+idcus+" "+
						"and closed = false "+
						"group by category2, xdtab";
				System.out.println(sql);
				rs2=st2.executeQuery(sql);
				while(rs2.next()) {
					JSONObject jsonObj2 = new JSONObject();
					jsonObj2.put("id", rs2.getString("category2").trim());
					jsonObj2.put("type", "CAT2");
					jsonObj2.put("text", rs2.getString("xdtab").trim());
					jsonObj2.put("selectable", true);
					
					// ---------------------->
					// Legge Area per xcdel 
					JSONArray jsonArray3 = new JSONArray();
					sql   = "select category3, nome_regione as xdtab from stores, regioni "+
							"where category3 = id_reg::text "+
							"and category1 = '"+rs.getString("category1")+"' "+
							"and category2 = '"+rs2.getString("category2")+"'"+
							"and id in(select idstore from rel_user_stores "+
							"where idusr = '"+username+"') "+
							"and id_customer = "+idcus+" "+
							"and closed = false "+
							"group by category3, xdtab";
					System.out.println(sql);
					rs3=st3.executeQuery(sql);
					while(rs3.next()) {
						JSONObject jsonObj3 = new JSONObject();
						jsonObj3.put("id", rs3.getString("category3").trim());
						jsonObj3.put("type", "CAT3");
						jsonObj3.put("text", rs3.getString("xdtab").trim());
						jsonObj3.put("selectable", true);
						
						// ---------------------->
						// Legge Stores per xcdel 
						JSONArray jsonArray4 = new JSONArray();
						sql   = "select id, category4 from stores "+
								"where category1 = '"+rs.getString("category1")+"' "+
								"and category2 = '"+rs2.getString("category2")+"' "+
								"and category3 = '"+rs3.getString("category3")+"' "+
								"and id in(select idstore from rel_user_stores "+
								"where idusr = '"+username+"') "+
								"and closed = false "+
								"group by id, category4 ";
						System.out.println(sql);
						rs4=st4.executeQuery(sql);
						while(rs4.next()) {
							JSONObject jsonObj4 = new JSONObject();
							jsonObj4.put("type", "STORE");
							jsonObj4.put("id", rs4.getString("id").trim());
							jsonObj4.put("text", rs4.getString("category4").trim());
							jsonObj4.put("selectable", true);
							
							if(stores.contains(rs4.getString("id").trim())) {
								JSONObject jsonObj5 = new JSONObject();
								jsonObj5.put("checked", true);
								jsonObj4.put("state", jsonObj5);
							}
							
							jsonArray4.add(jsonObj4);
						}
						rs4.close();
						// <----------------------
						
						jsonObj3.put("nodes", jsonArray4);
						
						jsonArray3.add(jsonObj3);
					}
					rs3.close();
					// <----------------------
					
					jsonObj2.put("nodes", jsonArray3);
					
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
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
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
