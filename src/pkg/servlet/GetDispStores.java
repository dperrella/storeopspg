package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;


public class GetDispStores extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetDispStores() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String optS = "";
		String resp = "";
		
		String id_customer = ""+request.getParameter("id_customer");
		id_customer = Utility.noNull(id_customer);
		
		optS = "<option value=''> -- Seleziona -- </option>";
		
		try {
			conn=Utility.getConnAdmin();
			st=conn.createStatement();
			
			sql   = "select s.id, s.description, s.store_code from stores s , customers c "
					+ "where c.id = s.id_customer and c.account_code in('"+id_customer+"') ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			while(rs.next()) {

				optS += "<option value='"+rs.getString("store_code")+"'>"+rs.getString("description")+"</option>"; 
				
			}
			rs.close();
			
			
			
			resp = Utility.noNull(""+optS);
		
			response.setContentType("text/plain");
			
			response.getWriter().write(resp);	

			
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
