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
import pkg.beans.HR.Minnum_employee;
import pkg.utility.Utility;

public class Minnum_employeeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Minnum_employee> minnum = new ArrayList<Minnum_employee>();
		HttpSession session = req.getSession();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		minnum.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		try {
			
			if(q.length()>0) {
				cond+=" and a.id || upper(b.description) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select a.id_departments, b.description, a.plan_year, a.plan_week, a.plan_day "+
					"from hr_minnum_employee a, hr_departments b "+
					"where a.id_departments = b.id and b.id_customer = "+session.getAttribute("idcus")+" "+cond+" "+
					"group by a.id_departments, b.description, a.plan_year, a.plan_week, a.plan_day "+
					"order by a.id_departments, a.plan_year, a.plan_week, a.plan_day ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
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
			
			while(rs.next()) {
				
				count+=1;
				
				//$id=rs.getString("id");
				$id_departments=rs.getString("id_departments");
				$description=rs.getString("description");
				$plan_year=rs.getString("plan_year");
				$plan_week=rs.getString("plan_week");
				$plan_day=rs.getString("plan_day");
				
				if($plan_week.trim().length()==0) {
					$cmp3 = $plan_week;
					$plan_week = "Tutte (Default)";
					$cmp1 = "<b>";
					$cmp2 = "</b>";
				}
				else {
					$cmp3 = $plan_week;
				}
				// ---
				if($plan_day.trim().length()==0) {
					$cmp4 = $plan_day;
					$plan_day = "Tutti (Default)";
					$cmp1 = "<b>";
					$cmp2 = "</b>";
				}
				else {
					$cmp4 = $plan_day;
				}
				
				//$plan_hour=rs.getString("plan_hour");
				//$value=rs.getString("value");
				
				minnum.add(new Minnum_employee($id,$id_departments,$description,$plan_year,$plan_week,$plan_day,$plan_hour,$value,$cmp1,$cmp2,$cmp3,$cmp4));
				
				$cmp1 = "";
				$cmp2 = "";
				$cmp3 = "";
				$cmp4 = "";
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Numero Minimo Dipendenti Pianificazione");
		obj.put("minnum", minnum);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/minnum_employee-list", obj);
	}
	
}
