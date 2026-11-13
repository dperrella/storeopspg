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

import pkg.beans.rfid.PrintersTemplates;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class TemplatesList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<PrintersTemplates> templates = new ArrayList<PrintersTemplates>();
		templates.clear();
		
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
			PrintersTemplates bean = new PrintersTemplates();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String name = req.getParameter("name");
				String zpl = req.getParameter("zpl");
				String breakLabel = ""+req.getParameter("breakLabel");
				breakLabel = Utility.noNull(breakLabel);
				if(breakLabel.equals("on"))
					breakLabel = "true";
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setName(name);
	            	bean.setZpl(zpl);
	            	bean.setIdCustomer(idcus);	 
	            	bean.setBreakLabel(Boolean.parseBoolean(breakLabel));
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setId(Integer.parseInt(id));
	            	bean.setName(name);
	            	bean.setZpl(zpl);
	            	bean.setIdCustomer(idcus);
	            	bean.setBreakLabel(Boolean.parseBoolean(breakLabel));
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setId(Integer.parseInt(id));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			templates = PrintersTemplates.getTemplatesByIdCustomer(conn, idcus);
			
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
		
		obj.put("templates", templates);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/printer/templates-list", obj);
	}
	
}
