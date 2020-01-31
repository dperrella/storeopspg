package pkg.analytics2;

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
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Kpi;
import pkg.beans.Tables2;
import pkg.beans.Report;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Customers;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk_BKP extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
		List<Kpi> kpilist = new ArrayList<Kpi>();
		kpilist.clear();
		
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
			
			
			
			// *********
			// HEATMAP
			String xAxis = "";
			String yAxis = "";
			int max = 0;
			int height = 0;
			String heatmap = "";
				
	 		height = 500;
	 		
	 		// Se idcus e' valorizzato
			if(idcus.length()>0) {
			
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
					
					sql   = "select dtda, dtal from pref_user_period where idusr = '"+username+"' ";
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					while(rs.next()) {
						dtda=rs.getString("dtda");
						dtal=rs.getString("dtal");
						dates=dtda+" / "+dtal;
					}
					rs.close();
					st.close();
					
					// Se non ci sono preferenze salvate imposta la settimana corrente
					if(dtda.length()==0) {
						
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
					
					sql   = "select kpi, xdtab, calculated, color, kpi_symbol "
							+ "from pref_user_kpi, ztables "
							+ "where trim(kpi) = trim(xcdel) and xcdtb = 'KPI' "
							+ "and idusr = '"+username+"' ";
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					String sel = "";
					String active = "";
					while(rs.next()) {
						nrkpi+=1;
						if(nrkpi==1) {
							sel = "bg-light";
							active = "active";
							kpi_selected = rs.getString("kpi");
							kpi_desc_selected = rs.getString("xdtab");
						}
						else {
							sel = "";
							active = "";
						}
						kpi+=rs.getString("kpi").trim()+",";
						kpilist.add(new Kpi("KPI",rs.getString("kpi"),rs.getString("xdtab"), "", "", 
								rs.getBoolean("calculated"), rs.getString("color"), rs.getString("kpi_symbol"),
								"0","0",sel,active,
								"","","",""));
					}
					rs.close();
					st.close();
					
					// Se non ci sono preferenze li seleziona tutti
					if(kpi.length()==0) {
						
						sql   = "select xcdel, xdtab, xdesc, calculated, color, kpi_symbol "+
								"from ztables a , rel_user_kpi "+
								"where xcdtb = 'KPI' "+
								"and trim(xcdel) = trim(idkpi) and idusr = '"+session.getAttribute("username")+"' "+
								"order by visorder limit 5 ";
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						while(rs.next()) {
							nrkpi+=1;
							if(nrkpi==1) {
								sel = "bg-light";
								active = "active";
								kpi_selected = rs.getString("xcdel");
								kpi_desc_selected = rs.getString("xdtab");
							}
							else {
								sel = "";
								active = "";
							}
							kpi+=rs.getString("xcdel").trim()+",";
							kpilist.add(new Kpi("KPI",rs.getString("xcdel"),rs.getString("xdtab"), "", rs.getString("xdesc"), 
									rs.getBoolean("calculated"), rs.getString("color"), rs.getString("kpi_symbol"),
									"0","0",sel,active,
									"","","",""));
						}
						rs.close();
						st.close();
					}
					
					if(kpi.length()>0)
						kpi = kpi.substring(0,kpi.length()-1);
					
				}
				else {
					String temp[]=kpi.split(",");
					//nrkpi = temp.length;
					
					String cond = "";
					for(int i=0;i<=temp.length;i++) {
						cond+="'"+temp[i]+"',";
					}
					if(cond.length()>0)
						cond = cond.substring(0,cond.length()-1);
					
					sql   = "select xcdel, xdtab, xdesc, calculated, color, kpi_symbol "+
							"from ztables a , rel_user_kpi "+
							"where xcdtb = 'KPI' "+
							"and trim(xcdel) = trim(idkpi) and idusr = '"+session.getAttribute("username")+"' and xcdel in("+cond+") "+
							"order by visorder limit 5 ";
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					String sel = "";
					String active = "";
					while(rs.next()) {
						nrkpi+=1;
						if(nrkpi==1) {
							sel = "bg-light";
							active = "active";
							kpi_selected = rs.getString("xcdel");
							kpi_desc_selected = rs.getString("xdtab");
						}
						else {
							sel = "";
							active = "";
						}
						kpi+=rs.getString("xcdel").trim()+",";
						kpilist.add(new Kpi("KPI",rs.getString("xcdel"),rs.getString("xdtab"), "", rs.getString("xdesc"), 
								rs.getBoolean("calculated"), rs.getString("color"), rs.getString("kpi_symbol"),
								"0","0",sel,active,
								"","","",""));
					}
					rs.close();
					st.close();
				}
				
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
				obj.put("col", col);
				obj.put("kpilist", kpilist);
				obj.put("kpi_selected", kpi_selected);
				obj.put("kpi_desc_selected", kpi_desc_selected);
				
				
				
				
				
				
				
				obj.put("dates", dates);
				
				obj.put("nrstores", nrstores);
				obj.put("idstores", idstores);
				obj.put("group", group);
				
				
				obj.put("dtda", dtda);
				obj.put("dtal", dtal);
				
				obj.put("kpi", kpi);
				obj.put("nrkpi", nrkpi);
				
				obj.put("quarter", quarter);
				obj.put("tipoPeriod", tipoPeriod);
				
				//Formattazione data
				Date d1 = sdf.parse(dtda);
				dtda = iso.format(d1);
				Date d2 = sdf.parse(dtal);
				dtal = iso.format(d2);
				
				System.out.println(dtda+" "+dtal);
				
				
				// *******
				// Se spuntato "Only Updates Stores ricalcola i negozi
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
				
				
				
				
				
				
				
		 		xAxis = "'08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22'";
		 		//yAxis = "'Sunday','Monday', 'Tuesday','Wednesday', 'Thursday', 'Friday', 'Saturday'";
		 		yAxis = "'Saturday','Friday','Thursday','Wednesday','Tuesday','Monday','Sunday'";
		 		
		 		/*
		 		sql   = "select max(_val) valore  from "
		 				+ "public.landing_heatmap('"+dtda+"','"+dtal+"','"+idstores.replaceAll(",","*")+"','VIS') ";
		 		*/
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
					
					heatmap+="["+(rs.getInt("data2")-8)+",'"+data1+"',"+val+"],";
					
				}
				rs.close();
				st.close();
				
				if(heatmap.length()>0)
					heatmap=heatmap.substring(0,heatmap.length()-1);
				
				
			}
			
			obj.put("xAxis", xAxis);
			obj.put("yAxis", yAxis);
			obj.put("heatmap", heatmap);
			obj.put("max", max);
			obj.put("height", height);
			
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
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		return new ModelAndView("/analytics2/main/index", obj);
	}
	
}
