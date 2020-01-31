package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
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

import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Report2_2 extends AbstractController {
	
	// Formato data
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Tables> sto = new ArrayList<Tables>();
		sto.clear();
		List<Tables> stoConfronto = new ArrayList<Tables>();
		stoConfronto.clear();
		
		List<Tables> period = new ArrayList<Tables>();
		period.clear();
		List<Tables> periodConfronto = new ArrayList<Tables>();
		periodConfronto.clear();
		List<Tables> periodBottom = new ArrayList<Tables>();
		periodBottom.clear();
		
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
		String historic = req.getParameter("historic");
		String benchmark = req.getParameter("benchmark");
		obj.put("tipo", tipo);
		
		// ==============
		// PERIODO
		String day = req.getParameter("day");
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String yearMonth = req.getParameter("yearMonth");
		String month = req.getParameter("month");
		String dtda = req.getParameter("dtda");
		String dtal = req.getParameter("dtal");
		
		String dayConfronto = day;
		String yearWeekConfronto = yearWeek;
		String weekConfronto = week;
		String yearMonthConfronto = yearMonth;
		String monthConfronto = month;
		String dtdaConfronto = dtda;
		String dtalConfronto = dtal;
		
		// ==============
		// PERIODO CONFRONTO (Solo Historic=true)
		if(historic.equals("t")) {
			dayConfronto = req.getParameter("dayConfronto");
			yearWeekConfronto = req.getParameter("yearWeekConfronto");
			weekConfronto = req.getParameter("weekConfronto");
			yearMonthConfronto = req.getParameter("yearMonthConfronto");
			monthConfronto = req.getParameter("monthConfronto");
			dtdaConfronto = req.getParameter("dtdaConfronto");
			dtalConfronto = req.getParameter("dtalConfronto");
		}
		
		String orario = req.getParameter("orario");
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAnalytics();
			
			// ==============
			// STORES
			String idstores = "";
			int nrstores = 0;
			String[] itemsStores = req.getParameterValues("id_store");
			
			if(itemsStores==null){
				System.out.println("NULL - STORES");
			}
			else {
				System.out.println("NO NULL - STORES");
				for(int i = 0; i < itemsStores.length; i++){
					nrstores+=1;
				    idstores+=""+itemsStores[i]+",";
				    
				    sto.add(new Tables("STORES",itemsStores[i],getStoreDescription(conn,itemsStores[i]),"","","",""));
				}
			}
			if(idstores.length()>0)
				idstores = idstores.substring(0,idstores.length()-1);
			
			// Store Default
			String store = ""+req.getParameter("store");
			store=Utility.noNull(store);
			if(store.length()==0)
				store = itemsStores[0];
			obj.put("store", store);
			
			String idstoresConfronto = store;
			int nrstoresConfronto = 1;
			
			// ==============
			// STORES CONFRONTO (Solo Benchmark=true)
			if(benchmark.equals("t")) {
				String[] itemsStoresConfronto = req.getParameterValues("id_storeConfronto");
				
				if(itemsStoresConfronto==null){
					System.out.println("NULL - STORES CONFRONTO");
				}
				else {
					idstoresConfronto = "";
					nrstoresConfronto = 0;
					System.out.println("NO NULL - STORES CONFRONTO");
					for(int i = 0; i < itemsStoresConfronto.length; i++){
						nrstoresConfronto+=1;
					    idstoresConfronto+=""+itemsStoresConfronto[i]+",";
					    
					    stoConfronto.add(new Tables("STORES",itemsStoresConfronto[i],"","","","",""));
					}
					
					if(idstoresConfronto.length()>0)
						idstoresConfronto = idstoresConfronto.substring(0,idstoresConfronto.length()-1);
				}
				
			}
			obj.put("nrstores", nrstores);
			obj.put("idstores", idstores);
			
			obj.put("nrstoresConfronto", nrstoresConfronto);
			obj.put("idstoresConfronto", idstoresConfronto);
			
			// ==============
			// Totale per allarmi...
			int $tot1 = ReportDash.getAlarmTot(conn, store, tipo, day, yearWeek, week, yearMonth, month, dtda, dtal, alarm1);
			obj.put("totAlarm1", $tot1);
			
			// ==============
			// Totale per allarmi (Confronto)...
			int $tot1Confronto = ReportDash.getAlarmTot(conn, idstoresConfronto, tipo, dayConfronto, yearWeekConfronto, 
					weekConfronto, yearMonthConfronto, monthConfronto, dtdaConfronto, dtalConfronto, alarm1);
			obj.put("totAlarm1Confronto", $tot1Confronto/nrstoresConfronto);
			
			/*
			int[] totAlarms = new int[]{$tot1,$tot1Confronto};
			int maxValue = totAlarms[0];
		    for (int i = 1; i < totAlarms.length; i++) {
		        if (totAlarms[i] > maxValue) {
		            maxValue = totAlarms[i];
		        }
		    }
		    obj.put("maxValue", maxValue);
		    */
			int maxValue=0;
			int maxValueConfronto=0;
			
			Calendar c = null;
			
        	switch (tipo) {
        	case "D":  
        		
        		// Store
        		sql   = "select i::date dt, i::time tm, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_daily r where eventcode = '"+alarm1+"' "
        				+ "and id_store = "+store+" and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ "and ora = cast(to_char(i, 'HH24') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00', "
        				+ "'"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00', '1 hour'::interval) i";
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				String dati = "";
				int tot1 = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1+=rs.getInt("count1");
					
					dati+="["+rs.getString("tm").substring(0,2)+", "+rs.getInt("count1")+"],";
					if(maxValue<rs.getInt("count1"))
						maxValue = rs.getInt("count1");
					
					period.add(new Tables("PERIOD",rs.getString("tm"),""+rs.getInt("count1"),"","","",""));
				}
				rs.close();
				st.close();
				
				if(dati.length()>0)
					dati=dati.substring(0,dati.length()-1);
				
				dati="var store = ["+dati+"];";
				
				obj.put("dati", dati);
				obj.put("tot1", tot1);
        		
				// *******************
				
        		// Store Confronto (Deve dividere i conteggi per il numero degli Store di confronto)
        		sql   = "select i::date dt, i::time tm, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_daily r where eventcode = '"+alarm1+"' "
        				+ "and id_store in( "+idstoresConfronto+" ) and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ "and ora = cast(to_char(i, 'HH24') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+dayConfronto.substring(6,10)+"-"+dayConfronto.substring(3,5)+"-"+dayConfronto.substring(0,2)+" 00:00:00', "
        				+ "'"+dayConfronto.substring(6,10)+"-"+dayConfronto.substring(3,5)+"-"+dayConfronto.substring(0,2)+" 23:00:00', '1 hour'::interval) i";
        		
        		System.out.println(sql);
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				String datiConfronto = "";
				int tot1Confronto = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1Confronto+=rs.getInt("count1")/nrstoresConfronto;
					
					datiConfronto+="["+rs.getString("tm").substring(0,2)+", "+rs.getInt("count1")/nrstoresConfronto+"],";
					if(maxValueConfronto<(rs.getInt("count1")/nrstoresConfronto))
						maxValueConfronto = (rs.getInt("count1")/nrstoresConfronto);
					
					periodConfronto.add(new Tables("PERIODCONFRONTO",rs.getString("tm"),""+rs.getInt("count1")/nrstoresConfronto,"","","",""));
				}
				rs.close();
				st.close();
				
				if(datiConfronto.length()>0)
					datiConfronto=datiConfronto.substring(0,datiConfronto.length()-1);
				
				datiConfronto="var storeConfronto = ["+datiConfronto+"];";
				
				obj.put("datiConfronto", datiConfronto);
				obj.put("tot1Confronto", tot1Confronto);
        		
        		break;
			
        	case "W":
        		
        		c = Calendar.getInstance();

    			// Set the calendar to monday of the current week
    			c.set(Calendar.YEAR, Integer.parseInt(yearWeek));     
    			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
    			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    			
    			dtda = sdf.format(c.getTime());
    			c.add(Calendar.DATE, 6);
    			dtal = sdf.format(c.getTime());
    			
    			// ===========
    			// Confronto
    			// Set the calendar to monday of the current week
    			c.set(Calendar.YEAR, Integer.parseInt(yearWeekConfronto));     
    			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(weekConfronto));     
    			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    			
    			dtdaConfronto = sdf.format(c.getTime());
    			c.add(Calendar.DATE, 6);
    			dtalConfronto = sdf.format(c.getTime());
    			
        		// Store
        		sql   = "select i::date dt, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_period r where eventcode = '"+alarm1+"' "
        				+ "and id_store = "+store+" and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"', "
        				+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"', '1 day'::interval) i ";	
        		System.out.println(sql);
        		
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				dati = "";
				tot1 = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1+=rs.getInt("count1");
					
					dati+="["+rs.getDate("dt").getTime()+", "+rs.getInt("count1")+"],";
					if(maxValue<rs.getInt("count1"))
						maxValue = rs.getInt("count1");
					period.add(new Tables("PERIOD",sdf.format(rs.getDate("dt")),""+rs.getInt("count1"),"","","",""));
				}
				rs.close();
				st.close();
				
				if(dati.length()>0)
					dati=dati.substring(0,dati.length()-1);
				
				dati="var store = ["+dati+"];";
				
				obj.put("dati", dati);
				obj.put("tot1", tot1);
        		
				// *******************
				
        		// Store Confronto (Deve dividere i conteggi per il numero degli Store di confronto)
        		sql   = "select i::date dt, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_period r where eventcode = '"+alarm1+"' "
        				+ "and id_store in( "+idstoresConfronto+" ) and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+dtdaConfronto.substring(6,10)+"-"+dtdaConfronto.substring(3,5)+"-"+dtdaConfronto.substring(0,2)+"', "
        				+ "'"+dtalConfronto.substring(6,10)+"-"+dtalConfronto.substring(3,5)+"-"+dtalConfronto.substring(0,2)+"', '1 day'::interval) i ";	
        		System.out.println(sql);
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				datiConfronto = "";
				tot1Confronto = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1Confronto+=rs.getInt("count1")/nrstoresConfronto;
					
					datiConfronto+="["+rs.getDate("dt").getTime()+", "+rs.getInt("count1")/nrstoresConfronto+"],";
					if(maxValueConfronto<(rs.getInt("count1")/nrstoresConfronto))
						maxValueConfronto = (rs.getInt("count1")/nrstoresConfronto);
					periodConfronto.add(new Tables("PERIODCONFRONTO",sdf.format(rs.getDate("dt")),""+rs.getInt("count1")/nrstoresConfronto,"","","",""));
				}
				rs.close();
				st.close();
				
				if(datiConfronto.length()>0)
					datiConfronto=datiConfronto.substring(0,datiConfronto.length()-1);
				
				datiConfronto="var storeConfronto = ["+datiConfronto+"];";
				
				obj.put("datiConfronto", datiConfronto);
				obj.put("tot1Confronto", tot1Confronto);
        		
        		break;
        		
        	case "M":
        		
        		c = Calendar.getInstance();
        		c.set(Calendar.MONTH, Integer.parseInt(month)-1);
        		
        		dtda = "01/"+month+"/"+yearMonth;
            	dtal = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+month+"/"+yearMonth;
            	
            	dtdaConfronto = "01/"+monthConfronto+"/"+yearMonthConfronto;
            	dtalConfronto = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+monthConfronto+"/"+yearMonthConfronto;
            	
            	// Store
        		sql   = "select i::date dt, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_period r where eventcode = '"+alarm1+"' "
        				+ "and id_store = "+store+" and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"', "
        				+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"', '1 day'::interval) i ";	
        		
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				dati = "";
				tot1 = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1+=rs.getInt("count1");
					
					dati+="["+rs.getDate("dt").getTime()+", "+rs.getInt("count1")+"],";
					if(maxValue<rs.getInt("count1"))
						maxValue = rs.getInt("count1");
					period.add(new Tables("PERIOD",sdf.format(rs.getDate("dt")),""+rs.getInt("count1"),"","","",""));
				}
				rs.close();
				st.close();
				
				if(dati.length()>0)
					dati=dati.substring(0,dati.length()-1);
				
				dati="var store = ["+dati+"];";
				
				obj.put("dati", dati);
				obj.put("tot1", tot1);
        		
				// *******************
				
        		// Store Confronto (Deve dividere i conteggi per il numero degli Store di confronto)
        		sql   = "select i::date dt, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_period r where eventcode = '"+alarm1+"' "
        				+ "and id_store in ( "+idstoresConfronto+" ) and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+dtdaConfronto.substring(6,10)+"-"+dtdaConfronto.substring(3,5)+"-"+dtdaConfronto.substring(0,2)+"', "
        				+ "'"+dtalConfronto.substring(6,10)+"-"+dtalConfronto.substring(3,5)+"-"+dtalConfronto.substring(0,2)+"', '1 day'::interval) i ";	
        		
        		System.out.println(sql);
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				datiConfronto = "";
				tot1Confronto = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1Confronto+=rs.getInt("count1")/nrstoresConfronto;
					
					datiConfronto+="["+rs.getDate("dt").getTime()+", "+rs.getInt("count1")/nrstoresConfronto+"],";
					if(maxValueConfronto<(rs.getInt("count1")/nrstoresConfronto))
						maxValueConfronto = (rs.getInt("count1")/nrstoresConfronto);
					periodConfronto.add(new Tables("PERIODCONFRONTO",sdf.format(rs.getDate("dt")),""+rs.getInt("count1")/nrstoresConfronto,"","","",""));
				}
				rs.close();
				st.close();
				
				if(datiConfronto.length()>0)
					datiConfronto=datiConfronto.substring(0,datiConfronto.length()-1);
				
				datiConfronto="var storeConfronto = ["+datiConfronto+"];";
				
				obj.put("datiConfronto", datiConfronto);
				obj.put("tot1Confronto", tot1Confronto);
				
        		break;
        		
        	case "P":
        		
        		// Store
        		sql   = "select i::date dt, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_period r where eventcode = '"+alarm1+"' "
        				+ "and id_store = "+store+" and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"', "
        				+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"', '1 day'::interval) i ";	
        		
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				dati = "";
				tot1 = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1+=rs.getInt("count1");
					
					dati+="["+rs.getDate("dt").getTime()+", "+rs.getInt("count1")+"],";
					if(maxValue<rs.getInt("count1"))
						maxValue = rs.getInt("count1");
					period.add(new Tables("PERIOD",sdf.format(rs.getDate("dt")),""+rs.getInt("count1"),"","","",""));
				}
				rs.close();
				st.close();
				
				if(dati.length()>0)
					dati=dati.substring(0,dati.length()-1);
				
				dati="var store = ["+dati+"];";
				
				obj.put("dati", dati);
				obj.put("tot1", tot1);
        		
				// *******************
				
        		// Store Confronto (Deve dividere i conteggi per il numero degli Store di confronto)
        		sql   = "select i::date dt, "
        				+ "coalesce(( "
        				+ "select sum(conteggio) "
        				+ "from rep_std_md_period r where eventcode = '"+alarm1+"' "
        				+ "and id_store in ( "+idstoresConfronto+" ) and anno = cast(to_char(i, 'YYYY') as int) "
        				+ "and mese = cast(to_char(i, 'MM') as int) "
        				+ "and giorno = cast(to_char(i, 'DD') as int) "
        				+ ") , 0) count1 "
        				+ "from generate_series('"+dtdaConfronto.substring(6,10)+"-"+dtdaConfronto.substring(3,5)+"-"+dtdaConfronto.substring(0,2)+"', "
        				+ "'"+dtalConfronto.substring(6,10)+"-"+dtalConfronto.substring(3,5)+"-"+dtalConfronto.substring(0,2)+"', '1 day'::interval) i ";	
        		
        		System.out.println(sql);
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				datiConfronto = "";
				tot1Confronto = 0;
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					tot1Confronto+=rs.getInt("count1")/nrstoresConfronto;
					
					datiConfronto+="["+rs.getDate("dt").getTime()+", "+rs.getInt("count1")/nrstoresConfronto+"],";
					if(maxValueConfronto<(rs.getInt("count1")/nrstoresConfronto))
						maxValueConfronto = (rs.getInt("count1")/nrstoresConfronto);
					periodConfronto.add(new Tables("PERIODCONFRONTO",sdf.format(rs.getDate("dt")),""+rs.getInt("count1")/nrstoresConfronto,"","","",""));
				}
				rs.close();
				st.close();
				
				if(datiConfronto.length()>0)
					datiConfronto=datiConfronto.substring(0,datiConfronto.length()-1);
				
				datiConfronto="var storeConfronto = ["+datiConfronto+"];";
				
				obj.put("datiConfronto", datiConfronto);
				obj.put("tot1Confronto", tot1Confronto);
        		
        		break;
        		
        	}
			
        	if(maxValue>=maxValueConfronto)
        		obj.put("maxValue", (maxValue+1));
        	else
        		obj.put("maxValue", (maxValueConfronto+1));
			
        	// Concateno le liste
        	int length = period.size();
        	if (length != periodConfronto.size()) { 
        	    // Fail
        	}
        	
        	
        	ArrayList<Tables> tbottom = new ArrayList<Tables>(length); 
        	String rapporto="";
        	int iRapporto=0;
        	for (int i = 0; i < length; i++) { 
        		
        		try {
        			iRapporto = (Integer.parseInt(period.get(i).getXdtab())*100)/Integer.parseInt(periodConfronto.get(i).getXdtab());
        		}
        		catch(Exception e) {}
        		
        		if(Integer.parseInt(period.get(i).getXdtab())>Integer.parseInt(periodConfronto.get(i).getXdtab())) {
        			rapporto = "<i class=\"fa fa-caret-up text-success\"></i> <span class=\"text-success\">"+iRapporto+"%</span>";
        		}
        		else if(Integer.parseInt(period.get(i).getXdtab())<Integer.parseInt(periodConfronto.get(i).getXdtab())) {
        			rapporto = "<i class=\"fa fa-caret-down text-danger\"></i> <span class=\"text-danger\">"+iRapporto+"%</span>";
        		}
        		else {
        			rapporto = "-";
        		}
        	    tbottom.add(new Tables(period.get(i).getXcdtb(), 
        	    		period.get(i).getXcdel(),
        	    		period.get(i).getXdtab(),
        	    		periodConfronto.get(i).getXdtab(),
        	    		""+iRapporto,rapporto,""));
        	    iRapporto=0;
        	}
        	obj.put("tbottom", tbottom);
        	
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("dsfun", "Report 2-2");
		obj.put("msg", msg);
		
		// parametri passati dalla maschera
		obj.put("id_report", id_report);
		obj.put("code", code);
		obj.put("description", description);
		obj.put("alarm1", alarm1);
		obj.put("alarm1_descr", alarm1_descr);
		obj.put("alarm2", alarm2);
		obj.put("alarm2_descr", alarm2_descr);
		obj.put("tipo" , tipo);
		obj.put("historic", historic);
		obj.put("benchmark", benchmark);
		
		obj.put("day" , day);
		obj.put("yearWeek" , yearWeek);
		obj.put("week" , week);
		obj.put("yearMonth" , yearMonth);
		obj.put("month" , month);
		obj.put("dtda" , dtda);
		obj.put("dtal" , dtal);
		
		if(historic.equals("t")) {
			
			obj.put("dayConfronto" , dayConfronto);
			obj.put("yearWeekConfronto" , yearWeekConfronto);
			obj.put("weekConfronto" , weekConfronto);
			obj.put("yearMonthConfronto" , yearMonthConfronto);
			obj.put("monthConfronto" , monthConfronto);
			obj.put("dtdaConfronto" , dtdaConfronto);
			obj.put("dtalConfronto" , dtalConfronto);
			
		}
		
		obj.put("sto", sto);
		
		if(benchmark.equals("t")) {
			obj.put("stoConfronto", stoConfronto);
		}
		
		obj.put("period", period);
		obj.put("periodConfronto", periodConfronto);
		
		return new ModelAndView("/app/report/report2-2", obj);
	}
	
	public static String getStoreDescription(Connection conn, String id_store) {
		
		String desc = "";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql  = "select description from stores where id = "+id_store+" ";
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			if(rs.next()) {
				desc = rs.getString("description");
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return desc;
		
	}
}
