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
import pkg.beans.HR.Employee;
import pkg.utility.Utility;

public class EmployeeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Employee> emp = new ArrayList<Employee>();
		HttpSession session = req.getSession();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		emp.clear();
		
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
				cond+=" and id || upper(name) || upper(surname) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			//sql  =  "select * from hr_employee where id_customer = "+session.getAttribute("idcus")+" "+cond;
			
			// Get calendar set to current date and time
			Calendar cw = Calendar.getInstance(Locale.GERMAN);
			cw.setTime(date);
			
			String anno = oggi.substring(6, 10);
			int week = cw.get(Calendar.WEEK_OF_YEAR);
			
			// Set the calendar to monday of the current week
			cw.set(Calendar.YEAR, Integer.parseInt(anno));     
			cw.set(Calendar.WEEK_OF_YEAR, week);     
			cw.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			String tick = ""+(cw.getTimeInMillis()/1000);
			
			sql   = "select c.*, coalesce(d.description , null , '') depdesc, to_timestamp(dateend)::date dateend "
					+ "from hr_employee c "
					+ "left join rel_dep_employee v on c.id = v.id_employee "
					+ "and datestart <= "+date.getTime()/1000+" "
					+ "and (dateend is null or dateend <= "+date.getTime()/1000+") " 
					+ "left join hr_departments d on v.id_departments = d.id "
					+ "where c.id_customer = "+session.getAttribute("idcus")+" "
					+ "group by c.id, c.name ,c.surname, c.address, c.locality, c.province, c.zipcode, c.birthdate, c.sex, c.birthlocality, c.birthprovince, c.state, " 
					+ "c.fcode, c.phone, c.email, c.id_customer, d.description, dateend";
			
			sql   = "select c.*, "
					+ "(select d.description from hr_departments d, rel_dep_employee v where c.id = v.id_employee "
					+ "and v.id_departments = d.id and datestart <= "+date.getTime()/1000+" order by datestart desc fetch first 1 rows only) depdesc, "
					+ "(select to_timestamp(dateend)::date dateend from hr_departments d, rel_dep_employee v where c.id = v.id_employee "
					+ "and v.id_departments = d.id and datestart <= "+date.getTime()/1000+" order by datestart desc fetch first 1 rows only) dateend "
					+ "from hr_employee c "
					+ "where c.id_customer = "+session.getAttribute("idcus")+" "
					+ "group by c.id, c.name ,c.surname, c.address, c.locality, c.province, c.zipcode, c.birthdate, c.sex, c.birthlocality, c.birthprovince, c.state, " 
					+ "c.fcode, c.phone, c.email, c.id_customer ";
			
			
			
			sql   = "select e.id, e.name, e.surname, e.matricola,count(id_departments) nr_dep, max(datada) from_date, max(dataal) to_date,\r\n" + 
					"(select description from hr_departments d\r\n" + 
					" join vw_emp_dep_indate v2 on v2.id_departments = d.id\r\n" + 
					" and v2.id_employee = e.id\r\n" + 
					" and anno = "+anno+" and settimana = "+week+" and dow = 0 order by v2.id_departments limit 1) dep,\r\n" + 
					"(select name from hr_roles r\r\n" + 
					" join vw_emp_dep_indate v3 on v3.id_roles = r.id\r\n" + 
					" and v3.id_employee = e.id\r\n" + 
					" and anno = "+anno+" and settimana = "+week+" and dow = 0 order by v3.id_departments limit 1) rol,\r\n" + 
					"(select number from rel_emp_badge \r\n" + 
					" join hr_badge b on b.id = id_badge \r\n" + 
					" where id_employ = e.id and (date_end is null or date_end >= "+tick+") \r\n" + 
					" and b.id_customer = "+session.getAttribute("idcus")+"\r\n" + 
					" limit 1) badge\r\n " +
					"from hr_employee e\r\n" + 
					"left join hr_contracts c on e.id = c.id_employee \r\n" + 
					"left join vw_emp_dep_indate v on v.id_employee = e.id and anno = "+anno+" and settimana = "+week+" and dow = 0\r\n" + 
					"where e.id_customer = "+session.getAttribute("idcus")+"\r\n" + 
					"group by e.id, e.name, e.surname, e.matricola\r\n" + 
					"";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $name="";
			String $surname="";
			String $address="";
			String $locality="";
			String $province="";
			String $zipcode="";
			String $birthdate="";
			String $sex="";
			String $birthlocality="";
			String $birthprovince="";
			String $state="";
			String $fcode="";
			String $phone="";
			String $email="";
			String $matricola="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			String $dep = "";
			String $rol = "";
			String $from_date = "";
			String $to_date = "";
			int $nr_dep = 0;
			String $badge = "";
			String $status = "";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$name=rs.getString("name");
				$surname=rs.getString("surname");
				$matricola=rs.getString("matricola");
				
				$dep = ""+rs.getString("dep");
				$rol = ""+rs.getString("rol");
				$from_date = ""+rs.getString("from_date");
				$to_date = ""+rs.getString("to_date");
				$nr_dep = rs.getInt("nr_dep");
				$badge = ""+rs.getString("badge");
				
				$dep = Utility.noNull($dep);
				if($dep.length()==0)
					$dep = "";
				
				$rol = Utility.noNull($rol);
				if($rol.length()==0)
					$rol = "";
				
				$from_date = Utility.noNull($from_date);
				if($from_date.length()==0)
					$from_date = "-";
				
				$to_date = Utility.noNull($to_date);
				if($to_date.length()==0)
					$to_date = "-";
				
				$badge = Utility.noNull($badge);
				if($badge.length()==0)
					$badge = "";
				
				String err = "";
				if($dep.length()>0&&$rol.length()>0&&$badge.length()>0) {
					$status = "<i class=\"fa fa-circle text-success\"></i>";
				}
				else {
					if($dep.length()==0&&$rol.length()==0&&$badge.length()==0) {
						$status = "<i class=\"fa fa-circle text-danger\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"Miss Department, Role and Badge\"></i>";
						err = "Miss Department, Role and Badge";
					}
					else if($dep.length()>0&&$rol.length()>0&&$badge.length()==0) {
						$status = "<i class=\"fa fa-circle text-warning\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"Miss Badge\"></i>";
						err = "Miss Bagde";
					}
					else if($dep.length()==0&&$rol.length()==0&&$badge.length()>0) {
						$status = "<i class=\"fa fa-circle text-warning\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"Miss Department and Role\"></i>";
						err = "Miss Department and Role";
					}
				}
				
				emp.add(new Employee($id,$name,$surname,$badge,$status,err,$zipcode,$birthdate,$sex,
						$birthlocality,$birthprovince,$state,$fcode,$phone,$email,session.getAttribute("idcus").toString(),
						$matricola,""+$nr_dep,"",$dep,$rol,$from_date,$to_date));
				
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
		
		obj.put("dsfun", "Lista Dipendenti");
		obj.put("emp", emp);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/employee/emp-list", obj);
	}
	
}
