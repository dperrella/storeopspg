package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

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

import pkg.beans.ats.AuditItem;
import pkg.beans.ats.Barcode;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetAuditItemAllDummy")
public class SetAuditItemAllDummy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAuditItemAllDummy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		String idAudit = request.getParameter("idaudit");
		String idCustomer = request.getParameter("idcustomer");
		
		System.out.println(idAudit);
		
		try {
			conn=Utility.getConnData();
			
			AuditItem ai = new AuditItem();
			ai.setIdAudit(Integer.parseInt(idAudit));
        	ai.addAllDummy(conn, Integer.parseInt(idCustomer));
			
			ArrayList<AuditItem> auditList = AuditItem.getAuditItems(conn, Integer.parseInt(idAudit));
			//String json = new Gson().toJson(auditList);
			
			GsonBuilder builder = new GsonBuilder(); 
		    builder.serializeNulls(); 
		    builder.setPrettyPrinting(); 
		    Gson gson = builder.create();  
		    String json = gson.toJson(auditList);
			
			Object object=null;
			JSONArray arrayObj=null;
			JSONParser jsonParser=new JSONParser();
			object=jsonParser.parse(json);
			arrayObj=(JSONArray) object;
			
			JSONObject jsonObj = new JSONObject();
			responseJson.put("auditItems", arrayObj);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		responseJson.put("status", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
