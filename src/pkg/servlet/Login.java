package pkg.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/app/ws/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection connAdmin = null;
		Statement stAdmin = null;
		ResultSet rsAdmin = null;
		
		String sql = "";
		
		String token = ""+request.getParameter("token");
		token = Utility.noNull(token);
		
		String username = "";
		String password = "";
		
		boolean tokenLogin=false;
		if(token.length()>0)
			tokenLogin = true;
		
		JSONObject responseJson = new JSONObject();
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			String usrAdmin = prop.get("usrAdmin");
			String pswAdmin = prop.get("pswAdmin");
			String urlAdmin = prop.get("urlAdmin");
			String drv = prop.get("drv");
			String databaseAdmin  = prop.get("databaseAdmin");
			
			//System.out.println(urlAdmin+databaseAdmin+","+usrAdmin+","+pswAdmin);
			
			Class.forName(drv);
			connAdmin = DriverManager.getConnection(urlAdmin+databaseAdmin, usrAdmin, pswAdmin);
			
			stAdmin=connAdmin.createStatement();
			
			
			if(tokenLogin) {
				
				if(token.contains(",")) {
					String temp[] = token.split(",");
					token = temp[0];
				}
				sql =   "select idusr, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language, token from zutenti " +
						"where token = '"+token+"' ";
				
			}
			else {
				
				username = request.getParameter("username");
				password = request.getParameter("password");
				
				
				// Trasformo la password in MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				byte[] digest = md.digest();
				StringBuffer sb = new StringBuffer();
				for (byte b : digest) {
					sb.append(String.format("%02x", b & 0xff));
				}
				
				sql =   "select idusr, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language, token from zutenti " +
						"where idusr = '"+username.toUpperCase()+"' " + 
						"and pwd = '"+sb.toString()+"' ";
				
			}
			
			String nome = "";
			String cogn = "";
			String foto = "";
			String tipo = "";
			String mail = "";
			String lang = "";
			
			boolean utenteEsiste = false;
			
			rsAdmin = stAdmin.executeQuery(sql);
			if(rsAdmin.next()){
				
				
				nome = rsAdmin.getString("nome");
				cogn = rsAdmin.getString("cogn");
				tipo = rsAdmin.getString("tipo");
				foto = rsAdmin.getString("foto");
				mail = rsAdmin.getString("mail");
				lang = rsAdmin.getString("language");
				token = rsAdmin.getString("token");
				
				utenteEsiste=true;
				
				username = rsAdmin.getString("idusr");
				
			}
			rsAdmin.close();
			
			
			if(utenteEsiste) {
				
				responseJson.put("user_exist", true);
				responseJson.put("msg", "OK");
				
			}
			else {
				responseJson.put("user_exist", false);
				responseJson.put("msg", username+" NOT EXIST");
			}
			
			
			
		}
		catch(SQLException e) {
			
			System.out.println( e.getMessage());
			responseJson.put("user_exist", false);
			responseJson.put("msg", e.getMessage());
			e.printStackTrace();
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(connAdmin!=null)
					connAdmin.close();
				if(stAdmin!=null)
					stAdmin.close();
			}
			catch(Exception e) {}
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
