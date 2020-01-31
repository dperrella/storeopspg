package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pkg.beans.Rfid_documents;
import pkg.beans.Rfid_documents_details;
import pkg.beans.Rfid_step;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/GetDocsFromMexall")
public class GetDocsFromMexall extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDocsFromMexall() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Connection connMexall = null;
		Statement st = null;
		Statement stMexall = null;
		Statement stMexallRow = null;
		ResultSet rs = null;
		ResultSet rsMexall = null;
		ResultSet rsMexallRow = null;
		
		
		String sql = "";
		
		// idflow e idstep
		String idflow = request.getParameter("idflow");
		String idstep = request.getParameter("idstep");
		
		// filtri
		String filters = request.getParameter("filters");
		
		// dataformat
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		
		// Oggetto JSON per response
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("idflow", idflow);
		jsonObj.put("idstep", idstep);
		
		try {
			connMexall = Utility.getConnMexall();
			stMexall = connMexall.createStatement();
			stMexallRow = connMexall.createStatement();
			
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			
			// Imposto l'oggetto step
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
			String origin_warehouse = (String) cfg_bef.get("origin_warehouse");
			String destination_warehouse = (String) cfg_bef.get("destination_warehouse");
			String update_diary = (String) cfg_bef.get("update_diary");
			String status = (String) cfg_bef.get("status");	
			String docref = ""; // da mexall
			int docnum = 0; // autoinc
			
			sql = "select top 10 * from smart.dbo.documenti order by datadoc desc ";	
			//sql = "select * from smart.dbo.documenti ";	
			
			rsMexall=stMexall.executeQuery(sql);
			
			Rfid_documents doc = new Rfid_documents();
			
			while(rsMexall.next()) {
				
				docref = rsMexall.getString("docid");
				doc.setDocref_origin(docref);
				if(!doc.isDocref_origin(conn)) {
					// scrive documents e documents_detail
					doc.setId_flow(Integer.parseInt(idflow));
					doc.setId_step(Integer.parseInt(idstep));
					docnum = Integer.parseInt(Utility.getNewProgr(conn, "documents", "docnum", "where id_flow = "+idflow+" and id_step = "+idstep));
					doc.setDocnum(docnum);
					doc.setDoctype(Integer.parseInt(doctype));
					doc.setDocdate(dateIso.format(rsMexall.getDate("datadoc")));
					doc.setOrigin_warehouse(Integer.parseInt(origin_warehouse));
					doc.setDestination_warehouse(Integer.parseInt(destination_warehouse));
					if(update_diary.equalsIgnoreCase("true"))
						doc.setUpdate_diary(true);
					else
						doc.setUpdate_diary(false);
					doc.setStatus(Integer.parseInt(status));
					doc.setExtra("");
					doc.insert(conn);
					
					// righe documento
					// TODO --- chiedi a Mario
					sql   = "select * from smart.dbo.documenti_dettaglio where docid = '"+rsMexall.getString("docid")+"' ";
					rsMexallRow = stMexallRow.executeQuery(sql);
					while(rsMexallRow.next()) {
						Rfid_documents_details det = new Rfid_documents_details();
						det.setId_flow(Integer.parseInt(idflow));
						det.setId_step(Integer.parseInt(idstep));
						det.setDocnum(docnum);
						//det.setNumline(Integer.parseInt(Utility.getNewProgr(conn, "documents_details", "numline", "where id_flow = "+idflow+" and id_step = "+idstep+" and docnum = "+docnum)));
						det.setBarcode(rsMexallRow.getString("barcode"));
						//det.setQty_theoretical(rsMexallRow.getInt("pezzi"));
						det.setQty_theoretical(1);
						det.setStatus(Integer.parseInt(status));
						det.setExtra("");
						
						for(int i=0;i<rsMexallRow.getInt("pezzi");i++) {
							det.setNumline(Integer.parseInt(Utility.getNewProgr(conn, "documents_details", "numline", "where id_flow = "+idflow+" and id_step = "+idstep+" and docnum = "+docnum)));
							
							det.insert(conn);
						}
					}
					rsMexallRow.close();
				}
				
			}	
			rsMexall.close();
			
			jsonObj.put("status", "OK");
			jsonObj.put("message", "Documents imported from Mexall");
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			jsonObj.put("status", "KO");
			jsonObj.put("message", e.getMessage());
			
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
				
				if(stMexallRow!=null)
					stMexallRow.close();
				if(stMexall!=null)
					stMexall.close();
				if(connMexall!=null)
					connMexall.close();
			}
			catch(Exception e) {}
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonObj.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
