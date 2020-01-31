package pkg.rfidws;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;

import pkg.utility.ScanRfid;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/ImpinjRead")
public class ImpinjRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImpinjRead() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static List tags  = new ArrayList();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String ip = request.getParameter("ip");	
		
		try {
			
			// Legge i Nuovi tags e li inserisce nella lista
			ScanRfid.scan(ip);
			
			// Pagina la lista e la inserisce in un array
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			if (tags != null)
			{
				
				Iterator i = tags.iterator();
				
				while (i.hasNext())
				{
					String element = (String) i.next();
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("tag", element.replaceAll(" ", ""));
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
}
