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
@WebServlet("/ats/SetAuditItem")
public class SetAuditItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAuditItem() {
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
		
		String type = request.getParameter("type");
		String idAudit = request.getParameter("idaudit");
		String idItem = request.getParameter("iditem");
		
		String result = request.getParameter("result");
		String note = request.getParameter("note");
		
		System.out.println(type);
		System.out.println(idAudit);
		System.out.println(idItem);
		System.out.println(result);
		System.out.println(note);
		
		
		
		
		try {
			conn=Utility.getConnData();
			
			AuditItem ai = new AuditItem();
		
			switch (type) {
            
            case "I": 
            	
            	ai.setIdAudit(Integer.parseInt(idAudit));
            	ai.setIdItem(Integer.parseInt(idItem));
            	ai.add(conn);
            	
            	break;
            	
            	
            case "E": 
            	
            	ai.setIdAudit(Integer.parseInt(idAudit));
            	ai.setIdItem(Integer.parseInt(idItem));
            	ai.setAuditResult(result);
            	ai.setNote(note);
            	
            	ai.update(conn);
            	
            	break;
            	
            case "D": 
            	
            	ai.setIdAudit(Integer.parseInt(idAudit));
            	ai.setIdItem(Integer.parseInt(idItem));
            	ai.delete(conn);
	
            	break;
			}
			
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
