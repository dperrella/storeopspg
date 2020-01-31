package eu.mcsistemi.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.mcsistemi.dao.ItemDAO;
import eu.mcsistemi.dao.ItemDAOImpl;
import eu.mcsistemi.dao.PhotoDAO;
import eu.mcsistemi.dao.PhotoDAOImpl;
import eu.mcsistemi.model.Item;
import pkg.utility.Utility;
@WebServlet("/GetItem")
public class GetItem extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res){
		Connection connection = Utility.getConnData();
		ItemDAO itemDAO = new ItemDAOImpl();
		PhotoDAO photoDAO = new PhotoDAOImpl();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Info itemInfo = gson.fromJson(req.getParameter("jsonParameters"), Info.class);
		Item item = itemDAO.getFullItemById(connection, itemInfo.getBarcode());
		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().append(gson2.toJson(item));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	class Info {
		private String barcode;

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}
		
	}
}
