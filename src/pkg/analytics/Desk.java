package pkg.analytics;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables2;
import pkg.beans.Report;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Customers;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		List<Customers> cus = new ArrayList<Customers>();
		List<Stores> sto = new ArrayList<Stores>();
		List<Tables> wk = new ArrayList<Tables>();
		List<Tables> mn = new ArrayList<Tables>();
		List<Report> rep = new ArrayList<Report>();
		
		List<Tables2> alarms = new ArrayList<Tables2>();
		
		// Pulisce la lista
		cus.clear();
		sto.clear();
		wk.clear();
		mn.clear();
		rep.clear();
		
		alarms.clear();
		
		String id_customer = ""+req.getParameter("id_customer");
		id_customer=Utility.noNull(id_customer);
		
		String id_report = ""+req.getParameter("id_report");
		id_report=Utility.noNull(id_report);
		
		String code = ""+req.getParameter("code");
		code=Utility.noNull(code);
		
		String description = ""+req.getParameter("description");
		description=Utility.noNull(description);
		
		String icon = ""+req.getParameter("icon");
		icon=Utility.noNull(icon);
		
		String id_report_type = ""+req.getParameter("id_report_type");
		id_report_type=Utility.noNull(id_report_type);
		
		String id_subreport_type = ""+req.getParameter("id_subreport_type");
		id_subreport_type=Utility.noNull(id_subreport_type);
		
		String alarm1 = ""+req.getParameter("alarm1");
		alarm1=Utility.noNull(alarm1);
		
		String alarm2 = ""+req.getParameter("alarm2");
		alarm2=Utility.noNull(alarm2);
		
		String alarm1_descr = ""+req.getParameter("alarm1_descr");
		alarm1=Utility.noNull(alarm1);
		
		String alarm2_descr = ""+req.getParameter("alarm2_descr");
		alarm2_descr=Utility.noNull(alarm2_descr);
		
		String historic = ""+req.getParameter("historic");
		historic=Utility.noNull(historic);
		
		String benchmark = ""+req.getParameter("benchmark");
		benchmark=Utility.noNull(benchmark);
		
		String orario = ""+req.getParameter("orario");
		orario=Utility.noNull(orario);
		
		if(orario.length()==0)
			orario = "S";
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String oggiIta = dateIta.format(date);
		
		String currentYear = oggi.substring(0,4);
		String currentMonth = oggi.substring(4,6);
		String currentWeek = "";
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		String db = (String) session.getAttribute("db");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int nr_customers = 0;
		int nr_stores = 0;
		int nr_devices = 0;
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAnalytics();
			
			// ==================
			// NUMERO NEGOZI
			// ==================
			// sql da eseguire
			sql  =  "select count(a.id) nrstores "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore::integer from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_stores=rs.getInt("nrstores");
			}
			rs.close();
			st.close();
			
			
			// ==================
			// NUMERO CLIENTI
			// ==================
			sql  =  "select count(id) nrcustomers from customers where "
					+ "id in(select idcustomer::integer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_customers = rs.getInt("nrcustomers");
			}
			rs.close();
			st.close();
			
			
			// ==================
			// NUMERO DISPOSITIVI
			// ==================
			sql  =  "select count(a.id) nrdevices "
					+ "from devices a, stores b, customers c, devicetype d "
					+ "where a.id_store = b.id and b.id_customer = c.id "
					+ "and a.device_type = d.id "
					+ "and a.id_store in(select idstore::integer from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_devices = rs.getInt("nrdevices");
			}
			rs.close();
			st.close();
			
			// ==================
			// REPORT ABILITATI ALL'UTENTE/RUOLO
			// ==================
			
			String lang = (String)session.getAttribute("language");
			if(lang.equals("ES"))
				lang = "EN";
			
			sql  =  "select c.id, c.code, d.description, c.id_report_type, "
					+ "c.id_subreport_type, c.alarm1, c.alarm2, c.storico, c.benchmark, c.icon, "
					+ "e.description ds_report_type, "
					+ "f.description ds_subreport_type, g1.description alarm1_descr, g2.description alarm2_descr "
					+ "from an_report_user a, rel_user_customers b,  "
					+ "an_report c "
					+ "left join analytics_eventcode g1 on g1.code = c.alarm1 "
					+ "left join analytics_eventcode g2 on g2.code = c.alarm2, "
					+ "lang_report d, "
					+ "lang_report_type e, lang_subreport_type f "
					+ "where a.id_customer = b.idcustomer::integer "
					+ "and a.id_report = c.id "
					+ "and c.id = d.id_report and d.lang = '"+lang+"' "
					+ "and a.id_role = '"+(String)session.getAttribute("tipo")+"' "
					+ "and b.idusr = '"+(String)session.getAttribute("username")+"' "
					+ "and c.id_report_type = e.id_report_type and e.lang = '"+lang+"' "
					+ "and c.id_subreport_type = f.id_subreport_type and f.lang = '"+lang+"' "
					+ "group by c.id, c.code, d.description, b.idusr, c.id_report_type, "
					+ "c.id_subreport_type, c.alarm1, c.alarm2, c.storico, c.benchmark, c.icon, e.description, f.description, "
					+ "g1.description, g2.description ";
			
			System.out.println(sql);
			
			// Campi database
			String $id="";
			String $code="";
			String $description="";
			String $id_report_type="";
			String $ds_report_type="";
			String $id_subreport_type="";
			String $ds_subreport_type="";
			String $alarm1="";
			String $alarm2="";
			String $historic="";
			String $benchmark="";
			String $icon="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			while(rs.next()) {
				
				$id=rs.getString("id");
				$code=rs.getString("code");
				$description=rs.getString("description");
				$id_report_type=rs.getString("id_report_type");
				$ds_report_type=rs.getString("ds_report_type");
				$id_subreport_type=rs.getString("id_subreport_type");
				$ds_subreport_type=rs.getString("ds_subreport_type");
				$alarm1=rs.getString("alarm1");
				$alarm2=rs.getString("alarm2");
				$historic=rs.getString("storico");
				$benchmark=rs.getString("benchmark");
				$icon=rs.getString("icon");
				
				$cmp1=""+rs.getString("alarm1_descr");
				$cmp1=Utility.noNull($cmp1);
				$cmp2=""+rs.getString("alarm2_descr");
				$cmp2=Utility.noNull($cmp2);
				
				rep.add(new Report($id,$code,$description,$id_report_type,$ds_report_type,
						$id_subreport_type,$ds_subreport_type,$alarm1,$alarm2,
						$historic,$benchmark,$icon,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			
			
			// =======================
			// Mesi
			String mm = "";
			String desMm = "";
			$cmp1 = "";
			
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
				
				// Settimana attuale
				if(mm.equals(currentMonth)) {
					$cmp1 = "selected ";
				}
				
				// valorizza la lista
				mn.add(new Tables("MON",mm, desMm,$cmp1,"","",""));
				
				$cmp1 = "";
				
			}
			
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(currentYear), 0, 1);
			
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
			Calendar cal = Calendar.getInstance();
			// Imposta l'anno corrente
			cal.set(Integer.parseInt(currentYear), 0, 1);
			
			String week = "";
			
			// campi tables
			String $xcdtb = "SET";
			String $xcdel = "";
			String $xdtab = "";
			$cmp1 = "";
			
			for (int i=1; i<=weeks; i++) {
				  
				week = "00"+i;
				week = week.substring(week.length()-2,week.length());
				
				// valorizza xcdel col numero della settimana
				$xcdel = week;
				
				cal.set(Integer.parseInt(currentYear), 0, 1);
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
			
			// Se è stato passato l'id di un report carico la lista dei clienti e dei negozi...
			if(id_report.length()>0) {
				
				sql  =  "select * from customers where "
						+ "id in(select idcustomer::integer from rel_user_customers "
						+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String $cus_companyname;
				String $cus_description; 
				String $cus_brandname;
				String $cus_id;
				
				while(rs.next()) {
					
					$cus_companyname=rs.getString("companyname");
					$cus_description=rs.getString("description");
					$cus_brandname=rs.getString("brandname");
					$cus_id=rs.getString("id");
					
					cus.add(new Customers($cus_companyname,$cus_description,"",$cus_brandname,
							"","",$cus_id,"","","","",
							"","","","","",
							"","","",""));
					
				}
				rs.close();
				st.close();
				
				
				String condCustomer = "";
				if(id_customer.length()>0) {
					condCustomer = " and c.id = "+id_customer+" ";
				}
				sql  =  "select r.id_report, a.id, a.description, c.brandname custbrand, "
						+ "customeridstore "
						+ "from an_report_user r, stores a, customers c "
						+ "where id_role = '"+(String)session.getAttribute("tipo")+"' "
						+ "and a.id_customer = c.id and a.id_customer = r.id_customer "
						+ "and id_report = '"+id_report+"' "+condCustomer+" "
						+ "and a.id in(select idstore::integer from rel_user_stores "
						+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
				System.out.println(sql);
				
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				// Campi database
				String $sto_description="";
				String $sto_id="";
				String $customeridstore="";
				$cmp1="";
				$cmp2="";
				$cmp3="";
				$cmp4="";
				
				while(rs.next()) {
					
					$sto_description=rs.getString("description");
					$sto_id=rs.getString("id");
					$cmp1=rs.getString("custbrand");
					$customeridstore=""+rs.getString("customeridstore");
					$customeridstore=Utility.noNull($customeridstore);
					if($customeridstore.length()==0)
						$customeridstore=$sto_id;
					
					sto.add(new Stores($sto_description,"","","",
							"","","","","","","",
							"","","","","",
							$sto_id,"","","","","",
							"","","","","",
							"","","",
							$customeridstore,$cmp2,$cmp3,$cmp4));
					
				}
				rs.close();
				st.close();
				
			}
			else {
			
				// Controlla se il ruolo/utente ha la possibilità di vedere gli allarmi attivi
				sql  =  "select count(id_alarm_report) num from an_alarm_report_user a, rel_user_customers b "
						+ "where a.id_customer = b.idcustomer::integer "
						+ "and a.id_role = '"+(String)session.getAttribute("tipo")+"' "
						+ "and b.idusr = '"+(String)session.getAttribute("username")+"' ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
			
				// Esegue l'sql
				rs=st.executeQuery();
				
				int num = 0;
				if(rs.next()) {
					num = rs.getInt("num");
				}
				rs.close();
				st.close();
				
				obj.put("num", num);
				
				
				// Carica gli allarmi attivi
				sql  =  "select x.id, x.devicename, x.store, x.code, x.description, "
						+ "x.threshold, x.timeunit, x.control_time, x.from_time, x.to_time, x.value "
						+ "from an_alarm_report_user a, rel_user_customers b,  "
						+ "an_alarm_report c "
						+ "left join analytics_eventcode g1 on g1.code = c.alarm1 "
						+ "left join analytics_eventcode g2 on g2.code = c.alarm2, "
						+ "lang_alarm_report d, vw_active_alarm x "
						+ "where c.code = x.code and a.id_customer = b.idcustomer "
						+ "and a.id_alarm_report = c.id "
						+ "and c.id = d.id_alarm_report and d.lang = '"+(String)session.getAttribute("language")+"' "
						+ "and a.id_role = '"+(String)session.getAttribute("tipo")+"' "
						+ "and b.idusr = '"+(String)session.getAttribute("username")+"' "
						+ "group by x.id, x.devicename, x.store, x.code, x.description, "
						+ "x.threshold, x.timeunit, x.control_time, x.from_time, x.to_time, x.value "
						+ "order by x.from_time desc ";
				
				sql  =  "select distinct x.id, x.devicename, x.store, x.code, x.description, "
						+ "x.threshold, x.timeunit, x.control_time, x.from_time, x.to_time, x.value "
						+ " from vw_active_alarm x join devices on x.id = devices.id "
						+ "join stores on id_store =stores.id " 
						+ "join rel_user_customers b on stores.id_customer = idcustomer::integer "
						+ "join an_alarm_report_user a on a.id_customer = b.idcustomer::integer "
						+ "where b.idusr = '"+(String)session.getAttribute("username")+"' "
						+ "and a.id_role = '"+(String)session.getAttribute("tipo")+"' ";
				
				System.out.println(sql);
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
			
				// Esegue l'sql
				rs=st.executeQuery();
				
				String $al_id = "";
				String $al_devicename = "";
				String $al_store = "";
				String $al_code = "";
				String $al_description = "";
				String $al_threshold = "";
				String $al_timeunit = "";
				String $al_control_time = "";
				String $al_from_time = "";
				String $al_to_time = "";
				String $al_value = "";
				
				while(rs.next()) {
					
					$al_id = rs.getString("id");
					$al_devicename = rs.getString("devicename");
					$al_store = rs.getString("store");
					$al_code = rs.getString("code");
					$al_description = rs.getString("description");
					$al_threshold = rs.getString("threshold");
					$al_timeunit = rs.getString("timeunit");
					$al_control_time = rs.getString("control_time");
					$al_from_time = rs.getString("from_time");
					$al_to_time = rs.getString("to_time");
					$al_value = df.format(rs.getDouble("value"));
					
					alarms.add(new Tables2("","",$al_value,
							$al_id, $al_devicename, $al_store,
							$al_code, $al_description, $al_threshold, $al_timeunit,
							$al_control_time, $al_from_time, $al_to_time));
				
				}
				
				rs.close();
				st.close();
			
			}
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("mn", mn);
		obj.put("weeks", wk);
		obj.put("oggi", oggiIta);
		obj.put("currentYear", currentYear);
		
		obj.put("nr_customers", nr_customers);
		obj.put("nr_stores", nr_stores);
		obj.put("nr_devices", nr_devices);
		obj.put("rep", rep);
		obj.put("sto", sto);
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		obj.put("id_report", id_report);
		obj.put("code", code);
		obj.put("description", description);
		obj.put("icon", icon);
		obj.put("id_report_type", id_report_type);
		obj.put("id_subreport_type", id_subreport_type);
		obj.put("alarm1", alarm1);
		obj.put("alarm2", alarm2);
		obj.put("alarm1_descr", alarm1_descr);
		obj.put("alarm2_descr", alarm2_descr);
		obj.put("historic", historic);
		obj.put("benchmark", benchmark);
		obj.put("orario", orario);
		
		obj.put("id_customer", id_customer);
		obj.put("cus", cus);
		
		// Lista active alarm
		obj.put("alarms", alarms);
		
		
		return new ModelAndView("/analytics/main/index", obj);
	}
	
}
