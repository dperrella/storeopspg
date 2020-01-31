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
@WebServlet("/ats/SelectItem")
public class SelectItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectItem() {
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
		
		System.out.println("idcustomer: "+idcustomer);
		
		List<Item> item = new ArrayList<Item>();
		item.clear();
		
		try {
			conn=Utility.getConnData();
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			if(idcustomer.length()==0) {
				item = Item.getItems(conn, 1);
			}
			else {
				item = Item.getItemsByCustomer(conn, 1, Integer.parseInt(idcustomer));
			}
			for(int i=0;i<item.size();i++) {
				JSONObject jsonObj = new JSONObject();
			
				jsonObj.put("key", item.get(i).getIdItem());
				jsonObj.put("value", item.get(i).getIdItem());
				jsonObj.put("label", item.get(i).getName()+" "+item.get(i).getDescription());
				
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
