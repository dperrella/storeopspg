package pkg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

import java.sql.*;
import java.security.MessageDigest;
	
public class LoginEsegui extends AbstractController {
	
	private static final String API_PASSWORD = "Checkpt$3"; 
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Tables> users = new ArrayList<Tables>();
		
		// Pulisce la lista
		users.clear();
		
		String username = ""+req.getParameter("username");
		String password = ""+req.getParameter("password");
		
		// Trasformo la password in MD5
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		String nome = "";
		String cogn = "";
		String tipo = "";
		String tipo_hr = "";
		String foto = "";
		String mail = "";
		String db = "";
		String style = "";
		String logo = "";
		String sysinf = "";
		String language = "";
		
		boolean utenteEsiste = false;
		
		String lnk = "";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			String cdapp = prop.get("cdapp");
			
			conn = Utility.getConnAdmin();
			st = conn.createStatement();
			
			sql =   "select idusr, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language, tipo_hr from zutenti " +
					"where idusr = '"+username.toUpperCase()+"' " + 
					"and pwd = '"+sb.toString()+"' ";
			
			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				
				username = rs.getString("idusr");
				nome = rs.getString("nome");
				cogn = rs.getString("cogn");
				tipo = rs.getString("tipo");
				tipo_hr = ""+rs.getString("tipo_hr");
				tipo_hr = Utility.noNull(tipo_hr).trim();
				foto = rs.getString("foto");
				mail = rs.getString("mail");
				db = rs.getString("db");
				style = rs.getString("style");
				logo = rs.getString("logo");
				sysinf = rs.getString("sysinf");
				language = rs.getString("language");
				
