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

import pkg.beans.rfid.DocDest;
import pkg.beans.rfid.Site;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DocDestList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<DocDest> dd = new ArrayList<DocDest>();
		dd.clear();
		
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
			DocDest bean = new DocDest();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idstep = req.getParameter("idstep");
				String origin = req.getParameter("origin");
				String destination = req.getParameter("destination");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setIdStep(Integer.parseInt(idstep));
	            	bean.setOrigin(origin);
	            	bean.setDestination(destination);
	            	bean.setIdCustomer(idcus);	            	
	            	bean.add(conn);
	            	
	            	break;
	            
	            case "E": 
	            	
	            	bean.setIdStep(Integer.parseInt(idstep));
	            	bean.setOrigin(origin);
	            	bean.setDestination(destination);
	            	bean.setIdCustomer(idcus);	            	
	            	
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			dd = DocDest.getDestByIdCustomer(conn, idcus);
			
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
		
		obj.put("dd", dd);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/docs/docdest-list", obj);
	}
	
}
