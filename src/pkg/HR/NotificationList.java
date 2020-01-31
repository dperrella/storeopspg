package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Notification;
import pkg.utility.Utility;

public class NotificationList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		String idusr = (String) session.getAttribute("username");
		
		List<Notification> notif = new ArrayList<Notification>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		notif.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		try {
			
			if(q.length()>0) {
				cond+=" and upper(titolo) || upper(nota) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select * from znotif where idusr = '"+idusr+"' "+cond;
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $idnota="";
			String $link="";
			String $img="";
			String $titolo="";
			String $nota="";
			String $stato="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$idnota=rs.getString("idnota");
				$link=rs.getString("link");
				$img=rs.getString("img");
				$titolo=rs.getString("titolo");
				$nota=rs.getString("nota");
				$stato=rs.getString("stato");
				
				notif.add(new Notification($idnota,$link,$img,$titolo,$nota,$stato,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Notifiche");
		obj.put("notif", notif);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/config/notification-list", obj);
	}
	
}
