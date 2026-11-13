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

public class PlannerListTab extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Utente sessione
		String idusr = (String)session.getAttribute("username");
		
		List<Tables> wk = new ArrayList<Tables>();
		List<Departments> dep = new ArrayList<Departments>();
		List<EmployeePlanner> emp = new ArrayList<EmployeePlanner>();
		List<Tables> day = new ArrayList<Tables>();
		List<Minnum_employee> minnum = new ArrayList<Minnum_employee>();
		
		// Dipendenti trasferiti nel dipartimento
		List<Tables> trasf0 = new ArrayList<Tables>();
		
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
		
		trasf0.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		String tp = ""+req.getParameter("tp");
		tp=Utility.noNull(tp);
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
			obj.put("margin", "style=\"margin-bottom:65px\"");
		}
		else {
			obj.put("rowheight", 40);
			obj.put("width", 50);
			obj.put("h3", "h3 font-thin");
			obj.put("margin", "style=\"margin-bottom:120px\"");
		}
		
		//Per capire quali giorni sono stati caricati controlla un flag
		String caricoDay1 = ""+req.getParameter("caricoDay1");
		String caricoDay2 = ""+req.getParameter("caricoDay2");
		String caricoDay3 = ""+req.getParameter("caricoDay3");
		String caricoDay4 = ""+req.getParameter("caricoDay4");
		String caricoDay5 = ""+req.getParameter("caricoDay5");
		String caricoDay6 = ""+req.getParameter("caricoDay6");
		
		caricoDay1=Utility.noNull(caricoDay1);
		caricoDay2=Utility.noNull(caricoDay2);
		caricoDay3=Utility.noNull(caricoDay3);
		caricoDay4=Utility.noNull(caricoDay4);
		caricoDay5=Utility.noNull(caricoDay5);
		caricoDay6=Utility.noNull(caricoDay6);
		
		String day_delete = "'0',";
		if(caricoDay1.length()==0)
			day_delete+="'1',";
		if(caricoDay2.length()==0)
			day_delete+="'2',";
		if(caricoDay3.length()==0)
			day_delete+="'3',";
		if(caricoDay4.length()==0)
			day_delete+="'4',";
		if(caricoDay5.length()==0)
			day_delete+="'5',";
		if(caricoDay6.length()==0)
			day_delete+="'6',";
		
		day_delete = day_delete.substring(0,day_delete.length()-1);
		
		String id_departments = req.getParameter("id_departments");
		String settimana = req.getParameter("week");
		String anno = req.getParameter("year");
		
		// In caso di inserimento
		String azione = ""+req.getParameter("azione");
		String tipoForm = ""+req.getParameter("tipoForm");
		
		azione=Utility.noNull(azione);
		tipoForm=Utility.noNull(tipoForm);
		
		// Calcolo data inizio e data fine settimana
		String dtini = "";
		String dtfin = "";
		
		// Get calendar set to current date and time
		Calendar cw = Calendar.getInstance(Locale.GERMAN);

		// Set the calendar to monday of the current week
		cw.set(Calendar.YEAR, Integer.parseInt(anno));     
		cw.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
		cw.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		dtini = ""+(cw.getTimeInMillis()/1000);
		for (int d = 0; d < 6; d++) {
			cw.add(Calendar.DATE, 1);
		}
		dtfin = ""+(cw.getTimeInMillis()/1000);
		
		//System.out.println(dtini+" "+dtfin);
		
		String richiestaApprovazione = ""+req.getParameter("richiestaApprovazione");
		richiestaApprovazione = Utility.noNull(richiestaApprovazione);
		
		obj.put("richiestaApprovazione", richiestaApprovazione);
		
		
		String msg = "";
		int count = 0;
		String currentWeek = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// In caso di approvazione legge da options come la deve fare
			String tpApp = "";
			if(tp.equals("A")) {
				tpApp = getTpApp(conn);
			}
			obj.put("tpApp", tpApp.trim());
			
			// Inserimento / Modifica
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Anche in caso di modifica cancello tutto e riscrivo
					sql   = "delete from hr_planification "+
							"where plan_year = '"+anno+"' "+
							"and plan_week = '"+settimana+"' "+
							"and plan_day in("+day_delete+") "+
							"and id_employee in (select x.id_employee from vw_emp_dep_indate x "+
							"where x.id_departments = '"+id_departments+"' and x.anno = "+anno+" and x.settimana = "+settimana+") "+
							"and id_planification_type = 'da24f14a-1436-4295-8f99-8b1a450b6277' ";
					
					System.out.println(sql);
					
					st.executeUpdate(sql);
					
					
					// porta lo status a ' ' per le pianificazioni
					sql   = "update hr_planification "+
							"set status = ' ' "+
							"where plan_year = '"+anno+"' "+
							"and plan_week = '"+settimana+"' "+
							"and plan_day in('0', '1', '2', '3', '4', '5', '6') "+
							"and id_employee in (select x.id_employee from vw_emp_dep_indate x "+
							"where x.id_departments = '"+id_departments+"' and x.anno = "+anno+" and x.settimana = "+settimana+") "+
							//"and id_planification_type <> 'da24f14a-1436-4295-8f99-8b1a450b6277' ";
							"";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					
					String req_id_departments = req.getParameter("id_departments");
					String req_week = req.getParameter("week");
					
					String req_day = "";
					String req_data = "";
					String req_id_employee = "";
					String req_id_departments_day_emp = "";
					String req_turno = "";
					//System.out.println(req_id_departments+" "+req_week);
					
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
					
					String last_ts = "";
					Calendar c = Calendar.getInstance(Locale.GERMAN);
					c.setTime(new Date());
					last_ts = ""+(c.getTimeInMillis() / 1000);
					
					System.out.println("------");
					
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
														oreIni = ore[0].trim();
														if(Double.parseDouble(oreIni)<10)
															oreIni = "0"+oreIni;
														
														oreFin = ore[1].trim();
														if(Double.parseDouble(oreFin)<10)
															oreFin = "0"+oreFin;
														
														oreIni=oreIni.replaceAll("\\.5", "30");
														oreIni=oreIni.replaceAll("\\.0", "");
														oreIni=oreIni+"00";
														
														oreFin=oreFin.replaceAll("\\.0", "");
														oreFin=oreFin.replaceAll("\\.5", "30");
														oreFin=oreFin+"00";
														
														if(oreIni.length()>4)
															oreIni = oreIni.substring(0, 4);
														
														if(oreFin.length()>4)
															oreFin = oreFin.substring(0, 4);
														
														System.out.println(oreIni+" "+oreFin);
														
														dateIni = req_data.replaceAll("/", "");
														dateIni = dateIni.substring(4,8)+dateIni.substring(2,4)+dateIni.substring(0,2);
														dateIni = dateIni+oreIni;
														
														//System.out.println(dateIni);
														
														d_dateIni = df.parse(dateIni);
														turnoini = d_dateIni.getTime();
														turnoini = turnoini/1000;
														
														// --------------
														
														dateFin = req_data.replaceAll("/", "");
														dateFin = dateFin.substring(4,8)+dateFin.substring(2,4)+dateFin.substring(0,2);
														// Se l'ora è "2400" aggiunge un giorno alla data
														if(oreFin.equals("2400")) {
															
															Calendar cfin = Calendar.getInstance(Locale.GERMAN);
															cfin.setTime(dateNum.parse(dateFin));
															cfin.add(Calendar.DATE, 1);
															
															dateFin = dateNum.format(cfin.getTime());
															
														}
														
														dateFin = dateFin+oreFin;
														
														//System.out.println(dateFin);
														
														d_dateFin = df.parse(dateFin);
														turnofin = d_dateFin.getTime();
														turnofin = turnofin/1000;
														
														// Inserimento in hr_planifiction
														sql   = "insert into hr_planification "+
																"(id_employee, id_departments, datetime_start, datetime_end, "+
																"plan_year, plan_week, plan_day, id_planification_type, last_ts, idusr_ins) "+
																"values "+
																"('"+req_id_employee+"', '"+req_id_departments_day_emp+"', "+turnoini+", "+turnofin+", "+
																"'"+anno+"', '"+settimana+"', '"+req_day+"', 'da24f14a-1436-4295-8f99-8b1a450b6277', "+last_ts+" , '"+idusr+"' ) ";
														System.out.println(sql);
														
														try {
															st.executeUpdate(sql);
														}
														catch(Exception err) {
															err.printStackTrace();
														}
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
			
			// Controlla se la pianificazione è modificabile
			if(tp.equals("I")) {
				int nrStatus = 0;
				// OLD
				/*
				sql  =  "select count(status) nrStatus from hr_planification "+
						"where plan_year = '"+anno+" ' "+
						"and plan_week = '"+settimana+"' "+
						"and id_departments = '"+id_departments+"' "+
						"and status not in ('' , 'R', 'Y') fetch first 1 rows only ";
				*/
				
				sql   = "select count(status) nrStatus from hr_planification a , vw_emp_dep_indate b "+
						"where plan_year = '"+anno+"' and plan_week = '"+settimana+"' "+
						"and a.id_departments = '"+id_departments+"' "+
						"and a.id_employee = b.id_employee and a.id_departments = b.id_departments "+
						"and b.anno = cast(a.plan_year as int) and b.settimana = cast(a.plan_week as int) "+
						"and b.dow = cast(plan_day as int) "+
						"and status not in ('' , 'R', 'Y') fetch first 1 rows only ";
				
				System.out.println(sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					nrStatus = rs.getInt("nrStatus");
				}
				rs.close();
				
				if(nrStatus>0) {
					// Settimana da riaprire???
					obj.put("dis","disabled");
					
				}
				
			}
				
			
			// =======================
			// Dipartimenti 
			sql  =  "select * from hr_departments where id='"+id_departments+"' ";
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
			String $minnum_intervals="";
			String $tollerance = "";
			
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
				$minnum_intervals=rs.getString("minnum_intervals");
				$tollerance=rs.getString("tollerance");
				
				dep.add(new Departments($id,$description,$parentid,$address,$locality,$province,$zipcode,
						$state,$phone,$email,$department_code,$minnum_intervals,$tollerance,"",0,0,0,false,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance(Locale.GERMAN);
			c.set(Integer.parseInt(anno), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			//System.out.println(currentWeek);
			
			// Per settimanale
			Calendar cal = Calendar.getInstance(Locale.GERMAN);
			// Imposta l'anno corrente
			cal.set(Integer.parseInt(anno), 0, 1);
			
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
				
				cal.set(Integer.parseInt(anno), 0, 1);
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
				
				// valorizza la lista
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,"","",""));
				
				//*blank bariabili
				$xdtab = "";
				$cmp1 = "";
		  	}
			
			// ====================
			// Lista utenti del dipartimento/ruolo
			sql = "select c.id, c.name, c.surname, d.description, v.id_departments "
				+ "from vw_emp_dep_indate v, hr_employee c, hr_departments d "
				+ "where c.id = v.id_employee and d.id = v.id_departments "
				+ "and anno = "+anno+" and settimana = "+settimana+" and v.id_Departments = '"+id_departments+"' "
				+ "group by c.id, c.name, c.surname, d.description, v.id_departments ";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			String arrayDep = "";
			
			int nrdip = 0;
			while(rs.next()) {
				nrdip+=1;
				
				arrayDep+="'"+rs.getString("id")+"',";
				
				/*
				emp.add(new EmployeePlanner(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
						"","","","","","",
						// Lun - Dom
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
				emp.add(new EmployeePlanner(rs.getString("id"),rs.getString("name"),rs.getString("surname"),"","","","","","",
						"","","","","","",
						// Lun - Dom
						Utility.getEmployeePlanningNew(conn, anno, settimana, "0", rs.getString("id"),"",""),
						"",
						"",
						"",
						"",
						"",
						"",
						rs.getString("description"),rs.getString("id_departments"),
						"",Utility.getBadge(conn, rs.getString("id"), dtini, dtfin)));
			}
			rs.close();
			
			obj.put("nrdip", nrdip);
			
			if(arrayDep.length()>0) {
				arrayDep=arrayDep.substring(0,arrayDep.length()-1);
				
				sql =   "select id_employee, plan_day, b.name, b.surname from hr_planification a, hr_employee b  "+
						"where a.id_employee = b.id and plan_year='"+anno+"' and plan_week = '"+settimana+"' "+
						"and id_departments = '"+id_departments+"' and plan_day = '0' "+
						"and id_employee not in("+arrayDep+") "+
						"group by id_employee, plan_day, b.name, b.surname "+
						"order by id_employee ";
				
				System.out.println(sql);
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
					
					//trasf.add(new Tables("DIP",rs.getString("id_employee"),rs.getString("plan_day"),"","","",""));
					
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
					
					d0 = Utility.getEmployeePlanningNew(conn, anno, settimana, "0", rs.getString("id_employee"),"S","");
					// valorizza la lista
					trasf0.add(new Tables(rs.getString("id_employee"),rs.getString("name"),rs.getString("surname"),
							d0,Utility.getBadge(conn, rs.getString("id_employee"), dtini, dtfin),"",""));
					
					
					rott = rs.getString("id_employee");
				}
				rs.close();
				
				obj.put("trasf0", trasf0);
				
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
			
			/*
			// Aggiunge ai dipendenti in trasferta i dipendenti "normali"
			for (int i = 0; i < emp.size(); i++) { 
				
				trasf.add(new Tables("DIP",emp.get(i).getId(),"0","","","",""));
				trasf.add(new Tables("DIP",emp.get(i).getId(),"1","","","",""));
				trasf.add(new Tables("DIP",emp.get(i).getId(),"2","","","",""));
				trasf.add(new Tables("DIP",emp.get(i).getId(),"3","","","",""));
				trasf.add(new Tables("DIP",emp.get(i).getId(),"4","","","",""));
				trasf.add(new Tables("DIP",emp.get(i).getId(),"5","","","",""));
				trasf.add(new Tables("DIP",emp.get(i).getId(),"6","","","",""));
				
			}
			// Dipendenti in trasferta
			obj.put("trasf", trasf);
			*/	
			
			// =====================
			// Settimana
			String giorno = "";
			
			// Get calendar set to current date and time
			Calendar cweek = Calendar.getInstance(Locale.GERMAN);

			// Set the calendar to monday of the current week
			cweek.set(Calendar.YEAR, Integer.parseInt(anno));     
			cweek.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
			cweek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			String newDepart = "";
			String minNum_employee = "";

			//for (int d = 0; d < 6; d++) {
			for (int d = 0; d < 1; d++) {
			    
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
			
			}
			
			obj.put("newDepart", newDepart);
			
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
		
		return new ModelAndView("/hr/planner/planner-list-tab", obj);
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

	
	/*
	public static String getMinnum_employee(Connection conn, String id_departments, String year, String week, String day) throws SQLException {
		
		String value = "";
		
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
				if(hh7.length()==0){hh7="0";}
				
				
				hh8 = ""+rs.getString("hh8");
				hh8 = Utility.noNull(hh8);
				if(hh8.length()==0){hh8="0";}
				
				hh9 = ""+rs.getString("hh9");
				hh9 = Utility.noNull(hh9);
				if(hh9.length()==0){hh9="0";}
				
				hh10 = ""+rs.getString("hh10");
				hh10 = Utility.noNull(hh10);
				if(hh10.length()==0){hh10="0";}
				
				hh11 = ""+rs.getString("hh11");
				hh11 = Utility.noNull(hh11);
				if(hh11.length()==0){hh11="0";}
				
				hh12 = ""+rs.getString("hh12");
				hh12 = Utility.noNull(hh12);
				if(hh12.length()==0){hh12="0";}
				
				hh13 = ""+rs.getString("hh13");
				hh13 = Utility.noNull(hh13);
				if(hh13.length()==0){hh13="0";}
				
				hh14 = ""+rs.getString("hh14");
				hh14 = Utility.noNull(hh14);
				if(hh14.length()==0){hh14="0";}
				
				hh15 = ""+rs.getString("hh15");
				hh15 = Utility.noNull(hh15);
				if(hh15.length()==0){hh15="0";}
				
				hh16 = ""+rs.getString("hh16");
				hh16 = Utility.noNull(hh16);
				if(hh16.length()==0){hh16="0";}
				
				hh17 = ""+rs.getString("hh17");
				hh17 = Utility.noNull(hh17);
				if(hh17.length()==0){hh17="0";}
				
				hh18 = ""+rs.getString("hh18");
				hh18 = Utility.noNull(hh18);
				if(hh18.length()==0){hh18="0";}
				
				hh19 = ""+rs.getString("hh19");
				hh19 = Utility.noNull(hh19);
				if(hh19.length()==0){hh19="0";}
				
				hh20 = ""+rs.getString("hh20");
				hh20 = Utility.noNull(hh20);
				if(hh20.length()==0){hh20="0";}
				
				hh21 = ""+rs.getString("hh21");
				hh21 = Utility.noNull(hh21);
				if(hh21.length()==0){hh21="0";}
				
				hh22 = ""+rs.getString("hh22");
				hh22 = Utility.noNull(hh22);
				if(hh22.length()==0){hh22="0";}
				
				hh23 = ""+rs.getString("hh23");
				hh23 = Utility.noNull(hh23);
				if(hh23.length()==0){hh23="0";}
				
				hh24 = ""+rs.getString("hh24");
				hh24 = Utility.noNull(hh24);
				if(hh24.length()==0){hh24="0";}
				
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
	*/
	
	
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
		
		// Default Day
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
			//System.out.println(sql);
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
			
			
			// Selezione Default (day)
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
			//System.out.println(sql);
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
