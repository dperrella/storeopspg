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

public class PlannerStampa2 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		List<Tables> css = new ArrayList<Tables>();
		List<Tables> giorni = new ArrayList<Tables>();
		css.clear();
		giorni.clear();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		String tp = ""+req.getParameter("tp");
		tp=Utility.noNull(tp);
		tp = tp.replaceAll("\\?cd=undefined", "");
		if(tp.length()==0)
			tp="I";
		String dsfun = "";
		if(tp.equals("I")) {
			dsfun = propLang.get("pla_tpi");
		}
		if(tp.equals("A")) {
			dsfun = propLang.get("pla_tpa");
		}
		if(tp.equals("M")) {
			dsfun = propLang.get("pla_tpm");
		}
		obj.put("dsfun", dsfun);
		String id_departments = req.getParameter("id_departments");
		id_departments = id_departments.replaceAll("\\?cd=undefined", "");
		String year = req.getParameter("year");
		year = year.replaceAll("\\?cd=undefined", "");
		String week = req.getParameter("week");
		week = week.replaceAll("\\?cd=undefined", "");
		String day = ""+req.getParameter("day");
		day = day.replaceAll("\\?cd=undefined", "");
		day=Utility.noNull(day);
		if(day.length()==0)
			day = "0";
		
		long dt = 0;
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		try {
			
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			////////////////////////////////
			// styles per planification type
			sql   = "select id, description, color, col from hr_planification_type ";
			rs=st.executeQuery(sql);
			String sel = "";
			while(rs.next()) {
				
				if(rs.getString("color").equalsIgnoreCase("turno"))
					sel = "selected";
				else
					sel = "";
				
				css.add(new Tables(rs.getString("color"),
						rs.getString("col"),convertHex(rs.getString("col"), 80),
						convertHex(rs.getString("col"), 60),sel,
						rs.getString("id"),rs.getString("description")));
				
			}
			rs.close();
			obj.put("css", css);
			/////////////////////////////////
			
			
			String trasf = "#0d5933";
			String trasf2 = convertHex(trasf, 80);
			String trasf3 = convertHex(trasf, 60);
			obj.put("trasf", trasf);
			obj.put("trasf2", trasf2);
			obj.put("trasf3", trasf3);
			
			
			
			// Settimana
			String giorno = "";
			
			// Get calendar set to current date and time
			Calendar cweek = Calendar.getInstance(Locale.GERMAN);

			cweek.set(Calendar.YEAR, Integer.parseInt(year));     
			cweek.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
			cweek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			String active = "";
			for (int d = 0; d <= 6; d++) {
			    
			    if(d==0) {
					giorno = propLang.get("lun");
					active = " class=\"active\" ";
			    }
				if(d==1) {
					giorno = propLang.get("mar");
				}
				if(d==2) {
					giorno = propLang.get("mer");
				}
				if(d==3) {
					giorno = propLang.get("gio");
				}
				if(d==4) {
					giorno = propLang.get("ven");
				}
				if(d==5) {
					giorno = propLang.get("sab");
				}
				if(d==6) {
					giorno = propLang.get("dom");
				}
				
				giorni.add(new Tables("DAY",""+d,giorno,sdf.format(cweek.getTime()),active," onclick=\"loadDay("+d+")\" ",""));
				
				cweek.add(Calendar.DATE, 1);
				active = "";
			
			}
			obj.put("giorni", giorni);
			////////////////////////////////
			
			
			////////////////////////////////
			// Controlla se la pianificazione è modificabile
			if(tp.equals("I")) {
				int nrStatus = 0;
				
				sql   = "select distinct(coalesce(status , null , '')) status from hr_planification a , vw_emp_dep_indate b "+
						"where plan_year = '"+year+"' and plan_week = '"+week+"' "+
						"and a.id_departments = '"+id_departments+"' "+
						"and a.id_employee = b.id_employee and a.id_departments = b.id_departments "+
						"and b.anno = a.plan_year::integer and b.settimana = a.plan_week::integer "+
						"and b.dow = plan_day::integer ";
						
				System.out.println(sql);
				rs=st.executeQuery(sql);
				String status = "";
				String sts = "";
				while(rs.next()) {
					
					
					status = rs.getString("status");
					System.out.println("status:"+status);
					
					if(status.equals("A")||status.equals("X")) {
						nrStatus = 1;
						sts = status;
					}
					
				}
				rs.close();
				
				if(nrStatus>0) {
					// Settimana da riaprire???
					obj.put("dis","disabled");
				}
				
				if(status.equals("A")) {
					status = "<span class=\"label bg-success\" data-toggle=\"tooltip\" "+
							 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("approvato")+"\">APP</span>";
				}
				else if(status.equals("R")) {
					status = "<span class=\"label bg-danger\" data-toggle=\"tooltip\" "+
							 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("rigettato")+"\">REJ</span>";
				}
				else if(status.equals("X")) {
					status = "<span class=\"label bg-info\" data-toggle=\"tooltip\" "+
							 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("richiesta")+"\">REQ</span>";
				}
				else if(status.equals("L")) {
					status = "<span class=\"label bg-warning\" data-toggle=\"tooltip\" "+
							 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("lavorato")+"\">WRK</span>";
				}
				else if(status.equals("Y")) {
					status = "<span class=\"label bg-light\" data-toggle=\"tooltip\" "+
							 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("riaperto")+"\">OPN</span>";
				}
				obj.put("status", status);
				obj.put("sts", sts);
				
			}
			
			
			String dsdep = getDsdep(conn, id_departments);
			obj.put("dsdep", dsdep);
			
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
		obj.put("day", day);
		obj.put("tp", tp);
		
		
		
		return new ModelAndView("/hr/planner2/planner-stampa", obj);
	}
	
	private static String convertHex(String hex, double opacity) {
		
		hex = hex.replaceAll("#","");
        int r = Integer.parseInt(hex.substring(0,2), 16);
        int g = Integer.parseInt(hex.substring(2,4), 16);
        int b = Integer.parseInt(hex.substring(4,6), 16);

        String result = "rgba("+r+","+g+","+b+","+opacity/100+")";
        return result;
		
	}
	
	
	
	
	public static String getMinnum_employee(Connection conn, String id_departments, String year, String week, String day) throws SQLException {
		
		String value = "";
		
		// Default
		String hh6Dft = "0";
		String hh7Dft = "0";
		String hh8Dft = "0";
		String hh9Dft = "0";
		String hh10Dft = "0";
		String hh11Dft = "0";
		String hh12Dft = "0";
		String hh13Dft = "0";
		String hh14Dft = "0";
		String hh15Dft = "0";
		String hh16Dft = "0";
		String hh17Dft = "0";
		String hh18Dft = "0";
		String hh19Dft = "0";
		String hh20Dft = "0";
		String hh21Dft = "0";
		String hh22Dft = "0";
		String hh23Dft = "0";
		String hh24Dft = "0";
		
		// Default Day
		String hh6DftDay = "0";
		String hh7DftDay = "0";
		String hh8DftDay = "0";
		String hh9DftDay = "0";
		String hh10DftDay = "0";
		String hh11DftDay = "0";
		String hh12DftDay = "0";
		String hh13DftDay = "0";
		String hh14DftDay = "0";
		String hh15DftDay = "0";
		String hh16DftDay = "0";
		String hh17DftDay = "0";
		String hh18DftDay = "0";
		String hh19DftDay = "0";
		String hh20DftDay = "0";
		String hh21DftDay = "0";
		String hh22DftDay = "0";
		String hh23DftDay = "0";
		String hh24DftDay = "0";
		
		// Settimana
		String hh6 = "";
		String hh7 = "";
		String hh8 = "";
		String hh9 = "";
		String hh10 = "";
		String hh11 = "";
		String hh12 = "";
		String hh13 = "";
		String hh14 = "";
		String hh15 = "";
		String hh16 = "";
		String hh17 = "";
		String hh18 = "";
		String hh19 = "";
		String hh20 = "";
		String hh21 = "";
		String hh22 = "";
		String hh23 = "";
		String hh24 = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql   = "";
		try {
			st=conn.createStatement();
			
			// Selezione Default
			sql   = "select 1, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '6') hh6, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '7') hh7, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '8') hh8, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '9') hh9, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '10') hh10, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '11') hh11, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '12') hh12, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '13') hh13, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '14') hh14, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '15') hh15, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '16') hh16, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '17') hh17, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '18') hh18, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '19') hh19, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '20') hh20, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '21') hh21, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '22') hh22, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '23') hh23, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '' and plan_hour = '24') hh24 ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
				hh6Dft = ""+rs.getString("hh6");
				hh6Dft = Utility.noNull(hh6Dft);
				if(hh6Dft.length()==0){hh6Dft="0";}
				
				hh7Dft = ""+rs.getString("hh7");
				hh7Dft = Utility.noNull(hh7Dft);
				if(hh7Dft.length()==0){hh7Dft="0";}
				
				hh8Dft = ""+rs.getString("hh8");
				hh8Dft = Utility.noNull(hh8Dft);
				if(hh8Dft.length()==0){hh8Dft="0";}
				
				hh9Dft = ""+rs.getString("hh9");
				hh9Dft = Utility.noNull(hh9Dft);
				if(hh9Dft.length()==0){hh9Dft="0";}
				
				hh10Dft = ""+rs.getString("hh10");
				hh10Dft = Utility.noNull(hh10Dft);
				if(hh10Dft.length()==0){hh10Dft="0";}
				
				hh11Dft = ""+rs.getString("hh11");
				hh11Dft = Utility.noNull(hh11Dft);
				if(hh11Dft.length()==0){hh11Dft="0";}
				
				hh12Dft = ""+rs.getString("hh12");
				hh12Dft = Utility.noNull(hh12Dft);
				if(hh12Dft.length()==0){hh12Dft="0";}
				
				hh13Dft = ""+rs.getString("hh13");
				hh13Dft = Utility.noNull(hh13Dft);
				if(hh13Dft.length()==0){hh13Dft="0";}
				
				hh14Dft = ""+rs.getString("hh14");
				hh14Dft = Utility.noNull(hh14Dft);
				if(hh14Dft.length()==0){hh14Dft="0";}
				
				hh15Dft = ""+rs.getString("hh15");
				hh15Dft = Utility.noNull(hh15Dft);
				if(hh15Dft.length()==0){hh15Dft="0";}
				
				hh16Dft = ""+rs.getString("hh16");
				hh16Dft = Utility.noNull(hh16Dft);
				if(hh16Dft.length()==0){hh16Dft="0";}
				
				hh17Dft = ""+rs.getString("hh17");
				hh17Dft = Utility.noNull(hh17Dft);
				if(hh17Dft.length()==0){hh17Dft="0";}
				
				hh18Dft = ""+rs.getString("hh18");
				hh18Dft = Utility.noNull(hh18Dft);
				if(hh18Dft.length()==0){hh18Dft="0";}
				
				hh19Dft = ""+rs.getString("hh19");
				hh19Dft = Utility.noNull(hh19Dft);
				if(hh19Dft.length()==0){hh19Dft="0";}
				
				hh20Dft = ""+rs.getString("hh20");
				hh20Dft = Utility.noNull(hh20Dft);
				if(hh20Dft.length()==0){hh20Dft="0";}
				
				hh21Dft = ""+rs.getString("hh21");
				hh21Dft = Utility.noNull(hh21Dft);
				if(hh21Dft.length()==0){hh21Dft="0";}
				
				hh22Dft = ""+rs.getString("hh22");
				hh22Dft = Utility.noNull(hh22Dft);
				if(hh22Dft.length()==0){hh22Dft="0";}
				
				hh23Dft = ""+rs.getString("hh23");
				hh23Dft = Utility.noNull(hh23Dft);
				if(hh23Dft.length()==0){hh23Dft="0";}
				
				hh24Dft = ""+rs.getString("hh24");
				hh24Dft = Utility.noNull(hh24Dft);
				if(hh24Dft.length()==0){hh24Dft="0";}
				
			}
			rs.close();
			
			
			// Selezione Default (day)
			sql   = "select 1, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '6') hh6, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '7') hh7, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '8') hh8, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '9') hh9, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '10') hh10, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '11') hh11, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '12') hh12, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '13') hh13, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '14') hh14, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '15') hh15, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '16') hh16, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '17') hh17, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '18') hh18, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '19') hh19, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '20') hh20, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '21') hh21, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '22') hh22, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '23') hh23, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '' and plan_day = '"+day+"' and plan_hour = '24') hh24 ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
				hh6DftDay = ""+rs.getString("hh6");
				hh6DftDay = Utility.noNull(hh6DftDay);
				if(hh6DftDay.length()>0){hh6Dft=hh6DftDay;}
				
				hh7DftDay = ""+rs.getString("hh7");
				hh7DftDay = Utility.noNull(hh7DftDay);
				if(hh7DftDay.length()>0){hh7Dft=hh7DftDay;}
				
				hh8DftDay = ""+rs.getString("hh8");
				hh8DftDay = Utility.noNull(hh8DftDay);
				if(hh8DftDay.length()>0){hh8Dft=hh8DftDay;}
				
				hh9DftDay = ""+rs.getString("hh9");
				hh9DftDay = Utility.noNull(hh9DftDay);
				if(hh9DftDay.length()>0){hh9Dft=hh9DftDay;}
				
				hh10DftDay = ""+rs.getString("hh10");
				hh10DftDay = Utility.noNull(hh10DftDay);
				if(hh10DftDay.length()>0){hh10Dft=hh10DftDay;}
				
				hh11DftDay = ""+rs.getString("hh11");
				hh11DftDay = Utility.noNull(hh11DftDay);
				if(hh11DftDay.length()>0){hh11Dft=hh11DftDay;}
				
				hh12DftDay = ""+rs.getString("hh12");
				hh12DftDay = Utility.noNull(hh12DftDay);
				if(hh12DftDay.length()>0){hh12Dft=hh12DftDay;}
				
				hh13DftDay = ""+rs.getString("hh13");
				hh13DftDay = Utility.noNull(hh13DftDay);
				if(hh13DftDay.length()>0){hh13Dft=hh13DftDay;}
				
				hh14DftDay = ""+rs.getString("hh14");
				hh14DftDay = Utility.noNull(hh14DftDay);
				if(hh14DftDay.length()>0){hh14Dft=hh14DftDay;}
				
				hh15DftDay = ""+rs.getString("hh15");
				hh15DftDay = Utility.noNull(hh15DftDay);
				if(hh15DftDay.length()>0){hh15Dft=hh15DftDay;}
				
				hh16DftDay = ""+rs.getString("hh16");
				hh16DftDay = Utility.noNull(hh16DftDay);
				if(hh16DftDay.length()>0){hh16Dft=hh16DftDay;}
				
				hh17DftDay = ""+rs.getString("hh17");
				hh17DftDay = Utility.noNull(hh17DftDay);
				if(hh17DftDay.length()>0){hh17Dft=hh17DftDay;}
				
				hh18DftDay = ""+rs.getString("hh18");
				hh18DftDay = Utility.noNull(hh18DftDay);
				if(hh18DftDay.length()>0){hh18Dft=hh18DftDay;}
				
				hh19DftDay = ""+rs.getString("hh19");
				hh19DftDay = Utility.noNull(hh19DftDay);
				if(hh19DftDay.length()>0){hh19Dft=hh19DftDay;}
				
				hh20DftDay = ""+rs.getString("hh20");
				hh20DftDay = Utility.noNull(hh20DftDay);
				if(hh20DftDay.length()>0){hh20Dft=hh20DftDay;}
				
				hh21DftDay = ""+rs.getString("hh21");
				hh21DftDay = Utility.noNull(hh21DftDay);
				if(hh21DftDay.length()>0){hh21Dft=hh21DftDay;}
				
				hh22DftDay = ""+rs.getString("hh22");
				hh22DftDay = Utility.noNull(hh22DftDay);
				if(hh22DftDay.length()>0){hh22Dft=hh22DftDay;}
				
				hh23DftDay = ""+rs.getString("hh23");
				hh23DftDay = Utility.noNull(hh23DftDay);
				if(hh23DftDay.length()>0){hh23Dft=hh23DftDay;}
				
				hh24DftDay = ""+rs.getString("hh24");
				hh24DftDay = Utility.noNull(hh24DftDay);
				if(hh24DftDay.length()>0){hh24Dft=hh24DftDay;}
				
			}
			rs.close();
			
			
			
			
			// Seleziona per settimana
			sql   = "select 1, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '6') hh6, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '7') hh7, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '8') hh8, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '9') hh9, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '10') hh10, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '11') hh11, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '12') hh12, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '13') hh13, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '14') hh14, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '15') hh15, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '16') hh16, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '17') hh17, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '18') hh18, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '19') hh19, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '20') hh20, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '21') hh21, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '22') hh22, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '23') hh23, "+
					"(select value from hr_minnum_employee "+
					"where id_departments = '"+id_departments+"' "+
					"and plan_year = '"+year+"' and plan_week = '"+week+"' and plan_day = '"+day+"' and plan_hour = '24') hh24 ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
				hh6 = ""+rs.getString("hh6");
				hh6 = Utility.noNull(hh6);
				if(hh6.length()==0){hh6=hh6Dft;}
				
				hh7 = ""+rs.getString("hh7");
				hh7 = Utility.noNull(hh7);
				if(hh7.length()==0){hh7=hh7Dft;}
				
				
				hh8 = ""+rs.getString("hh8");
				hh8 = Utility.noNull(hh8);
				if(hh8.length()==0){hh8=hh8Dft;}
				
				hh9 = ""+rs.getString("hh9");
				hh9 = Utility.noNull(hh9);
				if(hh9.length()==0){hh9=hh9Dft;}
				
				hh10 = ""+rs.getString("hh10");
				hh10 = Utility.noNull(hh10);
				if(hh10.length()==0){hh10=hh10Dft;}
				
				hh11 = ""+rs.getString("hh11");
				hh11 = Utility.noNull(hh11);
				if(hh11.length()==0){hh11=hh11Dft;}
				
				hh12 = ""+rs.getString("hh12");
				hh12 = Utility.noNull(hh12);
				if(hh12.length()==0){hh12=hh12Dft;}
				
				hh13 = ""+rs.getString("hh13");
				hh13 = Utility.noNull(hh13);
				if(hh13.length()==0){hh13=hh13Dft;}
				
				hh14 = ""+rs.getString("hh14");
				hh14 = Utility.noNull(hh14);
				if(hh14.length()==0){hh14=hh14Dft;}
				
				hh15 = ""+rs.getString("hh15");
				hh15 = Utility.noNull(hh15);
				if(hh15.length()==0){hh15=hh15Dft;}
				
				hh16 = ""+rs.getString("hh16");
				hh16 = Utility.noNull(hh16);
				if(hh16.length()==0){hh16=hh16Dft;}
				
				hh17 = ""+rs.getString("hh17");
				hh17 = Utility.noNull(hh17);
				if(hh17.length()==0){hh17=hh17Dft;}
				
				hh18 = ""+rs.getString("hh18");
				hh18 = Utility.noNull(hh18);
				if(hh18.length()==0){hh18=hh18Dft;}
				
				hh19 = ""+rs.getString("hh19");
				hh19 = Utility.noNull(hh19);
				if(hh19.length()==0){hh19=hh19Dft;}
				
				hh20 = ""+rs.getString("hh20");
				hh20 = Utility.noNull(hh20);
				if(hh20.length()==0){hh20=hh20Dft;}
				
				hh21 = ""+rs.getString("hh21");
				hh21 = Utility.noNull(hh21);
				if(hh21.length()==0){hh21=hh21Dft;}
				
				hh22 = ""+rs.getString("hh22");
				hh22 = Utility.noNull(hh22);
				if(hh22.length()==0){hh22=hh22Dft;}
				
				hh23 = ""+rs.getString("hh23");
				hh23 = Utility.noNull(hh23);
				if(hh23.length()==0){hh23=hh23Dft;}
				
				hh24 = ""+rs.getString("hh24");
				hh24 = Utility.noNull(hh24);
				if(hh24.length()==0){hh24=hh24Dft;}
				
				value = hh7+","+
						hh8+","+
						hh9+","+
						hh10+","+
						hh11+","+
						hh12+","+
						hh13+","+
						hh14+","+
						hh15+","+
						hh16+","+
						hh17+","+
						hh18+","+
						hh19+","+
						hh20+","+
						hh21+","+
						hh22+","+
						hh23+","+
						hh24;
				
				value=value.replaceAll("\\.00", "");
				value=value.replaceAll("\\.50", "\\.5");
				
			}
			rs.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return value;
		
	}
	
	private String getDsdep(Connection conn, String id_departments) {
		
		String ds = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st = conn.createStatement();
			sql = "select description from hr_departments where id='"+id_departments+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				ds = rs.getString("description");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		return ds;
		
	}
	
}
