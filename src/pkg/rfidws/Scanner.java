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
import pkg.beans.Rfid_scanner;
import pkg.utility.HttpHandler;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/Scanner")
public class Scanner extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Scanner() {
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
		String iddoc = ""+request.getParameter("iddoc");
		iddoc=Utility.noNull(iddoc);
		
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			
			/*
			// Se non viene passato il documento vengono passati magazzino di origine
			// e magazzino di destinazione e viene creato il documento
			if(iddoc.length()==0) {
				
				int origin_warehouse = Integer.parseInt(request.getParameter("origin_warehouse"));
				int destination_warehouse = Integer.parseInt(request.getParameter("destination_warehouse"));
				
				HttpHandler sh = new HttpHandler();

				// Scrive Scanner
				String url = "http://localhost:8080/SmartSuite/rfid/ws/WriteDoc?idflow="+idflow+"&idstep="+idstep+"&origin_warehouse="+origin_warehouse+"&destination_warehouse="+destination_warehouse;
				String jsonStr = sh.makeServiceCall(url);
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(jsonStr.toString());
				JSONObject responseJson = (JSONObject) _obj;
				
				iddoc = ""+responseJson.get("iddoc");
	            
			}
			*/
			
			Rfid_scanner bean = new Rfid_scanner();
			bean.setIddoc(iddoc);
			
			bean.selectRowFromIddoc(conn);
			int idscan = bean.getIdscan();
			String ins_date = "";
			
			if(idscan==0) {
				// Scrivo il record in idscan
				bean.setId_flow(Integer.parseInt(idflow));
				bean.setId_step(Integer.parseInt(idstep));
				bean.insert(conn);
				
				idscan = bean.getIdscan();
				ins_date = dateIso.format(new Date());
			}
			else {
				ins_date = bean.getIns_date();
			}
				
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("iddoc", iddoc);
			jsonObj.put("idflow", Integer.parseInt(idflow));
			jsonObj.put("idstep", Integer.parseInt(idstep));
			jsonObj.put("idscan", idscan);
			jsonObj.put("ins_date", ins_date);
			
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
