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


public class LoginCounter extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		String username = session.getAttribute("username").toString();
		String language = session.getAttribute("language").toString();
		String cdapp = "SCT";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String lnk = "";
		try {
			
			conn = Utility.getConnAdmin();
			st = conn.createStatement();
				
			lnk = "counter/main/index.htm";
			
			// --------------------------------------------------------------
			// Lettura funzioni in base all'utente e immissione nella Session
			
			// Cliente / Amministratore
			String cdcli = "";
			
			// --------------------------------------------------------------
			// Lettura funzioni in base all'utente e immissione nella Session
			// 01-Settings
			String mnuArray01 = "";
			
			//--------------
			// 01-Anagrafici
			sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
			        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
			        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
			        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
			        "and tipo = '01' and cdapp = '"+cdapp+"' "+
			        "order by f.ordinamento, f.tipo, f.descr ";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				
				mnuArray01+=rs.getString("cdfun")+rs.getString("descr")+rs.getString("tipo")+rs.getString("percorso")+rs.getString("icon")+";";
				
				cdcli = rs.getString("cdcli").trim();
				
			}
			rs.close();
			
			
			
			
			// tolgo l'ultimo byte
			if(mnuArray01.trim().length()>0)
				mnuArray01 = mnuArray01.substring(0,mnuArray01.length()-1);
			
			
			System.out.println("*****"+mnuArray01);
			
			// Inserisce Parametri in SESSION
			session.setAttribute("mnuArraySCT01", mnuArray01);
			session.setAttribute("cdcli", cdcli);
			
			/*
			Connection connCounter = Utility.getConnCounter();
			Statement stCounter = connCounter.createStatement();
			
			String arrayCustomers = "";
			// Legge i "customers" associati al profilo (per Counter)
			sql   = "select idcustomer from rel_user_customers "+
					"where idusr = '"+username+"' ";
			System.out.println(sql);
			rs=stCounter.executeQuery(sql);
			while(rs.next()) {
				arrayCustomers+=rs.getString("idcustomer")+",";
			}
			rs.close();
			// Tolgo ultimo byte
			if(arrayCustomers.length()>0)
				arrayCustomers = arrayCustomers.substring(0,arrayCustomers.length()-1);
			
			String arrayStores = "";
			// Legge gli "stores" assiciati al profilo (per Analytics)
			sql   = "select idstore from rel_user_stores "+
					"where idusr = '"+username+"' ";
			System.out.println(sql);
			rs=stCounter.executeQuery(sql);
			while(rs.next()) {
				arrayStores+=rs.getString("idstore")+",";
			}
			rs.close();
			// Tolgo ultimo byte
			if(arrayStores.length()>0)
				arrayStores = arrayStores.substring(0,arrayStores.length()-1);
			
			stCounter.close();
			connCounter.close();
			
			// Se i legami utente/clienti e utente/negozi esiste per l'app
			// Sovrascrive quelli di Suite. Altrimenti mantiene quelli di Suite
			if(arrayCustomers.length()>0)
				session.setAttribute("arrayCustomers", arrayCustomers);
			
			if(arrayStores.length()>0)
				session.setAttribute("arrayStores", arrayStores);
			*/
			
			// -------------------------------------------------------------
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
		
		obj.put("lnk", lnk);
		
		return new ModelAndView("/login-counter", obj);
	}

}
