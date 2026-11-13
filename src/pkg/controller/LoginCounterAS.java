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


public class LoginCounterAS extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		String username = session.getAttribute("username").toString();
		String language = session.getAttribute("language").toString();
		String cdapp = "CNA";
		session.setAttribute("aaapp", "CNA");
		
		
		
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
			
			session.setAttribute("idcus", null);
			session.setAttribute("dscus", "");
			
			lnk = "counterAS/main/index.htm";
			
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
			session.setAttribute("mnuArrayCNA01", mnuArray01);
			session.setAttribute("cdcli", cdcli);
			
			// -------------------------------------------------------------
			
			// ==================
			// NUMERO CLIENTI e lista clienti
			// ==================
			sql  =  "select account_code, account_code_evolvestore, description from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			System.out.println(sql);
			rs = st.executeQuery(sql);
			
			String cus_id = "";
			String cus_id2 = "";
			
			int count = 0;
			int nr_customers = 0;
			while(rs.next()) {
				count+=1;
				cus_id = rs.getString("account_code");
				cus_id2= Utility.noNull(rs.getString("account_code_evolvestore"));
//				if(cus_id2.length()>0)
//					cus_id = cus_id2;
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
				session.setAttribute("dscus", Utility.getCustomerDescriptionByAccountCode(conn, (String) session.getAttribute("idcus")));
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
		
		return new ModelAndView("/login-counterAS", obj);
	}

}
