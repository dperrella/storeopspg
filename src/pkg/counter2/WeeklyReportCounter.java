package pkg.counter2;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
		
		///////////
		//TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
		
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
		Connection connData = null;
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
		
		
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String weekstart = req.getParameter("weekstart");
		String dtda = "";
		String dtal = "";
		
		// kpi (solo ADMIN)
		String kpi = ""+session.getAttribute("kpi");
		kpi = Utility.noNull(kpi);
		if(kpi.length()==0)
			kpi = "VIS";
		obj.put("kpi",kpi);
		
		obj.put("weekstart", weekstart);
		
		String idcus = ""+session.getAttribute("idcus");
		idcus=Utility.noNull(idcus);
		if(idcus.length()==0)
			idcus = "0";
		
		String category = "4";
		String uid = "";
		String uid2= "";
		
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
			connData = Utility.getConnData();
			
			
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
				
				category = "5";
				descrStore = getDescription(conn, Integer.parseInt(idstores));
				obj.put("descrStore", " - Store: "+descrStore);
				
			}
			obj.put("totperc", 100/nrstores);
			
			
			
			
			System.out.println("***\n"+weekstart+"***\n");
			
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
			
			int max = 0;
			obj.put("max", max);
        	
			
        	obj.put("dtda", dtda.replaceAll("/", "-"));
        	obj.put("dtal", dtal.replaceAll("/", "-"));
        	
        	String from = "";
        	String to = "";
        	
        	System.out.println(dtda+" "+dtal);
        	
        	from = dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2);
    		to = dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2);
    		
    		System.out.println(from+" "+to);
    		
    		// UID 
			uid = "";
			sql   = "select * from uuid_generate_v1()";
			System.out.println(sql);
			st = connData.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next()) {
				uid = rs.getString(1);
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
			
			
			sql   = "select * from counter.report_counter_weekly('"+from+"','"+to+"','"+_idstores+"',"+category+", "+idcus+" )";
			System.out.println(sql);
			st = connData.prepareStatement(sql);
			rs=st.executeQuery();
			
			
			String intest = "";
			String xAxis = "";
			String total = "";
			int nrcol = 0;
			
			String array = "";
			
			
			String riga = "";
			String rott = "";
			int countrott = 0;
			int parent = 0;
			String border = "";
			String str = "";
			/*
			for(int i=0;i<nrstores;i++) {
				str+=""+i+",";
			}
			if(str.length()>0)
				str=str.substring(0, str.length()-1);
			*/
			String ddd = "";
			
			int index = 0;
			while(rs.next()) {
				
				array = ""+rs.getString("_array");
				array = Utility.noNull(array);
				
				// -------------------
				// Intestazione
				if(rs.getInt("_indice")==0) {
					String temp[] = array.split(",");
					intest = array;
					for(int i=0;i<temp.length;i++) {
						th+="<th>"+temp[i]+"</th>";
						xAxis+="'"+temp[i]+"',";
					}
				}
				
				
				
				// valori tabella
				else if(rs.getInt("_indice")>0&rs.getInt("_indice")<999) {
					
					String d = "";
					String h = "";
					
					h="00"+(rs.getInt("_indice2")-1);
	        		h=h.substring(h.length()-2,h.length())+":00";
					
	        		
	        		/*
					c = Calendar.getInstance();
	        		c.setTime(sdf.parse(dtda.replaceAll("-", "/")));
	        		c.add(Calendar.DATE, (rs.getInt("_indice")-1));
	        		
	        		int dd = c.get(Calendar.DAY_OF_WEEK);
	        		
	        		
	        		
            		if(dd==2) 
            			d = propLang.get("lun")+" "+sdf.format(c.getTime());
            		if(dd==3)
    					d = propLang.get("mar")+" "+sdf.format(c.getTime());
    				if(dd==4)
    					d = propLang.get("mer")+" "+sdf.format(c.getTime());
    				if(dd==5)
    					d = propLang.get("gio")+" "+sdf.format(c.getTime());
    				if(dd==6)
    					d = propLang.get("ven")+" "+sdf.format(c.getTime());
    				if(dd==7)
    					d = propLang.get("sab")+" "+sdf.format(c.getTime());
    				if(dd==1) 
    					d = propLang.get("dom")+" "+sdf.format(c.getTime());
    				*/
	        		
	        		
	        		
	        		
	        		if(weekstart.equals("M")) {
	        			
	        			c = Calendar.getInstance();
		        		c.setTime(sdf.parse(dtda.replaceAll("-", "/")));
		        		c.add(Calendar.DATE, (rs.getInt("_indice")-1));
		        		
		        		int dd = rs.getInt("_indice");
		        		
		        		if(dd==1) {
	            			d = propLang.get("lun")+" "+sdf.format(c.getTime());
	            			index = 1;
		        		}
	            		if(dd==2) {
	    					d = propLang.get("mar")+" "+sdf.format(c.getTime());
	    					index = 2;
	            		}
	    				if(dd==3) {
	    					d = propLang.get("mer")+" "+sdf.format(c.getTime());
	    					index = 3;
	    				}
	    				if(dd==4) {
	    					d = propLang.get("gio")+" "+sdf.format(c.getTime());
	    					index = 4;
	    				}
	    				if(dd==5) {
	    					d = propLang.get("ven")+" "+sdf.format(c.getTime());
	    					index = 5;
	    				}
	    				if(dd==6) {
	    					d = propLang.get("sab")+" "+sdf.format(c.getTime());
	    					index = 6;
	    				}
	    				if(dd==7) { 
	    					d = propLang.get("dom")+" "+sdf.format(c.getTime());
	    					index = 7;
	    				}
    				
	        		}
	        		else {
	        			
	        			c = Calendar.getInstance();
		        		c.setTime(sdf.parse(dtda.replaceAll("-", "/")));
		        		
		        		
		        		int dd = rs.getInt("_indice");
		        		
	        			if(dd==7) {
	        				
	        				c.add(Calendar.DATE, (rs.getInt("_indice")-7));
	        				
	            			d = propLang.get("dom")+" "+sdf.format(c.getTime());
	            			index = 7;
	        			}
	            		if(dd==1) {
	            			
	            			c.add(Calendar.DATE, (rs.getInt("_indice")));
	            			
	    					d = propLang.get("lun")+" "+sdf.format(c.getTime());
	    					index = 1;
	            		}
	    				if(dd==2) {
	    					
	    					c.add(Calendar.DATE, (rs.getInt("_indice")));
	    					
	    					d = propLang.get("mar")+" "+sdf.format(c.getTime());
	    					index = 2;
	    				}
	    				if(dd==3) {
	    					
	    					c.add(Calendar.DATE, (rs.getInt("_indice")));
	    					
	    					d = propLang.get("mer")+" "+sdf.format(c.getTime());
	    					index = 3;
	    				}
	    				if(dd==4) {
	    					
	    					c.add(Calendar.DATE, (rs.getInt("_indice")));
	    					
	    					d = propLang.get("gio")+" "+sdf.format(c.getTime());
	    					index = 4;
	    				}
	    				if(dd==5) {
	    					
	    					c.add(Calendar.DATE, (rs.getInt("_indice")));
	    					
	    					d = propLang.get("ven")+" "+sdf.format(c.getTime());
	    					index = 5;
	    				}
	    				if(dd==6) {
	    					
	    					c.add(Calendar.DATE, (rs.getInt("_indice")));
	    					
	    					d = propLang.get("sab")+" "+sdf.format(c.getTime());
	    					index = 6;
	    				}
	        		}
	        		
	        		//System.out.println(d+" "+h);
    				
    				
					if(!rott.equals(rs.getString("_indice"))) {
						
						System.out.println(rs.getString("_indice"));
						
						countrott+=1;
						
						riga = "<tr data-id=\""+countrott+"\" data-parent=\"0\" data-level=\"1\"><td data-column=\"name\">"+d+"</td>";
						
						ddd+=""+rs.getInt("_indice")+",";
						
						//for(int i=0;i<nrstores;i++) {
						String temp[] = array.split(",");
						for(int i=0;i<temp.length;i++) {
							str+=""+i+",";
			    			riga+= "<td><div id=\""+rs.getInt("_indice")+"_"+i+"\"></div></td>";
			    		}
						riga+= "</tr>";
						
						parent+=1;
					}
					
					
					String temp[] = array.split(",");
					for(int i=0;i<temp.length;i++) {
						td+="<td "+border+"><input type=\"hidden\" name=\""+rs.getString("_indice")+"_"+i+"\" value=\""+temp[i]+"\"/>"+temp[i]+"</td>";
					}
					
					
					period.add(new Tables2("WEEK",d,h,
							td,border,h,""+rs.getInt("_indice"), 
							riga,""+countrott,""+parent,str,"",""+index));
					
					
					td = "";
					border = "";
					riga = "";
					
					rott = rs.getString("_indice");
					
				}
				// Totali
				else if(rs.getInt("_indice")==999) {
					
					String temp[] = array.split(",");
					String temp2[] = intest.split(",");
					
					total = rs.getString("_array");
					
					nrcol = temp.length;
					for(int i=0;i<temp.length;i++) {
						sto.add(new Tables("STORES","",temp[i],""+temp[i],temp2[i],"",""));
					}
				}
				
			}
			rs.close();
			st.close();
			
			
			if(weekstart.equals("S")) {
				List<Tables2> period2 = new ArrayList<Tables2>();
				period2.clear();
				
				for(int i=0;i<period.size();i++) {
					
					if(period.get(i).getCmp4().equals("7")) {
						
						period2.add(new Tables2("WEEK",period.get(i).getXcdel(),period.get(i).getXdtab(),
								period.get(i).getCmp1(),period.get(i).getCmp2(),period.get(i).getCmp3(),period.get(i).getCmp4(), 
								period.get(i).getCmp5(),period.get(i).getCmp6(),period.get(i).getCmp7(),period.get(i).getCmp8(),
								"",""));
						
					}
					
				}
				
				for(int i=0;i<period.size();i++) {
					
					if(!period.get(i).getCmp4().equals("7")) {
						
						period2.add(new Tables2("WEEK",period.get(i).getXcdel(),period.get(i).getXdtab(),
								period.get(i).getCmp1(),period.get(i).getCmp2(),period.get(i).getCmp3(),period.get(i).getCmp4(), 
								period.get(i).getCmp5(),period.get(i).getCmp6(),period.get(i).getCmp7(),period.get(i).getCmp8(),
								"",""));
						
					}
					
				}
				
				period.clear();
				period = period2;
			}
			
			

			
			
			if(str.length()>0)
				str=str.substring(0, str.length()-1);
			
			obj.put("str", str);
			if(ddd.length()>0)
				ddd=ddd.substring(0, ddd.length()-1);
			obj.put("dd", ddd);
			
			obj.put("th", th);
			
			if(xAxis.length()>0)
				xAxis=xAxis.substring(0, xAxis.length()-1);
			obj.put("intest", intest);
			obj.put("xAxis", xAxis);
			obj.put("total", total);
			obj.put("nrcol", nrcol);
			
			
			obj.put("uid", uid);
        	
    		obj.put("from", from);
        	obj.put("to", to);
        	
        	
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
		
		return new ModelAndView("/counter2/report/weekly-report-counter", obj);
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
	
	public class DaySorter implements Comparator<Tables2>{

	    public int compare(Tables2 one, Tables2 another){
	        int returnVal = 0;

	    if(one.getCmp4().equals(another.getCmp4())){
	        returnVal =  -1;
	    }else if(one.getCmp4().equals(another.getCmp4())){
	        returnVal =  1;
	    }else if(one.getCmp4().equals(another.getCmp4())){
	        returnVal =  0;
	    }
	    return returnVal;
	    }
	}
	
}
