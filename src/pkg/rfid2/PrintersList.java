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

import pkg.beans.rfid.Printers;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PrintersList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Printers> printers = new ArrayList<Printers>();
		printers.clear();
		
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
			Printers bean = new Printers();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				String ip = req.getParameter("ip");
				String port = req.getParameter("port");
				String enabled = ""+req.getParameter("enabled");
				enabled = Utility.noNull(enabled);
				if(enabled.equals("on"))
					enabled = "true";
				
				System.out.println(enabled);
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setIp(ip);
	            	bean.setPort(Integer.parseInt(port));
	            	bean.setEnabled(Boolean.parseBoolean(enabled));
	            	bean.setIdCustomer(idcus);	            	
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setId(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	bean.setIp(ip);
	            	bean.setPort(Integer.parseInt(port));
	            	bean.setEnabled(Boolean.parseBoolean(enabled));
	            	bean.setIdCustomer(idcus);
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
			printers = Printers.getPrintersByIdCustomer(conn, idcus);
			
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
		
		obj.put("printers", printers);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/printer/printers-list", obj);
	}
	
}
