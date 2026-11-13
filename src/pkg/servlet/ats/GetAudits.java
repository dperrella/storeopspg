package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.ats.Audit;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/GetAudits")
public class GetAudits extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAudits() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		List<Audit> audit = new ArrayList<Audit>();
		audit.clear();
		
		String idusr = ""+request.getParameter("idusr");
		idusr = Utility.noNull(idusr);
		
		String state = ""+request.getParameter("state");
		state = Utility.noNull(state);
		if(state.length()==0)
			state = "0";
		
		
		try {
			conn=Utility.getConnData();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			if(idusr.length()>0) {
				audit = Audit.getAuditsByUser(conn, idusr, state);
			}
			else {
				audit = Audit.getAudits(conn);
			}
			for(int i=0;i<audit.size();i++) {
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("id", audit.get(i).getIdAudit());
				jsonObj.put("name", audit.get(i).getName());
				jsonObj.put("idtype", audit.get(i).getIdType());
				jsonObj.put("creation_date", audit.get(i).getCreationDate());
				jsonObj.put("closing_date", audit.get(i).getClosingDate());
				jsonObj.put("state", audit.get(i).getState());
				jsonObj.put("idstore", audit.get(i).getIdStore());
				jsonObj.put("type", audit.get(i).getTypeDescription());
				jsonObj.put("store", audit.get(i).getStoreDescription());
				
//				if(audit.get(i).getState()==0) {
//					jsonObj.put("state_desc", "Active");
//				}
//				else if(audit.get(i).getState()==1) {
//					jsonObj.put("state_desc", "Closed");
//				}
				
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
