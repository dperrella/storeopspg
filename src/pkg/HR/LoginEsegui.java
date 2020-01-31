package pkg.HR;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;

import java.sql.*;
import java.security.MessageDigest;


public class LoginEsegui extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
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
			
			conn = Utility.getConnHR();
			st = conn.createStatement();
			
			sql =   "select idusr, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language from zutenti " +
					"where idusr = '"+username.toUpperCase()+"' " + 
					"and pwd = '"+sb.toString()+"' ";
			
			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				
				username = rs.getString("idusr");
				nome = rs.getString("nome");
				cogn = rs.getString("cogn");
				tipo = rs.getString("tipo");
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
				
				session.setAttribute("username", username);
				session.setAttribute("desc", nome+" "+cogn);
				session.setAttribute("tipo", tipo);
				session.setAttribute("foto", foto);
				session.setAttribute("mail", mail);
				session.setAttribute("db", db);
				session.setAttribute("style", style.trim());
				session.setAttribute("logo", logo.trim());
				session.setAttribute("sysinf", sysinf.trim());
				session.setAttribute("language", language.trim());
				
				// Carica il file di properties relativo alla lingua associata all'utente
				ReadProperties propLang = new ReadProperties(language.trim()+".properties");
				
				lnk = "hr/main/index.htm";
				
				// --------------------------------------------------------------
				// Lettura funzioni in base all'utente e immissione nella Session
				
				// Cliente / Amministratore
				String cdcli = "";
				
				// --------------------------------------------------------------
				// Lettura funzioni in base all'utente e immissione nella Session
				// 01-Anagrafici
				String mnuArray01 = "";
				
				// 02-Relazioni
				String mnuArray02 = "";
				
				// 03-Pianificazione
				String mnuArray03 = "";
				
				// 04-Controlli
				String mnuArray04 = "";
				
				// ZZ-Configurazione (ADMIN)
				String mnuArrayZZ = "";
				
				// AA-Applicazioni
				String mnuApp = "";
				
				//--------------
				// 01-Anagrafici
				sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
				        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
				        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
				        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
				        "and tipo = '01' and cdapp = '"+cdapp+"' "+
				        "order by f.tipo, f.descr ";
				
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuArray01+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
				}
				rs.close();
				
				//--------------
				// 02-Relazioni
				sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
				        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
				        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
				        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
				        "and tipo = '02' and cdapp = '"+cdapp+"' "+
				        "order by f.tipo, f.descr ";
				
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuArray02+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
				}
				rs.close();
				
				//--------------
				// 03-Pianificazione
				sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
				        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
				        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
				        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
				        "and tipo = '03' and cdapp = '"+cdapp+"' "+
				        "order by f.tipo, f.descr ";
				
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuArray03+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
				}
				rs.close();
				
				//--------------
				// 04-Controlli
				sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
				        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
				        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
				        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
				        "and tipo = '04' and cdapp = '"+cdapp+"' "+
				        "order by f.tipo, f.descr ";
				
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuArray04+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
				}
				rs.close();
				
				//--------------
				// ZZ-Microgem Lab (ADMIN)
				sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
				        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
				        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
				        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
				        "and tipo = 'ZZ' and cdapp = '"+cdapp+"' "+
				        "order by f.tipo, f.descr ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuArrayZZ+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
					
					cdcli = rs.getString("cdcli").trim();
					
				}
				rs.close();
				
				// --------------
				// Autorizzazione ad Applicazioni
				sql   = "SELECT f.cdapp, f.nome, f.descr, f.percorso, f.icon, a.cdcli "+
						"FROM zapp f, zuteapp a "+
						"WHERE f.cdapp = a.cdapp "+
						"AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') "+
						"order by f.cdapp ";	
				
				rs = st.executeQuery(sql);
				while(rs.next()) {
					
					mnuApp+=rs.getString("cdapp")+rs.getString("nome")+rs.getString("descr")+rs.getString("percorso")+rs.getString("icon")+";";
					
				}
				rs.close();
				
				// tolgo l'ultimo byte
				if(mnuArray01.trim().length()>0)
					mnuArray01 = mnuArray01.substring(0,mnuArray01.length()-1);
				
				if(mnuArray02.trim().length()>0)
					mnuArray02 = mnuArray02.substring(0,mnuArray02.length()-1);
				
				if(mnuArray03.trim().length()>0)
					mnuArray03 = mnuArray03.substring(0,mnuArray03.length()-1);
				
				if(mnuArray04.trim().length()>0)
					mnuArray04 = mnuArray04.substring(0,mnuArray04.length()-1);
				
				if(mnuArrayZZ.trim().length()>0)
					mnuArrayZZ = mnuArrayZZ.substring(0,mnuArrayZZ.length()-1);
				
				if(mnuApp.trim().length()>0)
					mnuApp = mnuApp.substring(0,mnuApp.length()-1);
				
				System.out.println(mnuArray01);
				System.out.println(mnuArray02);
				System.out.println(mnuArray03);
				System.out.println(mnuArray04);
				System.out.println(mnuArrayZZ);
				
				// Applicazioni autorizzate
				System.out.println(mnuApp);
				
				
				// Inserisce Parametri in SESSION
				session.setAttribute("mnuArray01", mnuArray01);
				session.setAttribute("mnuArray02", mnuArray02);
				session.setAttribute("mnuArray03", mnuArray03);
				session.setAttribute("mnuArray04", mnuArray04);
				session.setAttribute("mnuArrayZZ", mnuArrayZZ);
				session.setAttribute("cdcli", cdcli);
				
				session.setAttribute("mnuApp", mnuApp);
				
				String app = "";
				
