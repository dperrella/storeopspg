package pkg.hb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import pkg.beans.FinancialCalendar;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class FinancialCalendarMonth extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<FinancialCalendar> fc = new ArrayList<FinancialCalendar>();
		fc.clear();
		
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
		String caltype = req.getParameter("caltype");
		String year = req.getParameter("year");
		String monthstart = req.getParameter("monthstart");
		
		String btnActive = "";
		String key = "";
		
		DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			
			// Campi database
			String $fromdate="";
			String $todate="";
			
			conn = Utility.getConnData();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Cancellazione precedente
					sql   = "delete from financial_calendar2 where year = ? and caltype= ? and customer_id = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt(year));
					st.setString(2, "M");
					st.setInt(3, Integer.parseInt(customer_id));
					
					// Esegue l'sql
					st.executeUpdate();
					
					
					// Inserimento
					System.out.println("INS...");
					
					int mm = 0;
					String yearCal=year;
					boolean yearChange = false;
					for(int i=0;i<=11;i++) {
						
						mm = i+(Integer.parseInt(monthstart));
						if(mm>11) {
							mm=mm-12;
							if(yearChange==false) {
								yearCal = ""+(Integer.parseInt(yearCal)+1);
								yearChange=true;
							}
						}
						Calendar c = Calendar.getInstance();
						c.set(Integer.parseInt(yearCal), mm, 1);
						
						$fromdate = iso.format(c.getTime());
						
						// fine mese
						c.set(Integer.parseInt(yearCal), (mm+1), 1);
						c.add(Calendar.DAY_OF_MONTH, -1);
						
						$todate = iso.format(c.getTime());
						
						
						System.out.println(mm+" "+$fromdate+" "+$todate);
						
						
						sql   = "insert into financial_calendar2 "+
								"(caltype, year, calperiod, fromdate, todate, customer_id) "+
								"values "+
								"(?, ?, ?, ?, ?, ?) ";
						
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1,  caltype);
						st.setInt(2, Integer.parseInt(year));
						st.setInt(3, (mm+1));
						st.setDate(4, java.sql.Date.valueOf($fromdate));
						st.setDate(5, java.sql.Date.valueOf($todate));
						st.setInt(6, Integer.parseInt(customer_id));
						
						st.executeUpdate();
						
					}
					
					tipoForm = "M";
				
				}
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MOD...");
					
					// prendiamo tutti i valori dell'input multiplo
					if (req.getParameterValues("fromdate").length >0){
						
						// riversiamo l'array
						String[] fromdate = req.getParameterValues("fromdate");
						String[] todate = req.getParameterValues("todate");
						String[] calperiod = req.getParameterValues("calperiod");
						
						// Cancellazione precedente
						sql   = "delete from financial_calendar2 where year = ? and caltype= ? and customer_id = ? ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setInt(1, Integer.parseInt(year));
						st.setString(2, "M");
						st.setInt(3, Integer.parseInt(customer_id));
						
						// Esegue l'sql
						st.executeUpdate();
						  
						// stampiamo i valori ottenuti
						for(int i=0; i<fromdate.length; i++) {
							
							System.out.println(fromdate[i]);
							System.out.println(todate[i]);
							
							sql   = "insert into financial_calendar2 "+
									"(caltype, year, calperiod, fromdate, todate, customer_id) "+
									"values "+
									"(?, ?, ?, ?, ?, ?) ";
							
							// Prepara il cursore
							st = conn.prepareStatement(sql);
							
							// Passa i parametri
							st.setString(1,  caltype);
							st.setInt(2, Integer.parseInt(year));
							st.setInt(3, Integer.parseInt(calperiod[i]));
							st.setDate(4, java.sql.Date.valueOf(fromdate[i]));
							st.setDate(5, java.sql.Date.valueOf(todate[i]));
							st.setInt(6, Integer.parseInt(customer_id));
							
							st.executeUpdate();
							
							
							
						}
					    
					}
					
					
					
					msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
						
				}
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					sql   = "delete from financial_calendar2 where year = ? and caltype= ? and customer_id = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt(year));
					st.setString(2, "M");
					st.setInt(3, Integer.parseInt(customer_id));
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				key = "readonly";
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"line line-dashed b-b line-lg pull-in\"></div><div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				
				DateFormat ita = new SimpleDateFormat("dd-MM-yyyy");
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from financial_calendar2 where year = ? and customer_id = ? and caltype = ? ";
				System.out.println(sql);
				System.out.println(year);
				System.out.println(customer_id);
				System.out.println(caltype);
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setInt(1, Integer.parseInt(year));
				st.setInt(2, Integer.parseInt(customer_id));
				st.setString(3, caltype);
				// Esegue l'sql
				
				rs=st.executeQuery();
				while(rs.next()) {
					
					String monthString;
			        switch (rs.getInt("calperiod")) {
			            case 1:  monthString = propLang.get("gen");
			                     break;
			            case 2:  monthString = propLang.get("feb");
			                     break;
			            case 3:  monthString = propLang.get("mar2");
			                     break;
			            case 4:  monthString = propLang.get("apr");
			                     break;
			            case 5:  monthString = propLang.get("mag");
			                     break;
			            case 6:  monthString = propLang.get("giu");
			                     break;
			            case 7:  monthString = propLang.get("lug");
			                     break;
			            case 8:  monthString = propLang.get("ago");
			                     break;
			            case 9:  monthString = propLang.get("set");
			                     break;
			            case 10: monthString = propLang.get("ott");
			                     break;
			            case 11: monthString = propLang.get("nov");
			                     break;
			            case 12: monthString = propLang.get("dic");
			                     break;
			            default: monthString = "Invalid month";
			                     break;
			        }
			        System.out.println(monthString);
					
					
					fc.add(new FinancialCalendar(rs.getString("caltype"),rs.getInt("year"), rs.getInt("calperiod"), 
							iso.format(rs.getDate("fromdate")),
							iso.format(rs.getDate("todate")),
							rs.getInt("customer_id"),
							monthString,"","",""));
					
					
				}
				rs.close();
				
			}
			
			obj.put("year", year);
			obj.put("caltype", caltype);
			obj.put("idcus", customer_id);
			obj.put("fc", fc);
			
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
				
		return new ModelAndView("/hb/config/finacial-calendar-month", obj);
	}
	
}
