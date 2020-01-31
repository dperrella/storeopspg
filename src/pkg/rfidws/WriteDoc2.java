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
@WebServlet("/rfid/ws/WriteDoc2")
public class WriteDoc2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteDoc2() {
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
		int doctype = Integer.parseInt(request.getParameter("doctype"));
		String update_diary = request.getParameter("update_diary");
		int status = Integer.parseInt(request.getParameter("status"));
		
		// dataformat
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
		
			String docref = ""; // da mexall
			int docnum = 0; // autoinc
			
			Rfid_documents doc = new Rfid_documents();
			doc.setDocref_origin(docref);
			// scrive documents e documents_detail
			doc.setId_flow(Integer.parseInt(idflow));
			doc.setId_step(Integer.parseInt(idstep));
			docnum = Integer.parseInt(Utility.getNewProgr(conn, "documents", "docnum", "where id_flow = "+idflow+" and id_step = "+idstep));
			doc.setDocnum(docnum);
			doc.setDoctype(doctype);
			doc.setDocdate(dateIso.format(new Date()));
			doc.setOrigin_warehouse(origin_warehouse);
			doc.setDestination_warehouse(destination_warehouse);
			if(update_diary.equalsIgnoreCase("true"))
				doc.setUpdate_diary(true);
			else
				doc.setUpdate_diary(false);
			doc.setStatus(status);
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
