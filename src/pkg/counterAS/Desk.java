package pkg.counterAS;


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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.ccn.Stores;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Pivot;
import pkg.utility.Utility;
import pkg.utility.Pivot.PivotReport;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();

		 
		
		List<Tables> cus = new ArrayList<Tables>();
		List<Stores> sto = new ArrayList<Stores>();
		List<Tables> tiles = new ArrayList<Tables>();
		List<Tables> stores = new ArrayList<Tables>();
		
		
		// Pulisce la lista
		cus.clear();
		sto.clear();
		tiles.clear();
		stores.clear();
		
		String id_customer = ""+req.getParameter("id_customer");
		id_customer=Utility.noNull(id_customer);
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties prop = new ReadProperties("connection.properties");
		String SAAS_endpoint = prop.get("saas_endpoint_dan");
		
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
			sql  =  "select account_code, account_code_evolvestore, description from customers where "
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
				cus_id = rs.getString("account_code");
				cus.add(new Tables("CUS",cus_id, cus_id+" - "+rs.getString("description"),"","","",""));
				
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
				session.setAttribute("dscus", Utility.getCustomerDescriptionByAccountCode(conn, (String) session.getAttribute("idcus")));
			}
			
			obj.put("idcus", idcus);
			// Se idcus e' valorizzato
			if(idcus.length()>0) {
				
				// kpi
				sql  =  "select idkpi from rel_cus_kpi where idcus = "+idcus+" and counter = true ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
			
				if(rs.next()) {
					kpi = rs.getString("idkpi");
				}
				else {
					kpi = "VIS";
				}
				rs.close();
				
				// kpi
				sql  =  "select idkpi from rel_cus_kpi where idcus = "+idcus+" and counterout = true ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
			
				if(rs.next()) {
					kpiout = rs.getString("idkpi");
				}
				else {
					kpiout = "EXI";
				}
				rs.close();
				
				
				
				
				
				
				
				obj.put("kpi", kpi);
				session.setAttribute("kpi", kpi);
				
				obj.put("kpiout", kpiout);
				session.setAttribute("kpiout", kpiout);
				
				
				
				
				
				
				
				
				String _verso = Utility.noNull(req.getParameter("verso"));
				if(_verso.length()==0)
					_verso = "I";
				obj.put("_verso", _verso);
				
				session.setAttribute("_verso", _verso);
				String kkk = "";
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
				
				
				String formula = "";
				sql  =  "SELECT json_agg(kpi_formulacomp) FROM kpi_formulacomp\r\n"
						+ "where kpi in ('"+kkk+"')";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();
				if(rs.next()) {
					formula = rs.getString(1);
				}
				rs.close();
				
				System.out.println(formula);
				
				String idstores = "";
				String idstoresReq = "";
				String desstores = "";
				
				stores = Stores.getStoresByAccountCodeAndUsername(conn, idcus, session.getAttribute("username").toString());
				
				
				String bardata = "";
				String xAxis = "";
				String series = "";
				int perc = 0;
				count = 0;
				String intest = "";
				String ids = "";
				String latlon = "";
				String total = "";
				
				// Min / Max per tiles
				int min = 999999999;
				int max = 0;
				
				List<PivotData> reports = new ArrayList<PivotData>();
				for(Tables store : stores) {
					idstoresReq+="\""+store.getXcdel()+"\",";
					desstores+=store.getXdtab().replaceAll(","," ")+",";
					xAxis+="'"+store.getXdtab().replaceAll("'", "\\'").replaceAll(",", " ")+"',";
            		intest+=store.getXdtab().replaceAll(","," ").replaceAll("'", "\\'").replaceAll(",", " ")+",";
            		ids+=store.getXcdel()+",";
					latlon+=store.getCmp1()+";"+store.getCmp2()+",";
				}
				if(idstoresReq.length()>0)
					idstoresReq=idstoresReq.substring(0,idstoresReq.length()-1);
				if(desstores.length()>0)
					desstores=desstores.substring(0,desstores.length()-1);
				idstores = idstoresReq.replaceAll("\"","");
				
				reports = Pivot.fillZero(oggiIso,oggiIso,desstores,"2","D");
				
				obj.put("idstores", idstores.replaceAll("\\*", ","));
				
				//oggiIso = "2020-11-03";
				
				
				if(idstores.length()>0) {
					
					String jsonParm = "{"
							+ "    \"account_id\": \""+idcus+"\","
							+ "    \"stores\": ["
							+ idstoresReq
							+ "    ],"
							+ "    \"kpi\": ["
							+ "        \""+kkk+"\""
							+ "    ],"
							+ "    \"from_date\": \""+oggiIso+"\","
							+ "    \"to_date\": \""+oggiIso+"\","
							+ "    \"time_grouped\": \"D\","
							+ "    \"groupByDoor\" : false,"
							+ "	   \"calc\": "+formula+" "
							+ "}";
					
					System.out.println(jsonParm);
					
					
					
					org.json.JSONObject json = Utility.consumeRest(SAAS_endpoint, "POST", jsonParm, "");
					org.json.JSONArray data = json.getJSONArray("listings");
					System.out.println(data.toString());
					
					for (int i = 0; i < data.length(); i++) {
						reports.add(new PivotData(data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName"), oggiIso, data.getJSONObject(i).getInt("value")));
					}
					
					String arr[] = PivotReport.create(reports).toString().split("\n");
					int idx = 0;
					for(String row : arr) {
						if(idx>0) {
							System.out.println(row);
							total+=row.split("\t")[1]+",";
							series+=row.split("\t")[1]+",";
							
						}
						idx++;
					}
					
					if(xAxis.length()>0)
						xAxis=xAxis.substring(0,xAxis.length()-1);
					if(intest.length()>0)
						intest=intest.substring(0,intest.length()-1);
					if(total.length()>0)
						total=total.substring(0,total.length()-1);
					if(series.length()>0)
						series=series.substring(0,series.length()-1);
					if(ids.length()>0)
						ids=ids.substring(0,ids.length()-1);
					if(latlon.length()>0)
						latlon=latlon.substring(0,latlon.length()-1);
					
					System.out.println(xAxis);
					System.out.println(intest);
					System.out.println(total);
					System.out.println(series);
					System.out.println(ids);
					System.out.println(latlon);
					
					String temp[] = ids.split(",");
					String temp2[] = intest.split(",");
					String temp3[] = total.split(",");
					String temp4[] = latlon.split(",");
					
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
						
						try {
							perc = (Integer.parseInt(temp3[i])*100/max);
						}
						catch(Exception e) {}
						
						String col = getCol(perc);
						
						tiles.add(new Tables(""+(i+1),temp[i],temp2[i],temp3[i],col,"#5B6A79",""+perc));
						
						xAxis+="'"+temp2[i].replaceAll("'", "\\'").replaceAll(",", " ")+" | ID:"+temp[i]+"',";
						
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
						catch(Exception e) {}
						
						Stores s = new Stores(Integer.parseInt(temp[i]),  
								false, temp2[i].replaceAll("'", "").replaceAll("\"", ""), 
								""+perc, "", "", lat,lon, 0, Integer.parseInt(idcus), 
								""+(i+1), temp4[i], col, temp3[i]);
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
					
					
					obj.put("min", min);
					obj.put("max", max);
					
					
				}
				
				session.setAttribute("sto",sto);
				
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
		
		
		return new ModelAndView("/counterAS/main/index", obj);
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