/* Commentata per Nuvolari
				app   = "<li  class=\"hidden-xs\">"+
				        "  <a href=\"#\" class=\"dropdown-toggle lt\" data-toggle=\"dropdown\">"+
				        "    <i class=\"fa fa-cogs\"></i>"+
				        "  </a>"+
				        "  <section class=\"dropdown-menu aside-xs animated fadeInUp\">"+
				        "    <section class=\"panel bg-white\">"+
				        "      <div class=\"panel-heading b-light bg-light\">"+
				        "        <strong>"+propLang.get("application")+"</strong>"+
				        "      </div>"+
				        "      <div class=\"list-group\">";
				
				        
				String temp[]=mnuApp.split(";");
				for(int a=0; a<temp.length; a++) {
					app+=   "		<a href=\""+temp[a].substring(128,228).trim()+"?username="+username+"&password="+password+"\" class=\"media list-group-item\">"+
			                "   		<span class=\"media-body block m-b-none\">"+temp[a].substring(3,28)+"</span>"+
			                "		</a>";
				}
				        
		                
		        app+=   "      </div>"+
				        
				        "    </section>"+
				        "  </section>"+
				        "</li>";
				// -------------------------------
*/
				
				
				// Legge le notifiche
				int numNotif = 0;
				String rigaNotif = "";
				String alertNotif = "";
				String notif = "";
				
				sql   = "select * from znotif where idusr = '"+username+"' order by idnota desc fetch first 5 rows only ";
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					if(!rs.getString("stato").equals("A"))
						numNotif+=1;
					
					rigaNotif+= "<a href=\"../../"+rs.getString("link")+"\" class=\"media list-group-item\">"+
				                " 	<span class=\"pull-left thumb-sm text-center\">"+rs.getString("img")+"</span>"+
				                "   <span class=\"media-body block m-b-none\">"+rs.getString("titolo")+"<br>"+
				                "      <small class=\"text-muted\">"+rs.getString("nota")+"</small>"+
				                "    </span>"+
				                "</a>";	
					
				}
				rs.close();
				
				if(numNotif>0)
					alertNotif = "<span class=\"badge badge-sm up bg-danger count\">"+numNotif+"</span>";
				
				notif = "<li class=\"hidden-xs\">"+
				        "  <a href=\"#\" class=\"dropdown-toggle lt\" data-toggle=\"dropdown\">"+
				        "    <i class=\"icon-bell\"></i>"+alertNotif+
				        "  </a>"+
				        "  <section class=\"dropdown-menu aside-xl animated fadeInUp\">"+
				        "    <section class=\"panel bg-white\">"+
				        "      <div class=\"panel-heading b-light bg-light\">"+
				        "        <strong>"+propLang.get("hai")+" <span class=\"count\">"+numNotif+"</span> "+propLang.get("notifiche")+"</strong>"+
				        "      </div>"+
				        "        <div class=\"list-group list-group-alt\">"+
				        rigaNotif+
				        "      </div>"+
				        "      <div class=\"panel-footer text-sm\">"+
				        "        <a href=\"../../app/config/notification-list.htm\" class=\"pull-right\"><i class=\"fa fa-cog\"></i></a>"+
				        "        <a href=\"../../app/config/notification-list.htm\">"+propLang.get("visnotif")+"</a>"+
				        "      </div>"+
				        "    </section>"+
				        "  </section>"+
				        "</li>";
				
				session.setAttribute("notif", app+notif);
				session.setAttribute("numNotif", numNotif);
				// -------------------------------------------------------------
				
				// Flagga le notifiche come lette
				sql = "update znotif set stato = 'A' where idusr = '"+username+"' ";
				st.executeUpdate(sql);
				
				
			}
			else {
				
				lnk = "signin.jsp?msg=E";
			
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
