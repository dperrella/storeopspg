package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

import pkg.beans.HR.Employee;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RolesDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Employee> emp = new ArrayList<Employee>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
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
			String $name="";
			String $description="";
			String $plan_view="";
		
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
					$name = req.getParameter("name");
					$description = req.getParameter("description");
					$plan_view = req.getParameter("plan_view");
					
					sql   = "insert into hr_roles "+
							"(name, description, plan_view) "+
							"values "+
							"('"+$name.replaceAll("'", "''")+"', '"+$description.replaceAll("'", "''")+"', '"+$plan_view+"' ) RETURNING id ";
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
					$name = req.getParameter("name");
					$description = req.getParameter("description");
					$plan_view = req.getParameter("plan_view");
					
					sql  =  "update hr_roles "+
							"set name = '"+$name.replaceAll("'", "''")+"', "+
							"description = '"+$description.replaceAll("'", "''")+"', "+
							"plan_view = '"+$plan_view+"' "+
							"where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from hr_roles where id = '"+$id+"' ";
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
				sql  =  "select * from hr_roles where id = '"+$id+"' ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$name=rs.getString("name");
					$description=rs.getString("description");
					$plan_view=rs.getString("plan_view");
				}
				rs.close();
				
				// ====================
				// Lista utenti del ruolo
				sql  =  "select c.id, c.name, c.surname, d.description from "+
						"hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
						"where a.id=b.id_roles "+
						"and b.id_employee = c.id "+ 
						"and b.id_departments = d.id "+
						"and a.id = '"+$id+"' "+
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
			obj.put("name", $name);
			obj.put("description", $description);
			obj.put("plan_view", $plan_view);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Ruolo");
		obj.put("tipoForm", tipoForm);
		
		obj.put("emp", emp);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/employee/roles-dett", obj);
	}
	
}
