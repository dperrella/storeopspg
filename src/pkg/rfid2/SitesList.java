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

import pkg.beans.rfid.Site;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class SitesList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Site> sites = new ArrayList<Site>();
		sites.clear();
		
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
			Site bean = new Site();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				String automatic = req.getParameter("automatic");
				String warehouseCode = req.getParameter("warehouse_code");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setIdCustomer(idcus);	         
	            	bean.setAutomatic(Boolean.parseBoolean(automatic));
	            	bean.setWarehouseCode(warehouseCode);
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIdSite(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	bean.setIdCustomer(idcus);
	            	bean.setAutomatic(Boolean.parseBoolean(automatic));
	            	bean.setWarehouseCode(warehouseCode);
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdSite(Integer.parseInt(id));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			sites = Site.getSitesByIdCustomer(conn, idcus);
			
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
		
		obj.put("sites", sites);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/sites-list", obj);
	}
	
}
