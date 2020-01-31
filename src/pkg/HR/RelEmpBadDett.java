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

public class RelEmpBadDett extends AbstractController {
	
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
			String $id_badge="";
			String $badge_number="";
			String $date_end = "";;
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
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
					$id_badge = req.getParameter("id_badge");
					$date_end = req.getParameter("date_end");
					Date d_Date_end = null;
					if($date_end.length()==0)
						$date_end = "null";
					else {
						d_Date_end = dateIta.parse($date_end);
						$date_end = ""+d_Date_end.getTime()/1000;
					}
					
					sql   = "insert into rel_emp_badge "+
							"(id_employ, id_badge, date_end) "+
							"values "+
							"('"+$id_employee+"', '"+$id_badge+"', "+$date_end+" ) RETURNING id ";
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
					
					// relazione dipendente
					sql  =  "update rel_emp_badge "+
							"set date_end = "+todayNum+" "+
							"where id_employ = '"+$id_employee+"' "+
							"and date_end is null and id <> '"+$id+"' ";
					st.executeUpdate(sql);
					
					// relazione badge
					sql  =  "update rel_emp_badge "+
							"set date_end = "+todayNum+" "+
							"where id_badge = '"+$id_badge+"' "+
							"and date_end is null and id <> '"+$id+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$id_employee = req.getParameter("id_employee");
					$id_badge = req.getParameter("id_badge");
					$date_end = req.getParameter("date_end");
					Date d_Date_end = null;
					if($date_end.length()==0)
						$date_end = "null";
					else {
						d_Date_end = dateIta.parse($date_end);
						$date_end = ""+d_Date_end.getTime()/1000;
					}
					
					
					sql  =  "update rel_emp_badge "+
							"set id_employ = '"+$id_employee+"', "+
							"id_badge = '"+$id_badge+"', date_end = "+$date_end+" "+
							"where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from rel_emp_badge where id = '"+$id+"' ";
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
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select a.id, a.id_employ, b.name, b.surname, a.id_badge, a.date_end, To_timestamp(a.date_end) dtend, number "+
						"from rel_emp_badge a, hr_employee b, hr_badge c "+
						"where a.id = '"+$id+"' and b.id = a.id_employ and a.id_badge = c.id "
						+ "and b.id_customer = "+session.getAttribute("idcus")+" ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$id_employee=rs.getString("id_employ");
					$emp_description=rs.getString("name")+" "+rs.getString("surname");
					$id_badge = rs.getString("id_badge");
					$date_end = rs.getString("date_end");
					$date_end = Utility.noNull($date_end);
					if($date_end.length()>0)
						$date_end = dateIta.format(rs.getDate("dtend"));
					$badge_number = rs.getString("number");
					
				}
				rs.close();
				
				// ====================
				// Lista utenti con questo badge
				sql   = "select a.id, a.name, a.surname "+
						"from hr_employee a "+
						"where a.id = '"+$id_employee+"' "
						+ "and a.id_customer = "+session.getAttribute("idcus")+" ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					
					emp.add(new Employee(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
							"","","","","","","","","","","","","",""));
					
				}
				rs.close();
				
			}
			
			obj.put("id", $id);
			obj.put("id_employee", $id_employee);
			obj.put("emp_description", $emp_description);
			obj.put("id_badge", $id_badge);
			obj.put("date_end", $date_end);
			obj.put("badge_number", $badge_number);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Relazione Dipendente / Badge");
		obj.put("tipoForm", tipoForm);
		
		obj.put("emp", emp);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/employee/relempbad-dett", obj);
	}
	
}
