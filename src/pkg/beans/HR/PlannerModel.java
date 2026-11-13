package pkg.beans.HR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class PlannerModel {

	private String id_employee;
	private String id_roles;
	private String id_departments;
	private int year;
	private int week;
	private int dow;
	private int monteore;
	private String name;
	private String surname;
	private String role_description;
	private String plan;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	public PlannerModel() {}

	public PlannerModel(String id_employee, String id_roles, String id_departments, 
			int year, int week, int dow, int monteore, 
			String name, String surname, String role_description, String plan, 
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_employee=id_employee;
		this.id_roles=id_roles;
		this.id_departments=id_departments;
		this.year=year;
		this.week=week;
		this.dow=dow;
		this.monteore=monteore;
		this.name=name;
		this.surname=surname;
		this.role_description=role_description;
		this.plan=plan;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
		
	
	}
	
	public String getId_employee() {
		return id_employee;
	}
	public void setId_employee(String id_employee) {
		this.id_employee = id_employee;
	}
	
	public String getId_roles() {
		return id_roles;
	}
	public void setId_roles(String id_roles) {
		this.id_roles = id_roles;
	}
	
	public String getId_departments() {
		return id_departments;
	}
	public void setId_departments(String id_departments) {
		this.id_departments = id_departments;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getDow() {
		return dow;
	}
	public void setDow(int dow) {
		this.dow = dow;
	}
	
	public int getMonteore() {
		return monteore;
	}
	public void setMonteore(int monteore) {
		this.monteore = monteore;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getRole_description() {
		return role_description;
	}
	public void setRole_description(String role_description) {
		this.role_description = role_description;
	}
	
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	
	public String getCmp1() {
		return cmp1;
	}
	public void setCmp1(String cmp1) {
		this.cmp1 = cmp1;
	}
	
	public String getCmp2() {
		return cmp2;
	}
	public void setCmp2(String cmp2) {
		this.cmp2 = cmp2;
	}
	
	public String getCmp3() {
		return cmp3;
	}
	public void setCmp3(String cmp3) {
		this.cmp3 = cmp3;
	}
	
	public String getCmp4() {
		return cmp4;
	}
	public void setCmp4(String cmp4) {
		this.cmp4 = cmp4;
	}
	
	public List<PlannerModel> selectPlan(Connection conn) {
		
		List<PlannerModel> planner = new ArrayList<PlannerModel>();
		planner.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select v.id_employee, v.id_roles, v.id_departments, v.monteore,\r\n" + 
					"e.name, e.surname, r.description,\r\n" + 
					"json_agg(p.*) AS vw_planner\r\n" + 
					"from vw_emp_dep_indatexxx v\r\n" + 
					"join hr_employee e on e.id = v.id_employee\r\n" + 
					"join hr_roles r on r.id = v.id_roles\r\n" + 					
					"left JOIN vw_planner p ON p.id_employee = v.id_employee\r\n" + 
					"--and p.id_departments = v.id_departments\r\n" + 
					//"and p.plan_year::integer = v.anno\r\n" + 
					//"and p.plan_week::integer = v.settimana\r\n" + 
					//"and p.plan_day::integer = v.dow\r\n" + 
					"and coalesce(p.plan_year, null, '0')::integer = v.anno\n" + 
					"and coalesce(p.plan_week, null, '0')::integer = v.settimana\n" + 
					"and coalesce(p.plan_day, null, '0')::integer = v.dow " +
					"where v.id_departments = '"+id_departments+"'\r\n" + 
					"and v.anno = "+year+" and settimana = "+week+" and dow = "+dow+" \r\n" + 
					"group by v.id_employee, v.id_roles, v.id_departments, v.monteore,\r\n" + 
					"e.name, e.surname, r.description order by e.surname\r\n";
			System.out.println("\nPLAN...");
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				String pln = rs.getString("vw_planner");
				if(pln.equals("[null]"))
					pln = "[]";
				
				planner.add(new PlannerModel(rs.getString("id_employee"),rs.getString("id_roles"),rs.getString("id_departments"),
						year,week,dow,rs.getInt("monteore"),rs.getString("name"),
						rs.getString("surname"),rs.getString("description"),pln,"","","",""));
				
			}
			rs.close();
			st.close();
			
			
			////////////
			sql   = "select a.id_employee, a.plan_day, b.name, b.surname, \n" + 
					"json_agg(p.*) AS vw_planner\n" + 
					"from hr_planification a\n" + 
					"join hr_employee b on a.id_employee = b.id\n" + 
					"left JOIN vw_planner p ON p.id_employee = a.id_employee\n" + 
					"and p.id_departments = a.id_departments \n " +
					"and p.plan_year::integer = a.plan_year::integer\n" + 
					"and p.plan_week::integer = a.plan_week::integer\n" + 
					"and p.plan_day::integer = a.plan_day::integer\n" + 
					"where a.plan_year::integer= "+year+" \n" + 
					"and a.plan_week::integer = "+week+" \n" + 
					"and a.id_departments = '"+id_departments+"' \n" + 
					"and a.plan_day::integer = "+dow+" \n" + 
					"and a.id_employee not in(\n" + 
					"select v.id_employee from vw_emp_dep_indate v\n" + 
					"	where v.id_departments = a.id_departments\n" + 
					"	and v.anno = a.plan_year::integer and settimana = a.plan_week::integer \n" + 
					"	and dow = a.plan_day::integer \n" + 
					") \n" + 
					"group by a.id_employee, a.plan_day, b.name, b.surname \n" + 
					"order by a.id_employee";
			System.out.println("\nTRASF...");
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				String pln = rs.getString("vw_planner");
				if(pln.equals("[null]"))
					pln = "[]";
				
				planner.add(new PlannerModel(rs.getString("id_employee"),"",id_departments,
						year,week,dow,0,rs.getString("name"),
						rs.getString("surname"),"",pln,"","","","TRASF"));
				
			}
			rs.close();
			st.close();
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
		
		return planner;
		
	}
	
	
	public List<PlannerModel> selectTrasf(Connection conn) {
		
		List<PlannerModel> planner = new ArrayList<PlannerModel>();
		planner.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select a.id_employee, a.plan_day, b.name, b.surname, \n" + 
					"json_agg(p.*) AS vw_planner\n" + 
					"from hr_planification a\n" + 
					"join hr_employee b on a.id_employee = b.id\n" + 
					"left JOIN vw_planner p ON p.id_employee = a.id_employee\n" + 
					"and p.plan_year::integer = a.plan_year::integer\n" + 
					"and p.plan_week::integer = a.plan_week::integer\n" + 
					"and p.plan_day::integer = a.plan_day::integer\n" + 
					"where a.plan_year='2019' \n" + 
					"and a.plan_week = '04' \n" + 
					"and a.id_departments = 'aa41914e-5c3c-42b9-8bfd-aed06486fcb5' \n" + 
					"and a.plan_day = '1' \n" + 
					"and a.id_employee not in(\n" + 
					"select v.id_employee from vw_emp_dep_indate v\n" + 
					"	where v.id_departments = a.id_departments\n" + 
					"	and v.anno = a.plan_year::integer and settimana = a.plan_week::integer \n" + 
					"	and dow = a.plan_day::integer \n" + 
					") \n" + 
					"group by a.id_employee, a.plan_day, b.name, b.surname \n" + 
					"order by a.id_employee";
			
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				String pln = rs.getString("vw_planner");
				if(pln.equals("[null]"))
					pln = "[]";
				
				planner.add(new PlannerModel(rs.getString("id_employee"),rs.getString("id_roles"),rs.getString("id_departments"),
						year,week,dow,rs.getInt("monteore"),rs.getString("name"),
						rs.getString("surname"),rs.getString("description"),pln,"","","",""));
				
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
		
		return planner;
		
	}
	
	
	public List<PlannerModel> selectPlanEmployee(Connection conn) {
		
		List<PlannerModel> planner = new ArrayList<PlannerModel>();
		planner.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select v.id_employee, v.id_roles, v.id_departments, v.monteore,\r\n" + 
					"e.name, e.surname, r.description,\r\n" + 
					"json_agg(p.*) AS vw_planner\r\n" + 
					"from vw_emp_dep_indatexxx v\r\n" + 
					"join hr_employee e on e.id = v.id_employee\r\n" + 
					"join hr_roles r on r.id = v.id_roles\r\n" + 					
					"left JOIN vw_planner p ON p.id_employee = v.id_employee\r\n" + 
					"--and p.id_departments = v.id_departments\r\n" + 
					"and p.plan_year::integer = v.anno\r\n" + 
					"and p.plan_week::integer = v.settimana\r\n" + 
					"--and p.plan_day::integer = v.dow\r\n" + 
					"where v.id_departments = '"+id_departments+"'\r\n" + 
					"and v.anno = "+year+" and settimana = "+week+" and dow = "+dow+" \r\n" + 
					"and v.id_employee = '"+id_employee+"' \r\n" + 
					"group by v.id_employee, v.id_roles, v.id_departments, v.monteore,\r\n" + 
					"e.name, e.surname, r.description\r\n";
			
			System.out.println("******* - - "+sql+"");
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				String pln = rs.getString("vw_planner");
				if(pln.equals("[null]"))
					pln = "[]";
				
				planner.add(new PlannerModel(rs.getString("id_employee"),rs.getString("id_roles"),rs.getString("id_departments"),
						year,week,dow,rs.getInt("monteore"),rs.getString("name"),
						rs.getString("surname"),rs.getString("description"),pln,"","","",""));
				
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
		
		return planner;
		
	}
	
}
