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
@WebServlet("/servlet/ws/GetStoreFromCategory_BKP")
public class GetStoreFromCategory_BKP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStoreFromCategory_BKP() {
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
			
			for(int i=0; i<values.length; i++)
			{
				String condcat = " and category = '"+values[i].replaceAll("'", "''")+"' ";
				
				String[] values2=request.getParameterValues("_cat_"+values[i]);
				
				String val = "";
				
				if(values2!=null) {
					for(int ii=0; ii<values2.length; ii++)
					{
						val+= "'"+values2[ii].replaceAll("'", "''")+"',";
					}
					if(val.length()>3) {
						val = val.substring(0,val.length()-1);
						condcat+=" and value in("+val+") ";
					}
					cond+=condcat;
				}	
			}
			
			System.out.println(cond);
			
		
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			String idstore = "";
			
			if(cond.length()>0) {
				
				sql   = "select c.id_store from stores_category c, " + 
						"f_stores s where c.id_store = s.id "+cond;
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
