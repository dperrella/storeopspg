package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import pkg.beans.ats.AuditItem;
import pkg.beans.ats.Barcode;
import pkg.beans.ats.Item;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetItem")
public class SetItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		String idusr = request.getParameter("idusr");
		String role = ""+request.getParameter("role");
		role =  Utility.noNull(role);
		
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String idvendor = request.getParameter("idvendor");
		String idbrand = request.getParameter("idbrand");
		String idcategory = request.getParameter("idcategory");
		String country = request.getParameter("country");
		String isProtected = request.getParameter("isProtected");
		String evReportLink = request.getParameter("evReportLink");
		String auditId = ""+request.getParameter("auditId");
		auditId = Utility.noNull(auditId);
		String barcodeInFromItems = ""+request.getParameter("barcodeInFromItems");
		barcodeInFromItems = Utility.noNull(barcodeInFromItems);
		
		//
		String sc = request.getParameter("sc");
		
		
		System.out.println(idusr);
		System.out.println(role);
		
		System.out.println(type);
		System.out.println(id);
		System.out.println(name);
		System.out.println(description);
		System.out.println(idvendor);
		System.out.println(idbrand);
		System.out.println(idcategory);
		System.out.println(country);
		System.out.println(isProtected);
		System.out.println(auditId);
		System.out.println(barcodeInFromItems);
		
		
		System.out.println(sc);
		
		
		
		try {
			conn=Utility.getConnData();
			
			Item item = new Item();
		
			switch (type) {
            
            case "I": 
            	
            	item.setIdusr(idusr);
            	item.setName(name);
            	item.setDescription(Utility.noNull(description));
            	item.setIdVendor(Integer.parseInt(idvendor));
            	item.setIdBrand(Integer.parseInt(idbrand));
            	item.setIdCategory(Integer.parseInt(idcategory));
            	item.setCountry(Integer.parseInt(country));
            	item.setItemProtected(Integer.parseInt(isProtected));
            	item.setEvReportLink(evReportLink);
            	item.add(conn);
            	
            	if(auditId.length()>0) {
            		int idItem = item.getIdItem();
            		
            		AuditItem ai = new AuditItem();
            		ai.setIdAudit(Integer.parseInt(auditId));
                	ai.setIdItem(idItem);
                	ai.add(conn);
                	
            	}
            	
            	if(barcodeInFromItems.length()>0) {
            		int idItem = item.getIdItem();
            		
            		Barcode b = new Barcode();
            		b.setIdItem(idItem);
                	b.setBarcode(barcodeInFromItems);
                	b.add(conn);
                	
            	}
            	
            	break;
            	
            case "E": 
            	
            	item.setIdItem(Integer.parseInt(id));
            	item.setName(name);
            	item.setDescription(description);
            	item.setIdVendor(Integer.parseInt(idvendor));
            	item.setIdBrand(Integer.parseInt(idbrand));
            	item.setIdCategory(Integer.parseInt(idcategory));
            	item.setCountry(Integer.parseInt(country));
            	item.setItemProtected(Integer.parseInt(isProtected));
            	item.setEvReportLink(evReportLink);
            	item.update(conn);
            	
            	break;
            
            case "S": 
            	
            	item.setIdItem(Integer.parseInt(id));
            	item.setItemProtected(Integer.parseInt(isProtected));
            	item.updateStatus(conn);
            	
            	break;
            
            case "D": 
            	
            	item.setIdItem(Integer.parseInt(id));
            	item.delete(conn);
	
            	break;
			}
			
			// Set itemCustomer
			
			try {
				if(sc!=null) {
					if(sc.length()>0) {
						
						ArrayList<Integer> customers = new ArrayList<Integer>();
						String temp[] = sc.split(",");
						for(String cus : temp) {
							
							customers.add(Integer.valueOf(cus));
							
						}
						
						Item.setCustomerItems(conn, item.getIdItem(), customers);
						
					}
				}
			}
			catch(Exception e) {}
			
			
			
			responseJson.put("idItem", item.getIdItem());
			
			
			/*
			ArrayList<Item> itemList = Item.getItems(conn, Integer.parseInt(isProtected));
			String json = new Gson().toJson(itemList);
			*/
			
//			Object object=null;
//			JSONArray arrayObj=null;
//			JSONParser jsonParser=new JSONParser();
//			object=jsonParser.parse(json);
//			arrayObj=(JSONArray) object;
//			
//			JSONObject jsonObj = new JSONObject();
//			responseJson.put("listings", arrayObj);
			

			
			if(type.equals("D")||type.equals("S")) {
				JSONArray jsonArray = new JSONArray();
				
				ArrayList<Item> itemm = new ArrayList<Item>();
				
				if(Integer.parseInt(isProtected)==0)
					itemm = Item.getUserRoleItems(conn, 0, idusr, role);
				else
					itemm = Item.getItemsByUserRole2(conn, "1,2", idusr, role);
				
				for(int i=0;i<itemm.size();i++) {
					JSONObject jsonObj = new JSONObject();
					
					jsonObj.put("id", itemm.get(i).getIdItem());
					jsonObj.put("name", itemm.get(i).getName());
					jsonObj.put("description", Utility.noNull(itemm.get(i).getDescription()));
					jsonObj.put("idvendor", itemm.get(i).getIdVendor());
					jsonObj.put("idbrand", itemm.get(i).getIdBrand());
					jsonObj.put("idcategory", itemm.get(i).getIdCategory());
					jsonObj.put("country", itemm.get(i).getCountry());
					jsonObj.put("protected", itemm.get(i).getItemProtected());
					jsonObj.put("evreportlink", itemm.get(i).getEvReportLink());
					jsonObj.put("vendorname", itemm.get(i).getVendorName());
					jsonObj.put("brandname", itemm.get(i).getBrandName());
					jsonObj.put("categoryname", itemm.get(i).getCategoryName());
					jsonObj.put("countryname", itemm.get(i).getCountryName());
					jsonObj.put("barcodes", itemm.get(i).getBarcodes());
					jsonObj.put("retailers", itemm.get(i).getRetailers().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", ""));
					
					
					jsonArray.add(jsonObj);
				}
				
				responseJson.put("listings", jsonArray);
			}
			
			String sql   = "select i.id, i.name, i.description,\n" + 
					"i.idvendor, i.idbrand, i.idcategory,\n" + 
					"i.country, i.evolution_report_link,\n" + 
					"v.name as vendorname, b.name as brandname,\n" + 
					"c.name as categoryname, n.name as countryname,protected,\n" + 
					"	CASE\n" + 
					"		WHEN json_agg(x.*)::text = '[null]'::text THEN '[]'::json\n" + 
					"		ELSE json_agg(x.*)\n" + 
					"	END AS barcode ,\n" + 
					"	CASE\n" + 
					"		WHEN (select json_agg(y.*) from ats.photo y where y.iditem = i.id)  is null THEN '[]'::json\n" + 
					"		ELSE (select json_agg(Y.*) from ats.photo y where y.iditem = i.id) \n" + 
					"	END AS photo, "+
					"	CASE\n" + 
					"		WHEN (select json_agg(z.idcustomer) from ats.rel_item_customer z where z.iditem = i.id)  is null THEN '[]'::json\n" + 
					"		ELSE (select json_agg(z.idcustomer) from ats.rel_item_customer z where z.iditem = i.id) \n" + 
					"	END AS customers "+
					"from ats.items i\n" + 
					"left join ats.vendor v on v.id = i.idvendor\n" + 
					"left join ats.brand b on b.id = i.idbrand\n" + 
					"left join ats.category c on c.id = i.idcategory\n" + 
					"left join ats.country n on n.id = i.country\n" + 
					"LEFT JOIN ats.barcode x ON x.iditem = i.id\n" + 
					"where i.id = ? \n" + 
					"group by i.id, i.name, i.description,\n" + 
					"i.idvendor, i.idbrand, i.idcategory,\n" + 
					"i.country, i.evolution_report_link,protected,\n" + 
					"v.name, b.name,\n" + 
					"c.name, n.name ";
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, item.getIdItem());
			System.out.println(st.toString());
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {
				
				/*
				jsonObj.put("id", rs.getInt("id"));
				jsonObj.put("name", rs.getString("name"));
				jsonObj.put("description", Utility.noNull(rs.getString("description")));
				jsonObj.put("idvendor", rs.getInt("idvendor"));
				jsonObj.put("idbrand", rs.getInt("idbrand"));
				jsonObj.put("idcategory", rs.getInt("idcategory"));
				jsonObj.put("country", rs.getInt("country"));
				jsonObj.put("evolution_report_link", rs.getString("evolution_report_link"));
				jsonObj.put("vendorname", rs.getString("vendorname"));
				jsonObj.put("brandname", rs.getString("brandname"));
				jsonObj.put("categoryname", rs.getString("categoryname"));
				jsonObj.put("countryname", rs.getString("countryname"));
				*/
				responseJson.put("barcode", rs.getObject("barcode"));
				
				/*
				jsonObj.put("photo", rs.getObject("photo"));
				jsonObj.put("customers", rs.getObject("customers"));
				jsonObj.put("isProtected", rs.getInt("protected"));
				*/
				
			}
			rs.close();
			st.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		responseJson.put("status", msg);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
