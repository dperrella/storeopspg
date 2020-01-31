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

import pkg.beans.rfid.ReaderConfig;
import pkg.beans.rfid.Site;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReaderConfigList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<ReaderConfig> config = new ArrayList<ReaderConfig>();
		config.clear();
		
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
		String idreader = req.getParameter("id");
		
		obj.put("idreader", idreader);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			// Inizializzazione Bean
			ReaderConfig bean = new ReaderConfig();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idsite = req.getParameter("idsite");
				String idstep = req.getParameter("idstep");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setIdreader(Integer.parseInt(idreader));
	            	bean.setIdsite(Integer.parseInt(idsite));
	            	bean.setIdstep(Integer.parseInt(idstep));;	            	
	            	bean.add(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdreader(Integer.parseInt(idreader));
	            	bean.setIdsite(Integer.parseInt(idsite));
	            	bean.setIdstep(Integer.parseInt(idstep));
	            	
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			config = ReaderConfig.getConfigByIdreader(conn, Integer.parseInt(idreader));
			
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
		
		obj.put("config", config);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/readers-config", obj);
	}
	
}
