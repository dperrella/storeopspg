package pkg.analyticsAS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables2;
import pkg.beans.ccn.Stores;
import pkg.counterAS.PivotData;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Pivot;
import pkg.utility.Utility;
import pkg.utility.Pivot.PivotReport;

public class DeskReport extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> sto = new ArrayList<Tables2>();
		sto.clear();
		
		List<Tables> _sto = new ArrayList<Tables>();
		_sto.clear();
		
		List<Tables> _rs = new ArrayList<Tables>();
		_rs.clear();
		
		
		List<Tables> chart = new ArrayList<Tables>();
		chart.clear();
		
		ArrayList<HashMap<String, String>> kpiList;
		kpiList = new ArrayList<>();
		kpiList.clear();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		ReadProperties prop = new ReadProperties("connection.properties");
		String SAAS_endpoint = prop.get("saas_endpoint_dan");
		
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat us = new SimpleDateFormat("M/d/yyyy hh:mm:ss aa");
		
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
		String idcus = (String) session.getAttribute("idcus");
		String accountCode = (String) session.getAttribute("accountCode");
		
		Connection connAdmin = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		// -------------------
		String kpi = "";
		int nrkpi = 0;
		String idstores = "";
		int nrstores = 0;
		String dtda = "";
		String dtal = "";
		String dates = "No Date Selected";
		String comparison = "";
		String comparison_type = "No Comparison";
		String tipoPeriod = "";
		String tipo2 = "";
		String quarter = "";
		String group = "";
		String upd = "";
		
		String _kpi = "";
		String _kpi2 = "";
		
		int rowspan = 0;
		try {
			
			conn = Utility.getConnData();
			
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
			//st.close();
		
			session.setAttribute("uid", uid);
			
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
			
			kpi = ""+req.getParameter("kpi");
			kpi = Utility.noNull(kpi);
			
			idstores = ""+req.getParameter("idstores");
			idstores = Utility.noNull(idstores);
			
			dtda = ""+req.getParameter("dtda");
			dtda = Utility.noNull(dtda);
			
			dtal = ""+req.getParameter("dtal");
			dtal = Utility.noNull(dtal);
			
			tipoPeriod = ""+req.getParameter("tipoPeriod");
			tipoPeriod = Utility.noNull(tipoPeriod);
			
			tipo2 = ""+req.getParameter("tipo2");
			tipo2 = Utility.noNull(tipo2);
			
			quarter = ""+req.getParameter("quarter");
			quarter = Utility.noNull(quarter);
			
			comparison = ""+req.getParameter("comparison");
			comparison = Utility.noNull(comparison);
			
			group = ""+req.getParameter("group");
			group = Utility.noNull(group);
			
			if(tipoPeriod.length()==0) {
				tipoPeriod = "W";
			}
			
			String tipoReq = "";
			if(tipo.equals("D"))
				tipoReq = "H";
			if(tipo.equals("W"))
				tipoReq = "D";
			if(tipo.equals("M"))
				tipoReq = "D";
			if(tipo.equals("P"))
				tipoReq = "D";


			
			if(comparison.trim().length()==0) {
				comparison = "C01";
			}
			
			upd = ""+req.getParameter("upd");
			upd = Utility.noNull(upd);
			
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
					//st.close();
					
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
						//st.close();
						
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
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					while(rs.next()) {
						dtda=rs.getString("dtda");
						dtal=rs.getString("dtal");
						tipoPeriod = rs.getString("tipo");
						dates=dtda+" / "+dtal;
					}
					rs.close();
					//st.close();
					
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
					//st.close();
					
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
						//st.close();
					}
					
					if(kpi.length()>0)
						kpi = kpi.substring(0,kpi.length()-1);
					
				}
				else {
					String temp[]=kpi.split(",");
					nrkpi = temp.length;		
				}
				
				if(comparison.length()==0) {
					
					sql   = "select comparison from pref_user_comparison where idusr = '"+username+"' ";
					st = conn.prepareStatement(sql);
					rs = st.executeQuery();
					if(rs.next()) {
						comparison=rs.getString("comparison");
					}
					rs.close();
					//st.close();
					
					// Se non ci sono preferenze li seleziona tutti
					if(comparison.trim().length()==0) {
						comparison = "C01";
					}
					
				}
				else {
					comparison_type = comparison;
				}
				
				
				// kpi per sql selezione per giorno
				String kpi_in = "";
				String kpi_in2 = "";
				
				String temp[] = kpi.split(",");
				rowspan = 0;
				for(int i=0;i<temp.length;i++) {
					rowspan+=1;
					kpi_in+="''"+temp[i]+"'',";
					kpi_in2+="'"+temp[i]+"',";
				}
				if(kpi_in.length()>0)
					kpi_in=kpi_in.substring(0,kpi_in.length()-1);
				if(kpi_in2.length()>0)
					kpi_in2=kpi_in2.substring(0,kpi_in2.length()-1);
				
				
				System.out.println("------");
				System.out.println(tipoPeriod);
				System.out.println(dtda);
				System.out.println(dtal);
				System.out.println(quarter);
				System.out.println(idstores.replaceAll(",","*"));
				System.out.println(kpi.replaceAll(",","*"));
				
				comparison = comparison.trim();
				
				group = ""+group;
				group = Utility.noNull(group);
				if(group.length()==0)
					group="4";
				
				obj.put("kpi", kpi);
				obj.put("nrkpi", nrkpi);
				obj.put("nrstores", nrstores);
				obj.put("idstores", idstores);
				obj.put("comparison", comparison);
				obj.put("comparison_type", comparison_type);
				obj.put("dtda", dtda);
				obj.put("dtal", dtal);
				obj.put("quarter", quarter);
				obj.put("tipoPeriod", tipoPeriod);
				obj.put("kpi_in", kpi_in2);
				obj.put("group", group);
				obj.put("dates", dates);
				
				
				//Formattazione data
				Date d1 = sdf.parse(dtda);
				dtda = iso.format(d1);
				Date d2 = sdf.parse(dtal);
				dtal = iso.format(d2);
				
				System.out.println(dtda+" "+dtal);
				
				
				
				// *******
				// Se spuntato "Only Updates Stores ricalcola i negozi
