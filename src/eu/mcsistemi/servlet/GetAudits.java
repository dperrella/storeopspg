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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eu.mcsistemi.dao.AuditDAO;
import eu.mcsistemi.dao.AuditDAOImpl;
import eu.mcsistemi.model.Database;
import pkg.utility.Utility;
import eu.mcsistemi.Utility.DateManager;
import eu.mcsistemi.model.Audit;

@WebServlet("/GetAudits")
public class GetAudits extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		int storeId = Integer.parseInt(req.getParameter("storeId"));
		//Database database = Database.getInstance();
		Connection conn = Utility.getConnData();
		AuditDAO auditDao = new AuditDAOImpl();
		//List<Audit> auditList = new LinkedList<>(auditDao.getAuditsByStoreId(database.getConnection(), storeId));
		List<Audit> auditList = new LinkedList<>(auditDao.getAuditsByStoreId(conn, storeId));
		JsonArray arrayJson = new JsonArray();
		JsonObject masterObj = new JsonObject();
		for(Audit audit: auditList){
			JsonObject tempObject = new JsonObject();
			tempObject.addProperty("auditId",audit.getAuditId());
			tempObject.addProperty("name",audit.getName());
			tempObject.addProperty("creationDate",DateManager.toString(audit.getCreationDate(), DateManager.normalDateformat));
			tempObject.addProperty("closingDate",DateManager.toString(audit.getClosingDate(), DateManager.normalDateformat));
			tempObject.addProperty("state",audit.getState());
			arrayJson.add(tempObject);
		}
		masterObj.add("Audits", arrayJson);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().append(masterObj.toString());
		
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
