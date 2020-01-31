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

import pkg.beans.HR.ControlPlan;
import pkg.beans.HR.Employee;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ControlPlanList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<ControlPlan> cnp = new ArrayList<ControlPlan>();
		cnp.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String dsfun = "Controllo Pianificazione";
		
		String msg = "";
		int count = 0;
		
		// -----------------------
		// Anno
		String year = req.getParameter("year");
		String week = req.getParameter("week");
		String id_departments = req.getParameter("id_departments");
		String status = req.getParameter("status");
		
		if(year.length()>0)
			cond+=" and plan_year = '"+year+"' ";
		if(week.length()>0)
			cond+=" and plan_week = '"+week+"' ";
		if(id_departments.length()>0)
			cond+=" and id_departments = '"+id_departments+"' ";
		if(status.length()>0)
			cond+=" and status = '"+status.trim()+"' ";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// Campi db 
			String $year="";
			String $week="";
			String $dtda="";
			String $dtal="";
			String $id_departments="";
			String $des_departments="";
			String $status="";
			
			sql   = "select id_departments, description, plan_year, plan_week "+
					"from hr_planification a, hr_departments b "+
					"where a.id_departments = b.id "+cond+" and b.id_customer = "+session.getAttribute("idcus")+" "+
					"group by id_departments, description, plan_year, plan_week "+
					"order by plan_year desc, cast(plan_week as numeric) desc, id_departments ";
			rs=st.executeQuery(sql);
			
			Calendar cal = Calendar.getInstance(Locale.GERMAN);   
			
			
			while(rs.next()) {
				
				$year = rs.getString("plan_year").trim();
				$week = rs.getString("plan_week").trim();
				
				cal.set(Integer.parseInt($year), 0, 1);
				cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt($week));        
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				// dtda
				$dtda = dateIta.format(cal.getTime());
				cal.add(Calendar.DATE, 6);  
				// dtal
				$dtal = dateIta.format(cal.getTime());
				
				$id_departments = rs.getString("id_departments");
				$des_departments = rs.getString("description");
				
				$status = getStatus(conn, $year, $week, $id_departments, (String)session.getAttribute("language"));
				
				cnp.add(new ControlPlan($year,$week,$dtda,$dtal,$id_departments,$des_departments,$status,
						"","","",""));
				
			}
			rs.close();

			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", dsfun);
		obj.put("week", week);
		obj.put("year", year);
		obj.put("id_departments", id_departments);
		obj.put("status", status);
		obj.put("msg", msg);
		
		obj.put("cnp", cnp);
		
		return new ModelAndView("/hr/control/control-plan-list", obj);
	}
	
	public static String getStatus(Connection conn, String year, String week, String id_departments, String lang) throws SQLException {
		
		String arrayStatus = "";
		
		String status = "";
		String xdtab = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql   = "";
		try {
			
			//Legge il file di properties associato alla lingua sul profilo
			ReadProperties propLang = new ReadProperties(lang+".properties");

			st=conn.createStatement();
			
			sql   = "select status, xdtab "+
					"from hr_planification p "+
					"left join ztables on trim(status) = trim(xcdel) "+
					"where p.id_departments = '"+id_departments+"' "+
					"and p.plan_year = '"+year+"' and p.plan_week = '"+week+"' "+
					"and p.id_employee in (select v.id_employee "+
					"from vw_emp_dep_indate v where v.id_departments = '"+id_departments+"' "+
					"and anno = "+year+" and settimana = "+week+" and dow = 0) "+
					"group by status, xdtab "+
					"order by status ";
			System.out.println(sql);
			
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				status = ""+rs.getString("status");
				status = Utility.noNull(status);
				status = status.trim();
				
				xdtab = ""+rs.getString("xdtab");
				xdtab = Utility.noNull(xdtab);
				
				if(status.length()==0)
					xdtab = "<span class=\"label bg-light\">"+propLang.get("salvato")+"</span>";
				else
					if(status.equals("A"))
						xdtab = "<span class=\"label bg-success\">"+propLang.get("approvato")+"</span>";
					else if(status.equals("R"))
						xdtab = "<span class=\"label bg-danger\">"+propLang.get("rigettato")+"</span>";
					else if(status.equals("X"))
						xdtab = "<span class=\"label bg-info\">"+propLang.get("richiesta")+"</span>";
					else if(status.equals("L"))
						xdtab = "<span class=\"label bg-dark\">"+propLang.get("lavorato")+"</span>";
					else if(status.equals("Y"))
						xdtab = "<span class=\"label bg-primary\">"+propLang.get("riaperto")+"</span>";
				
				arrayStatus+=xdtab+" ";
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(st!=null)
			st.close();
		
		return arrayStatus;
		
	}

}