//				if(upd.length()>0) {
//					
//					sql   = "select * from updated_stores('"+dtal+"'::date, '"+idstores.replaceAll(",","*")+"') ";
//					System.out.println(sql);
//					st = conn.prepareStatement(sql);
//					rs = st.executeQuery();
//					if(rs.next()) {
//						idstores = ""+rs.getString(1);
//						idstores = Utility.noNull(idstores);
//						idstores = idstores.replaceAll("\\*", ",");
//						
//						String temp2[] = idstores.split(",");
//						nrstores = temp2.length;
//						
//						obj.put("nrstores", nrstores);
//						obj.put("idstores", idstores);
//						obj.put("group", group);
//						
//					}
//					rs.close();
//					//st.close();
//					
//				}
				// *******
				
				
				
				String _kpiList[] = kpi.split(",");
				for(String k : _kpiList) {
					_kpi+="'"+k+"',";
					_kpi2+="\""+k+"\",";
					
				}
				_kpi = Utility.removeLast(_kpi);
				_kpi2 = Utility.removeLast(_kpi2);
				_sto = Stores.getStoresById(conn, idstores);
				
				
				String formula = "";
				sql  =  "SELECT json_agg(kpi_formulacomp) FROM kpi_formulacomp\r\n"
						+ "where kpi in ("+_kpi+")";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();
				if(rs.next()) {
					formula = rs.getString(1);
				}
				rs.close();
				
				String _stores = "";
				String _idstores = "";
				String _idstoresKpi = "";
				
				
				for(Tables store : _sto) {
					_stores+="\""+store.getXcdel()+"\",";
					_idstores+=store.getXcdel()+",";
					for(String k : _kpiList) {
						_idstoresKpi+=store.getXdtab().replaceAll(",", " ")+"##@@"+k+",";
					}
				}
				_stores = Utility.removeLast(_stores);
				_idstores = Utility.removeLast(_idstores);
				_idstoresKpi = Utility.removeLast(_idstoresKpi);
				
				obj.put("_idstores", _idstores);
				obj.put("_idstoresKpi", _idstoresKpi);
				
				
				
				System.out.println("accountCode: "+accountCode);
				System.out.println("idstores: "+idstores);
				System.out.println("kpi: "+kpi);
				System.out.println("_kpi: "+_kpi);
				System.out.println("_kpi2: "+_kpi2);
				System.out.println("idstoresKpi: "+_idstoresKpi);
				
				System.out.println("formula: "+formula);
				System.out.println("stores: "+_stores);
				
				
				
				
				
				String jsonParm = "{"
						+ "    \"account_id\": \""+accountCode	+"\","
						+ "    \"stores\": ["
						+ _stores
						+ "    ],"
						+ "    \"kpi\": ["
						+ "        "+_kpi2+" "
						+ "    ],"
						+ "    \"from_date\": \""+dtda+"\","
						+ "    \"to_date\": \""+dtal+"\","
						+ "    \"time_grouped\": \""+tipoReq+"\","
						+ "    \"groupByDoor\" : false,"
						+ "	   \"calc\": "+formula+" "
						+ "}";
				
				System.out.println(jsonParm);
				
				org.json.JSONObject json = Utility.consumeRest(SAAS_endpoint, "POST", jsonParm, "");
				org.json.JSONArray data = json.getJSONArray("listings");
				
				List<PivotData> table = new ArrayList<PivotData>();
				table = Pivot.fillZero(dtda,dtal,_idstoresKpi,"2","D");
				
				List<PivotData> total = new ArrayList<PivotData>();
				total = Pivot.fillZero(dtda,dtda,_idstoresKpi,"2","D");
				
				
				
				for (int i = 0; i < data.length(); i++) {
					table.add(new PivotData(data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName")+"##@@"+data.getJSONObject(i).getString("kpi"), iso.format(us.parse(data.getJSONObject(i).getString("date"))), data.getJSONObject(i).getInt("value")));	
					total.add(new PivotData(data.getJSONObject(i).getString("storeId")+" - "+data.getJSONObject(i).getString("storeName")+"##@@"+data.getJSONObject(i).getString("kpi"), dtda, data.getJSONObject(i).getInt("value")));	
					
				}
				
				PivotReport _total = PivotReport.create(total);
				
				// add total
				String arrTotal[] = _total.toString().split("\n");
				int _idx = 0;
				for(String __total : arrTotal) {
					if(_idx>0) {
						String __store = __total.split("\t")[0];
						int __value = Integer.parseInt(__total.split("\t")[1]);
						
						table.add(new PivotData(__store, "TOTAL", __value));	
						
					}
					_idx++;
				}
				
				PivotReport _table = PivotReport.create(table);
				System.out.println("--- ************************");
				System.out.println(_table.toString());
				System.out.println("************************ ---");
				
				
				_idx = 0;
				String arrRs[] = _table.toString().split("\n");
				String th = "";
				for(String __rs : arrRs) {
					if(_idx>0) {
						
					}
					else {
						
						String intest[] = __rs.split("\t");
						th+="<th>"+intest[0]+"</th><th>"+intest[1]+"</th>";
						
						for(int t=2;t<intest.length;t++) {
							th+="<th>"+intest[t]+"</th>";
						}
					}
				}
				
				
				// *******
				// REPORT
				
				// Paginazione
				String pag = ""+req.getParameter("pag");
				pag = Utility.noNull(pag);
				if(pag.length()==0) {
					pag = "1";
				}
				obj.put("pag", pag);
				
				String disPrev = "";
				String disNext = "";
				
				
				
				
				
				int rowIni = 0;
				int rowFin = 0;
				int nrPag = 0;

				int numRow = 0;
				String prev = "";
				String next = "";
				String nrRecord = ""+(5*nrkpi);

				// =========================================
				// Numero pagine totali
				sql   = "select * from " + 
						"public.report_gridcount('"+dtda+"','"+dtal+"', " + 
						"'"+idstores.replaceAll(",", "*")+"', " + 
						"'"+kpi.replaceAll(",","*")+"','"+tipoPeriod+"',false,"+group+",'"+comparison+"') ";
				
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				if(rs.next()) {
					numRow=rs.getInt(1);
					System.out.println(numRow);
				}
				rs.close();
				//st.close();
				
				nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
				System.out.println("nrPag: "+nrPag);
				System.out.println("pag  : "+pag);
				
				int max = 0;
				
				if(nrRecord.length()>0) {
					
					for (int i=1; i<=nrPag; i++) {
						
						max+=Integer.parseInt(nrRecord);
						
						if(i==Integer.parseInt(pag)) {
							
							rowIni = (max-Integer.parseInt(nrRecord));
							rowFin = Integer.parseInt(nrRecord);
							
						}
						
					}
				
				}
				
				System.out.println(rowIni+" "+rowFin);
				
				if(Integer.parseInt(pag)==1)
					disPrev = "disabled";
				if(Integer.parseInt(pag)==nrPag)
					disNext = "disabled";
				
				obj.put("disPrev", disPrev);
				obj.put("disNext", disNext);
				obj.put("nrPag", nrPag);
				obj.put("nrRecord", nrRecord);
				obj.put("numRow", numRow);
				
				obj.put("rowspan", rowspan);
				
				sql   = "select * from test.report_grid('"+dtda+"','"+dtal+"',"
         				+ "'"+idstores.replaceAll(",","*")+"',"
         				+ "'"+kpi.replaceAll(",","*")+"','"+tipoPeriod+"',false,"+group+",'"+comparison+"', '"+uid+"') "
         				+ "limit "+rowFin+" offset "+rowIni+" ";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				try {
					rs = st.executeQuery();
				}
				catch(Exception e) {}
				
				// Var per grafico
		        String stores = "";
		        
				int count = 0;
		        int num = 0;
		        
		        String td = "";
		        
		        String descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Store</th>";
				
				switch (group) {
				case "1":
					descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Customer</th>";
					break;
				case "2":
					descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Nation</th>";
					break;
				case "3":
					descr = "<th style=\"border-top: 2px solid #E0E0E0;\">Region</th>";
					break;
				}
				
				String idcat = "";
				String category = "";
				
				while(rs.next()) {
					
					idcat = rs.getString("idcat");
					category = rs.getString("category");
					
					if(count==0) {
		        		
		        		if(num>0) {
		        			
		        			//System.out.print(rs.getString("category")+" ");
		        			// Inserimento in array della riga
			        		sto.add(new Tables2("STO",""+num,td,
			        				idcat,category,"","","","","","","",""));
		        		}
		        		td="";
		        		
		        		if(group.equals("1")||group.equals("2")||group.equals("3")) {
		        			
		        			if(group.equals("1")) {
		        				//stores+="'"+rs.getString("category")+"',";
				        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','1')\"><b>"+Utility.getCustomerDescription(conn, rs.getString("category"))+"</b></a></td>";
			        		}
		        			else if(group.equals("2")) {
		        				//stores+="'"+rs.getString("category")+"',";
				        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','2')\"><b>"+Utility.getNationDescription(conn, rs.getString("category"))+"</b></a></td>";
			        		}
		        			else if(group.equals("3")) {
		        				//stores+="'"+rs.getString("category")+"',";
				        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><a href=\"#\" class=\"btn btn-default\" onclick=\"getNode('"+rs.getString("category")+"','3')\"><b>"+Utility.getRegionDescription(conn, rs.getString("category"))+"</b></a></td>";		
		        			}
		        		}
		        		else if(group.equals("4")){
			        		//stores+="'"+rs.getString("category").replaceAll("'", " ")+"',";
			        		td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\">"
			        			+ "<a href=\"#\" class=\"btn btn-default btn-block\" onclick=\"getDetail('"+rs.getString("idcat")+"')\">Store Detail</a>"
			        			+ "<b>"+rs.getString("category")+"</b></td>";
		        		}
		        		else {
		        			td+="<tr><td class=\"b-r\" style=\"width:120px\" rowspan=\""+rowspan+"\"><b>"+rs.getString("category")+"</b></td>";
			        	}
					}
					if(count>=1&&count<=rowspan)
		        		td+="<tr>";
					
					td+="<td class=\"b-r\" style=\"width:120px\">"+rs.getString("desckpi")+"</td>";
					
					for(int i=0;i<55;i++) {
						if(rs.getString((i+6))!=null) {
							//System.out.print(rs.getString((i+6))+" ");
							td+="<td class=\"b-r text-right\">"+Utility.round(rs.getDouble((i+6)) , 2)+"</td>";
							
						}
					}
					
					td+="<td class=\"b-r text-right\" style=\"width:120px\"><b>"+Utility.round(rs.getDouble("tot"), 2)+"</b></td>";
     				
					//System.out.println(rs.getString("tot"));
					
					td+="</tr>";
		        	
					count+=1;
		        	if(count==rowspan) {
		        		count = 0;
		        	}
		        	num+=1;
		        	
				}
				rs.close();
				//st.close();
				
				// Inserimento in array della riga (ULTIMA RIGA)
	    		sto.add(new Tables2("STO",""+num,td,
	    				idcat,category,"","","","","","","",""));
				
				obj.put("td", td);
				obj.put("sto", sto);
				
				String th=descr+"<th style=\"border-top: 2px solid #E0E0E0;\">KPI</th>";
				
				String times="";
		        
				// Intestazione
				switch (tipoPeriod) {
	         	case "Y": 
	         		
	         		
	         		Calendar startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		Calendar endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));

	         		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	         		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	         		
	         		for(int i=0;i<=diffMonth;i++) {
	         			
	         			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
	         		
	         			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
	         			
	         			startCalendar.add(Calendar.MONTH, 1);
	         			
	         		}
	         		
	         		
	         		break;
	         		
	         	case "YW": 
	         		
	         		/*
	         		String settimane = getWeeks(conn, idcus, dtal.substring(0, 4));
	         		String temp222[] = settimane.split(",");
	         		int ini = Integer.parseInt(temp222[0]);
	         		int fin = Integer.parseInt(temp222[1]);
	         		*/
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));
	         		
	         		long diffWeeks = 0;
	         		for( long t=1; ; t++ ) {         
	         			startCalendar.add( Calendar.WEEK_OF_YEAR, 1 );   // add one day, week, year, etc.
	         			if( startCalendar.after(endCalendar) ) {
	         	            diffWeeks = t-1;
	         	            break;
	         			}
	         	    }
	         		
	         			         		
	         		for(int i=0;i<=diffWeeks;i++) {
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">W"+(i+1)+"</th>";
	         		
	         			times+="'W"+i+"',";
	         			
	         		}
	         		
	         		break;
	         		
	         	case "Q":
	         		
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));

	         		diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	         		diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	         		
	         		for(int i=0;i<=diffMonth;i++) {
	         			
	         			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"</th>";
		         		
	         			times+="'"+propLang.get(iso.format(startCalendar.getTime()).substring(5,7))+"',";
	         			startCalendar.add(Calendar.MONTH, 1);
	         			
	         		}
	         		
	         		break;
	         		
	         	case "M":
				
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));
	         		
	         		Date startDate = startCalendar.getTime();
	         		Date endDate = endCalendar.getTime();
	         		long startTime = startDate.getTime();
	         		long endTime = endDate.getTime();
	         		long diffTime = endTime - startTime;
	         		//long diffDays = diffTime / (1000 * 60 * 60 * 24);
	         					
	         		//long diffDays = diffTime / (1000 * 60 * 60 * 24);
	         		double abc = diffTime / (1000.0 * 60.0 * 60.0 * 24.0);
	        		long diffDays = (long) Math.round(abc);                  
	        		
	         		
	         		DateFormat dateFormat = DateFormat.getDateInstance();
	         		System.out.println("The difference between "+
	         		  dateFormat.format(startDate)+" and "+
	         		  dateFormat.format(endDate)+" is "+
	         		  diffDays+" days.");
	         		
	         		for(int i=0;i<=diffDays;i++) {
	         			
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
		         		
	         			times+="'"+iso.format(startCalendar.getTime())+"',";
	         			startCalendar.add(Calendar.DATE, 1);
	         			
	         		}
	         		
	         		break;
	         		
	         	case "W":
	         		
	         		startCalendar = new GregorianCalendar();
	         		startCalendar.setTime(iso.parse(dtda));
	         		endCalendar = new GregorianCalendar();
	         		endCalendar.setTime(iso.parse(dtal));
	         		
	         		startDate = startCalendar.getTime();
	         		endDate = endCalendar.getTime();
	         		startTime = startDate.getTime();
	         		endTime = endDate.getTime();
	         		diffTime = endTime - startTime;
	         		
	         		abc = diffTime / (1000.0 * 60.0 * 60.0 * 24.0);
	        		diffDays = (long) Math.round(abc);                  
	        		
	        		dateFormat = DateFormat.getDateInstance();
	         		System.out.println("The difference between "+
	         		  dateFormat.format(startDate)+" and "+
	         		  dateFormat.format(endDate)+" is "+
	         		  diffDays+" days.");
	         		
	         		for(int i=0;i<=diffDays;i++) {
	         			
	         			System.out.println(iso.format(startCalendar.getTime()));
	         			
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+iso.format(startCalendar.getTime())+"</th>";
	         			
	         			times+="'"+iso.format(startCalendar.getTime())+"',";
	         			
	         			startCalendar.add(Calendar.DATE, 1);
	         			
	         		}
	         		
	         		break;
	         		
	         	case "D":
	         		
	         		for(int i=0;i<24;i++) {
	         			String hh = "00"+i;
	         			hh=hh.substring(hh.length()-2,hh.length());
	         			th+="<th style=\"border-top: 2px solid #E0E0E0;\">"+hh+":00</th>";
	         		}
	         		
	         		/*
	         		th="<th style=\"border-top: 2px solid #E0E0E0;\">08:00</th>"
	         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">09:00</th>"
	         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">10:00</th>"
	         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">11:00</th>"
	         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">12:00</th>"
	         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">13:00</th>"
	         			+ "<th style=\"border-top: 2px solid #E0E0E0;\">14:00</th>"
         				+ "<th style=\"border-top: 2px solid #E0E0E0;\">15:00</th>"
         				+ "<th style=\"border-top: 2px solid #E0E0E0;\">16:00</th>"
         				+ "<th style=\"border-top: 2px solid #E0E0E0;\">17:00</th>"
         				+ "<th style=\"border-top: 2px solid #E0E0E0;\">18:00</th>"
         				+ "<th style=\"border-top: 2px solid #E0E0E0;\">19:00</th>"
         				+ "<th style=\"border-top: 2px solid #E0E0E0;\">20:00</th>"
         				+ "<th style=\"border-top: 2px solid #E0E0E0;\">21:00</th>";
	         		*/
	         		
	         		times+="'00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10,11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24',";
	         		break;
				}
				
				th+="<th style=\"border-top: 2px solid #E0E0E0;\">Total</th>";
				
				obj.put("th", th);
				
				
				
				if(times.length()>0)
					times=times.substring(0, times.length()-1);
				
				obj.put("times", times);
				
				
				
				
				// Grafico
				sql   = "select idcat, category, codkpi, tot, color from test.report_grid('"+dtda+"','"+dtal+"', "
						+ "'"+idstores.replaceAll(",","*")+"','"+kpi.replaceAll(",","*")+"',"
						+ "'"+tipoPeriod+"',false,"+group+",'"+comparison+"','"+uid+"') , ztables "
						+ "where xcdtb = 'KPI' and trim(codkpi) = trim(xcdel) ";

				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				
				// Var per grafico
		        stores = "";
		        
				count = 0;
		        num = 0;
		        
				while(rs.next()) {
					
					// tmp hash map for single contact
                    HashMap<String, String> row = new HashMap<>();

                    
                    if(count==0) {
		        		
		        		
		        		if(group.equals("1")||group.equals("2")||group.equals("3")) {
		        			
		        			if(group.equals("1")) {
		        				stores+="'"+Utility.getCustomerDescription(conn, rs.getString("category"))+"',";
				        	}
		        			else if(group.equals("2")) {
		        				stores+="'"+Utility.getNationDescription(conn, rs.getString("category"))+"',";
				        	}
		        			else if(group.equals("3")) {
		        				stores+="'"+Utility.getRegionDescription(conn, rs.getString("category"))+"',";
				        	}
		        		}
		        		else {
			        		stores+="'"+rs.getString("category").replaceAll("'", " ")+"',";
			        	}
		        		
		        		td = "";
					}
                    
					
					
					//System.out.print(rs.getString("codkpi")+" ");
					//System.out.println(Utility.round(rs.getDouble("tot"), 2));
					
					row.put(rs.getString("codkpi"), ""+Utility.round(rs.getDouble("tot"), 2));
					row.put("color", rs.getString("color"));
					kpiList.add(row);
					
					count+=1;
		        	if(count==rowspan) {
		        		count = 0;
		        	}
		        	num+=1;
		        	
				}
				rs.close();
				//st.close();
				
				obj.put("stores", stores);
	    		
				String riga = "";
				String color = "";
				for(int i=0;i<temp.length;i++) {
					System.out.print(temp[i]+" ");
					for(int k=0;k<kpiList.size();k++) {
						//System.out.print(k+" ");
						String k2 = ""+kpiList.get(k).get(temp[i]);
						k2 = Utility.noNull(k2);
						
						if(k2.length()>0) {
							System.out.print(kpiList.get(k).get(temp[i])+" ");
							riga+=kpiList.get(k).get(temp[i])+",";
							color=kpiList.get(k).get("color");
						}
					}
					
					if(riga.length()>0)
						riga = riga.substring(0,riga.length()-1);
					
					chart.add(new Tables("KPI",temp[i],riga,color,"","",""));
					riga = "";
					color = "";
					System.out.println("--");
				}
				
			}
			
			
			
			// Total / avg
			String total = "";
			//total = ReportTotal.getTotal(conn, rowspan, dtda, dtal, idstores, kpi, tipoPeriod, "false", group, comparison, uid);
			
			obj.put("total", total);
			
			
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
		obj.put("idcus", idcus);
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		obj.put("chart", chart);
		
		return new ModelAndView("/analyticsAS/main/index-report", obj);
	}
	
	public static String getWeeks(Connection conn, String idcus, String year) {
		
		String weeks = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql = "select min(calperiod) p1, max(calperiod) p2 from financial_calendar2 " + 
					"where customer_id = "+idcus+" " + 
					"and caltype = 'W' and year = "+year+" ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				weeks = rs.getString("p1")+","+rs.getString("p2");
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
		return weeks;
		
	}
	
}
