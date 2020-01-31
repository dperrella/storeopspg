package pkg.analytics2;

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

import pkg.beans.AlarmTrigger2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AlarmTriggerList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		List<AlarmTrigger2> rep = new ArrayList<AlarmTrigger2>();
		
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
			conn = Utility.getConnData();
			
			if(azione.length()>0) {
				
				String id_alarm_report=req.getParameter("id_alarm_report");
				
				// Cancellazione lingue
				sql   = "delete from lang_alarm_report where id_alarm_report = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, id_alarm_report);
				
				// Esegue l'sql
				st.executeUpdate();
				
				sql   = "delete from an_alarm_report where id = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, id_alarm_report);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// *blank id
				id_alarm_report = "";
				
				msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
					"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				// Cancellazione report
				
			}
			// sql da eseguire
			sql  =  "select a.id, a.code, b.description, a.threshold, a.timeunit, "+
					"a.kpi, a.adaptive, a.controlfrequency, a.lastrun, a.sign, "+
					"hour_from, hour_to, store_time "+
					"from an_alarm_report a, lang_alarm_report b "+
					"where a.id = b.id_alarm_report and b.lang = '"+language+"' ";
			
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
			String $threshold="";
			String $timeunit="";
			String $kpi="";
			String $adaptive="";
			String $controlfrequency="";
			String $lastrun="";
			String $sign="";
			String $hour_from="";
			String $hour_to="";
			String $store_time="";
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$code=rs.getString("code");
				$description=rs.getString("description");
				$threshold=rs.getString("threshold");
				$timeunit=rs.getString("timeunit");
				$kpi=rs.getString("kpi");
				$adaptive=rs.getString("adaptive");
				$controlfrequency=rs.getString("controlfrequency");
				$lastrun=rs.getString("lastrun");
				$sign=rs.getString("sign");
				$hour_from=rs.getString("hour_from");
				$hour_to=rs.getString("hour_to");
				$store_time=rs.getString("store_time");
				
				rep.add(new AlarmTrigger2($id,$code,$description,$threshold,$timeunit,
						$kpi,$adaptive,$lastrun,$controlfrequency,$sign,
						$hour_from, $hour_to, $store_time,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Lista Alarm Triggers");
		obj.put("rep", rep);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/analytics2/config/alarm-trigger-list", obj);
	}
	
}
