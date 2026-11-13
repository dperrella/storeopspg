package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.ats.Item;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/GetItems")
public class GetItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetItems() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		String idcustomer = ""+request.getParameter("idcustomer");
		idcustomer = Utility.noNull(idcustomer);
		
		String idusr = ""+request.getParameter("idusr");
		idusr = Utility.noNull(idusr);
		String role = ""+request.getParameter("role");
		role = Utility.noNull(role);
		String page = ""+request.getParameter("page");
		page = Utility.noNull(page);
		
		
		String idcus = ""+request.getParameter("idcus");
		idcus = Utility.noNull(idcus);
		
		System.out.println("idcustomer: "+idcustomer);
		System.out.println("idusr: "+idusr);
		System.out.println("role: "+role);
		
		List<Item> item = new ArrayList<Item>();
		item.clear();
		
		try {
			conn=Utility.getConnData();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			if(idcustomer.length()==0) {
				if(idusr.length()==0) {
					item = Item.getItems(conn, 1);
				}
				else {
					//item = Item.getItemsByUser(conn, 1, idusr);
					//item = Item.getItemsByUserRole(conn, 1, idusr, role);
					
					if(page.length()==0)
						item = Item.getItemsByUserRole2(conn, "1,2,3", idusr, role);
					else
						item = Item.getItemsByUserRole2(conn, "1,2,3", idusr, role, page);
				}
			}
			else {
				item = Item.getItemsByCustomer(conn, 1, Integer.parseInt(idcustomer));
			}
			
			
			for(int i=0;i<item.size();i++) {
				JSONObject jsonObj = new JSONObject();
				
				
				jsonObj.put("id", item.get(i).getIdItem());
				jsonObj.put("name", item.get(i).getName());
				jsonObj.put("description", Utility.noNull(item.get(i).getDescription()));
				jsonObj.put("idvendor", item.get(i).getIdVendor());
				jsonObj.put("idbrand", item.get(i).getIdBrand());
				jsonObj.put("idcategory", item.get(i).getIdCategory());
				jsonObj.put("country", item.get(i).getCountry());
				jsonObj.put("protected", item.get(i).getItemProtected());
				jsonObj.put("evreportlink", item.get(i).getEvReportLink());
				jsonObj.put("vendorname", item.get(i).getVendorName());
				jsonObj.put("brandname", item.get(i).getBrandName());
				jsonObj.put("categoryname", item.get(i).getCategoryName());
				jsonObj.put("countryname", item.get(i).getCountryName());
				jsonObj.put("barcodes", item.get(i).getBarcodes());
				jsonObj.put("retailers", item.get(i).getRetailers().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", ""));
				jsonObj.put("photos", item.get(i).getPhotos().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", ""));
				jsonObj.put("editable", item.get(i).getEditable());
				
				boolean inErr = false;
			    if(item.get(i).getRetailers().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").length()==0) {
			      inErr=true;
			    }
			    if(item.get(i).getBarcodes().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").length()==0) {
			      inErr=true;
			    }
			    if(item.get(i).getPhotos().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").length()==0) {
			      inErr=true;
			    }
			    
			    jsonObj.put("inErr",inErr);
			    
				if(idcus.length()>0) {
					if(item.get(i).getRetailers().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").contains(idcus)) {
						jsonArray.add(jsonObj);
					}
				}
				else {
					jsonArray.add(jsonObj);
				}
				
				
				
				
				
			}
			
			responseJson.put("listings", jsonArray);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
