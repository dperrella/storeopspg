package pkg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.JobStatus;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetActiveAlarms
 */
@WebServlet("/servlet/ws/GetJobStatus")
public class GetJobStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJobStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<JobStatus> js = new ArrayList<JobStatus>();
		js.clear();
		
		HttpSession session = request.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy KK:mm:ss");
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			
			
			js = JobStatus.selectAll();
			
			String process = "";
			String type = "";
			String lastFail = "";
			String lastSuccessful = "";
			String nextRun = "";
			String lastStatus = "";
			String currentStatus = "";
			String statusSince = "";
			String icon = "";
			
			for (JobStatus record : js) {
				
				process = record.getProcess();
				type = record.getType();
				lastFail = record.getLastFail();
				lastSuccessful = record.getLastSuccessful();
				nextRun = record.getNextRun();
				lastStatus = record.getLastStatus();
				currentStatus = ""+record.getCurrentStatus();
				statusSince = record.getStatusSince();
				if(currentStatus.equalsIgnoreCase("SUCCESSFUL")) {
					icon = "<i class=\"fa fa-circle text-success\"></i>";
				}
				else if(currentStatus.equalsIgnoreCase("RUNNING")) {
					icon = "<i class=\"fa fa-circle-o text-success\"></i>";
				}
				else {
					icon = "<i class=\"fa fa-circle text-danger\"></i>";
				}
				 
				JSONArray jsonRecord = new JSONArray();
				jsonRecord.add(icon);
				jsonRecord.add(process);
				jsonRecord.add(type);
				jsonRecord.add(lastFail);
				jsonRecord.add(lastSuccessful);
				jsonRecord.add(nextRun);
				jsonRecord.add(lastStatus);
				jsonRecord.add(currentStatus);
				jsonRecord.add(statusSince);
				jsonArray.add(jsonRecord);
				
			}
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		responseJson.put("data", jsonArray);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
