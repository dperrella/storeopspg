package pkg.counter;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class WeeklyReportCounter2 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables2> period = new ArrayList<Tables2>();
		
		List<Tables> sto = new ArrayList<Tables>();
		List<Tables2> adj = new ArrayList<Tables2>();
		period.clear();
		sto.clear();
		adj.clear();
		
		// Formato data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
				
		Connection conn = null;
		Connection connData = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String msg = "";
		
		String tipo = req.getParameter("tipo");
		obj.put("tipo", tipo);
		
		String idstores = "";
		String[] itemsStores = req.getParameterValues("id_store");
		String _idstores = "";
		
		int nrstores = 0;
		if(itemsStores==null){
			System.out.println("NULL - STORES");
		}
		else {
			System.out.println("NO NULL - STORES");
			for(int i = 0; i < itemsStores.length; i++){
				nrstores+=1;
			    idstores+=""+itemsStores[i]+",";
			    _idstores+=""+itemsStores[i]+"*";
			}
		}
		if(idstores.length()>0)
			idstores = idstores.substring(0,idstores.length()-1);
		
		if(_idstores.length()>0)
			_idstores = _idstores.substring(0,_idstores.length()-1);
		
		System.out.println(_idstores);
		
		boolean oneStore = false;
		String iddevices = "";
		
		
		String yearWeek = req.getParameter("yearWeek");
		String week = req.getParameter("week");
		String weekstart = req.getParameter("weekstart");
		String dtda = "";
		String dtal = "";
		
		obj.put("weekstart", weekstart);
		
		String category = "4";
		String uid = "";
		String uid2= "";
		
		// kpi (solo ADMIN)
		String kpi = ""+req.getParameter("kpi");
		kpi = Utility.noNull(kpi);
		if(kpi.length()==0)
			kpi = "VIS";
		
		Locale lc = new Locale("en","UK");
		if(weekstart.equals("M")) {
			lc = new Locale("en","UK");
		}
		if(weekstart.equals("S")) {
			lc = new Locale("en","US");
		}
		
		try {
			// Apre la connessione
			conn = Utility.getConnCounter();
			connData = Utility.getConnData();
			
			
        	// Stores
			String th = "";
			
			
			String $xcdtb = "";
			String $xcdel = "";
			String $xdtab = "";
			
			String giorno = "";
			Calendar c = null;
			String td = "";
			
			int af = 0;
			
			int nrdev = 0;
			String descrStore = "";
			
			
			if(nrstores==1) {
				
				category = "5";
				descrStore = getDescription(conn, Integer.parseInt(idstores));
				obj.put("descrStore", " - Store: "+descrStore);
				
			}
			obj.put("totperc", 100/nrstores);
			
					
    		// Get calendar set to current date and time
    		c = Calendar.getInstance(lc);
    		if(weekstart.equals("M")) {
    			c.setFirstDayOfWeek(Calendar.MONDAY);
    		}
    		if(weekstart.equals("S")) {
    			c.setFirstDayOfWeek(Calendar.SUNDAY);
    		}
    		

			// Set the calendar to monday of the current week
			c.set(Calendar.YEAR, Integer.parseInt(yearWeek));     
			c.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));     
			if(weekstart.equals("M")) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}
			if(weekstart.equals("S")) {
				c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
			dtda = sdf.format(c.getTime());
			//c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			c.add(Calendar.DATE, 6);
			dtal = sdf.format(c.getTime());
			
    		
				
        	obj.put("dtda", dtda);
        	obj.put("dtal", dtal);
        	
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		finally {
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
			if(connData!=null)
				connData.close();
		}
		
		obj.put("dsfun", "Report 1-1");
		obj.put("msg", msg);
		
		// parametri passati dalla maschera
		obj.put("nrstores", nrstores);
		obj.put("idstores", idstores);
		obj.put("_idstores", _idstores);
		
		// Periodo analizzato (con dati)
		obj.put("period", period);
		// Stores per Charts
		obj.put("sto", sto);
		
		return new ModelAndView("/counter/report/weekly-report-counter2", obj);
	}
	
	private static String getDescription(Connection conn, int id_store) {
		
		String description = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			sql   = "select description from stores where id = ? ";
			st = conn.prepareStatement(sql);
			
			st.setInt(1, id_store);
			rs=st.executeQuery();
			if(rs.next()) {
				description = rs.getString("description");
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
		
		return description;
		
	}
	
}
