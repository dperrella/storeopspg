package pkg.counter;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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

public class WeeklyReportCounter extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables2> period = new ArrayList<Tables2>();
		
		
		
		
		List<Tables> sto = new ArrayList<Tables>();
		List<Tables2> adj = new ArrayList<Tables2>();
		period.clear();
		sto.clear();
		adj.clear();
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
				
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		PreparedStatement st2 = null;
		ResultSet rs2 = null;
		
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
		
		
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String weekstart = req.getParameter("weekstart");
		String dtda = "";
		String dtal = "";
		
		obj.put("weekstart", weekstart);
		
		Locale lc = new Locale("en","UK");
		if(weekstart.equals("M")) {
			lc = new Locale("en","UK");
		}
		if(weekstart.equals("S")) {
			lc = new Locale("en","US");
		}
		
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
				
				//nrstores = 0;
				oneStore = true;
				// Legge i devices
				sql   = "select _id_device from public.pcountergrouped_daily_device('"+iso.format(new Date())+"', '"+idstores+"') group by _id_device ";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();
				while(rs.next()) {
					//nrstores+=1;
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
			
					
    		// Get calendar set to current date and time
    		c = Calendar.getInstance(lc);
    		if(weekstart.equals("M")) {
    			c.setFirstDayOfWeek(Calendar.MONDAY);
    		}
    		if(weekstart.equals("S")) {
    			c.setFirstDayOfWeek(Calendar.SUNDAY);
    		}
    		

			// Set the calendar to monday of the current week
			c.set(Calendar.YEAR, Integer.parseInt(yearWeek));     
			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
			if(weekstart.equals("M")) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}
			if(weekstart.equals("S")) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			dtda = sdf.format(c.getTime());
			//c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			c.add(Calendar.DATE, 6);
			dtal = sdf.format(c.getTime());
			
    		String sql_col = "";
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
    		
    		// EDIT...
    		sql   = "select * from crosstab('select giorno::timestamp,_id_store,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
					+ "from public.pcountergrouped_weekly(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'' , ''"+_idstores+"'') "
    				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+" 00:00:00'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno "
    				+ "on _year = extract(year from giorno) "
    				+ "and _month = extract(month from giorno) "
    				+ "and _day = extract(day from giorno) "
    				+ "and _hour = cast(to_char(giorno, ''HH24'') as int) , ad_store s where id_store = _id_store "
    				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::timestamp order by 1,2') "
    				+ "AS final_result(giorno timestamp, "+sql_col+") ";
    		
    		
    		System.out.println(sql);
    		st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			int d = 0;
			String conteggio = "";
			
			String rott = "";
			String border = "";
			
			String riga = "";
			int countrott = 0;
			int parent = 0;
			String str = "";
			for(int i=0;i<nrstores;i++) {
				str+=""+i+",";
			}
			if(str.length()>0)
				str=str.substring(0, str.length()-1);
			
			String dd = "";
			
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
				
				
				if(!rott.equals("")) {
					if(!rott.equals(sdf.format(c.getTime()))) {
						//border = "style=\"border-top: 2px solid #c0c0c0;\"";
					}
				}
				
				
				if(!rott.equals(sdf.format(c.getTime()))) {
					
					countrott+=1;
					riga = "<tr data-id=\""+countrott+"\" data-parent=\"0\" data-level=\"1\"><td data-column=\"name\">"+giorno+"</td>";
					
					dd+=""+d+",";
					
					for(int i=0;i<nrstores;i++) {
		    			riga+= "<td><div id=\""+d+"_"+i+"\"></div></td>";
		    		}
					riga+= "</tr>";
					
					parent+=1;
				}
				
				for(int i=0;i<nrstores;i++) {
					conteggio = ""+rs.getString("S"+(i+1));
					conteggio = Utility.noNull(conteggio);
					if(conteggio.length()>0) {
						td+="<td "+border+"><input type=\"hidden\" name=\""+d+"_"+i+"\" value=\""+rs.getString("S"+(i+1))+"\"/>"+rs.getString("S"+(i+1))+"</td>";
					}
				}
				
				period.add(new Tables2("WEEK",giorno,sdf.format(c.getTime()),
						td,border,timeFormat.format(rs.getTime("giorno")),""+d, 
						riga,""+countrott,""+parent,str,"",""));
				
				
				
				td = "";
				border = "";
				riga = "";
				
				rott = sdf.format(c.getTime());
				
			}
			rs.close();
			st.close();
			
			obj.put("str", str);
			if(dd.length()>0)
				dd=dd.substring(0, dd.length()-1);
			obj.put("dd", dd);
			
			obj.put("dtda", dtda);
			obj.put("dtal", dtal);
            
			
            	
			//-----
			
    		int diff = Utility.getDateDiffDays2(dtda, dtal);
    		sql_col = "";
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
    		
    		/*
    		sql   = "select * from crosstab('select a.id,giorno, round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
    				+ "from (select extract(hour from giorno) giorno,stores.id from generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+" 00:00:00'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+" 23:00:00'', ''1 hour''::interval) giorno,(select id from stores where id in ("+idstores+")) stores) a "
    				+ "left join public.pcountergrouped_weekly(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''"+_idstores+"'') "
    				+ "on a.id = _id_store "
    				+ "and _hour = giorno , ad_store s where id_store = a.id "
    				+ "group by a.id,adjustment_factor,giorno order by a.id,giorno') "
    				+ "AS final_result(store integer, "+sql_col+" ) ";	
    		*/
    		sql   = "select * from crosstab('select _id_store,giorno::date,round(coalesce(sum(_inc),null,0)  + ((coalesce(sum(_inc),null,0)*adjustment_factor::numeric)/100)) inc "
    				+ "from public.pcountergrouped_period(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'',''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'',''"+_idstores+"'') "
    				+ "left join generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno "
    				+ "on _year = extract(year from giorno) "
    				+ "and _month = extract(month from giorno) "
    				+ "and _day = extract(day from giorno) , ad_store s where id_store = _id_store "
    				+ "group by _id_store,adjustment_factor,_year,_month,_day,_description,_stat,giorno::date order by 1,2') "
    				+ "AS final_result(store integer, "+sql_col+" )";	
    		
    		
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
				/*
				if(oneStore) {
					descr = getDeviceDescr(conn,rs.getInt("store"));
				}
				else {
					descr = getDescription(conn,rs.getInt("store"));
				}
				*/
				descr = getDescription(conn,rs.getInt("store"));
				
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
			
			
			
			// -----------------
			// HEATMAP
			
			// apertura chiusura
			sql   = "select extract(hour from min(fromhour)) min , extract(hour from max(tohour)) max from dailyoperation_stores \r\n" + 
					"		where id_stores in ("+idstores+") ";
			System.out.println("---");
			//System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			String hhmin = "";
			String hhmax = "";
			
			int hhmin2 = 0;
			int hhmax2 = 0;
			if(rs.next()) {
				hhmin = "00"+rs.getString("min");
				hhmin = hhmin.substring(hhmin.length()-2, hhmin.length())+":00:00";
				hhmin2 = rs.getInt("min");
				
				hhmax = "00"+rs.getString("max");
				hhmax = hhmax.substring(hhmax.length()-2, hhmax.length())+":00:00";
				hhmax2 = rs.getInt("max");
				
			}
			rs.close();
			
			System.out.println(hhmin+" - "+hhmax);
			
			String ora = "";
			String heatmap = "";
			
			c = Calendar.getInstance();

			// Set the calendar to monday of the current week
			c.set(Integer.parseInt(dtda.substring(6,10)), Integer.parseInt(dtda.substring(3,5))-1, Integer.parseInt(dtda.substring(0,2)),0,0,0);     
			c.add(Calendar.HOUR_OF_DAY, 2);

			List l = new ArrayList();
			
			
			
			
			sql   = "select extract(dow from giorno::date) giorno, giorno::date, giorno::time ora,round(coalesce(sum(_inc),null,0)  + \r\n" + 
					"((coalesce(sum(_inc),null,0)*max(adjustment_factor::numeric))/100)) inc \r\n" + 
					"from public.pcountergrouped_weekly('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"','"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"','"+_idstores+"') "+
					"left join generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+" 00:00:00', \r\n" + 
					"'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+" 23:00:00', '1 hour'::interval) giorno \r\n" + 
					"on _year = extract(year from giorno) and _month = extract(month from giorno) \r\n" + 
					"and _day = extract(day from giorno) and _hour = cast(to_char(giorno, 'HH24') as int) , \r\n" + 
					"ad_store s where id_store = _id_store \r\n" + 
					"group by _year,_month,_day,_stat, extract(dow from giorno::date),giorno::date,giorno::time order by 2,3,4";
			System.out.println("---");
			System.out.println(sql);
			
			
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			String val = "";
			
			String data1 = "";
			
			while(rs.next()) {
				val=""+rs.getString("inc");
				val=Utility.noNull(val);
				
				if(val.length()==0)
					val = "'-'";
				
				switch (rs.getInt("giorno")) {
	         	case 0: 
	         		/*
	         		if(weekstart.equals("M"))
	         			data1 = propLang.get("lun");
	         		else
	         			data1 = propLang.get("dom");
	         		*/
	         		data1 = propLang.get("dom");
	         		
	         		break;
	         	case 1: 
	         		/*
	         		if(weekstart.equals("M"))
	         			data1 = propLang.get("mar");
	         		else
	         			data1 = propLang.get("lun");
	         		*/
	         		data1 = propLang.get("lun");
	         		break;
	         	case 2: 
	         		/*
	         		if(weekstart.equals("M"))
	         			data1 = propLang.get("mer");
	         		else
	         			data1 = propLang.get("mar");
	         		*/
	         		data1 = propLang.get("mar");
	         		break;
	         	case 3: 
	         		/*
	         		if(weekstart.equals("M"))
	         			data1 = propLang.get("gio");
	         		else
	         			data1 = propLang.get("mer");
	         		*/
	         		data1 = propLang.get("mer");
	         		break;
	         	case 4: 
	         		/*
	         		if(weekstart.equals("M"))
	         			data1 = propLang.get("ven");
	         		else
	         			data1 = propLang.get("gio");
	         		*/
	         		data1 = propLang.get("gio");
	         		break;
	         	case 5: 
	         		/*
	         		if(weekstart.equals("M"))
	         			data1 = propLang.get("sab");
	         		else
	         			data1 = propLang.get("ven");
	         		*/
	         		data1 = propLang.get("ven");
	         		break;
	         	case 6: 
	         		/*
	         		if(weekstart.equals("M"))
	         			data1 = propLang.get("dom");
	         		else
	         			data1 = propLang.get("sab");
	         		*/
	         		data1 = propLang.get("sab");
	         		break;
				}
				
				l.add(Integer.parseInt(val));
				
				ora = timeFormat.format(rs.getTime("ora")).replaceAll(":", "");
				heatmap+="['"+timeFormat.format(rs.getTime("ora")).replaceAll(":00", "")+"','"+data1+"',"+val+"],";

				
				
				
			}
			rs.close();
			
			if(heatmap.length()>0)
				heatmap=heatmap.substring(0,heatmap.length()-1);
			
			int max = (int) Collections.max(l);
			
			//String xAxis = "'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'";
			String xAxis = "";
			for(int i=hhmin2;i<=hhmax2;i++) {
				String hh = "00"+i;
				hh=hh.substring(hh.length()-2,hh.length());
				xAxis+="'"+hh+"',";
			}
			if(xAxis.length()>0)
				xAxis=xAxis.substring(0, xAxis.length()-1);
			
			xAxis = "'00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23'";
	 		
			//"'24','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23'";
	 		
			String yAxis = "";
	 		
	 		if(weekstart.equals("M"))
	 			//yAxis = "'Sunday','Saturday','Friday','Thursday','Wednesday','Tuesday','Monday'";
	 			yAxis = "'"+propLang.get("dom")+"','"+propLang.get("sab")+"','"+propLang.get("ven")+"','"+propLang.get("gio")+"','"+propLang.get("mer")+"','"+propLang.get("mar")+"','"+propLang.get("lun")+"'";
	 		
	 		else
	 			//yAxis = "'Saturday','Friday','Thursday','Wednesday','Tuesday','Monday','Sunday'";
	 			yAxis = "'"+propLang.get("sab")+"','"+propLang.get("ven")+"','"+propLang.get("gio")+"','"+propLang.get("mer")+"','"+propLang.get("mar")+"','"+propLang.get("lun")+"','"+propLang.get("dom")+"'";
	 		
	 		obj.put("heatmap", heatmap);
			obj.put("max", max);
			obj.put("xAxis", xAxis);
			obj.put("yAxis", yAxis);
			
			if(donut.length()>0)
				donut = donut.substring(0,donut.length()-1);
			
			obj.put("donut", donut);
			obj.put("th", th);
				
        	
        	conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Report 1-1");
		obj.put("msg", msg);
		
		// parametri passati dalla maschera
		obj.put("nrstores", nrstores);
		obj.put("idstores", idstores);
		obj.put("_idstores", _idstores);
		
		// Periodo analizzato (con dati)
		obj.put("period", period);
		// Stores per Charts
		obj.put("sto", sto);
		
		return new ModelAndView("/counter/report/weekly-report-counter", obj);
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
