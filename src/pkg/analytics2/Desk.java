package pkg.analytics2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Kpi;
import pkg.beans.Tables2;
import pkg.beans.Tables3;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
		List<Kpi> kpilist = new ArrayList<Kpi>();
		kpilist.clear();
		List<Tables2> tablist = new ArrayList<Tables2>();
		tablist.clear();
		List<Tables> tablist2 = new ArrayList<Tables>();
		tablist2.clear();
		List<Tables> radarlimit = new ArrayList<Tables>();
		radarlimit.clear();
		
		
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
		
		Connection connAdmin = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		// Dalla request
		String idcus = ""+req.getParameter("idcus");
		idcus = Utility.noNull(idcus);
		
		// Se dalla request è vuoto legge dalla session
		if(idcus.length()==0) {
			idcus = ""+session.getAttribute("idcus");
			idcus = Utility.noNull(idcus);
		}
		
		int nr_customers = 0;
		int count = 0;
		
		// -------------------
		String idstores = "";
		int nrstores = 0;
		String dtda = "";
		String dtal = "";
		String dates = "No Date Selected";
		String kpi = "";
		int nrkpi = 0;
		String tipoPeriod = "";
		String tipo2 = "";
		String quarter = "";
		String group = "";
		String upd = "";
		
		
		// HEATMAP
		String xAxis = "";
		String yAxis = "";
		int max = 0;
		int height = 0;
		String heatmap = "";
			
 		height = 500;
 		
 		String legend = "[]";
		String indicator = "[]";
		
		double maxCol1 = 0;
		double maxCol2 = 0;
		double maxCol3 = 0;
		double maxCol4 = 0;
		double maxCol5 = 0;
		
		String c1="";
		String c2="";
		String c3="";
		String c4="";
		String c5="";
		
		
		String serie1 = "";
		String serie2 = "";
		String serie3 = "";
		
		String dft_chart = "";
		
		
		try {
			
			if(idcus.length()>0) {
				session.setAttribute("idcus", idcus);
				nr_customers = 1;
			}
			
			ReadProperties prop = new ReadProperties("connection.properties");
			connAdmin = Utility.getConnAdmin();
			conn = Utility.getConnData();
			// ==================
			// NUMERO CLIENTI e lista clienti
			// ==================
			sql  =  "select id, description from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = connAdmin.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			String cus_id = "";
			while(rs.next()) {
				count+=1;
				if(idcus.length()==0) {
					nr_customers += 1;
				}
				cus.add(new Tables("CUS",rs.getString("id"),rs.getString("description"),"","","",""));
				cus_id = rs.getString("id");
			}
			rs.close();
			st.close();
			
			if(count==0) {
				session.setAttribute("idcus", null);
				nr_customers = 0;
			}
			if(count==1) {
				session.setAttribute("idcus", cus_id);
				nr_customers = 1;
			}
			
			if(nr_customers==1) {
				session.setAttribute("dscus", Utility.getCustomerDescription(connAdmin, (String) session.getAttribute("idcus")));
			}
			
			
			
			
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
			
			idstores = ""+req.getParameter("idstores");
			idstores = Utility.noNull(idstores);
			
			dtda = ""+req.getParameter("dtda");
			dtda = Utility.noNull(dtda);
			
			dtal = ""+req.getParameter("dtal");
			dtal = Utility.noNull(dtal);
			
			System.out.println("***** "+dtda+" "+dtal+" *****");
			
			kpi = ""+req.getParameter("kpi");
			kpi = Utility.noNull(kpi);
			
			tipoPeriod = ""+req.getParameter("tipoPeriod");
			tipoPeriod = Utility.noNull(tipoPeriod);
			
			tipo2 = ""+req.getParameter("tipo2");
			tipo2 = Utility.noNull(tipo2);
			
			quarter = ""+req.getParameter("quarter");
			quarter = Utility.noNull(quarter);
			
			group = ""+req.getParameter("group");
			group = Utility.noNull(group);
			
			if(tipoPeriod.length()==0) {
				tipoPeriod = "W";
			}

			System.out.println("***");
			System.out.println(tipo);
			System.out.println(tipoPeriod);
			System.out.println("***");		
			
			upd = ""+req.getParameter("upd");
			upd = Utility.noNull(upd);
			
	 		// Se idcus e' valorizzato
			if(idcus.length()>0) {
				
				// Controlla se è il caso di creare il calendario di default...
				CreateDefaultCalendar.createCalendar(conn, idcus);
				
				// UID 
				String uid = "";
				sql   = "select * from uuid_generate_v1()";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				if(rs.next()) {
					uid = rs.getString(1);
				}
				rs.close();
				st.close();
				
				session.setAttribute("uid", uid);
				
				// Seleziona il grafico di default
				sql   = "select idchart from rel_user_chart where idusr = '"+username+"' ";
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				if(rs.next()) {
					dft_chart = rs.getString("idchart");
				}
				else {
					dft_chart = "R";
				}
				rs.close();
 				
				// Se non viene passata la variabile, gli id vengono presi dalle preferenze
				if(idstores.length()==0) {
					
					sql   = "select idstore, grp "
							+ "from pref_user_stores , stores s "
							+ "where idusr = '"+username+"' and s.id = pref_user_stores.idstore and s.id_customer = "+idcus+" "
							+ "and pref_user_stores.idstore in (select idstore from rel_user_stores where idusr = '"+username+"')";
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					while(rs.next()) {
						nrstores+=1;
						idstores+=rs.getString("idstore")+",";
						group=rs.getString("grp");
					}
					rs.close();
					st.close();
					
					// Se non ci sono preferenze li seleziona tutti
					if(idstores.length()==0) {
						
						sql   = "select idstore from rel_user_stores, stores s "
								+ "where s.id = idstore and id_customer = "+idcus+" and idusr = '"+username+"' ";
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						while(rs.next()) {
							nrstores+=1;
							idstores+=rs.getString("idstore")+",";
						}
						rs.close();
						st.close();
						
					}
					
					if(idstores.length()>0)
						idstores = idstores.substring(0,idstores.length()-1);
					
				}
				else {
					String temp[]=idstores.split(",");
					nrstores = temp.length;
				}
				
				// Se non viene passata la variabile, viene presa dalle preferenze
				if(dtda.length()==0) {
					
					sql   = "select dtda, dtal, tipo from pref_user_period where idusr = '"+username+"' ";
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					while(rs.next()) {
						dtda=rs.getString("dtda");
						dtal=rs.getString("dtal");
						tipoPeriod = rs.getString("tipo");
						dates=dtda+" / "+dtal;
					}
					rs.close();
					st.close();
					
					// Se non ci sono preferenze salvate imposta la settimana corrente
					if(dtda.length()==0) {
						
						tipoPeriod = "W";
						
						// Get calendar set to current date and time
						Calendar cw = Calendar.getInstance(new Locale("en","US"));
	
						// Set the calendar to monday of the current week
						cw.set(Calendar.YEAR, Integer.parseInt(currentYear));     
						cw.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(currentWeek));     
						cw.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
						
						dtda = dateIta.format(cw.getTime());
						for (int d = 0; d < 6; d++) {
							cw.add(Calendar.DATE, 1);
						}
						dtal = dateIta.format(cw.getTime());
						dates=dtda+" / "+dtal;
						
					}
				}
				else {
					dates=dtda+" / "+dtal;
				}
				
				String kpi_selected = "";
				String kpi_desc_selected = "";
				
				if(kpi.length()==0) {
					
					sql   = "select kpi "
							+ "from pref_user_kpi "
							+ "where idusr = '"+username+"' "
							+ "and idcus = "+session.getAttribute("idcus")+" ";
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					while(rs.next()) {
						nrkpi+=1;
						kpi+=rs.getString("kpi").trim()+",";
					}
					rs.close();
					st.close();
					
					// Se non ci sono preferenze li seleziona tutti
					if(kpi.length()==0) {
						
						sql   = "select idkpi "+
								"from rel_cus_kpi "+
								"where idcus = '"+session.getAttribute("idcus")+"' "+
								"order by visorder limit 5 ";
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						while(rs.next()) {
							nrkpi+=1;
							kpi+=rs.getString("idkpi").trim()+",";
						}
						rs.close();
						st.close();
					}
					
					if(kpi.length()>0)
						kpi = kpi.substring(0,kpi.length()-1);
					
				}
				else {
					String temp[]=kpi.split(",");
					nrkpi = temp.length;		
				}
				
				if(nrkpi==0) {
					
				}
				else {
					
					System.out.println(kpi);
					
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
					
					obj.put("dates", dates);
					
					obj.put("nrstores", nrstores);
					obj.put("idstores", idstores);
					obj.put("group", group);
					
					obj.put("dtda", dtda);
					obj.put("dtal", dtal);
					
					obj.put("kpi", kpi);
					
					String chart = "";
					String dis = "";
					String s1 = "";
					String s2 = "";
					String s3 = "";
					
					if(nrkpi>2) {
						chart = "R";
						dis = "";
						s1 = "checked=\"checked\"";
						s2 = "";
						s3 = "";
					}
					else {
						chart = "P";
						dis = "";
						s1 = "";
						s2 = "checked=\"checked\"";
						s3 = "";
					}
					obj.put("chart", chart);
					obj.put("dis", dis);
					obj.put("s1", s1);
					obj.put("s2", s2);
					obj.put("s3", s3);
					
					obj.put("quarter", quarter);
					
					System.out.println("--- START tipoPeriod ---");
					System.out.println("---      "+tipoPeriod+"      ---");
					System.out.println("--- FINE  tipoPeriod ---");
					
					obj.put("tipoPeriod", tipoPeriod);
					
					//Formattazione data
					Date d1 = sdf.parse(dtda);
					dtda = iso.format(d1);
					Date d2 = sdf.parse(dtal);
					dtal = iso.format(d2);
					
					System.out.println(dtda+" "+dtal);
					
					
					// *******
					// Se spuntato "Only Updates Stores" ricalcola i negozi
					if(upd.length()>0) {
						
						sql   = "select * from updated_stores('"+dtal+"'::date, '"+idstores.replaceAll(",","*")+"') ";
						System.out.println(sql);
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						if(rs.next()) {
							idstores = ""+rs.getString(1);
							idstores = Utility.noNull(idstores);
							idstores = idstores.replaceAll("\\*", ",");
							
							String temp[] = idstores.split(",");
							nrstores = temp.length;
							
							obj.put("nrstores", nrstores);
							obj.put("idstores", idstores);
							obj.put("group", group);
							
						}
						rs.close();
						st.close();
						
					}
					// *******
					
					
					
					
					
					
					// *******
					// kpilist...
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			        System.out.println(timestamp);
					
					sql   = "select * from landing_total('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', '"+uid+"') , "
							+ "ztables where xcdtb = 'KPI' and trim(_kpi) = trim(xcdel) order by order_position, _kpi ";
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					
					String sel = "";
					String active = "";
					count = 0;
					while(rs.next()) {
	
						count+=1;
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
					
					
					// *******
					// tablist...
					sql   = "select * from landing_widget('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', '"+uid+"') , "
							+ "ztables where xcdtb = 'KPI' and trim(_kpi) = trim(xcdel) order by order_position, _kpi, _inc desc";
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					String rott = "";
					String kpi_rs = "";
					double kpi_100 = 0;
					String tab = "";
					count = 0;
					
					String kpi_list = "";
					String kpi_best = "";
					String kpi_key  = "";
					String kpi_desc = "";
					double kpi_inc  = 0;
					String kpi_color= "";
					String kpi_idstore="";
					String best_selected="";
					String best_list="";
					double kpi_inc_best  = 0;
					
					
					String chartVal = "";
					
					active = "";
					while(rs.next()) {
						if(!rs.getString("_kpi").equals(rott)) {
	
							if(rott.length()>0) {
								//if(tab.length()>0)
								//	tab=tab.substring(0,tab.length()-1);
								if(kpi_list.length()>0)
									kpi_list=kpi_list.substring(0,kpi_list.length()-1);
								System.out.println(tab);
								
								if(kpi_rs.equals(kpi_selected)) {
									active = "active";
									best_selected = kpi_best;
									best_list = kpi_list;
								}
								else {
									active = "";
								}
								
								System.out.println(rs.getString("_kpi")+" "+active);
								
								tablist.add(new Tables2("KPI",kpi_rs,tab,kpi_best,kpi_list,kpi_desc,active,
										kpi_color,"","","","",""));
								
								radarlimit.add(new Tables("RADAR", kpi_best, kpi_desc, ""+kpi_100,"","",""));
								
								if(chartVal.length()>0)
									chartVal = chartVal.substring(0, chartVal.length()-1);
								
								for(int i=0;i<kpilist.size();i++) {
									if(kpilist.get(i).getXcdel().equals(kpi_rs)) {
										
										kpilist.get(i).setCmp8(chartVal);
										
									}
								}
								
							}
							
							System.out.println("--");
							
							kpi_rs = rs.getString("_kpi");
							kpi_100= rs.getDouble("_inc");
							kpi_best = rs.getString("_id_store");
							System.out.println(kpi_rs + " "+kpi_100);
							
							
							System.out.println("CHARTVAL: "+chartVal);
							
							
							
							count = 0;
							tab = "";
							chartVal = "";
							kpi_list = "";
							
						}
						
						count+=1;
						
						kpi_key  = rs.getString("_kpi");
						kpi_desc = rs.getString("xdtab");
						kpi_inc  = rs.getDouble("_inc");
						kpi_color= rs.getString("color");
						kpi_idstore=rs.getString("_id_store");
						
						kpi_list+=rs.getString("_id_store")+"*";
						//tab+=count+"##@@"+kpi_best+"##@@"+rs.getString("_id_store")+"##@@"+Math.round((((100*rs.getDouble("_inc"))/kpi_100)))+",";
						tab+="<tr><td>"+count+".</td><td><a href=\"#\" onclick=\"callReport('"+kpi_idstore+"')\">"+rs.getString("_description")+"</a></td><td><b>"+rs.getDouble("_inc")+"</b></td><td>"+
							"<div class=\"progress progress-xs\" style=\"margin-bottom:-10px\">"+
				            "<div class=\"progress-bar bg-dark\" data-toggle=\"tooltip\" data-original-title=\""+Math.round((((100*rs.getDouble("_inc"))/kpi_100)))+"%\" "+
							"style=\"width: "+Math.round((((100*rs.getDouble("_inc"))/kpi_100)))+"%\"></div>"+
							"</div></td></tr>";
						
						chartVal+=""+rs.getDouble("_inc")+",";
						
						//System.out.println(kpi_rs);
						
						rott = rs.getString("_kpi");
						
					}
					rs.close();
					st.close();
					
					// Ultimo record
					//if(tab.length()>0)
					//	tab=tab.substring(0,tab.length()-1);
					if(kpi_list.length()>0)
						kpi_list=kpi_list.substring(0,kpi_list.length()-1);
					
					System.out.println(tab);
					
					if(kpi_rs.equals(kpi_selected)) {
						active = "active";
						best_selected = kpi_best;
						best_list = kpi_list;
					}
					else {
						active = "";
					}
					
					if(nrkpi==1) {
						active = "active";
						best_selected = kpi_best;
						best_list = kpi_list;
					}
					
					tablist.add(new Tables2("KPI",kpi_rs,tab,
							kpi_best,kpi_list,kpi_desc,active,
							kpi_color,"","","","",""));
					
					radarlimit.add(new Tables("RADAR", kpi_best, kpi_desc, ""+kpi_100,"","",""));
					
					if(chartVal.length()>0)
						chartVal = chartVal.substring(0, chartVal.length()-1);
					
					for(int i=0;i<kpilist.size();i++) {
						if(kpilist.get(i).getXcdel().equals(kpi_rs)) {
							
							kpilist.get(i).setCmp8(chartVal);
							
						}
					}
					
					obj.put("tablist", tablist);
					obj.put("radarlimit", radarlimit);
					
					
					
					
					
					
					
					
					
					
					
					
					legend = "[]";
					indicator = "[]";
					
					
					/*
					switch (chart) {
		            case "R":
						// =====================
						// RADAR
						
						sql   = "select * from landing_radar_new('"+dtda+"','"+dtal+"', "
								+ "'"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"', "
								+ "'"+kpi_selected+"','"+best_selected+"','"+best_list+"') ";
						System.out.println(sql);
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						while(rs.next()) {
							
							if(rs.getString("tipo").toUpperCase().equals("LIMITE")) {
								maxCol1 = rs.getDouble("col1");
								maxCol2 = rs.getDouble("col2");
								maxCol3 = rs.getDouble("col3");
								maxCol4 = rs.getDouble("col4");
								maxCol5 = rs.getDouble("col5");
							}
							if(rs.getString("tipo").toUpperCase().equals("SERIE1")) {
								c1=""+rs.getString("col1");
								c1=Utility.noNull(c1);
								c2=""+rs.getString("col2");
								c2=Utility.noNull(c2);
								c3=""+rs.getString("col3");
								c3=Utility.noNull(c3);
								c4=""+rs.getString("col4");
								c4=Utility.noNull(c4);
								c5=""+rs.getString("col5");
								c5=Utility.noNull(c5);
								
								if(c1.length()>0)
									serie1+=c1+",";
								if(c2.length()>0)
									serie1+=c2+",";
								if(c3.length()>0)
									serie1+=c3+",";
								if(c4.length()>0)
									serie1+=c4+",";
								if(c5.length()>0)
									serie1+=c5+",";
								
								if(serie1.length()>0)
									serie1=serie1.substring(0,serie1.length()-1);
								
							}
							if(rs.getString("tipo").toUpperCase().equals("SERIE2")) {
								
								c1=""+rs.getString("col1");
								c1=Utility.noNull(c1);
								c2=""+rs.getString("col2");
								c2=Utility.noNull(c2);
								c3=""+rs.getString("col3");
								c3=Utility.noNull(c3);
								c4=""+rs.getString("col4");
								c4=Utility.noNull(c4);
								c5=""+rs.getString("col5");
								c5=Utility.noNull(c5);
								
								if(c1.length()>0)
									serie2+=c1+",";
								if(c2.length()>0)
									serie2+=c2+",";
								if(c3.length()>0)
									serie2+=c3+",";
								if(c4.length()>0)
									serie2+=c4+",";
								if(c5.length()>0)
									serie2+=c5+",";
								
								if(serie2.length()>0)
									serie2=serie2.substring(0,serie2.length()-1);
								
							}
							if(rs.getString("tipo").toUpperCase().equals("SERIE3")) {
								
								c1=""+rs.getString("col1");
								c1=Utility.noNull(c1);
								c2=""+rs.getString("col2");
								c2=Utility.noNull(c2);
								c3=""+rs.getString("col3");
								c3=Utility.noNull(c3);
								c4=""+rs.getString("col4");
								c4=Utility.noNull(c4);
								c5=""+rs.getString("col5");
								c5=Utility.noNull(c5);
								
								if(c1.length()>0)
									serie3+=c1+",";
								if(c2.length()>0)
									serie3+=c2+",";
								if(c3.length()>0)
									serie3+=c3+",";
								if(c4.length()>0)
									serie3+=c4+",";
								if(c5.length()>0)
									serie3+=c5+",";
								
								if(serie3.length()>0)
									serie3=serie3.substring(0,serie3.length()-1);
								
								
							}
						}
						rs.close();
						st.close();
						
						
						
						break;
						
		            case "P":
						
						// *******
						// pie...
						
						sql   = "select * from landing_widget('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"') , "
								+ "ztables where xcdtb = 'KPI' and trim(_kpi) = trim(xcdel) and _kpi = '"+kpi_selected+"' order by _kpi, _inc desc";
						System.out.println(sql);
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						
						while(rs.next()) {
							
							tablist2.add(new Tables("KPI",rs.getString("_kpi"),rs.getString("_description"),
									rs.getString("_inc"),rs.getString("_id_store"),"",""));
							
						}
						rs.close();
						st.close();
						
						legend = "[";
						indicator = "[";
						for(int i=0;i<tablist2.size();i++) {
							
							indicator+="{ \"value\": "+tablist2.get(i).getCmp1()+", \"name\": \""+tablist2.get(i).getXdtab()+"\" },";
							legend+="\""+tablist2.get(i).getXdtab()+"\",";
						}
						
						if(indicator.length()>1)
							indicator = indicator.substring(0,indicator.length()-1);
						if(legend.length()>1)
							legend = legend.substring(0,legend.length()-1);
						
						indicator+="]";
						legend+="]";
						
						
						
						break;
						
					}
					*/
					
					
					
					// *******
					// Heatmap...
					//xAxis = "'08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22'";
			 		
					xAxis = "'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','23-23','23-24'";
					//xAxis = "'00\n01','01\n02','02\n03','03\n04','04\n05','05\n06','06\n07','07\n08','08\n09','09\n10','10\n11','11\n12','12\n13','13\n14','14\n15','15\n16','16\n17','17\n18','18\n19','19\n20','20\n21','21\n22','23\n23','23\n24'";
					
					//yAxis = "'Saturday','Friday','Thursday','Wednesday','Tuesday','Monday','Sunday'";
					yAxis = "'"+propLang.get("lun")+"','"+propLang.get("mar")+"','"+propLang.get("mer")+"','"+propLang.get("gio")+"','"+propLang.get("ven")+"','"+propLang.get("sab")+"','"+propLang.get("dom")+"'";
			 		
					/*
			 		sql   = "select max(_val) valore  from "
			 				+ "public.landing_heatmap_new('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi_selected+"') ";
			 		System.out.println("---");
					System.out.println(sql);
					
			 		st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					if(rs.next()) {
			 			max = rs.getInt("valore");
			 		}
			 		rs.close();
			 		st.close();
			 		*/
			 		
					
					/*
			 		List l = new ArrayList();
			 		
			 		sql   = "select _dow data1 ,_hour data2 ,_val valore "
			 				+ "from public.landing_heatmap_new('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','"+kpi_selected+"') "
			 				+ "order by 1,2";
					System.out.println("---");
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					String val = "";
					
					String data1 = "";
					
					while(rs.next()) {
						val=""+rs.getString("valore");
						val=Utility.noNull(val);
						
						if(val.length()>0)
							l.add(Double.parseDouble(val));
						
						if(val.length()==0)
							val = "'-'";
						
						
						
						switch (rs.getInt("data1")) {
			         	case 0: 
			         		data1 = propLang.get("dom");
			         		break;
			         	case 1: 
			         		data1 = propLang.get("lun");
			         		break;
			         	case 2: 
			         		data1 = propLang.get("mar");
			         		break;
			         	case 3: 
			         		data1 = propLang.get("mer");
			         		break;
			         	case 4: 
			         		data1 = propLang.get("gio");
			         		break;
			         	case 5: 
			         		data1 = propLang.get("ven");
			         		break;
			         	case 6: 
			         		data1 = propLang.get("sab");
			         		break;
						}
						
						//heatmap+="["+(rs.getInt("data2")-8)+",'"+data1+"',"+val+"],";
						heatmap+="["+rs.getInt("data2")+",'"+data1+"',"+val+"],";
						
					}
					rs.close();
					st.close();
					
					try {
						double dMax = Collections.max(l);
						max = (int) Math.round(dMax);
					}
					catch(Exception e) {
						e.printStackTrace();
						max = 0;
					}
					
					if(heatmap.length()>0)
						heatmap=heatmap.substring(0,heatmap.length()-1);
					*/
					
					
					
					
					
					String showAlarm = ""+session.getAttribute("showAlarm");
					showAlarm=Utility.noNull(showAlarm);
					
					System.out.println("showAlarm: "+showAlarm);
					obj.put("showAlarm", showAlarm);
					
					if(showAlarm.length()>0) {
						
						// Activa alarms
						List<Tables3> alarms = new ArrayList<Tables3>();
						alarms.clear();
						
						String cond = "";
						String cond2= "";
						
						if(session.getAttribute("idcus").toString().trim().length()>0)
							cond2 = "and id_customer = "+session.getAttribute("idcus")+" ";
						count = 0;
						String msg = "";
						
						String order = ""+req.getParameter("order");
						order=Utility.noNull(order);
						String sort = ""+req.getParameter("sort");
						sort=Utility.noNull(sort);
					
						if(order.length()>0) {
							order = "order by "+order+" "+sort;
						}
						else
							order = "order by x.from_time desc ";
						
						if(sort.length()==0)
							sort = "desc";
						else
							sort = "";
						
						obj.put("sort", sort);
						
						// Paginazione
						String pag = ""+req.getParameter("pag");
						pag = Utility.noNull(pag);
			
						if(pag.length()==0) {
							pag = "1";
						}
						
						System.out.println(pag);
						
						int rowIni = 0;
						int rowFin = 0;
						int nrPag = 0;
			
						int numRow = 0;
						String prev = "";
						String next = "";
						String nrRecord = "10";
						
						// Numero di record totali
						sql  =  "select count(*) num " + 
								"from vw_active_alarm x " + 
								"left join lang_alarm_report l on alarm_id = id_alarm_report and lang = '"+session.getAttribute("language").toString()+"' "+
								"where id_store in(select idstore from rel_user_stores " + 
								"where idusr = '"+(String)session.getAttribute("username")+"') "+cond2+
								" "+cond;
						System.out.println(sql);
						st=conn.prepareStatement(sql);
						rs=st.executeQuery();
						if(rs.next()) {
							numRow=rs.getInt("num");
						}
						rs.close();
						st.close();
						
						nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
						
						int max2 = 0;
						
						for (int i=1; i<=nrPag; i++) {
							
							max2+=Integer.parseInt(nrRecord);
							
							if(i==Integer.parseInt(pag)) {
								
								prev = ""+(i-1);
								next = ""+(i+1);
								
								rowIni = (max2-Integer.parseInt(nrRecord));
								rowFin = Integer.parseInt(nrRecord);
								
							}
						}
						
						System.out.println(nrPag);
						
						sql  =  "select x.id, x.customer, x.store, x.code, l.description, "
								+ "x.threshold, x.timeunit, x.control_time, x.from_time, x.to_time, x.value, "
								+ "case when s.id_trigger is null then true else false end btn,"
								+ "s.fromdate, s.todate, x.id_store, x.alarm_id, x.kpi "
								+ "from vw_active_alarm x "
								+ "left join lang_alarm_report l on alarm_id = id_alarm_report and lang = '"+session.getAttribute("language").toString()+"' "
								+ "left join silenced_trigger s on s.id_store = x.id_store and s.id_trigger = x.alarm_id "
								+ "where x.id_store in(select idstore from rel_user_stores "
								+ "where idusr = '"+(String)session.getAttribute("username")+"') "
								+ " "+cond2+" "
								+ " "+cond+" "
								+ " "+order+" "
								+ "limit "+rowFin+" offset "+rowIni+" ";
						System.out.println(sql);
						
						// Prepara il cursore
						st = conn.prepareStatement(sql);
					
						// Esegue l'sql
						rs=st.executeQuery();
						
						String $al_id = "";
						String $al_customer = "";
						String $al_store = "";
						String $al_code = "";
						String $al_description = "";
						String $al_threshold = "";
						String $al_timeunit = "";
						String $al_control_time = "";
						String $al_from_time = "";
						String $al_to_time = "";
						String $al_value = "";
						boolean btn = false;
						String button = "";
						String $al_kpi = "";
						String $alarm_id = "";
						String $id_store = "";
						
						while(rs.next()) {
							
							$al_id = rs.getString("id");
							$al_customer = rs.getString("customer");
							$al_store = rs.getString("store");
							$al_code = rs.getString("code");
							$al_description = rs.getString("description");
							$al_threshold = rs.getString("threshold");
							$al_timeunit = rs.getString("timeunit");
							$al_control_time = rs.getString("control_time");
							$al_from_time = rs.getString("from_time");
							$al_to_time = rs.getString("to_time");
							$al_value = df.format(rs.getDouble("value"));
							$al_kpi = rs.getString("kpi");
							$alarm_id = rs.getString("alarm_id");
							$id_store = rs.getString("id_store");
							
							btn = rs.getBoolean("btn");
							
							if(btn) {
								
								/*
								button = "<div class=\"checkbox i-checks\">\r\n" + 
										"                              <label>\r\n" + 
										"                                <input type=\"checkbox\" name=\"chk\" value=\""+rs.getString("alarm_id")+";"+rs.getString("id_store")+"\"><i></i>\r\n" + 
										"                              </label>\r\n" + 
										"                            </div>";
								*/
								button = "<i class=\"fa fa-circle text-success\"><i>";
								
							}
							else {
								
								button = "<i class=\"fa fa-power-off text-danger\"><i>";
								
							}
							
							alarms.add(new Tables3(button,"",$al_value,
									$al_id, $al_customer, $al_store,
									$al_code, $al_description, $al_threshold, $al_timeunit,
									$al_control_time, $al_from_time, $al_to_time,
									$al_kpi,$alarm_id,$id_store,sdf.format(rs.getDate("from_time")),sdf.format(rs.getDate("to_time")),"","","","",""));
						
						}
						
						rs.close();
						st.close();
						
						obj.put("alarms", alarms);
						obj.put("count", count);
						obj.put("msg", msg);
						
						obj.put("tot", numRow);
						obj.put("nrPag", nrPag);
						obj.put("prev", prev);
						obj.put("next", next);
						obj.put("ini", rowIni);
						obj.put("fin", rowFin);
						
					}
				}
				
			}
			
			/*
			obj.put("xAxis", xAxis);
			obj.put("yAxis", yAxis);
			obj.put("heatmap", heatmap);
			obj.put("max", max);
			obj.put("height", height);
			
			obj.put("indicator", indicator);
			obj.put("legend", legend);
			
			obj.put("maxCol1", maxCol1);
			obj.put("maxCol2", maxCol2);
			obj.put("maxCol3", maxCol3);
			obj.put("maxCol4", maxCol4);
			obj.put("maxCol5", maxCol5);
			obj.put("serie1", serie1);
			obj.put("serie2", serie2);
			obj.put("serie3", serie3);
			*/
			
			
			
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
		obj.put("cus", cus);
		obj.put("count", count);
		obj.put("idcus", idcus);
		obj.put("nr_customers", nr_customers);
		obj.put("nrkpi", nrkpi);
		
		obj.put("xAxis", xAxis);
		obj.put("yAxis", yAxis);
		obj.put("heatmap", heatmap);
		obj.put("max", max);
		obj.put("height", height);
		
		obj.put("indicator", indicator);
		obj.put("legend", legend);
		
		obj.put("maxCol1", maxCol1);
		obj.put("maxCol2", maxCol2);
		obj.put("maxCol3", maxCol3);
		obj.put("maxCol4", maxCol4);
		obj.put("maxCol5", maxCol5);
		obj.put("serie1", serie1);
		obj.put("serie2", serie2);
		obj.put("serie3", serie3);
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		if(dft_chart.length()==0) {
			dft_chart = "R";
		}
		obj.put("dft_chart", dft_chart);
		
		return new ModelAndView("/analytics2/main/index", obj);
	}
	
}
