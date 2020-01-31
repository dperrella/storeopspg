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
@WebServlet("/servlet/ws/GetStoresNodesHB")
public class GetStoresNodesHB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStoresNodesHB() {
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
			
			sql   = "select category1, xdtab from stores_hb, ztables "+
					"where trim(category1) = trim(xcdel) and xcdtb = 'BRA' "+
					"and id in(select idstore from rel_user_stores "+ 
					"where idusr = '"+username+"') "+
					"and id_customer = "+idcus+" "+
					"group by category1, xdtab";
			//System.out.println(sql);
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
				sql   = "select category2, xdtab from stores_hb, ztables "+
						"where trim(category2) = trim(xcdel) and xcdtb = 'REG' "+
						"and trim(category1) = trim('"+rs.getString("category1")+"') "+
						"and id in(select idstore from rel_user_stores "+
						"where idusr = '"+username+"') "+
						"group by category2, xdtab";
				//System.out.println(sql);
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
					sql   = "select category3, xdtab from stores_hb, ztables "+
							"where trim(category3) = trim(xcdel) and xcdtb = 'ARA' "+
							"and trim(category1) = trim('"+rs.getString("category1")+"') "+
							"and trim(category2) = trim('"+rs2.getString("category2")+"') "+
							"and id in(select idstore from rel_user_stores "+
							"where idusr = '"+username+"') "+
							"group by category3, xdtab";
					//System.out.println(sql);
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
						sql   = "select id, category4 from stores_hb "+
								"where trim(category1) = trim('"+rs.getString("category1")+"') "+
								"and trim(category2) = trim('"+rs2.getString("category2")+"') "+
								"and trim(category3) = trim('"+rs3.getString("category3")+"') "+
								"and id in(select idstore from rel_user_stores "+
								"where idusr = '"+username+"') "+
								"group by id, category4 ";
						rs4=st4.executeQuery(sql);
						//System.out.println(sql);
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
