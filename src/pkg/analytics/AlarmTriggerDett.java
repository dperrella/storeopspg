package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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

import pkg.beans.Eventcode;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AlarmTriggerDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		String language = (String) session.getAttribute("language");
		
		List<Eventcode> ec = new ArrayList<Eventcode>();
		
		// Pulisce la lista
		ec.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		System.out.println(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		
		try {
			
			// Campi database
			String $id="";
			String $code="";
			String $description_it="";
			String $description_en="";
			String $threshold = "";
			String $timeunit = "";
			String $alarm1 = "";
			String $alarm2 = "";
			String $adaptive = "";
			String $lastrun = "";
			String $controlfrequency = "";
			String $tipoTrigger = ""; // Mono - Rapporto
			String $sign = "";
			String $hour_from = "";
			String $hour_to = "";
			String $formula = "";
			String $orario = "";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			conn = Utility.getConnAnalytics();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$code = req.getParameter("code");
					$description_it = req.getParameter("description_it");
					$description_en = req.getParameter("description_en");
					$threshold = req.getParameter("threshold");
					$timeunit = req.getParameter("timeunit");
					$alarm1 = req.getParameter("alarm1");
					$alarm2 = req.getParameter("alarm2");
					$adaptive = req.getParameter("adaptive");
					$controlfrequency = req.getParameter("controlfrequency");
					$sign = req.getParameter("sign");
					
					$hour_from = req.getParameter("hour_from");
					$hour_to = req.getParameter("hour_to");
					$formula = req.getParameter("formula");
					
					if($alarm2.trim().length()==0)
						$alarm2 = null;
					$adaptive=Utility.noNull($adaptive);
					if($adaptive.length()==0)
						$adaptive = "f";
					
					boolean b_ad = false;
					if($adaptive.equals("t"))
						b_ad = true;
					else if($adaptive.equals("f"))
						b_ad = false;
					
					if($hour_from.trim().length()==0)
						$hour_from = null;
					else
						$hour_from = "'"+$hour_from+"'";
					
					if($hour_to.trim().length()==0)
						$hour_to = null;
					else
						$hour_to = "'"+$hour_to+"'";
					
					if($formula.trim().length()==0)
						$formula = null;
					
					$orario = req.getParameter("orario");
					
					sql   = "insert into an_alarm_report "+
							"(code, description, threshold, timeunit, alarm1, alarm2, adaptive, "
							+ "controlfrequency, sign, hour_from, hour_to, formula, store_time) "+
							"values "+
							"(?, ?, ?, ?, ?, ?, ?, ?, ?, "+$hour_from+" , "+$hour_to+" , ?, ?) RETURNING id ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $code);
					st.setString(2, $description_it);
					st.setDouble(3, Double.parseDouble($threshold));
					st.setInt(4, Integer.parseInt($timeunit));
					st.setString(5, $alarm1);
					st.setString(6, $alarm2);
					st.setBoolean(7, b_ad);
					st.setInt(8, Integer.parseInt($controlfrequency));
					st.setString(9, $sign);
					st.setString(10, $formula);
					st.setBoolean(11, Boolean.parseBoolean($orario));
					
					// Esegue l'sql
					rs = st.executeQuery();
					
					if(rs.next()) {
						$id = rs.getString(1);
						System.out.println("ID: "+rs.getString(1));
					}
					else {
						$id = "-1";
						System.out.println("ID: -1");
					}
					rs.close();
				
					// Inserisce le lingue
					// -----
					// IT
					sql   = "insert into lang_alarm_report "
							+ "(id_alarm_report, lang, description) "
							+ "values"
							+ "(?, ?, ?) ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					st.setString(1, $id);
					st.setString(2, "IT");
					st.setString(3, $description_it);
					
					// Esegue l'sql
					st.executeUpdate();
					
					// -----
					// EN
					sql   = "insert into lang_alarm_report "
							+ "(id_alarm_report, lang, description) "
							+ "values"
							+ "(?, ?, ?) ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					st.setString(1, $id);
					st.setString(2, "EN");
					st.setString(3, $description_en);
					
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
					$description_it = req.getParameter("description_it");
					$description_en = req.getParameter("description_en");
					$threshold = req.getParameter("threshold");
					$timeunit = req.getParameter("timeunit");
					$alarm1 = req.getParameter("alarm1");
					$alarm2 = req.getParameter("alarm2");
					$adaptive = req.getParameter("adaptive");
					$controlfrequency = req.getParameter("controlfrequency");
					$sign = req.getParameter("sign");
					$hour_from = req.getParameter("hour_from");
					$hour_to = req.getParameter("hour_to");
					$formula = req.getParameter("formula");
					
					if($alarm2.trim().length()==0)
						$alarm2 = null;
					$adaptive=Utility.noNull($adaptive);
					if($adaptive.length()==0)
						$adaptive = "f";
					
					boolean b_ad = false;
					if($adaptive.equals("t"))
						b_ad = true;
					else if($adaptive.equals("f"))
						b_ad = false;
					
					if($hour_from.trim().length()==0)
						$hour_from = null;
					else
						$hour_from = "'"+$hour_from+"'";
					
					if($hour_to.trim().length()==0)
						$hour_to = null;
					else
						$hour_to = "'"+$hour_to+"'";
					
					if($formula.trim().length()==0)
						$formula = null;
					
					$orario = req.getParameter("orario");
					
					sql  =  "update an_alarm_report "+
							"set code = ?, description = ?, threshold = ?, timeunit = ?, "+
							"alarm1 = ?, alarm2 = ?, adaptive = ?, controlfrequency = ?, sign = ?, "+
							"hour_from = "+$hour_from+" , hour_to = "+$hour_to+" , formula = ? , store_time = ? "+
							"where id = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $code);
					st.setString(2, $description_it);
					st.setDouble(3, Double.parseDouble($threshold));
					st.setInt(4, Integer.parseInt($timeunit));
					st.setString(5, $alarm1);
					st.setString(6, $alarm2);
					st.setBoolean(7, b_ad);
					st.setInt(8, Integer.parseInt($controlfrequency));
					st.setString(9, $sign);
					st.setString(10, $formula);
					st.setBoolean(11, Boolean.parseBoolean($orario));
					
					st.setString(12, $id);
					
					// Esegue l'sql
					st.executeUpdate();
					
					// Lingue
					sql  =  "update lang_alarm_report "
							+ "set description = ? "
							+ "where id_alarm_report = ? and lang = ? ";
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $description_it);
					st.setString(2, $id);
					st.setString(3, "IT");
							
					
					sql  =  "update lang_alarm_report "
							+ "set description = ? "
							+ "where id_alarm_report = ? and lang = ? ";
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $description_en);
					st.setString(2, $id);
					st.setString(3, "EN");
							
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					sql   = "delete from lang_alarm_report where id_alarm_report = ? ";
					st = conn.prepareStatement(sql);
					st.setString(1, $id);
					st.executeUpdate();
					
					sql   = "delete from an_alarm_report where id = ? ";
					st = conn.prepareStatement(sql);
					st.setString(1, $id);
					st.executeUpdate();
					
					// *blank id
					$id = "";
					
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
				
				
				// ===================
				// Dettaglio / Modifica / Elimina 
				// sql da eseguire
				sql  =  "select a.id, a.code, b.description description_it, c.description description_en, a.threshold, a.timeunit, "+
						"a.alarm1, a.alarm2, a.adaptive, a.controlfrequency, a.lastrun, a.sign, "+
						"a.hour_from, a.hour_to, a.formula, a.store_time "+
						"from an_alarm_report a, lang_alarm_report b, lang_alarm_report c "+
						"where a.id = b.id_alarm_report and b.lang = 'IT' "+
						"and a.id = c.id_alarm_report and c.lang = 'EN' "+
						"and a.id = ? ";
				System.out.println(sql);
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $id);
				// Esegue l'sql
				rs=st.executeQuery();
				
				if(rs.next()) {
					$code=rs.getString("code");
					$description_it=rs.getString("description_it");
					$description_en=rs.getString("description_en");
					$threshold = rs.getString("threshold");
					$timeunit = rs.getString("timeunit");
					$alarm1 = rs.getString("alarm1");
					$alarm2 = rs.getString("alarm2");
					$adaptive = rs.getString("adaptive");
					$lastrun = rs.getString("lastrun");
					$controlfrequency = rs.getString("controlfrequency");
					$sign = rs.getString("sign");
					$hour_from = rs.getString("hour_from");
					$hour_to = rs.getString("hour_to");
					$formula = rs.getString("formula");
					if(rs.getBoolean("store_time"))
						$orario = "true";
					else
						$orario = "false";
				}
				rs.close();
				st.close();
				
			}
			
			$alarm2 = Utility.noNull($alarm2);
			
			if($alarm2.length()==0)
				$tipoTrigger = "1";
			else
				$tipoTrigger = "2";
			
			if($adaptive.equals("t"))
				$adaptive = "checked";
			
			// ====================
			// Lista Eventcode
			sql  =  "select * from analytics_eventcode "
					+ "order by CAST(coalesce(code, '0') AS integer) ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $ec_code="";
			String $ec_description="";
			String $range_description="";
			
			while(rs.next()) {
				
				count+=1;
				
				$ec_code=rs.getString("code");
				$ec_description=rs.getString("description");
				$range_description=rs.getString("range_description");
				
				ec.add(new Eventcode($ec_code,$ec_description,$range_description,"","","",""));
				
			}
			rs.close();
			st.close();
			
			obj.put("id", $id);
			obj.put("code", $code);
			obj.put("description_it", $description_it);
			obj.put("description_en", $description_en);
			obj.put("threshold", $threshold);
			obj.put("timeunit", $timeunit);
			obj.put("alarm1", $alarm1);
			obj.put("alarm2", $alarm2);
			obj.put("adaptive", $adaptive);
			obj.put("lastrun", $lastrun);
			obj.put("controlfrequency", $controlfrequency);
			obj.put("tipoTrigger", $tipoTrigger);
			obj.put("sign", $sign);
			obj.put("hour_from", $hour_from);
			obj.put("hour_to", $hour_to);
			obj.put("formula", $formula);
			obj.put("orario", $orario);
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Alarm Trigger");
		obj.put("tipoForm", tipoForm);
		
		obj.put("ec", ec);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/app/report/alarm-trigger-dett", obj);
	}
	
}