				utenteEsiste=true;
				
			}
			rs.close();
			
			if(utenteEsiste) {
				
				Utility.setLog(conn, username, "");
				
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("desc", nome+" "+cogn);
				session.setAttribute("tipo", tipo);
				session.setAttribute("tipo_hr", tipo_hr.trim());
				session.setAttribute("tipoLogin", tipo);
				session.setAttribute("foto", foto);
				session.setAttribute("mail", mail);
				session.setAttribute("db", db);
				session.setAttribute("style", style.trim());
				session.setAttribute("logo", logo.trim());
				session.setAttribute("sysinf", sysinf.trim());
				session.setAttribute("language", language.trim());
				
				// Origin = Suite
				session.setAttribute("origin", "Suite");
				
				String is_admin = "";
				sql = "select is_admin from roles where id = "+tipo+" ";
				rs = st.executeQuery(sql);
				if(rs.next()) {
					is_admin = rs.getString("is_admin");
				}
				rs.close();
				if(is_admin.equalsIgnoreCase("t"))
					is_admin = "S";
				else
					is_admin = "N";
				session.setAttribute("is_admin", is_admin);
				
				// Carica il file di properties relativo alla lingua associata all'utente
				ReadProperties propLang = new ReadProperties(language.trim()+".properties");
				
				lnk = "app/main/index.htm";
				
				// --------------------------------------------------------------
				// Lettura funzioni in base all'utente e immissione nella Session
				
				// Cliente / Amministratore
				String cdcli = "";
				
				// --------------------------------------------------------------
				// Lettura funzioni in base all'utente e immissione nella Session
				// 01-Applicazioni (ADMIN)
				String mnuArray01 = "";
				
				// ZZ-Configurazione (ADMIN)
				String mnuArrayZZ = "";
				// YY-Billing (ADMIN)
				String mnuArrayYY = "";
				
				//--------------
				// 01-Applicazioni
				sql   = "SELECT f.cdapp, f.nome, f.descr, f.percorso, f.icon, a.cdcli, f.url "+
						"FROM zapp f, zuteapp a "+
						"WHERE f.cdapp = a.cdapp "+
						"AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') "+
						"order by f.cdapp ";	
				
				String app = "";
				rs = st.executeQuery(sql);
				int nrapp = 0;
				String lnkApp = "";
				String arrayApp = "";
				String apps = "";
				
				String hasCounter = "";
				
				apps+="SUI##@@Smart Suite##@@fa fa-cloud;";
				while(rs.next()) {
					
					if(rs.getString("cdapp").equals("SCT")||rs.getString("cdapp").equals("CT2")) 
						hasCounter="Y";
					
					apps+=rs.getString("cdapp")+"##@@"+rs.getString("nome")+"##@@"+rs.getString("icon").trim()+";";
					arrayApp+=rs.getString("cdapp")+";";
					nrapp+=1;
					lnkApp=rs.getString("url");
					mnuArray01+=rs.getString("cdapp")+rs.getString("nome")+rs.getString("descr")+rs.getString("percorso")+rs.getString("url")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
					
					app+="<a href=\"../../"+rs.getString("url")+"\" class=\"media list-group-item\">"+
						 "<span class=\"media-body block m-b-none\">"+rs.getString("nome")+"</span></a>";
					
				}
				rs.close();
				if(nrapp==1) {
					lnk = lnkApp;
				}
				
				if(arrayApp.length()>0)
					arrayApp=arrayApp.substring(0,arrayApp.length()-1);
				if(apps.length()>0)
					apps=apps.substring(0,apps.length()-1);
				
				//--------------
				// ZZ-Configurazione (ADMIN)
				sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
				        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
				        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
				        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
				        "and tipo = 'ZZ' and cdapp = '"+cdapp+"' "+
				        "order by f.tipo, f.ordinamento ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuArrayZZ+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
				}
				rs.close();
				
				
				
				//--------------
				// YY-BILLING (ADMIN)
				sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
				        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
				        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
				        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
				        "and tipo = 'YY' and cdapp = '"+cdapp+"' "+
				        "order by f.tipo, f.ordinamento ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuArrayYY+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
				}
				rs.close();
				
				// tolgo l'ultimo byte
				if(mnuArray01.trim().length()>0)
					mnuArray01 = mnuArray01.substring(0,mnuArray01.length()-1);
				
				if(mnuArrayZZ.trim().length()>0)
					mnuArrayZZ = mnuArrayZZ.substring(0,mnuArrayZZ.length()-1);
				if(mnuArrayYY.trim().length()>0)
					mnuArrayYY = mnuArrayYY.substring(0,mnuArrayYY.length()-1);
				
				System.out.println(mnuArray01);
				System.out.println(mnuArrayZZ);
				System.out.println(mnuArrayYY);
				
				// Inserisce Parametri in SESSION
				session.setAttribute("mnuArray01", mnuArray01);
				session.setAttribute("mnuArrayZZ", mnuArrayZZ);
				session.setAttribute("mnuArrayYY", mnuArrayYY);
				session.setAttribute("cdcli", cdcli);
				session.setAttribute("arrayApp", arrayApp);
				session.setAttribute("apps", apps);
				
				session.setAttribute("hasCounter", hasCounter);
				
				sql   = "SELECT * FROM zutenti where upper(idusr) <> upper('"+username+"') order by cogn,nome ";	
				rs = st.executeQuery(sql);
				String Exch = "";
				while(rs.next()) {
					Exch+="<option value='"+rs.getString("idusr")+"' value2='"+rs.getString("pwd")+"' >"+rs.getString("idusr")+" - "+rs.getString("nome")+" "+rs.getString("cogn") +"</option>";
				}
				rs.close();
				
				session.setAttribute("Exch", Exch);
				session.setAttribute("app", app);
				
				
				String arrayCustomers = "";
				// Legge i "customers" associati al profilo
				sql   = "select idcustomer from rel_user_customers "+
						"where idusr = '"+username+"' ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					arrayCustomers+=rs.getString("idcustomer")+",";
				}
				rs.close();
				// Tolgo ultimo byte
				if(arrayCustomers.length()>0)
					arrayCustomers = arrayCustomers.substring(0,arrayCustomers.length()-1);
				
				String arrayStores = "";
				// Legge gli "stores" assiciati al profilo
				sql   = "select idstore from rel_user_stores "+
						"where idusr = '"+username+"' ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					arrayStores+=rs.getString("idstore")+",";
				}
				rs.close();
				// Tolgo ultimo byte
				if(arrayStores.length()>0)
					arrayStores = arrayStores.substring(0,arrayStores.length()-1);
				
				String arrayCountry = "";
				// Legge i "country" assiciati al profilo
				sql   = "select country from stores , rel_user_stores "
						+ "where id = idstore and idusr = '"+username+"' "
						+ "group by country ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					arrayCountry+=rs.getString("country")+",";
				}
				rs.close();
				// Tolgo ultimo byte
				if(arrayCountry.length()>0)
					arrayCountry = arrayCountry.substring(0,arrayCountry.length()-1);
				
				session.setAttribute("arrayCustomers", arrayCustomers);
				session.setAttribute("arrayStores", arrayStores);
				session.setAttribute("arrayCountry", arrayCountry);
				
				
				
			}
			else {
				
				lnk = "signinV2.htm?action=error";
				//lnk = "signin_MC.jsp?msg=E";
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
		
		obj.put("lnk", lnk);
		
		return new ModelAndView("/login-esegui", obj);
	}

}
