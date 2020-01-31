package pkg.rfid2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.rfid.Location;
import pkg.beans.rfid.ReaderConfig;
import pkg.beans.rfid.Site;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class SitesLocationList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Location> loc = new ArrayList<Location>();
		loc.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		
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
		String idsite = req.getParameter("idsite");
		
		obj.put("idsite", idsite);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			// Inizializzazione Bean
			Location bean = new Location();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idLocation = req.getParameter("id");
				String description = req.getParameter("description");
				String id_locationtype = req.getParameter("id_locationtype");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setIdSite(Integer.parseInt(idsite));
	            	bean.setDescription(description);
	            	bean.setIdLocationtype(Integer.parseInt(id_locationtype));
	            	bean.setIdCustomer(idcus);;	            	
	            	bean.add(conn);
	            	
	            	break;
	            	
	            case "M": 
	            	
	            	bean.setIdLocation(Integer.parseInt(idLocation));
	            	bean.setIdSite(Integer.parseInt(idsite));
	            	bean.setDescription(description);
	            	bean.setIdLocationtype(Integer.parseInt(id_locationtype));
	            	bean.setIdCustomer(idcus);           	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdLocation(Integer.parseInt(idLocation));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			loc = Location.getLocationByIdsite(conn, Integer.parseInt(idsite));
			
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
		
		obj.put("loc", loc);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/sites-location-list", obj);
	}
	
}
