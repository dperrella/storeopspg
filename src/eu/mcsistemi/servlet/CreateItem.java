package eu.mcsistemi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

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
import eu.mcsistemi.dao.PhotoDAO;
import eu.mcsistemi.dao.PhotoDAOImpl;
import eu.mcsistemi.model.Database;
import eu.mcsistemi.model.Item;
import eu.mcsistemi.model.Photo;
import net.sf.jasperreports.data.RewindableDataSourceCollection;
import pkg.utility.Utility;

@WebServlet("/CreateItem")
public class CreateItem extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException{
		ItemDAO itemDAO = new ItemDAOImpl();
		int checkItem = 0, checkUserItem = 0;
		PhotoDAO photoDAO = new PhotoDAOImpl();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Connection connection = Utility.getConnData();
		
//		System.out.println(req.getParameter("jsonParameters"));
		
		
		ItemInfo itemInfo = gson.fromJson(req.getParameter("jsonParameters"), ItemInfo.class);
		//System.out.println(itemInfo.toString());
		boolean itemCreation = false, photosCreation = true;
//		for(Photo p:itemInfo.getPhotos()){
//			System.out.println(p.getBarcode()+" "+p.getName()+" "+p.getPhotoId()+" "+p.getReference()+" "+p.getUrl());
//		}
		Item item = new Item(itemInfo.getBarcode(),itemInfo.getName(),itemInfo.getDescription(),
							 itemInfo.getVendor(), itemInfo.getBrand(), itemInfo.getCountry(),
							 itemInfo.getProtectionType(),itemInfo.getEvolutionReportLink(), itemInfo.getPhotos());
		try{
			
//			checkItem = itemDAO.checkExistItem(database.getConnection(), item.getBarcode());
//			if(checkItem > 0){
//				checkUserItem = itemDAO.checkUserItem(database.getConnection(), item.getBarcode());
//				if(checkUserItem == 0)
				//itemCreation = itemDAO.createItemUserConnection(connection, itemInfo.getUserId(), item.getBarcode());
//			}else{
				itemCreation = itemDAO.createItem(connection, item, itemInfo.getUserId(),itemInfo.getStoreId());
//				if(itemCreation)
				itemCreation = itemDAO.createItemUserConnection(connection, itemInfo.getUserId(), item.getBarcode());
//			}
//			if(itemCreation)
				itemCreation = itemDAO.createItemStoreConnection(connection, itemInfo.getStoreId(), item.getBarcode());
			if(item.getPhotos() != null)
			   photosCreation = photoDAO.createPhotos(connection, item.getPhotos());
			if(itemCreation && photosCreation){
				if(req.getParameter("web") != null)
					res.getWriter().append(gson.toJson(item));
				else
					res.getWriter().append("{"
							+ "\"response\": \"Ok\","
							+ "\"barcode\": \""+item.getBarcode()+"\""
							+ "}");	
			}
//			else
			
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
	class ItemInfo {
		 private String userId;
		 private int storeId;
		 private String barcode;
		 private String name;
		 private String description;
		 private String vendor;
		 private String brand;
		 private String country;
		 private String protectionType;
		 private String evolutionReportLink;
		 ArrayList<Photo> photos;


		 // Getter Methods 

		 public String getUserId() {
		  return userId;
		 }

		 public int getStoreId() {
		  return storeId;
		 }

		 public String getBarcode() {
		  return barcode;
		 }

		 public String getName() {
		  return name;
		 }

		 public String getDescription() {
		  return description;
		 }

		 public String getVendor() {
		  return vendor;
		 }

		 public String getBrand() {
		  return brand;
		 }

		 public String getCountry() {
		  return country;
		 }

		 public String getProtectionType() {
		  return protectionType;
		 }

		 public String getEvolutionReportLink() {
		  return evolutionReportLink;
		 }

		 // Setter Methods 

		 public void setUserId(String userId) {
		  this.userId = userId;
		 }

		 public void setStoreId(int storeId) {
		  this.storeId = storeId;
		 }

		 public void setBarcode(String barcode) {
		  this.barcode = barcode;
		 }

		 public void setName(String name) {
		  this.name = name;
		 }

		 public void setDescription(String description) {
		  this.description = description;
		 }

		 public void setVendor(String vendor) {
		  this.vendor = vendor;
		 }

		 public void setBrand(String brand) {
		  this.brand = brand;
		 }

		 public void setCountry(String country) {
		  this.country = country;
		 }

		 public void setProtectionType(String protectionType) {
		  this.protectionType = protectionType;
		 }

		 public void setEvolutionReportLink(String evolutionReportLink) {
		  this.evolutionReportLink = evolutionReportLink;
		 }
		 public String toString(){
				StringBuilder sb = new StringBuilder();
				sb.append("***** Employee Details *****\n");
				sb.append("USERID="+getUserId()+"\n");
				sb.append("StoreID="+getStoreId()+"\n");
				sb.append("Name="+getName()+"\n");
				sb.append("Description="+getDescription()+"\n");
				sb.append("Vendor="+getVendor()+"\n");
				sb.append("Brand="+getBrand()+"\n");
				sb.append("Country="+getCountry()+"\n");
				sb.append("ProtectionType="+getProtectionType()+"\n");
				sb.append("EvolutionReportLink="+getEvolutionReportLink()+"\n");
				sb.append("*****************************");
				return sb.toString();
		}
	
	public ArrayList<Photo> getPhotos() {
			return photos;
		}

		public void setPhotos(ArrayList<Photo> photos) {
			this.photos = photos;
		}
	}

}
