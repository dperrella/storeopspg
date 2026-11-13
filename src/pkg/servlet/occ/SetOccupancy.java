package pkg.servlet.occ;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/occupancy/setOccupancy")
public class SetOccupancy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String APIENDPOINT = "http://apickp.azurewebsites.net/storeops/";
	private static final String APIUSERNAME = "develop";
	private static final String APIPASSWORD = "Aprtssm$1234";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetOccupancy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String loginForm = "{\"username\": \""+APIUSERNAME+"\", \"password\": \""+APIPASSWORD+"\"}";
		
		org.json.JSONObject jsonObj = Utility.consumeRest(APIENDPOINT+"api/auth/signin","POST",loginForm,"");
        String ACCESS_TOKEN = (String) jsonObj.get("accessToken");
		
        String storeToken = request.getParameter("storeToken");
        String areaId = request.getParameter("areaId");
		String direction = request.getParameter("direction");
		String value = request.getParameter("value");
		
		String occupancy = "{\n"
				+ "  \"areaId\": "+areaId+",\n"
				+ "  \"direction\": \""+direction+"\",\n"
				+ "  \"value\": "+value+"\n"
				+ "}";
		
		Utility.consumeRest(APIENDPOINT+"api/v1/occupancy/"+storeToken,"PUT",occupancy ,ACCESS_TOKEN);
		
		
	}

}
