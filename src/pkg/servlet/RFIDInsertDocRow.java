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
@WebServlet("/servlet/ws/InsertDocRow")
public class RFIDInsertDocRow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RFIDInsertDocRow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		
		String id_customer = request.getParameter("id_customer");
		String iddoc = request.getParameter("iddoc");
		String category = request.getParameter("category");
		String season = request.getParameter("season");
		String itemcode = request.getParameter("itemcode");
		String colourcode = request.getParameter("colourcode");
		String size = request.getParameter("size");
		String code = request.getParameter("code");
		
		
		
		
		
		JSONObject responseJson = new JSONObject();
		
		try {
			conn = Utility.getConnRfid();
			Document.insertFromField(conn, Integer.parseInt(id_customer), Integer.parseInt(iddoc), category, season, itemcode, colourcode, size, code);
			
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
