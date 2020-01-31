package eu.mcsistemi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.mcsistemi.Utility.ExceptionHandler;
import eu.mcsistemi.Utility.ExceptionHandler.Errors;
import eu.mcsistemi.dao.ItemDAO;
import eu.mcsistemi.dao.ItemDAOImpl;
import eu.mcsistemi.model.Database;
import pkg.utility.Utility;

@WebServlet("/DeleteItem")
public class DeleteItem extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		Connection connection = Utility.getConnData();
		ItemDAO itemDAO = new ItemDAOImpl();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		DeleteItemInfo deleteItemInfo = gson.fromJson(req.getParameter("jsonParameters"), DeleteItemInfo.class);
		//System.out.println(deleteItemInfo.toString());
		boolean itemDeletion = false;

		try {
			itemDeletion = itemDAO.deleteItemReference(connection,deleteItemInfo.getBarcode(),deleteItemInfo.getUserId());
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
				catch(SQLException e){
					e.printStackTrace();
				}
		}
	
	}
	class DeleteItemInfo{
		private String barcode;
		private String userId;
		
		public String getBarcode() {
			return barcode;
		}
		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
	}
}
