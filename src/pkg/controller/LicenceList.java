package pkg.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
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

import pkg.beans.Aree;
import pkg.beans.Devices;
import pkg.beans.Tables;
import pkg.beans.licence.MobileLicence;
import pkg.beans.rfid.Printers;
import pkg.beans.Device_Detail;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class LicenceList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<MobileLicence> licence = new ArrayList<MobileLicence>();
		licence.clear();
		
		HttpSession session = req.getSession();
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		// Per operazioni di CRUD
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String msg = "";
		
		DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			// Apertura connessione
			conn = Utility.getConnAdmin();
			
			// Inizializzazione Bean
			MobileLicence bean = new MobileLicence();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idapp = req.getParameter("idapp");
				String idcustomer = req.getParameter("idcustomer");
				String macAddr = req.getParameter("macaddr");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	System.out.println("insert");
	            	bean.setIdApp(Integer.parseInt(idapp));
	            	bean.setIdCustomer(Integer.parseInt(idcustomer));
	            	bean.setMacAddr(macAddr);
	            	bean.setFromDate(iso.format(new Date()));	
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	System.out.println("edt");
	            	bean.setIdApp(Integer.parseInt(idapp));
	            	bean.setIdCustomer(Integer.parseInt(idcustomer));
	            	bean.setMacAddr(macAddr);
	            	bean.setFromDate(iso.format(new Date()));	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	System.out.println("delete");
	            	
	            	bean.setIdApp(Integer.parseInt(idapp));
	            	bean.setIdCustomer(Integer.parseInt(idcustomer));
	            	bean.setMacAddr(macAddr);
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			licence = MobileLicence.getLicences(conn);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("licence", licence);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/licence/licence-list", obj);
	}
	
}
