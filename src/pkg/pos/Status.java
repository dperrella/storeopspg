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

public class Status extends AbstractController {
	
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
		
		/**/
		String sql = "";
		Connection connext = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
		connext = Utility.getConnPosext();
		st = connext.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		String a = "", b = "", c = "", d = "", e = "", f = "",g = "",h = "",i = "",l = "",m = "",n = "",o = "",p = "",q = "",r = "";
		sql = "select idclient,descrizione,last_keepalive,last_data_syncro,diff,icon from vw_status_stores ";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			a = rs.getString("idclient");
			b = rs.getString("descrizione").toUpperCase();
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
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				if(st!=null)
					st.close();
				if(connext!=null)
					connext.close();
			}
			catch(Exception e) {}
		}		
		/**/
		
		obj.put("Listlog", listlog);
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		return new ModelAndView("/pos/main/status", obj);
	}
	
}
