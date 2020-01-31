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

import pkg.beans.rfid.LocationType;
import pkg.beans.rfid.Site;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class LoctypeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<LocationType> lot = new ArrayList<LocationType>();
		lot.clear();
		
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
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			// Inizializzazione Bean
			LocationType bean = new LocationType();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setIdCustomer(idcus);	            	
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIdLocationtype(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	bean.setIdCustomer(idcus);
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdLocationtype(Integer.parseInt(id));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			lot = LocationType.getLocationtypeByIdCustomer(conn, idcus);
			
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
		
		obj.put("lot", lot);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/config/loctype-list", obj);
	}
	
}
