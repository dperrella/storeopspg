package pkg.counterAS;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import pkg.beans.ccn.Stores;
import pkg.properties.ReadProperties;
import pkg.utility.Pivot;
import pkg.utility.Utility;
import pkg.utility.Pivot.PivotReport;

public class ReportCounter extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		///////////
		//TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		ReadProperties prop = new ReadProperties("connection.properties");
		String SAAS_endpoint = prop.get("saas_endpoint_dan");
		
		List<Tables> period = new ArrayList<Tables>();
		List<Tables> sto = new ArrayList<Tables>();
		List<Tables2> adj = new ArrayList<Tables2>();
		List<Tables> test = new ArrayList<Tables>();
		List<Tables> stores = new ArrayList<Tables>();
		
		period.clear();
		sto.clear();
		adj.clear();
		test.clear();
		stores.clear();
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat us = new SimpleDateFormat("M/d/yyyy hh:mm:ss aa");
		
		
				
		Connection connData = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String msg = "";
		
		String tipo = req.getParameter("tipo");
		obj.put("tipo", tipo);
		
		String idstores = "";
		String codestores = "";
		String[] itemsStores = req.getParameterValues("id_store");
		obj.put("_itemsStores", itemsStores);
		
		String _idstores = "";
		
		String idcus = ""+session.getAttribute("idcus");
		idcus=Utility.noNull(idcus);
		if(idcus.length()==0)
			idcus = "0";
		
		int nrstores = 0;
		int colspan = 1;
		if(itemsStores==null){
			System.out.println("NULL - STORES");
		}
		else {
			System.out.println("NO NULL - STORES");
			for(int i = 0; i < itemsStores.length; i++){
				nrstores+=1;
			    idstores+=""+itemsStores[i]+",";
			    codestores+="''"+itemsStores[i]+"'',";
			    _idstores+="\""+itemsStores[i]+"\",";
			}
		}
		if(idstores.length()>0)
			idstores = idstores.substring(0,idstores.length()-1);
		
		if(_idstores.length()>0)
			_idstores = _idstores.substring(0,_idstores.length()-1);
		
		if(codestores.length()>0)
			codestores = codestores.substring(0,codestores.length()-1);
		
		System.out.println(_idstores);
		
		
		
		
		
		boolean oneStore = false;
		String iddevices = "";
		
		if(nrstores>1) {
			colspan = 2;
		}
		
		
		
		String day = req.getParameter("day");
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String yearMonth = req.getParameter("yearMonth");
		String month = req.getParameter("month");
		String dtda = req.getParameter("dtda");
		String dtal = req.getParameter("dtal");
		
		obj.put("_day", day);
		obj.put("_yearWeek", yearWeek);
		obj.put("_week", week);
		obj.put("_yearMonth", yearMonth);
		obj.put("_month", month);
		obj.put("_dtda", dtda);
		obj.put("_dtal", dtal);
		
		
		String category = "4";
		String uid = "";
		String uid2= "";
		
		// kpi (solo ADMIN)
		String kpi = ""+session.getAttribute("kpi");
		kpi = Utility.noNull(kpi);
		if(kpi.length()==0)
			kpi = "VIS";
		
		String kpiout = ""+session.getAttribute("kpiout");
		kpiout = Utility.noNull(kpiout);
		if(kpiout.length()==0)
			kpiout = "EXI";
		
		
		String _verso = req.getParameter("verso");
		String kkk = "";
		session.setAttribute("_verso", _verso);
		if(_verso.equals("I")) {
			obj.put("chkI", "active");
			obj.put("chkO", "");
			kkk = kpi;
		}
		if(_verso.equals("O")) {
			obj.put("chkI", "");
			obj.put("chkO", "active");
			kkk = kpiout;
		}
		
		
		
		
		
		
		int af = 0;
		
		try {
			// Apre la connessione
			connData = Utility.getConnData();
			
			String formula = "";
			sql  =  "SELECT json_agg(kpi_formulacomp) FROM kpi_formulacomp\r\n"
					+ "where kpi in ('"+kkk+"')";
			System.out.println(sql);
			st = connData.prepareStatement(sql);
			rs=st.executeQuery();
			if(rs.next()) {
				formula = rs.getString(1);
			}
			rs.close();
			
			System.out.println(formula);
			
			stores = Stores.getStoresByAccountCodeAndUsernameAndStores(connData, idcus, session.getAttribute("username").toString(), _idstores.replaceAll("\"", "'"));
			
			String latlon = "";
			String ids = "";
			String desstores = "";
			for(Tables store : stores) {
				ids+=store.getXcdel()+",";
				latlon+=store.getCmp1()+";"+store.getCmp2()+",";
				desstores+=store.getXdtab().replaceAll(","," ")+",";
			}
			if(ids.length()>0)
				ids=ids.substring(0,ids.length()-1);
	        if(latlon.length()>0)
	        	latlon=latlon.substring(0,latlon.length()-1);
	        if(desstores.length()>0)
	        	desstores=desstores.substring(0,desstores.length()-1);
	        System.out.println(latlon);
	        
        	// Stores
			String th = "";
			
			Calendar c = null;
			String td = "";
			
			
			String descrStore = "";
			if(nrstores==1) {
				
				category = "5";
				descrStore = getDescription(connData, idcus,idstores);
				obj.put("descrStore", " - Store: "+descrStore);
				
			}
			
			obj.put("totperc", 100/nrstores);
			
			// Orari apertura negozi
			sql   = "SELECT * FROM crosstab('select distinct ''(''||rpad(store_code,4,'' '')||'') ''||rpad(description,30,'' '')||'' "
					+ "| Adj.Factor : ''||adjustment_factor negozio,days::text giorno, fromhour::text||'' - ''||tohour::text orario "
					+ "from dailyoperation_stores join stores_counter on id_stores = stores_counter.id join devices on stores_counter.id = id_store "
					+ "where "
					+ "stores_counter.store_code in ("+codestores+") and stores_counter.account_id = ''"+idcus+"'' "
					+ "or "
					+ "stores_counter.store_code in ("+codestores+") and stores_counter.account_code_evolvestore = ''"+idcus+"'' "
					+ "order by 1,2') "
					+ "AS ct (\"negozio\" text,\"lun\" text, \"mar\" text, \"mer\" text, \"gio\" text, \"ven\" text, \"sab\" text, \"dom\" text)";
			System.out.println(sql);
			st = connData.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				adj.add(new Tables2("ADJ",rs.getString("negozio"),"",
						rs.getString("lun"),rs.getString("mar"),rs.getString("mer"),rs.getString("gio"),
						rs.getString("ven"),rs.getString("sab"),rs.getString("dom"),"","",""));
			}
			rs.close();
			st.close();
			obj.put("adj", adj);
			
			
			String from = "";
			String to = "";
			String max = "450";
			String top = "0%";
			
			
			
			
			
			switch (tipo) {
        	case "D":  
        		
        		dtda = day;
        		dtal = day;
        		max = "180";
        		top = "25%";
        		
        		from = day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2);
        		to = day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2);
        		
        		break;
        		
        	case "W": 
        		
        		max = "450";
        		
        		sql = "select (date_trunc('week',i))::date,(date_trunc('week',i) + '6 days'::interval)::date from (select to_timestamp('"+week+" "+yearWeek+"','W YYYY') i) a ";
        		st = connData.prepareStatement(sql);
        		rs=st.executeQuery();
        		if(rs.next()) {
        			dtda = sdf.format(rs.getDate(1));
        			dtal = sdf.format(rs.getDate(2));
        			from = rs.getString(1);
        			to = rs.getString(2);
        		}
        		rs.close();
        		st.close();
        		
    			break;
    			
        	case "M": 
        		
        		max = "450";
        		c = Calendar.getInstance(Locale.GERMAN);

    			// Set the calendar to monday of the current week
    			c.set(Integer.parseInt(yearMonth), Integer.parseInt(month)-1, 1);     
    			
    			dtda = "01/"+month+"/"+yearMonth;
            	dtal = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+month+"/"+yearMonth;

            	from = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
        		to = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
        		
            	break;
            	
        	case "P": 
        		
        		max = "450";
        		tipo = "W";
        		from = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
        		to = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
        		
        		// Se le date sono identiche passa a visualizzazione "D"
        		if(from.equals(to)) {
        			tipo="D";
        			max = "180";
        			top = "25%";
        		}
        		
        		break;
        		
			}
			
			obj.put("max", max);
			obj.put("top", top);
			
			obj.put("from", from);
			obj.put("to", to);
			
			String tipoReq = "";
			if(tipo.equals("D"))
				tipoReq = "H";
			if(tipo.equals("W"))
				tipoReq = "D";
			if(tipo.equals("M"))
				tipoReq = "D";
			if(tipo.equals("P"))
				tipoReq = "D";
			
			String groupByDoor = "false";
			if(nrstores==1)
				groupByDoor = "true";
				
			// test
			groupByDoor = "false";
			
			String jsonParm = "{"
					+ "\"account_id\": \""+idcus+"\","
					+ "\"stores\": ["
					+ _idstores
					+ "],"
					+ "\"kpi\": ["
					+ "\""+kkk+"\""
					+ "],"
					+ "\"from_date\": \""+from+"\","
					+ "\"to_date\": \""+to+"\","
					+ "\"time_grouped\": \""+tipoReq+"\","
					+ "\"groupByDoor\" : "+groupByDoor+", "
					+ "\"calc\": "+formula+" "
					+ "}";
			
			System.out.println(jsonParm);
						
			org.json.JSONObject json = Utility.consumeRest(SAAS_endpoint, "POST", jsonParm, "");
			org.json.JSONArray data = json.getJSONArray("listings");
			
			List<PivotData> reports = new ArrayList<PivotData>();
			List<PivotData> totals = new ArrayList<PivotData>();
			List<PivotData> charts = new ArrayList<PivotData>();
			
			reports = Pivot.fillZero(from,to,desstores,"1",tipoReq);
			charts = Pivot.fillZero(from, to, desstores, "2",tipoReq);
			totals = Pivot.fillZero(from,from,desstores,"1","D");
			
			
			
			for (int i = 0; i < data.length(); i++) {
				if(tipoReq.equals("H")) {
					reports.add(new PivotData(String.format("%02d", Integer.parseInt(""+data.getJSONObject(i).get("time"))),data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName"), data.getJSONObject(i).getInt("value")));
					charts.add(new PivotData(data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName"), String.format("%02d", Integer.parseInt(""+data.getJSONObject(i).get("time"))), data.getJSONObject(i).getInt("value")));
				}
				else {
					reports.add(new PivotData(iso.format(us.parse(data.getJSONObject(i).getString("date"))),data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName"), data.getJSONObject(i).getInt("value")));
					charts.add(new PivotData(data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName"), iso.format(us.parse(data.getJSONObject(i).getString("date"))), data.getJSONObject(i).getInt("value")));
				}
				totals.add(new PivotData(from,data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName"), data.getJSONObject(i).getInt("value")));
				
			}
			
			PivotReport report = PivotReport.create(reports);
			PivotReport _total = PivotReport.create(totals);
			PivotReport _chart = PivotReport.create(charts);
			
			System.out.println("--- ************************");
			System.out.println(_total.toString());
			System.out.println("************************ ---");
			
	        String _data = report.toString();
	        String arr[] = _data.split("\n");
	        
	        String _dataT = _total.toString();
	        String arrT[] = _dataT.split("\n");
	        
	        String _dataC = _chart.toString();
	        String arrC[] = _dataC.split("\n");
	        
	        
	        String intest = "";
			String xAxis = "";
			String total = "";
			int nrcol = 0;
			
			String array = "";
			
			String datesss = "";
			
			int index = 0;
			String period2 = "";
	        for(String row : arr) {
	        	System.out.println(row);
	        	
	        	array = row.replaceAll(",", " ").replaceAll("'", "&apos;");
	        	array = array.replaceAll("\t", ",");
				array = Utility.noNull(array);
				// Intestazione
				if(index==0) {
					String temp[] = array.split(",");
					intest = array;
					for(int i=1;i<temp.length;i++) {
						
						if(colspan==1) {
							th+="<th>"+temp[i]+"</th>";
						}
						else {
							th+="<th></th><th>"+temp[i]+"</th>";
						}
						
						//th+="<th colspan=\""+colspan+"\">"+temp[i]+"</th>";
						xAxis+="'"+temp[i]+"',";
					}
				}
				// valori tabella
				else if(index>0) {
					
					String d = "";
					String d2= "";
					
					switch (tipo) {
		        	case "D":  
		        		
		        		d="00"+(index-1);
		        		d2=""+Integer.parseInt(((d.substring(d.length()-2,d.length()))+"00"));
		        		
		        		d=d.substring(d.length()-2,d.length())+":00";
		        		
		        		
		        		break;
		        	case "W": 
		        			
		        		c = Calendar.getInstance(Locale.GERMAN);
		        		c.setTime(sdf.parse(dtda.replaceAll("-", "/")));
		        		c.add(Calendar.DATE, (index-1));
		        		
		        		int dd = c.get(Calendar.DAY_OF_WEEK);
		        		
		        		
                		if(dd==2) 
                			d = propLang.get("lun")+" "+iso.format(c.getTime());
                		if(dd==3)
        					d = propLang.get("mar")+" "+iso.format(c.getTime());
        				if(dd==4)
        					d = propLang.get("mer")+" "+iso.format(c.getTime());
        				if(dd==5)
        					d = propLang.get("gio")+" "+iso.format(c.getTime());
        				if(dd==6)
        					d = propLang.get("ven")+" "+iso.format(c.getTime());
        				if(dd==7)
        					d = propLang.get("sab")+" "+iso.format(c.getTime());
        				if(dd==1) 
        					d = propLang.get("dom")+" "+iso.format(c.getTime());
        				
        				d2=""+iso.format(c.getTime());
        				
        				datesss+=d2+";";
		        		
		        		break;
		        	case "M": 
		        		
		        		c = Calendar.getInstance(Locale.GERMAN);
		        		c.setTime(sdf.parse(dtda.replaceAll("-", "/")));
		        		c.add(Calendar.DATE, (index-1));
		        		
		        		dd = c.get(Calendar.DAY_OF_WEEK);
		        		
		        		
                		if(dd==2) 
                			d = propLang.get("lun")+" "+iso.format(c.getTime());
                		if(dd==3)
        					d = propLang.get("mar")+" "+iso.format(c.getTime());
        				if(dd==4)
        					d = propLang.get("mer")+" "+iso.format(c.getTime());
        				if(dd==5)
        					d = propLang.get("gio")+" "+iso.format(c.getTime());
        				if(dd==6)
        					d = propLang.get("ven")+" "+iso.format(c.getTime());
        				if(dd==7)
        					d = propLang.get("sab")+" "+iso.format(c.getTime());
        				if(dd==1) 
        					d = propLang.get("dom")+" "+iso.format(c.getTime());
		        		
        				d2=""+iso.format(c.getTime());
        				
        				datesss+=d2+";";
		        		
		        		break;
		        	case "P": 
		        		
		        		c = Calendar.getInstance(Locale.GERMAN);
		        		c.setTime(sdf.parse(dtda.replaceAll("-", "/")));
		        		c.add(Calendar.DATE, (index-1));
		        		
		        		dd = c.get(Calendar.DAY_OF_WEEK);
		        		
		        		
                		if(dd==2) 
                			d = propLang.get("lun")+" "+iso.format(c.getTime());
                		if(dd==3)
        					d = propLang.get("mar")+" "+iso.format(c.getTime());
        				if(dd==4)
        					d = propLang.get("mer")+" "+iso.format(c.getTime());
        				if(dd==5)
        					d = propLang.get("gio")+" "+iso.format(c.getTime());
        				if(dd==6)
        					d = propLang.get("ven")+" "+iso.format(c.getTime());
        				if(dd==7)
        					d = propLang.get("sab")+" "+iso.format(c.getTime());
        				if(dd==1) 
        					d = propLang.get("dom")+" "+iso.format(c.getTime());
		        		
        				d2=""+iso.format(c.getTime());
        				
        				datesss+=d2+";";
		        		
		        		break;
		        	}
					
					String temp[] = array.split(",");
					for(int i=1;i<temp.length;i++) {
						if(nrstores>1)
							td+="<td style=\"width:30px\"><span class=\"pull-left thumb-xs avatar\" id=\"ico_"+(i)+"_"+d2+"\">" + 
								"<img src=\"../../images/loader_xs.gif\" style=\"width:24px\"/>" + 
								"</span></td>";
						td+="<td>"+temp[i]+"</td>";
					}
					
					period.add(new Tables("DAY",d,d,td,d2,"",""+index));
					period2+="'"+d2+"',";
    				td = "";
				}
				
				index++;
				
	        }
	        
	        if(period2.length()>0)
	        	period2=period2.substring(0,period2.length()-1);
	        obj.put("period2", period2);
	        
	        
	        // Totals
	        index = 0;
	        for(String tot : arrT) {
	        	System.out.println(tot);
	        	
	        	if(index>0) { 
		        	String _array = tot.replaceAll("\t", ",");
		        	_array = _array.replaceAll(from+",", "");
		        	_array = Utility.noNull(_array);
		        	
		        	String temp[] = _array.split(",");
					String temp2[] = intest.split(",");
						
					total = _array;
					
					nrcol = temp.length;
					for(int i=0;i<temp.length;i++) {
						sto.add(new Tables("STORES","",temp[i],""+temp[i],temp2[i+1],"",""+(i+1)));
					}
	        	}
	        	
				index++;
	        }
	        
	        
	        
			
			index = 0;
	        for(String ch : arrC) {
	        	System.out.println(ch);
	        	
	        	if(index>0) { 
		        	String _array = ch.replaceAll("\t", ",");
		        	_array = Utility.noNull(_array);
		        	
		        	String temp[] = _array.split(",");
		        	int t = 0;
		        	String l = "";
		        	for(int i=1;i<temp.length;i++) {
		        		t+=Integer.parseInt(temp[i]);
		        		l+=temp[i]+",";
		        	}
		        	if(l.length()>0)
		        		l=l.substring(0,l.length()-1);
		        	test.add(new Tables("CHARTS",temp[0],l,""+t,"","",""));
		        	
		        	
		        	
//		        	String temp[] = _array.split(",");
//					String temp2[] = intest.split(",");
//						
//					total = _array;
//					
//					nrcol = temp.length;
//					for(int i=0;i<temp.length;i++) {
//						sto.add(new Tables("STORES","",temp[i],""+temp[i],temp2[i],"",""+(i+1)));
//					}
	        	}
	        	
				index++;	
	        }
	        
	        obj.put("test", test);
	        
			
			
			if(Integer.parseInt(category)==5)
				latlon = getLatLon(connData, idstores);
			
			if(datesss.length()>0)
				datesss = datesss.substring(0,datesss.length()-1);
			
			obj.put("datesss", datesss);
			
			
			String query = "";
			
			ArrayList<String> q = new ArrayList<String>();
			int n = 0;
			String query2 = "";
			
			System.out.println(latlon);
			
			String temp4[] = latlon.split(",");
			for(int i=0;i<temp4.length;i++) {
				
				float iLat = 0;
				float iLon = 0;
				String lat = "0";
				String lon = "0";
				
				try {
					String ll[] = temp4[i].split(";");
					lat = ll[0];
					lon = ll[1];
					
					iLat = (int) Math.round((Double.parseDouble(lat)*1000));
					iLon = (int) Math.round((Double.parseDouble(lon)*1000));
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
				query+=iLat/1000+","+iLon/1000+";";
				query2+=iLat/1000+","+iLon/1000+";";
				n++;
				if(n==50) {
					
					if(query2.length()>0)
						query2=query2.substring(0,query2.length()-1);
					q.add(query2);
					query2="";
					n=0;
				}
			}
			
			if(n>0) {
				if(query2.length()>0)
					query2=query2.substring(0,query2.length()-1);
				q.add(query2);
			}
			
			if(query.length()>0)
				query=query.substring(0, query.length()-1);
			
			if(q.size()==0)	
				q.add(query);
		
				
			obj.put("query", query);
			obj.put("q", q);
			
			
			
			obj.put("th", th);
			
			if(xAxis.length()>0)
				xAxis=xAxis.substring(0, xAxis.length()-1);
			obj.put("intest", intest);
			obj.put("xAxis", xAxis);
			obj.put("total", total);
			obj.put("nrcol", nrcol);
			
			
			
			
			String chart_col = "8";
			String chart_col2= "4";

			if(nrstores<=1) {
				chart_col = "12";
				chart_col2= "0";
			}
			else {
				chart_col = "8";
				chart_col2= "4";
			}
			
			obj.put("chart_col", chart_col);
			obj.put("chart_col2", chart_col2);
			
			
			
        	
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		finally {
			if(st!=null)
				st.close();
			if(connData!=null)
				connData.close();
		}
		
		obj.put("dsfun", "Report Counter");
		
		obj.put("dtda", dtda.replaceAll("/", "-"));
		obj.put("dtal", dtal.replaceAll("/", "-"));
		
		obj.put("msg", msg);
		
		// parametri passati dalla maschera
		obj.put("nrstores", nrstores);
		obj.put("idstores", idstores);
		obj.put("_idstores", _idstores);
		
		// Periodo analizzato (con dati)
		obj.put("period", period);
		obj.put("tipoPeriod", tipo);
		obj.put("uid", uid);
		obj.put("category", category);
		obj.put("kpi", kpi);
		obj.put("kpiout", kpiout);
		obj.put("af", af);
		
		// Stores per Charts
		obj.put("sto", sto);
		
		return new ModelAndView("/counterAS/report/report-counter", obj);
	}
	
	private static String getDescription(Connection conn, String customer_id, String id_store) {
		
		String description = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql   = "select description from stores where account_id = ? and store_code = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, customer_id);
			st.setString(2, id_store);
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
	
	private static String getLatLon(Connection conn, String id_store) {
		
		String latlon = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql   = "select latitude, longitude from stores where store_code = ? ";
			st = conn.prepareStatement(sql);
			
			st.setString(1, id_store);
			
			System.out.println(st.toString());
			rs=st.executeQuery();
			if(rs.next()) {
				latlon = rs.getString("latitude")+";"+rs.getString("longitude");
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
		
		return latlon;
		
	}
	
}
