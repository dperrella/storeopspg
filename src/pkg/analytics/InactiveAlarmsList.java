package pkg.analytics;

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

import pkg.beans.Function;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class InactiveAlarmsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
				
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables2> alarms = new ArrayList<Tables2>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		alarms.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String order = ""+req.getParameter("order");
		order=Utility.noNull(order);
		String sort = ""+req.getParameter("sort");
		sort=Utility.noNull(sort);
	
		if(order.length()>0) {
			order = "order by "+order+" "+sort;
		}
		else
			order = "order by x.from_time desc ";
		
		if(sort.length()==0)
			sort = "desc";
		else
			sort = "";
		
		obj.put("sort", sort);
		
		// Paginazione
		String pag = ""+req.getParameter("pag");
		pag = Utility.noNull(pag);

		if(pag.length()==0) {
			pag = "1";
		}
		
		System.out.println(pag);
		
		int rowIni = 0;
		int rowFin = 0;
		int nrPag = 0;

		int numRow = 0;
		String prev = "";
		String next = "";
		String nrRecord = "20";
		
		try {
			
			if(q.length()>0) {
				cond+=" and concat(upper(x.store) , ' ' , upper(x.description)) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnAnalytics();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			// Numero di record totali
			sql  =  "select count(distinct(x.id||x.devicename||x.store||x.code||x.description|| "
					+ "x.threshold||x.timeunit||x.control_time||x.from_time||x.to_time||x.value)) num "
					+ "from an_alarm_report_user a, rel_user_customers b,  "
					+ "an_alarm_report c, vw_inactive_alarm x "
					+ "where c.code = x.code and a.id_customer = b.idcustomer "
					+ "and a.id_alarm_report = c.id "
					+ "and a.id_role = '"+(String)session.getAttribute("tipo")+"' "
					+ "and b.idusr = '"+(String)session.getAttribute("username")+"' "+cond;
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				numRow=rs.getInt("num");
			}
			rs.close();
			
			nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
			
			int max = 0;
			
			for (int i=1; i<=nrPag; i++) {
				
				max+=Integer.parseInt(nrRecord);
				
				if(i==Integer.parseInt(pag)) {
					
					prev = ""+(i-1);
					next = ""+(i+1);
					
					rowIni = (max-Integer.parseInt(nrRecord));
					rowFin = Integer.parseInt(nrRecord);
					
				}
			}
			
			System.out.println(nrPag);
			
			
			// Carica gli allarmi inattivi
			sql  =  "select x.id, x.devicename, x.store, x.code, x.description, "
					+ "x.threshold, x.timeunit, x.control_time, x.from_time, x.to_time, x.value "
					+ "from an_alarm_report_user a, rel_user_customers b,  "
					+ "an_alarm_report c "
					+ "left join analytics_eventcode g1 on g1.code = c.alarm1 "
					+ "left join analytics_eventcode g2 on g2.code = c.alarm2, "
					+ "lang_alarm_report d, vw_inactive_alarm x "
					+ "where c.code = x.code and a.id_customer = b.idcustomer "
					+ "and a.id_alarm_report = c.id "
					+ "and c.id = d.id_alarm_report and d.lang = '"+(String)session.getAttribute("language")+"' "
					+ "and a.id_role = '"+(String)session.getAttribute("tipo")+"' "
					+ "and b.idusr = '"+(String)session.getAttribute("username")+"' "+cond+" "
					+ "group by x.id, x.devicename, x.store, x.code, x.description, "
					+ "x.threshold, x.timeunit, x.control_time, x.from_time, x.to_time, x.value "
					+ " "+order+" "
					+ "limit "+rowFin+" offset "+rowIni+" ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.createStatement();
		
			// Esegue l'sql
			rs=st.executeQuery(sql);
			
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
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Inactive Alarms");
		obj.put("alarms", alarms);
		obj.put("count", count);
		obj.put("msg", msg);
		
		obj.put("tot", numRow);
		obj.put("nrPag", nrPag);
		obj.put("prev", prev);
		obj.put("next", next);
		obj.put("ini", rowIni);
		obj.put("fin", rowFin);
		
		
		return new ModelAndView("/app/report/inactive-alarms-list", obj);
	}
	
}
