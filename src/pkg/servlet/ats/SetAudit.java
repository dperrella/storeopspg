package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

import pkg.beans.ats.Audit;
import pkg.beans.ats.AuditItem;
import pkg.beans.ats.Item;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetAudit")
public class SetAudit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAudit() {
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
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String idType = request.getParameter("idtype");
		String closingDate = request.getParameter("closingdate");
		String state = request.getParameter("auditstate");
		
		String idcustomer = ""+request.getParameter("idcustomer");
		idcustomer = Utility.noNull(idcustomer);
		if(idcustomer.length()==0)
			idcustomer = "0";
		String idstore = request.getParameter("idstore");
		
		
		System.out.println(type);
		System.out.println(id);
		System.out.println(name);
		System.out.println(idType);
		System.out.println(closingDate);
		System.out.println(state);
		
		System.out.println(idcustomer);
		System.out.println(idstore);
		
		
		
		try {
			conn=Utility.getConnData();
			
			Audit audit = new Audit();
		
			switch (type) {
            
            case "I": 
            	
            	audit.setName(name);
            	audit.setIdType(Integer.parseInt(idType));
            	audit.setClosingDate(closingDate);
            	audit.setState(0);
            	audit.setIdStore(Integer.parseInt(idstore));
            	audit.setIdCustomer(Integer.parseInt(idcustomer));
            	audit.add(conn);
            	
            	break;
            	
            case "E": 
            	
            	audit.setIdAudit(Integer.parseInt(id));
            	audit.setName(name);
            	audit.setIdType(Integer.parseInt(idType));
            	audit.setClosingDate(closingDate);
            	audit.setState(Integer.parseInt(state));
            	audit.setIdStore(Integer.parseInt(idstore));
            	audit.setIdCustomer(Integer.parseInt(idcustomer));
            	audit.update(conn);
            	
            	break;
            	
            case "D": 
            	
            	audit.setIdAudit(Integer.parseInt(id));
            	audit.delete(conn);
	
            	break;
			}
			
			
			
			switch (type) {
            
	            case "I": 
	            case "E":
				try {
	            	String[] items=request.getParameterValues("iditem");
	            	AuditItem ai = new AuditItem();
	            	
	            	
	            	System.out.println("items: "+items.length+" "+items.toString());
	            	if(items.length>0) {
		            	AuditItem.deleteAuditItems(conn, Integer.parseInt(id));
		            	
		                for(int i=0;i<items.length;i++)
		                {
		                	
		                	System.out.println("item: "+items[i]);
			                ai.setIdItem(Integer.parseInt(items[i]));
			                ai.setIdAudit(Integer.parseInt(id));
			                ai.add(conn);
		                   
		                }
	            	}
	        	}
	        	catch(Exception e) {
	        		
	        	}
				break;
			}
            
			
			
			responseJson.put("idAudit", audit.getIdAudit());
			
			
			JSONArray jsonArray = new JSONArray();
			ArrayList<Audit> auditList = Audit.getAudits(conn);
			for(int i=0;i<auditList.size();i++) {
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("id", auditList.get(i).getIdAudit());
				jsonObj.put("name", auditList.get(i).getName());
				jsonObj.put("idtype", auditList.get(i).getIdType());
				jsonObj.put("creation_date", auditList.get(i).getCreationDate());
				jsonObj.put("closing_date", auditList.get(i).getClosingDate());
				jsonObj.put("state", auditList.get(i).getState());
				jsonObj.put("idstore", auditList.get(i).getIdStore());
				jsonObj.put("type", auditList.get(i).getTypeDescription());
				jsonObj.put("store", auditList.get(i).getStoreDescription());
				
				jsonArray.add(jsonObj);
			}
			
			responseJson.put("listings", jsonArray);
			
			
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
