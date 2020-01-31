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
import pkg.beans.Rfid_documents_details;
import pkg.beans.Rfid_flow;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/ReadDocDetails")
public class ReadDocDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadDocDetails() {
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
		
		String idflow = request.getParameter("idflow");	
		String idstep = request.getParameter("idstep");	
		String docnum = request.getParameter("docnum");
		String group = request.getParameter("group");
		String package_id = ""+request.getParameter("package_id");
		package_id = Utility.noNull(package_id);
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			Rfid_documents_details bean = new Rfid_documents_details();
			bean.setId_flow(Integer.parseInt(idflow));
			bean.setId_step(Integer.parseInt(idstep));
			bean.setDocnum(Integer.parseInt(docnum));
			
			
			List<Rfid_documents_details> doc = new ArrayList<Rfid_documents_details>();
			doc.clear();
			doc = bean.selectAllGrouped(conn, group, package_id);
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			int tot_theo = 0;
			int tot_read = 0;
			
			for(int i=0;i<doc.size();i++) {
				
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("id_flow", doc.get(i).getId_flow());
				jsonObj.put("id_step", doc.get(i).getId_step());
				jsonObj.put("docnum", doc.get(i).getDocnum());
				jsonObj.put("numline", doc.get(i).getNumline());
				jsonObj.put("epc", doc.get(i).getEpc());
				jsonObj.put("barcode", doc.get(i).getBarcode());
				jsonObj.put("qty_theoretical", doc.get(i).getQty_theoretical());
				jsonObj.put("qty_readed", doc.get(i).getQty_readed());
				jsonObj.put("loadunits_id", doc.get(i).getLoadunits_id());
				jsonObj.put("package_id", doc.get(i).getPackage_id());
				jsonObj.put("pallet_id", doc.get(i).getpallet_id());
				jsonObj.put("status", doc.get(i).getStatus());
				jsonObj.put("extra", doc.get(i).getExtra());
				jsonObj.put("reference", doc.get(i).getCmp2());
				jsonObj.put("name", doc.get(i).getCmp3());
				
				tot_theo+=doc.get(i).getQty_theoretical();
				tot_read+=doc.get(i).getQty_readed();
				
				jsonArray.add(jsonObj);
				
			}
			
			responseJson.put("listings", jsonArray);
			
			responseJson.put("tot_theo", tot_theo);
			responseJson.put("tot_read", tot_read);
			
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

}
