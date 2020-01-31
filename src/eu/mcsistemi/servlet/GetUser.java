package eu.mcsistemi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import eu.mcsistemi.Utility.DateManager;
import eu.mcsistemi.Utility.ExceptionHandler;
import eu.mcsistemi.Utility.ExceptionHandler.Errors;
import eu.mcsistemi.dao.UserDAO;
import eu.mcsistemi.dao.UserDAOImpl;
import eu.mcsistemi.model.User;
import pkg.utility.Utility;


@WebServlet("/GetUser")
public class GetUser extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
		//			int userId = Integer.parseInt(req.getParameter("userId"));
//			
//			Database database = Database.getInstance();
//			database.openConnection();
//			Connection connection = database.getConnection();
//			
//			UserDAO userDao = new UserDAOImpl();
//			StoreDAO storeDao = new StoreDAOImpl();
//			AuditDAO auditDAO = new AuditDAOImpl();
//			AuditItemDAO auditItemDao = new AuditItemDAOImpl();
//			PhotoDAO photoDao = new PhotoDAOImpl();
//			ItemDAO itemDao = new ItemDAOImpl();
//			
//			JsonObject user = new JsonObject();
//			JsonArray stores = new JsonArray();
//			
//			JsonArray items = new JsonArray();
//			JsonObject masterUser = new JsonObject();
//			User u = userDao.getUserById(connection, userId);
//			user.addProperty("userId", u.getUserId());
//			user.addProperty("name", u.getName());
//			user.addProperty("surname", u.getSurname());
//			
//			List<Store> storeList = new LinkedList<>(storeDao.getStoresByUserId(connection, userId));
//			if(storeList != null){
//				for(Store store : storeList){
//					JsonObject st = new JsonObject();
//					JsonArray audits = new JsonArray();
//					st.addProperty("storeId", store.getStoreId());
//					st.addProperty("name", store.getName());
//					st.addProperty("description", store.getDescription());
//					st.addProperty("idCustomer", store.getIdCustomer());
//					
//					List<Audit> auditList = new LinkedList<>(auditDAO.getAuditsByStoreId(connection, store.getStoreId()));
//					if(auditList != null){
//						for(Audit audit:auditList){
//							JsonObject au = new JsonObject();
//							JsonArray auditItems = new JsonArray();
//							au.addProperty("auditId",audit.getAuditId());
//							au.addProperty("name",audit.getName());
//							au.addProperty("creationDate",DateManager.toString(audit.getCreationDate(), DateManager.importexportDateFormat));
//							au.addProperty("closingDate",DateManager.toString(audit.getClosingDate(), DateManager.importexportDateFormat));
//							au.addProperty("state",audit.getState());
//							
//							List<AuditItem> auditItemList = new LinkedList<>(auditItemDao.getAuditItemByAuditId(connection, audit.getAuditId()));
//							if(auditItemList != null){
//								for(AuditItem auditItem: auditItemList){
//									JsonObject ai = new JsonObject();
//									JsonArray photos = new JsonArray();
//									ai.addProperty("barcode",auditItem.getBarcode());
//									ai.addProperty("name",auditItem.getName());
//									ai.addProperty("description",auditItem.getDescription());
//									ai.addProperty("vendor",auditItem.getVendor());
//									ai.addProperty("brand",auditItem.getBrand());
//									ai.addProperty("country",auditItem.getCountry());
//									ai.addProperty("protected",auditItem.getProtectionType());
//									ai.addProperty("evolutionReportLink",auditItem.getEvolutionReportLink());
//									ai.addProperty("lotNumber",auditItem.getLotNumber());
//									
//									List<Photo> photoList1 = new LinkedList<>(photoDao.getPhotosByBarcode(connection, auditItem.getBarcode()));
//									if(photoList1 != null){
//										for(Photo photo :photoList1){
//											JsonObject ph1 = new JsonObject();
//											ph1.addProperty("photo_id", photo.getPhotoId());
//											ph1.addProperty("name", photo.getName());
//											ph1.addProperty("url", photo.getUrl());
//											ph1.addProperty("reference", photo.getReference());
//											photos.add(ph1);
//										}
//									}
//									ai.add("photos", photos);
//									ai.addProperty("auditResult", auditItem.getAuditResult());
//									auditItems.add(ai);
//								}
//							}
//							au.add("auditItems", auditItems);
//							audits.add(au);
//						}
//					}
//					st.add("audits",audits);
//					stores.add(st);
//				}
//			}
//			List<Item> itemList = new LinkedList<>(itemDao.getItemsByUserId(connection, u.getUserId()));
//			if(itemList != null){
//				for(Item item: itemList){
//					JsonObject itl = new JsonObject();
//					JsonArray photos = new JsonArray();
//					itl.addProperty("barcode",item.getBarcode());
//					itl.addProperty("name",item.getName());
//					itl.addProperty("description",item.getDescription());
//					itl.addProperty("vendor",item.getVendor());
//					itl.addProperty("brand",item.getBrand());
//					itl.addProperty("country",item.getCountry());
//					itl.addProperty("protected",item.getProtectionType());
//					itl.addProperty("evolutionReportLink",item.getEvolutionReportLink());
//					itl.addProperty("lotNumber",item.getLotNumber());
//					
//					List<Photo> photoList2 = new LinkedList<>(photoDao.getPhotosByBarcode(connection, item.getBarcode()));
//					if(photoList2 != null){
//						for(Photo photo :photoList2){
//							JsonObject ph2 = new JsonObject();
//							ph2.addProperty("photo_id", photo.getPhotoId());
//							ph2.addProperty("name", photo.getName());
//							ph2.addProperty("url", photo.getUrl());
//							ph2.addProperty("reference", photo.getReference());
//							photos.add(ph2);
//						}
//					}
//					itl.add("photos", photos);
//					items.add(itl);
//				}
//			}
//			user.add("stores",stores);
//			user.add("items", items);
//			u.setCustomer(new LinkedList<>(customerDao.getCustomersByUserId(connection,u.getUserId()));
//			user.addProperty("customer", u.getCustomer());
//			user.addProperty("role", u.getRole());
//			masterUser.add("user", user);
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			res.setContentType("application/json");
//			res.setCharacterEncoding("UTF-8");
//			res.getWriter().append(masterUser.toString());
//			
		
		System.out.println("1:"+DateManager.toString(new Date(),new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS")));
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		System.out.println(userId+password);
		
		//Database database = Database.getInstance();
		//database.openConnection();
		//Connection connection = database.getConnection();
		Connection connection = Utility.getConnData();
		UserDAO userDao = new UserDAOImpl();
		try{
			System.out.println("2:"+DateManager.toString(new Date(),new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS"))+" precall");
			User u = userDao.getFullUserById(connection, userId, password);
			//System.out.println( gson.toJson(u));  
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			
			System.out.println(DateManager.toString(new Date(),new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS"))+" gson (start)");
			
			JsonParser jsonParser = new JsonParser();
			JsonArray customersArray = jsonParser.parse(u.getCustomers()).getAsJsonArray();
			JsonArray itemsArray = jsonParser.parse(u.getItems()).getAsJsonArray();
			JsonObject user = new JsonObject();
			user.addProperty("userId", u.getUserId());
			user.addProperty("name", u.getName());
			user.addProperty("surname", u.getSurname());
			user.addProperty("role", u.getRole());
			user.add("customers", customersArray);
			user.add("items", itemsArray);
			//String json = gson.toJson(u);
			
			System.out.println(DateManager.toString(new Date(),new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS"))+" gson (end)");
			
			//res.getWriter().append(json);
			res.getWriter().append(user.toString());
			
			System.out.println(DateManager.toString(new Date(),new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS"))+" getWriter");

			System.out.println(DateManager.toString(new Date(),new SimpleDateFormat("dd-MM-yy HH:mm:ss.SSS")));
		}catch(Exception e){
			res.getWriter().append("{"
					+ "\"response\": \"Ko\","
					+ "\"errorCode\": \""+Errors.wrongUserOrPassword.printCode()+"\","
					+ "\"errorDescription\": \""+Errors.wrongUserOrPassword.toString()+"\""
					+ "}");	
		}
		finally{
			try{
				connection.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		

		
		
		
	}
		
}
