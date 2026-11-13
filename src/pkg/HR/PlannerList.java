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
import pkg.beans.HR.Employee;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Tables> wk = new ArrayList<Tables>();
		List<Departments> dep = new ArrayList<Departments>();
		List<Employee> emp = new ArrayList<Employee>();
		List<Tables> day = new ArrayList<Tables>();
		
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
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String tp = req.getParameter("tp");
		String dsfun = "";
		if(tp.equals("I")) {
			dsfun = "Creazione Turni";
		}
		if(tp.equals("A")) {
			dsfun = "Approvazioni";
		}
		if(tp.equals("M")) {
			dsfun = "Controllo";
		}
		
		String id_departments = req.getParameter("id_departments");
		String settimana = req.getParameter("week");
		String anno = req.getParameter("year");
		
		// In caso di inserimento
		String azione = ""+req.getParameter("azione");
		String tipoForm = ""+req.getParameter("tipoForm");
		
		azione=Utility.noNull(azione);
		tipoForm=Utility.noNull(tipoForm);
		
		
		
		String msg = "";
		int count = 0;
		String currentWeek = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// Inserimento / Modifica
			
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Anche in caso di modifica cancello tutto e riscrivo
					sql   = "delete from hr_planification where plan_year = '"+anno+"' and plan_week = '"+settimana+"' "+
							"and id_planification_type = 'da24f14a-1436-4295-8f99-8b1a450b6277' ";
					st.executeUpdate(sql);
					
					String req_id_departments = req.getParameter("id_departments");
					String req_week = req.getParameter("week");
					
					String req_day = "";
					String req_data = "";
					String req_id_employee = "";
					String req_id_departments_day_emp = "";
					String req_turno = "";
					System.out.println(req_id_departments+" "+req_week);
					
					long turnoini = 0;
					long turnofin = 0;
					String dateIni = "";
					String dateFin = "";
					String oreIni = "";
					String oreFin = "";
					Date d_dateIni = null;
					Date d_dateFin = null;
					// ------------------>
					// Giorno
					String[] cd = req.getParameterValues("day");
					if(cd!=null) {
						
						if(cd.length>0) {
							
							for(int i=0; i<cd.length; i++) {
								
								req_day = req.getParameterValues("day")[i];
								req_data = req.getParameterValues("data")[i];
								
								//System.out.println(req_day+" "+req_data);
								
								// ------------------>
								// Dipendente per giorno
								String[] idemp = req.getParameterValues("id_employee"+req_day);
								if(idemp!=null) {
									
									if(idemp.length>0) {
										
										for(int e=0; e<idemp.length; e++) {
											
											req_id_employee = req.getParameterValues("id_employee"+req_day)[e];
											req_id_departments_day_emp = req.getParameter("id_departments"+req_day+req_id_employee);
											
											//System.out.println(req_id_employee+" "+req_id_departments_day_emp);
											
											// ------------------>
											// turni
											String[] turno = req.getParameterValues("p_dati"+req_day+req_id_employee);
											if(turno!=null) {
												
												if(turno.length>0) {
													
													for(int t=0; t<turno.length; t++) {
														
														req_turno = req.getParameterValues("p_dati"+req_day+req_id_employee)[t];
														
														//System.out.println(req_turno);
														
														
														
														String[] ore = req_turno.split(",");
														oreIni = ore[0];
														if(Double.parseDouble(oreIni)<10) {
															oreIni = "0"+oreIni;
														}
														
														oreFin = ore[1];
														if(Double.parseDouble(oreFin)<10) {
															oreFin = "0"+oreFin;
														}
														
														dateIni = req_data.replaceAll("/", "");
														dateIni = dateIni.substring(4,8)+dateIni.substring(2,4)+dateIni.substring(0,2);
														dateIni = dateIni+oreIni.replaceAll("\\.5", "30");
														dateIni = dateIni+"00";
														if(dateIni.length()>12)
															dateIni = dateIni.substring(0,12);
														
														System.out.println(dateIni);
														
														d_dateIni = df.parse(dateIni);
														turnoini = d_dateIni.getTime();
														turnoini = turnoini/1000;
														
														// --------------
														
														dateFin = req_data.replaceAll("/", "");
														dateFin = dateFin.substring(4,8)+dateFin.substring(2,4)+dateFin.substring(0,2);
														dateFin = dateFin+oreFin.replaceAll("\\.5", "30");
														dateFin = dateFin+"00";
														if(dateFin.length()>12)
															dateFin = dateFin.substring(0,12);
														
														System.out.println(dateFin);
														
														d_dateFin = df.parse(dateFin);
														turnofin = d_dateFin.getTime();
														turnofin = turnofin/1000;
														
														// Inserimento in hr_planifiction
														sql   = "insert into hr_planification "+
																"(id_employee, id_departments, datetime_start, datetime_end, "+
																"plan_year, plan_week, plan_day, id_planification_type) "+
																"values "+
																"('"+req_id_employee+"', '"+req_id_departments_day_emp+"', "+turnoini+", "+turnofin+", "+
																"'"+anno+"', '"+settimana+"', '"+req_day+"', 'da24f14a-1436-4295-8f99-8b1a450b6277' ) ";
														st.executeUpdate(sql);
														
														System.out.println(req_data+" - "+dfIta.format(d_dateIni) +" "+ dfIta.format(d_dateFin));
														
														turnoini = 0;
														turnofin = 0;
														
													}
												
												}
												
											}
											// <------------------
											
										}
									
									}
								
								}
								// <-----------------
							}
							
						}
						
					}
					// <-----------------
					
					
					System.out.println("------");
					
				}
				
			}
			
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
				
				dep.add(new Departments($id,$description,$parentid,$address,$locality,$province,$zipcode,
						$state,$phone,$email,"","","","",0,0,0,false,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance(Locale.GERMAN);   
			c.set(Integer.parseInt(oggi.substring(0, 4)), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			System.out.println(currentWeek);
			
			// Per settimanale
			Calendar cal = Calendar.getInstance(Locale.GERMAN);   
			// Imposta l'anno corrente
			cal.set(Integer.parseInt(oggi.substring(0, 4)), 0, 1);
			
			String week = "";
			
			// campi tables
			String $xcdtb = "SET";
			String $xcdel = "";
			String $xdtab = "";
			
			for (int i=1; i<=weeks; i++) {
				  
				week = "00"+i;
				week = week.substring(week.length()-2,week.length());
				
				// valorizza xcdel col numero della settimana
				$xcdel = week;
				
				cal.set(Calendar.WEEK_OF_YEAR, i);        
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				// valorizza il primo giorno della settimana
				$xdtab=sdf.format(cal.getTime())+" - ";
				
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				// valorizza l'ultimo giorno della settimana
				$xdtab+=sdf.format(cal.getTime());
				
				// Settimana attuale
				if($xcdel.equals(currentWeek)) {
					$cmp1 = "selected ";
				}
				
				// valorizza la lista
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,"","",""));
				
				//*blank bariabili
				$xdtab = "";
				$cmp1 = "";
		  	}
			
			
			// ====================
			// Lista utenti del dipartimento/ruolo
			sql  =  "select c.id, c.name, c.surname, d.description, id_departments from "+
					"hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
					"where a.id=b.id_roles "+
					"and b.id_employee = c.id "+ 
					"and b.id_departments = d.id "+
					"and d.id = '"+id_departments+"' ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				emp.add(new Employee(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
						"","","","","","",rs.getString("description"),rs.getString("id_departments"),"","","","","",""));
				
			}
			rs.close();
			
			
			// =====================
			// Settimana
			String giorno = "";
			
			// Get calendar set to current date and time
			Calendar cweek = Calendar.getInstance(Locale.GERMAN);   

			// Set the calendar to monday of the current week
			cweek.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(currentWeek));     
			cweek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			String planification = "";

			for (int d = 0; d < 7; d++) {
			    
			    
			    if(d==0)
					giorno = "Luned&igrave; "+sdf.format(cweek.getTime());
				if(d==1)
					giorno = "Marted&igrave; "+sdf.format(cweek.getTime());
				if(d==2)
					giorno = "Mercoled&igrave; "+sdf.format(cweek.getTime());
				if(d==3)
					giorno = "Gioved&igrave; "+sdf.format(cweek.getTime());
				if(d==4)
					giorno = "Venerd&igrave; "+sdf.format(cweek.getTime());
				if(d==5)
					giorno = "Sabato "+sdf.format(cweek.getTime());
				if(d==6)
					giorno = "Domenica "+sdf.format(cweek.getTime());
				
				
				// Ciclo la lista degli utenti per successivamente leggere il planning
				for (int e = 0; e < emp.size(); e++) {
					
					planification = getEmployeePlanning(anno, settimana, ""+d, emp.get(e).getId());
					
					planification = "";
					
				}
				
				
				
				
				day.add(new Tables("DAY",""+d,giorno,sdf.format(cweek.getTime()),"","",""));
				
				cweek.add(Calendar.DATE, 1);
			
			}
			
			
			
			
			
			
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", dsfun);
		obj.put("currentWeek", currentWeek);
		obj.put("id_departments", id_departments);
		obj.put("tp", tp);
		
		obj.put("plan_year", anno);
		obj.put("plan_week", settimana);
		
		
		obj.put("weeks", wk);
		obj.put("dep", dep);
		obj.put("emp", emp);
		obj.put("day", day);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/planner-list", obj);
	}
	
	public static String getEmployeePlanning(String plan_year, String plan_week, String plan_day, String id_employee) throws SQLException {
		
		String planning = "";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql   = "";
		try {
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			sql   = "select * from hr_planification "+
					"where plan_year = '"+plan_year+"' "+
					"and plan_week = '"+plan_week+"' "+
					"and plan_day = '"+plan_day+"' "+
					"and id_employee = '"+id_employee+"' ";
			//System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				
				planning+="intervals.addPeriod("+rs.getString("datetime_start")+","+rs.getString("datetime_end")+");";
				
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		if(conn!=null)
			conn.close();
		
		return planning;
		
	}

}
