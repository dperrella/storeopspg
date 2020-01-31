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
import eu.mcsistemi.dao.ItemDAO;
import eu.mcsistemi.dao.ItemDAOImpl;
import eu.mcsistemi.model.Database;
import eu.mcsistemi.model.Item;
import pkg.utility.Utility;

@WebServlet("/GetStoreItems")
public class GetStoreItems extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		int storeId = Integer.parseInt(req.getParameter("storeId"));
		Connection connection = Utility.getConnData();
		ItemDAO itemDao = new ItemDAOImpl();
		List<Item> itemsList = new LinkedList<>(itemDao.getItemsByStoreId(connection, storeId));
		JsonArray arrayJson = new JsonArray();
		JsonObject masterObj = new JsonObject();
		for(Item item: itemsList){
			JsonObject tempObject = new JsonObject();
			tempObject.addProperty("barcode",item.getBarcode());
			tempObject.addProperty("name",item.getName());
			tempObject.addProperty("description",item.getDescription());
			tempObject.addProperty("vendor",item.getVendor());
			tempObject.addProperty("brand",item.getBrand());
			tempObject.addProperty("country",item.getCountry());
			tempObject.addProperty("protected",item.getProtectionType());
			tempObject.addProperty("evolutionReportLink",item.getEvolutionReportLink());
			arrayJson.add(tempObject);
		}
		masterObj.add("Items", arrayJson);
		
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().append(masterObj.toString());
		
		//System.out.println(masterObj.toString());
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
