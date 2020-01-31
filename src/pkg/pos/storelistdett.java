package pkg.pos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import groovy.ui.Console;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;
import pkg.beans.pos.Sm_Listlogs;
import pkg.beans.pos.Sm_Listupdat;
import pkg.beans.pos.Sm_DevnotP;
import pkg.beans.pos.Sm_DevnotP2;
import pkg.beans.pos.Sm_Peoplnu;
import pkg.beans.pos.Sm_DevnotE;
import pkg.beans.pos.Users;
import pkg.beans.pos.Stores_;

import com.google.gson.Gson;

public class storelistdett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();

		List<Sm_DevnotE> pepl = new ArrayList<Sm_DevnotE>();

		

		pepl.clear();

		
		HttpSession session = req.getSession();
		
		String ling = session.getAttribute("language").toString();

		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		/**/
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		conn = Utility.getConnPosext();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sql = "";
		String id_ = "";
		String idC = "";
		String RidC = "";
		String idCountry = "";
		String param = "";
		String param_ = "";
		String id_customer = "";
		String stores_ = "";
		String box1 = "";
		String box2 = "";
		String box3 = "";
		String box4 = "";
		String tab1 = "";
		String tab2 = "";		
		String tab3 = "";
		String tab4 = "";
		String tab5 = "";		
		String timing = "";
		/**/

		String us = session.getAttribute("username").toString().toUpperCase();
		
		String idsto = req.getParameter("id");


		
		
		String a = "", b = "", c = "", d = "", e = "", f = "", g = "";
		
		try {
		
		sql = "select * from app_stores where idclient = '"+idsto+"'";
		
		rs = st.executeQuery(sql);
		while(rs.next()) {
			a = rs.getString("idclient");
			a = Utility.noNull(a);
			b = rs.getString("descrizione");
			b = Utility.noNull(b);
			c = rs.getString("conn_string");
			c = Utility.noNull(c);
			d = rs.getString("ip");
			d = Utility.noNull(d);
			e = rs.getString("utenzadb");
			e = Utility.noNull(e);
			f = rs.getString("passworddb");
			f = Utility.noNull(f);
			g = rs.getString("nomedb");
			g = Utility.noNull(g);
		}
		rs.close();
		
		}
		catch(Exception ed) {
			ed.printStackTrace();
		}
		finally {
			
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception ex) {}
		}		
		
		session.setAttribute("ID", a);
		session.setAttribute("Descrizione", b);
		session.setAttribute("Conn", c);
		session.setAttribute("Ip", d);
		session.setAttribute("Utenza", e);
		session.setAttribute("Pwd", f);
		session.setAttribute("Nomedb", g);


		
		
		return new ModelAndView("/pos/main/store-list-dett", obj);

	}
	
}

