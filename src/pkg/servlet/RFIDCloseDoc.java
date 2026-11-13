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
import pkg.beans.rfid.DocumentToclose;
import pkg.utility.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class GetProductDetail
 */
@WebServlet("/servlet/ws/CloseDoc")
public class RFIDCloseDoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RFIDCloseDoc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		String iddoc = request.getParameter("iddoc");
		String closeType = request.getParameter("closeType");
		
		JSONObject responseJson = new JSONObject();
		
		try {
			conn = Utility.getConnRfid();
			
			List<DocumentToclose> epc = new ArrayList();
			String[] values=request.getParameterValues("chkSku");
			
			if(values!=null) {
				for(int i=0;i<values.length;i++)
				{
					// Select epc from sku
					String epcs[] = Document.getDocEpcFromSku(conn, Integer.parseInt(iddoc), values[i]).split(",");
					for(int e=0;e<epcs.length;e++) {
						if(epcs[e].length()>=24)
							epc.add(new DocumentToclose(Integer.parseInt(iddoc),epcs[e],values[i]));
					}
				}
			}
			Document.closeDoc(conn, Integer.parseInt(iddoc), epc);
			
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

}
