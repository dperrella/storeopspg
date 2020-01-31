package pkg.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pkg.utility.Utility;


public class JobStatus {
	private String process;
	private String type;
	private String lastFail;
	private String lastSuccessful;
	private String nextRun;
	private String lastStatus;
	private String currentStatus;
	private String statusSince;
	
	public JobStatus() {}

	public JobStatus(String process, String type, String lastFail, String lastSuccessful, 
			String nextRun, String lastStatus, String currentStatus, String statusSince) {
		
		this.process=process;
		this.type=type;
		this.lastFail=lastFail;
		this.lastSuccessful=lastSuccessful;
		this.nextRun=nextRun;
		this.lastStatus=lastStatus;
		this.currentStatus=currentStatus;
		this.statusSince=statusSince;
	
	}
	
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLastFail() {
		return lastFail;
	}
	public void setLastFail(String lastFail) {
		this.lastFail = lastFail;
	}
	
	public String getLastSuccessful() {
		return lastSuccessful;
	}
	public void setLastSuccessful(String lastSuccessful) {
		this.lastSuccessful = lastSuccessful;
	}
	
	public String getNextRun() {
		return nextRun;
	}
	public void setNextRun(String nextRun) {
		this.nextRun = nextRun;
	}
	
	public String getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}
	
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	public String getStatusSince() {
		return statusSince;
	}
	public void setStatusSince(String statusSince) {
		this.statusSince = statusSince;
	}
	
	public static List<JobStatus> selectAll() {
		
		
		List<JobStatus> js = new ArrayList<JobStatus>();
		js.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			conn = Utility.getConnPostgres();
			
			sql = "select * from jobstatus ";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				js.add(new JobStatus(rs.getString(1),rs.getString(2),
						rs.getString(3),rs.getString(4),rs.getString(5),
						rs.getString(6),rs.getString(7),rs.getString(8)));
				
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		return js;
		
	}
	
}
