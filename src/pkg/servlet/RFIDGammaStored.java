package pkg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.rfid.Document;
import pkg.utility.Utility;

import java.sql.*;
/**
 * Servlet implementation class GetProductDetail
 */
@WebServlet("/servlet/ws/GammaStored")
public class RFIDGammaStored extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RFIDGammaStored() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		
		String iddoc = request.getParameter("iddoc");
		
		JSONObject responseJson = new JSONObject();
		
		try {
			conn = Utility.getConnRfid();
			Document.gammaStored(conn, Integer.parseInt(iddoc));
			
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
		
		responseJson.put("message", "OK");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
