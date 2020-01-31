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
import pkg.beans.Rfid_device_type;
import pkg.beans.Rfid_extra;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerEmployee extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<PlannerModel> plan = new ArrayList<PlannerModel>();
		plan.clear();
		
		List<Tables2> tab = new ArrayList<Tables2>();
		tab.clear();
		
		ReadProperties prop = new ReadProperties("connection.properties");
		int sec = Integer.parseInt(prop.get("sec"));
		
		
		SimpleDateFormat num = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat tf = new SimpleDateFormat();
        tf.setTimeZone(TimeZone.getTimeZone("UTC"));
       
		
        String id_employee = req.getParameter("id_employee");
		String id_departments = req.getParameter("id_departments");
		String year = req.getParameter("year");
		String week = req.getParameter("week");
		String day = ""+req.getParameter("day");
		day=Utility.noNull(day);
		if(day.length()==0)
			day = "0";
		
		
		Connection conn = null;
		
		Statement st = null;
		ResultSet rs = null;
		try {
			
			conn = Utility.getConnHR();
			st = conn.createStatement();
			
			// Get calendar set to current date and time
			Calendar cw = Calendar.getInstance(Locale.GERMAN);

			// Set the calendar to monday of the current week
			cw.set(Calendar.YEAR, Integer.parseInt(year));     
			cw.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
			cw.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            
			long dt = 0;
			dt = cw.getTimeInMillis();
			String dtNum = num.format(new Date(dt));
			String a = dtNum.substring(0, 4);
			String m = dtNum.substring(4, 6);
			String d = dtNum.substring(6, 8);
			
			obj.put("dt", dt);
			obj.put("a", a);
			obj.put("m", m);
			obj.put("d", d);
			
			PlannerModel planner = new PlannerModel();
			planner.setId_employee(id_employee);
			planner.setId_departments(id_departments);
			planner.setYear(Integer.parseInt(year));
			planner.setWeek(Integer.parseInt(week));
			planner.setDow(Integer.parseInt(day));
			plan = planner.selectPlanEmployee(conn);
			
			// create "location" & "events";
			String locations = "";
			String events = "";
			
			for(int i=0;i<7;i++) {
				String dayText = "";
				switch (i) {
	            case 0:  
	            	dayText = propLang.get("lun");
	                break;
	            case 1:  
	            	dayText = propLang.get("mar");
	                break;
	            case 2:  
	            	dayText = propLang.get("mer");
	                break;
	            case 3:  
	            	dayText = propLang.get("gio");
	                break;
	            case 4:  
	            	dayText = propLang.get("ven");
	                break;
	            case 5:  
	            	dayText = propLang.get("sab");
	                break;
	            case 6:  
	            	dayText = propLang.get("dom");
	                break;
	            default:
	            	dayText = propLang.get("lun");
	            	break;
				} 
				
				locations+="{id: '"+i+"', name: '"+dayText+"'},";
			}
			
			for(PlannerModel pln : plan)
			{
				String planned = pln.getPlan();
				
				String employee = pln.getName().replaceAll("'", " ")+" "+pln.getSurname().replaceAll("'", " ");
				obj.put("employee", employee);
				
			    JSONParser parser = new JSONParser();
				Object _obj = parser.parse(planned);
				
				// loop array
				JSONArray msg = (JSONArray) _obj;
				Iterator<String> iterator = msg.iterator();
				
				
				
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    if(jsonObject2!=null) {
					    System.out.println(jsonObject2);
					    
					    long start = (long) jsonObject2.get("datetime_start");
					    
					    long end = (long) jsonObject2.get("datetime_end");
					    
					    String idtype = (String) jsonObject2.get("id_planification_type");
					    String plan_day = (String) jsonObject2.get("plan_day");
					    String iddep = (String) jsonObject2.get("id_departments");
					    String dsdep = (String) jsonObject2.get("description");
					    String css = (String) jsonObject2.get("color");
					    if(!iddep.equals(id_departments)) {
					    	dsdep = "TRASF. "+(String) jsonObject2.get("dsdep");
					    	css = "trasf bold";
					    }
					    
					    int sec2=0;
					    switch (Integer.parseInt(plan_day)) {
			            case 0:  
			            	sec2=0;
			                break;
			            case 1:  
			            	sec2 = 86400;
			                break;
			            case 2:  
			            	sec2 = 86400*2;
			                break;
			            case 3:  
			            	sec2 = 86400*3;
			                break;
			            case 4:  
			            	sec2 = 86400*4;
			                break;
			            case 5:  
			            	sec2 = 86400*5;
			                break;
			            case 6:  
			            	sec2 = 86400*6;
			                break;
			            default:
			            	sec2 = 0;
			            	break;
						} 
					    
					    events+="{name: '"+dsdep+"',location: '"+plan_day+"',start: new Date("+((start+sec)-sec2)*1000+"),end: new Date("+((end+sec)-sec2)*1000+"), className: '"+css+"', "
					    		+ "userData: {idtype: '"+idtype+"', datetime_start: "+start+" , datetime_end: "+end+", plan_day: "+plan_day+", iddep: '"+iddep+"'}},";
					    
				    }
				}	
				
			}
			if(locations.length()>0)
				locations = locations.substring(0, locations.length()-1);
			obj.put("locations", locations);
			
			if(events.length()>0)
				events = events.substring(0, events.length()-1);
			obj.put("events", events);
			
			
			
			
			
			
			
			// tabella totali
			String sql   = "select id_planification_type, w.color, w.col, description, "+
					"(select monteore from hr_contracts c where c.id_employee = w.id_employee order by datada desc limit 1) monteore,\r\n" + 
					"coalesce ((select sum from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '0'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					")  , null , 0) lun,\r\n" + 
					"coalesce ((select sum from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '1'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) mar,\r\n" + 
					"coalesce ((select sum from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '2'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) mer,\r\n" + 
					"coalesce ((select sum from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '3'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0)gio,\r\n" + 
					"coalesce ((select sum from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '4'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) ven,\r\n" + 
					"coalesce ((select sum from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '5'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) sab,\r\n" + 
					"coalesce ((select sum from vw_totempday d\r\n" + 
					"where w.id_departments = d.id_departments\r\n" + 
					"and w.id_employee = d.id_employee\r\n" + 
					"and w.plan_year = d.plan_year\r\n" + 
					"and w.plan_week = d.plan_week and d.plan_day = '6'\r\n" + 
					"and w.id_planification_type = d.id_planification_type\r\n" + 
					") , null , 0) dom\r\n" + 
					"from vw_totempweek w\r\n" + 
					"join hr_planification_type t on t.id = w.id_planification_type "+
					"where id_departments = '"+id_departments+"' 	\r\n" + 
					"and plan_year = '"+year+"' \r\n" + 
					"and plan_week = '"+week+"' \r\n" + 
					"and id_employee = '"+id_employee+"'\r\n";
			rs=st.executeQuery(sql);
			
			double lun = 0;
			double mar = 0;
			double mer = 0;
			double gio = 0;
			double ven = 0;
			double sab = 0;
			double dom = 0;
			
			double totturno = 0;
			double monteore = 0;
			double resto = 0;
			
			int count = 0;
			
			while(rs.next()) {
				count++;
				
				if(count==1) {
					monteore = rs.getDouble("monteore");
				}
				
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
						rs.getString("description"),""
						));
				
				if(rs.getString("color").trim().equalsIgnoreCase("turno")) {
					
					count++;
					lun = rs.getDouble("lun");
					mar = rs.getDouble("mar");
					mer = rs.getDouble("mer");
					gio = rs.getDouble("gio");
					ven = rs.getDouble("ven");
					sab = rs.getDouble("sab");
					dom = rs.getDouble("dom");
					
				}
				
			}
			rs.close();
			
			// table
			obj.put("tab", tab);
			
			obj.put("lun", lun);
			obj.put("mar", mar);
			obj.put("mer", mer);
			obj.put("gio", gio);
			obj.put("ven", ven);
			obj.put("sab", sab);
			obj.put("dom", dom);
			
			obj.put("monteore", monteore);
			obj.put("resto", (monteore-((lun+mar+mer+gio+ven+sab+dom)))*-1);
			
			
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
		
		obj.put("ed_employee", id_employee);
		
		obj.put("id_departments", id_departments);
		obj.put("year", year);
		obj.put("week", week);
		obj.put("day", day);
		
		
		
		
		return new ModelAndView("/hr/planner2/plannerEmployee", obj);
	}
	
}
