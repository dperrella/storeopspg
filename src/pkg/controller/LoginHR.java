package pkg.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import pkg.utility.Utility;
import java.sql.*;


public class LoginHR extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		String username = session.getAttribute("username").toString();
		String language = session.getAttribute("language").toString();
		String cdapp = "SHR";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String lnk = "";
		try {
			
			// Azzera "idcus"
			session.setAttribute("idcus", null);
			session.setAttribute("dscus", "");
			
			conn = Utility.getConnAdmin();
			st = conn.createStatement();
				
			lnk = "hr/main/index.htm";
			
			// --------------------------------------------------------------
			// Lettura funzioni in base all'utente e immissione nella Session
			
			// Cliente / Amministratore
			String cdcli = "";
			
			// --------------------------------------------------------------
			// Lettura funzioni in base all'utente e immissione nella Session
			// --------------------------------------------------------------
			// Lettura funzioni in base all'utente e immissione nella Session
			// Lettura funzioni in base all'utente e immissione nella Session
			// 01-Anagrafici
			String mnuArray01 = "";
			
			// 02-Relazioni
			String mnuArray02 = "";
			
			// 03-Pianificazione
			String mnuArray03 = "";
			
			// 04-Controlli
			String mnuArray04 = "";
			
			//--------------
			// 01-Anagrafici
			sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
			        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
			        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
			        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
			        "and tipo = '01' and cdapp = '"+cdapp+"' "+
			        "order by f.tipo, f.descr ";
			System.out.println(sql);
			
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				mnuArray01+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
				
				//cdcli = rs.getString("cdcli").trim();
				
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
			System.out.println(sql);
			
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				mnuArray02+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
				
				//cdcli = rs.getString("cdcli").trim();
				
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
			System.out.println(sql);
			
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				mnuArray03+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
				
				//cdcli = rs.getString("cdcli").trim();
				
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
			System.out.println(sql);
			
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				mnuArray04+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
				
				//cdcli = rs.getString("cdcli").trim();
				
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
			
			System.out.println("*****"+mnuArray01);
			System.out.println("*****"+mnuArray02);
			System.out.println("*****"+mnuArray03);
			System.out.println("*****"+mnuArray04);
			
			
			// Inserisce Parametri in SESSION
			session.setAttribute("mnuArraySHR01", mnuArray01);
			session.setAttribute("mnuArraySHR02", mnuArray02);
			session.setAttribute("mnuArraySHR03", mnuArray03);
			session.setAttribute("mnuArraySHR04", mnuArray04);
			session.setAttribute("cdcli", cdcli);
			
			// -------------------------------------------------------------
			
			// ==================
			// NUMERO CLIENTI e lista clienti
			// ==================
			sql  =  "select id, description from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			String cus_id = "";
			int count = 0;
			String id_cus="";
			int nr_customers = 0;
			while(rs.next()) {
				count+=1;
				cus_id = rs.getString("id");
			}
			rs.close();
			st.close();
			
			if(count==0) {
				System.out.println("count is 0");
				session.setAttribute("idcus", null);
				nr_customers = 0;
			}
			if(count==1) {
				System.out.println("count is 1 ("+cus_id+")");
				
				session.setAttribute("idcus", cus_id);
				nr_customers = 1;
			}
			
			if(nr_customers==1) {
				session.setAttribute("dscus", Utility.getCustomerDescription(conn, (String) session.getAttribute("idcus")));
			}
			
			session.setAttribute("nrcus", count);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
		
		obj.put("lnk", lnk);
		
		
		return new ModelAndView("/login-hr", obj);
	}

}
