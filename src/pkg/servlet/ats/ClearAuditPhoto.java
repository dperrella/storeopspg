package pkg.servlet.ats;

import java.io.IOException;

import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import pkg.beans.ats.AuditItem;
import pkg.beans.ats.Photo;
import pkg.utility.Utility;

import java.io.File;
 
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import sun.misc.BASE64Decoder;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/ats/ClearAuditPhoto")
public class ClearAuditPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearAuditPhoto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		String idAudit = request.getParameter("idaudit");
		String idItem = request.getParameter("iditem");
		String url="";
		JSONObject responseJson = new JSONObject();
		String msg = "OK";
		
		try {
			
			conn=Utility.getConnData();
			
			AuditItem ai = new AuditItem();
			ai.setIdAudit(Integer.parseInt(idAudit));
        	ai.setIdItem(Integer.parseInt(idItem));
        	ai.setUrl(url);
        	ai.updateUrl(conn);
        
			
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
		doGet(request, response);
		
	}

}
