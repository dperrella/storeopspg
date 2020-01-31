package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
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
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ControlPlanRic extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Tables> wk = new ArrayList<Tables>();
		List<Departments> dep = new ArrayList<Departments>();
		List<Tables> sts = new ArrayList<Tables>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		// Pulisce le lista
		wk.clear();
		dep.clear();
		sts.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String dsfun = "Controllo Pianificazioni";
		
		String msg = "";
		int count = 0;
		String currentWeek = "";
		
		// -----------------------
		// Anno
		String year = ""+req.getParameter("year");
		year = Utility.noNull(year);
		if(year.length()==0)
			year = currentYear;
		
		String id_departments = ""+req.getParameter("id_departments");
		id_departments = Utility.noNull(id_departments);
		
		String status = ""+req.getParameter("status");
		status = Utility.noNull(status);
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// =======================
			// Dipartimenti 
			String tipo = (String)session.getAttribute("tipo_hr");
			String username = (String)session.getAttribute("username");
			if(tipo.equals("M")||tipo.equals("S")||tipo.equals("D")) {
				
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
			
			String week = "";
			
			// campi tables
			String $xcdtb = "SET";
			String $xcdel = "";
			String $xdtab = "";
			String status2 = "";
			
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
				status = ""+PlannerRic.getStatus(conn, id_departments, year, week);
				status = Utility.noNull(status);
				
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
				
				// valorizza la lista
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,status,"",""));
				
				//*blank bariabili
				$xdtab = "";
				$cmp1 = "";
		  	}
			*/
			
			
			// Status
			sql  =  "select xcdel, xdtab from ztables where xcdtb = 'STS' ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				sts.add(new Tables("STS",rs.getString("xcdel").trim(),rs.getString("xdtab").trim(),"","","",""));
				
			}
			rs.close();
			
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
		obj.put("status", status);
		
		obj.put("weeks", wk);
		obj.put("dep", dep);
		obj.put("sts", sts);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/control/control-plan-ric", obj);
	}

}
