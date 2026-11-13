package pkg.servlet.ats;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
@WebServlet("/ats/Login")
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
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		
		String username = "";
		String password = "";
		
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONArray jsonArray2 = new JSONArray();
		JSONArray jsonArray3 = new JSONArray();
		
		
		
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
			
			st=connAdmin.createStatement();
			
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
			System.out.println(sql);	
			
			
			String nome = "";
			String cogn = "";
			String foto = "";
			String tipo = "";
			String mail = "";
			String lang = "";
			
			boolean utenteEsiste = false;
			
			
			rs = st.executeQuery(sql);
			if(rs.next()){
				
				
				nome = rs.getString("nome");
				cogn = rs.getString("cogn");
				tipo = rs.getString("tipo");
				foto = rs.getString("foto");
				mail = rs.getString("mail");
				lang = rs.getString("language");
				
				utenteEsiste=true;
				
				username = rs.getString("idusr");
				
				
				
			}
			rs.close();
			
			
			if(utenteEsiste) {
				
				// customers with items
				sql   = "select id, account_code, description, "
						+ "case when (select idcustomer "
						+ "from pref_user_customers where idusr = '"+username.toUpperCase()+"' and idcustomer = id) "
						+ "is null then 0 else 1 end checked "
						+ "from customers "
						+ "where "
						+ "id in(select idcustomer from rel_user_customers "
						+ "where idusr = '"+username.toUpperCase()+"') "
						+ "and id in(select idcustomer from ats.rel_item_customer) "
						+ "order by description ";
				
				
				
				rs=st.executeQuery(sql);
				
				String sqlpref = "";
				while(rs.next()) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", rs.getInt("id"));
					jsonObj.put("accountid", rs.getInt("account_code"));
					jsonObj.put("description", rs.getString("description"));
					jsonObj.put("checked", rs.getInt("checked"));
					jsonArray.add(jsonObj);
					
					sqlpref+="('"+username.toUpperCase()+"', "+rs.getInt("id")+"),";
					
				}
				rs.close();
				
				if(sqlpref.length()>0)
					sqlpref = sqlpref.substring(0,sqlpref.length()-1);
				
				// all customers
				sql   = "select id, account_code, description, "
						+ "case when (select idcustomer "
						+ "from pref_user_customers where idusr = '"+username.toUpperCase()+"' and idcustomer = id) "
						+ "is null then 0 else 1 end checked "
						+ "from customers where "
						+ "id in(select idcustomer from rel_user_customers "
						+ "where idusr = '"+username.toUpperCase()+"') "
						+ "order by description ";
				System.out.println(sql);
				
				
				rs=st.executeQuery(sql);
				
				while(rs.next()) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", rs.getInt("id"));
					jsonObj.put("accountid", rs.getInt("account_code"));
					jsonObj.put("description", rs.getString("description"));
					jsonObj.put("checked", rs.getInt("checked"));
					
					jsonArray2.add(jsonObj);
				}
				rs.close();
				
				
				// preferred customers
				sql   = "select count(*) num from pref_user_customers where idusr = '"+username.toUpperCase()+"' ";
				rs=st.executeQuery(sql);
				int num = 0;
				if(rs.next()) {
					num = rs.getInt("num");
				}
				rs.close();
				if(num==0) {			
					sql = "insert into pref_user_customers (idusr, idcustomer) values "+sqlpref+" ";
					System.out.println(sql);
					st.executeUpdate(sql);
				}
				
				sql   = "select id, account_code, description, "
						+ "1 as checked "
						+ "from customers where "
						+ "id in(select idcustomer from rel_user_customers "
						+ "where idusr = '"+username.toUpperCase()+"') "
						+ "and id in (select idcustomer " 
						+ "from pref_user_customers where idusr = '"+username.toUpperCase()+"' and idcustomer = id) "
						+ "order by description ";
				System.out.println(sql);			
				rs=st.executeQuery(sql);
				
				while(rs.next()) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("id", rs.getInt("id"));
					jsonObj.put("accountid", rs.getInt("account_code"));
					jsonObj.put("description", rs.getString("description"));
					jsonObj.put("checked", rs.getInt("checked"));
					
					jsonArray3.add(jsonObj);
				}
				rs.close();
				
				
				
				
				
				
				
				responseJson.put("user_exist", true);
				responseJson.put("idusr", username.toUpperCase());
				responseJson.put("name", nome);
				responseJson.put("surname", cogn);
				responseJson.put("mail", mail);
				responseJson.put("lang", lang);
				responseJson.put("type", tipo);
				
				
				responseJson.put("customers", jsonArray);
				responseJson.put("customersAll", jsonArray2);
				responseJson.put("customersPref", jsonArray3);
				
				
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
				if(st!=null)
					st.close();
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
