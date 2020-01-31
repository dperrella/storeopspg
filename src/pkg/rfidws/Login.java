package pkg.rfidws;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
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
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/Login")
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
		
		Connection conn = null;
		Statement st = null;
		Statement st2 = null;
		Statement st3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		
		String sql = "";
		
		String token = ""+request.getParameter("token");
		token = Utility.noNull(token);
		
		String username = "";
		String password = "";
		
		boolean tokenLogin=false;
		if(token.length()>0)
			tokenLogin = true;
		
		try {
			
			connAdmin=Utility.getConnAdmin();
			stAdmin=connAdmin.createStatement();
			
			JSONObject responseJson = new JSONObject();
			
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
				responseJson.put("idusr", username);
				responseJson.put("name", nome);
				responseJson.put("surname", cogn);
				responseJson.put("type", tipo);
				responseJson.put("photo", foto);
				responseJson.put("mail", mail);
				responseJson.put("language", lang);
				responseJson.put("token", token);
				
				conn = Utility.getConnRfid();
				st = conn.createStatement();
				st2 = conn.createStatement();
				st3 = conn.createStatement();
				
				
				JSONArray jsonArray = new JSONArray();
				sql   = "select a.*, b.serial_num, case when c.id_interface_type = 2 then true else false end is_bluetooth "
						+ "from rel_user_profile a, devices b, device_type c "
						+ "where a.id_device = b.iddevice "
						+ "and b.devicetype = c.iddevicetype "
						+ "and a.id_user = '"+username+"' ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					// User has default
					responseJson.put("dft_warehouse", rs.getInt("warehouse"));
					responseJson.put("dft_iddevice", rs.getInt("id_device"));
					responseJson.put("dft_flow", rs.getInt("flow"));
					responseJson.put("dft_step", rs.getInt("step"));
					responseJson.put("dft_antenna", rs.getString("antenna"));
					responseJson.put("serial_num", rs.getString("serial_num"));
					responseJson.put("is_bluetooth", rs.getBoolean("is_bluetooth"));
				}
				else {
					// User does not have default
					responseJson.put("dft_warehouse", null);
					responseJson.put("dft_iddevice", null);
					responseJson.put("dft_flow", null);
					responseJson.put("dft_step", null);
					responseJson.put("dft_antenna", null);
					responseJson.put("serial_num", null);
					responseJson.put("is_bluetooth", false);
				}
				rs.close();
				
				
				jsonArray = new JSONArray();
				sql   = "select a.idwarehouse , w.description "
						+ "from rel_usr_warehouse a, warehouse w "
						+ "where idusr = '"+username+"' "
						+ "and a.idwarehouse = w.idwarehouse";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					JSONObject jsonWarehouse = new JSONObject();
					jsonWarehouse.put("idwarehouse", rs.getInt("idwarehouse"));
					jsonWarehouse.put("description", rs.getString("description"));
					jsonArray.add(jsonWarehouse);
				}
				rs.close();
				responseJson.put("warehouse", jsonArray);
				
			}
			else {
				responseJson.put("user_exist", false);
				responseJson.put("idusr", username);
			}
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
				if(st2!=null)
					st.close();
				if(st3!=null)
					st.close();
				if(connAdmin!=null)
					connAdmin.close();
				if(stAdmin!=null)
					stAdmin.close();
				
			}
			catch(Exception e) {}
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
