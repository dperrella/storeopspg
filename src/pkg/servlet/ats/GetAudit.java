package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pkg.beans.ats.Audit;
import pkg.beans.ats.AuditItem;
import pkg.beans.ats.Item;
import pkg.beans.rfid.Readers;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/GetAudit")
public class GetAudit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAudit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		try {
			conn=Utility.getConnData();
			
			JSONObject responseJson = new JSONObject();
			
			String idAudit = request.getParameter("idAudit");
			
			Audit audit = new Audit();
			audit.setIdAudit(Integer.parseInt(idAudit));
			audit.selectRow(conn);
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("id", audit.getIdAudit());
			jsonObj.put("name", audit.getName());
			jsonObj.put("idtype", audit.getIdType());
			jsonObj.put("creation_date", audit.getCreationDate());
			jsonObj.put("closing_date", audit.getClosingDate());
			jsonObj.put("state", audit.getState());
			jsonObj.put("idstore", audit.getIdStore());
			jsonObj.put("type", audit.getTypeDescription());
			jsonObj.put("store", audit.getStoreDescription());
			jsonObj.put("idcustomer", audit.getIdCustomer());
			
			ArrayList<AuditItem> auditList = AuditItem.getAuditItems(conn, audit.getIdAudit());
			
			/*
			Gson gson = new Gson();  
			String json = gson.toJson(auditList);
			*/
			
			GsonBuilder builder = new GsonBuilder(); 
		    builder.serializeNulls(); 
		    builder.setPrettyPrinting(); 
		    Gson gson = builder.create();  
		    String json = gson.toJson(auditList);
		    
		    //System.out.println(json);
			
			Object object=null;
			JSONArray arrayObj=null;
			JSONParser jsonParser=new JSONParser();
			object=jsonParser.parse(json);
			arrayObj=(JSONArray) object;
			
			jsonObj.put("auditItems", arrayObj);
			
			//responseJson.put("item", jsonObj);
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
