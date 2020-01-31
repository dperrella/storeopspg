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
@WebServlet("/rfid/ws/Login_BKP")
public class Login_BKP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login_BKP() {
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
				sql =   "select idusr, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language from zutenti " +
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
				
				sql =   "select idusr, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language from zutenti " +
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
				
				conn = Utility.getConnRfid();
				st = conn.createStatement();
				st2 = conn.createStatement();
				st3 = conn.createStatement();
				
				JSONArray jsonArray = new JSONArray();
				sql   = "select f.description as flow_description, a.idflow "
						+ "from rel_usr_step a , flow f "
						+ "where idusr = '"+username+"' "
						+ "and a.idflow = f.idflow "
						+ "group by a.idflow, f.description "
						+ "order by a.idflow ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					JSONObject jsonFlow = new JSONObject();
					jsonFlow.put("idflow", rs.getInt("idflow"));
					jsonFlow.put("description", rs.getString("flow_description"));
					
					JSONArray jsonArray2 = new JSONArray();
					sql   = "select s.description as step_description, a.* "
							+ "from rel_usr_step a , step s "
							+ "where idusr = '"+username+"' and a.idflow = "+rs.getInt("idflow")+" "
							+ "and a.idstep = s.idstep order by s.idstep";
					rs2=st2.executeQuery(sql);
					while(rs2.next()) {
						JSONObject jsonStep = new JSONObject();
						jsonStep.put("idstep", rs2.getInt("idstep"));
						jsonStep.put("description", rs2.getString("step_description"));
						
						JSONArray jsonArray3 = new JSONArray();
						sql   = "select d.* "
								+ "from rel_dev_step a, devices d "
								+ "where idflow = "+rs.getInt("idflow")+" "
								+ "and idstep = "+rs2.getInt("idstep")+" "
								+ "and d.iddevice = a.id_device";
						rs3=st3.executeQuery(sql);
						while(rs3.next()) {
							JSONObject jsonDevice = new JSONObject();
							jsonDevice.put("iddevice", rs3.getInt("iddevice"));
							jsonDevice.put("name", rs3.getString("name"));
							jsonDevice.put("description", rs3.getString("description"));
							jsonDevice.put("config", rs3.getString("config_device"));
							jsonArray3.add(jsonDevice);
						}
						rs3.close();
						jsonStep.put("device", jsonArray3);
						
						jsonArray2.add(jsonStep);
					}
					rs2.close();
					jsonFlow.put("step", jsonArray2);
					
					jsonArray.add(jsonFlow);
				}
				rs.close();
				responseJson.put("flow", jsonArray);
				
				jsonArray = new JSONArray();
				sql   = "select a.idwarehouse , w.description "
						+ "from rel_usr_warehouse a, warehouse w "
						+ "where idusr = 'DEVELOP' "
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
