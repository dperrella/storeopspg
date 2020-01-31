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
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Departments;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerRic extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables> wk = new ArrayList<Tables>();
		List<Departments> dep = new ArrayList<Departments>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		// Pulisce le lista
		wk.clear();
		dep.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String tp = req.getParameter("tp");
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
		
		String msg = "";
		int count = 0;
		String currentWeek = "";
		
		msg = ""+req.getParameter("msg");
		msg = Utility.noNull(msg);
		
		// Id_departments
		String id_departments = ""+req.getParameter("id_departments");
		id_departments = Utility.noNull(id_departments);
		
		// Anno
		String year = ""+req.getParameter("year");
		year = Utility.noNull(year);
		
		if(year.length()==0)
			year = currentYear;
		
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
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// =======================
			// Dipartimenti 
			String tipo = (String)session.getAttribute("tipo_hr");
			String username = (String)session.getAttribute("username");
			if(tipo.equals("M")||tipo.equals("S")||tipo.equals("D")) {
				
				/*
				sql  =  "select a.* "+
						"from hr_departments a, rel_dep_employee b, "+
						"hr_employee c, rel_emp_user d "+
						"where a.id = b.id_departments and c.id = b.id_employee "+
						"and d.id_employee = c.id and b.dateend is null "+
						"and d.id_user = '"+username+"' and a.id_customer = "+session.getAttribute("idcus")+" ";
				*/
				sql  =  "select a.* "+
						"from hr_departments a, rel_dep_employee b, "+
						"hr_employee c, rel_emp_user d "+
						"where "+
						"("+
						"a.id = b.id_departments and c.id = b.id_employee "+
						"and d.id_employee = c.id and to_timestamp(b.dateend) >= now() "+
						"and d.id_user = '"+username+"' and a.id_customer = "+session.getAttribute("idcus")+" "+
						") "+
						"or "+
						"("+
						"a.id = b.id_departments and c.id = b.id_employee "+
						"and d.id_employee = c.id and to_timestamp(b.dateend) is null "+
						"and d.id_user = '"+username+"' and a.id_customer = "+session.getAttribute("idcus")+" "+
						")";
				
			}
			else if(tipo.equals("H")||tipo.equals("A")) {
				sql  =  "select * from hr_departments where id_customer = "+session.getAttribute("idcus")+" "+cond;
			}
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $description="";
			String $parentid="";
			String $address="";
			String $locality="";
			String $province="";
			String $zipcode="";
			String $state="";
			String $phone="";
			String $email="";
			String $department_code="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			String statusDep="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$description=rs.getString("description");
				$parentid=rs.getString("parent_id");
				$address=rs.getString("address");
				$locality=rs.getString("locality");
				$province=rs.getString("province");
				$zipcode=rs.getString("zipcode");
				$state=rs.getString("state");
				$phone=rs.getString("phone");
				$email=rs.getString("email");
				$department_code=rs.getString("department_code");
				
				dep.add(new Departments($id,$description,$parentid,$address,$locality,$province,$zipcode,
								$state,$phone,$email,$department_code,"","","",0,0,0,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance(Locale.GERMAN);
			c.set(Integer.parseInt(year), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  
			
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			// valorizza il primo giorno della settimana
			String $xdtab=sdf.format(c.getTime())+" - ";
			
			c.add(Calendar.DATE, 6);  
			//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
			// valorizza l'ultimo giorno della settimana
			$xdtab+=sdf.format(c.getTime());
			obj.put("currentWeekDesc", $xdtab);
			
			
			
			
			System.out.println(currentWeek);
			
			
			
			/*
			// Per settimanale
			Calendar cal = Calendar.getInstance(Locale.GERMAN);
			// Imposta l'anno corrente
			cal.set(Integer.parseInt(year), 0, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			System.out.println(sdf.format(cal.getTime()));
			obj.put("data", sdf.format(cal.getTime()));
			obj.put("ts", cal.getTime());
			
			String week = "";
			
			// campi tables
			String $xcdtb = "SET";
			String $xcdel = "";
			String $xdtab = "";
			String status = "";
			for (int i=1; i<=weeks; i++) {
				
				week = "00"+i;
				week = week.substring(week.length()-2,week.length());
				
				// valorizza xcdel col numero della settimana
				$xcdel = week;
				
				cal.set(Integer.parseInt(year), 0, 1);
				cal.set(Calendar.WEEK_OF_YEAR, i);        
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				// valorizza il primo giorno della settimana
				$xdtab=sdf.format(cal.getTime())+" - ";
				
				cal.add(Calendar.DATE, 6);  
				//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				// valorizza l'ultimo giorno della settimana
				$xdtab+=sdf.format(cal.getTime());
				
				// Settimana attuale
				if($xcdel.equals(currentWeek)) {
					$cmp1 = "selected ";
				}
				
				status = ""+getStatus(conn, id_departments, year, week);
				status = Utility.noNull(status);
				// valorizza la lista
				if(tp.equals("A")) {
					
					// Controlla se la settimana ha una richiesta di approvazione
					if(status.equals("X")) {
						wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,"","",""));
					}
					
				}
				else {
					
					if(status.equals("R")) {
						status = "#f05050";
					}
					if(status.equals("X")) {
						status = "#545ca6";
					}
					if(status.equals("A")) {
						status = "#1ab667";
					}
					if(status.equals("L")) {
						status = "#4cb6cb";
					}
					// Aggiunge le sole settimane NON LAVORATE
					if(!status.equals("L")) {
						wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,status,"",""));
					}
					status = "";
				}
				//*blank bariabili
				$xdtab = "";
				$cmp1 = "";
		  	}
			*/
			
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", dsfun);
		obj.put("currentWeek", currentWeek);
		obj.put("year", year);
		obj.put("id_departments", id_departments);
		obj.put("tp", tp);
		
		obj.put("weeks", wk);
		obj.put("dep", dep);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/planner-ric", obj);
	}
	
	public static String getStatus(Connection conn, String id_departments, String year, String week) throws SQLException {
		
		String status = "";
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			st=conn.createStatement();
			sql   = "select status, xdtab "+
					"from hr_planification p "+
					"left join ztables on trim(status) = trim(xcdel) "+
					"where p.id_departments = '"+id_departments+"' "+
					"and p.plan_year = '"+year+"' and p.plan_week = '"+week+"' "+
					"and p.id_employee in (select v.id_employee "+
					"from vw_emp_dep_indate v where v.id_departments = '"+id_departments+"' "+
					"and anno = "+year+" and settimana = "+week+" "+
					//"and dow = '0' "+
					") "+
					"group by status, xdtab "+
					"order by status ";
			System.out.println("getstatus = "+sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				status = rs.getString("status");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(st!=null)
			st.close();
		
		return status;
		
	}
	
	public static String getStatusDep(Connection conn, String id_departments) throws SQLException {
		
		String status = "";
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			st=conn.createStatement();
			
			sql   = "select plan_year, plan_week, status from hr_planification "+
					"where id_employee in (select x.id_employee from rel_dep_employee x "+
					"where x.id_departments = '"+id_departments+"') "+
					"group by plan_year, plan_week, status ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				status = rs.getString("status");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(st!=null)
			st.close();
		
		return status;
		
	}
	
}
