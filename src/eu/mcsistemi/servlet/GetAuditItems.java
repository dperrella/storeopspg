package eu.mcsistemi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eu.mcsistemi.dao.AuditItemDAO;
import eu.mcsistemi.dao.AuditItemDAOImpl;
import eu.mcsistemi.model.AuditItem;
import eu.mcsistemi.model.Database;
import pkg.utility.Utility;
import eu.mcsistemi.Utility.DateManager;

@WebServlet("/GetAuditItems")
public class GetAuditItems extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Connection connection = Utility.getConnData();
		int auditId = Integer.parseInt(req.getParameter("auditId"));
		AuditItemDAO auditItemDao = new AuditItemDAOImpl();
		List<AuditItem> auditItemsList = new LinkedList<>(auditItemDao.getAuditItemByAuditId(connection, auditId));
		JsonArray arrayJson = new JsonArray();
		JsonObject masterObj = new JsonObject();
		for(AuditItem auditItem: auditItemsList){
			JsonObject tempObject = new JsonObject();
			tempObject.addProperty("barcode",auditItem.getBarcode());
			tempObject.addProperty("name",auditItem.getName());
			tempObject.addProperty("description",auditItem.getDescription());
			tempObject.addProperty("vendor",auditItem.getVendor());
			tempObject.addProperty("brand",auditItem.getBrand());
			tempObject.addProperty("country",auditItem.getCountry());
			tempObject.addProperty("protected",auditItem.getProtectionType());
			tempObject.addProperty("evolutionReportLink",auditItem.getEvolutionReportLink());
			tempObject.addProperty("lotsNumber",auditItem.getLotsNumber());
			tempObject.addProperty("auditResult", auditItem.getAuditResult());
			tempObject.addProperty("note", auditItem.getNote());
			tempObject.addProperty("lot", auditItem.getLot());
			tempObject.addProperty("expiry", DateManager.toString(auditItem.getExpiry(),DateManager.normalDateformat));
			arrayJson.add(tempObject);
		}
		masterObj.add("AuditItems", arrayJson);
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().append(masterObj.toString());
		
		if(connection != null){
			try{
				connection.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
