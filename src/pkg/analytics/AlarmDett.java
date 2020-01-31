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

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AlarmDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> tab = new ArrayList<Tables>();
		
		// Pulisce la lista
		tab.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		String key = "";
		
		try {
			
			// Campi database
			String $code="";
			String $description="";
			String $range_description="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$code = ""+req.getParameter("code");
			$code=Utility.noNull($code);
			
			conn = Utility.getConnAnalytics();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$code = req.getParameter("code");
					$description = req.getParameter("description");
					$range_description = req.getParameter("range_description");
					
					sql   = "insert into analytics_eventcode "+
							"(code, description, range_description) "+
							"values "+
							"(?, ?, ?) ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $code);
					st.setString(2, $description);
					st.setString(3, $range_description);
					
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MOD...");
					
					$code = req.getParameter("code");
					$description = req.getParameter("description");
					$range_description = req.getParameter("range_description");
					
					sql  =  "update analytics_eventcode "+
							"set description = ?, range_description = ? "+
							"where code = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $description);
					st.setString(2, $range_description);
					st.setString(3, $code);
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					sql   = "delete from analytics_eventcode where code = ? ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $code);
					
					// Esegue l'sql
					st.executeUpdate();
					
					// *blank id
					$code = "";
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
					key = "readonly";	
				}
				
				
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from analytics_eventcode where code = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $code);
				// Esegue l'sql
				rs=st.executeQuery();
				
				if(rs.next()) {
					$description=rs.getString("description");
					$range_description=rs.getString("range_description");
				}
				rs.close();
				
				// ====================
				// Lista Devices con stesso allarme
				sql  =  "select a.iddevice, b.ip, c.description "
						+ "from rel_device_alarms a, devices b, stores c "
						+ "where a.iddevice = b.id and b.id_store = c.id "
						+ "and a.alarmcode = ? ";
				st = conn.prepareStatement(sql);
				st.setString(1, $code);
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					
					tab.add(new Tables("","","",
							rs.getString("iddevice"),rs.getString("ip"),rs.getString("description"),""));
					
				}
				rs.close();
				
			}
			
			obj.put("code", $code);
			obj.put("description", $description);
			obj.put("range_description", $range_description);
			
			if(st!=null)
				st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Eventcode");
		obj.put("tipoForm", tipoForm);
		
		obj.put("tab", tab);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		
		return new ModelAndView("/app/anag/alarm-dett", obj);
	}
	
}
