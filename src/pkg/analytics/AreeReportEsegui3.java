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
import pkg.beans.AreaMovement;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AreeReportEsegui3 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		List<Tables> wk = new ArrayList<Tables>();
		List<Tables> mn = new ArrayList<Tables>();
		List<Tables2> data = new ArrayList<Tables2>();
		
		// Pulisce la lista
		wk.clear();
		mn.clear();
		data.clear();
		
		String id_store = ""+req.getParameter("id_store");
		id_store=Utility.noNull(id_store);
		
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
		
		String orario = ""+req.getParameter("orario");
		orario=Utility.noNull(orario);
		
		obj.put("orario", orario);
		
		String table = "vw_flowreport";
		
		if(orario.equals("S")) {
			table = "vw_flowreport_orari";
		}
		
		String req_day = req.getParameter("day");
		String req_yearWeek = req.getParameter("yearWeek");
		String req_week = req.getParameter("week");
		String req_yearMonth = req.getParameter("yearMonth");
		String req_month = req.getParameter("month");
		String req_dtda = req.getParameter("dtda");
		String req_dtal = req.getParameter("dtal");
		String req_tipo = req.getParameter("tipo");
		String rep = req.getParameter("rep");
		
		obj.put("day", req_day);
		obj.put("yearWeek", req_yearWeek);
		obj.put("week", req_week);
		obj.put("yearMonth", req_yearMonth);
		obj.put("month", req_month);
		obj.put("dtda", req_dtda);
		obj.put("dtal", req_dtal);
		obj.put("tipo", req_tipo);
		obj.put("rep", rep);
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		PreparedStatement st2 = null;
		ResultSet rs2 = null;
		
		String sql = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAnalytics();
			
			// ==================
			// STORES UTENTE
			// ==================
			// sql da eseguire
			sql  =  "select a.*, c.brandname custbrand, "
					+ "(select count(b.id) from devices b where b.id_store=a.id) nrdevices "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "and a.id = "+id_store+" ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $description="";
			
			if(rs.next()) {
				$description=rs.getString("description");
			}
			rs.close();
			st.close();
			obj.put("description_store",$description);
			
			// =======================
			// Mesi
			String mm = "";
			String desMm = "";
			String $cmp1 = "";
			
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
				
				switch (req_tipo) {
	        	case "D":  
	        		
	        		req_dtda = req_day;
	        		req_dtal = req_day;
	        		
	        		break;
	        		
	        	case "W":
	        		
	        		c = Calendar.getInstance();

	    			// Set the calendar to monday of the current week
	    			c.set(Calendar.YEAR, Integer.parseInt(req_yearWeek));     
	    			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(req_week));     
	    			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    			
	    			req_dtda = sdf.format(c.getTime());
	    			c.add(Calendar.DATE, 6);
	    			req_dtal = sdf.format(c.getTime());
	    			
	    			
	    			
	        		break;
	        		
	        	case "M":
	        		
	        		c = Calendar.getInstance();
	        		c.set(Calendar.MONTH, Integer.parseInt(req_month)-1);
	        		
	        		req_dtda = "01/"+req_month+"/"+req_yearMonth;
	            	req_dtal = c.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+req_month+"/"+req_yearMonth;
	            	
	            	
	            	
	        		break;
	        		
	        	case "P":
	        		
	        		req_dtda = req_dtda;
	        		req_dtal = req_dtal;

	        		break;
				
				}
				
				// ===============
				// REPORT
				// ===============
				String bool_orario = "false";
				if(orario.equals("S"))
					bool_orario = "true";
				else
					bool_orario = "false";
				
				// Prima riga
				sql   = "select * from rep_flow_report(1, "+
	        			"'"+req_dtda.substring(6,10)+"-"+req_dtda.substring(3,5)+"-"+req_dtda.substring(0,2)+"', "+
	        			"'"+req_dtal.substring(6,10)+"-"+req_dtal.substring(3,5)+"-"+req_dtal.substring(0,2)+"', "+bool_orario+") "+
	        			"order by ordine_ ";
				st = conn.prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
				
				// Esegue l'sql
				rs=st.executeQuery();
				int id = 0;
				int parentid = 0;
				int id_linea = 0;
				boolean hasChild = false;
				String albero = "<ul>";
				while(rs.next()) {
					
					id=rs.getInt("id_");
    				parentid = rs.getInt("parentid_");
    				id_linea = rs.getInt("id_linea_");
    				hasChild = hasChild(conn, id, id_linea);
    				
    				if(hasChild) {
    					
    					System.out.println(id+" "+rs.getString("descrizione_")+" ha figli");
    					
    					albero+="<li><a href=\"#\">"+rs.getString("descrizione_")+"<br/><span style=\"font-size:40px\">"+rs.getInt("valore_")+"</span></a>";
    					
    					// carica Figli...
    					albero+=readChild(conn, id, id_linea, req_dtda, req_dtal, table);
    					
    					albero+="</li>";
    				}
    				else {
    					System.out.println(id+" "+rs.getString("descrizione_")+" NON ha figli");
    					
    					albero+="<li><a href=\"#\">"+rs.getString("descrizione_")+"<br/><span style=\"font-size:40px\">"+rs.getInt("valore_")+"</span></a></li>";
    				}
    				
				}
				rs.close();
				st.close();
				
				albero+="</ul>";
				
				System.out.println(albero);
        		
        		obj.put("albero", albero);
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
		
		obj.put("id_store", id_store);
		
		return new ModelAndView("/app/report/aree-report-esegui3", obj);
	}
	
	private static boolean hasChild(Connection conn, int id, int id_linea) {
		
		boolean has = false;
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			st=conn.createStatement();
			sql   = "select count(parentid) count from flowreport_Config "
					+ "where parentid = "+id+" "
					+ "and id_linea = "+(id_linea+1)+" ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				if(rs.getInt("count")>0)
					has=true;
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(st!=null) {
				try {
					st.close();
				}
				catch(Exception e) {}
			}
		}
		
		return has;
	}
	
	private static String readChild(Connection conn, int id, int id_linea, String dtda, String dtal, String table) {
		
		String child = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			st=conn.createStatement();
			
			sql   = "select id, descrizione, sum(valore) valore, id_linea, parentid "
					+ "from "+table+" "
					+ "where parentid = "+id+" "
					+ "and (year||'-'||month||'-'||day)::date between "
					+ "'"+dtda.substring(6,10)+"-"+dtda.substring(3,5)+"-"+dtda.substring(0,2)+"' "
					+ "and "
					+ "'"+dtal.substring(6,10)+"-"+dtal.substring(3,5)+"-"+dtal.substring(0,2)+"' "
					+ "group by id, descrizione, id_linea, parentid ";
			//System.out.println(sql);
			rs=st.executeQuery(sql);
			int parentid = 0;
			boolean hasChild = false;
			child = "<ul>";
			while(rs.next()) {
				
				
				id=rs.getInt("id");
				parentid = rs.getInt("parentid");
				id_linea = rs.getInt("id_linea");
				hasChild = hasChild(conn, id, id_linea);
				
				if(hasChild) {
					
					System.out.println(id+" "+rs.getString("descrizione")+" ha figli");
					
					child+="<li><a href=\"#\">"+rs.getString("descrizione")+"<br/><span style=\"font-size:40px\">"+rs.getInt("valore")+"</span></a>";
					
					// carica Figli...
					child+=readChild(conn, id, id_linea, dtda, dtal, table);
					
					child+="</li>";
				}
				else {
					System.out.println(id+" "+rs.getString("descrizione")+" NON ha figli");
					
					child+="<li><a href=\"#\">"+rs.getString("descrizione")+"<br/><span style=\"font-size:40px\">"+rs.getInt("valore")+"</span></a></li>";
				}
				
			}
			rs.close();
			child+= "</ul>";
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(st!=null) {
				try {
					st.close();
				}
				catch(Exception e) {}
			}
		}
		
		return child;
	}
	
}
