package pkg.servlet.ats;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import pkg.beans.ats.Barcode;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/SetBarcode")
public class SetBarcode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetBarcode() {
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
		
		String type = request.getParameter("type");
		String idItem = request.getParameter("idItem");
		String id = request.getParameter("id");
		String barcode = request.getParameter("barcode");
		
		try {
			conn=Utility.getConnData();
			
			Barcode b = new Barcode();
		
			switch (type) {
            
            case "I": 
            	
            	b.setIdItem(Integer.parseInt(idItem));
            	b.setBarcode(barcode);
            	b.add(conn);
            	
            	break;
            	
            case "E": 
            	
            	b.setId(Integer.parseInt(id));
            	b.setBarcode(barcode);
            	b.update(conn);
            	
            	break;
            	
            case "D": 
            	
            	b.setId(Integer.parseInt(id));
            	b.delete(conn);
	
            	break;
			}
			
			ArrayList<Barcode> barcodeList = Barcode.getBarcodeByItem(conn, Integer.parseInt(idItem));
			String json = new Gson().toJson(barcodeList);
			
			
			Object object=null;
			JSONArray arrayObj=null;
			JSONParser jsonParser=new JSONParser();
			object=jsonParser.parse(json);
			arrayObj=(JSONArray) object;
			
			JSONObject jsonObj = new JSONObject();
			responseJson.put("barcodeList", arrayObj);
			
			
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
