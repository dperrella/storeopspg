package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import pkg.beans.ats.Photo;
import pkg.beans.ats.Photo2;
import pkg.beans.rfid.Readers;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/GetItem")
public class GetItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			conn=Utility.getConnData();
			
			JSONObject responseJson = new JSONObject();
			
			String idItem = request.getParameter("idItem");
			
			sql   = "select i.id, i.name, i.description,\n" + 
					"i.idvendor, i.idbrand, i.idcategory,\n" + 
					"i.country, i.idwishlist, i.evolution_report_link, i.note, \n" + 
					"v.name as vendorname, b.name as brandname,\n" + 
					"c.name as categoryname, n.name as countryname, w.name as wishlistname, protected,\n" + 
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
					"left join ats.wishlist w on w.id = i.idwishlist\n" + 
					"LEFT JOIN ats.barcode x ON x.iditem = i.id\n" + 
					"where i.id = ? \n" + 
					"group by i.id, i.name, i.description,\n" + 
					"i.idvendor, i.idbrand, i.idcategory,\n" + 
					"i.country, i.idwishlist, i.evolution_report_link,protected,note,\n" + 
					"v.name, b.name,\n" + 
					"c.name, n.name, w.name  ";
			
			st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(idItem));
			System.out.println(st.toString());
			rs=st.executeQuery();
			
			JSONObject jsonObj = new JSONObject();
			
			if(rs.next()) {
				
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
				jsonObj.put("barcode", rs.getObject("barcode"));
				
				
				/*
				ArrayList<Photo2> ph2 = new ArrayList<Photo2>();
				ph2 = Photo2.getPhotoByItem(conn, rs.getInt("id"));
 				
				JSONArray jsonArray = new JSONArray();
				for(Photo2 p : ph2) {
					JSONObject ph = new JSONObject();
					ph.put("title",rs.getString("name"));
					ph.put("caption",Utility.noNull(rs.getString("description")));
					ph.put("url",p.getUrl());
					jsonArray.add(ph);
				}
				jsonObj.put("photo", jsonArray);
				*/
				
				jsonObj.put("photo", rs.getObject("photo"));
				jsonObj.put("customers", rs.getObject("customers"));
				jsonObj.put("isProtected", rs.getInt("protected"));
				
				jsonObj.put("idwishlist", rs.getInt("idwishlist"));
				jsonObj.put("wishlistname", rs.getString("wishlistname"));
				
				jsonObj.put("note", rs.getString("note"));
				
				
			}
			rs.close();
			st.close();
			
			//responseJson.put("item", jsonObj);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObj.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
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
