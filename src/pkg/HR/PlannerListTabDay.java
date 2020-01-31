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
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerListTabDay extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> wk = new ArrayList<Tables>();
		List<Departments> dep = new ArrayList<Departments>();
		List<EmployeePlanner> emp = new ArrayList<EmployeePlanner>();
		List<Tables> day = new ArrayList<Tables>();
		List<Minnum_employee> minnum = new ArrayList<Minnum_employee>();
		
		// Dipendenti trasferiti nel dipartimento
		List<Tables> trasf = new ArrayList<Tables>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		
		// Formato data per insert
		DateFormat df = new SimpleDateFormat("yyyyMMddkkmm");
		DateFormat dfIta = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		
		
		// Pulisce le lista
		wk.clear();
		dep.clear();
		emp.clear();
		day.clear();
		minnum.clear();
		
		trasf.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String vis = ""+req.getParameter("vis");
		vis=Utility.noNull(vis);
		if(vis.length()==0)
			vis="1";
		obj.put("vis", vis);
		
		String tpvis = ""+req.getParameter("tpvis");
		tpvis=Utility.noNull(tpvis);
		if(tpvis.length()==0)
			tpvis="1";
		obj.put("tpvis", tpvis);
		
		if(tpvis.equals("2")) {
			obj.put("btn", "btn-xs");
			obj.put("rowheight", 20);
			obj.put("width", 30);
			obj.put("h4", "h4");
			
		}
		else {
			obj.put("rowheight", 40);
			obj.put("width", 50);
			obj.put("h3", "h3 font-thin");
		}
		
		String id_departments = req.getParameter("id_departments");
		String settimana = req.getParameter("week");
		String anno = req.getParameter("year");
		String giorno = req.getParameter("day");
		obj.put("giorno", giorno);
		
		String dis = req.getParameter("dis");
		// Settimana da riaprire???
		obj.put("dis",dis);
		
		// Get calendar set to current date and time
		Calendar cw = Calendar.getInstance(Locale.GERMAN);
		

		// Calcolo data inizio e data fine settimana
		String dtini = "";
		String dtfin = "";
		
		// Set the calendar to monday of the current week
		cw.set(Calendar.YEAR, Integer.parseInt(anno));     
		cw.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
		cw.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		dtini = ""+(cw.getTimeInMillis()/1000);
		for (int d = 0; d < 6; d++) {
			cw.add(Calendar.DATE, 1);
		}
		dtfin = ""+(cw.getTimeInMillis()/1000);
		
		String msg = "";
		int count = 0;
		String currentWeek = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			sql = "select c.id, c.name, c.surname, d.description, v.id_departments "
				+ "from vw_emp_dep_indate v, hr_employee c, hr_departments d "
				+ "where c.id = v.id_employee and d.id = v.id_departments "
				+ "and anno = "+anno+" and settimana = "+settimana+" and v.id_Departments = '"+id_departments+"'"
				+ "group by c.id, c.name, c.surname, d.description, v.id_departments ";
			
			//System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String arrayDep = "";
			
			int nrdip = 0;
			String fun0 = "";
			String fun1 = "";
			String fun2 = "";
			String fun3 = "";
			String fun4 = "";
			String fun5 = "";
			String fun6 = "";
			
			while(rs.next()) {
				nrdip+=1;
				
				arrayDep+="'"+rs.getString("id")+"',";
				
				/*
				emp.add(new EmployeePlanner(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
						"","","","","","",
						// Lun - Sab
						Utility.getEmployeePlanningNew(conn, anno, settimana, "0", rs.getString("id"),""),
						Utility.getEmployeePlanningNew(conn, anno, settimana, "1", rs.getString("id"),""),
						Utility.getEmployeePlanningNew(conn, anno, settimana, "2", rs.getString("id"),""),
						Utility.getEmployeePlanningNew(conn, anno, settimana, "3", rs.getString("id"),""),
						Utility.getEmployeePlanningNew(conn, anno, settimana, "4", rs.getString("id"),""),
						Utility.getEmployeePlanningNew(conn, anno, settimana, "5", rs.getString("id"),""),
						Utility.getEmployeePlanningNew(conn, anno, settimana, "6", rs.getString("id"),""),
						rs.getString("description"),rs.getString("id_departments"),
						"",Utility.getBadge(conn, rs.getString("id"), dtini, dtfin)));
				*/
				
				if(giorno.equals("0")) {
					fun0 = Utility.getEmployeePlanningNew(conn, anno, settimana, "0", rs.getString("id"),"","");
				}
				if(giorno.equals("1")) {
					fun1 = Utility.getEmployeePlanningNew(conn, anno, settimana, "1", rs.getString("id"),"","");
				}
				if(giorno.equals("2")) {
					fun2 = Utility.getEmployeePlanningNew(conn, anno, settimana, "2", rs.getString("id"),"","");
				}
				if(giorno.equals("3")) {
					fun3 = Utility.getEmployeePlanningNew(conn, anno, settimana, "3", rs.getString("id"),"","");
				}
				if(giorno.equals("4")) {
					fun4 = Utility.getEmployeePlanningNew(conn, anno, settimana, "4", rs.getString("id"),"","");
				}
				if(giorno.equals("5")) {
					fun5 = Utility.getEmployeePlanningNew(conn, anno, settimana, "5", rs.getString("id"),"","");
				}
				if(giorno.equals("6")) {
					fun6 = Utility.getEmployeePlanningNew(conn, anno, settimana, "6", rs.getString("id"),"","");
				}
				
				emp.add(new EmployeePlanner(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
						"","","","","","",
						// Lun - Sab
						fun0,
						fun1,
						fun2,
						fun3,
						fun4,
						fun5,
						fun6,
						rs.getString("description"),rs.getString("id_departments"),
						"",Utility.getBadge(conn, rs.getString("id"), dtini, dtfin)));
				
			}
			rs.close();
			
			obj.put("nrdip", nrdip);
			
			if(arrayDep.length()>0) {
				arrayDep=arrayDep.substring(0,arrayDep.length()-1);
				
				sql =   "select id_employee, plan_day, b.name, b.surname from hr_planification a, hr_employee b  "+
						"where a.id_employee = b.id and plan_year='"+anno+"' and plan_week = '"+settimana+"' "+
						"and id_departments = '"+id_departments+"' and plan_day = '"+giorno+"' "+
						"and id_employee not in("+arrayDep+") "+
						"group by id_employee, plan_day, b.name, b.surname "+
						"order by id_employee ";
				System.out.println("XXXXXXX:"+sql);
				rs=st.executeQuery(sql);
				
				String d0 = "";
				String d1 = "";
				String d2 = "";
				String d3 = "";
				String d4 = "";
				String d5 = "";
				String d6 = "";
				
				String rott = "";
				
				String xx_id_employee = "";
				String xx_name = "";
				String xx_surname = "";
				
				int numdip = 0;
				
				while(rs.next()) {
					
					numdip+=1;
					
					if(rott.length()>0) {
						
						if(!rott.equals(rs.getString("id_employee"))) {
							
							/*
							emp.add(new EmployeePlanner(xx_id_employee,xx_name,xx_surname,"","","","","","",
									"","","","","","",
									// Lun - Sab
									d0,
									d1,
									d2,
									d3,
									d4,
									d5,
									d6,
									"","","",Utility.getBadge(conn, xx_id_employee, dtini, dtfin)));
							*/
							
							d0 = "";
							d1 = "";
							d2 = "";
							d3 = "";
							d4 = "";
							d5 = "";
							d6 = "";
						}
					}
					
					xx_id_employee = rs.getString("id_employee");
					xx_name = rs.getString("name");
					xx_surname = rs.getString("surname");
					
					/*
					if(rs.getString("plan_day").trim().equals("0"))
						d0 = Utility.getEmployeePlanningNew(conn, anno, settimana, "0", rs.getString("id_employee"));
					if(rs.getString("plan_day").trim().equals("1"))
						d1 = Utility.getEmployeePlanningNew(conn, anno, settimana, "1", rs.getString("id_employee"));
					if(rs.getString("plan_day").trim().equals("2"))
						d2 = Utility.getEmployeePlanningNew(conn, anno, settimana, "2", rs.getString("id_employee"));
					if(rs.getString("plan_day").trim().equals("3"))
						d3 = Utility.getEmployeePlanningNew(conn, anno, settimana, "3", rs.getString("id_employee"));
					if(rs.getString("plan_day").trim().equals("4"))
						d4 = Utility.getEmployeePlanningNew(conn, anno, settimana, "4", rs.getString("id_employee"));
					if(rs.getString("plan_day").trim().equals("5"))
						d5 = Utility.getEmployeePlanningNew(conn, anno, settimana, "5", rs.getString("id_employee"));
					if(rs.getString("plan_day").trim().equals("6"))
						d6 = Utility.getEmployeePlanningNew(conn, anno, settimana, "6", rs.getString("id_employee"));
					*/
					d0 = Utility.getEmployeePlanningNew(conn, anno, settimana, giorno, rs.getString("id_employee"),"S","");
					// valorizza la lista
					trasf.add(new Tables(rs.getString("id_employee"),rs.getString("name"),rs.getString("surname"),
							d0,Utility.getBadge(conn, rs.getString("id_employee"), dtini, dtfin),"",""));
					
					rott = rs.getString("id_employee");
					
				}
				rs.close();
				
				obj.put("trasf", trasf);
				
				if(numdip>0) {
					// Ultimo record
					/*
					emp.add(new EmployeePlanner(xx_id_employee,xx_name,xx_surname,"","","","","","",
							"","","","","","",
							// Lun - Sab
							d0,
							d1,
							d2,
							d3,
							d4,
							d5,
							d6,
							"","","",Utility.getBadge(conn, xx_id_employee, dtini, dtfin)));
					*/
					d0 = "";
					d1 = "";
					d2 = "";
					d3 = "";
					d4 = "";
					d5 = "";
					d6 = "";
					
				}
				
			}
			
			// =====================
			// Settimana
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			// Get calendar set to current date and time
			Calendar cweek = Calendar.getInstance(Locale.GERMAN); 
			
			String newDepart = "";
			String minNum_employee = "";

			int d = Integer.parseInt(giorno);
			
			// Set the calendar to monday of the current week
			cweek.set(Calendar.YEAR, Integer.parseInt(anno));     
			cweek.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
			
			if(d==0)
				cweek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			else if(d==1)
				cweek.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			else if(d==2)
				cweek.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			else if(d==3)
				cweek.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			else if(d==4)
				cweek.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			else if(d==5)
				cweek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			else if(d==6)
				cweek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
		    if(d==0)
				giorno = propLang.get("lun")+" "+sdf.format(cweek.getTime());
			if(d==1)
				giorno = propLang.get("mar")+" "+sdf.format(cweek.getTime());
			if(d==2)
				giorno = propLang.get("mer")+" "+sdf.format(cweek.getTime());
			if(d==3)
				giorno = propLang.get("gio")+" "+sdf.format(cweek.getTime());
			if(d==4)
				giorno = propLang.get("ven")+" "+sdf.format(cweek.getTime());
			if(d==5)
				giorno = propLang.get("sab")+" "+sdf.format(cweek.getTime());
			if(d==6)
				giorno = propLang.get("dom")+" "+sdf.format(cweek.getTime());
			
			minNum_employee = getMinnum_employee(conn, id_departments, anno, settimana, ""+d);
		
			// Ciclo la lista degli utenti sapere se sono stati spostati in altro dipartimento
			for (int e = 0; e < emp.size(); e++) {
				
				newDepart+= getEmployeeDepartment(conn, anno, settimana, ""+d, emp.get(e).getId(), id_departments, (String)session.getAttribute("language"));
				
			}
			
			day.add(new Tables("DAY",""+d,giorno,sdf.format(cweek.getTime()),minNum_employee,"",""));
			
			cweek.add(Calendar.DATE, 1);
			
			obj.put("newDepart", newDepart);
			
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
		
		
		obj.put("weeks", wk);
		obj.put("dep", dep);
		obj.put("emp", emp);
		obj.put("day", day);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/planner-list-tabDay", obj);
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
						status = "<span class=\"label bg-success\">"+propLang.get("approvato")+"</span>";
					}
					else if(status.equals("R")) {
						status = "<span class=\"label bg-danger\">"+propLang.get("rigettato")+"</span>";
					}
					else if(status.equals("X")) {
						status = "<span class=\"label bg-info\">"+propLang.get("richiesta")+"</span>";
					}
					else if(status.equals("L")) {
						status = "<span class=\"label bg-warning\">"+propLang.get("lavorato")+"</span>";
					}
					else if(status.equals("Y")) {
						status = "<span class=\"label bg-light\">"+propLang.get("riaperto")+"</span>";
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
