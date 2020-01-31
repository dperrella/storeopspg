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
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlanificationList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Planification> plan = new ArrayList<Planification>();
		
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
		
		// Ricerca generica
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		// Ricerca Avanzata
		String id_departments = ""+req.getParameter("id_departments");
		id_departments=Utility.noNull(id_departments);
		
		String week = ""+req.getParameter("week");
		week=Utility.noNull(week);
		
		String year = ""+req.getParameter("year");
		year=Utility.noNull(year);
		
		try {
			
			if(q.length()>0) {
				cond+=" and upper(name) || upper(surname) || upper(description) like '%"+q.toUpperCase()+"%' ";
			}
			else {
				if(id_departments.length()>0) {
					cond+=" and d.id = '"+id_departments+"' ";
				}
				if(week.length()>0) {
					cond+=" and plan_week = '"+week+"' ";
				}
				if(year.length()>0) {
					cond+=" and plan_year = '"+year+"' ";
				}
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select p.id, p.id_employee, p.id_departments, "+
					"to_timestamp(datetime_start) date_start, to_timestamp(datetime_end) date_end, "+
					"name, surname, d.description, id_planification_type, t.description planification_type_description, "+
					"plan_year, plan_week, plan_day, color "+
					"from hr_planification p "+
					"left join hr_planification_type t on t.id = p.id_planification_type, "+
					"hr_employee e, hr_departments d "+
					"where p.id_employee = e.id and p.id_departments = d.id "+cond+" "+
					"and d.id_customer = "+session.getAttribute("idcus")+" "+
					"order by plan_year, plan_week, plan_day ";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id;
			String $id_employee;
			String $name;
			String $surname;
			String $id_departments;
			String $department_description;
			String $datetime_start;
			String $datetime_end;
			String $id_planification_type;
			String $planification_type_description;
			String $plan_year;
			String $plan_week;
			String $plan_day;
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
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
				
				if($plan_day.equals("0"))
					$cmp1 = propLang.get("lun");
				if($plan_day.equals("1"))
					$cmp1 = propLang.get("mar");
				if($plan_day.equals("2"))
					$cmp1 = propLang.get("mer");
				if($plan_day.equals("3"))
					$cmp1 = propLang.get("gio");
				if($plan_day.equals("4"))
					$cmp1 = propLang.get("ven");
				if($plan_day.equals("5"))
					$cmp1 = propLang.get("sab");
				if($plan_day.equals("6"))
					$cmp1 = propLang.get("dom");
				
				$cmp2 = rs.getString("color");
				
				plan.add(new Planification($id,$id_employee,$name,$surname,$id_departments,
						$department_description,$datetime_start,$datetime_end,
						$id_planification_type,$planification_type_description,
						$plan_year,$plan_week,$plan_day,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Pianificazioni");
		obj.put("plan", plan);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/planification-list", obj);
	}
	
}
