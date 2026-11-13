package pkg.occupancy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.ccn.Stores;
import pkg.analytics2.CreateDefaultCalendar;
import pkg.beans.Tables;
import pkg.beans.Tables2;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk_BKP extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();

		 
		
		List<Tables> cus = new ArrayList<Tables>();
		List<Stores> sto = new ArrayList<Stores>();
		List<Tables2> tiles = new ArrayList<Tables2>();
		
		// Pulisce la lista
		cus.clear();
		sto.clear();
		tiles.clear();
		
		String id_customer = ""+req.getParameter("id_customer");
		id_customer=Utility.noNull(id_customer);
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String oggiIta = dateIta.format(date);
		String oggiIso = dateIso.format(date);
		
		String currentYear = oggi.substring(0,4);
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		
		Connection conn = null;
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int nr_customers = 0;
		int nr_stores = 0;
		int nr_devices = 0;
		
		// Dalla request
		String idcus = ""+req.getParameter("idcus");
		idcus = Utility.noNull(idcus);
		
		// Se dalla request è vuoto legge dalla session
		if(idcus.length()==0) {
			idcus = ""+session.getAttribute("idcus");
			idcus = Utility.noNull(idcus);
		}
		
		int count = 0;
		String kpi = "";
		String kpiout = "";
		
		
		try {
			
			if(idcus.length()>0) {
				session.setAttribute("idcus", idcus);
				nr_customers = 1;
			}
			
			conn = Utility.getConnData();
			
			if(idcus.length()>0) {
				session.setAttribute("idcus", idcus);
				nr_customers = 1;
			}
			
			// ==================
			// NUMERO CLIENTI e lista clienti
			// ==================
			sql  =  "select id, description from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
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
				session.setAttribute("dscus", Utility.getCustomerDescription(conn, (String) session.getAttribute("idcus")));
			}
			
			obj.put("idcus", idcus);
			// Se idcus e' valorizzato
			if(idcus.length()>0) {
				
				// Controlla se è il caso di creare il calendario di default...
				CreateDefaultCalendar.createCalendar(conn, idcus);

				String idstores = "";
				
				idstores = Stores.getIdsOccupancyByIdCustomerAndUsername(conn, Integer.parseInt(idcus), session.getAttribute("username").toString());
				
				System.out.println(idstores);
				obj.put("idstores", idstores.replaceAll("\\*", ","));
				
				if(idstores.length()>0) {
					
					sql   = "select * from counter.occupancy_dashboard('"+idstores+"') ";
					
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs=st.executeQuery();
					
					String[] w = {"https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0008_clear_sky_night.png","https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0002_sunny_intervals.png","https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0017_cloudy_with_light_rain.png","https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0001_sunny.png","https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0004_black_low_cloud.png","https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0006_mist.png"};
					
					String[] colors = {"#414883", "#676473", "#8e8165", "#a7945d", "#d5b754", "#efb74d", "#e78842", "#e1643b", "#df4c37", "#dc3a34"};
					String bardata = "";
					
					String xAxis = "";
					String series = "";
					
					int perc = 0;
					
					count = 0;
					String inc2="";
	
					String array = "";
					String intest = "";
					String ids = "";
					String latlon = "";
					String lastTime = "";
					String bg = "";
					String total = "";
					int nrcol = 0;
					
					// Min / Max per tiles
					int min = 999999999;
					int max = 0;
					
					while(rs.next()) {
						
						array = ""+rs.getString("_array");
						array = Utility.noNull(array);
						
						// 0 = desc, 999 = tot, 1000 = id
						// Intestazioni
						if(rs.getInt("_indice")==0) {
							String temp[] = array.split(",");
							intest = array;
							for(int i=0;i<temp.length;i++) {
								xAxis+="'"+temp[i].replaceAll("'", "\\'").replaceAll(",", " ")+"',";
							}
						}
						// Totali
						else if(rs.getInt("_indice")==999) {
							
							total = array;
							series=array;
							
						}
						// Ids
						else if(rs.getInt("_indice")==1000) {
							
							ids = array;
							
						}
						// lat/lon
						else if(rs.getInt("_indice")==1001) {
							
							latlon = array;
							
						}
						// lastTime
						else if(rs.getInt("_indice")==1002) {
							
							lastTime = array;
							
						}
						// Background
						else if(rs.getInt("_indice")==1003) {
							
							bg = array;
							
						}
					}
					rs.close();
					
					String temp[] = ids.split(",");
					String temp2[] = intest.split(",");
					String temp3[] = total.split(",");
					String temp4[] = latlon.split(",");
					String temp5[] = lastTime.split(",");
					String temp6[] = bg.split(",");
					
					
					
					// per min / max
					for(int i=0;i<temp.length;i++) {
						if(Integer.parseInt(temp3[i])>max)
							max = Integer.parseInt(temp3[i]);
						
						if(Integer.parseInt(temp3[i])<min)
							min = Integer.parseInt(temp3[i]);
					}
					
					String query = "";
					
					ArrayList<String> q = new ArrayList<String>();
					int n = 0;
					String query2 = "";
					
					xAxis = "";
					for(int i=0;i<temp.length;i++) {
						
						Random random = new Random();
						int index = random.nextInt(w.length);
						
						try {
							perc = (Integer.parseInt(temp3[i])*100/max);
						}
						catch(Exception e) {}
						
						String col = getCol(perc);
						
						String background = "";
						switch(temp6[i]) {
						case "N":
						default :
							background = "bg-white";
							break;
						case "W":
							background = "bg-warning";
							break;
						case "E":
							background = "bg-danger";
							break;
							
						}
						
						
						//background = "bg-warning";
						
						tiles.add(new Tables2(""+(i+1),temp[i],temp2[i],temp3[i],col,temp5[i],""+perc,
								background,"","","","",""));
						
						xAxis+="'"+temp2[i].replaceAll("'", "\\'").replaceAll(",", " ")+" | ID:"+temp[i]+" | Date:"+temp5[i]+"',";
						
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
						
						Stores s = new Stores(Integer.parseInt(temp[i]),  
								false, temp2[i].replaceAll("'", "").replaceAll("\"", ""), 
								""+perc, temp5[i], "", lat,lon, 0, Integer.parseInt(idcus), 
								""+(i+1), iLat+","+iLon, col, temp3[i]);
						sto.add(s);
						
						//query+=temp4[i].replaceAll("\\*", ",")+";";
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
						
					
					
					
					
					if(xAxis.length()>0)
						xAxis=xAxis.substring(0,xAxis.length()-1);
					if(query.length()>0)
						query=query.substring(0,query.length()-1);
					
					if(q.size()==0)
						q.add(query);
				
					obj.put("bardata", bardata);
					obj.put("xAxis", xAxis);
					obj.put("series", series);
					obj.put("sto", sto);
					obj.put("query", query);
					
					
					obj.put("q", q);
					
					session.setAttribute("sto",sto);
					
					obj.put("min", min);
					obj.put("max", max);
					
					
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
			
		}
		
		obj.put("oggi", oggiIta);
		obj.put("currentYear", currentYear);
		
		obj.put("nr_customers", nr_customers);
		obj.put("nr_stores", nr_stores);
		obj.put("nr_devices", nr_devices);
		obj.put("sto", sto);
		
		obj.put("username", username);
		
		obj.put("id_customer", id_customer);
		obj.put("cus", cus);
		obj.put("tiles", tiles);
		
		
		return new ModelAndView("/occupancy/main/index", obj);
	}
	
	private String getCol(int perc) {
		
		String col = "";
		
		if(perc<=0) 
			col = "#ffffff";
		else if(perc>0&&perc<=10)
			col = "#414883";
		else if(perc>10&&perc<=20)
			col = "#676473";
		else if(perc>20&&perc<=30)
			col = "#8e8165";
		else if(perc>30&&perc<=40)
			col = "#a7945d";
		else if(perc>40&&perc<=50)
			col = "#d5b754";
		else if(perc>50&&perc<=60)
			col = "#efb74d";
		else if(perc>60&&perc<=70)
			col = "#e78842";
		else if(perc>70&&perc<=80)
			col = "#e1643b";
		else if(perc>80&&perc<=90)
			col = "#df4c37";
		else if(perc>90&&perc<=100)
			col = "#dc3a34";
		
		return col;
		
	}
	
}
