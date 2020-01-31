package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

public class Report1_1 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> period = new ArrayList<Tables>();
		List<Tables> sto = new ArrayList<Tables>();
		period.clear();
		sto.clear();
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String msg = "";
		
		String id_report = req.getParameter("id_report");
		String code = req.getParameter("code");
		String description = req.getParameter("description");
		String alarm1 = req.getParameter("alarm1");
		String alarm2 = req.getParameter("alarm2");
		String alarm1_descr = req.getParameter("alarm1_descr");
		String alarm2_descr = req.getParameter("alarm2_descr");
		String tipo = req.getParameter("tipo");
		obj.put("tipo", tipo);
		
		String idstores = "";
		String[] itemsStores = req.getParameterValues("id_store");
		
		int nrstores = 0;
		if(itemsStores==null){
			System.out.println("NULL - STORES");
		}
		else {
			System.out.println("NO NULL - STORES");
			for(int i = 0; i < itemsStores.length; i++){
				nrstores+=1;
			    idstores+=""+itemsStores[i]+",";
			}
		}
		if(idstores.length()>0)
			idstores = idstores.substring(0,idstores.length()-1);
		
		String day = req.getParameter("day");
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String yearMonth = req.getParameter("yearMonth");
		String month = req.getParameter("month");
		String dtda = req.getParameter("dtda");
		String dtal = req.getParameter("dtal");
		String orario = req.getParameter("orario");
		
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			
			// Totale per allarmi...
			int $tot1 = ReportDash.getAlarmTot(conn, idstores, tipo, day, yearWeek, week, yearMonth, month, dtda, dtal, alarm1);
			obj.put("totAlarm1", $tot1);
			
        	// Stores
			String th = "";
			
			/*
			for(int i = 0; i < itemsStores.length; i++){
				sql  =  "select id, description from stores where id = "+itemsStores[i]+" order by id ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				if(rs.next()) {
					th+="<th class=\"b-r\">"+rs.getString("description")+"</th>";
				}
				rs.close();
				st.close();
			}
			*/
			
			/*
			sql  =  "select id, description from stores where id in("+idstores+") order by id ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			// Esegue l'sql
			rs=st.executeQuery();
			while(rs.next()) {
				th+="<th class=\"b-r\">"+rs.getString("id")+"<br/>"+rs.getString("description")+"</th>";
			}
			rs.close();
			obj.put("th", th);
			*/
			
			String $xcdtb = "";
			String $xcdel = "";
			String $xdtab = "";
			
			String giorno = "";
			Calendar c = null;
			String td = "";
			
			switch (tipo) {
            	case "D":  
            		
            		// Solo un giorno vado per fascia oraria
                	for (int i=0; i<=23; i++) {
                		
                		$xcdtb = "DAY";
                		$xcdel = "00"+i;
                		$xcdel = $xcdel.substring($xcdel.length()-2, $xcdel.length());
                		
                		for(int s = 0; s < itemsStores.length; s++){
                			
	                		sql   = "select s.id, "
	                				+ "coalesce((select sum(conteggio) "
	                				+ "from rep_std_md_daily r "
	                				+ "where eventcode = '"+alarm1+"' "
	                				+ "and id_store = s.id "
	                				+ "and anno = "+day.substring(6,10)+" "
	                				+ "and mese = "+day.substring(3,5)+" "
	                				+ "and giorno = "+day.substring(0,2)+" "
	                				+ "and ora = "+$xcdel+") , 0) count "
	                				+ "from stores s where s.id = "+itemsStores[s]+" "
	                				+ "order by id ";
	                		System.out.println(sql);
	                		// Prepara il cursore
	        				st = conn.prepareStatement(sql);
	        				
	        				// Esegue l'sql
	        				rs=st.executeQuery();
	        				while(rs.next()) {
	        					td+="<td>"+rs.getString("count")+"</td>";
	        				}
	        				rs.close();
	        				st.close();
	        				
                		}
        				
        				period.add(new Tables($xcdtb,$xcdel+":00:00",$xdtab,td,"","",""+i));
                		
                		td = "";
                		
                		
                		// Totale alarm1
                	}
                	
                	
            	break;
            	
            	case "W": 
            		
            		// Get calendar set to current date and time
        			c = Calendar.getInstance(Locale.GERMAN);

        			// Set the calendar to monday of the current week
        			c.set(Calendar.YEAR, Integer.parseInt(yearWeek));     
        			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
        			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        			dtda = sdf.format(c.getTime());
        			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        			dtal = sdf.format(c.getTime());
        			
            		String sql_col = "";
            		for(int i=0;i<nrstores;i++) {
            			sql_col+="S"+(i+1)+" NUMERIC,";
            		}
            		if(sql_col.length()>0)
            			sql_col = sql_col.substring(0,sql_col.length()-1);
            		
            		sql   = "select * FROM crosstab( 'select a.giorno::date,a.id_store::text ,coalesce(conteggio::numeric,null,0) conteggio "
            				+ "from ( select ''"+alarm1+"''::text eventcode,stores.id id_store, description,giorno "
            				+ "from generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno, "
            				+ "stores where stores.id in(select id from stores where id in ("+idstores+") order by id "
            				+ ")) a left join (select * from group_"+yearWeek+"_daily where eventcode = ''"+alarm1+"'') g "
            				+ "on a.eventcode = g.eventcode and a.id_store = g.id_store "
            				+ "and extract(year from a.giorno) = extract(year from g.giorno) "
            				+ "and extract(month from a.giorno) = extract(month from g.giorno) "
            				+ "and extract(day from a.giorno) = extract(day from g.giorno) "
            				+ "order by 1,2') "
            				+ "AS final_result(giorno date, "+sql_col+") ";
            		System.out.println(sql);
            		st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
    				int d = 0;
    				while(rs.next()) {
    					
    					c = Calendar.getInstance(Locale.GERMAN);

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
    						td+="<td>"+rs.getString("S"+(i+1))+"</td>";
                		}
    					
    					period.add(new Tables("WEEK",giorno,sdf.format(c.getTime()),td,"","",""+d));
        				
        				td = "";
    					
    				}
    				rs.close();
    				st.close();
            		
            	break;
            	
            	case "M": 
            		
            		c = Calendar.getInstance(Locale.GERMAN);

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
            		
            		sql   = "select * FROM crosstab( 'select a.giorno::date,a.id_store::text ,coalesce(conteggio::numeric,null,0) conteggio "
            				+ "from ( select ''"+alarm1+"''::text eventcode,stores.id id_store, description,giorno "
            				+ "from generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno, "
            				+ "stores where stores.id in(select id from stores where id in ("+idstores+") order by id "
            				+ ")) a left join (select * from group_"+yearMonth+"_daily where eventcode = ''"+alarm1+"'') g "
            				+ "on a.eventcode = g.eventcode and a.id_store = g.id_store "
            				+ "and extract(year from a.giorno) = extract(year from g.giorno) "
            				+ "and extract(month from a.giorno) = extract(month from g.giorno) "
            				+ "and extract(day from a.giorno) = extract(day from g.giorno) "
            				+ "order by 1,2') "
            				+ "AS final_result(giorno date, "+sql_col+") ";
            		System.out.println(sql);
            		st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
    				d = 0;
    				while(rs.next()) {
    					
    					c = Calendar.getInstance(Locale.GERMAN);

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
    						td+="<td>"+rs.getString("S"+(i+1))+"</td>";
                		}
    					
    					period.add(new Tables("MONTH",giorno,sdf.format(c.getTime()),td,"","",""+d));
        				
        				td = "";
    					
    				}
    				rs.close();
    				st.close();
            		
            	break;
            	
			}
			
			
			
			// Per grafico
        	if(tipo.equals("D")) {
        		
        		for(int i = 0; i < itemsStores.length; i++){
        			
	        		sql   = "select i::date dt, i::time tm, "
	        				+ "coalesce(( "
	        				+ "select sum(conteggio) "
	        				+ "from rep_std_md_daily r where eventcode = '"+alarm1+"' "
	        				+ "and id_store = "+itemsStores[i]+" and anno = cast(to_char(i, 'YYYY') as int) "
	        				+ "and mese = cast(to_char(i, 'MM') as int) "
	        				+ "and giorno = cast(to_char(i, 'DD') as int) "
	        				+ "and ora = cast(to_char(i, 'HH24') as int) "
	        				+ ") , 0) count, "
	        				+ "(select s.description from stores s where s.id = "+itemsStores[i]+") description "
	        				+ "from generate_series('"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00', "
	        				+ "'"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00', '1 hour'::interval) i";
	        		System.out.println(sql);
	        		// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Esegue l'sql
					rs=st.executeQuery();
					
					String store = "";
					String dati = "";
					int tot = 0;
					String sto_description = "";
					while(rs.next()) {
						tot+=rs.getInt("count");
						dati+="["+rs.getString("tm").substring(0,2)+", "+rs.getString("count")+"],";
						sto_description = rs.getString("description");
					}
					rs.close();
					st.close();
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					store="var s"+itemsStores[i]+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",itemsStores[i],store,""+tot,sto_description,"",""));
					
					// ----
					th+="<th class=\"b-r\">"+sto_description+"</th>";
					// ----
					
					dati = "";
					store = "";
					tot = 0;
        		}
        		
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
        		
        		sql   = "SELECT stores.description,a.* from (select * "
        				+ "FROM crosstab( '"
        				+ "select a.id_store::text, a.giorno, coalesce(conteggio::numeric,null,0) conteggio from ( "
        				+ "select ''"+alarm1+"''::text eventcode,stores.id id_store, description,giorno "
        				+ "from generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno, "
        				+ "stores where stores.id in("+idstores+") order by stores.id) a "
        				+ "left join (select * from group_"+yearWeek+"_daily where eventcode = ''"+alarm1+"'') "
        				+ "g on a.eventcode = g.eventcode "
        				+ "and extract(year from a.giorno) = extract(year from g.giorno) "
        				+ "and extract(month from a.giorno) = extract(month from g.giorno) "
        				+ "and extract(day from a.giorno) = extract(day from g.giorno) "
        				+ "and a.id_store = g.id_store "
        				+ "order by 1,2"
        				+ "') "
        				+ "AS final_result(store TEXT, "+sql_col+")) a "
        				+ "join stores on a.store = stores.id::text ";
        		System.out.println(sql);
        		st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String store = "";
				String dati = "";
				int tot = 0;
				
				while(rs.next()) {
					
					c = Calendar.getInstance(Locale.GERMAN);

	    			// Set the calendar to monday of the current week
	    			c.set(Integer.parseInt(dtda.substring(6,10)), Integer.parseInt(dtda.substring(3,5))-1, Integer.parseInt(dtda.substring(0,2)));     
	    			
					for(int i=0;i<=diff;i++) {
						
						tot+=rs.getInt("D"+(i+1));
						dati+="["+c.getTimeInMillis()+", "+rs.getInt("D"+(i+1))+"],";
						
						c.add(Calendar.DATE, 1);
					}
					
					System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8)+" "+rs.getString(9));
					
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					store="var s"+rs.getString("store")+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",rs.getString("store"),store,""+tot,rs.getString("description"),"",""));
					
					// ----
					th+="<th class=\"b-r\">"+rs.getString("description")+"</th>";
					// ----
					
					dati = "";
					store = "";
					tot = 0;
				}
				rs.close();
				st.close();
        		
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
        		
        		sql   = "SELECT stores.description,a.* from (select * "
        				+ "FROM crosstab( '"
        				+ "select a.id_store::text, a.giorno, coalesce(conteggio::numeric,null,0) conteggio from ( "
        				+ "select ''"+alarm1+"''::text eventcode,stores.id id_store, description,giorno "
        				+ "from generate_series(''"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"'', ''"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"'', ''1 day''::interval) giorno, "
        				+ "stores where stores.id in("+idstores+")  order by stores.id) a "
        				+ "left join (select * from group_"+yearMonth+"_daily where eventcode = ''"+alarm1+"'') "
        				+ "g on a.eventcode = g.eventcode "
        				+ "and extract(year from a.giorno) = extract(year from g.giorno) "
        				+ "and extract(month from a.giorno) = extract(month from g.giorno) "
        				+ "and extract(day from a.giorno) = extract(day from g.giorno) "
        				+ "and a.id_store = g.id_store "
        				+ "order by 1,2"
        				+ "') "
        				+ "AS final_result(store TEXT, "+sql_col+")) a "
        				+ "join stores on a.store = stores.id::text ";
        		System.out.println(sql);
        		st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				String store = "";
				String dati = "";
				int tot = 0;
				
				while(rs.next()) {
					
					c = Calendar.getInstance(Locale.GERMAN);

	    			// Set the calendar to monday of the current week
	    			c.set(Integer.parseInt(dtda.substring(6,10)), Integer.parseInt(dtda.substring(3,5))-1, Integer.parseInt(dtda.substring(0,2)));     
	    			
					for(int i=0;i<=diff;i++) {
						
						tot+=rs.getInt("D"+(i+1));
						dati+="["+c.getTimeInMillis()+", "+rs.getInt("D"+(i+1))+"],";
						
						c.add(Calendar.DATE, 1);
					}
					
					System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8)+" "+rs.getString(9));
					
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					store="var s"+rs.getString("store")+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",rs.getString("store"),store,""+tot,rs.getString("description"),"",""));
					
					// ----
					th+="<th class=\"b-r\">"+rs.getString("description")+"</th>";
					// ----
					
					dati = "";
					store = "";
					tot = 0;
				}
				rs.close();
				st.close();
        		
				obj.put("th", th);
				
        	}
        	
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
		obj.put("id_report", id_report);
		obj.put("code", code);
		obj.put("description", description);
		obj.put("alarm1", alarm1);
		obj.put("alarm1_descr", alarm1_descr);
		obj.put("alarm2", alarm2);
		obj.put("alarm2_descr", alarm2_descr);
		obj.put("nrstores", nrstores);
		obj.put("idstores", idstores);
		
		// Periodo analizzato (con dati)
		obj.put("period", period);
		// Stores per Charts
		obj.put("sto", sto);
		
		return new ModelAndView("/app/report/report1-1", obj);
	}
	
}
