package pkg.counter;


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
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables2;
import pkg.beans.Report;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Customers;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		List<Customers> cus = new ArrayList<Customers>();
		List<Stores> sto = new ArrayList<Stores>();
		List<Tables> wk = new ArrayList<Tables>();
		List<Tables> mn = new ArrayList<Tables>();
		List<Tables> tiles = new ArrayList<Tables>();
		
		// Pulisce la lista
		cus.clear();
		sto.clear();
		wk.clear();
		mn.clear();
		tiles.clear();
		
		String id_customer = ""+req.getParameter("id_customer");
		id_customer=Utility.noNull(id_customer);
		
		String orario = ""+req.getParameter("orario");
		orario=Utility.noNull(orario);
		
		if(orario.length()==0)
			orario = "S";
		
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
		String currentMonth = oggi.substring(4,6);
		String currentWeek = "";
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		String db = (String) session.getAttribute("db");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int nr_customers = 0;
		int nr_stores = 0;
		int nr_devices = 0;
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnCounter();
			
			// ==================
			// NUMERO NEGOZI
			// ==================
			// sql da eseguire
			sql  =  "select count(a.id) nrstores "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_stores=rs.getInt("nrstores");
			}
			rs.close();
			st.close();
			
			
			// ==================
			// NUMERO CLIENTI
			// ==================
			sql  =  "select count(id) nrcustomers from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_customers = rs.getInt("nrcustomers");
			}
			rs.close();
			st.close();
			
			
			// ==================
			// NUMERO DISPOSITIVI
			// ==================
			sql  =  "select count(a.id) nrdevices "
					+ "from devices a, stores b, customers c, devicetype d "
					+ "where a.id_store = b.id and b.id_customer = c.id "
					+ "and a.device_type = d.id "
					+ "and a.id_store in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_devices = rs.getInt("nrdevices");
			}
			rs.close();
			st.close();
			
			
			
			// =======================
			// Mesi
			String mm = "";
			String desMm = "";
			String $cmp1 = "";
			String $cmp2 = "";
			String $cmp3 = "";
			String $cmp4 = "";
			
			for(int i=1;i<=12;i++) {
				
				mm = "00"+i;
				mm = mm.substring(mm.length()-2, mm.length());
				
				if(mm.equals("01")) {
					desMm = propLang.get("gen");
				}
				else if(mm.equals("02")) {
					desMm = propLang.get("feb");
				}
				else if(mm.equals("03")) {
					desMm = propLang.get("mar2");
				}
				else if(mm.equals("04")) {
					desMm = propLang.get("apr");
				}
				else if(mm.equals("05")) {
					desMm = propLang.get("mag");
				}
				else if(mm.equals("06")) {
					desMm = propLang.get("giu");
				}
				else if(mm.equals("07")) {
					desMm = propLang.get("lug");
				}
				else if(mm.equals("08")) {
					desMm = propLang.get("ago");
				}
				else if(mm.equals("09")) {
					desMm = propLang.get("set");
				}
				else if(mm.equals("10")) {
					desMm = propLang.get("ott");
				}
				else if(mm.equals("11")) {
					desMm = propLang.get("nov");
				}
				else if(mm.equals("12")) {
					desMm = propLang.get("dic");
				}
				
				// Settimana attuale
				if(mm.equals(currentMonth)) {
					$cmp1 = "selected ";
				}
				
				// valorizza la lista
				mn.add(new Tables("MON",mm, desMm,$cmp1,"","",""));
				
				$cmp1 = "";
				
			}
			
			
			// =======================
			// Settimane
			
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(currentYear), 0, 1);
			
			// Formato data
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			  
			// Numero di settimane
			int weeks = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
			
			// Settimana attuale
			c.setTime(date);
			currentWeek = "00"+c.get(Calendar.WEEK_OF_YEAR);
			currentWeek = currentWeek.substring(currentWeek.length()-2,currentWeek.length());
			
			System.out.println(currentWeek);
			
			// Per settimanale
			Calendar cal = Calendar.getInstance();
			// Imposta l'anno corrente
			cal.set(Integer.parseInt(currentYear), 0, 1);
			
			String week = "";
			
			// campi tables
			String $xcdtb = "SET";
			String $xcdel = "";
			String $xdtab = "";
			$cmp1 = "";
			
			for (int i=1; i<=weeks; i++) {
				  
				week = "00"+i;
				week = week.substring(week.length()-2,week.length());
				
				// valorizza xcdel col numero della settimana
				$xcdel = week;
				
				cal.set(Integer.parseInt(currentYear), 0, 1);
				cal.set(Calendar.WEEK_OF_YEAR, i);        
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				
				// valorizza il primo giorno della settimana
				$xdtab=sdf.format(cal.getTime())+" - ";
				
				cal.add(Calendar.DATE, 6);  
				//cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				
				// valorizza l'ultimo giorno della settimana
				$xdtab+=sdf.format(cal.getTime());
				
				// Settimana attuale
				if($xcdel.equals(currentWeek)) {
					$cmp1 = "selected ";
				}
				
				// valorizza la lista
				wk.add(new Tables($xcdtb,$xcdel,$xdtab,$cmp1,"","",""));
				
				//*blank bariabili
				$xdtab = "";
				$cmp1 = "";
		  	}
			
				
			sql  =  "select * from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			String $cus_companyname;
			String $cus_description; 
			String $cus_brandname;
			String $cus_id;
			
			while(rs.next()) {
				
				$cus_companyname=rs.getString("companyname");
				$cus_description=rs.getString("description");
				$cus_brandname=rs.getString("brandname");
				$cus_id=rs.getString("id");
				
				cus.add(new Customers($cus_companyname,$cus_description,"",$cus_brandname,
						"","",$cus_id,"","","","",
						"","","","","",
						"","","",""));
				
			}
			rs.close();
			st.close();
			
			
			String condCustomer = "";
			if(id_customer.length()>0) {
				condCustomer = " and a.id_customer = "+id_customer+" ";
			}
			sql  =  "select a.id, a.description, c.brandname custbrand, "
					+ "customeridstore "
					+ "from stores a, customers c "
					+ "where a.id in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') and a.id_customer = c.id "+condCustomer+" ";
			System.out.println(sql);
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $sto_description="";
			String $sto_id="";
			String $customeridstore="";
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			String idstores = "";
			
			while(rs.next()) {
				
				$sto_description=rs.getString("description");
				$sto_id=rs.getString("id");
				$cmp1=rs.getString("custbrand");
				$customeridstore=""+rs.getString("customeridstore");
				$customeridstore=Utility.noNull($customeridstore);
				if($customeridstore.length()==0)
					$customeridstore=$sto_id;
				
				sto.add(new Stores($sto_description,"","","",
						"","","","","","","",
						"","","","","",
						$sto_id,"","","","","",
						"","","","","",
						"","","",
						$customeridstore,$cmp2,$cmp3,$cmp4));
				
				idstores+=rs.getString("id")+"*";
				
			}
			rs.close();
			st.close();
			
			if(idstores.length()>0) {
				idstores = idstores.substring(0,idstores.length()-1);
				
				// Max per tiles
				int max = 0;
				/*
				sql   = "select sum(_inc) inc, _id_store "
						+ "from public.pcountergrouped_daily('"+oggiIso+"','"+idstores+"') "
						+ "group by _id_store "
						+ "order by inc desc "
						+ "limit 1 ";
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();
				if(rs.next()) {
					max = rs.getInt("inc");
				}
				rs.close();
				*/
				
				/*
				sql   = "select _description, _id_store,  _inc inc "
						+ "from public.pcountergrouped_dashboard('"+oggiIso+"','"+idstores+"') "
						+ "order by inc desc";
				*/
				sql   = "select _description, _id_store, " + 
						"round(coalesce(max(_inc),null,0)  + ((coalesce(max(_inc),null,0)*max(adjustment_factor)::numeric)/100)) inc, _inc inc2 " + 
						"from " + 
						"public.pcountergrouped_dashboard('"+oggiIso+"','"+idstores+"') " + 
						", ad_store where _id_store = id_store " + 
						"group by _description, _id_store,  _inc " + 
						"order by inc desc ";
				/*
				sql   = "select description _description, a.id_store _id_store, " + 
						"coalesce(sum(ingresso)::numeric,null,0) inc, coalesce(sum(ingresso)::numeric,null,0) inc2 " + 
						"from (select description,id id_store from stores " + 
						"where stores.id in ("+idstores+")) a left join " + 
						"(select * from pgrouped_current_month where id_store in ("+idstores+") " + 
						"and to_timestamp(tic)::date = '"+oggiIso+"'::date " + 
						"and hour >= (select extract(hour from min(fromhour)) from dailyoperation_stores " + 
						"		where days = extract(dow from '"+oggiIso+"'::date) and " + 
						"		id_stores in ("+idstores+"))::integer " + 
						"and hour <= (select extract(hour from max(tohour)) from dailyoperation_stores " + 
						"		where days = extract(dow from '"+oggiIso+"'::date) and " + 
						"		id_stores in ("+idstores+"))::integer " + 
						") p on p.id_store = a.id_store " + 
						"group by a.id_store,description";
				*/
				
				System.out.println(sql);
				st = conn.prepareStatement(sql);
				rs=st.executeQuery();
				

				String[] colors = {"#EE5254","#7BC3D1","#7980B2","#F9CD2E","#EF8384","#5B6A79","#38D783","#3B464D"};
				String bardata = "";
				int perc = 0;
				
				int count = 0;
				String inc2="";
				while(rs.next()) {
					
					count+=1;
					if(count==1) {
						max = rs.getInt("inc");
					}
					
					
					Random random = new Random();
					int index = random.nextInt(colors.length);
					
					try {
						perc = (rs.getInt("inc")*100/max);
					}
					catch(Exception e) {}
					
					if(rs.getInt("inc")!=rs.getInt("inc2"))
						inc2="["+rs.getInt("inc2")+"]";
					else
						inc2="";
					
					bardata+="{ y: '"+rs.getString("_description").replaceAll("'", "\\'")+"', a: "+rs.getInt("inc")+"},";
					tiles.add(new Tables(inc2,rs.getString("_id_store"),rs.getString("_description"),
							""+rs.getInt("inc"),colors[index],"#5B6A79",""+perc));
					
					perc=0;
				}
				rs.close();
				if(bardata.length()>0)
					bardata=bardata.substring(0,bardata.length()-1);
				
				obj.put("bardata", bardata);
				
			}
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("mn", mn);
		obj.put("weeks", wk);
		obj.put("oggi", oggiIta);
		obj.put("currentYear", currentYear);
		
		obj.put("nr_customers", nr_customers);
		obj.put("nr_stores", nr_stores);
		obj.put("nr_devices", nr_devices);
		obj.put("sto", sto);
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		obj.put("orario", orario);
		
		obj.put("id_customer", id_customer);
		obj.put("cus", cus);
		obj.put("tiles", tiles);
		
		
		return new ModelAndView("/counter/main/index", obj);
	}
	
}
