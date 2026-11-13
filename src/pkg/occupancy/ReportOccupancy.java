package pkg.occupancy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables2;
import pkg.beans.Kpi;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.TrippleDes;
import pkg.utility.Utility;

public class ReportOccupancy extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> _sto = new ArrayList<Tables2>();
		_sto.clear();
		
		List<Tables> dev = new ArrayList<Tables>();
		dev.clear();
		
		
		List<Tables> chart = new ArrayList<Tables>();
		chart.clear();
		
		List<Tables> timechart = new ArrayList<Tables>();
		timechart.clear();
		
		List<Tables> piechart = new ArrayList<Tables>();
		piechart.clear();
		
		List<Kpi> kpilist = new ArrayList<Kpi>();
		kpilist.clear();
		
		ArrayList<HashMap<String, String>> kpiList;
		kpiList = new ArrayList<>();
		kpiList.clear();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
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
		String idcus = (String) session.getAttribute("idcus");
		
		Connection connAdmin = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		// -------------------
		String kpi = "";
		int nrkpi = 0;
		String idstores = "";
		int nrstores = 0;
		String dtda = "";
		String dtal = "";
		String dates = "No Date Selected";
		String comparison = "";
		String comparison_type = "No Comparison";
		String tipoPeriod = "";
		String tipo2 = "";
		String quarter = "";
		String group = "";
		String upd = "";
		
		int rowspan = 0;
		
		
		try {
			
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnData();
			
			
			// UID 
			String uid = ""+req.getParameter("uid");
			uid = Utility.noNull(uid);
			if(uid.length()==0) {
				System.out.println("uid=null");
				sql   = "select * from uuid_generate_v1()";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				if(rs.next()) {
					uid = rs.getString(1);
				}
				rs.close();
			}
			obj.put("uid",uid);
			
			String uid2 = "";
			sql   = "select * from uuid_generate_v1()";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()) {
				uid2 = rs.getString(1);
			}
			rs.close();
			st.close();
			
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(currentYear), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
			  
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			System.out.println(currentWeek);
			// ========================
			
			kpi = ""+req.getParameter("kpi");
			kpi = Utility.noNull(kpi);
			
			kpi = "OCCC,OCCW,OCCE";
			
			idstores = ""+req.getParameter("idstores");
			idstores = Utility.noNull(idstores);
			
			dtda = ""+req.getParameter("_dtda");
			dtda = Utility.noNull(dtda);
			
			dtal = ""+req.getParameter("_dtal");
			dtal = Utility.noNull(dtal);
			
			tipoPeriod = ""+req.getParameter("tipoPeriod");
			tipoPeriod = Utility.noNull(tipoPeriod);
			
			tipo2 = ""+req.getParameter("tipo2");
			tipo2 = Utility.noNull(tipo2);
			
			quarter = ""+req.getParameter("quarter");
			quarter = Utility.noNull(quarter);
			
			comparison = ""+req.getParameter("comparison");
			comparison = Utility.noNull(comparison);
			
			group = ""+req.getParameter("group");
			group = Utility.noNull(group);
			
			if(tipoPeriod.length()==0) {
				tipoPeriod = "W";
			}
			
			if(comparison.trim().length()==0) {
				comparison = "C01";
			}
			
			upd = ""+req.getParameter("upd");
			upd = Utility.noNull(upd);
			
			
			String kpi_selected = ""+req.getParameter("kpi_selected");
			kpi_selected = Utility.noNull(kpi_selected);
			
			String kpi_desc_selected = "";
			
	 		// Se idcus e' valorizzato
			
			System.out.println(".....");
			System.out.println(idstores);
			System.out.println(tipo);
			System.out.println(req.getParameter("day"));
			
			System.out.println(".....");
			
			
			
			
			
			
			
			
			switch (tipoPeriod.trim()) {
        	case "D":  
        		
        		dtda = req.getParameter("day");
        		dtal = req.getParameter("day");
        		
				obj.put("heatmapHeight", 140);
				obj.put("perc", 50);
        		
        		break;
        		
        	case "W": 
        		
        		sql = "select (date_trunc('week',i))::date,(date_trunc('week',i) + '6 days'::interval)::date from (select to_timestamp('"+req.getParameter("week")+" "+req.getParameter("yearWeek")+"','W YYYY') i) a ";
        		st = conn.prepareStatement(sql);
        		rs=st.executeQuery();
        		if(rs.next()) {
        			dtda = sdf.format(rs.getDate(1));
        			dtal = sdf.format(rs.getDate(2));
        		}
        		rs.close();
        		
				obj.put("heatmapHeight", 440);
				obj.put("perc", 70);
				
    			break;
    			
        	case "M": 
        		
        		c = Calendar.getInstance(Locale.GERMAN);

    			// Set the calendar to monday of the current week
    			c.set(Integer.parseInt(req.getParameter("yearMonth")), Integer.parseInt(req.getParameter("month"))-1, 1);     
    			
    			dtda = "01-"+req.getParameter("month")+"-"+req.getParameter("yearMonth");
            	dtal = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"-"+req.getParameter("month")+"-"+req.getParameter("yearMonth");
        		
				obj.put("heatmapHeight", 450);
				obj.put("perc", 70);
				
            	break;
            	
        	case "P": 
        		
        		dtda = req.getParameter("_dtda");
        		dtal = req.getParameter("_dtal");
        		
				
        		// Se le date sono identiche passa a visualizzazione "D"
        		if(dtda.equals(dtal)) {
        			tipo="D";
        			tipoPeriod="D";
        			
    				obj.put("heatmapHeight", 140);
    				obj.put("perc", 50);
        		}
        		else {
    				obj.put("heatmapHeight", 450);
    				obj.put("perc", 70);
    				
        		}
        		
        		
        		
        		
        		break;
        		
			}
			
			if(idcus.length()>0) {
			
				
				String temp[]=idstores.split(",");
				nrstores = temp.length;
				dates=dtda+" / "+dtal;
				String temp2[]=kpi.split(",");
				nrkpi = temp2.length;		
				comparison_type = comparison;
				
				String col = "";
				
				switch (nrkpi) {
		            case 1:  
		            	col = "col-lg-12";
		            break;
		            case 2:  
		            	col = "col-lg-6";
		            break;
		            case 3:  
		            	col = "col-lg-4";
		            break;
		            case 4:  
		            	col = "col-lg-3";
		            break;
		            case 5:  
		            	col = "col-lg-2-4";
		            break;
		            case 6:  
		            	col = "col-lg-2";
		            break;
				}
				
				
				// kpi per sql selezione per giorno
				String kpi_in = "";
				String kpi_in2 = "";
				
				String temp3[] = kpi.split(",");
				rowspan = 0;
				for(int i=0;i<temp3.length;i++) {
					rowspan+=1;
					kpi_in+="''"+temp3[i]+"'',";
					kpi_in2+="'"+temp3[i]+"',";
				}
				if(kpi_in.length()>0)
					kpi_in=kpi_in.substring(0,kpi_in.length()-1);
				if(kpi_in2.length()>0)
					kpi_in2=kpi_in2.substring(0,kpi_in2.length()-1);
				
				
				
				
				
				
				System.out.println("------");
				System.out.println(tipoPeriod);
				System.out.println(dtda);
				System.out.println(dtal);
				System.out.println(quarter);
				System.out.println(idstores.replaceAll(",","*"));
				System.out.println(kpi.replaceAll(",","*"));
				
				comparison = comparison.trim();
				
				group = ""+group;
				group = Utility.noNull(group);
				if(group.length()==0)
					group="4";
				
				obj.put("kpi", kpi);
				obj.put("nrkpi", nrkpi);
				obj.put("nrstores", nrstores);
				obj.put("idstores", idstores);
				obj.put("comparison", comparison);
				obj.put("comparison_type", comparison_type);
				obj.put("dtda", dtda);
				obj.put("dtal", dtal);
				obj.put("quarter", quarter);
				obj.put("tipoPeriod", tipoPeriod);
				obj.put("kpi_in", kpi_in2);
				obj.put("group", group);
				obj.put("dates", dates);
				
				
				//Formattazione data
				Date d1 = sdf.parse(dtda);
				dtda = iso.format(d1);
				Date d2 = sdf.parse(dtal);
				dtal = iso.format(d2);
				
				System.out.println(dtda+" "+dtal);
				
				
				
				// rowspan
				int span = 0;
				
				String xxx = kpi.replaceAll(",","*");
				String tempxxx[] = xxx.split(",");
				span = tempxxx.length;
				
				// *******
				// kpilist...
				/*
				sql   = "select * from landing_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"') , "
						+ "ztables where xcdtb = 'KPI' and trim(_kpi) = trim(xcdel) order by _kpi ";
				*/
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		        System.out.println(timestamp);
				
				sql   = "select * from landing_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', '"+uid+"') , "
						+ "ztables where xcdtb = 'KPI' and trim(_kpi) = trim(xcdel) order by order_position, _kpi  ";
				
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				
				String sel = "";
				String active = "";
				int count = 0;
				while(rs.next()) {

					count+=1;
					
					if(kpi_selected.length()==0) {
						
						if(count==1) {
							sel = "bg-light dk";
							active = "active";
							kpi_selected = rs.getString("xcdel");
							kpi_desc_selected = rs.getString("xdtab");
						}
						else {
							sel = "";
							active = "";
						}
					}
					else {
						
						if(rs.getString("xcdel").equals(kpi_selected)) {
							sel = "bg-light";
							active = "active";
							kpi_desc_selected = rs.getString("xdtab");
						}
						else {
							sel = "";
							active = "";
						}
						
					}
					
					//kpi+=rs.getString("xcdel").trim()+",";
					kpilist.add(new Kpi("KPI",rs.getString("xcdel"),rs.getString("xdtab"), "", rs.getString("xdesc"), 
							rs.getBoolean("calculated"), rs.getString("color"), rs.getString("kpi_symbol"),
							""+rs.getDouble("_inc"),rs.getString("pretty_num"),sel,active,
							"","","",""));
					
				}
				rs.close();
				st.close();
				
				obj.put("col", col);
				obj.put("kpilist", kpilist);
				obj.put("kpi_selected", kpi_selected);
				obj.put("kpi_desc_selected", kpi_desc_selected);
				
				
				timestamp = new Timestamp(System.currentTimeMillis());
		        System.out.println(timestamp);
				
		        String tp = tipoPeriod;
		        if(tipoPeriod.equals("P")) {
		        	tp = "W";
		        	tipoPeriod = "W";
		        }
		        	
//				sql   = "select * from test.report_grid('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"','"+tipoPeriod+"',false, 5 ,'C01', '"+uid+"')  ";
//				System.out.println(sql);
//				st = conn.prepareStatement(sql);
//				rs = st.executeQuery();
//				rs.close();
				
				timestamp = new Timestamp(System.currentTimeMillis());
		        System.out.println(timestamp);
				
		        
		        
		        sql   = "select a.*, xdtab, color from test.report_grid('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"','"+tp+"',false, 5 ,'C01', '"+uid+"') a , " + 
						"ztables where xcdtb = 'KPI' and trim(codkpi) = trim(xcdel) " + 
						"and idcat in(select idcat from test.report_grid('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"','"+tp+"',false, 5 ,'C01', '"+uid+"') group by idcat having sum(tot) <> 0) " + 
						"order by category, codkpi ";
				
				System.out.println("\n"+sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				
				// Var per grafico
		        String stores = "";
		        
				int num = 0;
		        
		        String td = "";
		        
		        String descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Area</th><th style=\"border-top: 2px solid #E0E0E0;\">KPI</th>";
				
				String deviceList = "";
				String val = "";
				
				num = 0;
				count = 0;
				
				
				String category = "";
				
				while(rs.next()) {
					
					category = rs.getString("category").replaceAll("'", " ");
					
					if(count==0) {
		        		
		        		if(num>0) {
		        			
		        			//System.out.print(rs.getString("category")+" ");
		        			// Inserimento in array della riga
			        		_sto.add(new Tables2("STO",""+num,td,
			        				"",category,"","","","","","","",""));
			        		
		        		}
		        		td="";
		        		
		        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\">"
		        			+ "<b>"+rs.getString("category").replaceAll("'", " ")+"</b></td>";
		        		
		        		
		        		
					}
					if(count>=1&&count<=rowspan)
		        		td+="<tr>";
					
					td+="<td class=\"b-r\" style=\"width:120px\">"+rs.getString("xdtab")+"</td>";
					
					for(int i=0;i<55;i++) {
						if(rs.getString((i+6))!=null) {
							//System.out.print(rs.getString((i+6))+" ");
							td+="<td class=\"b-r text-right\">"+Utility.round(rs.getDouble((i+6)) , 2)+"</td>";
							
						}
					}
					
					td+="<td class=\"b-r text-right\" style=\"width:120px\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
     				
					//System.out.println(rs.getString("tot"));
					
					td+="</tr>";
		        	
					count+=1;
		        	if(count==rowspan) {
		        		count = 0;
		        	}
		        	num+=1;
		        	
				}
				rs.close();
				st.close();
				
				// Inserimento in array della riga (ULTIMA RIGA)
	    		_sto.add(new Tables2("STO",""+num,td,
	    				"",category,"","","","","","","",""));
	    		
	    		
	    		timestamp = new Timestamp(System.currentTimeMillis());
		        System.out.println(timestamp);
				
				obj.put("td", td);
				obj.put("_sto", _sto);
				
				
				
				
				
				int nrdev = 0;
				
				obj.put("deviceList", deviceList);
				obj.put("timechart", timechart);
				obj.put("piechart", piechart);
				obj.put("nrdev", nrdev);
				
				String chart_col = "8";
				String chart_col2= "4";
				
				if(nrdev<=1) {
					chart_col = "12";
					chart_col2= "0";
				}
				else {
					chart_col = "8";
					chart_col2= "4";
				}
				
				obj.put("chart_col", chart_col);
				obj.put("chart_col2", chart_col2);
				
				obj.put("td", td);
				obj.put("_sto", _sto);
				
				String th=descr;
		        
				String times="";
		        
				
				// Intestazione
				switch (tipoPeriod) {
	         	case "Y": 
	         		
	         		
	         		Calendar startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		Calendar endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));

	         		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	         		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	         		
	         		for(int i=0;i<=diffMonth;i++) {
	         			
	         			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
	         			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
	         			
	         			startCalendar.add(Calendar.MONTH, 1);
	         			
	         		}
	         		
	         		
	         		break;
	         		
	         	case "YW": 
	         		
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));
	         		
	         		long diffWeeks = 0;
	         		for( long t=1; ; t++ ) {         
	         			startCalendar.add( Calendar.WEEK_OF_YEAR, 1 );   // add one day, week, year, etc.
	         			if( startCalendar.after(endCalendar) ) {
	         	            diffWeeks = t-1;
	         	            break;
	         			}
	         	    }
	         		
	         			         		
	         		for(int i=0;i<=diffWeeks;i++) {
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">W"+(i+1)+"</th>";
	         		
	         			times+="'W"+i+"',";
	         			
	         		}
	         		
	         		break;
	         		
	         	case "Q":
	         		
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));

	         		diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	         		diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	         		
	         		for(int i=0;i<=diffMonth;i++) {
	         			
	         			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
	         			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
	         			
	         			startCalendar.add(Calendar.MONTH, 1);
	         			
	         		}
	         		
	         		break;
	         		
	         	case "M":
				
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));
	         		
	         		Date startDate = startCalendar.getTime();
	         		Date endDate = endCalendar.getTime();
	         		long startTime = startDate.getTime();
	         		long endTime = endDate.getTime();
	         		long diffTime = endTime - startTime;
	         		double abc = diffTime / (1000.0 * 60.0 * 60.0 * 24.0);
	        		long diffDays = (long) Math.round(abc);                  
	        		
	         		DateFormat dateFormat = DateFormat.getDateInstance();
	         		System.out.println("The difference between "+
	         		  dateFormat.format(startDate)+" and "+
	         		  dateFormat.format(endDate)+" is "+
	         		  diffDays+" days.");
	         		
	         		for(int i=0;i<=diffDays;i++) {
	         			
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
	         			times+="'"+iso.format(startCalendar.getTime())+"',";
	         			
	         			startCalendar.add(Calendar.DATE, 1);
	         			
	         		}
	         		
	         		break;
	         		
	         	case "W":
	         		
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));
	         		
	         		startDate = startCalendar.getTime();
	         		endDate = endCalendar.getTime();
	         		startTime = startDate.getTime();
	         		endTime = endDate.getTime();
	         		diffTime = endTime - startTime;
	         		abc = diffTime / (1000.0 * 60.0 * 60.0 * 24.0);
	        		diffDays = (long) Math.round(abc);                  
	        		
	         		dateFormat = DateFormat.getDateInstance();
	         		System.out.println("The difference between "+
	         		  dateFormat.format(startDate)+" and "+
	         		  dateFormat.format(endDate)+" is "+
	         		  diffDays+" days.");
	         		
	         		for(int i=0;i<=diffDays;i++) {
	         			
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
	         			times+="'"+iso.format(startCalendar.getTime())+"',";
	         			
	         			startCalendar.add(Calendar.DATE, 1);
	         			
	         		}
	         		
	         		break;
	         		
	         	
	         		
	         	case "D":
	         		
	         		for(int i=0;i<24;i++) {
	         			String hh = "00"+i;
	         			hh=hh.substring(hh.length()-2,hh.length());
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+hh+":00</th>";
	         		}
	         		times+="'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10,11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24',";
	         		
	         		break;
				}
				
				th+="<th style=\"border-top: 2px solid #E0E0E0;\">Total</th>";
				
				obj.put("th", th);
				
				if(times.length()>0)
					times=times.substring(0, times.length()-1);
				
				obj.put("times", times);
				
			}
			
			// Total / avg
			String total = ReportTotal.getTotal(conn, rowspan, dtda, dtal, idstores, kpi, tipoPeriod, "false", group, comparison, uid);
			
			obj.put("total", total);
			
			// HEATMAP
			String xAxis = "";
			String yAxis = "";
			int max = 0;
			int height = 0;
			String heatmap = "";
				
	 		height = 500;
	 		
	 		
			
			obj.put("xAxis", xAxis);
			obj.put("yAxis", yAxis);
			obj.put("heatmap", heatmap);
			obj.put("max", max);
			obj.put("height", height);
			
			obj.put("dsstore", getDescr(conn, idstores.replaceAll(",","*")));
			
			String store = getStore(conn, idstores.replaceAll(",","*"));
			obj.put("store", store);
			
			String license = "";
			try {
		        TrippleDes td = new TrippleDes();
		        
		        System.out.println(store);
		        license = td.encrypt(store);
		    }
		    catch(Exception e) {
		        e.printStackTrace();
		    }
			
			obj.put("license", license);
			
			
			//////////////
			// Temperature
			
