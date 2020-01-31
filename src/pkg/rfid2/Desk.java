package pkg.rfid2;

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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables2;
import pkg.beans.Tables3;
import pkg.beans.rfid.Log;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.Step;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.analytics2.CreateDefaultCalendar;
import pkg.beans.Customers;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
				
		
		List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
		List<Tables> steplist = new ArrayList<Tables>();
		steplist.clear();
		
		// transact
		int tot = 0;
			
		// campi passati dalla session
		System.out.println("*****************");
		System.out.println(session.getAttribute("idcus"));
		System.out.println("*****************");
		
		// Dalla request
		String idcus = ""+req.getParameter("idcus");
		idcus = Utility.noNull(idcus);
		int nr_customers = 0;
		
		System.out.println("idcus:"+idcus);
		
		// Se dalla request è vuoto legge dalla session
		if(idcus.length()==0) {
			idcus = ""+session.getAttribute("idcus");
			
			System.out.println("idcus:"+idcus);
			
			idcus = Utility.noNull(idcus);
		}
		
		System.out.println("idcus:"+idcus);
		
		Connection connAdmin = null;
		Statement stAdmin = null;
		ResultSet rs = null;
		
		Connection conn = null;
		PreparedStatement st = null;
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		
		
		Date date = new Date();
		String oggi = dateNum.format(date);
		String oggiIta = dateIta.format(date);
		
		String currentYear = oggi.substring(0,4);
		String currentMonth = oggi.substring(4,6);
		String currentWeek = "";
		
		String sql = "";
		
		try {
				
			if(idcus.length()>0) {
				session.setAttribute("idcus", idcus);
				nr_customers = 1;
			}
			
			ReadProperties prop = new ReadProperties("connection.properties");
			connAdmin = Utility.getConnAdmin();
			stAdmin = connAdmin.createStatement();
			
			// ==================
			// NUMERO CLIENTI e lista clienti
			// ==================
			sql  =  "select id, description from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Esegue l'sql
			rs=stAdmin.executeQuery(sql);
			String cus_id = "";
			
			while(rs.next()) {
				if(idcus.length()==0) {
					nr_customers += 1;
				}
				cus.add(new Tables("CUS",rs.getString("id"),rs.getString("description"),"","","",""));
				cus_id = rs.getString("id");
			}
			rs.close();
			stAdmin.close();
			
			System.out.println(nr_customers);
			
			if(nr_customers==1) {
				session.setAttribute("dscus", Utility.getCustomerDescription(connAdmin, (String) session.getAttribute("idcus")));
			}
			
			
			stAdmin.close();
			connAdmin.close();
			
			
			
			
			
			
			
			
			// smartrfid...
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
			
			String idsites = "";
			int nrsites = 0;
			String dtda = "";
			String dtal = "";
			String dates = "No Date Selected";
			String step = "";
			int nrstep = 0;
			String tipoPeriod = "";
			String tipo2 = "";
			String quarter = "";
			
			
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
			
			
			
			idsites = ""+req.getParameter("idsites");
			idsites = Utility.noNull(idsites);
			
			dtda = ""+req.getParameter("dtda");
			dtda = Utility.noNull(dtda);
			
			dtal = ""+req.getParameter("dtal");
			dtal = Utility.noNull(dtal);
			
			step = ""+req.getParameter("step");
			step = Utility.noNull(step);
			
			tipoPeriod = ""+req.getParameter("tipoPeriod");
			tipoPeriod = Utility.noNull(tipoPeriod);
			
			tipo2 = ""+req.getParameter("tipo2");
			tipo2 = Utility.noNull(tipo2);
			
			quarter = ""+req.getParameter("quarter");
			quarter = Utility.noNull(quarter);
			
			if(tipoPeriod.length()==0) {
				tipoPeriod = "W";
			}

			System.out.println("***");
			System.out.println(tipoPeriod);
			System.out.println("***");		
			
			// Se idcus e' valorizzato
			if(idcus.length()>0) {
				
				conn = Utility.getConnRfid();
				
				
				// Controlla se è il caso di creare il calendario di default...
				CreateDefaultCalendar.createCalendar(conn, idcus);
				
				// Se non viene passata la variabile, gli id vengono presi dalle preferenze
				if(idsites.length()==0) {
					
					sql   = "select idsite "
							+ "from pref_user_sites, sites s "
							+ "where idusr = '"+username+"' and s.id = pref_user_sites.idsite and s.id_customer = "+idcus+" ";
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					while(rs.next()) {
						nrsites+=1;
						idsites+=rs.getString("idsite")+",";
					}
					rs.close();
					st.close();
					
					// Se non ci sono preferenze li seleziona tutti
					if(idsites.length()==0) {
						
						sql   = "select id from sites s "
								+ "where id_customer = "+idcus+" ";
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						while(rs.next()) {
							nrsites+=1;
							idsites+=rs.getString("id")+",";
						}
						rs.close();
						st.close();
						
					}
					
					if(idsites.length()>0)
						idsites = idsites.substring(0,idsites.length()-1);
					
				}
				else {
					String temp[]=idsites.split(",");
					nrsites = temp.length;
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
				
				String step_selected = "";
				String step_desc_selected = "";
				
				
				if(step.length()==0) {
					
					sql   = "select step "
							+ "from pref_user_step "
							+ "where idusr = '"+username+"' "
							+ "and idcus = "+session.getAttribute("idcus")+" ";
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					while(rs.next()) {
						nrstep+=1;
						step+=rs.getString("step").trim()+",";
					}
					rs.close();
					st.close();
					
					// Se non ci sono preferenze li seleziona tutti
					if(step.length()==0) {
						
						sql   = "select idstep "+
								"from step "+
								"where idflow in (select idflow from flow where id_customer = "+session.getAttribute("idcus")+" )"+
								"order by idstep limit 5 ";
						st = conn.prepareStatement(sql);
						rs = st.executeQuery();
						while(rs.next()) {
							nrstep+=1;
							step+=rs.getString("idstep").trim()+",";
						}
						rs.close();
						st.close();
					}
					
					if(step.length()>0)
						step = step.substring(0,step.length()-1);
					
				}
				else {
					String temp[]=step.split(",");
					nrstep = temp.length;		
				}
				
				
				System.out.println("nrstep: "+nrstep);
				
				if(nrstep==0) {
					// do nothing
					obj.put("nrstep", nrstep);
				}
				else {
					
					System.out.println(step);
					
					String col = "";
					
					switch (nrstep) {
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
					
					obj.put("nrsites", nrsites);
					obj.put("idsites", idsites);
					
					obj.put("dtda", dtda);
					obj.put("dtal", dtal);
					
					obj.put("step", step);
					
					
					
					obj.put("nrstep", nrstep);
					
					obj.put("quarter", quarter);
					
					obj.put("tipoPeriod", tipoPeriod);
					
					//Formattazione data
					Date d1 = sdf.parse(dtda);
					dtda = iso.format(d1);
					Date d2 = sdf.parse(dtal);
					dtal = iso.format(d2);
					
					System.out.println(dtda+" "+dtal);
					
					/*
					sql   = "SELECT s.idstep, description , color, " + 
							"	(select count(*) from documents d " + 
							"	where d.idstep = s.idstep and d.idsite in("+idsites+") " + 
							"	and doc_date::date between '"+dtda+"' and '"+dtal+"') val, " + 
							"	(select count(sku) nrsku from documents_detail r, documents d " + 
							"	where d.idstep = s.idstep and d.idsite in("+idsites+") " + 
							"	and doc_date::date between '"+dtda+"' and '"+dtal+"' and d.id = r.iddoc "+
							"   group by iddoc order by nrsku desc limit 1) nrrow, " + 
							"   case when json_agg(d.*)::text = '[null]' then '[]'::json else json_agg(d.*) end as docs " + 
							"FROM step s " + 
							"   LEFT JOIN LATERAL(select d.* from docs_dash d "+
							"   WHERE d.idstep = s.idstep and d.idsite in("+idsites+") "+
							"   and doc_date between '"+dtda+"' and '"+dtal+"' "+
							"   order by nr_row desc limit 10) d "+
							"ON TRUE "+
							"where s.idstep in("+step+") "+
							"group by s.idstep, description ";
					*/
					
					/*
					sql   = "SELECT s.idstep, description , color, " + 
							"	(select count(*) from documents d " + 
							"	where d.idstep = s.idstep and d.idsite in("+idsites+") " + 
							"	and doc_date::date between '"+dtda+"' and '"+dtal+"') val, " + 
							"	(select count(sku) nrsku from documents_detail r, documents d " + 
							"	where d.idstep = s.idstep and d.idsite in("+idsites+") " + 
							"	and doc_date::date between '"+dtda+"' and '"+dtal+"' and d.id = r.iddoc "+
							"   group by iddoc order by nrsku desc limit 1) nrrow, " + 
							"   case when json_agg(d.*)::text = '[null]' then '[]'::json else json_agg(d.*) end as docs " + 
							"FROM step s " + 
							"   LEFT JOIN LATERAL(select d.* from docs_dash d "+
							"   WHERE d.idstep = s.idstep and d.idsite in("+idsites+") "+
							"   and doc_date between '"+dtda+"' and '"+dtal+"' "+
							"   order by doc_date desc limit 10) d "+
							"ON TRUE "+
							"where s.idstep in("+step+") "+
							"group by s.idstep, description, color ";
					*/
					
					sql   = "SELECT s.idstep, description , color, " + 
							"	(select count(*) from documents d " + 
							"	where "+
							"   --d.idstep = s.idstep\n "+
							"   (case when s.type = 'N' then s.idstep=d.idstep else d.idstep in (select g.idstep from grouped_step g where g.grouped_idstep = s.idstep ) end) " + 
							"   and d.idsite in("+idsites+") " + 
							"	and doc_date::date between '"+dtda+"' and '"+dtal+"') val, " + 
							"	(select count(sku) nrsku from documents_detail r, documents d " + 
							"	where "+
							"   --d.idstep = s.idstep\n "+
							"   (case when s.type = 'N' then s.idstep=d.idstep else d.idstep in (select g.idstep from grouped_step g where g.grouped_idstep = s.idstep ) end) " + 
							"   and d.idsite in("+idsites+") " + 
							"	and doc_date::date between '"+dtda+"' and '"+dtal+"' and d.id = r.iddoc "+
							"   group by iddoc order by nrsku desc limit 1) nrrow, " + 
							"   case when json_agg(d.*)::text = '[null]' then '[]'::json else json_agg(d.*) end as docs " + 
							"FROM f_step s " + 
							"   LEFT JOIN LATERAL(select d.* from docs_dash2 d "+
							"   WHERE "+
							"   --d.idstep = s.idstep\n "+
							"   (case when s.type = 'N' then s.idstep=d.idstep else d.idstep in (select g.idstep from grouped_step g where g.grouped_idstep = s.idstep ) end) " + 
							"   and d.idsite in("+idsites+") "+
							"   and doc_date between '"+dtda+"' and '"+dtal+"' "+
							"   order by doc_date desc, doc_time desc limit 10) d "+
							"ON TRUE "+
							"where s.idstep in("+step+") "+
							"group by s.idstep, description, color ";
					
					
					System.out.println("***"+sql);
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					
					//docs in json
					String sel = "";
					String active = "";
					int count = 0;
					
					String docs = "";
					int nrrow = 0;
					while(rs.next()) {
						
						nrrow = rs.getInt("nrrow");
						docs = rs.getString("docs");
						
						JSONParser parser = new JSONParser();
						Object _obj = parser.parse(docs);
				
						JSONArray msg = (JSONArray) _obj;
						
						String tab = "";
						// loop array
						Iterator<String> iterator = msg.iterator();
						
						int count2=0;
						int doc_100 = nrrow;
						while (iterator.hasNext()) {
						    
							count2++;
							Object obj2 = iterator.next();
						    
						    JSONObject jsonObject2 = (JSONObject) obj2;
						    //System.out.println(jsonObject2);
						    
						    
						    String iddoc = ""+ jsonObject2.get("iddoc");
						    String doc_ref = (String) jsonObject2.get("doc_ref");
						    String site_description = (String) jsonObject2.get("site_description");
						    String doc_date = (String) jsonObject2.get("doc_date");
						    String doc_time = (String) jsonObject2.get("doc_time");
						    String nr_row = ""+ jsonObject2.get("nr_row");
						    String nr_read = ""+ jsonObject2.get("nr_read");
						    String status = ""+jsonObject2.get("status");
						    
						    status = Utility.noNull(status);
							if(status.length()==0) {
								status = "<span class=\"label bg-success\">OPN</span>";
							}
							else {
								status = "<span class=\"label bg-light\">CLS</span>";
							}
						    tab+="<tr><td>"+count2+".</td><td>"+status+"</td><td><a href=\"#\" onclick=\"viewDoc('"+iddoc+"')\">"+doc_ref+"</a></td><td>"+doc_date+"</td><td>"+doc_time+"</td><td><b>"+nr_row+"</b></td><td><b>"+nr_read+"</b></td>"+
						    		"<td style=\"width:40px\">"+
									"<div class=\"progress progress-xs\" style=\"margin-bottom:-10px\">"+
						            "<div class=\"progress-bar bg-dark\" data-toggle=\"tooltip\" data-original-title=\""+Math.round((((100*Double.parseDouble(nr_row))/doc_100)))+"%\" "+
									"style=\"width: "+Math.round((((100*Double.parseDouble(nr_row))/doc_100)))+"%\"></div>"+
									"</div></td></tr>";
						}
						
					
						
						count+=1;
						if(count==1) {
							sel = "bg-light dk";
							active = "active";
							step_selected = rs.getString("idstep");
							step_desc_selected = rs.getString("description");
						}
						else {
							sel = "";
							active = "";
						}
						steplist.add(new Tables(rs.getString("color"),rs.getString("idstep"),rs.getString("description"),
								rs.getString("val"), tab,sel,active));
						
					}
					rs.close();
					
					obj.put("col", col);
					obj.put("steplist", steplist);
					obj.put("step_selected", step_selected);
					obj.put("step_desc_selected", step_desc_selected);
					
					
					
					
					
					
					
					
				}
				
				
				String showTransact = ""+session.getAttribute("showTransact");
				showTransact=Utility.noNull(showTransact);
				
				System.out.println("showTransact: "+showTransact);
				obj.put("showTransact", showTransact);
				
				if(showTransact.length()>0) {
					
					// legge log transact e mette a video
					// Activa alarms
					LinkedList<Log> logList = Log.getLogsDash(conn);
		            obj.put("logList",logList);
		            
		            
		            for(int i=0;i<logList.size();i++) {
		            	tot++;
		            }
		            
				}
				
				
				// ---------
				List<Tables2> ris = new ArrayList<Tables2>();
				ris.clear();
				
				sql = "select analisidoc.*, (select count(0) from timeline where id = iddoc) timeline from analisidoc where idstep in ("+step+") "
						+ "and idsite in("+idsites+") and doc_date between '"+dtda+"' and '"+dtal+"' ";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				int num = 0;
				while(rs.next()) {
					num++;
					ris.add(new Tables2(rs.getString("id"),rs.getString("idstep"),rs.getString("idsite"),rs.getString("doc_date"),
							rs.getString("doc_ref"),rs.getString("inizio"),rs.getString("fine"),rs.getString("durata"),
							rs.getString("pezzi"),rs.getString("tempo_medio"),rs.getString("timeline"),"",""));
				}
				rs.close();
				
				obj.put("num", num);
				obj.put("ris", ris);
				
				System.out.println("num: "+num);
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(stAdmin!=null)
				stAdmin.close();
			if(connAdmin!=null)
				connAdmin.close();
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
		}
		
		obj.put("cus", cus);
		obj.put("tot", tot);
        
		
		System.out.println("idcus: "+idcus);
		
		obj.put("idcus", idcus);
		obj.put("nr_customers", nr_customers);
		
		return new ModelAndView("/rfid2/main/index", obj);
	}
	
}
