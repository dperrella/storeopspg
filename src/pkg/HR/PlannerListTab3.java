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
import pkg.beans.HR.Plan;
import pkg.beans.HR.Plan2;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PlannerListTab3 extends AbstractController {
	
	private static boolean isInt(double d){
		return d % 1 == 0;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Utente sessione
		String idusr = (String)session.getAttribute("username");
		
		// Dipendenti trasferiti nel dipartimento
		List<Tables> trasf0 = new ArrayList<Tables>();
		
		// Giorni settimana
		List<Tables> giorni = new ArrayList<Tables>();
		
		// Pianificazione
		List<Plan2> plan2 = new ArrayList<Plan2>();
		
		// Data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("kk:mm");
		Date date = new Date();
		String oggi = dateNum.format(date);
		
		// Formato data per insert
		DateFormat df = new SimpleDateFormat("yyyyMMddkkmm");
		DateFormat dfIta = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		
		
		// Pulisce le lista
		trasf0.clear();
		giorni.clear();
		plan2.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
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
		
		// Get calendar set to current date and time
		Calendar cw = Calendar.getInstance(Locale.GERMAN);

		// Set the calendar to monday of the current week
		cw.set(Calendar.YEAR, Integer.parseInt(anno));     
		cw.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
		cw.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		String richiestaApprovazione = ""+req.getParameter("richiestaApprovazione");
		richiestaApprovazione = Utility.noNull(richiestaApprovazione);
		
		obj.put("richiestaApprovazione", richiestaApprovazione);
		
		String msg = "";
		String currentWeek = "";
		
		try {
			
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// *********
			// HEATMAP
			String xAxis = "";
			String yAxis = "";
			int max = 0;
			int height = 0;
			
			//Calendar c_heat = Calendar.getInstance(new Locale("en","US"));
			Calendar c_heat = Calendar.getInstance(Locale.GERMAN);
			
			// Set the calendar to monday of the current week
			c_heat.set(Calendar.YEAR, Integer.parseInt(anno));     
			c_heat.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
			c_heat.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			String dtda = dateIso.format(c_heat.getTime());
			for (int d = 0; d < 6; d++) {
				c_heat.add(Calendar.DATE, 1);
			}
			String dtal = dateIso.format(c_heat.getTime());
			String dates=dtda+" / "+dtal;
			
	 		height = 500;
	 		
	 		sql   = "SELECT ora::time ora "
	 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
	 				+ "'"+dtda+" 23:30', '0.5 hours') ora ";
	 		rs=st.executeQuery(sql);
	 		String ora = "";
	 		while(rs.next()) {
	 			ora = timeFormat.format(rs.getTime("ora")).replaceAll(":", "");
	 			if(Integer.parseInt(ora)>=600&&Integer.parseInt(ora)<=2330)
	 				xAxis+="'"+timeFormat.format(rs.getTime("ora"))+"',";
	 		}
	 		rs.close();
	 		if(xAxis.length()>0)
	 			xAxis = xAxis.substring(0,xAxis.length()-1);
	 		
	 		//yAxis = "'Sunday','Monday', 'Tuesday','Wednesday', 'Thursday', 'Friday', 'Saturday'";
	 		yAxis = "'"+propLang.get("lun")+"','"+propLang.get("mar")+"','"+propLang.get("mer")+"','"+propLang.get("gio")+"','"+propLang.get("ven")+"','"+propLang.get("sab")+"','"+propLang.get("dom")+"'";
	 		
	 		sql   = "SELECT max( "
	 				+ "(select count(id_employee) from hr_planification "
	 				+ "where plan_year='"+anno+"' and plan_week = '"+settimana+"' "
	 				+ "and id_departments = '"+id_departments+"' "
	 				+ "and plan_day = (extract(isodow from ora) -1)::text "
	 				+ "and ora::time between "
	 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
	 				+ ")) valore "
	 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
	 				+ "'"+dtal+" 23:30', '0.5 hours') ora   ";
	 		System.out.println(sql);
	 		rs=st.executeQuery(sql);
	 		if(rs.next()) {
	 			max = rs.getInt("valore");
	 		}
	 		rs.close();
	 		
	 		sql   = "SELECT ora::date giorno, ora::time ora, "
	 				+ "case when (extract(isodow from ora) -1) = 0 then 7 else (extract(isodow from ora) -1) end dow1 ,"
	 				+ "extract(isodow from ora)-1 dow, "
	 				+ "(select count(id_employee) from hr_planification "
	 				+ "where plan_year='"+anno+"' and plan_week = '"+settimana+"' "
	 				+ "and id_departments = '"+id_departments+"' "
	 				+ "and plan_day = (extract(isodow from ora) -1) ::text "
	 				+ "and ora::time between "
	 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
	 				+ ") valore "
	 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
	 				+ "'"+dtal+" 23:30', '0.5 hours') ora "
	 				+ "order by giorno, ora ";
			System.out.println("---");
			System.out.println(sql);
			String heatmap = "";
			rs=st.executeQuery(sql);
			String val = "";
			
			String data1 = "";
			
			
			
			while(rs.next()) {
				val=""+rs.getString("valore");
				val=Utility.noNull(val);
				
				if(val.length()==0)
					val = "'-'";
				
				switch (rs.getInt("dow")) {
	         	case 0: 
	         		data1 = propLang.get("lun");
	         		break;
	         	case 1: 
	         		data1 = propLang.get("mar");
	         		break;
	         	case 2: 
	         		data1 = propLang.get("mer");
	         		break;
	         	case 3: 
	         		data1 = propLang.get("gio");
	         		break;
	         	case 4: 
	         		data1 = propLang.get("ven");
	         		break;
	         	case 5: 
	         		data1 = propLang.get("sab");
	         		break;
	         	case 6: 
	         		data1 = propLang.get("dom");
	         		break;
				}
				
				ora = timeFormat.format(rs.getTime("ora")).replaceAll(":", "");
				if(Integer.parseInt(ora)>=600&&Integer.parseInt(ora)<=2330)
					
	 				heatmap+="['"+timeFormat.format(rs.getTime("ora"))+"','"+data1+"',"+val+"],";
				
			}
			rs.close();
			
			if(heatmap.length()>0)
				heatmap=heatmap.substring(0,heatmap.length()-1);
			
			obj.put("xAxis", xAxis);
			obj.put("yAxis", yAxis);
			obj.put("heatmap", heatmap);
			obj.put("max", max);
			obj.put("height", height);
			// *********
			
			
			// *********
			// Pianificazione
			//Calendar c_heat = Calendar.getInstance(new Locale("en","US"));
			
			
			c_heat = Calendar.getInstance(Locale.GERMAN);
			
			// Set the calendar to monday of the current week
			c_heat.set(Calendar.YEAR, Integer.parseInt(anno));     
			c_heat.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
			c_heat.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			dtda = dateIso.format(c_heat.getTime());
			c_heat.add(Calendar.DATE, 1);
			dtal = dateIso.format(c_heat.getTime());
			
			// ---------
			// Colonne e ruler
			String cols = "";
			String rulertot = "";
			for (int i = 0; i <= 36; i++) {
				cols+="h"+(i+1)+" text,";
				
				if(isInt((double)i/2)) {
					//System.out.println(((double)i/2)+" int");
					rulertot+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\">"+(((i/2)+5)+1)+"</label>";
				}
				else {
					//System.out.println(((double)i/2)+" double");
					rulertot+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\"><span style=\"font-size:8px\">&times;</span></label>";
				}
				
			}
			cols = cols.substring(0,cols.length()-1);
			System.out.println(cols);
			
			sql   = "select * from crosstab(' select rel.id_employee, rel.name, rel.surname, rel.description, rel.id_departments, "
					+ "rel.b,plan.color from (select name, surname, id_employee, description, id_departments, b from "
					+ "(select * from vw_emp_dep_indate v, hr_employee e, hr_departments d "
					+ "where e.id = id_employee and d.id = v.id_departments "
					+ "and id_departments = ''"+id_departments+"'' "
					+ "and anno = "+anno+" and settimana = "+settimana+") a, "
					+ "generate_series(''"+dtda+" 06:00:00''::timestamp,''"+dtal+" 00:00:00''::timestamp,''30 minutes''::interval) b) "
					+ "rel left join (select to_timestamp(datetime_start),* from hr_planification "
					+ "join hr_planification_type on id_planification_type = hr_planification_type.id "
					+ "where id_departments = ''"+id_departments+"'' and plan_year = ''"+anno+"'' "
					+ "and plan_week = ''"+settimana+"'' and plan_day = ''0'') plan on rel.id_employee = plan.id_employee "
					+ "and rel.b between to_timestamp(datetime_start) and to_timestamp(datetime_end) order by rel.id_employee,rel.b'::text,' "
					+ "select generate_series(''"+dtda+" 06:00:00''::timestamp,''"+dtal+" 00:00:00''::timestamp,''30 minutes''::interval)'::text) "
					+ "as final_result(id_employee text, name text, surname text, description text, id_departments text, "+cols+" "
					+ ") ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			int nrdip = 0;
			
			String $emp_id = "";
			String $emp_name = "";
			String $emp_surname = "";
			
			String eyeClass="";
			String ruler="";
			String day0="";
			String totday1="";
			String totday2="";
			String totday3="";
			String totday4="";
			String totday5="";
			String totday6="";
			String id_dep="";
			String ds_dep="";
			String inputs="";
			double tot_day_emp = 0;
			
			
			String ini = "";
			String fin = "";
			String hv = "";
			String hv_prec = "";
			int periodid = 0;
			
			
			// totale per fascia oraria
			String tot1 = "";
			
			List<String> to = new ArrayList<String>();
			
			double t0=0;
			double t1=0;
			double t2=0;
			double t3=0;
			double t4=0;
			double t5=0;
			double t6=0;
			double t7=0;
			double t8=0;
			double t9=0;
			double t10=0;
			double t11=0;
			double t12=0;
			double t13=0;
			double t14=0;
			double t15=0;
			double t16=0;
			double t17=0;
			double t18=0;
			double t19=0;
			double t20=0;
			double t21=0;
			double t22=0;
			double t23=0;
			double t24=0;
			double t25=0;
			double t26=0;
			double t27=0;
			double t28=0;
			double t29=0;
			double t30=0;
			double t31=0;
			double t32=0;
			double t33=0;
			double t34=0;
			double t35=0;
			double t36=0;
			double t37=0;
			
			// totale numero addetti
			String tot2 = "";
			
			List<String> tto = new ArrayList<String>();
			
			String tot3="";
			
			while(rs.next()) {
				
				nrdip+=1;
				
				$emp_id = rs.getString("id_employee");
				$emp_name = rs.getString("name");
				$emp_surname = rs.getString("surname");
				
				id_dep=rs.getString("id_departments");
				ds_dep=rs.getString("description");
				
				if(id_dep.equals(id_departments))
					ds_dep="";
				
				boolean skipfirst = true;
				int last = -1;
				
				for (int i = 0; i <= 36; i++) {
					
					//last = -1;
					
					String h=""+rs.getString("h"+(i+1));
					h=Utility.noNull(h);
					
					if(h.trim().length()==0) {
						skipfirst = true;
						//last = (i-1);
							
						System.out.println(last);
						
						
						
						
						
						
						if(last>=0) {
							
							if(last==0) {
								t0-=0.5;
								last = -1;
							}
							else if(last==1) {
								t1-=0.5;
								last = -1;
							}
							else if(last==2) {
								t2-=0.5;
								last = -1;
							}
							else if(last==3) {
								t3-=0.5;
								last = -1;
							}
							else if(last==4) {
								t4-=0.5;
								last = -1;
							}
							else if(last==5) {
								t5-=0.5;
								last = -1;
							}
							else if(last==6) {
								t6-=0.5;
								last = -1;
							}
							else if(last==7) {
								t7-=0.5;
								last = -1;
							}
							else if(last==8) {
								t8-=0.5;
								last = -1;
							}
							else if(last==9) {
								t9-=0.5;
								last = -1;
							}
							else if(last==10) {
								t10-=0.5;
								last = -1;
							}
							else if(last==11) {
								t11-=0.5;
								last = -1;
							}
							else if(last==12) {
								t12-=0.5;
								last = -1;
							}
							else if(last==13) {
								t13-=0.5;
								last = -1;
							}
							else if(last==14) {
								t14-=0.5;
								last = -1;
							}
							else if(last==15) {
								t15-=0.5;
								last = -1;
							}
							else if(last==16) {
								t16-=0.5;
								last = -1;
							}
							else if(last==17) {
								t17-=0.5;
								last = -1;
							}
							else if(last==18) {
								t18-=0.5;
								last = -1;
							}
							else if(last==19) {
								t19-=0.5;
								last = -1;
							}
							else if(last==20) {
								t20-=0.5;
								last = -1;
							}
							else if(last==21) {
								t21-=0.5;
								last = -1;
							}
							else if(last==22) {
								t22-=0.5;
								last = -1;
							}
							else if(last==23) {
								t23-=0.5;
								last = -1;
							}
							else if(last==24) {
								t24-=0.5;
								last = -1;
							}
							else if(last==25) {
								t25-=0.5;
								last = -1;
							}
							else if(last==26) {
								t26-=0.5;
								last = -1;
							}
							else if(last==27) {
								t27-=0.5;
								last = -1;
							}
							else if(last==28) {
								t28-=0.5;
								last = -1;
							}
							else if(last==29) {
								t29-=0.5;
								last = -1;
							}
							else if(last==30) {
								t30-=0.5;
								last = -1;
							}
							else if(last==31) {
								t31-=0.5;
								last = -1;
							}
							else if(last==32) {
								t32-=0.5;
								last = -1;
							}
							else if(last==33) {
								t33-=0.5;
								last = -1;
							}
							else if(last==34) {
								t34-=0.5;
								last = -1;
							}
							else if(last==35) {
								t35-=0.5;
								last = -1;
							}
							else if(last==36) {
								t36-=0.5;
								last = -1;
							}
							else if(last==37) {
								t37-=0.5;
								last = -1;
							}
							
						}
						
						
						
					}
					
					
						
					
					String css="";
					// totale dipendente/giorno - inputs
					if(h.equals("turno")) {
						
						last = i;
						
						if(hv_prec.length()==0) {
							ini = ""+((((double)i/2)+5)+1);
						}
						
						if(skipfirst) {
							skipfirst = false;
						}else {
							tot_day_emp+=0.5;
						}
						
						if(i==0) {
							t0+=0.5;
						}
						else if(i==1) {
							t1+=0.5;
						}
						else if(i==2) {
							t2+=0.5;
						}
						else if(i==3) {
							t3+=0.5;
						}
						else if(i==4) {
							t4+=0.5;
						}
						else if(i==5) {
							t5+=0.5;
						}
						else if(i==6) {
							t6+=0.5;
						}
						else if(i==7) {
							t7+=0.5;
						}
						else if(i==8) {
							t8+=0.5;
						}
						else if(i==9) {
							t9+=0.5;
						}
						else if(i==10) {
							t10+=0.5;
						}
						else if(i==11) {
							t11+=0.5;
						}
						else if(i==12) {
							t12+=0.5;
						}
						else if(i==13) {
							t13+=0.5;
						}
						else if(i==14) {
							t14+=0.5;
						}
						else if(i==15) {
							t15+=0.5;
						}
						else if(i==16) {
							t16+=0.5;
						}
						else if(i==17) {
							t17+=0.5;
						}
						else if(i==18) {
							t18+=0.5;
						}
						else if(i==19) {
							t19+=0.5;
						}
						else if(i==20) {
							t20+=0.5;
						}
						else if(i==21) {
							t21+=0.5;
						}
						else if(i==22) {
							t22+=0.5;
						}
						else if(i==23) {
							t23+=0.5;
						}
						else if(i==24) {
							t24+=0.5;
						}
						else if(i==25) {
							t25+=0.5;
						}
						else if(i==26) {
							t26+=0.5;
						}
						else if(i==27) {
							t27+=0.5;
						}
						else if(i==28) {
							t28+=0.5;
						}
						else if(i==29) {
							t29+=0.5;
						}
						else if(i==30) {
							t30+=0.5;
						}
						else if(i==31) {
							t31+=0.5;
						}
						else if(i==32) {
							t32+=0.5;
						}
						else if(i==33) {
							t33+=0.5;
						}
						else if(i==34) {
							t34+=0.5;
						}
						else if(i==35) {
							t35+=0.5;
						}
						else if(i==36) {
							t36+=0.5;
						}
						else if(i==37) {
							t37+=0.5;
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					if(h.length()==0) {
						if(hv_prec.length()>0) {
							fin = hv_prec;
						}
					}
					
					
					if(h.length()>0)
						css="color:#000;font-size:12px;font-weight:bold;";
					
					day0+="<label class=\"ruler2 "+h+"\" style=\"left: "+(((double)i/2)/18*100)+"%\">&nbsp;</label>";
					
					if(isInt((double)i/2)) {
						//System.out.println(((double)i/2)+" int");
						ruler+="<label class=\"ruler\" style=\""+css+"left: "+(((double)i/2)/18*100)+"%\">"+(((i/2)+5)+1)+"</label>";
					}
					else {
						//System.out.println(((double)i/2)+" double");
						ruler+="<label class=\"ruler\" style=\""+css+"left: "+(((double)i/2)/18*100)+"%\"><span style=\"font-size:8px\">&times;</span></label>";
					}
					
					
					
					
					if(ini.length()>0&&fin.length()>0) {
						periodid+=1;
						System.out.println($emp_surname+" "+ini+" "+fin);
						inputs+="<input type=\"hidden\" id=\"p_dati0"+$emp_id+"_"+periodid+"\" name=\"p_dati0"+$emp_id+"\" value=\""+Double.parseDouble(ini)+","+Double.parseDouble(fin)+"\" />";
						
						ini = "";
						fin = "";
						hv = "";
						hv_prec = "";
					}
					
					if(h.equals("turno")) 
						hv_prec = ""+((((double)i/2)+5)+1);
					
					//tot_day_emp=tot_day_emp-0.5;
					
				}
				
				if(tot_day_emp==0) {
					eyeClass = "eye";
				}
				else {
					eyeClass = "";
				}
				
				plan2.add(new Plan2($emp_id,$emp_name,$emp_surname,
						eyeClass,ruler,day0,inputs,totday1,totday2,totday3,totday4,totday5,totday6,
						id_dep, ds_dep, ""+tot_day_emp));
				
				tot_day_emp = 0;
				day0 = "";
				ruler="";
				
				ini = "";
				fin = "";
				hv = "";
				hv_prec = "";
				periodid = 0;
				inputs = "";
				
			}
			rs.close();
			
			
			to.add(""+(t0+t1));
			to.add(""+(t2+t3));
			to.add(""+(t4+t5));
			to.add(""+(t6+t7));
			to.add(""+(t8+t9));
			to.add(""+(t10+t11));
			to.add(""+(t12+t13));
			to.add(""+(t14+t15));
			to.add(""+(t16+t17));
			to.add(""+(t18+t19));
			to.add(""+(t20+t21));
			to.add(""+(t22+t23));
			to.add(""+(t24+t25));
			to.add(""+(t26+t27));
			to.add(""+(t28+t29));
			to.add(""+(t30+t31));
			to.add(""+(t32+t33));
			to.add(""+(t34+t35));
			to.add(""+(t36+t37));
			
			
			
			tto.add(""+Math.ceil(t0+t1));
			tto.add(""+Math.ceil(t2+t3));
			tto.add(""+Math.ceil(t4+t5));
			tto.add(""+Math.ceil(t6+t7));
			tto.add(""+Math.ceil(t8+t9));
			tto.add(""+Math.ceil(t10+t11));
			tto.add(""+Math.ceil(t12+t13));
			tto.add(""+Math.ceil(t14+t15));
			tto.add(""+Math.ceil(t16+t17));
			tto.add(""+Math.ceil(t18+t19));
			tto.add(""+Math.ceil(t20+t21));
			tto.add(""+Math.ceil(t22+t23));
			tto.add(""+Math.ceil(t24+t25));
			tto.add(""+Math.ceil(t26+t27));
			tto.add(""+Math.ceil(t28+t29));
			tto.add(""+Math.ceil(t30+t31));
			tto.add(""+Math.ceil(t32+t33));
			tto.add(""+Math.ceil(t34+t35));
			tto.add(""+Math.ceil(t36+t37));
			
			double ttt1 = 0;
			double ttt2 = 0;
			double ttt3 = 0;
			
			String minNum_employee = getMinnum_employee(conn, id_departments, anno, settimana, "0");
			System.out.println(minNum_employee);
			
			String temp[] = minNum_employee.split(",");
			
			for (int i = 0; i <= 36; i++) {
				cols+="h"+(i+1)+" text,";
				
				if(isInt((double)i/2)) {
					//System.out.println(((double)i/2)+" int");
					tot1+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\">|</label>";
					tot2+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\">|</label>";
					tot3+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\">|</label>";
					
				}
				else {
					//System.out.println(((double)i/2)+" double");
					tot1+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\"><span style=\"font-size:14px;color:#000\">"+to.get(i/2)+"</span></label>";
					tot2+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\"><span style=\"font-size:14px;color:#000\">"+tto.get(i/2)+"</span></label>";
					
					String css = "";
					if(Float.parseFloat(to.get(i/2))!=Float.parseFloat(temp[(i/2)])) {
						css = "color:#f00";
					}
					else {
						css = "color:#000";
					}
					tot3+="<label class=\"ruler\" style=\"left: "+(((double)i/2)/18*100)+"%\"><span style=\"font-size:14px;"+css+"\">"+temp[(i/2)]+"</span></label>";
					
					ttt1+=Double.parseDouble(to.get(i/2));
					ttt2+=Double.parseDouble(tto.get(i/2));
					ttt3+=Float.parseFloat(temp[(i/2)]);
					
					
					
				}
				
			}
			
			obj.put("tot1", tot1);
			obj.put("tot2", tot2);
			obj.put("tot3", tot3);
			
			obj.put("ttt1", ttt1);
			obj.put("ttt2", ttt2);
			obj.put("ttt3", ttt3);
			
			obj.put("plan2", plan2);
			obj.put("rulertot", rulertot);
			obj.put("nrdip", nrdip);
			
			// *********
			
			
			
			
			
			
			
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
					//st.executeUpdate(sql);
					
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
					//st.executeUpdate(sql);
					
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
															//st.executeUpdate(sql);
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
				String status = "";
				String sts = "";
				
				sql   = "select count(status) nrstatus, status from hr_planification a , vw_emp_dep_indate b "+
						"where plan_year = '"+anno+"' and plan_week = '"+settimana+"' "+
						"and a.id_departments = '"+id_departments+"' "+
						"and a.id_employee = b.id_employee and a.id_departments = b.id_departments "+
						"and b.anno = cast(a.plan_year as int) and b.settimana = cast(a.plan_week as int) "+
						"and b.dow = cast(plan_day as int) "+
						"and status not in ('') group by status fetch first 1 rows only ";
				
				System.out.println(sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					status = rs.getString("status");
					nrStatus = rs.getInt("nrStatus");
				}
				rs.close();
				
				if(status.length()>0) {
					if(status.equals("A")) {
						sts = "A";
						status = "<span class=\"label bg-success\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("approvato")+"\">APP</span>";
						
						obj.put("dis","disabled");
					}
					else if(status.equals("R")) {
						sts = "R";
						status = "<span class=\"label bg-danger\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("rigettato")+"\">REJ</span>";
					}
					else if(status.equals("X")) {
						sts = "X";
						status = "<span class=\"label bg-info\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("richiesta")+"\">REQ</span>";
						
						obj.put("dis","disabled");
					}
					else if(status.equals("L")) {
						sts = "L";
						status = "<span class=\"label bg-warning\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("lavorato")+"\">WRK</span>";
						
						obj.put("dis","disabled");
					}
					else if(status.equals("Y")) {
						sts = "Y";
						status = "<span class=\"label bg-light\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("riaperto")+"\">OPN</span>";
					}
				}
				
				obj.put("s", status);
				obj.put("sts", sts);
				
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			// Set the calendar to monday of the current week
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			Calendar cweek = Calendar.getInstance(Locale.GERMAN);
			String giorno = "";
			cweek.set(Calendar.YEAR, Integer.parseInt(anno));     
			cweek.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(settimana));     
			cweek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			String active = "";
			for (int d = 0; d <= 6; d++) {
			    
			    if(d==0) {
					giorno = propLang.get("lun");
					active = " class=\"active\" ";
			    }
				if(d==1) {
					giorno = propLang.get("mar");
				}
				if(d==2) {
					giorno = propLang.get("mer");
				}
				if(d==3) {
					giorno = propLang.get("gio");
				}
				if(d==4) {
					giorno = propLang.get("ven");
				}
				if(d==5) {
					giorno = propLang.get("sab");
				}
				if(d==6) {
					giorno = propLang.get("dom");
				}
				
				giorni.add(new Tables("DAY",""+d,giorno,sdf.format(cweek.getTime()),active," onclick=\"loadDay("+d+")\" ",""));
				
				cweek.add(Calendar.DATE, 1);
				active = "";
			
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
		
		
		obj.put("msg", msg);
		
		obj.put("giorni", giorni);
		
		return new ModelAndView("/hr/planner/planner-list-tab3", obj);
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
						status = "<span class=\"label bg-success\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("approvato")+"\">APP</span>";
					}
					else if(status.equals("R")) {
						status = "<span class=\"label bg-danger\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("rigettato")+"\">REJ</span>";
					}
					else if(status.equals("X")) {
						status = "<span class=\"label bg-info\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("richiesta")+"\">REQ</span>";
					}
					else if(status.equals("L")) {
						status = "<span class=\"label bg-warning\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("lavorato")+"\">WRK</span>";
					}
					else if(status.equals("Y")) {
						status = "<span class=\"label bg-light\" data-toggle=\"tooltip\" "+
								 "data-placement=\"right\" title=\"\" data-original-title=\""+propLang.get("riaperto")+"\">OPN</span>";
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
