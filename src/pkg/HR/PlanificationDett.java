package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
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

import pkg.beans.HR.PlanificationType;
import pkg.beans.HR.Employee;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlanificationDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<PlanificationType> plantype = new ArrayList<PlanificationType>();
		List<Employee> emp = new ArrayList<Employee>();		
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat hourIta = new SimpleDateFormat("kk:mm");
		
		DateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy kk:mm");
		
		
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		plantype.clear();
		emp.clear();
		
		
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
		
		try {
			
			
			
			// Campi database
			String $id = "";
			String $id_employee = "";
			String $name = "";
			String $surname = "";
			String $id_departments = "";
			String $department_description = "";
			String $datetime_start = "";
			String $datetime_end = "";
			String $id_planification_type = "";
			String $planification_type_description = "";
			String $plan_year = "";
			String $plan_week = "";
			String $plan_day = "";
			
			// Ulteriori campi maschera
			String day = "";
			String dayFin = "";
			String ora_ini = "";
			String ora_fin = "";
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			// Per pulsante back
			String req_id_departments = req.getParameter("id_departments");
			String req_week = req.getParameter("week");
			String req_year = req.getParameter("year");
			
			obj.put("req_id_departments", req_id_departments);
			obj.put("req_week", req_week);
			obj.put("req_year", req_year);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			// ===================
			// planification_type
			// Campi AAGE
			String id="";
			String description="";
			String min_time="";
			String max_time="";
			String direction="";
			String approvation="";
			String color="";
			
			// Lettura hr_planification_type
			sql   = "select * from hr_planification_type ";
			
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				id = rs.getString("id").trim();
				description = rs.getString("description").trim();
				min_time = rs.getString("min_time").trim();
				max_time = rs.getString("max_time").trim();
				direction = rs.getString("direction").trim();
				approvation = rs.getString("approvation").trim();
				color = rs.getString("color").trim();
				
				$cmp1 = "";
				$cmp2 = "";
				$cmp3 = "";
				$cmp4 = "";
				
				plantype.add(new PlanificationType(id, description, min_time, max_time, 
						direction, approvation, color, $cmp1, $cmp2, $cmp3, $cmp4));	
			}
			rs.close();
			
			if(azione.length()>0) {
				
				String last_ts = "";
				Calendar c = Calendar.getInstance(Locale.GERMAN);   
				c.setTime(new Date());
				last_ts = ""+(c.getTimeInMillis() / 1000);
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$id_employee=req.getParameter("id_employee");
					$id_departments=req.getParameter("id_departments");
					$datetime_start="";
					$datetime_end="";
					$id_planification_type=req.getParameter("id_planification");
					$plan_year="";
					$plan_week="";
					$plan_day="";
					
					day = req.getParameter("day");
					dayFin = day;
					ora_ini = req.getParameter("ora_ini");
					ora_fin = req.getParameter("ora_fin");
					
					// Anno
					$plan_year = day.substring(6,10);
					
					// Settimana
					Date dateDay = dateIta.parse(day);
					Calendar cal = Calendar.getInstance(Locale.GERMAN);   
					cal.setTime(dateDay);
					$plan_week = ""+cal.get(Calendar.WEEK_OF_YEAR);
					
					// Giorno
					$plan_day = ""+cal.get(Calendar.DAY_OF_WEEK);
					if($plan_day.equals("1"))
						$plan_day = "6";
					else if($plan_day.equals("2"))
						$plan_day = "0";
					else if($plan_day.equals("3"))
						$plan_day = "1";
					else if($plan_day.equals("4"))
						$plan_day = "2";
					else if($plan_day.equals("5"))
						$plan_day = "3";
					else if($plan_day.equals("6"))
						$plan_day = "4";
					else if($plan_day.equals("7"))
						$plan_day = "5";
					
					// Datetime_start / Datetime_end
					String dateTimeIni = day+" "+ora_ini;
					String dateTimeFin = day+" "+ora_fin;
					
					Date dateIni = dateTime.parse(dateTimeIni);
					Date dateFin = dateTime.parse(dateTimeFin);
					
					cal.setTime(dateIni);
					$datetime_start = ""+(cal.getTimeInMillis()/1000);
					cal.setTime(dateFin);
					// =======================================
					// Se ora fine = "24:00" aggiunge 1 giorno
					if(ora_fin.equals("24:00")) {
						cal.add(Calendar.DATE, 1);
					}
					// =======================================
					$datetime_end = ""+(cal.getTimeInMillis()/1000);
					
					sql   = "insert into hr_planification "+
							"(id_employee, id_departments, datetime_start, datetime_end,"+
							" id_planification_type, plan_year, plan_week, plan_day, last_ts) "+
							"values "+
							"('"+$id_employee+"', '"+$id_departments+"', "+$datetime_start+", "+$datetime_end+", "+
							 "'"+$id_planification_type+"', '"+$plan_year+"', '"+$plan_week+"', '"+$plan_day+"', "+last_ts+" ) RETURNING id ";
					System.out.println(sql);
					
					rs = st.executeQuery(sql);
					if(rs.next()) {
						$id = rs.getString(1);
						System.out.println("ID: "+rs.getString(1));
					}
					else {
						$id = "-1";
						System.out.println("ID: -1");
					}
					rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$id_employee=req.getParameter("id_employee");
					$id_departments=req.getParameter("id_departments");
					$datetime_start="";
					$datetime_end="";
					$id_planification_type=req.getParameter("id_planification");
					$plan_year="";
					$plan_week="";
					$plan_day="";
					
					day = req.getParameter("day");
					ora_ini = req.getParameter("ora_ini");
					ora_fin = req.getParameter("ora_fin");
					
					// Anno
					$plan_year = day.substring(6,10);
					
					// Settimana
					Date dateDay = dateIta.parse(day);
					Calendar cal = Calendar.getInstance(Locale.GERMAN);   
					cal.setTime(dateDay);
					$plan_week = ""+cal.get(Calendar.WEEK_OF_YEAR);
					
					// Giorno
					$plan_day = ""+cal.get(Calendar.DAY_OF_WEEK);
					System.out.println($plan_day);
					if($plan_day.equals("1"))
						$plan_day = "6";
					else if($plan_day.equals("2"))
						$plan_day = "0";
					else if($plan_day.equals("3"))
						$plan_day = "1";
					else if($plan_day.equals("4"))
						$plan_day = "2";
					else if($plan_day.equals("5"))
						$plan_day = "3";
					else if($plan_day.equals("6"))
						$plan_day = "4";
					else if($plan_day.equals("7"))
						$plan_day = "5";
					
					System.out.println($plan_day);
					
					
					// Datetime_start / Datetime_end
					String dateTimeIni = day+" "+ora_ini;
					String dateTimeFin = day+" "+ora_fin;
					
					Date dateIni = dateTime.parse(dateTimeIni);
					Date dateFin = dateTime.parse(dateTimeFin);
					
					cal.setTime(dateIni);
					$datetime_start = ""+(cal.getTimeInMillis()/1000);
					cal.setTime(dateFin);
					$datetime_end = ""+(cal.getTimeInMillis()/1000);
					
					
					sql  =  "update hr_planification "+
							"set id_employee = '"+$id_employee+"', "+
							"id_departments = '"+$id_departments+"', "+
							"datetime_start = "+$datetime_start+", datetime_end = "+$datetime_end+", "+
							"plan_year = '"+$plan_year+"', plan_week = '"+$plan_week+"', plan_day = '"+$plan_day+"', "+
							"id_planification_type = '"+$id_planification_type+"', last_ts = "+last_ts+" "+
							"where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from hr_planification where id = '"+$id+"' ";
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
				// Dettaglio / Modifica / Elimina Planification
				sql  =  "select p.id, p.id_employee, p.id_departments, "+
						"to_timestamp(datetime_start) date_start, to_timestamp(datetime_end) date_end, "+
						"datetime_start, datetime_end, "+
						"name, surname, d.description, id_planification_type, t.description planification_type_description, "+
						"plan_year, plan_week, plan_day "+
						"from hr_planification p "+
						"left join hr_planification_type t on t.id = p.id_planification_type, "+
						"hr_employee e, hr_departments d "+
						"where p.id_employee = e.id and p.id_departments = d.id and p.id = '"+$id+"' ";
						
				rs=st.executeQuery(sql);
				
				
				if(rs.next()) {
					$id=rs.getString("id");
					$id_employee=rs.getString("id_employee");
					$name=rs.getString("name");
					$surname=rs.getString("surname");
					$id_departments=rs.getString("id_departments");
					$department_description=rs.getString("description");
					$datetime_start=rs.getString("date_start");
					$datetime_end=rs.getString("date_end");
					$id_planification_type=rs.getString("id_planification_type");
					$planification_type_description=rs.getString("planification_type_description");
					$plan_year=rs.getString("plan_year");
					$plan_week=rs.getString("plan_week");
					$plan_day=rs.getString("plan_day");
					
					day = dateIta.format(rs.getDate("date_start"));
					ora_ini = hourIta.format(rs.getTime("date_start"));
					ora_fin = hourIta.format(rs.getTime("date_end"));
				}
				rs.close();
				
				// ====================
				// Lista dipendenti nella settimana/negozio del ruolo
				sql  =  "select id_employee, name, surname "+
						"from hr_planification p, hr_employee e "+
						"where e.id = p.id_employee "+
						"and plan_year = '"+$plan_year+"' and plan_week = '"+$plan_week+"' "+
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
			
			obj.put("id", $id);
			obj.put("id_departments", $id_departments);
			obj.put("department_description", $department_description);
			obj.put("id_employee", $id_employee);
			obj.put("name", $name);
			obj.put("surname", $surname);
			obj.put("id_planification_type", $id_planification_type);
			obj.put("day", day);
			obj.put("ora_ini", ora_ini);
			obj.put("ora_fin", ora_fin);
			
			obj.put("emp", emp);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Pianificazione");
		obj.put("tipoForm", tipoForm);
		

		obj.put("plantype", plantype);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/planner/planification-dett", obj);
	}
	
}
