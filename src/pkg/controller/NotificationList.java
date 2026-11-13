package pkg.controller;

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

import pkg.beans.Notification;
import pkg.beans.Tables;
import pkg.utility.Utility;

public class NotificationList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		String idusr = (String) session.getAttribute("username");
		
		List<Notification> notif = new ArrayList<Notification>();
		List<Tables> apps = new ArrayList<Tables>();
		List<Tables> sts = new ArrayList<Tables>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		notif.clear();
		apps.clear();
		sts.clear();
		
		String temp[] = session.getAttribute("apps").toString().split(";");
		int nrapp = 0;
		String selected_app = ""+req.getParameter("app");
		selected_app=Utility.noNull(selected_app);
		
		String selected_sts = ""+req.getParameter("sts");
		selected_sts=Utility.noNull(selected_sts);
		
		String all_active = "";
		if(selected_sts.length()==0)
			all_active = "active";
		else
			all_active = "";
		
		
		
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
			
			conn = Utility.getConnAdmin();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			
			for(int i=0;i<temp.length;i++) {
				
				nrapp+=1;
				
				String temp2[] = temp[i].split("##@@");
				if(selected_app.length()==0) {
					if(nrapp==1) {
						selected_app = temp2[0];
					}
				}
				
				String active = "";
				if(temp2[0].equals(selected_app))
					active = "active";
				
				int nr =  not4App(conn, idusr, temp2[0]);
				String nrnot = "";
				if(nr>0)
					nrnot = "<span class=\"badge badge-sm up bg-danger count\" style=\"display: inline-block;\">"+nr+"</span>";
					
				
				apps.add(new Tables("APP", temp2[0], temp2[1],active,temp2[2],nrnot,""));
				
			}
			
			// Status messaggi
			sql  =  "select xcdel, xdtab from ztables where xcdtb = '"+selected_app+"' ";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				String active = "";
				if(rs.getString("xcdel").trim().equals(selected_sts.trim())) {
					active = "active";
				}
				sts.add(new Tables("STS", rs.getString("xcdel"), rs.getString("xdtab"),active,"","",""));
				
			}
			rs.close();
			
			sql  =  "select * from znotif where idusr = '"+idusr+"' and cdapp = '"+selected_app+"' "+cond+" order by idnota desc";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $idnota="";
			String $link="";
			String $img="";
			String $titolo="";
			String $nota="";
			String $stato="";
			String $dtnot="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$idnota=rs.getString("idnota");
				$link=Utility.noNull(rs.getString("link"));
				if($link.length()==0)
					$link = "#";
				$img=rs.getString("img");
				$titolo=rs.getString("titolo");
				$nota=rs.getString("nota");
				$stato=""+rs.getString("stato");
				$stato=Utility.noNull($stato);
				
				if($stato.trim().length()==0)
					$stato = "<span class=\"badge bg-danger\">NEW</span>";
				else
					$stato = "";
				$dtnot = dateIta.format(rs.getTimestamp("dtnot"));
				
				notif.add(new Notification($idnota,$link,$img,$titolo,$nota,$stato,$dtnot,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			// Segna le notifiche come lette
			sql   = "update znotif set stato = 'A' where idusr = '"+idusr+"' and cdapp = '"+selected_app+"' and (stato <> 'A' or stato is null) ";
			System.out.println(sql);
			st.executeUpdate(sql);
			
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
		obj.put("apps", apps);
		obj.put("sts", sts);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("all_active", all_active);
		
		obj.put("app", selected_app);
		obj.put("selected_sts", selected_sts);
		
		
		return new ModelAndView("/app/config/notification-list", obj);
	}
	
	private static int not4App(Connection conn, String idusr, String cdapp) {
		
		int nr = 0;
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql   = "select count(idnota) num from znotif where idusr = '"+idusr+"' and cdapp = '"+cdapp+"' and (stato <> 'A' or stato is null) ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				nr=rs.getInt("num");
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
			}
			catch(Exception e) {}
		}
		
		return nr;
		
		
	}
}
