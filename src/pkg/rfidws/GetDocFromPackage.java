package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import pkg.beans.Rfid_documents;
import pkg.beans.Rfid_flow;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/GetDocFromPackage")
public class GetDocFromPackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDocFromPackage() {
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
		String package_id = request.getParameter("package_id");
		
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			
			String iddoc = "";
			int docnum = 0;
			
			sql   = "select iddoc, a.docnum "
					+ "from documents a, documents_details b "
					+ "where a.id_flow = b.id_flow "
					+ "and a.id_step = b.id_step "
					+ "and a.docnum = b.docnum "
					+ "and b.package_id = '"+package_id+"' "
					+ "and a.id_flow = "+idflow+" "
					+ "and a.id_step = "+idstep+" ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				iddoc = rs.getString("iddoc");
				docnum = rs.getInt("docnum");
			}
			rs.close();
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("iddoc", iddoc);
			jsonObj.put("docnum", docnum);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObj.toString());
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
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
