package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
import pkg.beans.rfid.Readers;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/GetItemsRequest")
public class GetItemsRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetItemsRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		String idusr = ""+request.getParameter("idusr");
		idusr = Utility.noNull(idusr);
		String role = ""+request.getParameter("role");
		role = Utility.noNull(role);
		String idwishlist = ""+request.getParameter("idwishlist");
		idwishlist = Utility.noNull(idwishlist);
		
		List<Item> item = new ArrayList<Item>();
		item.clear();
		
		try {
			conn=Utility.getConnData();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			if(idusr.length()==0)
				item = Item.getItems(conn, 0);
			else
				item = Item.getUserRoleItemsWish(conn, 0, idusr, role, idwishlist);
			
			for(int i=0;i<item.size();i++) {
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("id", item.get(i).getIdItem());
				jsonObj.put("name", item.get(i).getName());
				jsonObj.put("idusr", item.get(i).getIdusr());
				jsonObj.put("description", item.get(i).getDescription());
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
				jsonObj.put("idusr", item.get(i).getIdusr());
				jsonObj.put("wish", item.get(i).getWishlistName());
				jsonObj.put("photos", item.get(i).getPhotos().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", ""));
				
				
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
				
				jsonArray.add(jsonObj);
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
