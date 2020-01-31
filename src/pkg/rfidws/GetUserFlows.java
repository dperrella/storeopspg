package pkg.rfidws;

import java.io.IOException;
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
@WebServlet("/rfid/ws/GetUserFlows")
public class GetUserFlows extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserFlows() {
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
		
		String idusr = request.getParameter("idusr");
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			
			sql   = "select f.idflow, f.description, count(idstep) nrstep "
					+ "from rel_usr_step a, flow f " 
					+ "where a.idusr = '"+idusr+"' "
					+ "and a.idflow = f.idflow "
					+ "group by f.idflow, f.description ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("idflow", rs.getInt("idflow"));
				jsonObj.put("description", rs.getString("description"));
				jsonObj.put("nrstep", rs.getInt("nrstep"));
				if(rs.getInt("nrstep")==1) {
					jsonObj.put("idstep", getStepFromFlow(conn, rs.getInt("idflow")));
				}
				else {
					jsonObj.put("idstep", 0);
				}
				jsonArray.add(jsonObj);
			}
			rs.close();
			
			responseJson.put("listings", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
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
	
	private static int getStepFromFlow(Connection conn, int idflow) {
		
		int idstep = 0;
		Statement st = null;
		
		try {
			st = conn.createStatement();
			
			String sql   = "select idstep from rel_usr_step where idflow = "+idflow+" ";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				idstep = rs.getInt("idstep");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		return idstep;
		
	}

}
