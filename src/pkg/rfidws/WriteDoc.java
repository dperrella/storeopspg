package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import pkg.beans.Rfid_step;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/WriteDoc")
public class WriteDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteDoc() {
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
		int origin_warehouse = Integer.parseInt(request.getParameter("origin_warehouse"));
		int destination_warehouse = Integer.parseInt(request.getParameter("destination_warehouse"));
		
		// dataformat
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			Rfid_step step = new Rfid_step();
			step.setIdflow(Integer.parseInt(idflow));
			step.setIdstep(Integer.parseInt(idstep));
			step.selectRow(conn);
			// Leggo i parametri per il before
			String config_before = step.getConfig_before();
			
			// Lo trasformo in json
			JSONParser parser = new JSONParser();
			Object _obj = parser.parse(config_before);
			JSONObject jsonObject = (JSONObject) _obj;
			System.out.println(jsonObject);
	
			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("config");
			Iterator<String> iterator = msg.iterator();
			
			JSONObject cfg_bef = new JSONObject();
			while (iterator.hasNext()) {
			    
				Object obj2 = iterator.next();
			    JSONObject jsonObject2 = (JSONObject) obj2;
			    
			    String name = (String) jsonObject2.get("name");
			    String tp = (String) jsonObject2.get("type");
			    
			    if(tp.equalsIgnoreCase("Text"))
			    	cfg_bef.put(name, (String) jsonObject2.get("value"));
			    else
			    	cfg_bef.put(name, jsonObject2.get("value"));
			}
			// leggo i parametri per l'importazione dei documenti
			String doctype = (String) cfg_bef.get("doc_type");
			//String origin_warehouse = (String) cfg_bef.get("origin_warehouse");
			//String destination_warehouse = (String) cfg_bef.get("destination_warehouse");
			String update_diary = (String) cfg_bef.get("update_diary");
			String status = (String) cfg_bef.get("status");	
			String docref = ""; // da mexall
			int docnum = 0; // autoinc
			
			Rfid_documents doc = new Rfid_documents();
			doc.setDocref_origin(docref);
			// scrive documents e documents_detail
			doc.setId_flow(Integer.parseInt(idflow));
			doc.setId_step(Integer.parseInt(idstep));
			docnum = Integer.parseInt(Utility.getNewProgr(conn, "documents", "docnum", "where id_flow = "+idflow+" and id_step = "+idstep));
			doc.setDocnum(docnum);
			doc.setDoctype(Integer.parseInt(doctype));
			doc.setDocdate(dateIso.format(new Date()));
			doc.setOrigin_warehouse(origin_warehouse);
			doc.setDestination_warehouse(destination_warehouse);
			if(update_diary.equalsIgnoreCase("true"))
				doc.setUpdate_diary(true);
			else
				doc.setUpdate_diary(false);
			doc.setStatus(Integer.parseInt(status));
			doc.setExtra("");
			doc.insert(conn);
			
			// iddoc inserito
			String iddoc = doc.getIddoc();
			
			
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
