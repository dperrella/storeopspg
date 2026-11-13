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
import pkg.beans.HR.Employee;
import pkg.beans.HR.Control;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ControlList_BKP3 extends AbstractController {
	
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		SimpleDateFormat hour = new SimpleDateFormat("kk:mm:ss");
		hour.setTimeZone(TimeZone.getTimeZone("CET"));
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Employee> emp = new ArrayList<Employee>();
		List<Control> cnt = new ArrayList<Control>();
		List<Tables> wk = new ArrayList<Tables>();
		List<Departments> dep = new ArrayList<Departments>();
		List<Tables> sett = new ArrayList<Tables>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		// Pulisce le lista
		emp.clear();
		cnt.clear();
		wk.clear();
		dep.clear();
		sett.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st2 = null;
		ResultSet rs2 = null;
		
		String sql = "";
		String cond = "";
		
		String dsfun = "";
		
		String msg = "";
		int count = 0;
		
		// -----------------------
		// Anno
		String year = req.getParameter("year");
		// Settimane / Mesi (In base a tp)
		String week = req.getParameter("week");
		// Dipartmento
		String id_departments = req.getParameter("id_departments");
		// Tipo ('' / 'D' / 'M')
		String tp = req.getParameter("tp");
		
		System.out.println(tp);
		
		try {
			
			// Imposta data inizio e data fine
			// dal periodo
			// Formato data
			String dtini = "";
			String dtfin = "";
			
			String dtiniTic = "";
			String dtfinTic = "";
			
			SimpleDateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
			
			// Controllo Mensile
			if(tp.equals("M")) {
				Calendar cal = Calendar.getInstance(Locale.GERMAN);  
				cal.set(Integer.parseInt(year), (Integer.parseInt(week)-1), 1);
				// dtini
				dtini = dateIso.format(cal.getTime());
				dtiniTic = ""+(cal.getTimeInMillis()/1000);
				// dtfin
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				dtfin = dateIso.format(cal.getTime());
				dtfinTic = ""+(cal.getTimeInMillis()/1000);
			}
			// Controllo / Controllo Dettagliato
			else {
				Calendar cal = Calendar.getInstance(Locale.GERMAN);  
				cal.set(Integer.parseInt(year), 0, 1);
				cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));        
				// dtini
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				dtini = dateIso.format(cal.getTime());
				dtiniTic = ""+(cal.getTimeInMillis()/1000);
				// dtfin
				cal.add(Calendar.DATE, 6);  
				dtfin = dateIso.format(cal.getTime());
				dtfinTic = ""+(cal.getTimeInMillis()/1000);
			}
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAdmin();
			st=conn.createStatement();
			st2=conn.createStatement();
			
			String currentWeek = "";
			
			// =======================
			// Dipartimenti 
			sql  =  "select * from hr_departments "+cond;
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
								$state,$phone,$email,$department_code,"","","",0,0,0,false,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			
			if(tp.equals("M")) {
				// =======================
				// Mesi
				String mm = "";
				String desMm = "";
				for(int i=1;i<=12;i++) {
					
					mm = "00"+i;
					mm = mm.substring(mm.length()-2, mm.length());
					
					if(mm.equals("01")) {
						desMm = propLang.get("gen");
					}
					else if(mm.equals("02")) {
						desMm = propLang.get("feb");
					}
					else if(mm.equals("03")) {
						desMm = propLang.get("mar2");
					}
					else if(mm.equals("04")) {
						desMm = propLang.get("apr");
					}
					else if(mm.equals("05")) {
						desMm = propLang.get("mag");
					}
					else if(mm.equals("06")) {
						desMm = propLang.get("giu");
					}
					else if(mm.equals("07")) {
						desMm = propLang.get("lug");
					}
					else if(mm.equals("08")) {
						desMm = propLang.get("ago");
					}
					else if(mm.equals("09")) {
						desMm = propLang.get("set");
					}
					else if(mm.equals("10")) {
						desMm = propLang.get("ott");
					}
					else if(mm.equals("11")) {
						desMm = propLang.get("nov");
					}
					else if(mm.equals("12")) {
						desMm = propLang.get("dic");
					}
					
					// valorizza la lista
					wk.add(new Tables("MON",mm, desMm,"","","",""));
					
				}
			}
			else {
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
				
				System.out.println(currentWeek);
				
				// Per settimanale
				Calendar cal2 = Calendar.getInstance(Locale.GERMAN);  
				// Imposta l'anno corrente
				cal2.set(Integer.parseInt(year), 0, 1);
				cal2.set(Calendar.HOUR_OF_DAY, 0);
				cal2.set(Calendar.MINUTE, 0);
				cal2.set(Calendar.SECOND, 0);
				cal2.set(Calendar.MILLISECOND, 0);
				
				String week2 = "";
				
				// campi tables
				String $xcdtb = "SET";
				String $xcdel = "";
				String $xdtab = "";
				String status = "";
				for (int i=1; i<=weeks; i++) {
					  
					week2 = "00"+i;
					week2 = week2.substring(week2.length()-2,week2.length());
					
					// valorizza xcdel col numero della settimana
					$xcdel = week2;
					
					cal2.set(Integer.parseInt(year), 0, 1);
					cal2.set(Calendar.WEEK_OF_YEAR, i);        
					cal2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					
					// valorizza il primo giorno della settimana
					$xdtab=sdf.format(cal2.getTime())+" - ";
					
					cal2.add(Calendar.DATE, 6);  
					//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					
					// valorizza l'ultimo giorno della settimana
					$xdtab+=sdf.format(cal2.getTime());
					
					// Settimana attuale
					if($xcdel.equals(currentWeek)) {
						$cmp1 = "selected ";
					}
					
					wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,"","",""));
					
					//*blank bariabili
					$xdtab = "";
					$cmp1 = "";
			  	}
				
				// ====================
			}
			
			
			
			
			
			
			
			
			
			
			// ====================
			// Lista utenti del dipartimento/ruolo
			/*
			sql  =  "select c.id, c.name, c.surname, d.description, id_departments from "+
					"hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
					"where a.id=b.id_roles "+
					"and b.id_employee = c.id "+ 
					"and b.id_departments = d.id "+
					"and d.id = '"+id_departments+"' and b.dateend is null ";
			*/
			sql  =  "select c.id, c.name, c.surname, d.description, id_departments from "+
					"hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
					"where "+
					"("+
					"a.id=b.id_roles "+
					"and b.id_employee = c.id "+ 
					"and b.id_departments = d.id "+
					"and d.id = '"+id_departments+"' and b.dateend is null "+
					"and a.plan_view = 'Y' "+
					") "+
					"or "+
					"("+
					"a.id=b.id_roles "+
					"and b.id_employee = c.id "+ 
					"and b.id_departments = d.id "+
					"and d.id = '"+id_departments+"' and b.dateend between "+dtini+" and "+dtfin+" "+
					"and a.plan_view = 'Y' "+
					") "+
					"group by c.id, c.name, c.surname, d.description, id_departments ";
					
			System.out.println(sql);
			
			rs=st.executeQuery(sql);
			
			// Tabella dettagli
			String table = "";
			String css = "";
			String cssBottom = "";
			String font = "";
			String bg1 = "";
			String bg2 = "";
			String bg3 = "";
			String bg4 = "";
			String bg5 = "";
			long wtotale = 0;
			long ptotale = 0;
			String sWtotale = "";
			String sPtotale = "";
			
			
			long wtotaleSet = 0;
			long ptotaleSet= 0;
			String sWtotaleSet = "";
			String sPtotaleSet = "";
			
			
			
			
			
			String $id_employee="";
			String $des_employee="";
			String $id_departments="";
			String $des_departments="";
			String $day_ref="";
			String $type="";
			String $des_type="";
			String $ingresso="";
			String $inizio_pausa="";
			String $fine_pausa="";
			String $uscita="";
			String $totale="";
			
			String $totaliNew = "";
			String $th="";
			String $td="<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>";
			
			while(rs.next()) {
				
				if(tp.trim().length()==0) {
					
					dsfun = propLang.get("cnt_controllo");
					
					sql   = "select a.*, substr(xdtab , 4 , 100) xdtab from vw_control a, ztables "+
							"where type = xcdel and xcdtb = 'TPT' "+ 
							"and id_employee = '"+rs.getString("id")+"' "+
							"and id_departments = '"+rs.getString("id_departments")+"' "+
							"and day_ref between '"+dtini+"' and '"+dtfin+"' "+
							"order by day_ref, cast(substr(xdtab , 1 , 3) as numeric) ";	
				
				}
				else if(tp.equals("D")) {
					
					dsfun = propLang.get("cnt_contrDett");
					
					sql   = "select a.*, substr(xdtab , 3 , 100) xdtab from control_anomaly a, ztables "+
							"where type = xcdel and xcdtb = 'TPT' "+ 
							"and id_employee = '"+rs.getString("id")+"' "+
							"and id_departments = '"+rs.getString("id_departments")+"' "+
							"and day_ref between '"+dtini+"' and '"+dtfin+"' "+
							"order by day_ref, cast(substr(xdtab , 1 , 3) as numeric) ";	
					
				}
				else if(tp.equals("M")) {
					
					dsfun = propLang.get("cnt_contrMese");
					
					sql   = "select a.*, substr(xdtab , 3 , 100) xdtab from vw_control a, ztables "+
							"where type = xcdel and xcdtb = 'TPT' "+ 
							"and id_employee = '"+rs.getString("id")+"' "+
							"and id_departments = '"+rs.getString("id_departments")+"' "+
							"and day_ref between '"+dtini+"' and '"+dtfin+"' "+
							"order by day_ref, cast(substr(xdtab , 1 , 3) as numeric) ";
					
				}
				
				System.out.println(sql);
				rs2 = st2.executeQuery(sql);
				
				String rott = "";
				
				String sDay_ref = "";
				String day_ref_iso = "";
				while(rs2.next()) {
					
					sDay_ref = ""+rs2.getDate("day_ref");
					sDay_ref = Utility.noNull(sDay_ref);
					
					if(sDay_ref.length()>0) {
						if(!rott.equals(rs2.getString("day_ref"))) {
							$day_ref = dateIta.format(rs2.getDate("day_ref"));
							day_ref_iso = dateIso.format(rs2.getDate("day_ref"));
						}
						else {
							$day_ref = "";
						}
					}
					
					$id_employee=rs2.getString("id_employee");
					$id_departments=rs2.getString("id_departments");
					//$day_ref=rs2.getString("day_ref");
					$type=rs2.getString("type");
					$des_type=rs2.getString("xdtab");
					$ingresso=""+rs2.getString("ingresso");
					$ingresso=Utility.noNull($ingresso);
					if($ingresso.length()>0) {
						if(!$type.equals("DIF")) {
							$ingresso = hour.format(rs2.getTime("ingresso"));
						}
					}
					else {
						$ingresso = "-";
					}
						
					$inizio_pausa=""+rs2.getString("inizio_pausa");
					$inizio_pausa=Utility.noNull($inizio_pausa);
					if($inizio_pausa.length()>0) {
						if(!$type.equals("DIF")) {
							$inizio_pausa = hour.format(rs2.getTime("inizio_pausa"));
						}
					}
					else {
						$inizio_pausa = "-";
					}
					
					$fine_pausa=""+rs2.getString("fine_pausa");
					$fine_pausa=Utility.noNull($fine_pausa);
					if($fine_pausa.length()>0) {
						if(!$type.equals("DIF")) {
							$fine_pausa = hour.format(rs2.getTime("fine_pausa"));
						}
					}
					else {
						$fine_pausa = "-";
					}
						
					$uscita=""+rs2.getString("Uscita");
					$uscita=Utility.noNull($uscita);
					if($uscita.length()>0) {
						//if($type.equals("WRK")||$type.equals("PLN")) {
						if(!$type.equals("DIF")) {
							$uscita = hour.format(rs2.getTime("uscita"));
						}
					}
					else {
						$uscita = "-";
					}
					$totale=""+rs2.getString("totale");
					$totale=Utility.noNull($totale);
					
					if($type.equals("PLN")) {
						css = "bg-light";
					}
					if($type.equals("WRK")) {
						css = "bg-light";
					}
					if($type.equals("DIF")) {
						css = "bg-light";
						cssBottom = "b-2x b-b b-dark";
						font = "style=\"font-weight: bold\" ";
						
						if($ingresso.length()>1) {
							if($ingresso.substring(0,1).equals("-")) {
								bg1 = "label bg-success";
							}
							else {
								bg1 = "label bg-danger ";
							}
						}
						
						if($inizio_pausa.length()>1) {
							if($inizio_pausa.substring(0,1).equals("-")) {
								bg2 = "label bg-danger";
							}
							else {
								bg2 = "label bg-success ";
							}
						}
						
						if($fine_pausa.length()>1) {
							if($fine_pausa.substring(0,1).equals("-")) {
								bg3 = "label bg-success";
							}
							else {
								bg3 = "label bg-danger ";
							}
						}
						
						if($uscita.length()>1) {
							if($uscita.substring(0,1).equals("-")) {
								bg4 = "label bg-danger";
							}
							else {
								bg4 = "label bg-success ";
							}
						}
						
						if($totale.length()>1) {
							if($totale.substring(0,1).equals("-")) {
								bg5 = "label bg-danger";
							}
							else {
								if($totale.equals("00:00:00")) {
									
								}
								else {
									bg5 = "label bg-success ";
								}
							}
						}
						
					}
					
					// totale WRK
					if($type.equals("WRK")) {
						if($totale.length()>1) {
							
							long lwtotale = 0;
							try {
								lwtotale= hour.parse($totale).getTime();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
							
							Calendar wc = Calendar.getInstance(Locale.GERMAN);  
							wc.setTimeInMillis(lwtotale);
							wc.add(Calendar.HOUR, 1);
							
							wtotale+= wc.getTimeInMillis();
							wtotaleSet+= wc.getTimeInMillis();
							
						}
					}
					
					// totale PLN
					if($type.equals("PLN")) {
						if($totale.length()>1) {
							long lptotale = 0;
							try {
								lptotale = hour.parse($totale).getTime();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
							
							Calendar wc = Calendar.getInstance(Locale.GERMAN);  
							wc.setTimeInMillis(lptotale);
							wc.add(Calendar.HOUR, 1);
							
							ptotale+= wc.getTimeInMillis();
							ptotaleSet+= wc.getTimeInMillis();
						}
					}
					
					// table
					table+="<tr><td class=\""+$type+" b-b "+cssBottom+"\" "+font+"><b>"+$day_ref+"</b></td>"+
						   "<td class=\""+$type+" b-b "+cssBottom+"\" "+font+">"+$des_type+"</td>"+
						   "<td class=\""+$type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg1+"\">"+$ingresso+"</span></td>"+
						   "<td class=\""+$type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg2+"\">"+$inizio_pausa+"</span></td>"+
						   "<td class=\""+$type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg3+"\">"+$fine_pausa+"</span></td>"+
						   "<td class=\""+$type+" b-b "+cssBottom+"\" "+font+"><span class=\""+bg4+"\">"+$uscita+"</span></td>"+
						   "<td class=\""+$type+" b-b "+cssBottom+"\" style=\"text-align:right;\"><span class=\""+bg5+"\"><b>"+$totale+"</b></span></td></tr>";
					
					cnt.add(new Control($id_employee,$des_employee,$id_departments,$des_departments,$day_ref,
							$type,$des_type,$ingresso,$inizio_pausa,$fine_pausa,$uscita,$totale,
							"","","",""));
					
					cssBottom = "";
					font = "";
					bg1 = "";
					bg2 = "";
					bg3 = "";
					bg4 = "";
					bg5 = "";
					
					rott = rs2.getString("day_ref");
					
				}
				rs2.close();
				
				// Worked
				int seconds = (int) (wtotale / 1000) % 60 ;
				int minutes = (int) ((wtotale / (1000*60)) % 60);
				int hours   = (int) ((wtotale / (1000*60*60)) % 24);
				int day   = (int) ((wtotale / (1000*60*60*24)));
				
				System.out.println("day:"+day);
				if(day>0)
					hours = hours+(day*24);
				
				String sec = "00"+seconds;
				String min = "00"+minutes;
				String hh = ""+hours;
				
				//hh = hh.substring(hh.length()-2,hh.length());
				min = min.substring(min.length()-2,min.length());
				sec = sec.substring(sec.length()-2,sec.length());
				
				sWtotale = hh+":"+min+":"+sec;
				
				// Planned
				seconds = (int) (ptotale / 1000) % 60 ;
				minutes = (int) ((ptotale / (1000*60)) % 60);
				hours   = (int) ((ptotale / (1000*60*60)) % 24);
				day   = (int) ((ptotale / (1000*60*60*24)));
				
				System.out.println("day:"+day);
				if(day>0)
					hours = hours+(day*24);
				
				sec = "00"+seconds;
				min = "00"+minutes;
				hh = ""+hours;
				
				//hh = hh.substring(hh.length()-2,hh.length());
				min = min.substring(min.length()-2,min.length());
				sec = sec.substring(sec.length()-2,sec.length());
				
				sPtotale = hh+":"+min+":"+sec;
				
				
				if(day_ref_iso.length()>0) {
					$totaliNew=getTotali(conn, rs.getString("id_departments"), rs.getString("id"), dtini, dtfin);
					
					String temp[] = $totaliNew.split("##@@");
					$totaliNew = temp[0];
					$th = temp[1];
					$td = temp[2];
					
				}
				else {
					$totaliNew="<div class=\"row wrapper-xs\"><div class=\"col-sm-12\"><b>-</b></div></div>";
				}
				emp.add(new Employee(rs.getString("id"),rs.getString("name"),rs.getString("surname"),sWtotale,""+sPtotale,$totaliNew,"",$td,$th,
						"","","","","","",rs.getString("description"),rs.getString("id_departments"),table,
						Utility.getBadge(conn, rs.getString("id"), dtiniTic, dtfinTic),"","","",""));
				
				table = "";
				wtotale = 0;
				ptotale = 0;
				
				$totaliNew = "";
				
			}
			rs.close();
			
			// Totale
			// Worked
			int seconds = (int) (wtotaleSet / 1000) % 60 ;
			int minutes = (int) ((wtotaleSet / (1000*60)) % 60);
			int hours   = (int) ((wtotaleSet / (1000*60*60)) % 24);
			int day   = (int) ((wtotaleSet / (1000*60*60*24)));
			
			System.out.println("day:"+day);
			if(day>0)
				hours = hours+(day*24);
			
			String sec = "00"+seconds;
			String min = "00"+minutes;
			String hh = "";
			if(hours<10) {
				hh = "00"+hours;
				hh = hh.substring(hh.length()-2,hh.length());
			}
			else {
				hh = ""+hours;
			}
			min = min.substring(min.length()-2,min.length());
			sec = sec.substring(sec.length()-2,sec.length());
			
			sWtotaleSet = hh+":"+min+":"+sec;
			
			// Planned
			seconds = (int) (ptotaleSet / 1000) % 60 ;
			minutes = (int) ((ptotaleSet / (1000*60)) % 60);
			hours   = (int) ((ptotaleSet / (1000*60*60)) % 24);
			day   = (int) ((ptotaleSet / (1000*60*60*24)));
			
			System.out.println("day:"+day);
			if(day>0)
				hours = hours+(day*24);
			
			sec = "00"+seconds;
			min = "00"+minutes;
			hh = "";
			if(hours<10) {
				hh = "00"+hours;
				hh = hh.substring(hh.length()-2,hh.length());
			}
			else {
				hh = ""+hours;
			}
			min = min.substring(min.length()-2,min.length());
			sec = sec.substring(sec.length()-2,sec.length());
			
			sPtotaleSet = hh+":"+min+":"+sec;
			
			
			
			obj.put("totPlanned", sPtotaleSet);
			obj.put("totWorked", sWtotaleSet);
			
			String totaliDep = "";
			
			totaliDep = getTotaliDepartment(conn, id_departments, dtini, dtfin);
			obj.put("totaliDep", totaliDep);
			
			
			// Nuovo totale per settimana (solo controllo mensile)
			if(tp.equals("M")) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Calendar calNew = Calendar.getInstance(Locale.GERMAN); 
				calNew.set(Calendar.YEAR, Integer.parseInt(year));
				calNew.set(Calendar.MONTH, Integer.parseInt(week)-1);
				calNew.set(Calendar.DAY_OF_MONTH, 1);

			    int ndays = calNew.getActualMaximum(Calendar.DAY_OF_MONTH);
			    int iWeek = 0;
			    int rott = 0;
			    String sDtini = "";
			    String sDtfin = "";
			    for (int i = 0; i < ndays; i++)
			    {
			    	iWeek = calNew.get(Calendar.WEEK_OF_YEAR);
			    	if(rott!=iWeek) {
			    		
			    		sDtini = sdf.format(calNew.getTime());
			    		
			    		Calendar cal2 = Calendar.getInstance(Locale.GERMAN);  
						cal2.setTime(calNew.getTime());
						cal2.set(Calendar.WEEK_OF_YEAR, iWeek);        
						// dtfin
						cal2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
						
						if(calNew.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
							sDtfin = sdf.format(cal2.getTime());
						}
						else {
							cal2.set(Calendar.DAY_OF_MONTH, 1);  
					        cal2.add(Calendar.DATE, -1);  
							cal2.set(Calendar.DATE, cal2.getActualMaximum(Calendar.DATE));
							sDtfin =  sdf.format(cal2.getTime());
						}
						
			    		System.out.println(iWeek+" "+sDtini+" "+sDtfin );
			    		
			    		sett.add(new Tables(""+iWeek,sDtini,sDtfin,getTotaliDepartment2(conn, id_departments, sDtini, sDtfin),"","",""));
			    				
			    	}
			    	calNew.add(Calendar.DATE, 1);
			        
			        rott = iWeek;
			    }
				
			}
			
			
			
			
			st2.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", dsfun);
		obj.put("week", week);
		obj.put("year", year);
		obj.put("id_departments", id_departments);
		obj.put("tp", tp);
		
		obj.put("emp", emp);
		obj.put("cnt", cnt);
		obj.put("weeks", wk);
		obj.put("dep", dep);
		obj.put("sett", sett);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/app/control/control-list", obj);
	}
	
	public static String getTotali(Connection conn, String id_departments, String id_employee, String dtini, String dtfin) throws SQLException {
		
		String totali = "";
		String th = "";
		String td = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			st = conn.createStatement();
			sql   = "select a.xcdel, a.xdtab, Sum(totale::interval)::character varying totale "+
					"from ztables a "+
					"left join vw_control_totali b on id_employee = '"+id_employee+"' "+
					"and id_departments = '"+id_departments+"' "+
					"and a.xcdel = b.xcdel and day_ref between '"+dtini+"' and '"+dtfin+"' "+
					"where xcdtb = 'TPT' "+
					"group by a.xcdel, a.xdtab "+
					"order by a.xdtab ";	
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			totali = "<div class=\"row wrapper-xs\">";
			
			String val = "";
			while(rs.next()) {
				val = ""+rs.getString("totale");
				val = Utility.noNull(val);
				
				if(val.length()==0)
					val = "-";
				
				th+="<th class=\"b-r\">"+rs.getString("xdtab").substring(3, rs.getString("xdtab").length())+"</th>";
				td+="<td><b>"+val+"</b></td>";
				
				totali+="<div class=\"col-sm-2\"><b>"+rs.getString("xdtab").substring(3, rs.getString("xdtab").length())+"</b></div><div class=\"col-sm-2\">"+val+"</div>";
				
			}
			rs.close();
			
			totali+="</div>";
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return totali+"##@@"+th+"##@@"+td;
		
	}
	
	
	public static String getTotaliDepartment(Connection conn, String id_departments, String dtini, String dtfin) throws SQLException {
		
		String totali = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			st = conn.createStatement();
			sql   = "select a.xcdel, a.xdtab, Sum(totale::interval)::character varying totale "+
					"from ztables a "+
					"left join vw_control_totali b on id_departments = '"+id_departments+"' "+
					"and a.xcdel = b.xcdel and day_ref between '"+dtini+"' and '"+dtfin+"' "+
					"where xcdtb = 'TPT' "+
					"group by a.xcdel, a.xdtab "+
					"order by a.xdtab ";	
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String val = "";
			while(rs.next()) {
				val = ""+rs.getString("totale");
				val = Utility.noNull(val);
				
				if(val.length()==0)
					val = "-";
				
				totali+="<td><b>"+val+"</b></td>";
				
			}
			rs.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return totali;
		
	}
	
	
	
	public static String getTotaliDepartment2(Connection conn, String id_departments, String dtini, String dtfin) throws SQLException {
		
		String totali = "";
		
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			st = conn.createStatement();
			sql   = "select a.xcdel, a.xdtab, Sum(totale::interval)::character varying totale "+
					"from ztables a "+
					"left join vw_control_totali b on id_departments = '"+id_departments+"' "+
					"and a.xcdel = b.xcdel and day_ref between '"+dtini+"' and '"+dtfin+"' "+
					"where xcdtb = 'TPT' and a.xcdel in('PLN', 'WRK', 'DIF') "+
					"group by a.xcdel, a.xdtab "+
					"order by a.xdtab ";	
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String val = "";
			while(rs.next()) {
				val = ""+rs.getString("totale");
				val = Utility.noNull(val);
				
				if(val.length()==0)
					val = "-";
				
				totali+="<td><b>"+val+"</b></td>";
				
			}
			rs.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		
		return totali;
		
	}
	
	
	
}
