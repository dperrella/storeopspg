package pkg.analytics2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class FinancialCalendarDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		String customer_id = req.getParameter("idcus");
		
		String btnActive = "";
		String key = "";
		
		DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
			// Campi database
			String $year="";
			String $fromdate="";
			String $todate="";
			
			conn = Utility.getConnData();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$year = req.getParameter("year");
					$fromdate = req.getParameter("fromdate");
					$todate = req.getParameter("todate");
						
					sql   = "insert into financial_calendar "+
							"(year, fromdate, todate, customer_id) "+
							"values "+
							"(?, ?, ?, ?) RETURNING year ";
					
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($year));
					st.setDate(2, java.sql.Date.valueOf($fromdate));
					st.setDate(3, java.sql.Date.valueOf($todate));
					st.setInt(4, Integer.parseInt(customer_id));
					
					// Esegue l'sql
					rs = st.executeQuery();
						
					if(rs.next()) {
						$year = rs.getString(1);
						System.out.println("year: "+rs.getString(1));
					}
					else {
						$year= "-1";
						System.out.println("year: -1");
					}
					rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					//tipoForm = "M";
					tipoForm = "I";
				
				}
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MOD...");
					
					$year = req.getParameter("year");
					$fromdate = req.getParameter("fromdate");
					$todate = req.getParameter("todate");
					
					sql  =  "update financial_calendar "+
							"set year = ?, fromdate = ?, todate = ? "+
							"where year = ? and customer_id = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri			
					st.setInt(1, Integer.parseInt($year));
					st.setDate(2, java.sql.Date.valueOf($fromdate));
					st.setDate(3, java.sql.Date.valueOf($todate));
					st.setInt(4, Integer.parseInt($year));
					st.setInt(5, Integer.parseInt(customer_id));
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					$year = req.getParameter("year");
					
					sql   = "delete from financial_calendar where year = ? and customer_id = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($year));
					st.setInt(2, Integer.parseInt(customer_id));
					
					// Esegue l'sql
					st.executeUpdate();
					
					// *blank id
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				key = "readonly";
				
				$year = req.getParameter("year");
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from financial_calendar where year = ? and customer_id = ?";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setInt(1, Integer.parseInt($year));
				st.setInt(2, Integer.parseInt(customer_id));
				// Esegue l'sql
				
				rs=st.executeQuery();
				
				if(rs.next()) {
					$year=rs.getString("year");
					$fromdate=iso.format(rs.getDate("fromdate"));
					$todate=iso.format(rs.getDate("todate"));
				}
				rs.close();
				
			}
			
			obj.put("year", $year);
			obj.put("fromdate", $fromdate);
			obj.put("todate", $todate);
			obj.put("idcus", customer_id);
			
			
			if(st!=null)
				st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Finacial Calendar");
		
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		

		
		return new ModelAndView("/analytics2/config/finacial-calendar-dett", obj);
	}
	
}
