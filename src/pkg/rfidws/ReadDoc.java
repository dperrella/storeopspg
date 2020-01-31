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
@WebServlet("/rfid/ws/ReadDoc")
public class ReadDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadDoc() {
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
		
		String status = ""+request.getParameter("status");
		status = Utility.noNull(status);
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			Rfid_documents bean = new Rfid_documents();
			bean.setId_flow(Integer.parseInt(idflow));
			bean.setId_step(Integer.parseInt(idstep));
			
			
			List<Rfid_documents> doc = new ArrayList<Rfid_documents>();
			doc.clear();
			
			String cond = "";
			if(status.length()==0)
				cond = "and status = 1 ";
			else
				cond = "";
			doc = bean.selectAll(conn, cond);
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			for(int i=0;i<doc.size();i++) {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("iddoc", doc.get(i).getIddoc());
				jsonObj.put("id_flow", doc.get(i).getId_flow());
				jsonObj.put("id_step", doc.get(i).getId_step());
				jsonObj.put("docnum", doc.get(i).getDocnum());
				jsonObj.put("doctype", doc.get(i).getDoctype());
				jsonObj.put("type_description", doc.get(i).getCmp2());
				jsonObj.put("docdate", doc.get(i).getDocdate());
				jsonObj.put("origin_warehouse", doc.get(i).getOrigin_warehouse());
				jsonObj.put("origin_description", doc.get(i).getCmp3());
				jsonObj.put("destination_warehouse", doc.get(i).getDestination_warehouse());
				jsonObj.put("destination_description", doc.get(i).getCmp4());
				jsonObj.put("destination_location", doc.get(i).getDestination_location());
				jsonObj.put("update_diary", doc.get(i).getUpdate_diary());
				jsonObj.put("status", doc.get(i).getStatus());
				jsonObj.put("docref_origin", doc.get(i).getDocref_origin());
				jsonObj.put("docref_destination", doc.get(i).getDocref_destination());
				jsonObj.put("extra", doc.get(i).getExtra());
				jsonObj.put("doc_note", doc.get(i).getDoc_note());
				jsonObj.put("id_doc_prec", doc.get(i).getId_doc_prec());
				jsonObj.put("doc_exchange", doc.get(i).getDoc_exchange());
				jsonObj.put("tot_theo", doc.get(i).getTot_theo());
				jsonObj.put("tot_read", doc.get(i).getTot_read());
				
				jsonArray.add(jsonObj);
				
			}
			
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

}
