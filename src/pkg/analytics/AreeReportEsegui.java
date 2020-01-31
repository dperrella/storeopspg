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

import pkg.beans.Aree;
import pkg.beans.Tables2;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.AreaMovement;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AreeReportEsegui extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		List<Tables> wk = new ArrayList<Tables>();
		List<Tables> mn = new ArrayList<Tables>();
		List<Aree> aree = new ArrayList<Aree>();
		List<Tables> intest = new ArrayList<Tables>();
		List<Tables> data = new ArrayList<Tables>();
		
		
		// Pulisce la lista
		wk.clear();
		mn.clear();
		aree.clear();
		intest.clear();
		data.clear();
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		String id_store = ""+req.getParameter("id_store");
		id_store=Utility.noNull(id_store);
		
		String orario = ""+req.getParameter("orario");
		orario=Utility.noNull(orario);
		
		obj.put("orario", orario);
		
		String tableFlussi = "vw_flussi";
		
		if(orario.equals("S")) {
			tableFlussi = "vw_flussi_orari";
		}
		
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
		
		String idaree = "";
		String dsaree = "";
		String[] itemsAree = req.getParameterValues("id_area");
		
		int nraree = 0;
		if(itemsAree==null){
			System.out.println("NULL - AREE");
		}
		else {
			System.out.println("NO NULL - AREE");
			for(int i = 0; i < itemsAree.length; i++){
				nraree+=1;
			    idaree+=""+itemsAree[i]+",";
			    dsaree+="descrizione"+itemsAree[i]+" int,";
			}
		}
		if(idaree.length()>0) {
			idaree = idaree.substring(0,idaree.length()-1);
			dsaree = dsaree.substring(0,dsaree.length()-1);
		}
		System.out.println(idaree);
		
		String req_day = req.getParameter("day");
		String req_yearWeek = req.getParameter("yearWeek");
		String req_week = req.getParameter("week");
		String req_yearMonth = req.getParameter("yearMonth");
		String req_month = req.getParameter("month");
		String req_dtda = req.getParameter("dtda");
		String req_dtal = req.getParameter("dtal");
		String req_tipo = req.getParameter("tipo");
		String rep = req.getParameter("rep");
		
		obj.put("nraree", nraree);
		obj.put("idaree", idaree);
		
		obj.put("day", req_day);
		obj.put("yearWeek", req_yearWeek);
		obj.put("week", req_week);
		obj.put("yearMonth", req_yearMonth);
		obj.put("month", req_month);
		obj.put("dtda", req_dtda);
		obj.put("dtal", req_dtal);
		obj.put("tipo", req_tipo);
		obj.put("rep", rep);
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAnalytics();
			
			// ==================
			// STORES UTENTE
			// ==================
			// sql da eseguire
			sql  =  "select a.*, c.brandname custbrand, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "and a.id = "+id_store+" ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $description="";
			
			if(rs.next()) {
				$description=rs.getString("description");
			}
			rs.close();
			st.close();
			obj.put("description_store",$description);
			
			// =======================
			// Mesi
			String mm = "";
			String desMm = "";
			String $cmp1 = "";
			
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
			
			// Se è stato passato l'id di un negozio carico la lista delle aree...
			if(id_store.length()>0) {
				
				sql  =  "select a.id_store, a.id_area, a.descrizione description, "
						+ "a.codice, b.description store_description, esterna, gruppo, tipo "
						+ "from tab_aree a, stores b "
						+ "where a.id_store = b.id and a.id_store = "+id_store+" "
						+ "and a.gruppo = 'CIS' "
						+ "order by a.id_area";
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				// Campi database
				String $id_store="";
				String $id_area="";
				String $aree_description="";
				String $codice="";
				String $store_description="";
				String $esterna="";
				String $gruppo="";
				String $tipo="";
			
				$cmp1="";
				String $cmp2="";
				String $cmp3="";
				String $cmp4="";
				
				String tempAree[] = idaree.split(",");
				String selArea = "";
				while(rs.next()) {
					
					for(int i =0; i < tempAree.length ; i++) {
						 
						 if(tempAree[i].equals(rs.getString("id_area"))) {
							 selArea = " selected=\"selected\" ";
						 }
					}
					$id_store=rs.getString("id_store");
					$id_area=rs.getString("id_area");
					$aree_description=rs.getString("description");
					$codice=rs.getString("codice");
					$store_description=rs.getString("store_description");
					$esterna=rs.getString("esterna");
					if($esterna.toUpperCase().equals("T"))
						$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-out\"></i> "+propLang.get("aar_N")+"</span>";
					else
						$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-in\"></i> "+propLang.get("aar_S")+"</span>";
					$gruppo=rs.getString("gruppo");
					$tipo=rs.getString("tipo");
					aree.add(new Aree($id_store,$id_area,$aree_description,$codice,$store_description,$esterna,$gruppo,$tipo,
							"","",selArea,$cmp2,$cmp3,$cmp4));
					
					selArea = "";
				}
				rs.close();
				st.close();
				
				// REPORT!!!
				
				int width = Math.round(80/tempAree.length);
				System.out.println(width);
				
				$xcdtb = "";
				$xcdel = "";
				$xdtab = "";
				
				c = null;
				String table = "";
				String th = "";
				
				int totaleGenerale = 0;
				
				switch (req_tipo) {
	        	case "D":  
	        		
	        		req_dtda = req_day;
	        		req_dtal = req_day;
	        		
	        		String sqlArea = "";
	        		
	        		for(int i = 0; i < itemsAree.length; i++){
	        			
	        			sqlArea+="coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								"and hour = cast(to_char(i, 'HH24') as int) "+ 
								") , 0) count"+itemsAree[i]+", "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") "+
								"description"+itemsAree[i]+",";
					
	        		}
	        		
	        		if(sqlArea.length()>0)
	        			sqlArea = sqlArea.substring(0,sqlArea.length()-1);
	        		
	        		sql   = "select i::date dt, i::time tm, "+
							sqlArea+
							" from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 00:00:00', "+
							"'"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 23:00:00', '1 hour'::interval) i ";
	        		
	        		
	        		/*
	        		sql   = "select * from crosstab('select extract(hour from i)::integer tm,id_area,sum(ingressi) conteggio from "
	        				+ "(select distinct i, id_area,descrizione,id_store "
	        				+ "from generate_series(''"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 00:00:00'', "
	        				+ "''"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 23:00:00'', ''1 hour''::interval) i,tab_aree) a "
	        				+ "join vw_flussi_orari v on a.id_store = v.id_store and v.year = extract(year from i) and v.month = extract(month from i) and v.day = extract(day from i) and v.hour = extract(hour from i) "
	        				+ "and id_area_to = a.id_area "
	        				+ "where v.id_store = "+id_store+" "
	        				+ "group by  extract(hour from i)::integer ,id_area order by extract(hour from i)::integer' "
	        				+ ",'select id_area from tab_aree where id_store = "+id_store+" and id_area in ("+idaree+") order by id_area') "
	        				+ "AS ct(tm int, "+dsaree+" )";	
	        		*/
	        		
        			System.out.println(sql);
        			// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
    				String tm = "";
					while(rs.next()) {
    					
						tm = rs.getString("tm");
						
						table+= "<tr><td>"+req_dtda+"</td>"
							 +	"<td>"+tm.substring(0,2)+":00</td>";
						th = "<tr><th>Date</th><th>Time</th>";
						
						for(int i = 0; i < itemsAree.length; i++){
							th+="<th style=\"width:"+width+"%\">"+rs.getString("description"+itemsAree[i]+"")+"</th>";
							table+="<td style=\"text-align:right\"><b>"+rs.getString("count"+itemsAree[i]+"")+"</b></td>";
						}
						table+="</tr>";
						th+="</tr>";
    				}
    				rs.close();
    				st.close();
    				
    				obj.put("th", th);
    				obj.put("table", table);
    				
    				for(int i = 0; i < itemsAree.length; i++){
	        			sql   = "select i::date dt, i::time tm, "+
								"coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								"and hour = cast(to_char(i, 'HH24') as int) "+ 
								") , 0) count, "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") description "+
								"from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 00:00:00', "+
								"'"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 23:00:00', '1 hour'::interval) i ";
	        			System.out.println(sql);
	        			// Prepara il cursore
        				st = conn.prepareStatement(sql);
        				
        				// Esegue l'sql
        				rs=st.executeQuery();
        				
        				String dati = "";
    					int tot = 0;
    					String area_description = "";
    					
    					while(rs.next()) {
        					
    						tot+=rs.getInt("count");
    						dati+="["+rs.getString("tm").substring(0,2)+", "+rs.getString("count")+"],";
    						area_description = rs.getString("description");
    						
        				}
        				rs.close();
        				st.close();
        				
        				if(dati.length()>0)
    						dati=dati.substring(0,dati.length()-1);
        				
    					dati="var a"+itemsAree[i]+" = ["+dati+"];";
    					
    					data.add(new Tables("AREE",itemsAree[i],dati,""+tot,area_description,table,""));
    					
    					totaleGenerale+=tot;

                		table = "";
    					dati = "";
    					tot = 0;
	        		}
	        		
    				// --------------------------
    				
    				
    				
    				
    				
    				
    				
    				sql   = "select * from crosstab('select extract(hour from i)::integer tm,id_area,sum(ingressi) conteggio from "
	        				+ "(select distinct i, id_area,descrizione,id_store "
	        				+ "from generate_series(''"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 00:00:00'', "
	        				+ "''"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+" 23:00:00'', ''1 hour''::interval) i,tab_aree) a "
	        				+ "join vw_flussi_orari v on a.id_store = v.id_store and v.year = extract(year from i) and v.month = extract(month from i) and v.day = extract(day from i) and v.hour = extract(hour from i) "
	        				+ "and id_area_to = a.id_area "
	        				+ "where v.id_store = "+id_store+" "
	        				+ "group by  extract(hour from i)::integer ,id_area order by extract(hour from i)::integer' "
	        				+ ",'select id_area from tab_aree where id_store = "+id_store+" and id_area in ("+idaree+") order by id_area') "
	        				+ "AS ct(tm int, "+dsaree+" )";	
    				
    				
    				
    				
    				
    				
    				
    				
    				
    				
	        		break;
	        		
	        	case "W":
	        		
	        		c = Calendar.getInstance();

	    			// Set the calendar to monday of the current week
	    			c.set(Calendar.YEAR, Integer.parseInt(req_yearWeek));     
	    			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(req_week));     
	    			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    			
	    			req_dtda = sdf.format(c.getTime());
	    			c.add(Calendar.DATE, 6);
	    			req_dtal = sdf.format(c.getTime());
	    			
	    			sqlArea = "";
	        		
	        		for(int i = 0; i < itemsAree.length; i++){
	        			
	        			sqlArea+="coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								") , 0) count"+itemsAree[i]+", "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") "+
								"description"+itemsAree[i]+",";
					
	        		}
	        		
	        		if(sqlArea.length()>0)
	        			sqlArea = sqlArea.substring(0,sqlArea.length()-1);
	        		
	        		sql   = "select i::date dt, i::time tm, "+
							sqlArea+
							" from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+"', "+
							"'"+req_dtal.substring(6,10)+"-"+req_dtal.substring(3,5)+"-"+req_dtal.substring(0,2)+"', '1 day'::interval) i ";
        			System.out.println(sql);
        			// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
					while(rs.next()) {
    					
						table+= "<tr><td>"+dateIta.format(rs.getDate("dt"))+"</td>"
							 +	"<td>0-24</td>";
						th = "<tr><th>Date</th><th>Time</th>";
						
						for(int i = 0; i < itemsAree.length; i++){
							th+="<th style=\"width:"+width+"%\">"+rs.getString("description"+itemsAree[i]+"")+"</th>";
							table+="<td style=\"text-align:right\"><b>"+rs.getString("count"+itemsAree[i]+"")+"</b></td>";
						}
						table+="</tr>";
						th+="</tr>";
    				}
    				rs.close();
    				st.close();
    				
    				obj.put("th", th);
    				obj.put("table", table);
    				
    				for(int i = 0; i < itemsAree.length; i++){
	        			sql   = "select i::date dt, i::time tm, "+
								"coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								") , 0) count, "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") description "+
								"from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+"', "+
							    "'"+req_dtal.substring(6,10)+"-"+req_dtal.substring(3,5)+"-"+req_dtal.substring(0,2)+"', '1 day'::interval) i ";
	        			System.out.println(sql);
	        			// Prepara il cursore
        				st = conn.prepareStatement(sql);
        				
        				// Esegue l'sql
        				rs=st.executeQuery();
        				
        				String dati = "";
    					int tot = 0;
    					String area_description = "";
    					
    					while(rs.next()) {
        					
    						tot+=rs.getInt("count");
    						dati+="["+rs.getDate("dt").getTime()+", "+rs.getString("count")+"],";
    						area_description = rs.getString("description");
    						
        				}
        				rs.close();
        				st.close();
        				
        				if(dati.length()>0)
    						dati=dati.substring(0,dati.length()-1);
        				
    					dati="var a"+itemsAree[i]+" = ["+dati+"];";
    					
    					data.add(new Tables("AREE",itemsAree[i],dati,""+tot,area_description,table,""));
    					
    					totaleGenerale+=tot;
    					
                		table = "";
    					dati = "";
    					tot = 0;
	        		}
	    			
	        		break;
	        		
	        	case "M":
	        		
	        		c = Calendar.getInstance();
	        		c.set(Calendar.MONTH, Integer.parseInt(req_month)-1);
	        		
	        		req_dtda = "01/"+req_month+"/"+req_yearMonth;
	            	req_dtal = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+req_month+"/"+req_yearMonth;
	            	
	            	sqlArea = "";
	        		
	        		for(int i = 0; i < itemsAree.length; i++){
	        			
	        			sqlArea+="coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								") , 0) count"+itemsAree[i]+", "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") "+
								"description"+itemsAree[i]+",";
					
	        		}
	        		
	        		if(sqlArea.length()>0)
	        			sqlArea = sqlArea.substring(0,sqlArea.length()-1);
	        		
	        		sql   = "select i::date dt, i::time tm, "+
							sqlArea+
							" from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+"', "+
							"'"+req_dtal.substring(6,10)+"-"+req_dtal.substring(3,5)+"-"+req_dtal.substring(0,2)+"', '1 day'::interval) i ";
        			System.out.println(sql);
        			// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
					while(rs.next()) {
    					
						table+= "<tr><td>"+dateIta.format(rs.getDate("dt"))+"</td>"
							 +	"<td>0-24</td>";
						th = "<tr><th>Date</th><th>Time</th>";
						
						for(int i = 0; i < itemsAree.length; i++){
							th+="<th style=\"width:"+width+"%\">"+rs.getString("description"+itemsAree[i]+"")+"</th>";
							table+="<td style=\"text-align:right\"><b>"+rs.getString("count"+itemsAree[i]+"")+"</b></td>";
						}
						table+="</tr>";
						th+="</tr>";
    				}
    				rs.close();
    				st.close();
    				
    				obj.put("th", th);
    				obj.put("table", table);
    				
    				for(int i = 0; i < itemsAree.length; i++){
	        			sql   = "select i::date dt, i::time tm, "+
								"coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								") , 0) count, "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") description "+
								"from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+"', "+
							    "'"+req_dtal.substring(6,10)+"-"+req_dtal.substring(3,5)+"-"+req_dtal.substring(0,2)+"', '1 day'::interval) i ";
	        			System.out.println(sql);
	        			// Prepara il cursore
        				st = conn.prepareStatement(sql);
        				
        				// Esegue l'sql
        				rs=st.executeQuery();
        				
        				String dati = "";
    					int tot = 0;
    					String area_description = "";
    					
    					while(rs.next()) {
        					
    						tot+=rs.getInt("count");
    						dati+="["+rs.getDate("dt").getTime()+", "+rs.getString("count")+"],";
    						area_description = rs.getString("description");
    						
        				}
        				rs.close();
        				st.close();
        				
        				if(dati.length()>0)
    						dati=dati.substring(0,dati.length()-1);
        				
    					dati="var a"+itemsAree[i]+" = ["+dati+"];";
    					
    					data.add(new Tables("AREE",itemsAree[i],dati,""+tot,area_description,table,""));
    					
    					totaleGenerale+=tot;
    					
                		table = "";
    					dati = "";
    					tot = 0;
	        		}
	            	
	        		break;
	        		
	        	case "P":
	        		
	        		req_dtda = req_dtda;
	        		req_dtal = req_dtal;
	        		
	        		sqlArea = "";
	        		
	        		for(int i = 0; i < itemsAree.length; i++){
	        			
	        			sqlArea+="coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								") , 0) count"+itemsAree[i]+", "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") "+
								"description"+itemsAree[i]+",";
					
	        		}
	        		
	        		if(sqlArea.length()>0)
	        			sqlArea = sqlArea.substring(0,sqlArea.length()-1);
	        		
	        		sql   = "select i::date dt, i::time tm, "+
							sqlArea+
							" from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+"', "+
							"'"+req_dtal.substring(6,10)+"-"+req_dtal.substring(3,5)+"-"+req_dtal.substring(0,2)+"', '1 day'::interval) i ";
        			System.out.println(sql);
        			// Prepara il cursore
    				st = conn.prepareStatement(sql);
    				
    				// Esegue l'sql
    				rs=st.executeQuery();
    				
					while(rs.next()) {
    					
						table+= "<tr><td>"+dateIta.format(rs.getDate("dt"))+"</td>"
							 +	"<td>0-24</td>";
						th = "<tr><th>Date</th><th>Time</th>";
						
						for(int i = 0; i < itemsAree.length; i++){
							th+="<th style=\"width:"+width+"%\">"+rs.getString("description"+itemsAree[i]+"")+"</th>";
							table+="<td style=\"text-align:right\"><b>"+rs.getString("count"+itemsAree[i]+"")+"</b></td>";
						}
						table+="</tr>";
						th+="</tr>";
    				}
    				rs.close();
    				st.close();
    				
    				obj.put("th", th);
    				obj.put("table", table);
    				
    				for(int i = 0; i < itemsAree.length; i++){
	        			sql   = "select i::date dt, i::time tm, "+
								"coalesce(( "+
								"select sum(ingressi) "+
								"from "+tableFlussi+" r where id_area_from = "+itemsAree[i]+" "+
								"and id_store = "+id_store+" and year = cast(to_char(i, 'YYYY') as int) "+
								"and month = cast(to_char(i, 'MM') as int) "+
								"and day = cast(to_char(i, 'DD') as int) "+
								") , 0) count, "+
								"(select descrizione from tab_aree where id_area = "+itemsAree[i]+" and id_store="+id_store+") description "+
								"from generate_series('"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+"', "+
							    "'"+req_dtal.substring(6,10)+"-"+req_dtal.substring(3,5)+"-"+req_dtal.substring(0,2)+"', '1 day'::interval) i ";
	        			System.out.println(sql);
	        			// Prepara il cursore
        				st = conn.prepareStatement(sql);
        				
        				// Esegue l'sql
        				rs=st.executeQuery();
        				
        				String dati = "";
    					int tot = 0;
    					String area_description = "";
    					
    					while(rs.next()) {
        					
    						tot+=rs.getInt("count");
    						dati+="["+rs.getDate("dt").getTime()+", "+rs.getString("count")+"],";
    						area_description = rs.getString("description");
    						
        				}
        				rs.close();
        				st.close();
        				
        				if(dati.length()>0)
    						dati=dati.substring(0,dati.length()-1);
        				
    					dati="var a"+itemsAree[i]+" = ["+dati+"];";
    					
    					data.add(new Tables("AREE",itemsAree[i],dati,""+tot,area_description,table,""));
    					
    					totaleGenerale+=tot;
    					
                		table = "";
    					dati = "";
    					tot = 0;
	        		}
	        		
	        		break;
				
				}
				
				
				obj.put("totIngressi", totaleGenerale);
				
				
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
		
		obj.put("aree", aree);
		obj.put("data", data);
		obj.put("intest", intest);
		
		obj.put("id_store", id_store);
		
		return new ModelAndView("/app/report/aree-report-esegui", obj);
	}
	
}
