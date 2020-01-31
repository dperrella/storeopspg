package pkg.servlet;

import java.io.IOException;

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
/**
 * Servlet implementation class GetProductDetail
 */
@ServerEndpoint("/eas")
public class RFIDEASNotify {
	
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
        	
        	
        	
            session.getBasicRemote().sendText("message from socket...");
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }
	
	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}

}
