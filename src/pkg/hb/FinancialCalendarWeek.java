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

public class FinancialCalendarWeek extends AbstractController {
	
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
		String weekstart = req.getParameter("weekstart");
		String weeknumber = req.getParameter("weeknumber");
		String firstweek = req.getParameter("firstweek");
		
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
					st.setString(2, "W");
					st.setInt(3, Integer.parseInt(customer_id));
					
					// Esegue l'sql
					st.executeUpdate();
					
					
					
					
					
					// =======================
					// Settimane
					String optWE = "";
					
					Calendar c = Calendar.getInstance();
					c.set(Integer.parseInt(firstweek.substring(0, 4)), Integer.parseInt(firstweek.substring(5, 7))-1, Integer.parseInt(firstweek.substring(8, 10)));
					
					// Formato data
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					  
					// Numero di settimane
					int weeks = Integer.parseInt(weeknumber);
					
					// Per settimanale
					Locale lc = new Locale("en","UK");
					if(weekstart.equals("M")) {
						lc = new Locale("en","UK");
					}
					if(weekstart.equals("S")) {
						lc = new Locale("en","US");
					}
					
					// Per settimanale
					Calendar cal = Calendar.getInstance(lc);
					// Imposta l'anno corrente
					
					System.out.println(Integer.parseInt(firstweek.substring(0, 4))+" "+(Integer.parseInt(firstweek.substring(5, 7))-1)+" "+Integer.parseInt(firstweek.substring(8, 10)));
					System.out.println(weekstart);
					cal.set(Integer.parseInt(firstweek.substring(0, 4)), Integer.parseInt(firstweek.substring(5, 7))-1, Integer.parseInt(firstweek.substring(8, 10)));
					if(weekstart.equals("M")) {
						cal.setFirstDayOfWeek(Calendar.MONDAY);
					}
					if(weekstart.equals("S")) {
						cal.setFirstDayOfWeek(Calendar.SUNDAY);
					}
					//cal.set(Integer.parseInt(anno), 0, 1);
					//cal.set(Calendar.WEEK_OF_YEAR, i);        
					
					/*
					if(weekstart.equals("M")) {
						cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					}
					if(weekstart.equals("S")) {
						cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					}
					*/
					
					String week = "";
					
					// campi tables
					String $xcdtb = "SET";
					String $xcdel = "";
					String $xdtab = "";
					String $cmp1 = "";
					
					String dtda = "";
					String dtal = "";
					for (int i=1; i<=weeks; i++) {
						  
						week = "00"+i;
						week = week.substring(week.length()-2,week.length());
						
						// valorizza xcdel col numero della settimana
						$xcdel = week;
						
						/*
						if(weekstart.equals("M")) {
							cal.setFirstDayOfWeek(Calendar.MONDAY);
						}
						if(weekstart.equals("S")) {
							cal.setFirstDayOfWeek(Calendar.SUNDAY);
						}
						//cal.set(Integer.parseInt(anno), 0, 1);
						//cal.set(Calendar.WEEK_OF_YEAR, i);        
						
						if(weekstart.equals("M")) {
							cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
						}
						if(weekstart.equals("S")) {
							cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
						}
						*/
						
						dtda = sdf.format(cal.getTime());
						System.out.println(dtda);
						
						// valorizza il primo giorno della settimana
						$xdtab=sdf.format(cal.getTime())+" - ";
						
						cal.add(Calendar.DATE, 6);  
						//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
						
						// valorizza l'ultimo giorno della settimana
						$xdtab+=sdf.format(cal.getTime());
						
						dtal = sdf.format(cal.getTime());
						System.out.println(dtal);
						
						optWE+="<option value=\""+$xcdel+"\" "+$cmp1+">"+$xdtab+"</option>";
						
						
						sql   = "insert into financial_calendar2 "+
								"(caltype, year, calperiod, fromdate, todate, customer_id) "+
								"values "+
								"(?, ?, ?, ?, ?, ?) ";
						
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1,  caltype);
						st.setInt(2, Integer.parseInt(year));
						st.setInt(3, (i));
						st.setDate(4, java.sql.Date.valueOf(dtda));
						st.setDate(5, java.sql.Date.valueOf(dtal));
						st.setInt(6, Integer.parseInt(customer_id));
						
						st.executeUpdate();
						
						
						
						cal.add(Calendar.DATE, 1);  
						
						//*blank bariabili
						$xdtab = "";
						$cmp1 = "";
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
						
						// Cancellazione precedente
						sql   = "delete from financial_calendar2 where year = ? and caltype= ? and customer_id = ? ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setInt(1, Integer.parseInt(year));
						st.setString(2, "W");
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
							st.setInt(3, (i+1));
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
					st.setString(2, "W");
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
					
					fc.add(new FinancialCalendar(rs.getString("caltype"),rs.getInt("year"), rs.getInt("calperiod"), 
							iso.format(rs.getDate("fromdate")),
							iso.format(rs.getDate("todate")),
							rs.getInt("customer_id"),
							"","","",""));
					
					
				}
				rs.close();
				
			}
			
			obj.put("year", year);
			obj.put("caltype", caltype);
			obj.put("idcus", customer_id);
			obj.put("weekstart", weekstart);
			obj.put("weeknumber", weeknumber);
			obj.put("firstweek", firstweek);
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
				
		return new ModelAndView("/hb/config/finacial-calendar-week", obj);
	}
	
}
