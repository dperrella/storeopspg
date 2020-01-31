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


public class LoginControl extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		String username = session.getAttribute("username").toString();
		String language = session.getAttribute("language").toString();
		String cdapp = "SCN";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String lnk = "";
		try {
			
			conn = Utility.getConnAdmin();
			st = conn.createStatement();
			
			session.setAttribute("idcus", null);
			session.setAttribute("dscus", "");
			
			/*
			if(username.trim().equalsIgnoreCase("DEVELOP")) {
				lnk = "control/main/index2.htm";
			}
			else if(username.trim().equalsIgnoreCase("marco.furli@checkpt.com")) {
				lnk = "control/main/index2.htm";
			}
			else {
				lnk = "control/main/index.htm";
			}
			*/
			lnk = "control/main/index2.htm";
			
			// --------------------------------------------------------------
			// Lettura funzioni in base all'utente e immissione nella Session
			
			// Cliente / Amministratore
			String cdcli = "";
			
			// --------------------------------------------------------------
			// Lettura funzioni in base all'utente e immissione nella Session
			// 01-Settings
			String mnuArray01 = "";
			
			//--------------
			// 01-Menu
			sql   = "SELECT f.cdfun, l.descr, f.tipo, a.cdazi, f.percorso, f.icon, a.cdcli " + 
			        "FROM zfunzioni f, zutefunz a, zfunlang l " + 
			        "WHERE f.cdfun = a.cdfun and f.cdfun = l.cdfun and l.lang = '"+language+"' " + 
			        "AND a.idusr = '"+username+"' and a.cdazi in('T', 'V') " +
			        "and tipo = '01' and cdapp = '"+cdapp+"' "+
			        "order by f.ordinamento, f.tipo, f.descr ";
			
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
			session.setAttribute("mnuArraySCN01", mnuArray01);
			session.setAttribute("cdcli", cdcli);
			
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
		
		return new ModelAndView("/login-control", obj);
	}

}
