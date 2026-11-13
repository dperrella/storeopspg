package pkg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pkg.utility.OccupancySettings;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/api/occupancy/settings")
public class OccupancySettingsApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OccupancySettingsApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accountId = Utility.noNull(request.getParameter("account_id"));
		String message = "";
		if(accountId.length()>0) {
			try {
				new Thread(new OccupancySettings(accountId)).start();
				message = "{\"message\": \"Occupancy Settings set in batch...\"}";
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			message = "{\"message\": \"Missing 'account_id' parameter\"}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
