package pkg.counterPlus;


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

public class ReportCounter_BKP extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		///////////
		//TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
		
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
		Connection connData = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String msg = "";
		
		String tipo = req.getParameter("tipo");
		obj.put("tipo", tipo);
		
		String idstores = "";
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
		
		if(nrstores>1) {
			colspan = 2;
		}
		
		
		String _verso = req.getParameter("verso");
		session.setAttribute("_verso", _verso);
		if(_verso.equals("I")) {
			obj.put("chkI", "active");
			obj.put("chkO", "");
		}
		if(_verso.equals("O")) {
			obj.put("chkI", "");
			obj.put("chkO", "active");
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
		
		
		int af = 0;
		
		try {
			// Apre la connessione
			conn = Utility.getConnCounter();
			connData = Utility.getConnData();
			
			// UID 
			uid = "";
			uid2= "";
			sql   = "select * from uuid_generate_v1()";
			System.out.println(sql);
			st = connData.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()) {
				uid = rs.getString(1);
			}
			rs.close();
			st.close();
			sql   = "select * from uuid_generate_v1()";
			System.out.println(sql);
			st = connData.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()) {
				uid2 = rs.getString(1);
			}
			rs.close();
			st.close();
			
			////////////////////////////////////
			/*
			sql   = "set timezone to 'UTC-2'; ";
			st = connData.prepareStatement(sql);
			st.execute();
			st.close();
			*/
			////////////////////////////////////
			
        	// Stores
			String th = "";
			
			String giorno = "";
			Calendar c = null;
			String td = "";
			
			
			String descrStore = "";
			if(nrstores==1) {
				
				category = "5";
				descrStore = getDescription(conn, Integer.parseInt(idstores));
				obj.put("descrStore", " - Store: "+descrStore);
				
			}
			
			/*
			sql = "select adjustment_factor from ad_store where id_store in("+idstores+") group by adjustment_factor";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			if(rs.next()) {
				af = rs.getInt("adjustment_factor");
			}
			rs.close();
			
			// forzo af=0 xkè ora è gestito a query
			af = 0;
			
			
			//Adjustment Factor
			System.out.println("Adjustment Factor: "+af);
			*/
			
			obj.put("totperc", 100/nrstores);
			
			// Orari apertura negozi
			sql   = "SELECT * FROM crosstab('select distinct ''(''||rpad(store_code,4,'' '')||'') ''||rpad(description,30,'' '')||'' "
					+ "| Adj.Factor : ''||adjustment_factor negozio,days::text giorno, fromhour::text||'' - ''||tohour::text orario "
					+ "from dailyoperation_stores join stores on id_stores = stores.id join devices on stores.id = id_store "
					+ "where id_stores in ("+idstores+") order by 1,2') "
					+ "AS ct (\"negozio\" text,\"lun\" text, \"mar\" text, \"mer\" text, \"gio\" text, \"ven\" text, \"sab\" text, \"dom\" text)";
			System.out.println(sql);
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				adj.add(new Tables2("ADJ",rs.getString("negozio"),"",
						rs.getString("lun"),rs.getString("mar"),rs.getString("mer"),rs.getString("gio"),
						rs.getString("ven"),rs.getString("sab"),rs.getString("dom"),"","",""));
			}
			rs.close();
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
        		/*
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
    			
    			from = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
        		to = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
        		*/
        		
        		
        		sql = "select (date_trunc('week',i))::date,(date_trunc('week',i) + '6 days'::interval)::date from (select to_timestamp('"+week+" "+yearWeek+"','W YYYY') i) a ";
        		st = conn.prepareStatement(sql);
        		rs=st.executeQuery();
        		if(rs.next()) {
        			dtda = sdf.format(rs.getDate(1));
        			dtal = sdf.format(rs.getDate(2));
        			from = rs.getString(1);
        			to = rs.getString(2);
        		}
        		rs.close();
        		
        		
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
			
			
			
			// forzo idstores per avere dati )Da TOGLIERE)
			//_idstores = "1750*1610";
			//idstores = "1750,1610";
			sql   = "select * from counter.report_counter_plus2('"+from+"', '"+to+"', '"+_idstores+"', '"+tipo+"', "+category+", "+idcus+", '"+_verso+"') ";
			
			System.out.println(sql);
			st = connData.prepareStatement(sql);
			rs=st.executeQuery();
			
			
			String intest = "";
			String xAxis = "";
			String total = "";
			int nrcol = 0;
			
			String array = "";
			String latlon = "";
			
			String datesss = "";
			while(rs.next()) {
				
				array = ""+rs.getString("_array");
				array = Utility.noNull(array);
				// Intestazione
				if(rs.getInt("_indice")==0) {
					String temp[] = array.split(",");
					intest = array;
					for(int i=0;i<temp.length;i++) {
						th+="<th colspan=\""+colspan+"\">"+temp[i]+"</th>";
						xAxis+="'"+temp[i]+"',";
					}
				}
				// valori tabella
				else if(rs.getInt("_indice")>0&rs.getInt("_indice")<999) {
					
					String d = "";
					String d2= "";
					
					switch (tipo) {
		        	case "D":  
		        		
		        		d="00"+(rs.getInt("_indice")-1);
		        		d2=""+Integer.parseInt(((d.substring(d.length()-2,d.length()))+"00"));
		        		
		        		d=d.substring(d.length()-2,d.length())+":00";
		        		
		        		
		        		break;
		        	case "W": 
		        			
		        		c = Calendar.getInstance(Locale.GERMAN);
		        		c.setTime(sdf.parse(dtda.replaceAll("-", "/")));
		        		c.add(Calendar.DATE, (rs.getInt("_indice")-1));
		        		
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
		        		c.add(Calendar.DATE, (rs.getInt("_indice")-1));
		        		
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
		        		c.add(Calendar.DATE, (rs.getInt("_indice")-1));
		        		
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
					for(int i=0;i<temp.length;i++) {
						if(nrstores>1)
							td+="<td style=\"width:30px\"><a href=\"#\" class=\"pull-left thumb-xs avatar\" id=\"ico_"+(i+1)+"_"+d2+"\">" + 
								"<img src=\"../../images/loader_xs.gif\" style=\"width:24px\"/>" + 
								"</a></td>";
						td+="<td>"+temp[i]+"</td>";
					}
					
					period.add(new Tables("DAY",d,d,td,d2,"",""+rs.getInt("_indice")));
    				td = "";
				}
				// Totali
				else if(rs.getInt("_indice")==999) {
					
					String temp[] = array.split(",");
					String temp2[] = intest.split(",");
					
					
					
					total = rs.getString("_array");
					
					nrcol = temp.length;
					for(int i=0;i<temp.length;i++) {
						sto.add(new Tables("STORES","",temp[i],""+temp[i],temp2[i],"",""+(i+1)));
					}
					
				}
				// lat/lon
				else if(rs.getInt("_indice")==1001) {
					
					latlon = rs.getString("_array");
					
				}
				
				
			}
			rs.close();
			st.close();
			
			if(Integer.parseInt(category)==5)
				latlon = getLatLon(conn, Integer.parseInt(_idstores));
			
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
					String ll[] = temp4[i].split("\\*");
					lat = ll[0];
					lon = ll[1];
					
					iLat = (int) Math.round((Double.parseDouble(lat)*1000));
					iLon = (int) Math.round((Double.parseDouble(lon)*1000));
				}
				catch(Exception e) {}
				
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
			
			
			
			
			
        	
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		finally {
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
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
		
		return new ModelAndView("/counterPlus/report/report-counter", obj);
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
	
	private static String getLatLon(Connection conn, int id_store) {
		
		String latlon = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql   = "select latitude, longitude from stores where id = ? ";
			st = conn.prepareStatement(sql);
			
			st.setInt(1, id_store);
			
			System.out.println(st.toString());
			rs=st.executeQuery();
			if(rs.next()) {
				latlon = rs.getString("latitude")+"*"+rs.getString("longitude");
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
