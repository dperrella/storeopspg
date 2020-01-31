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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Departments;
import pkg.beans.HR.PlanificationType;
import pkg.beans.HR.PlannerModel;
import pkg.beans.HR.PlannerWeekModel;
import pkg.beans.Rfid_device_type;
import pkg.beans.Rfid_extra;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerTot extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<PlannerWeekModel> plan = new ArrayList<PlannerWeekModel>();
		plan.clear();
		
		List<Tables2> tab = new ArrayList<Tables2>();
		tab.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		String id_departments = req.getParameter("id_departments");
		String year = req.getParameter("year");
		String week = req.getParameter("week");
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			
			conn = Utility.getConnHR();
			
			PlannerWeekModel pln = new PlannerWeekModel();
			pln.setId_departments(id_departments);
			pln.setYear(Integer.parseInt(year));
			pln.setWeek(Integer.parseInt(week));
			pln.setDow(0);
			
			plan = pln.selectTotEmployee(conn);
			obj.put("plan", plan);
			
			// totali settimana
			st=conn.createStatement();
			sql = "select "
					+ "sum(tot_lun) lun, "
					+ "sum(tot_mar) mar, "
					+ "sum(tot_mer) mer, "
					+ "sum(tot_gio) gio, "
					+ "sum(tot_ven) ven, "
					+ "sum(tot_sab) sab, "
					+ "sum(tot_dom) dom, "
					
					+ "sum(tot_lun2) lun2, "
					+ "sum(tot_mar2) mar2, "
					+ "sum(tot_mer2) mer2, "
					+ "sum(tot_gio2) gio2, "
					+ "sum(tot_ven2) ven2, "
					+ "sum(tot_sab2) sab2, "
					+ "sum(tot_dom2) dom2 "
					+ "from vw_totweek_new "
					+ "where id_departments = '"+id_departments+"' "
					+ "and anno = "+year+" and settimana = "+week+" and dow = 0 ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
				
				obj.put("lun", rs.getDouble("lun"));
				obj.put("mar", rs.getDouble("mar"));
				obj.put("mer", rs.getDouble("mer"));
				obj.put("gio", rs.getDouble("gio"));
				obj.put("ven", rs.getDouble("ven"));
				obj.put("sab", rs.getDouble("sab"));
				obj.put("dom", rs.getDouble("dom"));
				
				obj.put("lun2", rs.getDouble("lun2"));
				obj.put("mar2", rs.getDouble("mar2"));
				obj.put("mer2", rs.getDouble("mer2"));
				obj.put("gio2", rs.getDouble("gio2"));
				obj.put("ven2", rs.getDouble("ven2"));
				obj.put("sab2", rs.getDouble("sab2"));
				obj.put("dom2", rs.getDouble("dom2"));
				
				obj.put("tot", rs.getDouble("lun")+ rs.getDouble("mar")+ rs.getDouble("mer")+ rs.getDouble("gio")+ rs.getDouble("ven")+ rs.getDouble("sab")+ rs.getDouble("dom"));
				
				obj.put("tot2", rs.getDouble("lun2")+ rs.getDouble("mar2")+ rs.getDouble("mer2")+ rs.getDouble("gio2")+ rs.getDouble("ven2")+ rs.getDouble("sab2")+ rs.getDouble("dom2"));
				
			}
			rs.close();
			
			
			
			
			
			sql   = "select id_planification_type, w.color, w.col,description,\r\n" + 
					"coalesce ((select sum(sum) from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"--and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '0'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					")  , null , 0) lun,\r\n" + 
					"coalesce ((select sum(sum) from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"--and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '1'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) mar,\r\n" + 
					"coalesce ((select sum(sum) from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"--and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '2'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) mer,\r\n" + 
					"coalesce ((select sum(sum) from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"--and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '3'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0)gio,\r\n" + 
					"coalesce ((select sum(sum) from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"--and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '4'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) ven,\r\n" + 
					"coalesce ((select sum(sum) from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"--and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '5'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) sab,\r\n" + 
					"coalesce ((select sum(sum) from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"--and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '6'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) dom\r\n" + 
					"from vw_totempweek w\r\n" + 
					"join hr_planification_type t on t.id = w.id_planification_type "+
					"where id_departments = '"+id_departments+"' 	\r\n" + 
					"and plan_year = '"+year+"' \r\n" + 
					"and plan_week = '"+week+"' \r\n" + 
					"group by id_planification_type, w.color, w.col, plan_year, plan_week, \r\n" + 
					"id_departments, description\r\n" + 
					"--and id_employee = '8bc637f8-86c6-4789-910e-737861ea6ef9'\r\n";
			rs = st.executeQuery(sql);
			int count = 0;
			
			while(rs.next()) {
				count++;
				
				tab.add(new Tables2("PLN", rs.getString("color"), rs.getString("col"),
						rs.getString("lun"),
						rs.getString("mar"),
						rs.getString("mer"),
						rs.getString("gio"),
						rs.getString("ven"),
						rs.getString("sab"),
						rs.getString("dom"),
						""+(rs.getDouble("lun")+
						rs.getDouble("mar")+
						rs.getDouble("mer")+
						rs.getDouble("gio")+
						rs.getDouble("ven")+
						rs.getDouble("sab")+
						rs.getDouble("dom")),
						rs.getString("description"),Planner.convertHex(rs.getString("col"), 50)
						));
				
				
			}
			rs.close();
			
			// table
			obj.put("tab", tab);
						
			String col = "";
			
			switch (count) {
	            case 1:  
	            	col = "col-lg-12";
	            break;
	            case 2:  
	            	col = "col-lg-6";
	            break;
	            case 3:  
	            	col = "col-lg-4";
	            break;
	            case 4:  
	            	col = "col-lg-3";
	            break;
	            case 5:  
	            	col = "col-lg-2-4";
	            break;
	            case 6:  
	            	col = "col-lg-2";
	            break;
			}
			
			obj.put("col", col);
 			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("id_departments", id_departments);
		obj.put("year", year);
		obj.put("week", week);
		
		
		return new ModelAndView("/hr/planner2/plannerTot", obj);
	}
	
}
