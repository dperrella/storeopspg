package eu.mcsistemi.servlet;

import java.io.IOException;
import java.sql.Connection;
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
import eu.mcsistemi.model.Database;
import pkg.utility.Utility;

@WebServlet("/DeleteAudit")
public class DeleteAudit extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Connection connection = Utility.getConnData();
		AuditDAO auditDao = new AuditDAOImpl();
		AuditItemDAO auditItemDAO = new AuditItemDAOImpl();
//		System.out.println(req.getParameter("jsonParameters"));
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		DeleteAuditInfo deleteAuditInfo = gson.fromJson(req.getParameter("jsonParameters"), DeleteAuditInfo.class);
		boolean auditItemsDelete = true, auditDelete = false;
		Audit audit = auditDao.getAuditById(connection, deleteAuditInfo.getAuditId());
//		System.out.println(deleteAuditInfo.toString());	
//		for(AuditItem p: audit.getAuditItems()){
//			System.out.println(p.getBarcode()+" "+p.getName());
//		}
//		System.out.println(audit.getAuditId());
		try{
			auditDelete = auditDao.deleteAudit(connection, audit);
			if(auditDelete && auditItemsDelete)
				res.getWriter().append("{"
						+ "\"response\": \"Ok\""
						+ "}");	
		}catch(Exception e){
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
	class DeleteAuditInfo{
		private int auditId;

		public int getAuditId() {
			return auditId;
		}

		public void setAuditId(int auditId) {
			this.auditId = auditId;
		}
		
	}
}
