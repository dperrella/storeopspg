package eu.mcsistemi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.mcsistemi.Utility.ExceptionHandler;
import eu.mcsistemi.Utility.ExceptionHandler.Errors;
import eu.mcsistemi.dao.AuditDAO;
import eu.mcsistemi.dao.AuditDAOImpl;
import eu.mcsistemi.dao.AuditItemDAO;
import eu.mcsistemi.dao.AuditItemDAOImpl;
import eu.mcsistemi.model.Audit;
import eu.mcsistemi.model.AuditItem;
import eu.mcsistemi.model.Database;
import pkg.utility.Utility;

@WebServlet("/UpdateAudit")
public class UpdateAudit extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Connection connection = Utility.getConnData();
		AuditDAO auditDao = new AuditDAOImpl();
		AuditItemDAO auditItemDAO = new AuditItemDAOImpl();
		//System.out.println(req.getParameter("jsonParameters"));
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		UpdateAuditInfo updateAuditInfo = gson.fromJson(req.getParameter("jsonParameters"), UpdateAuditInfo.class);
		
		boolean auditItemsUpdate = true, auditUpdate = false;
		Audit audit = auditDao.getAuditById(connection, updateAuditInfo.getAuditId());
		audit.setClosingDate(updateAuditInfo.getClosingDate());
		audit.setState(updateAuditInfo.getState());
		audit.setAuditItem(updateAuditInfo.getAuditItems());
//		for(AuditItem p: updateAuditInfo.getAuditItems()){
//			System.out.println(p.getBarcode()+" "+p.getName());
//		}
		try{
			auditUpdate = auditDao.updateAudit(connection, audit);
			if(audit.getAuditItems() != null)
				auditItemsUpdate = auditItemDAO.updateAuditItems(connection, audit.getAuditItems(), audit.getAuditId());
			if(auditUpdate && auditItemsUpdate)
				res.getWriter().append("{"
						+ "\"response\": \"Ok\""
						+ "}");	
		}catch(Exception e){
			e.printStackTrace();
			ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();
			Errors error = exceptionHandler.getErrorCode(e.toString());
			System.out.println(error.toString());
			res.getWriter().append("{"
					+ "\"response\": \"Ko\","
					+ "\"errorCode\": \""+error.printCode()+"\","
					+ "\"errorDescription\": \""+error.toString()+"\""
					+ "}");	
		}
		finally {
			try{
				connection.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	class UpdateAuditInfo{
		private String state;
		private Date closingDate;
		private ArrayList<AuditItem> auditItems;
		private int auditId;
		
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public Date getClosingDate() {
			return closingDate;
		}
		public void setClosingDate(Date closingDate) {
			this.closingDate = closingDate;
		}
		public ArrayList<AuditItem> getAuditItems() {
			return auditItems;
		}
		public void setAuditItems(ArrayList<AuditItem> auditItems) {
			this.auditItems = auditItems;
		}
		public int getAuditId() {
			return auditId;
		}
		public void setAuditId(int auditId) {
			this.auditId = auditId;
		}
		
	}
}
