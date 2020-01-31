package pkg.counter;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReportCounter extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> period = new ArrayList<Tables>();
		List<Tables> sto = new ArrayList<Tables>();
		List<Tables2> adj = new ArrayList<Tables2>();
		period.clear();
		sto.clear();
		adj.clear();
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
				
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String msg = "";
		
		String tipo = req.getParameter("tipo");
		obj.put("tipo", tipo);
		
		String idstores = "";
		String[] itemsStores = req.getParameterValues("id_store");
		String _idstores = "";
		
		int nrstores = 0;
		if(itemsStores==null){
			System.out.println("NULL - STORES");
		}
		else {
			System.out.println("NO NULL - STORES");
			for(int i = 0; i < itemsStores.length; i++){
				nrstores+=1;
			    idstores+=""+itemsStores[i]+",";
			    _idstores+=""+itemsStores[i]+"*";
			}
		}
		if(idstores.length()>0)
			idstores = idstores.substring(0,idstores.length()-1);
		
		if(_idstores.length()>0)
			_idstores = _idstores.substring(0,_idstores.length()-1);
		
		System.out.println(_idstores);
		
		boolean oneStore = false;
		String iddevices = "";
		
		
		String day = req.getParameter("day");
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String yearMonth = req.getParameter("yearMonth");
		String month = req.getParameter("month");
		String dtda = req.getParameter("dtda");
		String dtal = req.getParameter("dtal");
		
		try {
			// Apre la connessione
			conn = Utility.getConnCounter();
			
        	// Stores
			String th = "";
			
			
			String $xcdtb = "";
			String $xcdel = "";
			String $xdtab = "";
			
			String giorno = "";
			Calendar c = null;
			String td = "";
			
			int af = 0;
			
			int nrdev = 0;
			String descrStore = "";
			if(nrstores==1) {
				
				
				descrStore = getDescription(conn, Integer.parseInt(idstores));
				obj.put("descrStore", " - Store: "+descrStore);
				
				nrstores = 0;
				oneStore = true;
				// Legge i devices
				sql   = "select _id_device from public.pcountergrouped_daily_device('"+iso.format(new Date())+"', '"+idstores+"') group by _id_device ";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();
				while(rs.next()) {
					nrstores+=1;
					nrdev+=1;
					iddevices+=rs.getString("_id_device")+",";
				}
				rs.close();
				
				// numero devices
				obj.put("nrdev", nrdev);
				
				if(iddevices.length()>0) {
					iddevices=iddevices.substring(0,iddevices.length()-1);
				
					sql = "select adjustment_factor from devices where id in("+iddevices+") group by adjustment_factor";
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs=st.executeQuery();
					if(rs.next()) {
						af = rs.getInt("adjustment_factor");
					}
					rs.close();
				}
				else {
					iddevices = "0";
				}
					
				
			}
			else {
				
				// numero devices
				obj.put("nrdev", 9999);
				
				sql = "select adjustment_factor from ad_store where id_store in("+idstores+") group by adjustment_factor";
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();
				if(rs.next()) {
					af = rs.getInt("adjustment_factor");
				}
				rs.close();
				
			}
			
			obj.put("totperc", 100/nrstores);
			
			// Orari apertura negozi
			sql   = "SELECT * FROM crosstab('select ''(''||rpad(store_code,4,'' '')||'') ''||rpad(description,30,'' '')||'' "+
					"| Adj.Factor : ''||adjustment_factor negozio,days::text giorno, fromhour::text||'' - ''||tohour::text orario "+
					"from dailyoperation_stores join stores on id_stores = stores.id join devices on stores.id = id_store "+
					"where id_stores in ("+idstores+") order by 1,2') "+
					"AS ct (\"negozio\" text,\"lun\" text, \"mar\" text, \"mer\" text, \"gio\" text, \"ven\" text, \"sab\" text, \"dom\" text)";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				adj.add(new Tables2("ADJ",rs.getString("negozio"),"",
						rs.getString("lun"),rs.getString("mar"),rs.getString("mer"),rs.getString("gio"),
						rs.getString("ven"),rs.getString("sab"),rs.getString("dom"),"","",""));
			}
			rs.close();
			obj.put("adj", adj);
			
			switch (tipo) {
            	case "D":  
            		
            		String sql_col = "";
            		for(int i=0;i<nrstores;i++) {
            			sql_col+="S"+(i+1)+" NUMERIC,";
            		}
            		if(sql_col.length()>0)
            			sql_col = sql_col.substring(0,sql_col.length()-1);
            		
            		if(oneStore) {
            			/*
            			sql   = "select * from crosstab('select giorno::time,_id_device,coalesce(sum(_inc),null,0) inc from public.pcountergrouped_daily_device(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) "
	            				+ "and _hour = cast(to_char(giorno, ''HH24'') as int) "
	            				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::time order by 1,2') "
	            				+ "AS final_result(giorno time, "+sql_col+") ";
            			*/
            			
            			sql   = "select * from crosstab('select giorno::time,_id_device,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc  "
            					+ "from public.pcountergrouped_daily_device(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) "
	            				+ "and _hour = cast(to_char(giorno, ''HH24'') as int) "
	            				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::time order by 1,2') "
	            				+ "AS final_result(giorno time, "+sql_col+") ";
            			
            			
            			
            			/*
            			sql   = "select * from crosstab('select giorno::time,_id_device, round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc  "
            					+ "from public.pcountergrouped_daily_device(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) "
	            				+ "and _hour = cast(to_char(giorno, ''HH24'') as int) , devices d "
	            				+ "where _id_device = d.id "
	            				+ "group by _id_device, adjustment_factor,_year,_month,_day,_description,_stat,giorno::time order by 1,2') "
	            				+ "AS final_result(giorno time, "+sql_col+") ";
            			*/
            			
	            		
            		}
            		else {
            			sql   = "select * from crosstab('select giorno::time,_id_store,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
            					+ "from public.pcountergrouped_daily(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) "
	            				+ "and _hour = cast(to_char(giorno, ''HH24'') as int) , ad_store s where id_store = _id_store "
	            				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::time order by 1,2') "
	            				+ "AS final_result(giorno time, "+sql_col+") ";
	            	}
            		System.out.println(sql);
            		st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
    				int d = 0;
    				String conteggio = "";
    				while(rs.next()) {
    					
    					for(int i=0;i<nrstores;i++) {
    						conteggio = ""+rs.getString("S"+(i+1));
    						conteggio = Utility.noNull(conteggio);
    						if(conteggio.length()>0)
    							td+="<td>"+rs.getString("S"+(i+1))+"</td>";
                		}
    					
    					period.add(new Tables("DAY",rs.getString("giorno"),rs.getString("giorno"),td,"","",""+d));
        				
        				td = "";
    					
    				}
    				rs.close();
    				st.close();
            		
            	break;
            	
            	case "W": 
            		
            		// Get calendar set to current date and time
        			c = Calendar.getInstance(new Locale("en","UK"));

        			// Set the calendar to monday of the current week
        			c.set(Calendar.YEAR, Integer.parseInt(yearWeek));     
        			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
        			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        			dtda = sdf.format(c.getTime());
        			//c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        			c.add(Calendar.DATE, 6);
        			dtal = sdf.format(c.getTime());
        			
            		sql_col = "";
            		for(int i=0;i<nrstores;i++) {
            			sql_col+="S"+(i+1)+" NUMERIC,";
            		}
            		if(sql_col.length()>0)
            			sql_col = sql_col.substring(0,sql_col.length()-1);
            		
            		if(oneStore) {
            			sql   = "select * from crosstab('select giorno::date,_id_device,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc "
	            				+ "from public.pcountergrouped_period_device(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) "
	            				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	            				+ "AS final_result(giorno date, "+sql_col+" )";	
            		}
            		else {
	            		sql   = "select * from crosstab('select giorno::date,_id_store,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
	            				+ "from public.pcountergrouped_period(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) , ad_store s where id_store = _id_store "
	            				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	            				+ "AS final_result(giorno date, "+sql_col+" )";	
            		}
            		System.out.println(sql);
            		st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
    				d = 0;
    				while(rs.next()) {
    					
    					c = Calendar.getInstance();

            			// Set the calendar to monday of the current week
            			c.setTime(rs.getDate("giorno"));
            			d = c.get(Calendar.DAY_OF_WEEK);
                		if(d==2) 
                			giorno = propLang.get("lun")+" "+sdf.format(c.getTime());
                		if(d==3)
        					giorno = propLang.get("mar")+" "+sdf.format(c.getTime());
        				if(d==4)
        					giorno = propLang.get("mer")+" "+sdf.format(c.getTime());
        				if(d==5)
        					giorno = propLang.get("gio")+" "+sdf.format(c.getTime());
        				if(d==6)
        					giorno = propLang.get("ven")+" "+sdf.format(c.getTime());
        				if(d==7)
        					giorno = propLang.get("sab")+" "+sdf.format(c.getTime());
        				if(d==1) 
        					giorno = propLang.get("dom")+" "+sdf.format(c.getTime());
        				
    					for(int i=0;i<nrstores;i++) {
    						conteggio = ""+rs.getString("S"+(i+1));
    						conteggio = Utility.noNull(conteggio);
    						if(conteggio.length()>0)
    							td+="<td>"+rs.getString("S"+(i+1))+"</td>";
                		}
    					
    					period.add(new Tables("WEEK",giorno,sdf.format(c.getTime()),td,"","",""+d));
        				
        				td = "";
    					
    				}
    				rs.close();
    				st.close();
            		
            	break;
            	
            	case "M": 
            		
            		c = Calendar.getInstance();

        			// Set the calendar to monday of the current week
        			c.set(Integer.parseInt(yearMonth), Integer.parseInt(month)-1, 1);     
        			
        			dtda = "01/"+month+"/"+yearMonth;
                	dtal = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+month+"/"+yearMonth;

            		sql_col = "";
            		for(int i=0;i<nrstores;i++) {
            			sql_col+="S"+(i+1)+" NUMERIC,";
            		}
            		if(sql_col.length()>0)
            			sql_col = sql_col.substring(0,sql_col.length()-1);
            		
            		if(oneStore) {
            			sql   = "select * from crosstab('select giorno::date,_id_device,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc "
	            				+ "from public.pcountergrouped_period_device(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) "
	            				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	            				+ "AS final_result(giorno date, "+sql_col+" )";	
            		}
            		else {
	            		sql   = "select * from crosstab('select giorno::date,_id_store,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
	            				+ "from public.pcountergrouped_period(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) , ad_store s where id_store = _id_store "
	            				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	            				+ "AS final_result(giorno date, "+sql_col+" )";	
            		}
            		System.out.println(sql);
            		st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
    				d = 0;
    				while(rs.next()) {
    					
    					c = Calendar.getInstance();

            			// Set the calendar to monday of the current week
            			c.setTime(rs.getDate("giorno"));
            			d = c.get(Calendar.DAY_OF_WEEK);
                		if(d==2) 
                			giorno = propLang.get("lun")+" "+sdf.format(c.getTime());
                		if(d==3)
        					giorno = propLang.get("mar")+" "+sdf.format(c.getTime());
        				if(d==4)
        					giorno = propLang.get("mer")+" "+sdf.format(c.getTime());
        				if(d==5)
        					giorno = propLang.get("gio")+" "+sdf.format(c.getTime());
        				if(d==6)
        					giorno = propLang.get("ven")+" "+sdf.format(c.getTime());
        				if(d==7)
        					giorno = propLang.get("sab")+" "+sdf.format(c.getTime());
        				if(d==1) 
        					giorno = propLang.get("dom")+" "+sdf.format(c.getTime());
        				
    					for(int i=0;i<nrstores;i++) {
    						conteggio = ""+rs.getString("S"+(i+1));
    						conteggio = Utility.noNull(conteggio);
    						if(conteggio.length()>0)
    							td+="<td>"+rs.getString("S"+(i+1))+"</td>";
                		}
    					
    					period.add(new Tables("MONTH",giorno,sdf.format(c.getTime()),td,"","",""+d));
        				
        				td = "";
    					
    				}
    				rs.close();
    				st.close();
            		
            	break;
            	
            	case "P": 
            		
            		sql_col = "";
            		for(int i=0;i<nrstores;i++) {
            			sql_col+="S"+(i+1)+" NUMERIC,";
            		}
            		if(sql_col.length()>0)
            			sql_col = sql_col.substring(0,sql_col.length()-1);
            		
            		if(oneStore) {
            			sql   = "select * from crosstab('select giorno::date,_id_device,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc "
	            				+ "from public.pcountergrouped_period_device(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) "
	            				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	            				+ "AS final_result(giorno date, "+sql_col+" )";	
            		}
            		else {
	            		sql   = "select * from crosstab('select giorno::date,_id_store,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
	            				+ "from public.pcountergrouped_period(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	            				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	            				+ "on _year = extract(year from giorno) "
	            				+ "and _month = extract(month from giorno) "
	            				+ "and _day = extract(day from giorno) , ad_store s where id_store = _id_store "
	            				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	            				+ "AS final_result(giorno date, "+sql_col+" )";	
            		
            		}
            		System.out.println(sql);
            		st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
    				d = 0;
    				while(rs.next()) {
    					
    					c = Calendar.getInstance();

            			// Set the calendar to monday of the current week
            			c.setTime(rs.getDate("giorno"));
            			d = c.get(Calendar.DAY_OF_WEEK);
                		if(d==2) 
                			giorno = propLang.get("lun")+" "+sdf.format(c.getTime());
                		if(d==3)
        					giorno = propLang.get("mar")+" "+sdf.format(c.getTime());
        				if(d==4)
        					giorno = propLang.get("mer")+" "+sdf.format(c.getTime());
        				if(d==5)
        					giorno = propLang.get("gio")+" "+sdf.format(c.getTime());
        				if(d==6)
        					giorno = propLang.get("ven")+" "+sdf.format(c.getTime());
        				if(d==7)
        					giorno = propLang.get("sab")+" "+sdf.format(c.getTime());
        				if(d==1) 
        					giorno = propLang.get("dom")+" "+sdf.format(c.getTime());
        				
    					for(int i=0;i<nrstores;i++) {
    						conteggio = ""+rs.getString("S"+(i+1));
    						conteggio = Utility.noNull(conteggio);
    						if(conteggio.length()>0)
    							td+="<td>"+rs.getString("S"+(i+1))+"</td>";
                		}
    					
    					period.add(new Tables("PERIOD",giorno,sdf.format(c.getTime()),td,"","",""+d));
        				
        				td = "";
    					
    				}
    				rs.close();
    				st.close();
            		
            	break;
            	
			}
			
			// Per grafico
        	if(tipo.equals("D")) {
        		
        		int diff = 23;
        		String sql_col = "";
        		for(int i=0;i<=diff;i++) {
        			sql_col+="D"+(i+1)+" NUMERIC,";
        		}
        		if(sql_col.length()>0)
        			sql_col = sql_col.substring(0,sql_col.length()-1);
        		
        		if(oneStore) {
        			
        			/*
        			sql   = "select * from crosstab('select a.id,giorno, coalesce(sum(_inc),null,0) inc "
	        				+ "from (select extract(hour from giorno) giorno,devices.id from generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno,(select id from devices where id in ("+iddevices+")) devices) a "
	        				+ "left join public.pcountergrouped_daily_device(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "on a.id = _id_device "
	        				+ "and _hour = giorno "
	        				+ "group by a.id,giorno order by a.id,giorno') "
	        				+ "AS final_result(store integer, "+sql_col+" ) ";	
	        		*/
        			
        			sql   = "select * from crosstab('select a.id,giorno, round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc  "
	        				+ "from (select extract(hour from giorno) giorno,devices.id from generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno,(select id from devices where id in ("+iddevices+")) devices) a "
	        				+ "left join public.pcountergrouped_daily_device(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "on a.id = _id_device "
	        				+ "and _hour = giorno "
	        				+ "group by a.id,giorno order by a.id,giorno') "
	        				+ "AS final_result(store integer, "+sql_col+" ) ";	
        			
        			/*
        			sql   = "select * from crosstab('select a.id,giorno, round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc  "
	        				+ "from (select extract(hour from giorno) giorno,devices.id from generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno,"
	        						+ "(select id from devices where id in ("+iddevices+")) devices) a "
	        				+ "left join public.pcountergrouped_daily_device(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "on a.id = _id_device "
	        				+ "and _hour = giorno "
	        				+ "group by a.id,giorno order by a.id,giorno') "
	        				+ "AS final_result(store integer, "+sql_col+" ) ";	
	        		*/
        				
        			
        		}
        		else {
	        		sql   = "select * from crosstab('select a.id,giorno, round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
	        				+ "from (select extract(hour from giorno) giorno,stores.id from generate_series(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00'', ''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno,(select id from stores where id in ("+idstores+")) stores) a "
	        				+ "left join public.pcountergrouped_daily(''"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "on a.id = _id_store "
	        				+ "and _hour = giorno , ad_store s where id_store = a.id "
	        				+ "group by a.id,adjustment_factor,giorno order by a.id,giorno') "
	        				+ "AS final_result(store integer, "+sql_col+" ) ";	
        		}
        		System.out.println(sql);
        		st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String store = "";
				String dati = "";
				int tot = 0;
				String donut = "";
				
				while(rs.next()) {
						    			
					for(int i=0;i<=diff;i++) {
						
						tot+=rs.getInt("D"+(i+1));
						dati+="["+i+", "+rs.getInt("D"+(i+1))+"],";
						
					}
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					String descr = "";
					if(oneStore) {
						descr = getDeviceDescr(conn,rs.getInt("store"));
					}
					else {
						descr = getDescription(conn,rs.getInt("store"));
					}
					store="var s"+rs.getString("store")+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",rs.getString("store"),store,""+tot,descr,"",""));
					donut+="{label: \""+descr+"\",data: "+tot+"},";
					
					// ----
					if(descr.length()>0)
						th+="<th class=\"b-r\">"+descr+"</th>";
					// ----
					
					dati = "";
					store = "";
					tot = 0;
				}
				rs.close();
				st.close();
        		
				if(donut.length()>0)
					donut = donut.substring(0,donut.length()-1);
				
				obj.put("donut", donut);
				obj.put("th", th);
        		
        	}
        	
        	if(tipo.equals("W")) {
        		
        		int diff = Utility.getDateDiffDays2(dtda, dtal);
        		String sql_col = "";
        		for(int i=0;i<=diff;i++) {
        			sql_col+="D"+(i+1)+" NUMERIC,";
        		}
        		if(sql_col.length()>0)
        			sql_col = sql_col.substring(0,sql_col.length()-1);
        		
        		if(oneStore) {
        			sql   = "select * from crosstab('select _id_device,giorno::date,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc "
	        				+ "from public.pcountergrouped_period_device(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	        				+ "on _year = extract(year from giorno) "
	        				+ "and _month = extract(month from giorno) "
	        				+ "and _day = extract(day from giorno) "
	        				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	        				+ "AS final_result(store integer, "+sql_col+" )";	
        		}
        		else {
        			
	        		sql   = "select * from crosstab('select _id_store,giorno::date,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
	        				+ "from public.pcountergrouped_period(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	        				+ "on _year = extract(year from giorno) "
	        				+ "and _month = extract(month from giorno) "
	        				+ "and _day = extract(day from giorno) , ad_store s where id_store = _id_store "
	        				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	        				+ "AS final_result(store integer, "+sql_col+" )";	
        		}
        		System.out.println(sql);
        		st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String store = "";
				String dati = "";
				int tot = 0;
				String donut = "";
				
				while(rs.next()) {
					
					c = Calendar.getInstance();

	    			// Set the calendar to monday of the current week
	    			c.set(Integer.parseInt(dtda.substring(6,10)), Integer.parseInt(dtda.substring(3,5))-1, Integer.parseInt(dtda.substring(0,2)),0,0,0);     
	    			c.add(Calendar.HOUR_OF_DAY, 2);
					
					for(int i=0;i<=diff;i++) {
						
						tot+=rs.getInt("D"+(i+1));
						dati+="["+c.getTimeInMillis()+", "+rs.getInt("D"+(i+1))+"],";
						
						c.add(Calendar.DATE, 1);
					}
					
					System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8));
					
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					String descr = "";
					if(oneStore) {
						descr = getDeviceDescr(conn,rs.getInt("store"));
					}
					else {
						descr = getDescription(conn,rs.getInt("store"));
					}
					store="var s"+rs.getString("store")+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",rs.getString("store"),store,""+tot,descr,"",""));
					donut+="{label: \""+descr+"\",data: "+tot+"},";
					
					// ----
					if(descr.length()>0)
						th+="<th class=\"b-r\">"+descr+"</th>";
					// ----
					
					dati = "";
					store = "";
					tot = 0;
				}
				rs.close();
				st.close();
        		
				if(donut.length()>0)
					donut = donut.substring(0,donut.length()-1);
				
				obj.put("donut", donut);
				obj.put("th", th);
				
        	}
        	
        	if(tipo.equals("M")) {
        		
        		int diff = Utility.getDateDiffDays2(dtda, dtal);
        		String sql_col = "";
        		for(int i=0;i<=diff;i++) {
        			sql_col+="D"+(i+1)+" NUMERIC,";
        		}
        		if(sql_col.length()>0)
        			sql_col = sql_col.substring(0,sql_col.length()-1);
        		
        		if(oneStore) {
        			sql   = "select * from crosstab('select _id_device,giorno::date,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc "
	        				+ "from public.pcountergrouped_period_device(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	        				+ "on _year = extract(year from giorno) "
	        				+ "and _month = extract(month from giorno) "
	        				+ "and _day = extract(day from giorno) "
	        				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	        				+ "AS final_result(store integer, "+sql_col+" )";	
        		}
        		else {
        			sql   = "select * from crosstab('select _id_store,giorno::date,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
	        				+ "from public.pcountergrouped_period(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	        				+ "on _year = extract(year from giorno) "
	        				+ "and _month = extract(month from giorno) "
	        				+ "and _day = extract(day from giorno) , ad_store s where id_store = _id_store "
	        				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	        				+ "AS final_result(store integer, "+sql_col+" )";	
        		}
        		System.out.println(sql);
        		st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String store = "";
				String dati = "";
				int tot = 0;
				String donut = "";
				
				while(rs.next()) {
					
					c = Calendar.getInstance();

	    			// Set the calendar to monday of the current week
	    			c.set(Integer.parseInt(dtda.substring(6,10)), Integer.parseInt(dtda.substring(3,5))-1, Integer.parseInt(dtda.substring(0,2)),0,0,0);     
	    			c.add(Calendar.HOUR_OF_DAY, 2);
					
					for(int i=0;i<=diff;i++) {
						
						tot+=rs.getInt("D"+(i+1));
						dati+="["+c.getTimeInMillis()+", "+rs.getInt("D"+(i+1))+"],";
						
						c.add(Calendar.DATE, 1);
					}
					
					System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8));
					
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					String descr = "";
					if(oneStore) {
						descr = getDeviceDescr(conn,rs.getInt("store"));
					}
					else {
						descr = getDescription(conn,rs.getInt("store"));
					}
					store="var s"+rs.getString("store")+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",rs.getString("store"),store,""+tot,descr,"",""));
					donut+="{label: \""+descr+"\",data: "+tot+"},";
					
					// ----
					if(descr.length()>0)
						th+="<th class=\"b-r\">"+descr+"</th>";
					// ----
					
					dati = "";
					store = "";
					tot = 0;
				}
				rs.close();
				st.close();
        		
				if(donut.length()>0)
					donut = donut.substring(0,donut.length()-1);
				
				obj.put("donut", donut);
				obj.put("th", th);
				
        	}
        	
        	if(tipo.equals("P")) {
        		
        		int diff = Utility.getDateDiffDays(dtda, dtal);
        		String sql_col = "";
        		for(int i=0;i<=diff;i++) {
        			sql_col+="D"+(i+1)+" NUMERIC,";
        		}
        		if(sql_col.length()>0)
        			sql_col = sql_col.substring(0,sql_col.length()-1);
        		
        		if(oneStore) {
        			sql   = "select * from crosstab('select _id_device,giorno::date,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*"+af+")/100)) inc "
	        				+ "from public.pcountergrouped_period_device(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	        				+ "on _year = extract(year from giorno) "
	        				+ "and _month = extract(month from giorno) "
	        				+ "and _day = extract(day from giorno) "
	        				+ "group by _id_device,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	        				+ "AS final_result(store integer, "+sql_col+" )";	
        		}
        		else {
	        		sql   = "select * from crosstab('select _id_store,giorno::date,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
	        				+ "from public.pcountergrouped_period(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
	        				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
	        				+ "on _year = extract(year from giorno) "
	        				+ "and _month = extract(month from giorno) "
	        				+ "and _day = extract(day from giorno) , ad_store s where id_store = _id_store "
	        				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
	        				+ "AS final_result(store integer, "+sql_col+" )";	
        		}
        		System.out.println(sql);
        		st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String store = "";
				String dati = "";
				int tot = 0;
				String donut = "";
				
				while(rs.next()) {
					
					c = Calendar.getInstance();

	    			// Set the calendar to monday of the current week
	    			c.set(Integer.parseInt(dtda.substring(6,10)), Integer.parseInt(dtda.substring(3,5))-1, Integer.parseInt(dtda.substring(0,2)),0,0,0);     
	    			c.add(Calendar.HOUR_OF_DAY, 2);
					
					for(int i=0;i<=diff;i++) {
						
						tot+=rs.getInt("D"+(i+1));
						dati+="["+c.getTimeInMillis()+", "+rs.getInt("D"+(i+1))+"],";
						
						c.add(Calendar.DATE, 1);
					}
					
					//System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8));
					
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					String descr = "";
					if(oneStore) {
						descr = getDeviceDescr(conn,rs.getInt("store"));
					}
					else {
						descr = getDescription(conn,rs.getInt("store"));
					}
					store="var s"+rs.getString("store")+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",rs.getString("store"),store,""+tot,descr,"",""));
					donut+="{label: \""+descr+"\",data: "+tot+"},";
					
					// ----
					if(descr.length()>0)
						th+="<th class=\"b-r\">"+descr+"</th>";
					// ----
					
					dati = "";
					store = "";
					tot = 0;
				}
				rs.close();
				st.close();
        		
				if(donut.length()>0)
					donut = donut.substring(0,donut.length()-1);

				obj.put("donut", donut);
				obj.put("th", th);
				
        	}
        	
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Report Counter");
		
		obj.put("dtda", dtda);
		obj.put("dtal", dtal);
		
		obj.put("msg", msg);
		
		// parametri passati dalla maschera
		obj.put("nrstores", nrstores);
		obj.put("idstores", idstores);
		obj.put("_idstores", _idstores);
		
		// Periodo analizzato (con dati)
		obj.put("period", period);
		// Stores per Charts
		obj.put("sto", sto);
		
		return new ModelAndView("/counter/report/report-counter", obj);
	}
	
	private static String getDescription(Connection conn, int id_store) {
		
		String description = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql   = "select description from stores where id = ? ";
			st = conn.prepareStatement(sql);
			
			st.setInt(1, id_store);
			rs=st.executeQuery();
			if(rs.next()) {
				description = rs.getString("description");
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
		
		return description;
		
	}
	
	private static String getDeviceDescr(Connection conn, int id_device) {
		
		String description = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			System.out.println(id_device);
			sql   = "select s.description, doorid, devicename from devices d, stores s where d.id_store = s.id and d.id = ? ";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			
			st.setInt(1, id_device);
			rs=st.executeQuery();
			if(rs.next()) {
				
				String devicename = ""+rs.getString("devicename");
				devicename=Utility.noNull(devicename);
				if(devicename.length()==0) {
					description = rs.getString("description")+" "; //("+rs.getString("doorid")+") ";
				}
				else {
					description = rs.getString("devicename")+" "; //("+rs.getString("doorid")+") ";
				}
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
		
		return description;
		
	}
	
}
