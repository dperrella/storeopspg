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

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Tables> plan = new ArrayList<Tables>();
		List<Tables> cus = new ArrayList<Tables>();
		
		plan.clear();
		cus.clear();
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String currentYear = oggi.substring(0,4);
		
		
		Calendar c = Calendar.getInstance(Locale.GERMAN);
		c.set(Integer.parseInt(currentYear), 0, 1);
		
		// Settimana attuale
		c.setTime(date);
		String currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
		currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
				
		// campi passati dalla session
		System.out.println("*****************");
		System.out.println(session.getAttribute("idcus"));
		System.out.println(session.getAttribute("tipo_hr"));
		System.out.println("*****************");
		
		// Dalla request
		String idcus = ""+req.getParameter("idcus");
		idcus = Utility.noNull(idcus);
		int nr_customers = 0;
		
		System.out.println("idcus:"+idcus);
		
		// Se dalla request è vuoto legge dalla session
		if(idcus.length()==0) {
			idcus = ""+session.getAttribute("idcus");
			
			System.out.println("idcus:"+idcus);
			
			idcus = Utility.noNull(idcus);
		}
		
		System.out.println("idcus:"+idcus);
		
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo_hr");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		String db = (String) session.getAttribute("db");
		
		// Codice Cliente (Se tipo = 'A' -> '')
		String cdcli = (String) session.getAttribute("cdcli");
		
		Connection conn = null;
		Connection connAdmin = null;
		Statement st = null;
		Statement stAdmin = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int nrcli = 0;
		int nrdep = 0;
		int nrplan = 0;
		
		try {
			
			System.out.println("idcus:"+idcus);
			
			if(idcus.length()>0) {
				session.setAttribute("idcus", idcus);
				nr_customers = 1;
			}
			
			ReadProperties prop = new ReadProperties("connection.properties");
			connAdmin = Utility.getConnAdmin();
			stAdmin = connAdmin.createStatement();
			
			// ==================
			// NUMERO CLIENTI e lista clienti
			// ==================
			sql  =  "select id, description from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Esegue l'sql
			rs=stAdmin.executeQuery(sql);
			String cus_id = "";
			
			while(rs.next()) {
				if(idcus.length()==0) {
					nr_customers += 1;
				}
				cus.add(new Tables("CUS",rs.getString("id"),rs.getString("description"),"","","",""));
				cus_id = rs.getString("id");
			}
			rs.close();
			stAdmin.close();
			
			System.out.println(nr_customers);
			
			conn = Utility.getConnHR();
			st=conn.createStatement();
			
			// Numero dipendenti
			sql   = "select count(id) nrcli from hr_employee where id_customer = "+session.getAttribute("idcus")+" ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				nrcli = rs.getInt("nrcli");
			}
			rs.close();
			
			// Numero dipartimenti
			sql   = "select count(id) nrdep from hr_departments where id_customer = "+session.getAttribute("idcus")+" ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				nrdep = rs.getInt("nrdep");
			}
			rs.close();
			
			// Numero Pianificazioni Anno
			sql   = "select count(distinct(plan_year || plan_week || id_departments)) nrplan "
					+ "from hr_planification p, hr_departments d "+
					"where plan_year = '"+oggi.substring(0,4)+"' and p.id_departments = d.id "
					+ "and d.id_customer = "+session.getAttribute("idcus")+" ";	
			rs=st.executeQuery(sql);
			if(rs.next()) {
				nrplan = rs.getInt("nrplan");
			}
			rs.close();
			
			// Ultime 50 pianificazioni
			
			if(tipo.equals("S")||tipo.equals("M")||tipo.equals("D")) {
				sql   = "select count(distinct(plan_year || plan_week || id_departments)) nrplan, "+
						"plan_year, plan_week, id_departments, description "+
						"from hr_planification a, hr_departments b "+
						"where a.id_departments = b.id and plan_year = '"+oggi.substring(0,4)+"' "+
						"and a.id_departments in ("+
						"select a.id "+
						"from hr_departments a, rel_dep_employee b, "+
						"hr_employee c, rel_emp_user d "+
						"where a.id = b.id_departments and c.id = b.id_employee "+
						"and d.id_employee = c.id and b.dateend is null "+
						"and d.id_user = '"+username+"' "+
						"and a.id_customer = "+session.getAttribute("idcus")+" "+
						") "+
						"group by plan_year, plan_week, id_departments, description "+
						"order by cast(plan_week as numeric) desc "+
						"limit 10 ";
			}
			else if(tipo.equals("H")||tipo.equals("A")) {
				sql   = "select count(distinct(plan_year || plan_week || id_departments)) nrplan, "+
						"plan_year, plan_week, id_departments, description "+
						"from hr_planification a, hr_departments b "+
						"where a.id_departments = b.id and plan_year = '"+oggi.substring(0,4)+"' "+
						"and b.id_customer = "+session.getAttribute("idcus")+" "+
						"group by plan_year, plan_week, id_departments, description "+
						"order by cast(plan_week as numeric) desc "+
						"limit 10 ";
			}
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()) {
				plan.add(new Tables("PLN","","",rs.getString("plan_year"),rs.getString("plan_week"),
						rs.getString("id_departments"),rs.getString("description")));
			}
			rs.close();
			
			
			
			
			
			
			// *********
			// HEATMAP
			DateFormat timeFormat = new SimpleDateFormat("kk:mm");
			DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
			//Legge il file di properties associato alla lingua sul profilo
			ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
			
			String xAxis = "";
			String yAxis = "";
			int max = 0;
			int height = 0;
			
			
			Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        int week = cal.get(Calendar.WEEK_OF_YEAR);
			
			
			//Calendar c_heat = Calendar.getInstance(new Locale("en","US"));
			Calendar c_heat = Calendar.getInstance(Locale.GERMAN);
			
			// Set the calendar to monday of the current week
			c_heat.set(Calendar.YEAR, Integer.parseInt(currentYear));     
			c_heat.set(Calendar.WEEK_OF_YEAR, week);     
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
	 		
	 		
	 		if(tipo.equals("S")||tipo.equals("M")||tipo.equals("D")) {
	 			
	 			sql   = "SELECT max( "
		 				+ "(select count(id_employee) from hr_planification "
		 				+ "where plan_year='"+currentYear+"' and plan_week = '"+currentWeek+"' "
		 				+ "and id_departments in "
		 				+ "(" + 
		 				"select a.id " + 
		 				"from hr_departments a, rel_dep_employee b, " + 
		 				"hr_employee c, rel_emp_user d "+ 
		 				"where a.id = b.id_departments and c.id = b.id_employee " + 
		 				"and d.id_employee = c.id and b.dateend is null " + 
		 				"and d.id_user = '"+username+"' " + 
		 				"and a.id_customer = "+session.getAttribute("idcus")+" " + 
		 				")  and "
		 				+ "plan_day = (extract(isodow from ora) -1)::text "
		 				+ "and ora::time between "
		 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
		 				+ ")) valore "
		 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
		 				+ "'"+dtal+" 23:30', '0.5 hours') ora   ";
	 			
	 		}
	 		else if(tipo.equals("H")||tipo.equals("A")) {
	 			
	 			sql   = "SELECT max( "
		 				+ "(select count(id_employee) from hr_planification "
		 				+ "where plan_year='"+currentYear+"' and plan_week = '"+currentWeek+"' "
		 				+ "and id_departments in (select id from hr_departments where id_customer = "+session.getAttribute("idcus")+") and "
		 				+ "plan_day = (extract(isodow from ora) -1)::text "
		 				+ "and ora::time between "
		 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
		 				+ ")) valore "
		 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
		 				+ "'"+dtal+" 23:30', '0.5 hours') ora   ";
	 			
	 		}
	 		
	 		/*
	 		sql   = "SELECT max( "
	 				+ "(select count(id_employee) from hr_planification "
	 				+ "where plan_year='"+currentYear+"' and plan_week = '"+currentWeek+"' "
	 				+ "and id_departments in (select id from hr_departments where id_customer = "+session.getAttribute("idcus")+") and "
	 				+ "plan_day = (extract(isodow from ora) -1)::text "
	 				+ "and ora::time between "
	 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
	 				+ ")) valore "
	 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
	 				+ "'"+dtal+" 23:30', '0.5 hours') ora   ";
	 		*/
	 		System.out.println(sql);
	 		rs=st.executeQuery(sql);
	 		if(rs.next()) {
	 			max = rs.getInt("valore");
	 		}
	 		rs.close();
	 		
	 		if(tipo.equals("S")||tipo.equals("M")||tipo.equals("D")) {
	 			
	 			sql   = "SELECT ora::date giorno, ora::time ora, "
		 				+ "case when (extract(isodow from ora) -1) = 0 then 7 else (extract(isodow from ora) -1) end dow1 ,"
		 				+ "extract(isodow from ora)-1 dow, "
		 				+ "(select count(id_employee) from hr_planification "
		 				+ "where  plan_year='"+currentYear+"' and plan_week = '"+currentWeek+"' "
		 				+ "and id_departments in(" +
		 				"select a.id " + 
		 				"from hr_departments a, rel_dep_employee b, " + 
		 				"hr_employee c, rel_emp_user d "+ 
		 				"where a.id = b.id_departments and c.id = b.id_employee " + 
		 				"and d.id_employee = c.id and b.dateend is null " + 
		 				"and d.id_user = '"+username+"' " + 
		 				"and a.id_customer = "+session.getAttribute("idcus")+" " 
		 				+ ") and "
		 				+ "plan_day = (extract(isodow from ora) -1) ::text "
		 				+ "and ora::time between "
		 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
		 				+ ") valore "
		 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
		 				+ "'"+dtal+" 23:30', '0.5 hours') ora "
		 				+ "order by giorno, ora ";
	 			
	 		}
	 		else if(tipo.equals("H")||tipo.equals("A")) {
	 			
	 			sql   = "SELECT ora::date giorno, ora::time ora, "
		 				+ "case when (extract(isodow from ora) -1) = 0 then 7 else (extract(isodow from ora) -1) end dow1 ,"
		 				+ "extract(isodow from ora)-1 dow, "
		 				+ "(select count(id_employee) from hr_planification "
		 				+ "where  plan_year='"+currentYear+"' and plan_week = '"+currentWeek+"' "
		 				+ "and id_departments in(select id from hr_departments where id_customer = "+session.getAttribute("idcus")+") and "
		 				+ "plan_day = (extract(isodow from ora) -1) ::text "
		 				+ "and ora::time between "
		 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
		 				+ ") valore "
		 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
		 				+ "'"+dtal+" 23:30', '0.5 hours') ora "
		 				+ "order by giorno, ora ";
	 			
	 		}
	 		
	 		/*
	 		sql   = "SELECT ora::date giorno, ora::time ora, "
	 				+ "case when (extract(isodow from ora) -1) = 0 then 7 else (extract(isodow from ora) -1) end dow1 ,"
	 				+ "extract(isodow from ora)-1 dow, "
	 				+ "(select count(id_employee) from hr_planification "
	 				+ "where  plan_year='"+currentYear+"' and plan_week = '"+currentWeek+"' "
	 				+ "and id_departments in(select id from hr_departments where id_customer = "+session.getAttribute("idcus")+") and "
	 				+ "plan_day = (extract(isodow from ora) -1) ::text "
	 				+ "and ora::time between "
	 				+ "to_timestamp(datetime_start)::time and to_timestamp(datetime_end)::time "
	 				+ ") valore "
	 				+ "FROM generate_series('"+dtda+" 00:00'::timestamp, "
	 				+ "'"+dtal+" 23:30', '0.5 hours') ora "
	 				+ "order by giorno, ora ";
	 		*/
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
			
			stAdmin.close();
			connAdmin.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		obj.put("cdcli", cdcli);
		
		obj.put("currentYear", currentYear);
		
		obj.put("nrcli", nrcli);
		obj.put("nrdep", nrdep);
		obj.put("nrplan", nrplan);
		obj.put("plan", plan);
		obj.put("cus", cus);
		
		
		System.out.println("idcus: "+idcus);
		
		obj.put("idcus", idcus);
		obj.put("nr_customers", nr_customers);
		
		
		
		
		
		return new ModelAndView("/hr/main/index", obj);
	}
	
	private static String getDescription(Connection conn, String id_departments) throws SQLException {
		
		String description = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			st=conn.createStatement();
			sql   = "select description from hr_departments where id = '"+id_departments+"' ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				description = rs.getString("description");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if(st!=null)
			st.close();
		return description;
	}

}
