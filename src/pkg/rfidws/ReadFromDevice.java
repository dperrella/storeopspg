package pkg.rfidws;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_config;
import pkg.beans.Rfid_devices;
import pkg.beans.Rfid_step;
import pkg.utility.Utility;
import pkg.utility.HttpHandler;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/ReadFromDevice")
public class ReadFromDevice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadFromDevice() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement st = null;
		
		String urlws = request.getParameter("urlws");
		String parws = request.getParameter("parws");
		String par = "";
		
		String temp[] = parws.split(";");
		for(int i=0;i<temp.length;i++) {
			par+=temp[i]+"&";
		}
		if(par.length()>0)
			par=par.substring(0,par.length()-1);
		
		int idflow = Integer.parseInt(request.getParameter("idflow"));
		int idstep = Integer.parseInt(request.getParameter("idstep"));
		String iddoc = request.getParameter("iddoc");
		
		System.out.println(urlws+"?"+par);
		
		try {
			conn=Utility.getConnRfid();
			st=conn.createStatement();
			
			HttpHandler sh = new HttpHandler();

			// Scrive Scanner
			String url = "http://localhost:8080/SmartSuite/rfid/ws/Scanner?idflow="+idflow+"&idstep="+idstep+"&iddoc="+iddoc;
			String jsonStr = sh.makeServiceCall(url);
			
			JSONParser parser = new JSONParser();
			Object _obj = parser.parse(jsonStr.toString());
			JSONObject responseJson = (JSONObject) _obj;
			
            String idscan = ""+responseJson.get("idscan");
            System.out.println(idscan);
            
            
            // Richiamo iil ws che mi restituisce i tags
            url = urlws+"?"+par;
            System.out.println(url);
			jsonStr = sh.makeServiceCall(url);
			
			parser = new JSONParser();
			_obj = parser.parse(jsonStr.toString());
			responseJson = (JSONObject) _obj;
			System.out.println(jsonStr);
			
			JSONArray data = (JSONArray) responseJson.get("listings");
			if(data.size()==0) {
				System.out.println("lista vuota");
			}
			else {
				System.out.println("lista piena");
				
				String arrayTags = "";
				
				Iterator<String> iterator2 = data.iterator();
				while (iterator2.hasNext()) {
				
					Object obj3 = iterator2.next();
				    JSONObject jsonObject3 = (JSONObject) obj3;
				    
				    String tag = (String) jsonObject3.get("tag");
				    if(tag.trim().length()==24)
				    	arrayTags+=(String) jsonObject3.get("tag")+",";
				    
				}
				
				System.out.println(".....");
				System.out.println(arrayTags);
				System.out.println(".....");
				
				
				if(arrayTags.length()>0)
					arrayTags = arrayTags.substring(0,arrayTags.length()-1);
				
				// Scrive Scanner Detail
	            url = "http://localhost:8080/SmartSuite/rfid/ws/ScannerDetail";
	            Utility.sendPost(url, "idscan="+idscan+"&package_id=&epc="+arrayTags);
	            
			}
			
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
				if(st!=null)
					st.close();
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
