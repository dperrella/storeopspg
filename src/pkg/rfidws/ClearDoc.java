package pkg.rfidws;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_config;
import pkg.beans.Rfid_devices;
import pkg.beans.Rfid_step;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/ClearDoc")
public class ClearDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearDoc() {
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
		String iddoc = "";
		String idscan = "";
		try {
			
			conn = Utility.getConnRfid();
			st = conn.createStatement();
			
			sql   = "select iddoc from documents "
					+ "where id_flow = "+idflow+" and id_step = "+idstep+" "
					+ "and docnum = "+docnum;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				iddoc = rs.getString("iddoc");
			}
			
			sql   = "select idscan from scanner where iddoc = '"+iddoc+"' ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				idscan = rs.getString("idscan");
			}
			
			// Cancellazione
			sql   = "delete from documents_details "
					+ "where id_flow = "+idflow+" and id_step = "+idstep+" "
					+ "and docnum = "+docnum;
			System.out.println(sql);
			st.executeUpdate(sql);
			
			sql   = "delete from documents "
					+ "where id_flow = "+idflow+" and id_step = "+idstep+" "
					+ "and docnum = "+docnum;
			System.out.println(sql);
			st.executeUpdate(sql);
			
			sql   = "delete from scannerdetail "
					+ "where idscan = "+idscan;
			System.out.println(sql);
			st.executeUpdate(sql);
			
			sql   = "delete from scanner "
					+ "where idscan = "+idscan;
			System.out.println(sql);
			st.executeUpdate(sql);
			
			
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
