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

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.utility.Utility;

public class Report1_3 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables> period = new ArrayList<Tables>();
		List<Tables> sto = new ArrayList<Tables>();
		period.clear();
		sto.clear();
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		PreparedStatement st2 = null;
		ResultSet rs2 = null;
		
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
			
			// Totale per allarmi...
			int $tot2 = ReportDash.getAlarmTot(conn, idstores, tipo, day, yearWeek, week, yearMonth, month, dtda, dtal, alarm2);
			obj.put("totAlarm2", $tot2);
			
			// Stores
			String th = "";
			String th2 = "";
			for(int i = 0; i < itemsStores.length; i++){
				sql  =  "select id, description from stores where id = "+itemsStores[i]+" ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				if(rs.next()) {
					th+="<th colspan=\"3\" class=\"b-r\">"+rs.getString("description")+"</th>";
					th2+= "<th class=\"b-r\">"+alarm1_descr+"</th>"
						+ "<th class=\"b-r\">"+alarm2_descr+"</th>"
						+ "<th class=\"b-r\">%</th>";
				}
				rs.close();
				st.close();
			}
			obj.put("th", th);
			obj.put("th2", th2);
        	
			// --------
			
			Calendar c = null;
			String td = "";
			
        	switch (tipo) {
        	case "D":  
        		
        		for(int i = 0; i < itemsStores.length; i++){
        			
        			sql   = "select i::date dt, i::time tm, "
	        				+ "coalesce(( "
	        				+ "select sum(conteggio) "
	        				+ "from rep_std_md_daily r where eventcode = '"+alarm1+"' "
	        				+ "and id_store = "+itemsStores[i]+" and anno = cast(to_char(i, 'YYYY') as int) "
	        				+ "and mese = cast(to_char(i, 'MM') as int) "
	        				+ "and giorno = cast(to_char(i, 'DD') as int) "
	        				+ "and ora = cast(to_char(i, 'HH24') as int) "
	        				+ ") , 0) count1, "
	        				+ "coalesce(( "
	        				+ "select sum(conteggio) "
	        				+ "from rep_std_md_daily r where eventcode = '"+alarm2+"' "
	        				+ "and id_store = "+itemsStores[i]+" and anno = cast(to_char(i, 'YYYY') as int) "
	        				+ "and mese = cast(to_char(i, 'MM') as int) "
	        				+ "and giorno = cast(to_char(i, 'DD') as int) "
	        				+ "and ora = cast(to_char(i, 'HH24') as int) "
	        				+ ") , 0) count2, "
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
					int tot1 = 0;
					int tot2 = 0;
					String sto_description = "";
					double rapporto = 0;
					while(rs.next()) {
						tot1+=rs.getInt("count1");
						tot2+=rs.getInt("count2");
						try {
							rapporto = (rs.getInt("count1")*100)/rs.getInt("count2");
						}
						catch(Exception e) {
							rapporto = 0;
						}
						
						dati+="["+rs.getString("tm").substring(0,2)+", "+rapporto+"],";
						sto_description = rs.getString("description");
						
					}
					rs.close();
					st.close();
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					store="var s"+itemsStores[i]+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",itemsStores[i],store,""+tot1,sto_description,""+tot2,""));
					
					dati = "";
					store = "";
					tot1 = 0;
					tot2 = 0;
					rapporto = 0;
        		}
        		
        		// Dati griglia
        		sql  = "select i::date dt, i::time tm "
        				+ "from generate_series('"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 00:00:00', "
        				+ "'"+day.substring(6,10)+"-"+day.substring(3,5)+"-"+day.substring(0,2)+" 23:00:00', '1 hour'::interval) i";
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					
					for(int i = 0; i < itemsStores.length; i++){
						
						sql   = "select s.id, "
                				+ "coalesce((select sum(conteggio) "
                				+ "from rep_std_md_daily r "
                				+ "where eventcode = '"+alarm1+"' "
                				+ "and id_store = s.id "
                				+ "and anno = "+day.substring(6,10)+" "
                				+ "and mese = "+day.substring(3,5)+" "
                				+ "and giorno = "+day.substring(0,2)+" "
                				+ "and ora = "+rs.getString("tm").substring(0,2)+") , 0) count1, "
                				+ "coalesce((select sum(conteggio) "
                				+ "from rep_std_md_daily r "
                				+ "where eventcode = '"+alarm2+"' "
                				+ "and id_store = s.id "
                				+ "and anno = "+day.substring(6,10)+" "
                				+ "and mese = "+day.substring(3,5)+" "
                				+ "and giorno = "+day.substring(0,2)+" "
                				+ "and ora = "+rs.getString("tm").substring(0,2)+") , 0) count2 "
                				+ "from stores s where s.id = "+itemsStores[i]+" "
                				+ "order by id ";
						System.out.println(sql);
						// Prepara il cursore
						st2 = conn.prepareStatement(sql);
						
						// Esegue l'sql
						rs2=st2.executeQuery();
						double rapporto = 0;
						while(rs2.next()) {
							
							try {
								rapporto = (rs2.getInt("count1")*100)/rs2.getInt("count2");
							}
							catch(Exception e) {
								rapporto = 0;
							}
							
							td+="<td>"+rs2.getString("count1")+"</td><td>"+rs2.getString("count2")+"</td><td class=\"b-r\">"+rapporto+"%</td>";
							
						}
						rs2.close();
						st2.close();
					}
					period.add(new Tables("DAY",rs.getString("tm"),"",td,"","",""));
					
					td="";
				}
				rs.close();
				st.close();
        		
        		
        		
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
    			
        		for(int i = 0; i < itemsStores.length; i++){
	        		
        			
                    sql   = "select i::date dt, "
                    		+ "coalesce(( "
                    		+ "select sum(conteggio) "
                    		+ "from group_"+yearWeek+"_daily r where eventcode = '"+alarm1+"' "
                    		+ "and id_store = "+itemsStores[i]+" "
                    		+ "and extract(year from giorno) = extract(year from i) "
                    		+ "and extract(month from giorno) = extract(month from i) "
                    		+ "and extract(day from giorno) = extract(day from i) "
                    		+ ") , 0) count1, "
                    		+ "coalesce(( select sum(conteggio) "
                    		+ "from group_"+yearWeek+"_daily r where eventcode = '"+alarm2+"' "
                    		+ "and id_store = "+itemsStores[i]+" "
                    		+ "and extract(year from giorno) = extract(year from i) "
                    		+ "and extract(month from giorno) = extract(month from i) "
                    		+ "and extract(day from giorno) = extract(day from i) "
                    		+ ") , 0) count2, "
                    		+ "(select s.description from stores s where s.id = "+itemsStores[i]+") description "
                    		+ "from generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"', "
	        				+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"', '1 day'::interval) i ";
                    
        			System.out.println(sql);
        			// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Esegue l'sql
					rs=st.executeQuery();
					
					String store = "";
					String dati = "";
					int tot1 = 0;
					int tot2 = 0;
					String sto_description = "";
					double rapporto = 0;
					while(rs.next()) {
						tot1+=rs.getInt("count1");
						tot2+=rs.getInt("count2");
						try {
							rapporto = (rs.getInt("count1")*100)/rs.getInt("count2");
						}
						catch(Exception e) {
							rapporto = 0;
						}
						dati+="["+rs.getDate("dt").getTime()+", "+rapporto+"],";
						sto_description = rs.getString("description");
						
						
					}
					rs.close();
					st.close();
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					store="var s"+itemsStores[i]+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",itemsStores[i],store,""+tot1,sto_description,""+tot2,""));
					
					dati = "";
					store = "";
					tot1 = 0;
					tot2 = 0;
					rapporto = 0;
					
        		}
        		
        		
        		// Dati griglia
        		sql  = "select i::date dt "
        				+ "from generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"', "
        				+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"', '1 day'::interval) i ";
        		
        		System.out.println(sql);
        		// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					
					for(int w = 0; w < itemsStores.length; w++){
						
						sql   = "select s.id, "
                				+ "coalesce((select sum(conteggio) "
                				+ "from group_"+yearMonth+"_daily r "
                				+ "where eventcode = '"+alarm1+"' "
                				+ "and id_store = s.id "
                				+ "and giorno = '"+iso.format(rs.getDate("dt"))+"' "
                				+ "and extract(year from giorno) = "+iso.format(rs.getDate("dt")).substring(0,4)+" "
	                    		+ "and extract(month from giorno) = "+iso.format(rs.getDate("dt")).substring(5,7)+" "
	                    		+ "and extract(day from giorno) = "+iso.format(rs.getDate("dt")).substring(8,10)+" "
                				+ ") , 0) count1, "
                				+ "coalesce((select sum(conteggio) "
                				+ "from group_"+yearMonth+"_daily r "
                				+ "where eventcode = '"+alarm2+"' "
                				+ "and id_store = s.id "
                				+ "and extract(year from giorno) = "+iso.format(rs.getDate("dt")).substring(0,4)+" "
	                    		+ "and extract(month from giorno) = "+iso.format(rs.getDate("dt")).substring(5,7)+" "
	                    		+ "and extract(day from giorno) = "+iso.format(rs.getDate("dt")).substring(8,10)+" "
                				+ ") , 0) count2 "
                				+ "from stores s where s.id = "+itemsStores[w]+" "
                				+ "order by id ";
						System.out.println(sql);
						
						// Prepara il cursore
						st2 = conn.prepareStatement(sql);
						
						// Esegue l'sql
						rs2=st2.executeQuery();
						double rapporto = 0;
						while(rs2.next()) {
							
							try {
								rapporto = (rs2.getInt("count1")*100)/rs2.getInt("count2");
							}
							catch(Exception e) {
								rapporto = 0;
							}
							
							td+="<td>"+rs2.getString("count1")+"</td><td>"+rs2.getString("count2")+"</td><td class=\"b-r\">"+rapporto+"%</td>";
							
						}
						rs2.close();
						st2.close();
					}
					
					period.add(new Tables("WEEK",sdf.format(rs.getDate("dt")),"",td,"","",""));
					
					td="";
				}
				rs.close();
				st.close();
				
        		break;
        		
        	case "M":
        		
        		c = Calendar.getInstance();
        		c.set(Calendar.MONTH, Integer.parseInt(month)-1);
        		
        		dtda = "01/"+month+"/"+yearMonth;
            	dtal = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+month+"/"+yearMonth;
            	
        		for(int i = 0; i < itemsStores.length; i++){
        			
        			
        			sql   = "select i::date dt, "
                    		+ "coalesce(( "
                    		+ "select sum(conteggio) "
                    		+ "from group_"+yearMonth+"_daily r where eventcode = '"+alarm1+"' "
                    		+ "and id_store = "+itemsStores[i]+" "
                    		+ "and extract(year from giorno) = extract(year from i) "
                    		+ "and extract(month from giorno) = extract(month from i) "
                    		+ "and extract(day from giorno) = extract(day from i) "
                    		+ ") , 0) count1, "
                    		+ "coalesce(( select sum(conteggio) "
                    		+ "from group_"+yearWeek+"_daily r where eventcode = '"+alarm2+"' "
                    		+ "and id_store = "+itemsStores[i]+" "
                    		+ "and extract(year from giorno) = extract(year from i) "
                    		+ "and extract(month from giorno) = extract(month from i) "
                    		+ "and extract(day from giorno) = extract(day from i) "
                    		+ ") , 0) count2, "
                    		+ "(select s.description from stores s where s.id = "+itemsStores[i]+") description "
                    		+ "from generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"', "
	        				+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"', '1 day'::interval) i ";
                    
        			System.out.println(sql);
        			// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Esegue l'sql
					rs=st.executeQuery();
					
					String store = "";
					String dati = "";
					int tot1 = 0;
					int tot2 = 0;
					String sto_description = "";
					double rapporto = 0;
					while(rs.next()) {
						tot1+=rs.getInt("count1");
						tot2+=rs.getInt("count2");
						try {
							rapporto = (rs.getInt("count1")*100)/rs.getInt("count2");
						}
						catch(Exception e) {
							rapporto = 0;
						}
						
						dati+="["+rs.getDate("dt").getTime()+", "+rapporto+"],";
						sto_description = rs.getString("description");
						
						
					}
					rs.close();
					st.close();
					
					if(dati.length()>0)
						dati=dati.substring(0,dati.length()-1);
					
					store="var s"+itemsStores[i]+" = ["+dati+"];";
					
					sto.add(new Tables("STORES",itemsStores[i],store,""+tot1,sto_description,""+tot2,""));
					
					dati = "";
					store = "";
					tot1 = 0;
					tot2 = 0;
					rapporto = 0;
					
        		}
        		
        		// Dati griglia
				sql  = "select i::date dt "
        				+ "from generate_series('"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"', "
        				+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"', '1 day'::interval) i ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				while(rs.next()) {
					
					for(int m = 0; m < itemsStores.length; m++){
						
						sql   = "select s.id, "
                				+ "coalesce((select sum(conteggio) "
                				+ "from group_"+yearMonth+"_daily r "
                				+ "where eventcode = '"+alarm1+"' "
                				+ "and id_store = s.id "
                				+ "and giorno = '"+iso.format(rs.getDate("dt"))+"' "
                				+ "and extract(year from giorno) = "+iso.format(rs.getDate("dt")).substring(0,4)+" "
	                    		+ "and extract(month from giorno) = "+iso.format(rs.getDate("dt")).substring(5,7)+" "
	                    		+ "and extract(day from giorno) = "+iso.format(rs.getDate("dt")).substring(8,10)+" "
                				+ ") , 0) count1, "
                				+ "coalesce((select sum(conteggio) "
                				+ "from group_"+yearMonth+"_daily r "
                				+ "where eventcode = '"+alarm2+"' "
                				+ "and id_store = s.id "
                				+ "and extract(year from giorno) = "+iso.format(rs.getDate("dt")).substring(0,4)+" "
	                    		+ "and extract(month from giorno) = "+iso.format(rs.getDate("dt")).substring(5,7)+" "
	                    		+ "and extract(day from giorno) = "+iso.format(rs.getDate("dt")).substring(8,10)+" "
                				+ ") , 0) count2 "
                				+ "from stores s where s.id = "+itemsStores[m]+" "
                				+ "order by id ";
						System.out.println(sql);
						// Prepara il cursore
						st2 = conn.prepareStatement(sql);
						
						// Esegue l'sql
						rs2=st2.executeQuery();
						double rapporto = 0;
						while(rs2.next()) {
							
							try {
								rapporto = (rs2.getInt("count1")*100)/rs2.getInt("count2");
							}
							catch(Exception e) {
								rapporto = 0;
							}
							
							td+="<td>"+rs2.getString("count1")+"</td><td>"+rs2.getString("count2")+"</td><td class=\"b-r\">"+rapporto+"%</td>";
							
						}
						rs2.close();
						st2.close();
					}
					period.add(new Tables("MONTH",sdf.format(rs.getDate("dt")),"",td,"","",""));
					
					td="";
					
				}
        		
        		break;
        		
        	}
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Report 1-3");
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
		
		return new ModelAndView("/app/report/report1-3", obj);
	}
	
}
