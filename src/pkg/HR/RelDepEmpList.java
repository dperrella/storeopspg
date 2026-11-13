package pkg.HR;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.HR.Departments;
import pkg.beans.HR.RelDepEmp;
import pkg.utility.Utility;

public class RelDepEmpList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<RelDepEmp> rel = new ArrayList<RelDepEmp>();
		List<Departments> dep = new ArrayList<Departments>();
		HttpSession session = req.getSession();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		rel.clear();
		dep.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String id_employee = ""+req.getParameter("id_employee");
		id_employee=Utility.noNull(id_employee);
		
		String id_departments = ""+req.getParameter("id_departments");
		id_departments = Utility.noNull(id_departments);
		
		String all = ""+req.getParameter("all");
		all = Utility.noNull(all);
		
		if(all.equals("false"))
			all = "";
		
		obj.put("id_departments", id_departments);
		obj.put("all", all);
		
		try {
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			// =======================
			// Dipartimenti 
			sql  =  "select * from hr_departments where id_customer = "+session.getAttribute("idcus")+" "+cond;
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
						$state,$phone,$email,$department_code,"","","",0,0,0,false,"","","",""));
				
			}
			rs.close();
			
			
			
			
			if(q.length()>0) {
				cond+=" and b.id_employee || upper(c.name) || upper(c.surname) || upper(d.description) like '%"+q.toUpperCase()+"%' ";
			}
			
			if(id_employee.length()>0){
				cond+=" and b.id_employee = '"+id_employee+"' ";
			}
			
			if(all.length()>0) {
				sql  =  "select b.id_reldepemployee, b.id_employee, b.id_departments, b.id_roles, "+
						"to_timestamp(b.datestart)::date datestart, to_timestamp(b.dateend)::date dateend, datestart dt1, dateend dt2, c.name emp_name, c.surname emp_surname, d.description dep_description, "+
						"a.description rol_description "+
						"from hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
						"where a.id=b.id_roles and b.id_employee = c.id "+
						"and b.id_departments = d.id "+cond+" and b.id_departments = '"+id_departments+"' ";
			}
			else {
				sql  =  "select b.id_reldepemployee, b.id_employee, b.id_departments, b.id_roles, "+
						"to_timestamp(b.datestart)::date datestart, to_timestamp(b.dateend)::date dateend, datestart dt1, dateend dt2, c.name emp_name, c.surname emp_surname, d.description dep_description, "+
						"a.description rol_description "+
						"from hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
						"where a.id=b.id_roles and b.id_employee = c.id "+
						"and b.id_departments = d.id "+cond+" and b.id_departments = '"+id_departments+"' "+
						"and datestart <= "+date.getTime()/1000+" " + 
						"and (dateend is null or dateend > "+date.getTime()/1000+") " +
						"and d.id_customer = "+session.getAttribute("idcus")+" ";
			}
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id_reldepemployee="";
			String $id_employee="";
			String $id_departments="";
			String $id_roles="";
			String $datestart="";
			String $dateend="";
			String $emp_name="";
			String $emp_surname="";
			String $dep_description="";
			String $rol_description="";
			
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id_reldepemployee=rs.getString("id_reldepemployee");
				$id_employee=rs.getString("id_employee");
				$id_departments=rs.getString("id_departments");
				$id_roles=rs.getString("id_roles");
				$datestart=rs.getString("datestart");
				$dateend=""+rs.getString("dateend");
				$dateend=Utility.noNull($dateend);
				
				$emp_name=rs.getString("emp_name");
				$emp_surname=rs.getString("emp_surname");
				$dep_description=rs.getString("dep_description");
				$rol_description=rs.getString("rol_description");
				
				$dateend = ""+rs.getString("dateend");
				$dateend = Utility.noNull($dateend);
				
				if($dateend.length()>0) {
					if(rs.getInt("dt2")<date.getTime()/1000) {
						$cmp4 = "<i class=\"fa  fa-circle text-danger\"></i>";
					}else {
						$cmp4 = "<i class=\"fa  fa-circle text-success\"></i>";
					}
				}
				else {
					if(rs.getInt("dt1")>date.getTime()/1000) {
						$cmp4 = "<i class=\"fa  fa-circle text-danger\"></i>";
					}else {
						$cmp4 = "<i class=\"fa  fa-circle text-success\"></i>";
					}
				}
				
				rel.add(new RelDepEmp($id_reldepemployee,$id_employee,$id_departments,
						$id_roles,$datestart,$dateend,$emp_name,$emp_surname,
						$dep_description,$rol_description,$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Ruoli / Dipertimenti Dipendente");
		obj.put("rel", rel);
		obj.put("dep", dep);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/employee/reldepemp-list", obj);
	}
	
}
