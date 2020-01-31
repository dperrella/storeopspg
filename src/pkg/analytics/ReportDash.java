package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReportDash extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> period = new ArrayList<Tables>();
		List<Stores> sto = new ArrayList<Stores>();
		List<Tables> ala = new ArrayList<Tables>();
		
		sto.clear();
		period.clear();
		ala.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String idstores = req.getParameter("idstores");
		String tipo = req.getParameter("tipo");
		String day = req.getParameter("day");
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String yearMonth = req.getParameter("yearMonth");
		String month = req.getParameter("month");
		String dtda = req.getParameter("dtda");
		String dtal = req.getParameter("dtal");
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			conn = Utility.getConnAnalytics();
			
			// ==================
			// LISTA NEGOZI
			// ==================
			// sql da eseguire
			sql  =  "select a.id, a.description, c.brandname custbrand, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id and a.id in("+idstores+") "
					+ "and a.id in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $description="";
			String $id="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				$description=rs.getString("description");
				$id=rs.getString("id");
				
				$cmp1=rs.getString("custbrand");
				$cmp2=rs.getString("nrdevices");
				
				sto.add(new Stores($description,"","","",
						"","","","","","","",
						"","","","","",
						$id,"","","","","",
						"","","","","",
						"","","",
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			
			// ====================
			// Lista allarmi per intestazione colonne
			sql  =  "select a.alarmcode, e.description, e.range_description "
					+ "from stores s, devices d, rel_user_stores r, rel_device_alarms a, analytics_eventcode e "
					+ "where s.id = d.id_store and s.id = r.idstore and d.id = a.iddevice and e.code = a.alarmcode "
					+ "and r.idusr = '"+(String)session.getAttribute("username")+"' and s.id in("+idstores+") "
					+ "group by a.alarmcode, e.description, e.range_description "
					+ "order by a.alarmcode ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// campi tables
			String $xcdtb = "";
			String $xcdel = "";
			String $xdtab = "";
			int $tot = 0;
			
			String arrayAlarms = "";
			int nrAlarms = 0;
			
			String gridcol = "";
			String color = "";
			
			while(rs.next()) {
				
				nrAlarms+=1;
				
				$xcdtb = rs.getString("alarmcode");
				$xcdel = rs.getString("description");
				$xdtab = rs.getString("range_description");
				
				$tot = 0;
				
				if(nrAlarms<=6) {
					
					if(nrAlarms==1) 
						color = "bg-primary";
					if(nrAlarms==2) 
						color = "bg-black";
					if(nrAlarms==3) 
						color = "bg-light dker";
					if(nrAlarms==4) 
						color = "bg-info dker";
					if(nrAlarms==5) 
						color = "bg-success dker";
					if(nrAlarms==6) 
						color = "bg-warning dker";
					
					// Totale per allarmi...
					$tot = getAlarmTot(conn, idstores, tipo, day, yearWeek, week, yearMonth, month, dtda, dtal, $xcdtb);
					
					arrayAlarms+=rs.getString("alarmcode")+",";
					ala.add(new Tables($xcdtb,$xcdel,$xdtab,color,""+$tot,"",""));
				
				}
			}
			rs.close();
			
			if(nrAlarms>6)
				nrAlarms = 6;
			
			// Per grid...
			if(nrAlarms==1) {
				gridcol = "col-lg-12";
			}
			if(nrAlarms==2) {
				gridcol = "col-lg-6";
			}
			if(nrAlarms==3) {
				gridcol = "col-lg-4";
			}
			if(nrAlarms==4) {
				gridcol = "col-lg-3";
			}
			if(nrAlarms==5) {
				gridcol = "col-lg-2-4";
			}
			if(nrAlarms==6) {
				gridcol = "col-lg-2";
			}
			
			obj.put("gridcol", gridcol);
			
			
			if(arrayAlarms.length()>0)
				arrayAlarms=arrayAlarms.substring(0,arrayAlarms.length()-1);
			
			// ====================
			
			$xcdtb = "";
			$xcdel = "";
			$xdtab = "";
			
			String giorno = "";
			Calendar c = null;
			
			String cols = "";
			String td = "";
			String al = "";
			String al0 = "";
			
			String tdTot = "";
			
			String lnkDay = "";
			
			switch (tipo) {
            case "D":  
            	
            	// Solo un giorno vado per fascia oraria
            	for (int i=7; i<=24; i++) {
            		
            		$xcdtb = "DAY";
            		$xcdel = "00"+i;
            		$xcdel = $xcdel.substring($xcdel.length()-2, $xcdel.length());
            		
            		//Istruzione di selezione per conteggio
                	String temp[] = arrayAlarms.split(",");
    				
    				for(int t=0; t<temp.length; t++) {
    					
    					cols+="(select sum(numero) from analytics_eventgrouped e "
    							+ "where e.id_store = s.id and anno = "+day.substring(6,10)+" "
    							+ "and mese = "+day.substring(3,5)+" "
    							+ "and giorno = "+day.substring(0,2)+" "
    							+ "and eventcode = '"+temp[t]+"' and ora = "+$xcdel+") a"+temp[t]+",";
    					
    				}
    				if(cols.length()>0)
    					cols=cols.substring(0,cols.length()-1);
    				
    				sql  =  "select s.id, s.description, "+cols+" "
    						+ "from stores s, rel_user_stores r "
    						+ "where s.id = r.idstore and s.id in("+idstores+") "
    						+ "and r.idusr = '"+(String)session.getAttribute("username")+"' ";
    				
    				// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				while(rs.next()) {
    					
    					td+="<tr><td>"+rs.getString("description")+"</td>";
    					for(int t=0; t<temp.length; t++) {
    						
    						al = ""+rs.getString("a"+temp[t]);
    						al = Utility.noNull(al);
    						if(al.length()==0) {
    							al = "-";
    							al0 = "0";
    						}
    						else {
    							al0 = al;
    						}
    								
    						td+="<td><input type=\"hidden\" name=\"a"+i+""+temp[t]+"\" value=\""+al0+"\">"+al+"</td>";
    						
    						//tdTot+="<td><span id=\"tot"+i+""+temp[t]+"\"></span></td>";
    					}
    					td+="</tr>";
    					
    					
    				}
    				rs.close();
    				st.close();
    				
            		period.add(new Tables($xcdtb,$xcdel,$xdtab,td,"",tdTot,""+i));
    				
            		// *blank cols
    				cols = "";
    				td = "";
    				tdTot = "";
            	}
            	
            	break;
            	
            case "W": 
            	
            	giorno = "";
            	// Get calendar set to current date and time
    			c = Calendar.getInstance();

    			// Set the calendar to monday of the current week
    			c.set(Calendar.YEAR, Integer.parseInt(yearWeek));     
    			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
    			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            	
            	// Settimana
            	for (int d = 0; d < 7; d++) {
            		
            		if(d==0)
    					giorno = propLang.get("lun")+" "+sdf.format(c.getTime());
    				if(d==1)
    					giorno = propLang.get("mar")+" "+sdf.format(c.getTime());
    				if(d==2)
    					giorno = propLang.get("mer")+" "+sdf.format(c.getTime());
    				if(d==3)
    					giorno = propLang.get("gio")+" "+sdf.format(c.getTime());
    				if(d==4)
    					giorno = propLang.get("ven")+" "+sdf.format(c.getTime());
    				if(d==5)
    					giorno = propLang.get("sab")+" "+sdf.format(c.getTime());
    				if(d==6)
    					giorno = propLang.get("dom")+" "+sdf.format(c.getTime());
    				
    				//Istruzione di selezione per conteggio
                	String temp[] = arrayAlarms.split(",");
    				
    				for(int t=0; t<temp.length; t++) {
    					
    					cols+="(select sum(numero) from analytics_eventgrouped e "
    							+ "where e.id_store = s.id and anno = "+sdf.format(c.getTime()).substring(6,10)+" "
    							+ "and mese = "+sdf.format(c.getTime()).substring(3,5)+" "
    							+ "and giorno = "+sdf.format(c.getTime()).substring(0,2)+" "
    							+ "and eventcode = '"+temp[t]+"') a"+temp[t]+",";
    					
    				}
    				if(cols.length()>0)
    					cols=cols.substring(0,cols.length()-1);
    				
    				sql  =  "select s.id, s.description, "+cols+" "
    						+ "from stores s, rel_user_stores r "
    						+ "where s.id = r.idstore and s.id in("+idstores+") "
    						+ "and r.idusr = '"+(String)session.getAttribute("username")+"' ";
    				
    				// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				while(rs.next()) {
    					
    					td+="<tr><td>"+rs.getString("description")+"</td>";
    					for(int t=0; t<temp.length; t++) {
    						
    						al = ""+rs.getString("a"+temp[t]);
    						al = Utility.noNull(al);
    						if(al.length()==0) {
    							al = "-";
    							al0 = "0";
    						}
    						else {
    							al0 = al;
    						}
    								
    						td+="<td><input type=\"hidden\" name=\"a"+d+""+temp[t]+"\" value=\""+al0+"\">"+al+"</td>";
    						
    					}
    					td+="</tr>";
    					
    				}
    				rs.close();
    				st.close();
    				
    				lnkDay = "<a href=\"../report/report-dash.htm?idstores="+idstores+"&tipo=D&day="+sdf.format(c.getTime())+"&yearWeek="+yearWeek+"&week="+week+"&yearMonth="+yearMonth+"&month="+month+"&dtda="+dtda+"&dtal="+dtal+"\" class=\"btn btn-default btn-xs\">"+propLang.get("dett")+"</a>";
    				
    				period.add(new Tables("WEEK",giorno,sdf.format(c.getTime()),td,lnkDay,"",""+d));
    				
    				c.add(Calendar.DATE, 1);
    				
    				// *blank cols / td
    				cols = "";
    				td = "";
    				
            	}
                
            	break;
            	
            case "M": 
            	
            	// Mese
            	giorno = "";
            	
            	// Get calendar set to current date and time
    			c = Calendar.getInstance();

    			// Set the calendar to monday of the current week
    			c.set(Integer.parseInt(yearMonth), Integer.parseInt(month)-1, 1);     
    			
    			// Mese
            	for (int m = 1; m <= c.getActualMaximum(Calendar.DAY_OF_MONTH); m++) {
            		
            		if(c.get(Calendar.DAY_OF_WEEK)==2)
    					giorno = propLang.get("lun")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==3)
    					giorno = propLang.get("mar")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==4)
    					giorno = propLang.get("mer")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==5)
    					giorno = propLang.get("gio")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==6)
    					giorno = propLang.get("ven")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==7)
    					giorno = propLang.get("sab")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==1)
    					giorno = propLang.get("dom")+" "+sdf.format(c.getTime());
    				
    				//Istruzione di selezione per conteggio
                	String temp[] = arrayAlarms.split(",");
    				
    				for(int t=0; t<temp.length; t++) {
    					
    					cols+="(select sum(numero) from analytics_eventgrouped e "
    							+ "where e.id_store = s.id and anno = "+sdf.format(c.getTime()).substring(6,10)+" "
    							+ "and mese = "+sdf.format(c.getTime()).substring(3,5)+" "
    							+ "and giorno = "+sdf.format(c.getTime()).substring(0,2)+" "
    							+ "and eventcode = '"+temp[t]+"') a"+temp[t]+",";
    					
    				}
    				if(cols.length()>0)
    					cols=cols.substring(0,cols.length()-1);
    				
    				sql  =  "select s.id, s.description, "+cols+" "
    						+ "from stores s, rel_user_stores r "
    						+ "where s.id = r.idstore and s.id in("+idstores+") "
    						+ "and r.idusr = '"+(String)session.getAttribute("username")+"' ";
    				
    				// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				while(rs.next()) {
    					
    					td+="<tr><td>"+rs.getString("description")+"</td>";
    					for(int t=0; t<temp.length; t++) {
    						
    						al = ""+rs.getString("a"+temp[t]);
    						al = Utility.noNull(al);
    						if(al.length()==0) {
    							al = "-";
    							al0 = "0";
    						}
    						else {
    							al0 = al;
    						}
    								
    						td+="<td><input type=\"hidden\" name=\"a"+m+""+temp[t]+"\" value=\""+al0+"\">"+al+"</td>";
    						
    					}
    					td+="</tr>";
    					
    				}
    				rs.close();
    				st.close();
    				
    				lnkDay = "<a href=\"../report/report-dash.htm?idstores="+idstores+"&tipo=D&day="+sdf.format(c.getTime())+"&yearWeek="+yearWeek+"&week="+week+"&yearMonth="+yearMonth+"&month="+month+"&dtda="+dtda+"&dtal="+dtal+"\" class=\"btn btn-default btn-xs\">"+propLang.get("dett")+"</a>";
    				
            		period.add(new Tables("MONTH",giorno,sdf.format(c.getTime()),td,lnkDay,"",""+m));
            		
            		c.add(Calendar.DATE, 1);
            		
            		// *blank cols / td
    				cols = "";
    				td = "";
    				
            	}
            	break;
            	
            case "P": 
            	
            	// Periodo
            	int diff = Utility.getDateDiffDays(dtda, dtal);
            	
            	// Get calendar set to current date and time
    			c = Calendar.getInstance();

    			// Set the calendar to monday of the current week
    			c.set(Integer.parseInt(dtda.substring(6,10)), Integer.parseInt(dtda.substring(3,5))-1, Integer.parseInt(dtda.substring(0,2)));     

    			// Mese
            	for (int p = 0; p <= diff; p++) {
            		
            		if(c.get(Calendar.DAY_OF_WEEK)==2)
    					giorno = propLang.get("lun")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==3)
    					giorno = propLang.get("mar")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==4)
    					giorno = propLang.get("mer")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==5)
    					giorno = propLang.get("gio")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==6)
    					giorno = propLang.get("ven")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==7)
    					giorno = propLang.get("sab")+" "+sdf.format(c.getTime());
    				if(c.get(Calendar.DAY_OF_WEEK)==1)
    					giorno = propLang.get("dom")+" "+sdf.format(c.getTime());
            		
    				//Istruzione di selezione per conteggio
                	String temp[] = arrayAlarms.split(",");
    				
    				for(int t=0; t<temp.length; t++) {
    					
    					cols+="(select sum(numero) from analytics_eventgrouped e "
    							+ "where e.id_store = s.id and anno = "+sdf.format(c.getTime()).substring(6,10)+" "
    							+ "and mese = "+sdf.format(c.getTime()).substring(3,5)+" "
    							+ "and giorno = "+sdf.format(c.getTime()).substring(0,2)+" "
    							+ "and eventcode = '"+temp[t]+"') a"+temp[t]+",";
    					
    				}
    				if(cols.length()>0)
    					cols=cols.substring(0,cols.length()-1);
    				
    				sql  =  "select s.id, s.description, "+cols+" "
    						+ "from stores s, rel_user_stores r "
    						+ "where s.id = r.idstore and s.id in("+idstores+") "
    						+ "and r.idusr = '"+(String)session.getAttribute("username")+"' ";
    				
    				// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				while(rs.next()) {
    					
    					td+="<tr><td>"+rs.getString("description")+"</td>";
    					for(int t=0; t<temp.length; t++) {
    						
    						al = ""+rs.getString("a"+temp[t]);
    						al = Utility.noNull(al);
    						if(al.length()==0) {
    							al = "-";
    							al0 = "0";
    						}
    						else {
    							al0 = al;
    						}
    								
    						td+="<td><input type=\"hidden\" name=\"a"+p+""+temp[t]+"\" value=\""+al0+"\">"+al+"</td>";
    						
    					}
    					td+="</tr>";
    					
    				}
    				rs.close();
    				st.close();
    				
    				lnkDay = "<a href=\"../report/report-dash.htm?idstores="+idstores+"&tipo=D&day="+sdf.format(c.getTime())+"&yearWeek="+yearWeek+"&week="+week+"&yearMonth="+yearMonth+"&month="+month+"&dtda="+dtda+"&dtal="+dtal+"\" class=\"btn btn-default btn-xs\">"+propLang.get("dett")+"</a>";
    				
            		period.add(new Tables("MONTH",giorno,sdf.format(c.getTime()),td,lnkDay,"",""+p));
            		
            		c.add(Calendar.DATE, 1);
            		
            		// *blank cols / td
    				cols = "";
    				td = "";
    				
            	}
            	break;
            	
            default: 
                
            	break;
			}
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Report Dash");
		obj.put("sto", sto);
		obj.put("period", period);
		obj.put("ala", ala);
		
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/report/report-dash", obj);
	}
	
	public static int getAlarmTot(Connection conn, String idstores, String tipo, String day, 
			String yearWeek, String week, String yearMonth, String month, String dtda, String dtal, String eventcode) {
		
		int tot = 0;
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			// Per periodi
			int annoDa = 0;
        	int meseDa = 0;
        	int giornoDa = 0;
			String meseDaP = "";
			String giornoDaP = "";
			
        	int annoAl = 0;
        	int meseAl = 0;
        	int giornoAl = 0;
        	String meseAlP = "";
			String giornoAlP = "";
			
        	Calendar c = null;
        	
			switch (tipo) {
	            case "D":  
	            	sql = "select sum(numero) tot from analytics_eventgrouped e "
	    					+ "where e.id_store in("+idstores+") and e.eventcode = '"+eventcode+"' "
	    					+ "and anno = "+day.substring(6,10)+" "
	    					+ "and mese = "+day.substring(3,5)+" "
	    					+ "and giorno = "+day.substring(0,2)+" ";
	            	break;
	            	
	            case "W":
	            	// Get calendar set to current date and time
	    			c = Calendar.getInstance();

	    			// Set the calendar to monday of the current week
	    			c.set(Calendar.YEAR, Integer.parseInt(yearWeek));     
	    			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
	    			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	            	
	            	// Settimana
	            	for (int d = 0; d < 7; d++) {
	            		
	            		if(d==0)
	    					dtda = sdf.format(c.getTime());
	    				if(d==6)
	    					dtal = sdf.format(c.getTime());
	    				
	    				c.add(Calendar.DATE, 1);
	    				
	            	}
	            	
	            	annoDa = Integer.parseInt(dtda.substring(6,10));
	            	meseDaP = dtda.substring(3,5);
	            	giornoDaP = dtda.substring(0,2);
	            	annoAl = Integer.parseInt(dtal.substring(6,10));
	            	meseAlP = dtal.substring(3,5);
	            	giornoAlP = dtal.substring(0,2);
	            	
	            	sql = "select sum(numero) tot from analytics_eventgrouped e "
	    					+ "where e.id_store in("+idstores+") and e.eventcode = '"+eventcode+"' "
	    					+ "and anno::text || LPAD(mese::text, 2, '0') || LPAD(giorno::text, 2, '0') "
	    					+ "between '"+annoDa+""+meseDaP+""+giornoDaP+"' and '"+annoAl+""+meseAlP+""+giornoAlP+"' ";
	            	
	            	break;
	            	
	            case "M":
	            	sql = "select sum(numero) tot from analytics_eventgrouped e "
	    					+ "where e.id_store in("+idstores+") and e.eventcode = '"+eventcode+"' "
	    					+ "and anno = "+yearMonth+" "
	    					+ "and mese = "+month+" ";
	            	break;
	            	
	            case "P":
	            	annoDa = Integer.parseInt(dtda.substring(6,10));
	            	meseDaP = dtda.substring(3,5);
	            	giornoDaP = dtda.substring(0,2);
	            	annoAl = Integer.parseInt(dtal.substring(6,10));
	            	meseAlP = dtal.substring(3,5);
	            	giornoAlP = dtal.substring(0,2);
	            	
	            	sql = "select sum(numero) tot from analytics_eventgrouped e "
	    					+ "where e.id_store in("+idstores+") and e.eventcode = '"+eventcode+"' "
	    					+ "and anno::text || LPAD(mese::text, 2, '0') || LPAD(giorno::text, 2, '0') "
	    					+ "between '"+annoDa+""+meseDaP+""+giornoDaP+"' and '"+annoAl+""+meseAlP+""+giornoAlP+"' ";
	            	 
	            	break;
	            	
			}
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			if(rs.next()) {
				tot = rs.getInt("tot");
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return tot;
		
	}
	
	
}
