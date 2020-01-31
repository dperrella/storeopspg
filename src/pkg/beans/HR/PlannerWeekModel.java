package pkg.beans.HR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class PlannerWeekModel {

	private String id_employee;
	private String id_roles;
	private String id_departments;
	private int year;
	private int week;
	private int dow;
	private String name;
	private String surname;
	private String monte;
	private double lun;
	private double mar;
	private double mer;
	private double gio;
	private double ven;
	private double sab;
	private double dom;
	private double tot_emp;
	
	private double lun2;
	private double mar2;
	private double mer2;
	private double gio2;
	private double ven2;
	private double sab2;
	private double dom2;
	private double tot_emp2;
	
	public PlannerWeekModel() {}

	public PlannerWeekModel(String id_employee, String id_roles, String id_departments, 
			int year, int week, int dow, 
			String name, String surname, String monte,
			double lun, double mar, double mer, double gio, double ven, double sab, double dom, double tot_emp,
			double lun2, double mar2, double mer2, double gio2, double ven2, double sab2, double dom2, double tot_emp2) {
		
		this.id_employee=id_employee;
		this.id_roles=id_roles;
		this.id_departments=id_departments;
		this.year=year;
		this.week=week;
		this.dow=dow;
		this.name=name;
		this.surname=surname;
		this.monte=monte;
		this.lun=lun;
		this.mar=mar;
		this.mer=mer;
		this.gio=gio;
		this.ven=ven;
		this.sab=sab;
		this.dom=dom;
		this.tot_emp=tot_emp;
		
		this.lun2=lun2;
		this.mar2=mar2;
		this.mer2=mer2;
		this.gio2=gio2;
		this.ven2=ven2;
		this.sab2=sab2;
		this.dom2=dom2;
		this.tot_emp2=tot_emp2;
	
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
	
	public String getMonte() {
		return monte;
	}
	public void setMonte(String monte) {
		this.monte = monte;
	}
	
	public double getLun() {
		return lun;
	}
	public void setLun(double lun) {
		this.lun = lun;
	}
	
	public double getMar() {
		return mar;
	}
	public void setMar(double mar) {
		this.mar = mar;
	}
	
	public double getMer() {
		return mer;
	}
	public void setMer(double mer) {
		this.mer = mer;
	}
	
	public double getGio() {
		return gio;
	}
	public void setGio(double gio) {
		this.gio = gio;
	}
	
	public double getVen() {
		return ven;
	}
	public void setVen(double ven) {
		this.ven = ven;
	}
	
	public double getSab() {
		return sab;
	}
	public void setSab(double sab) {
		this.sab = sab;
	}
	
	public double getDom() {
		return dom;
	}
	public void setDom(double dom) {
		this.dom = dom;
	}
	
	public double getTot_emp() {
		return tot_emp;
	}
	public void setTot_emp(double tot_emp) {
		this.tot_emp = tot_emp;
	}
	
	
	
	
	
	
	
	
	
	public double getLun2() {
		return lun2;
	}
	public void setLun2(double lun2) {
		this.lun2 = lun2;
	}
	
	public double getMar2() {
		return mar2;
	}
	public void setMar2(double mar2) {
		this.mar2 = mar2;
	}
	
	public double getMer2() {
		return mer2;
	}
	public void setMer2(double mer2) {
		this.mer2 = mer2;
	}
	
	public double getGio2() {
		return gio2;
	}
	public void setGio2(double gio2) {
		this.gio2 = gio2;
	}
	
	public double getVen2() {
		return ven2;
	}
	public void setVen2(double ven2) {
		this.ven2 = ven2;
	}
	
	public double getSab2() {
		return sab2;
	}
	public void setSab2(double sab2) {
		this.sab2 = sab2;
	}
	
	public double getDom2() {
		return dom2;
	}
	public void setDom2(double dom2) {
		this.dom2 = dom2;
	}
	
	public double getTot_emp2() {
		return tot_emp2;
	}
	public void setTot_emp2(double tot_emp2) {
		this.tot_emp2 = tot_emp2;
	}
	
	public List<PlannerWeekModel> selectTotEmployee(Connection conn) {
		
		List<PlannerWeekModel> planner = new ArrayList<PlannerWeekModel>();
		planner.clear();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
	    
		try {
			
			sql   = "select distinct * from vw_totweek_new v \r\n" + 
					"where v.id_departments = '"+id_departments+"'\r\n" + 
					"and v.anno = "+year+" and settimana = "+week+" and dow = "+dow+" order by surname ";
			System.out.println("****"+sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			double tot = 0;
			double tot2 = 0;
			while(rs.next()) {
				
				tot = rs.getDouble("tot_lun")
						+rs.getDouble("tot_mar")
						+rs.getDouble("tot_mer")
						+rs.getDouble("tot_gio")
						+rs.getDouble("tot_ven")
						+rs.getDouble("tot_sab")
						+rs.getDouble("tot_dom");
				
				tot2 = rs.getDouble("tot_lun2")
						+rs.getDouble("tot_mar2")
						+rs.getDouble("tot_mer2")
						+rs.getDouble("tot_gio2")
						+rs.getDouble("tot_ven2")
						+rs.getDouble("tot_sab2")
						+rs.getDouble("tot_dom2");
						
				
				planner.add(new PlannerWeekModel(rs.getString("id_employee"),rs.getString("id_roles"),rs.getString("id_departments"),
						year,week,dow, rs.getString("name"), rs.getString("surname"), ""+(rs.getDouble("monteore")-tot2),
						rs.getDouble("tot_lun"), rs.getDouble("tot_mar"), rs.getDouble("tot_mer"), 
						rs.getDouble("tot_gio"), rs.getDouble("tot_ven"), rs.getDouble("tot_sab"), 
						rs.getDouble("tot_dom"), tot,
						rs.getDouble("tot_lun2"), rs.getDouble("tot_mar2"), rs.getDouble("tot_mer2"), 
						rs.getDouble("tot_gio2"), rs.getDouble("tot_ven2"), rs.getDouble("tot_sab2"), 
						rs.getDouble("tot_dom2"), tot2
						));
				
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
