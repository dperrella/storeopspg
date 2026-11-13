package pkg.occupancy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();

		List<Tables> cus = new ArrayList<Tables>();
		List<Stores> sto = new ArrayList<Stores>();
		List<Stores> sto2 = new ArrayList<Stores>();
		List<Tables2> tiles = new ArrayList<Tables2>();
		
		// Pulisce la lista
		cus.clear();
		sto.clear();
		sto2.clear();
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
		
		DateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
				
				obj.put("accountCode", getAccountId(conn, idcus));
				
				// Controlla se è il caso di creare il calendario di default...
				CreateDefaultCalendar.createCalendar(conn, idcus);

				String idstores = "";
				
				idstores = Stores.getIdsOccupancyByIdCustomerAndUsername(conn, Integer.parseInt(idcus), session.getAttribute("username").toString());
				
				System.out.println(idstores);
				obj.put("idstores", idstores.replaceAll("\\*", ","));
				
				if(idstores.length()>0) {
					
					String page = ""+req.getParameter("page");
					page = Utility.noNull(page);
					
					String qq = ""+req.getParameter("q");
					qq = Utility.noNull(qq);
					
					
					obj.put("qq", qq);
					String cond = "";
					if(qq.length()>0)
						cond = "and upper(store_code||description) like '%"+qq.toUpperCase().replaceAll("'", "''")+"%' ";
					
					if(page.length()==0)
						page = "1";
					
					int nrPag = 0;
					int numRow = 0;
					String nrRecord = req.getParameter("nr_record");
					nrRecord=Utility.noNull(nrRecord);
					if(nrRecord.length()==0)
						nrRecord = "48";
					
					
					sql = "select count(*) num from f_stores where id in("+idstores.replaceAll("\\*", ",")+") "+cond+"; ";
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs=st.executeQuery();
					if(rs.next()) {
						numRow=rs.getInt("num");
					}
					rs.close();
					st.close();
					
					nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
					
					
					int limit = Integer.parseInt(nrRecord);
					int offset = (Integer.parseInt(page)*limit)-limit;
					
					ArrayList<Tables> paging = new ArrayList<Tables>();
					
					for (int i=1; i<=nrPag; i++) {
						String sel = "";
						String prev = "";
						String next = "";
						String disprev = "";
						String disnext = "";
						if(i==Integer.parseInt(page)) {
							sel = "active";
							prev = ""+(i-1);
							next = ""+(i+1);
							
							if(Integer.parseInt(prev)<i)
								disprev = "disabled";
							if(Integer.parseInt(next)>i)
								disnext = "disabled";
						}
						paging.add(new Tables("PAG",""+i,sel,prev,next,disprev,disnext));
						
					}
					
					obj.put("paging", paging);
					
					sql = "select * from f_stores where id in("+idstores.replaceAll("\\*", ",")+") "+cond+" order by store_code::integer ";
					sql+= "limit "+limit+" offset "+offset+" ";
					
					System.out.println(sql);
					st = conn.prepareStatement(sql);
					rs=st.executeQuery();
					
					
					String bardata = "";
					
					String xAxis = "";
					String series = "";
					
					int perc = 0;
					
					count = 0;
					
					int i = 0;
					
					String query = "";
					ArrayList<String> q = new ArrayList<String>();
					int n = 0;
					String query2 = "";
					while(rs.next()) {
						
						i++;
						
						
						float iLat = 0;
						float iLon = 0;
						String lat = "0";	
						String lon = "0";
						
						float iLat2 = 0;
						float iLon2 = 0;
						
						
						try {
							
							lat = rs.getString("latitude");
							lon = rs.getString("longitude");
							iLat = (int) Math.round((rs.getDouble("latitude")*1000));
							iLon = (int) Math.round((rs.getDouble("longitude")*1000));
							
							iLat2 = (int) Math.round((rs.getDouble("latitude")*100));
							iLon2 = (int) Math.round((rs.getDouble("longitude")*100));
							
							
						}
						catch(Exception e) {}
						
						String llll = "Lat_"+round(iLat2/100,2)+"_and_Lon_"+round(iLon2/100,2);
						llll=llll.replaceAll("\\.", "_");
						llll=llll.replaceAll("\\+", "p");
						llll=llll.replaceAll("-", "m");
						
						String llll2 = "Lat_"+round((iLat2+1)/100,2)+"_and_Lon_"+round(iLon2/100,2);
						llll2=llll2.replaceAll("\\.", "_");
						llll2=llll2.replaceAll("\\+", "p");
						llll2=llll2.replaceAll("-", "m");
						
						String llll3 = "Lat_"+round((iLat2)/100,2)+"_and_Lon_"+round((iLon2+1)/100,2);
						llll3=llll3.replaceAll("\\.", "_");
						llll3=llll3.replaceAll("\\+", "p");
						llll3=llll3.replaceAll("-", "m");
						
						String llll4 = "Lat_"+round((iLat2-1)/100,2)+"_and_Lon_"+round(iLon2/100,2);
						llll4=llll4.replaceAll("\\.", "_");
						llll4=llll4.replaceAll("\\+", "p");
						llll4=llll4.replaceAll("-", "m");
						
						String llll5 = "Lat_"+round((iLat2)/100,2)+"_and_Lon_"+round((iLon2-1)/100,2);
						llll5=llll5.replaceAll("\\.", "_");
						llll5=llll5.replaceAll("\\+", "p");
						llll5=llll5.replaceAll("-", "m");
						
						String llll6 = "Lat_"+round((iLat2+1)/100,2)+"_and_Lon_"+round((iLon2+1)/100,2);
						llll6=llll6.replaceAll("\\.", "_");
						llll6=llll6.replaceAll("\\+", "p");
						llll6=llll6.replaceAll("-", "m");
						
						String llll7 = "Lat_"+round((iLat2-1)/100,2)+"_and_Lon_"+round((iLon2-1)/100,2);
						llll7=llll7.replaceAll("\\.", "_");
						llll7=llll7.replaceAll("\\+", "p");
						llll7=llll7.replaceAll("-", "m");
						
						
						tiles.add(new Tables2(""+(i),rs.getString("id"),rs.getString("store_code")+" "+rs.getString("description"),"","",ts.format(new Date()),"0",
								"",rs.getString("store_code"),"","","","__ico"+llll+" __ico"+llll2+" __ico"+llll3+" __ico"+llll4+" __ico"+llll5+" __ico"+llll6+" __ico"+llll7));

//						tiles.add(new Tables2(""+(iLat/1000)+"_"+(iLon/1000),rs.getString("id"),rs.getString("store_code")+" "+rs.getString("description"),"","",ts.format(new Date()),"0",
//								"",rs.getString("store_code"),"","","",""));

						
						xAxis+="'"+rs.getString("store_code")+" "+rs.getString("description").replaceAll("'", "\\'").replaceAll(",", " ")+" | ID:"+rs.getString("id")+" | Date: -',";
						
						
						System.out.println(rs.getString("id")+" "+iLat+" "+iLon);
						
						Stores s = new Stores(rs.getInt("id"),  
								false, rs.getString("store_code")+" "+rs.getString("description").replaceAll("'", "").replaceAll("\"", ""), 
								"0", "", "", lat,lon, 0, Integer.parseInt(idcus), 
								""+(i), iLat+","+iLon, "#df5c56", "");
						sto.add(s);
						
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
					rs.close();
					st.close();
					
					sql = "select * from f_stores where id in("+idstores.replaceAll("\\*", ",")+") "+cond+" order by store_code::integer ";
					st = conn.prepareStatement(sql);
					rs=st.executeQuery();
					i=0;
					while(rs.next()) {
						
						float iLat = 0;
						float iLon = 0;
						String lat = "0";	
						String lon = "0";
						
						try {
							
							lat = rs.getString("latitude");
							lon = rs.getString("longitude");
							iLat = (int) Math.round((rs.getDouble("latitude")*1000));
							iLon = (int) Math.round((rs.getDouble("longitude")*1000));
						}
						catch(Exception e) {}
						
						System.out.println(rs.getString("id")+" "+iLat+" "+iLon);
						
						Stores s = new Stores(rs.getInt("id"),  
								false, rs.getString("store_code")+" "+rs.getString("description").replaceAll("'", "").replaceAll("\"", ""), 
								"0", "", "", lat,lon, 0, Integer.parseInt(idcus), 
								""+(i), iLat+","+iLon, "#df5c56", "");
						sto2.add(s);
						
					}
					rs.close();
					st.close();
					
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
					obj.put("sto2", sto2);
					
					obj.put("query", query);
					
					
					obj.put("q", q);
					
					session.setAttribute("sto",sto2);
					
					
					
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
	
	private static String getAccountId(Connection conn, String idcus) {
		String accountCode = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql= "select account_code from customers where id = "+idcus+" ";
			System.out.println("***** "+sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				accountCode = rs.getString("account_code");
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
 		
		return accountCode;
	}
	
	public static BigDecimal round(float d, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(d));
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
	    return bd;
	}
	
}
