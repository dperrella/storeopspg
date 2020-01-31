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

import pkg.beans.HR.Employee;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelDepEmpDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Employee> emp = new ArrayList<Employee>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
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
			String $id="";
			String $id_employee="";
			String $emp_description="";
			String $id_department="";
			String $dep_description="";
			String $id_roles = "";
			String $rol_description="";
			String $date_start="";
			String $date_end="";
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			if(tipoForm.equals("I")) {
				$id_employee = ""+req.getParameter("id_employee");
				$id_employee = Utility.noNull($id_employee);
				
				$emp_description = ""+req.getParameter("emp_description");
				$emp_description = Utility.noNull($emp_description);
			}
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$id_employee = req.getParameter("id_employee");
					$id_department = req.getParameter("id_department");
					$id_roles = req.getParameter("id_roles");
					$date_start = ""+req.getParameter("date_start");
					$date_start = Utility.noNull($date_start);
					Date d_Date_start = null;
					if($date_start.length()==0)
						$date_start = "null";
					else {
						d_Date_start = dateIta.parse($date_start);
						$date_start = ""+d_Date_start.getTime()/1000;
					}
					
					$date_end = ""+req.getParameter("date_end");
					$date_end = Utility.noNull($date_end);
					Date d_Date_end = null;
					if($date_end.length()==0)
						$date_end = "null";
					else {
						d_Date_end = dateIta.parse($date_end);
						$date_end = ""+d_Date_end.getTime()/1000;
					}
					
					sql   = "insert into rel_dep_employee "+
							"(id_employee, id_departments, id_roles, datestart, dateend) "+
							"values "+
							"('"+$id_employee+"', '"+$id_department+"', '"+$id_roles+"', "+$date_start+", "+$date_end+" ) RETURNING id_reldepemployee ";
					
					System.out.println("sql_insert = " + sql);
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
					
					
					// In caso esistesse già una relazione per il dipendente imposta la data fine
					long todayNum = 0;
					Calendar c = Calendar.getInstance(Locale.GERMAN); 
					todayNum = c.getTimeInMillis()/1000;
					
					/*
					sql  =  "update rel_dep_employee "+
							"set dateend = "+todayNum+" "+
							"where id_employee = '"+$id_employee+"' "+
							"and dateend is null and id_reldepemployee <> '"+$id+"' ";
					st.executeUpdate(sql);
					*/
					
					sql  =  "update rel_dep_employee "+
							"set dateend = "+(Long.parseLong($date_start)-86400)+" "+
							"where id_employee = '"+$id_employee+"' "+
							"and dateend is null and id_reldepemployee <> '"+$id+"' ";
					st.executeUpdate(sql);
					
					
					
					// controlla l'esistenza di pianificazioni per il dipendente
					// ed eventualmente invia una mail
					String id_departments_from = "";
					String id_departments_to = $id_department;
					
					sql  =  "select id_departments from rel_dep_employee "+
							"where "+
							"("+
							"id_employee = '"+$id_employee+"' "+ 
							"and dateend is null and id_reldepemployee <> '"+$id+"' "+
							") "+
							"or "+
							"("+
							"id_employee = '"+$id_employee+"' "+ 
							"and dateend >= "+$date_start+" and id_reldepemployee <> '"+$id+"' "+
							") ";
					rs=st.executeQuery(sql);
					if(rs.next()) {
						id_departments_from = rs.getString("id_departments");
					}
					rs.close();
					
					// Imposta la data a lunedi
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis((Long.parseLong($date_start)*1000));
					System.out.println("DT1:  " + cal.getTime());
					
					int week = cal.get(Calendar.WEEK_OF_YEAR);
					cal.set(Calendar.WEEK_OF_YEAR, week);        
					cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					
					String dt = ""+(cal.getTimeInMillis()/1000);
					
					System.out.println("DT2:  " + cal.getTime());
					
					// Riapre i settimanali
					Utility.reopenPlanner(conn, id_departments_from, id_departments_to, dt);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$id_employee = req.getParameter("id_employee");
					$id_department = req.getParameter("id_department");
					$id_roles = req.getParameter("id_roles");
					$date_start = ""+req.getParameter("date_start");
					$date_start = Utility.noNull($date_start);
					Date d_Date_start = null;
					if($date_start.length()==0)
						$date_start = "null";
					else {
						d_Date_start = dateIta.parse($date_start);
						$date_start = ""+d_Date_start.getTime()/1000;
					}
					
					$date_end = ""+req.getParameter("date_end");
					$date_end = Utility.noNull($date_end);
					Date d_Date_end = null;
					if($date_end.length()==0)
						$date_end = "null";
					else {
						d_Date_end = dateIta.parse($date_end);
						$date_end = ""+d_Date_end.getTime()/1000;
					}
					
					sql  =  "update rel_dep_employee "+
							"set id_employee = '"+$id_employee+"', "+
							"id_departments = '"+$id_department+"', "+
							"id_roles = '"+$id_roles+"', "+
							"datestart = "+$date_start+", dateend = "+$date_end+" "+
							"where id_reldepemployee = '"+$id+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from rel_dep_employee where id_reldepemployee = '"+$id+"' ";
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
				// Data Inizio disabilitata in caso di modifica
				if(tipoForm.equals("M")) {
					obj.put("dtini_status", "readonly");	
				}
				
				
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select b.id_reldepemployee, b.id_employee, b.id_departments, b.id_roles, "+
						"b.datestart, b.dateend, c.name emp_name, c.surname emp_surname, d.description dep_description, "+
						"a.description rol_description, To_timestamp(b.datestart) dtstart, To_timestamp(b.dateend) dtend  "+
						"from hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
						"where a.id=b.id_roles and b.id_employee = c.id "+
						"and b.id_departments = d.id and b.id_reldepemployee = '"+$id+"' ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$id_employee=rs.getString("id_employee");
					$emp_description=rs.getString("emp_name")+" "+rs.getString("emp_surname");
					$id_department = rs.getString("id_departments");
					$dep_description = rs.getString("dep_description");
					$id_roles = rs.getString("id_roles");
					$rol_description = rs.getString("rol_description");
					
					$date_start = rs.getString("datestart");
					$date_start = Utility.noNull($date_start);
					if($date_start.length()>0)
						$date_start = dateIta.format(rs.getDate("dtstart"));
					
					$date_end = rs.getString("dateend");
					$date_end = Utility.noNull($date_end);
					if($date_end.length()>0)
						$date_end = dateIta.format(rs.getDate("dtend"));
					
					
				}
				rs.close();
				
				// ====================
				// Lista utenti con stesso ruolo???
				
				sql  =  "select c.id, c.name, c.surname, d.description from "+
						"hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
						"where a.id=b.id_roles "+
						"and b.id_employee = c.id "+ 
						"and b.id_departments = d.id "+
						"and a.id = '"+$id_roles+"' "+
						"and datestart <= "+date.getTime()/1000+" " + 
						"and (dateend is null or dateend > "+date.getTime()/1000+") " +
						"and d.id_customer = "+session.getAttribute("idcus")+" ";
				
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					emp.add(new Employee(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
							"","","","","","",rs.getString("description"),"","","","","","",""));
					
				}
				rs.close();
				
			}
			
			
			
			obj.put("id", $id);
			obj.put("id_employee", $id_employee);
			obj.put("emp_description", $emp_description);
			obj.put("id_department", $id_department);
			obj.put("dep_description", $dep_description);
			obj.put("id_roles", $id_roles);
			obj.put("rol_description", $rol_description);
			obj.put("date_start", $date_start);
			obj.put("date_end", $date_end);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Relazione Dipartimento / Dipendente / Ruolo");
		obj.put("tipoForm", tipoForm);
		
		obj.put("emp", emp);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/employee/reldepemp-dett", obj);
	}
	
}