//			sql = "select distinct idcat, category "
//					+ "from test.temperature_grid('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+tipoPeriod+"',false, 5 ,'C01',"
//					+ "'"+uid2+"') a , ztables where xcdtb = 'KPI' and trim(codkpi) = trim(xcdel) "
//					+ "and idcat in(select idcat from test.temperature_grid('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+tipoPeriod+"',false, 5 ,'C01',"
//					+ "'"+uid2+"') group by idcat having sum(tot) <> 0) order by idcat ";
//			
			
			sql = "select door_number as idcat, door_description as category from storeareas where id_store = "+idstores.replaceAll(",","*")+" ";
			st = conn.prepareStatement(sql);
			
			
			
			rs = st.executeQuery();
			while(rs.next()) {
				
				dev.add(new Tables("DEV",rs.getString("idcat"),rs.getString("category"),"","","",""));
				
			}
			rs.close();
			st.close();
			
			obj.put("dev", dev);
			obj.put("uid2", uid2);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
				if(connAdmin!=null)
					connAdmin.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("oggi", oggiIta);
		obj.put("currentYear", currentYear);
		obj.put("idcus", idcus);
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		obj.put("chart", chart);
		
		return new ModelAndView("/occupancy/report/report-occupancy", obj);
	}
	
	private static String getDescr(Connection conn, String store) {
		String descr = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql= "select store_id, store_name from stores where id = "+store+" ";
			System.out.println("***** "+sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				descr = rs.getString("store_id")+" "+rs.getString("store_name");
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
 		
		return descr;
	}
	
	private static String getStore(Connection conn, String id) {
		String store = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql= "select account_id||'-'||store_code store from stores where id = "+id+" ";
			System.out.println("***** "+sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				store = rs.getString("store");
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
 		
		return store;
	}
	
	public static String getXAxis(Connection conn, String idstores) {
		
		String xAxis = "";
		String min = "0";
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql="select " + 
				"min(extract(hour from fromhour)) m1, " + 
				"max(extract(hour from tohour)) m2 " + 
				"from dailyoperation_stores where id_stores in( "+idstores+" )";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				int m1 = rs.getInt("m1");
				int m2 = rs.getInt("m2");
				
				String s1 = ""+rs.getString("m1");
				String s2 = ""+rs.getString("m2");
				s1=Utility.noNull(s1);
				s2=Utility.noNull(s2);
				
				
				if(s1.length()>0&&s2.length()>0) {
					
					min = rs.getString("m1");
				
					for(int i=m1;i<=m2;i++) {
						String h1 = "00"+i;
						String h2 = "00"+(i+1);
						h1=h1.substring(h1.length()-2,h1.length());
						h2=h2.substring(h2.length()-2,h2.length());
						xAxis+="'"+h1+"-"+h2+"',";
					}
					
					if(xAxis.length()>0)
						xAxis = xAxis.substring(0, xAxis.length()-1);
				
				}
				else {
					xAxis = "'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'";
				}
			}
			else {
				xAxis = "'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'";
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
		
		return xAxis+"##@@"+min;
		
	}
	
}
