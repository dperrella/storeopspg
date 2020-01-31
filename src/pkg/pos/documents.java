package pkg.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.pos.Sm_Listlogs;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class documents extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		//List<Sm_Listlogs> listlog = new ArrayList<Sm_Listlogs>();
		List<Users> listlog = new ArrayList<Users>();
		
		listlog.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		String db = (String) session.getAttribute("db");
		
		try {
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		String sql = "";
		Connection connext = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		connext = Utility.getConnext();
		st = connext.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		String a = "", b = "", c = "", d = "", e = "", f = "",g = "",h = "",i = "",l = "",m = "",n = "",o = "",p = "",q = "",r = "";
		sql = "select idclient,descrizione,last_keepalive,last_data_syncro,diff,icon from vw_status_stores ";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			a = rs.getString("idclient");
			b = rs.getString("descrizione");
			c = rs.getString("last_keepalive");
			d = rs.getString("last_data_syncro");
			e = rs.getString("diff");
			f = rs.getString("icon");
			
			if (f == "OK" || f.equals("OK")){
				g = f;
				h = "Connesso";
				f = "fa fa-wifi";
			}else if(f == "WARNING" ||  f.equals("WARNING") ){
				g = f;
				h = "Attenzione";
				f = "fa fa-exclamation-triangle";
			}else{
				g = f;
				h = "Non Connesso";
				//f = "fa fa-stop";
				f = "fa fa-ban";
			}
			
			listlog.add(new Users(a,b,c,d,e,f,g,h,i,l,m,n,o,p,q,r));
			
		}
	
		rs.close();
		*/
		String list_app_store = "";
		String sql = "";
		String combocli = "";
		Connection connext = null;
		Connection conn = null;
		Connection conn2 = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		connext = Utility.getConnPosext();
		st = connext.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		
		conn = Utility.getConnPos();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		conn2 = Utility.getConnPos();
		st2 = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		sql = "select * from rel_user_stores where upper(idusr) = '"+username.toUpperCase()+"'";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			if ("".equals(list_app_store)){
				list_app_store = "'"+rs.getString("idstore")+"'";
			}else{
				list_app_store = list_app_store + ",'" + rs.getString("idstore")+"'";
			}
		}
		rs.close();
		
		//sql = "select * from app_stores where idclient in("+list_app_store+") order by descrizione ";
		sql = "select idclient, descrizione from app_stores where idclient in("+list_app_store+") order by idclient ";
		//System.out.println("sql = " + sql);
		rs = st2.executeQuery(sql);
		while(rs.next()) {
			combocli = combocli + "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";
		}
		//rs.close();
		/*
		String a = "", b = "", c = "", d = "", e = "", f = "",g = "",h = "",i = "",l = "",m = "",n = "",o = "",p = "",q = "",r = "";
		sql = "select idclient, descrizione from app_stores order by idclient ";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			combocli += "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";
		}
		 */
		rs.close();		
		obj.put("ComboCli", combocli);
		
		sql = "select idclient, descrizione from app_stores where idclient in("+list_app_store+") order by idclient ";
		//System.out.println("sql = " + sql);
		rs = st2.executeQuery(sql);
		while(rs.next()) {
			combocli = combocli + "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";
		}
		//rs.close();
		/*
		String a = "", b = "", c = "", d = "", e = "", f = "",g = "",h = "",i = "",l = "",m = "",n = "",o = "",p = "",q = "",r = "";
		sql = "select idclient, descrizione from app_stores order by idclient ";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			combocli += "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";
		}
		 */
		rs.close();		
		obj.put("ComboCli", combocli);
/*
		int numstore = 0;
		
		obj.put("ComboCli", combocli);
		
		System.out.println("tip = "+req.getParameter("tip"));
		

		
		if (!req.getParameter("tip").equals(null)){
		if (req.getParameter("tip").equals("E")){
		
			connext = Utility.getConnext();
			st = connext.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			sql = "select receipts.idclient||'-'||tickettype::text||'-'||ticketid::text as ticket,datenew datenew, "+
					"round((sum(units*price*(select 1+rate from taxes where category = '001' and idclient = receipts.idclient order by validfrom desc limit 1)))::numeric,2) totale, "+
					"people.name from receipts join tickets on receipts.id = tickets.id and receipts.idclient = tickets.idclient "+
					"join ticketlines on tickets.id = ticket and tickets.idclient = ticketlines.idclient "+ 
					"join people on person = people.id and tickets.idclient = people.idclient "+ 
					"where ";
					if (!req.getParameter("tip").equals(null)){
					sql+= "receipts.idclient = '"+req.getParameter("codstore")+"' and ";
					}
					sql+= "datenew::date = '"+req.getParameter("datestore")+"' "+
					"group by receipts.idclient,tickettype,ticketid,datenew ,people.name "+
					"having round((sum(units*price*(select 1+rate from taxes where category = '001' "
					+ "and idclient = receipts.idclient order by validfrom desc limit 1)))::numeric,2) >= "+numstore+" order by receipts.idclient,datenew";
			System.out.println("sql = "+sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				a = rs.getString("ticket");
				b = rs.getString("datenew");
				c = rs.getString("totale");
				d = rs.getString("name");
								
				listlog.add(new Users(a,b,c,d,e,f,g,h,i,l,m,n,o,p,q,r));
				
			}
		
			rs.close();
		
			
			
		}
		}
*/	
		
		
		
		return new ModelAndView("/pos/main/documents", obj);
	}
	
}
