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
import com.sun.glass.ui.CommonDialogs.Type;

import eu.mcsistemi.dao.AuditDAO;
import eu.mcsistemi.dao.AuditDAOImpl;
import eu.mcsistemi.dao.AuditItemDAO;
import eu.mcsistemi.dao.AuditItemDAOImpl;
import eu.mcsistemi.model.Audit;
import eu.mcsistemi.model.AuditItem;
import eu.mcsistemi.model.Database;
import eu.mcsistemi.servlet.CreateItem.ItemInfo;
import pkg.utility.Utility;
import eu.mcsistemi.Utility.DateManager;
import eu.mcsistemi.Utility.ExceptionHandler;
import eu.mcsistemi.Utility.ExceptionHandler.Errors;

@WebServlet("/CreateAudit")
public class CreateAudit extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Connection connection = Utility.getConnData();
		AuditDAO auditDao = new AuditDAOImpl();
		AuditItemDAO auditItemDAO = new AuditItemDAOImpl();
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		AuditInfo auditInfo = gson.fromJson(req.getParameter("jsonParameters"), AuditInfo.class);
		boolean auditItemsCreation = true;
		Audit audit = new Audit(auditInfo.getName(), auditInfo.getType(), auditInfo.getCreationDate(), auditInfo.getState(), auditInfo.getStoreId(), auditInfo.getAuditItems());
//		System.out.println(auditInfo.toString());	
//		for(AuditItem p: auditInfo.getAuditItems()){
//			System.out.println(p.getBarcode()+" "+p.getName());
//		}
		try{
			audit.setAuditId(auditDao.createAudit(connection, audit));
			if(audit.getAuditItems() != null)
				auditItemsCreation = auditItemDAO.createAuditItems(connection, audit.getAuditItems(), audit.getAuditId());
			if(audit.getAuditId()!= 0 && auditItemsCreation){
				Audit auditResponse = auditDao.getAuditById(connection, audit.getAuditId());
				res.getWriter().append("{"
						+ "\"response\": \"Ok\","
						+ "\"auditId\": \""+auditResponse.getAuditId()+"\","
						+ "\"auditName\": \""+auditResponse.getName()+"\""
						+ "}");	
			}
				
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
	enum Type{ST,Alpha,InStore};
	class AuditInfo{
		//name, storeid, creationdate, state, [audititems], type
		private String name;
		private int storeId;
		private Date creationDate;
		private String state;
		private ArrayList<AuditItem> auditItems;
		private Type type;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public int getStoreId() {
			return storeId;
		}
		public void setStoreId(int storeId) {
			this.storeId = storeId;
		}
		
		public Date getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public ArrayList<AuditItem> getAuditItems() {
			return auditItems;
		}
		public void setAuditItems(ArrayList<AuditItem> auditItems) {
			this.auditItems = auditItems;
		}
		public String getType() {
			return type.toString();
		}
		public void setType(String type) {
			this.type = Type.valueOf(type);
		}
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("***** Audit Details *****\n");
			sb.append("StoreID="+getStoreId()+"\n");
			sb.append("Name="+getName()+"\n");
			sb.append("Creation Date="+DateManager.toString(getCreationDate(), DateManager.normalDateformat)+"\n");
			sb.append("State="+getState()+"\n");
			sb.append("Type="+getType()+"\n");
			sb.append("*****************************");
			return sb.toString();
	}
	}
}
