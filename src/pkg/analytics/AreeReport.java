package pkg.analytics;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Aree;
import pkg.beans.Tables2;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AreeReport extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		List<Stores> sto = new ArrayList<Stores>();
		List<Tables> wk = new ArrayList<Tables>();
		List<Tables> mn = new ArrayList<Tables>();
		List<Aree> aree = new ArrayList<Aree>();
		
		// Pulisce la lista
		sto.clear();
		wk.clear();
		mn.clear();
		aree.clear();
		
		String id_store = ""+req.getParameter("id_store");
		id_store=Utility.noNull(id_store);
		
		String rep = ""+req.getParameter("rep");
		rep=Utility.noNull(rep);
		
		if(rep.length()==0)
			rep = "1";
		
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
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int nr_customers = 0;
		int nr_stores = 0;
		int nr_devices = 0;
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAnalytics();
			
			// ==================
			// NUMERO NEGOZI
			// ==================
			// sql da eseguire
			sql  =  "select count(a.id) nrstores "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore::integer from rel_user_stores "
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
					+ "id in(select idcustomer::integer from rel_user_customers "
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
					+ "and a.id_store in(select idstore::integer from rel_user_stores "
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
			
			// ==================
			// STORES UTENTE
			// ==================
			// sql da eseguire
			sql  =  "select a.*, c.brandname custbrand, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore::integer from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $description="";
			String $address="";
			String $country="";
			String $city="";
			String $postalcode="";
			String $region="";
			String $contact="";
			String $cphone="";
			String $sphone="";
			String $cmail="";
			String $smail="";
			String $coordinates="";
			String $store_code="";
			String $brandname="";
			String $brandlogo="";
			String $id_customer="";
			String $id="";
			String $cmobile="";
			String $province="";
			String $id_optempl="";
			String $customeridstore="";
			String $sapid="";
			String $latitude="";
			String $longitude="";
			String $notes="";
			String $openhh="";
			String $closehh="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			int count = 0;
			while(rs.next()) {
				
				count+=1;
				
				$description=rs.getString("description");
				$address=rs.getString("address");
				$country=rs.getString("country");
				$city=rs.getString("city");
				$postalcode=rs.getString("postalcode");
				$region=rs.getString("region");
				$contact=rs.getString("contact");
				$cphone=rs.getString("cphone");
				$sphone=rs.getString("sphone");
				$cmail=rs.getString("cmail");
				$smail=rs.getString("smail");
				$coordinates=rs.getString("coordinates");
				$store_code=rs.getString("store_code");
				$brandname=rs.getString("brandname");
				$brandlogo=rs.getString("brandlogo");
				$id_customer=rs.getString("id_customer");
				$id=rs.getString("id");
				$cmobile=rs.getString("cmobile");
				$province=rs.getString("province");
				$id_optempl=rs.getString("id_optempl");
				$customeridstore=rs.getString("customeridstore");
				$sapid=rs.getString("sapid");
				$latitude=rs.getString("latitude");
				$longitude=rs.getString("longitude");
				$notes=rs.getString("notes");
				$openhh=rs.getString("openhh");
				$closehh=rs.getString("closehh");
				
				$cmp1=rs.getString("custbrand");
				$cmp2=rs.getString("nrdevices");
				
				sto.add(new Stores($description,$address,$country,$city,
						$postalcode,$region,$contact,$cphone,$sphone,$cmail,$smail,
						$coordinates,$store_code,$brandname,$brandlogo,$id_customer,
						$id,$cmobile,$province,$id_optempl,$customeridstore,$sapid,
						$latitude,$longitude,$notes,$openhh,$closehh,
						"","","",
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			
			
			// =======================
			// Mesi
			String mm = "";
			String desMm = "";
			$cmp1 = "";
			
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
			
			// Se è stato passato l'id di un negozio carico la lista delle aree...
			if(id_store.length()>0) {
				
				String cond = "";
				if(rep.equals("1"))
					cond=" and a.gruppo = 'CIS' ";
				
				sql  =  "select a.id_store, a.id_area, a.descrizione description, "
						+ "a.codice, b.description store_description, esterna, gruppo, tipo "
						+ "from tab_aree a, stores b "
						+ "where a.id_store = b.id and a.id_store = "+id_store+" "
						+ cond
						+ "order by a.id_area";
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Esegue l'sql
				rs=st.executeQuery();
				
				// Campi database
				String $id_store="";
				String $id_area="";
				String $aree_description="";
				String $codice="";
				String $store_description="";
				String $esterna="";
				String $gruppo="";
				String $tipo="";
			
				$cmp1="";
				$cmp2="";
				$cmp3="";
				$cmp4="";
				
				while(rs.next()) {
					
					count+=1;
					
					$id_store=rs.getString("id_store");
					$id_area=rs.getString("id_area");
					$aree_description=rs.getString("description");
					$codice=rs.getString("codice");
					$store_description=rs.getString("store_description");
					$esterna=rs.getString("esterna");
					if($esterna.toUpperCase().equals("T"))
						$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-out\"></i> "+propLang.get("aar_N")+"</span>";
					else
						$esterna="<span class=\"btn btn-default\"><i class=\"fa fa-sign-in\"></i> "+propLang.get("aar_S")+"</span>";
					$gruppo=rs.getString("gruppo");
					$tipo=rs.getString("tipo");
					aree.add(new Aree($id_store,$id_area,$aree_description,$codice,$store_description,$esterna,$gruppo,$tipo,
							"","",$cmp1,$cmp2,$cmp3,$cmp4));
					
				}
				rs.close();
				st.close();
				
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
		obj.put("aree", aree);
		obj.put("sto", sto);
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		obj.put("id_store", id_store);
		obj.put("rep", rep);
		obj.put("orario", orario);
		
		return new ModelAndView("/app/report/aree-report", obj);
	}
	
}
