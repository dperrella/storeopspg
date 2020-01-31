package pkg.HR;

import java.sql.Connection;
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
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Employee;
import pkg.beans.HR.Minnum_employee;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Minnum_employeeDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Minnum_employee> minnum = new ArrayList<Minnum_employee>();
		List<Employee> emp = new ArrayList<Employee>();
		List<Tables> wk = new ArrayList<Tables>();
				
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		String currentWeek = "";
		
		
		// Pulisce la lista
		minnum.clear();
		emp.clear();
		wk.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		String msgEli = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		
		// Anno
		String reqYear = ""+req.getParameter("year");
		reqYear = Utility.noNull(reqYear);
		
		if(reqYear.length()==0)
			reqYear = currentYear;
		
		String reqWeek = ""+req.getParameter("week");
		reqWeek = Utility.noNull(reqWeek);
		
		String reqDay = ""+req.getParameter("day");
		reqDay = Utility.noNull(reqDay);
		
		String reqId_departments = ""+req.getParameter("id_departments");
		reqId_departments = Utility.noNull(reqId_departments);
		
		try {
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance(Locale.GERMAN); 
			c.set(Integer.parseInt(reqYear), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			System.out.println(currentWeek);
			
			// Per settimanale
			Calendar cal = Calendar.getInstance(Locale.GERMAN);  
			// Imposta l'anno corrente
			cal.set(Integer.parseInt(reqYear), 0, 1);
			
			String week = "";
			
			// campi tables
			String $xcdtb = "SET";
			String $xcdel = "";
			String $xdtab = "";
			String sel = "";
			for (int i=1; i<=weeks; i++) {
				  
				week = "00"+i;
				week = week.substring(week.length()-2,week.length());
				
				// valorizza xcdel col numero della settimana
				$xcdel = week;
				
				cal.set(Calendar.WEEK_OF_YEAR, i);        
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				// valorizza il primo giorno della settimana
				$xdtab=sdf.format(cal.getTime())+" - ";
				
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				// valorizza l'ultimo giorno della settimana
				$xdtab+=sdf.format(cal.getTime());
				
				// Settimana attuale
				if($xcdel.equals(currentWeek)) {
					sel = "selected ";
				}
				
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,sel,"","",""));
				
				//*blank bariabili
				$xdtab = "";
				sel = "";
		  	}
			
			
			// Campi database
			String $id="";
			String $id_departments="";
			String $description="";
			String $plan_year="";
			String $plan_week="";
			String $plan_day="";
			String $plan_hour="";
			String $value="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id_departments = ""+req.getParameter("id_departments");
			$id_departments=Utility.noNull($id_departments);
			
			$plan_year = ""+req.getParameter("year");
			$plan_year=Utility.noNull($plan_year);
			
			$plan_week = ""+req.getParameter("week");
			$plan_week=Utility.noNull($plan_week);
			
			$plan_day = ""+req.getParameter("day");
			$plan_day=Utility.noNull($plan_day);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					
					// Cancella il record
					sql   = "delete from hr_minnum_employee "+
							"where id_departments = '"+$id_departments+"' "+
							"and plan_year = '"+$plan_year+"' "+
							"and plan_week = '"+$plan_week+"' "+
							"and plan_day = '"+$plan_day+"' ";
					st.executeUpdate(sql);
					
					// Inserisce i record
					// Riga
					String[] cd = req.getParameterValues("plan_hour");
					String plan_hour 	= "";
					String value	= "";
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							System.out.println(cd.length);
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								plan_hour = req.getParameterValues("plan_hour")[i];
								value = req.getParameterValues("value")[i];
								
								if(value.length()==0)
									value="0";
								
								sql  =  "insert into hr_minnum_employee "+
										"(id_departments, plan_year, plan_week, plan_day, plan_hour, value) "+
										"values "+
										"('"+$id_departments+"', '"+$plan_year+"', '"+$plan_week+"', '"+$plan_day+"', '"+plan_hour+"', "+value+" ) ";
								st.executeUpdate(sql);
								
							}
							
						}
						
					}	
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					
					// Cancella il record
					sql   = "delete from hr_minnum_employee "+
							"where id_departments = '"+$id_departments+"' "+
							"and plan_year = '"+$plan_year+"' "+
							"and plan_week = '"+$plan_week+"' "+
							"and plan_day = '"+$plan_day+"' ";
					st.executeUpdate(sql);
					
					// Inserisce i record
					// Riga
					String[] cd = req.getParameterValues("plan_hour");
					String plan_hour 	= "";
					String value	= "";
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							System.out.println(cd.length);
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								plan_hour = req.getParameterValues("plan_hour")[i];
								value = req.getParameterValues("value")[i];
								
								sql  =  "insert into hr_minnum_employee "+
										"(id_departments, plan_year, plan_week, plan_day, plan_hour, value) "+
										"values "+
										"('"+$id_departments+"', '"+$plan_year+"', '"+$plan_week+"', '"+$plan_day+"', '"+plan_hour+"', "+value+" ) ";
								st.executeUpdate(sql);
								
							}
							
						}
						
					}	
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from hr_minnum_employee "+
							"where id_departments = '"+$id_departments+"' "+
							"and plan_year = '"+$plan_year+"' "+
							"and plan_week = '"+$plan_week+"' "+
							"and plan_day = '"+$plan_day+"' ";
					st.executeUpdate(sql);
					
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
				sql  =  "select a.id, a.id_departments, b.description, "+
						"a.plan_year, a.plan_week, a.plan_day, a.plan_hour, a.value "+
						"from hr_minnum_employee a, hr_departments b "+
						"where a.id_departments = b.id "+
						"and a.id_departments = '"+reqId_departments+"' "+
						"and a.plan_year = '"+reqYear+"' "+
						"and a.plan_week = '"+reqWeek+"' "+
						"and a.plan_day = '"+reqDay+"' ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					$id=rs.getString("id");
					$id_departments=rs.getString("id_departments");
					$description=rs.getString("description");
					$plan_year=rs.getString("plan_year");
					$plan_week=rs.getString("plan_week");
					$plan_day=rs.getString("plan_day");
					$plan_hour=rs.getString("plan_hour");
					$value=rs.getString("value");
					
					minnum.add(new Minnum_employee($id,$id_departments,$description,$plan_year,$plan_week,$plan_day,$plan_hour,$value,$cmp1,$cmp2,$cmp3,$cmp4));
					
				}
				rs.close();
				
				// Reperisce la descrizione del dipartimento
				$description = getDescription(conn, reqId_departments);
				
				// ====================
				// Lista dipendenti nella settimana/negozio del ruolo
				sql  =  "select id_employee, name, surname "+
						"from hr_planification p, hr_employee e "+
						"where e.id = p.id_employee "+
						"and plan_year = '"+reqYear+"' and plan_week = '"+reqWeek+"' "+
						"and id_departments = '"+$id_departments+"' "+
						"group by id_employee, name, surname ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					emp.add(new Employee(rs.getString("id_employee"),rs.getString("name"),rs.getString("surname"),"","","","","","",
							"","","","","","","","","","","","","",""));
					
				}
				rs.close();
				
			}
			else if(tipoForm.equals("I")) {
				
				//Crea una schiera
				for(int i=6; i<=24; i++) {
					
					minnum.add(new Minnum_employee("",reqId_departments,$description,reqYear,reqWeek,reqDay,""+i,"",$cmp1,$cmp2,$cmp3,$cmp4));
					
				}
				
			}
			
			obj.put("id_departments", reqId_departments);
			obj.put("description", $description);
			obj.put("plan_year", reqYear);
			obj.put("plan_week", reqWeek);
			obj.put("plan_day", reqDay);
			obj.put("minnum", minnum);
			obj.put("weeks", wk);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Numero Minimo Dipendenti");
		obj.put("tipoForm", tipoForm);
		
		obj.put("emp", emp);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/planner/minnum_employee-dett", obj);
	}
	
	public static String getDescription(Connection conn, String id_departments) throws SQLException {
		
		String description = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			st=conn.createStatement();
			sql   = "select description from hr_departments where id = '"+id_departments+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				description = rs.getString("description");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		return description;
	}
	
}
