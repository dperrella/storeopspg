package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Report;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReportList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		List<Report> rep = new ArrayList<Report>();
		
		// Pulisce la lista
		rep.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String language = (String) session.getAttribute("language");
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			
			if(azione.length()>0) {
				
				String id_report=req.getParameter("id_report");
				
				// Cancellazione lingue
				sql   = "delete from lang_report where id_report = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, id_report);
				
				// Esegue l'sql
				st.executeUpdate();
				
				sql   = "delete from an_report where id = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, id_report);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// *blank id
				id_report = "";
				
				msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
					"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				// Cancellazione report
				
			}
			// sql da eseguire
			sql  =  "select a.id, a.code, b.description, a.id_report_type, a.id_subreport_type, "+
					"alarm1, alarm2, storico, benchmark, icon, c.description ds_report_type, "+
					"d.description ds_subreport_type "+
					"from an_report a, lang_report b, lang_report_type c, lang_subreport_type d "+
					"where a.id = b.id_report and b.lang = '"+language+"' "+
					"and a.id_report_type = c.id_report_type and c.lang = '"+language+"' "+
					"and a.id_subreport_type = d.id_subreport_type and d.lang = '"+language+"' ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" where b.description like ? ";
			}
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $id="";
			String $code="";
			String $description="";
			String $id_report_type="";
			String $ds_report_type="";
			String $id_subreport_type="";
			String $ds_subreport_type="";
			String $alarm1="";
			String $alarm2="";
			String $historic="";
			String $benchmark="";
			String $icon="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$code=rs.getString("code");
				$description=rs.getString("description");
				$id_report_type=rs.getString("id_report_type");
				$ds_report_type=rs.getString("ds_report_type");
				$id_subreport_type=rs.getString("id_subreport_type");
				$ds_subreport_type=rs.getString("ds_subreport_type");
				$alarm1=rs.getString("alarm1");
				$alarm2=rs.getString("alarm2");
				$historic=rs.getString("storico");
				$benchmark=rs.getString("benchmark");
				$icon=rs.getString("icon");
				
				rep.add(new Report($id,$code,$description,$id_report_type,$ds_report_type,
						$id_subreport_type,$ds_subreport_type,$alarm1,$alarm2,
						$historic,$benchmark,$icon,$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Lista Report");
		obj.put("rep", rep);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/report/report-list", obj);
	}
	
}
