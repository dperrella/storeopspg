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
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerDay extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<PlannerModel> plan = new ArrayList<PlannerModel>();
		plan.clear();
		
		ReadProperties prop = new ReadProperties("connection.properties");
		int sec = Integer.parseInt(prop.get("sec"));
		
		
		SimpleDateFormat num = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat ita = new SimpleDateFormat("dd-MM-yyyy");
		
		SimpleDateFormat tf = new SimpleDateFormat();
        tf.setTimeZone(TimeZone.getTimeZone("UTC"));
       
		
		String id_departments = req.getParameter("id_departments");
		String year = req.getParameter("year");
		String week = req.getParameter("week");
		String day = ""+req.getParameter("day");
		day=Utility.noNull(day);
		if(day.length()==0)
			day = "0";
		String eye = ""+req.getParameter("eye");
		eye=Utility.noNull(eye);
		if(eye.length()==0)
			eye = "";
		
		long dt = 0;
		long dt2= 0;
		
		Connection conn = null;
		
		String sql = "";
		try {
			
			conn = Utility.getConnHR();
			
			// Get calendar set to current date and time
			Calendar cw = Calendar.getInstance(Locale.GERMAN);

			// Set the calendar to monday of the current week
			cw.set(Calendar.YEAR, Integer.parseInt(year));     
			cw.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
			
			
			switch (Integer.parseInt(day)) {
            case 0:  
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            	obj.put("dayName",propLang.get("lun"));
                break;
            case 1:  
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            	obj.put("dayName",propLang.get("mar"));
                break;
            case 2:  
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            	obj.put("dayName",propLang.get("mer"));
                break;
            case 3:  
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            	obj.put("dayName",propLang.get("gio"));
                break;
            case 4:  
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            	obj.put("dayName",propLang.get("ven"));
                break;
            case 5:  
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            	obj.put("dayName",propLang.get("sab"));
                break;
            case 6:  
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            	obj.put("dayName",propLang.get("dom"));
                break;
            default:
            	cw.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            	obj.put("dayName",propLang.get("lun"));
            	break;
			} 
			
			dt = cw.getTimeInMillis();
			String dtNum = num.format(new Date(dt));
			String a = dtNum.substring(0, 4);
			String m = dtNum.substring(4, 6);
			String d = dtNum.substring(6, 8);
			
			// tolgo 7 giorni a dt
			cw.add(Calendar.DAY_OF_MONTH, -7);
			dt2= cw.getTimeInMillis();
			String dtda = ita.format(new Date(dt2));
			String dtal = ita.format(new Date(dt2));
			
			obj.put("dtda", dtda);
			obj.put("dtal", dtal);
			obj.put("idstores", "1");
			
			obj.put("dt", dt);
			obj.put("a", a);
			obj.put("m", m);
			obj.put("d", d);
			
			PlannerModel planner = new PlannerModel();
			planner.setId_departments(id_departments);
			planner.setYear(Integer.parseInt(year));
			planner.setWeek(Integer.parseInt(week));
			planner.setDow(Integer.parseInt(day));
			plan = planner.selectPlan(conn);
			
			// create "location" & "events";
			String locations = "";
			String events = "";
			int nremp = 0;
			
			String locationsDis = "";
			
			for(PlannerModel pln : plan)
			{
				nremp++;
				String planned = pln.getPlan();
			    
				if(eye.equals("P")&&planned.equals("[]")) {
					// do nothing
				}
				else {
					
					String cmp4 = pln.getCmp4();
					
				    locations+="{id: '"+pln.getId_employee()+"', name: '"+pln.getName().replaceAll("'"," ")+" "+pln.getSurname().replaceAll("'"," ")+"', userData: {monte: "+pln.getMonteore()+", trasf: '"+cmp4+"'}},";
				    
				    
				    JSONParser parser = new JSONParser();
					Object _obj = parser.parse(planned);
					
					// loop array
					JSONArray msg = (JSONArray) _obj;
					Iterator<String> iterator = msg.iterator();
					
					
				    
					while (iterator.hasNext()) {
					    
						Object obj2 = iterator.next();
					    
					    JSONObject jsonObject2 = (JSONObject) obj2;
					    
					    try {
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
						    
						    String disabled = "false";
						    if(cmp4.equalsIgnoreCase("TRASF")) {
						    	dsdep = "IN TRASFERTA";
						    	css = "trasf bold";
						    	disabled = "true";
						    	
						    	locationsDis+="'"+pln.getId_employee()+"',";
						    }
						    
						    events+="{name: '"+dsdep+"', disabled: "+disabled+", location: '"+jsonObject2.get("id_employee")+"',start: new Date("+(start+sec)*1000+"),end: new Date("+(end+sec)*1000+"), className: '"+css+"', "
						    		+ "userData: {idtype: '"+idtype+"', datetime_start: "+start+" , datetime_end: "+end+", plan_day: "+plan_day+", iddep: '"+iddep+"'}},";
					    }
					    catch(Exception e) {}
					}	
				}
			    
			}
			
			if(locationsDis.length()>0)
				locationsDis=locationsDis.substring(0, locationsDis.length()-1);
			
			obj.put("locationsDis", "["+locationsDis+"]");
			
			obj.put("nremp", nremp);
			
			if(locations.length()>0)
				locations = locations.substring(0, locations.length()-1);
			obj.put("locations", locations);
			
			if(events.length()>0)
				events = events.substring(0, events.length()-1);
			obj.put("events", events);
			
			String minNum_employee = Planner.getMinnum_employee(conn, id_departments, year, week,day);
			obj.put("minNum", minNum_employee);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("id_departments", id_departments);
		obj.put("year", year);
		obj.put("week", week);
		obj.put("day", day);
		
		return new ModelAndView("/hr/planner2/plannerDay", obj);
	}
	
}
