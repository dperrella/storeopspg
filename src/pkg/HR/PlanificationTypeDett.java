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

import pkg.beans.HR.Planification;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlanificationTypeDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> plan = new ArrayList<Tables>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		plan.clear();
		
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
			
			// idcus (da session)
			String idcus = ""+session.getAttribute("idcus");
			
			// Campi database
			String $id="";
			String $description="";
			String $min_time="";
			String $max_time="";
			String $direction="";
			String $approvation="";
			String $color="";
			
			String $cus_description="";
			String $expcode="";
			
			
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
					$description = req.getParameter("description");
					$min_time = req.getParameter("min_time");
					$max_time = req.getParameter("max_time");
					$direction = req.getParameter("direction");
					$approvation = req.getParameter("approvation");
					$color = req.getParameter("color");
					
					sql   = "insert into hr_planification_type "+
							"(description, min_time, max_time, direction, approvation, color) "+
							"values "+
							"('"+$description.replaceAll("'", "''")+"', "+$min_time+", "+$max_time+", "+
							"'"+$direction+"', '"+$approvation+"', '"+$color+"' ) RETURNING id ";
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
					
					$cus_description = req.getParameter("cus_description");
					$expcode = req.getParameter("expcode");
					
					sql   = "delete from rel_cus_plantype where idcus = "+idcus+" and idplantype = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "insert into rel_cus_plantype "
							+ "(idcus, idplantype, description, export_code) "
							+ "values "
							+ "("+idcus+" , '"+$id+"' , '"+$cus_description.replaceAll("'", "''")+"', '"+$expcode.replaceAll("'", "''")+"') ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$description = req.getParameter("description");
					$min_time = req.getParameter("min_time");
					$max_time = req.getParameter("max_time");
					$direction = req.getParameter("direction");
					$approvation = req.getParameter("approvation");
					$color = req.getParameter("color");
					
					sql  =  "update hr_planification_type "+
							"set description = '"+$description.replaceAll("'", "''")+"', "+
							"min_time = "+$min_time+", "+
							"max_time = "+$max_time+", "+
							"direction = '"+$direction+"', "+
							"approvation = '"+$approvation+"', "+
							"color = '"+$color+"' "+
							"where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					$cus_description = req.getParameter("cus_description");
					$expcode = req.getParameter("expcode");
					
					sql   = "delete from rel_cus_plantype where idcus = "+idcus+" and idplantype = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "insert into rel_cus_plantype "
							+ "(idcus, idplantype, description, export_code) "
							+ "values "
							+ "("+idcus+" , '"+$id+"' , '"+$cus_description.replaceAll("'", "''")+"', '"+$expcode.replaceAll("'", "''")+"') ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					sql   = "delete from rel_cus_plantype where idcus = "+idcus+" and idplantype = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "delete from hr_planification_type where id = '"+$id+"' ";
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
				sql  =  "select * from hr_planification_type where id = '"+$id+"' ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$description=rs.getString("description");
					$min_time=rs.getString("min_time");
					$max_time=rs.getString("max_time");
					$direction=rs.getString("direction");
					$approvation=rs.getString("approvation");
					$color=rs.getString("color");
				}
				rs.close();
				
				sql  =  "select * from rel_cus_plantype where idcus = "+idcus+" and idplantype = '"+$id+"' ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$cus_description=rs.getString("description");
					$expcode=rs.getString("export_code");
				}
				rs.close();
				
				
				// Ultime 50 pianificazioni
				sql   = "select count(distinct(plan_year || plan_week || id_departments)) nrplan, "+
						"plan_year, plan_week, id_departments, description "+
						"from hr_planification a, hr_departments b "+
						"where a.id_departments = b.id and plan_year = '"+oggi.substring(6,10)+"' "+
						"and b.id_customer = "+session.getAttribute("idcus")+" "+
						"group by plan_year, plan_week, id_departments, description "+
						"order by cast(plan_week as numeric) desc "+
						"limit 50 ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					plan.add(new Tables("PLN","","",rs.getString("plan_year"),rs.getString("plan_week"),
							rs.getString("id_departments"),rs.getString("description")));
				}
				rs.close();
				
				
			}
			
			obj.put("id", $id);
			obj.put("description", $description);
			obj.put("min_time", $min_time);
			obj.put("max_time", $max_time);
			obj.put("direction", $direction.trim());
			obj.put("approvation", $approvation.trim());
			obj.put("color", $color);
			
			obj.put("plan", plan);
			
			obj.put("cus_description", $cus_description);
			obj.put("expcode", $expcode);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Tipo Pianificazione");
		obj.put("tipoForm", tipoForm);
		
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/planner/planification-type-dett", obj);
	}
	
}
