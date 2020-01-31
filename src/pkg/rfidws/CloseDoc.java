package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/CloseDoc")
public class CloseDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CloseDoc() {
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
		
		String idflow = request.getParameter("idflow");
		String idstep = request.getParameter("idstep");
		String docnum = request.getParameter("docnum");
		String status = request.getParameter("status");
		
		try {
			
			conn = Utility.getConnRfid();
			st = conn.createStatement();
			
			// leggo iddoc 
			sql   = "select iddoc from documents where docnum = "+docnum+" and id_flow = "+idflow+" and id_step = "+idstep+" ";
			rs=st.executeQuery(sql);
			String iddoc = "";
			if(rs.next()) {
				iddoc = rs.getString("iddoc");
			}
			rs.close();
			
			sql   = "update documents "
					+ "set status = "+status+" "
					+ "where docnum = "+docnum+" "
					+ "and id_flow = "+idflow+" and id_step = "+idstep+" ";
 			System.out.println(sql);
			st.executeUpdate(sql);
			
			sql   = "update documents_details "
					+ "set status = "+status+" "
					+ "where docnum = "+docnum+" "
					+ "and id_flow = "+idflow+" and id_step = "+idstep+" ";
			System.out.println(sql);
			st.executeUpdate(sql);
			
			// call function
			try {
				sql   = "select * from advance_step('"+iddoc+"', "+idstep+" ) ";
				rs=st.executeQuery(sql);
				rs.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			JSONObject responseJson = new JSONObject();
			responseJson.put("status", true);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			JSONObject responseJson = new JSONObject();
			responseJson.put("status", false);
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
				
			}
			catch(Exception e) {}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
