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
@WebServlet("/rfid/ws/GetFastLink")
public class GetFastLink extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFastLink() {
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
		Statement st2 = null;
		ResultSet rs2 = null;
		String sql = "";
		
		String mac = request.getParameter("mac");		
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			st2=conn.createStatement();
			
			int iddevice = 0;
			int nrflow = 0;
			int nrstep = 0;
			int idflow = 0;
			int idstep = 0;
			
			sql   = "select d.iddevice, idflow "
					+ "from devices d , rel_dev_step a "
					+ "where mac = '"+mac+"' "
					+ "and d.iddevice = a.id_device "
					+ "group by d.iddevice, a.idflow";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				nrflow+=1;
				iddevice = rs.getInt("iddevice");
				idflow = rs.getInt("idflow");
				
				sql   = "select idstep from rel_dev_step "+
						"where idflow = "+idflow+" and id_device = "+iddevice;
				rs2=st2.executeQuery(sql);
				while(rs2.next()) {
					
					nrstep+=1;
					idstep = rs2.getInt("idstep");
					
				}
				rs2.close();
				
			}
			rs.close();
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("nrflow", nrflow);
			jsonObj.put("nrstep", nrstep);
			jsonObj.put("iddevice", iddevice);
			if(nrflow==1&&nrstep==1) {
				jsonObj.put("idflow", idflow);
				jsonObj.put("idstep", idstep);
			}
			else {
				jsonObj.put("idflow", null);
				jsonObj.put("idstep", null);
			}
				
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
