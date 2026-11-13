package pkg.servlet;

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
 * Servlet implementation class GetNazioni
 */
@WebServlet("/servlet/ws/GetStoreFromCategory")
public class GetStoreFromCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStoreFromCategory() {
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
			conn=Utility.getConnAdmin();
			st=conn.createStatement();

			String cond = "";
			//String description=request.getParameter("_description");
			//System.out.println(description);
			
			//if(description.length()>0)
			//	cond+=" and upper(description) like '%"+description.toUpperCase().replaceAll("'","''")+"%' ";
			
			String[] values=request.getParameterValues("_cat");
			
			String cat = "";
			String val = "";
			
			String condd  = "";
			
			
			for(int i=0; i<values.length; i++)
			{
				
				//String condcat = " and category = '"+values[i].replaceAll("'", "''")+"' ";
				cat+= "'"+values[i].replaceAll("'", "''")+"',";
				
				String[] values2=request.getParameterValues("_cat_"+values[i]);
				
				if(values2!=null) {
					for(int ii=0; ii<values2.length; ii++)
					{
						val+= "'"+values2[ii].replaceAll("'", "''")+"',";
					}
					
				}	
			}
			
			if(cat.length()>3) {
				cat =  cat.substring(0,cat.length()-1);
				condd+=" and category in("+cat+") ";
			}
			
			if(val.length()>3) {
				val = val.substring(0,val.length()-1);
				condd+=" and value in("+val+") ";
			}
			
			System.out.println(cat);
			System.out.println(val);
			
			
			System.out.println(condd);
			
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			String idstore = "";
			
			if(condd.length()>0) {
					
				sql   = "select distinct c.id_store from stores_category c, " + 
						"f_stores s where c.id_store = s.id "+condd+" ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				
				while(rs.next()) {
					idstore+=rs.getString("id_store")+",";
					
				}
				rs.close();
				if(idstore.length()>0)
					idstore=idstore.substring(0,idstore.length()-1);
				
			}
			responseJson.put("stores", idstore);
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
		doGet(request, response);
	}

}
