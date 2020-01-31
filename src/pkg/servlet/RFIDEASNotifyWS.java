package pkg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

import java.sql.*;

@WebServlet("/SetEasWS")
@ServerEndpoint("/easws")
public class RFIDEASNotifyWS extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public static Session s;
	
	@OnOpen
    public void onOpen(Session session) throws IOException {
        //session.getBasicRemote().sendText("onOpen------");
        s = session;
        System.out.println("* Session ID: "+s.getId());
    }
	
	@OnClose
	public void onClose(){
		System.out.println("Close Connection ...");
	}
	
	@OnMessage
    public void onMessage(String message, Session session) {
		
		s = session;
        
		System.out.println("Message from " + session.getId() + ": " + message);
        try {
        	
        	if(message.equals("ping")) {
        		session.getBasicRemote().sendText("pong");
        	}
        	else {
        		session.getBasicRemote().sendText(message);
        	}
        	
            
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }
	
	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}
	
	public RFIDEASNotifyWS() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String barcode = request.getParameter("barcode");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		JSONObject jsonObj = new JSONObject();
		
		try {
			
			conn = Utility.getConnRfid();
			st = conn.createStatement();
			sql = "select code, description, sku, colordesc, size, season, image, bcode1, bcode2 from products where sku = '"+barcode+"' ";
			rs = st.executeQuery(sql);
			String image = "";
			if(rs.next()) {
				
				image = Utility.noNull((""+rs.getString("image")));
				if(image.length()==0)
					image = "http://suite.smartretail.it/images/SmartSuite_logo_NEW.png";
				jsonObj.put("code", rs.getString("code"));
				jsonObj.put("description", rs.getString("description"));
				jsonObj.put("sku", rs.getString("sku"));
				jsonObj.put("colordesc", rs.getString("colordesc"));
				jsonObj.put("size", rs.getString("size"));
				jsonObj.put("season", rs.getString("season"));
				jsonObj.put("image", image);
				jsonObj.put("bcode1", rs.getString("bcode1"));
				jsonObj.put("bcode2", rs.getString("bcode2"));
				
			}
			rs.close();
			
			
     	} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("*********"+s.getId());
     		s.getBasicRemote().sendText(jsonObj.toString());
		}
		catch(Exception err) {}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonObj.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


}
