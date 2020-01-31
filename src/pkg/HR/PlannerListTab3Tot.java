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

import pkg.beans.HR.Departments;
import pkg.beans.HR.EmployeePlanner;
import pkg.beans.HR.Minnum_employee;
import pkg.beans.HR.Plan2;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerListTab3Tot extends AbstractController {
	
	private static boolean isInt(double d){
		return d % 1 == 0;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Pianificazione
		List<Tables2> tot = new ArrayList<Tables2>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		
		// Formato data per insert
		DateFormat df = new SimpleDateFormat("yyyyMMddkkmm");
		DateFormat dfIta = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		
		
		// Pulisce le lista
		tot.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		
		
		String id_departments = req.getParameter("id_departments");
		String settimana = req.getParameter("week");
		String anno = req.getParameter("year");
		
		
		// Get calendar set to current date and time
		Calendar cw = Calendar.getInstance(Locale.GERMAN);
				
		String msg = "";
		int count = 0;
		String currentWeek = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// ---------
			// Colonne
			String cols = "";
			String rulertot = "";
			for (int i = 0; i <= 36; i++) {
				cols+="h"+(i+1)+" text,";
			}
			cols = cols.substring(0,cols.length()-1);
			System.out.println(cols);
			
			
			Calendar c_heat = Calendar.getInstance(Locale.GERMAN);
			c_heat = Calendar.getInstance(Locale.GERMAN);
			// Set the calendar to monday of the current week
			c_heat.set(Calendar.YEAR, Integer.parseInt(anno));     
			c_heat.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
			c_heat.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			String dtda = "";
			String dtal = "";
			int giorno = 0;
			
			List<String> emp = new ArrayList<String>();
			List<String> t0 = new ArrayList<String>();
			List<String> t1 = new ArrayList<String>();
			List<String> t2 = new ArrayList<String>();
			List<String> t3 = new ArrayList<String>();
			List<String> t4 = new ArrayList<String>();
			List<String> t5 = new ArrayList<String>();
			List<String> t6 = new ArrayList<String>();
			
			for(int g=0;g<=6;g++) {
				giorno = g;
				
				dtda = dateIso.format(c_heat.getTime());
				c_heat.add(Calendar.DATE, 1);
				dtal = dateIso.format(c_heat.getTime());
				
				
				sql   = "select * from crosstab(' select rel.id_employee, rel.name, rel.surname, rel.description, rel.id_departments, "
						+ "rel.b,plan.color from (select name, surname, id_employee, description, id_departments, b from "
						+ "(select * from vw_emp_dep_indate v, hr_employee e, hr_departments d "
						+ "where e.id = id_employee and d.id = v.id_departments "
						+ "and id_departments = ''"+id_departments+"'' "
						+ "and anno = "+anno+" and settimana = "+settimana+") a, "
						+ "generate_series(''"+dtda+" 06:00:00''::timestamp,''"+dtal+" 00:00:00''::timestamp,''30 minutes''::interval) b) "
						+ "rel left join (select to_timestamp(datetime_start),* from hr_planification "
						+ "join hr_planification_type on id_planification_type = hr_planification_type.id "
						+ "where id_departments = ''"+id_departments+"'' and plan_year = ''"+anno+"'' "
						+ "and plan_week = ''"+settimana+"'' and plan_day = ''"+giorno+"'') plan on rel.id_employee = plan.id_employee "
						+ "and rel.b between to_timestamp(datetime_start) and to_timestamp(datetime_end) order by rel.id_employee,rel.b'::text,' "
						+ "select generate_series(''"+dtda+" 06:00:00''::timestamp,''"+dtal+" 00:00:00''::timestamp,''30 minutes''::interval)'::text) "
						+ "as final_result(id_employee text, name text, surname text, description text, id_departments text, "+cols+" "
						+ ") ";
				//System.out.println(sql);
				rs=st.executeQuery(sql);
				
				String $emp_id = "";
				String $emp_name = "";
				String $emp_surname = "";
				double tot_day_emp = 0;
				
				boolean skipfirst = true;
				
				int nrdip = 0;
				while(rs.next()) {
					
					nrdip+=1;
					
					$emp_id = rs.getString("id_employee");
					$emp_name = rs.getString("name");
					$emp_surname = rs.getString("surname");
					
					for (int i = 0; i <= 36; i++) {
						String h=""+rs.getString("h"+(i+1));
						h=Utility.noNull(h);
						// totale dipendente/giorno - inputs
						if(h.equals("turno")) {
							
							if(skipfirst) {
								skipfirst = false;
							}else {
								tot_day_emp+=0.5;
							}
							
							//tot_day_emp+=0.5;
						}
					}
					
					if(giorno==0)
						t0.add(""+tot_day_emp);
					if(giorno==1)
						t1.add(""+tot_day_emp);
					if(giorno==2)
						t2.add(""+tot_day_emp);
					if(giorno==3)
						t3.add(""+tot_day_emp);
					if(giorno==4)
						t4.add(""+tot_day_emp);
					if(giorno==5)
						t5.add(""+tot_day_emp);
					if(giorno==6)
						t6.add(""+tot_day_emp);
					
					
					if(giorno==6) {
						
						emp.add($emp_name+" "+$emp_surname);
						
					}
					
					tot_day_emp = 0;
					
					
					
				}
				rs.close();
				
				
			}
			
			double tt0=0;
			double tt1=0;
			double tt2=0;
			double tt3=0;
			double tt4=0;
			double tt5=0;
			double tt6=0;
			double ttt=0;
			
			
			for(int d=0;d<t0.size();d++) {
				System.out.print(emp.get(d));
				System.out.print(" ");
				System.out.print(t0.get(d));
				System.out.print(" ");
				System.out.print(t1.get(d));
				System.out.print(" ");
				System.out.print(t2.get(d));
				System.out.print(" ");
				System.out.print(t3.get(d));
				System.out.print(" ");
				System.out.print(t4.get(d));
				System.out.print(" ");
				System.out.print(t5.get(d));
				System.out.print(" ");
				System.out.print(t6.get(d));
				
				System.out.println("");
				
				double t = Double.parseDouble(t0.get(d))+
						Double.parseDouble(t1.get(d))+
						Double.parseDouble(t2.get(d))+
						Double.parseDouble(t3.get(d))+
						Double.parseDouble(t4.get(d))+
						Double.parseDouble(t5.get(d))+
						Double.parseDouble(t6.get(d));
				
				tot.add(new Tables2("","",emp.get(d),
						t0.get(d),t1.get(d),t2.get(d),
						t3.get(d),t4.get(d),t5.get(d),
						t6.get(d),""+t,"",""));
				
				tt0+=Double.parseDouble(t0.get(d));
				tt1+=Double.parseDouble(t1.get(d));
				tt2+=Double.parseDouble(t2.get(d));
				tt3+=Double.parseDouble(t3.get(d));
				tt4+=Double.parseDouble(t4.get(d));
				tt5+=Double.parseDouble(t5.get(d));
				tt6+=Double.parseDouble(t6.get(d));
				ttt+=t;
				
			}
			
			obj.put("tot", tot);
			
			obj.put("tt0", tt0);
			obj.put("tt1", tt1);
			obj.put("tt2", tt2);
			obj.put("tt3", tt3);
			obj.put("tt4", tt4);
			obj.put("tt5", tt5);
			obj.put("tt6", tt6);
			obj.put("ttt", ttt);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("currentWeek", currentWeek);
		obj.put("id_departments", id_departments);
		obj.put("plan_year", anno);
		obj.put("plan_week", settimana);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/planner-list-tab3Tot", obj);
	}
	
	public static String getEmployeeDepartment(Connection conn, String plan_year, String plan_week, String plan_day, String id_employee, String id_department_orig, String lang) throws SQLException {
		
		String department = "";
		String status = "";
		Statement st = null;
		ResultSet rs = null;
		
		String sql   = "";
		try {
			//Legge il file di properties associato alla lingua sul profilo
			ReadProperties propLang = new ReadProperties(lang+".properties");
			
			st=conn.createStatement();
			
			sql   = "select id_departments, d.description, status from hr_planification p, hr_departments d "+
					"where p.id_departments = d.id and plan_year = '"+plan_year+"' "+
					"and plan_week = '"+plan_week+"' "+
					"and plan_day = '"+plan_day+"' "+
					"and id_employee = '"+id_employee+"' ";
			//System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
				status = ""+rs.getString("status");
				status = Utility.noNull(status);
				
				if(status.length()>0) {
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
					department+="setStatus('"+plan_day+"', '"+id_employee+"', '"+status+"');\n";
				}
				
				if(!rs.getString("id_departments").equals(id_department_orig)) {
					//System.out.println("diverso...");
					
					department+="setDepartment('"+plan_day+"', '"+id_employee+"', '"+rs.getString("id_departments")+"', '"+rs.getString("description").replaceAll("'", " ")+"');\n";
				}
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return department;
		
	}
	
	public static String getTpApp(Connection conn) throws SQLException {
		
		String tpApp = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql   = "";
		try {
			st=conn.createStatement();
			
			sql   = "select value from zopzioni where name = 'APP' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				tpApp = rs.getString("value");
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return tpApp;
		
	}

	public static String getMinnum_employee(Connection conn, String id_departments, String year, String week, String day) throws SQLException {
		
		String value = "";
		
		// Default
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
		
		// Settimana
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
			
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
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
			
			
			
			// Selezione Default
			sql   = "select 1, "+
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
			
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
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
			
			
			
			// Seleziona per settimana
			sql   = "select 1, "+
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
			
			rs=st.executeQuery(sql);
			if(rs.next()) {
				
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

}
