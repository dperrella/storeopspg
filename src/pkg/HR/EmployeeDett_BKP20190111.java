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

import pkg.beans.Tables;
import pkg.beans.HR.RelDepEmp;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class EmployeeDett_BKP20190111 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> naz = new ArrayList<Tables>();
		List<Tables> plan = new ArrayList<Tables>();
		List<RelDepEmp> rel = new ArrayList<RelDepEmp>();
		
		naz.clear();
		plan.clear();
		rel.clear();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		
		try {
			
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
			
			String $id_customer="";
		
			String $matricola="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			$id = ""+req.getParameter("id");
			$id=Utility.noNull($id);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$name = req.getParameter("name");
					$surname = req.getParameter("surname");
					$address=req.getParameter("address");
					$locality=req.getParameter("locality");
					$province=req.getParameter("province");
					$zipcode=req.getParameter("zipcode");
					$birthdate=req.getParameter("birthdate");
					if($birthdate.length()==10)
						$birthdate = "'"+$birthdate.substring(6,10)+"-"+$birthdate.substring(3,5)+"-"+$birthdate.substring(0,2)+"'";
					else
						$birthdate = "null";
					$sex=req.getParameter("sex");
					$birthlocality=req.getParameter("birthlocality");
					$birthprovince=req.getParameter("birthprovince");
					$state=""+req.getParameter("state");
					$fcode=req.getParameter("fcode");
					$phone=req.getParameter("phone");
					$email=req.getParameter("email");
					
					$id_customer = (String) session.getAttribute("idcus");
					
					$matricola=req.getParameter("matricola");
					
					sql   = "insert into hr_employee "
							+ "("
							+ "name, surname, address, locality, "
							+ "province, zipcode, birthdate, sex, "
							+ "birthlocality, birthprovince, state, fcode, "
							+ "phone, email, id_customer, matricola "
							+ ")"
							+ "values "
							+ "( "
							+ "'"+$name.replaceAll("'", "''")+"', '"+$surname.replaceAll("'", "''")+"', '"+$address.replaceAll("'", "''")+"', '"+$locality.replaceAll("'", "''")+"', "
							+ "'"+$province.replaceAll("'", "''")+"', '"+$zipcode.replaceAll("'", "''")+"', "+$birthdate+", '"+$sex+"', "
							+ "'"+$birthlocality.replaceAll("'", "''")+"', '"+$birthprovince.replaceAll("'", "''")+"', '"+$state+"', '"+$fcode+"', "
							+ "'"+$phone.replaceAll("'", "''")+"', '"+$email+"', "+$id_customer+" , '"+$matricola.replaceAll("'", "''")+"' "
							+ ") RETURNING id ";
					System.out.println(sql);
					
					rs = st.executeQuery(sql);
					if(rs.next()) {
						$id = rs.getString(1);
						System.out.println("ID: "+rs.getString(1));
					}
					else {
						$id = "-1";
						System.out.println("ID: -1");
					}
					rs.close();
					
					// ======================
					// Se sono stati compilati i dati di associazione del dipendente al ruolo li inserisce
					
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				    
					String  $datestart = req.getParameter("datestart");
					if($datestart.length()==10)
						$datestart = $datestart.substring(6,10)+"-"+$datestart.substring(3,5)+"-"+$datestart.substring(0,2);
						
					String  $dateend = req.getParameter("dateend");
					if($dateend.length()==10)
						$dateend = $dateend.substring(6,10)+"-"+$dateend.substring(3,5)+"-"+$dateend.substring(0,2);
					
					String  $id_roles = req.getParameter("id_roles");
					String  $id_department = req.getParameter("id_department");
					
					// Campi inserimento
					String fld = "";
					String val = "";
					if($datestart.length()>0) {
						fld+= "datestart ,";
						
						Date tick =  df.parse($datestart); 
						$datestart = ""+(tick.getTime()/1000);
					    
						val+= "'"+$datestart+"',";
					}
					if($dateend.length()>0) {
						fld+= "dateend ,";
						
						Date tick =  df.parse($dateend); 
						$dateend = ""+(tick.getTime()/1000);
						
						val+= "'"+$dateend+"',";
					}
					if($id_roles.length()>0) {
						fld+= "id_roles ,";
						val+= "'"+$id_roles+"',";
					}
					if($id_department.length()>0) {
						fld+= "id_departments ,";
						val+= "'"+$id_department+"',";
					}
					
					// Toglie ultimo byte
					if(fld.length()>0)
						fld = fld.substring(0,fld.length()-1);
					if(val.length()>0)
						val = val.substring(0,val.length()-1);
					
					if(fld.length()>0) {
						
						sql   = "insert into rel_dep_employee (id_employee, "+fld+") values ('"+$id+"', "+val+") RETURNING id_reldepemployee  ";
						rs = st.executeQuery(sql);
						
						String $idd="";
						if(rs.next()) {
							$idd = rs.getString(1);
							System.out.println("ID: "+rs.getString(1));
						}
						else {
							$idd = "-1";
							System.out.println("ID: -1");
						}
						rs.close();
						
						// In caso esistesse già una relazione per il dipendente imposta la data fine
						long todayNum = 0;
						Calendar c = Calendar.getInstance(Locale.GERMAN); 
						todayNum = c.getTimeInMillis()/1000;
						
						sql  =  "update rel_dep_employee "+
								"set dateend = "+(Long.parseLong($datestart)-86400)+" "+
								"where id_employee = '"+$id+"' "+
								"and dateend is null and id_reldepemployee <> '"+$idd+"' ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
					}
					// badge
					String $id_badge = ""+req.getParameter("id_badge");
					$id_badge=Utility.noNull($id_badge);
					if($id_badge.length()>0) {
						sql   = "insert into rel_emp_badge (id_employ, id_badge) values ('"+$id+"', '"+$id_badge+"') RETURNING id ";
						rs = st.executeQuery(sql);
						
						String $idb="";
						if(rs.next()) {
							$idb = rs.getString(1);
							System.out.println("ID: "+rs.getString(1));
						}
						else {
							$idb = "-1";
							System.out.println("ID: -1");
						}
						rs.close();
						
						// In caso esistesse già una relazione per il dipendente imposta la data fine
						long todayNum = 0;
						Calendar c = Calendar.getInstance(Locale.GERMAN);
						todayNum = c.getTimeInMillis()/1000;
						
						// relazione dipendente
						sql  =  "update rel_emp_badge "+
								"set date_end = "+todayNum+" "+
								"where id_employ = '"+$id+"' "+
								"and date_end is null and id <> '"+$idb+"' ";
						st.executeUpdate(sql);
						
						// relazione badge
						sql  =  "update rel_emp_badge "+
								"set date_end = "+todayNum+" "+
								"where id_badge = '"+$id_badge+"' "+
								"and date_end is null and id <> '"+$idb+"' ";
						st.executeUpdate(sql);
						
					}
					
					// ===============================
					// controlla l'esistenza di pianificazioni per il dipendente
					// ed eventualmente invia una mail
					if($id_department.length()>0) {
						
						String id_departments_from = "";
						String id_departments_to = $id_department;
						
						sql  =  "select id_departments from rel_dep_employee "+
								"where "+
								"("+
								"id_employee = '"+$id+"' "+ 
								"and dateend is null and id_departments <> '"+$id_department+"' "+
								") "+
								"or "+
								"("+
								"id_employee = '"+$id+"' "+ 
								"and dateend >= "+$datestart+" and id_departments <> '"+$id_department+"' "+
								") ";
						rs=st.executeQuery(sql);
						if(rs.next()) {
							id_departments_from = rs.getString("id_departments");
						}
						rs.close();
						
						System.out.println($datestart);
						
						// Imposta la data a lunedi
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis((Long.parseLong($datestart)*1000));
						System.out.println("DT1:  " + cal.getTime());
						
						int week = cal.get(Calendar.WEEK_OF_YEAR);
						cal.set(Calendar.WEEK_OF_YEAR, week);        
						cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
						
						String dt = ""+(cal.getTimeInMillis()/1000);
						
						System.out.println("DT2:  " + cal.getTime());
						
						// Riapre i settimanali
						Utility.reopenPlanner(conn, id_departments_from, id_departments_to, dt);
						
					}
					// ===============================
					
					
					String  $dtda = ""+req.getParameter("datada");
					$dtda=Utility.noNull($dtda);
					if($dtda.length()==10)
						$dtda = $dtda.substring(6,10)+"-"+$dtda.substring(3,5)+"-"+$dtda.substring(0,2);
						
					String  $dtal = ""+req.getParameter("dataal");
					$dtal=Utility.noNull($dtal);
					if($dtal.length()==10)
						$dtal = "'"+$dtal.substring(6,10)+"-"+$dtal.substring(3,5)+"-"+$dtal.substring(0,2)+"'";
					else 
						$dtal = "null";
					
					String $more = ""+req.getParameter("monteore");
					
					$more=Utility.noNull($more);
					
					if($more.length()>0&&$dtda.length()>0) {
						
						sql   = "insert into hr_contracts "+
								"(id_employee, datada, dataal, monteore, "+
								"saldo_ore, data_saldo) values "+
								"('"+$id.replaceAll("'", "''")+"', '"+$dtda+"', "+
								""+$dtal+", "+$more+",null,null) ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
					}
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$name = req.getParameter("name");
					$surname = req.getParameter("surname");
					$address=req.getParameter("address");
					$locality=req.getParameter("locality");
					$province=req.getParameter("province");
					$zipcode=req.getParameter("zipcode");
					$birthdate=req.getParameter("birthdate");
					if($birthdate.length()==10)
						$birthdate = "'"+$birthdate.substring(6,10)+"-"+$birthdate.substring(3,5)+"-"+$birthdate.substring(0,2)+"'";
					else 
						$birthdate = "null";
					$sex=""+req.getParameter("sex");
					$sex=Utility.noNull($sex);
					if($sex.length()==1)
						$sex = "'"+$sex+"'";
					else
						$sex = "null";
					
					$birthlocality=req.getParameter("birthlocality");
					$birthprovince=req.getParameter("birthprovince");
					$state=req.getParameter("state");
					$fcode=req.getParameter("fcode");
					$phone=req.getParameter("phone");
					$email=req.getParameter("email");
					$matricola=req.getParameter("matricola");
					
					// Elimina relazioni dipendente
					//String id_reldepemp = req.getParameter("id_reldepemp");
					//sql   = "delete from rel_dep_employee where id_reldepemployee = '"+id_reldepemp+"' ";
					//st.executeUpdate(sql);
					
					//String id_relempbadge = req.getParameter("id_relempbadge");
					//sql   = "delete from rel_emp_badge where id = '"+id_relempbadge+"' ";
					//st.executeUpdate(sql);
					
					String id_contract = ""+req.getParameter("id_contract");
					id_contract=Utility.noNull(id_contract);
					if(id_contract.length()==0)
						id_contract = "0";
					sql   = "delete from hr_contracts where id_contract = '"+id_contract+"' ";
					st.executeUpdate(sql);
					
					// Inserisce le nuove relazioni
					
					
					
					sql  =  "update hr_employee "
							+ "set name = '"+$name.replaceAll("'", "''")+"', "
							+ "surname = '"+$surname.replaceAll("'", "''")+"', "
							+ "address = '"+$address.replaceAll("'", "''")+"', "
							+ "locality = '"+$locality.replaceAll("'", "''")+"', "
							+ "province = '"+$province.replaceAll("'", "''")+"', "
							+ "zipcode = '"+$zipcode.replaceAll("'", "''")+"', "
							+ "birthdate = "+$birthdate+", "
							+ "sex = "+$sex+", "
							+ "birthlocality = '"+$birthlocality.replaceAll("'", "''")+"', "
							+ "birthprovince = '"+$birthprovince.replaceAll("'", "''")+"', "
							+ "state = '"+$state+"', "
							+ "fcode = '"+$fcode+"', "
							+ "phone = '"+$phone.replaceAll("'", "''")+"', "
							+ "email= '"+$email+"', "
							+ "matricola= '"+$matricola.replaceAll("'", "''")+"' "
							+ "where id = '"+$id+"' ";
					System.out.println(sql);
					
					st.executeUpdate(sql);
					
					
					
					
					
					
					
					// ======================
					// Se sono stati compilati i dati di associazione del dipendente al ruolo li inserisce
					
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				    
					String  $datestart = req.getParameter("datestart");
					if($datestart.length()==10)
						$datestart = $datestart.substring(6,10)+"-"+$datestart.substring(3,5)+"-"+$datestart.substring(0,2);
						
					String  $dateend = req.getParameter("dateend");
					if($dateend.length()==10)
						$dateend = $dateend.substring(6,10)+"-"+$dateend.substring(3,5)+"-"+$dateend.substring(0,2);
					
					String  $id_roles = req.getParameter("id_roles");
					String  $id_department = req.getParameter("id_department");
					
					// Campi inserimento
					String fld = "";
					String val = "";
					if($datestart.length()>0) {
						fld+= "datestart ,";
						
						Date tick =  df.parse($datestart); 
						$datestart = ""+(tick.getTime()/1000);
					    
						val+= "'"+$datestart+"',";
					}
					if($dateend.length()>0) {
						fld+= "dateend ,";
						
						Date tick =  df.parse($dateend); 
						$dateend = ""+(tick.getTime()/1000);
						
						val+= "'"+$dateend+"',";
					}
					if($id_roles.length()>0) {
						fld+= "id_roles ,";
						val+= "'"+$id_roles+"',";
					}
					if($id_department.length()>0) {
						fld+= "id_departments ,";
						val+= "'"+$id_department+"',";
					}
					
					// Toglie ultimo byte
					if(fld.length()>0)
						fld = fld.substring(0,fld.length()-1);
					if(val.length()>0)
						val = val.substring(0,val.length()-1);
					
					if(fld.length()>0) {
						sql   = "insert into rel_dep_employee (id_employee, "+fld+") values ('"+$id+"', "+val+") RETURNING id_reldepemployee  ";
						rs = st.executeQuery(sql);
						
						String $idd="";
						if(rs.next()) {
							$idd = rs.getString(1);
							System.out.println("ID: "+rs.getString(1));
						}
						else {
							$idd = "-1";
							System.out.println("ID: -1");
						}
						rs.close();
						
						// In caso esistesse già una relazione per il dipendente imposta la data fine
						long todayNum = 0;
						Calendar c = Calendar.getInstance(Locale.GERMAN); 
						todayNum = c.getTimeInMillis()/1000;
						
						sql  =  "update rel_dep_employee "+
								"set dateend = "+(Long.parseLong($datestart)-86400)+" "+
								"where id_employee = '"+$id+"' "+
								"and dateend is null and id_reldepemployee <> '"+$idd+"' ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
					}
					// badge
					String $id_badge = ""+req.getParameter("id_badge");
					$id_badge=Utility.noNull($id_badge);
					if($id_badge.length()>0) {
						
						sql   = "delete from rel_emp_badge where id_employ = '"+$id+"' and id_badge = '"+$id_badge+"' ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
						sql   = "insert into rel_emp_badge (id_employ, id_badge) values ('"+$id+"', '"+$id_badge+"') RETURNING id ";
						rs = st.executeQuery(sql);
						
						String $idb="";
						if(rs.next()) {
							$idb = rs.getString(1);
							System.out.println("ID: "+rs.getString(1));
						}
						else {
							$idb = "-1";
							System.out.println("ID: -1");
						}
						rs.close();
						
						// In caso esistesse già una relazione per il dipendente imposta la data fine
						long todayNum = 0;
						Calendar c = Calendar.getInstance(Locale.GERMAN);
						todayNum = c.getTimeInMillis()/1000;
						
						// relazione dipendente
						sql  =  "update rel_emp_badge "+
								"set date_end = "+todayNum+" "+
								"where id_employ = '"+$id+"' "+
								"and date_end is null and id <> '"+$idb+"' ";
						st.executeUpdate(sql);
						
						// relazione badge
						sql  =  "update rel_emp_badge "+
								"set date_end = "+todayNum+" "+
								"where id_badge = '"+$id_badge+"' "+
								"and date_end is null and id <> '"+$idb+"' ";
						st.executeUpdate(sql);
						
					}
					
					// ===============================
					// controlla l'esistenza di pianificazioni per il dipendente
					// ed eventualmente invia una mail
					if($id_department.length()>0) {
						
						String id_departments_from = "";
						String id_departments_to = $id_department;
						
						sql  =  "select id_departments from rel_dep_employee "+
								"where "+
								"("+
								"id_employee = '"+$id+"' "+ 
								"and dateend is null and id_departments <> '"+$id_department+"' "+
								") "+
								"or "+
								"("+
								"id_employee = '"+$id+"' "+ 
								"and dateend >= "+$datestart+" and id_departments <> '"+$id_department+"' "+
								") ";
						rs=st.executeQuery(sql);
						if(rs.next()) {
							id_departments_from = rs.getString("id_departments");
						}
						rs.close();
						
						System.out.println($datestart);
						
						// Imposta la data a lunedi
						Calendar cal = Calendar.getInstance();
						cal.setTimeInMillis((Long.parseLong($datestart)*1000));
						System.out.println("DT1:  " + cal.getTime());
						
						int week = cal.get(Calendar.WEEK_OF_YEAR);
						cal.set(Calendar.WEEK_OF_YEAR, week);        
						cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
						
						String dt = ""+(cal.getTimeInMillis()/1000);
						
						System.out.println("DT2:  " + cal.getTime());
						
						// Riapre i settimanali
						Utility.reopenPlanner(conn, id_departments_from, id_departments_to, dt);
						
					}
					// ===============================
					
					
					String  $dtda = ""+req.getParameter("datada");
					$dtda=Utility.noNull($dtda);
					if($dtda.length()==10)
						$dtda = $dtda.substring(6,10)+"-"+$dtda.substring(3,5)+"-"+$dtda.substring(0,2);
						
					String  $dtal = ""+req.getParameter("dataal");
					$dtal=Utility.noNull($dtal);
					if($dtal.length()==10)
						$dtal = "'"+$dtal.substring(6,10)+"-"+$dtal.substring(3,5)+"-"+$dtal.substring(0,2)+"'";
					else
						$dtal = "null";
					
					String $more = ""+req.getParameter("monteore");
					
					$more=Utility.noNull($more);
					
					if($more.length()>0&&$dtda.length()>0) {
						
						
						
						sql   = "insert into hr_contracts "+
								"(id_employee, datada, dataal, monteore, "+
								"saldo_ore, data_saldo) values "+
								"('"+$id.replaceAll("'", "''")+"', '"+$dtda+"', "+
								$dtal+" , "+$more+",null,null) ";
						System.out.println(sql);
						st.executeUpdate(sql);
						
					}
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					sql   = "delete from hr_control where id_employee = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "delete from rel_dep_employee where id_employee = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "delete from rel_emp_user where id_employee = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "delete from rel_emp_badge where id_employ = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "delete from hr_planification where id_employee = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "delete from hr_contracts where id_employee = '"+$id+"' ";
					st.executeUpdate(sql);
					
					sql   = "delete from hr_employee where id = '"+$id+"' ";
					st.executeUpdate(sql);
					
					// *blank id
					$id = "";
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			// ===================
			// Nazioni
			sql  =  "select xcdel, xdtab from ztables where xcdtb = 'NAZ' ";
			rs=st.executeQuery(sql);
			while(rs.next()) {		
				naz.add(new Tables("NAZ",rs.getString("xcdel").trim(),rs.getString("xdtab").trim(),"","","",""));
			}
			rs.close();
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from hr_employee where id = '"+$id+"' ";
				System.out.println(sql);
				
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$name=rs.getString("name");
					$surname = rs.getString("surname");
					$address=rs.getString("address");
					$locality=rs.getString("locality");
					$province=rs.getString("province");
					$zipcode=rs.getString("zipcode");
					if(rs.getDate("birthdate")!=null)
						$birthdate=dateIta.format(rs.getDate("birthdate"));
					else
						$birthdate="";
					$sex=rs.getString("sex");
					$birthlocality=rs.getString("birthlocality");
					$birthprovince=rs.getString("birthprovince");
					$state=rs.getString("state");
					$fcode=rs.getString("fcode");
					$phone=rs.getString("phone");
					$email=rs.getString("email");
					$matricola=rs.getString("matricola");
				}
				rs.close();
				
				
				// Relazioni Dipendente / Ruolo / Dipartimento
				String $id_reldepemp = "";
				sql  =  "select b.id_reldepemployee, b.id_employee, b.id_departments, b.id_roles, "+
						"b.datestart, b.dateend, c.name emp_name, c.surname emp_surname, d.description dep_description, "+
						"a.description rol_description, To_timestamp(b.datestart) dtstart, To_timestamp(b.dateend) dtend  "+
						"from hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
						"where a.id=b.id_roles and b.id_employee = c.id "+
						"and b.id_departments = d.id and b.id_employee = '"+$id+"' "+
						"and datestart <= "+date.getTime()/1000+" " + 
						"and (dateend is null or dateend <= "+date.getTime()/1000+") ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$id_reldepemp = rs.getString("id_reldepemployee");
					
					String dt1=""+rs.getString("dtstart");
					dt1=Utility.noNull(dt1);
					String dt2=""+rs.getString("dtend");
					dt2=Utility.noNull(dt2);
					
					if(dt1.length()>0)
						dt1=dateIta.format(rs.getDate("dtstart"));
					
					if(dt2.length()>0)
						dt2=dateIta.format(rs.getDate("dtend"));
					
					obj.put("datestart",  dt1);
					obj.put("dateend",  dt2);
					obj.put("id_roles", rs.getString("id_roles"));
					obj.put("roles_description", rs.getString("rol_description"));
					obj.put("id_department", rs.getString("id_departments"));
					obj.put("department_description", rs.getString("dep_description"));
					
				}
				rs.close();
				
				
				
				// Relazioni Dipendente / Badge
				String $id_relempbadge = "";
				sql  =  "select a.id, a.id_employ, b.name, b.surname, a.id_badge, a.date_end, To_timestamp(a.date_end) dtend, number "+
						"from rel_emp_badge a, hr_employee b, hr_badge c "+
						"where a.id_employ = '"+$id+"' and (a.date_end is null or date_end <= "+date.getTime()/1000+") "+
						"and b.id = a.id_employ "+
						"and c.id = a.id_badge ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					$id_relempbadge = rs.getString("id");
					
					obj.put("id_badge", rs.getString("id_badge"));
					obj.put("nr_badge", rs.getString("number"));
					
				}
				rs.close();
				
				obj.put("id_reldepemp", $id_reldepemp);
				obj.put("id_relempbadge", $id_relempbadge);
				
				
				
				// Contratto
				sql  =  "select * from hr_contracts a where a.id_employee = '"+$id+"' ";
				System.out.println("----");
				System.out.println(sql);
				System.out.println("----");
				rs=st.executeQuery(sql);
				if(rs.next()) {
					
					String dt1=""+rs.getString("datada");
					dt1=Utility.noNull(dt1);
					String dt2=""+rs.getString("dataal");
					dt2=Utility.noNull(dt2);
					
					if(dt1.length()>0)
						dt1=dateIta.format(rs.getDate("datada"));
					
					if(dt2.length()>0)
						dt2=dateIta.format(rs.getDate("dataal"));
					
					
					obj.put("datada", dt1);
					obj.put("dataal", dt2);
					obj.put("monteore", rs.getString("monteore"));
					obj.put("id_contract", rs.getString("id_contract"));
					
				}
				rs.close();
				
				
				
				// Ultime 50 pianificazioni
				sql   = "select count(distinct(plan_year || plan_week || id_departments)) nrplan, "+
						"plan_year, plan_week, id_departments, description "+
						"from hr_planification a, hr_departments b "+
						"where a.id_departments = b.id and plan_year = '"+oggi.substring(6,10)+"' "+
						"and a.id_employee = '"+$id+"' "+
						"group by plan_year, plan_week, id_departments, description "+
						"order by cast(plan_week as numeric) desc "+
						"limit 50 ";
				System.out.println(sql);
				rs=st.executeQuery(sql);
				while(rs.next()) {
					plan.add(new Tables("PLN","","",rs.getString("plan_year"),rs.getString("plan_week"),
							rs.getString("id_departments"),rs.getString("description")));
				}
				rs.close();
				
				
				
				
				
				
				// Relazioni Dipendente
				sql  =  "select b.id_reldepemployee, b.id_employee, b.id_departments, b.id_roles, "+
						"to_timestamp(b.datestart)::date datestart, to_timestamp(b.dateend)::date dateend, datestart dt1, dateend dt2, c.name emp_name, c.surname emp_surname, d.description dep_description, "+
						"a.description rol_description "+
						"from hr_roles a, rel_dep_employee b, hr_employee c, hr_departments d "+
						"where a.id=b.id_roles and b.id_employee = c.id "+
						"and b.id_departments = d.id and b.id_employee = '"+$id+"' ";
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
				
				
				$cmp1="";
				$cmp2="";
				$cmp3="";
				$cmp4="";
				
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
				
				
			}
			
			
			
			obj.put("id", $id);
			obj.put("name", $name);
			obj.put("surname", $surname);
			obj.put("address", $address);
			obj.put("locality", $locality);
			obj.put("province", $province);
			obj.put("zipcode", $zipcode);
			obj.put("birthdate", $birthdate);
			obj.put("sex", $sex);
			obj.put("birthlocality", $birthlocality);
			obj.put("birthprovince", $birthprovince);
			obj.put("state", $state);
			obj.put("fcode", $fcode);
			obj.put("phone", $phone);
			obj.put("email", $email);
			obj.put("matricola", $matricola);
			
			
			// Pianificazioni Dipendente
			obj.put("plan", plan);
			
			// Relazioni Dipendente
			obj.put("rel", rel);
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Dipendente");
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("naz", naz);
		
		
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/employee/emp-dett", obj);
	}
	
}
