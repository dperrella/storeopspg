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

import pkg.beans.rfid.Client;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.Step;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DocumentsNew extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Site> sites = new ArrayList<Site>();
		sites.clear();
		
		List<Step> steps = new ArrayList<Step>();
		steps.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		String idsite = ""+req.getParameter("idsite");
		idsite = Utility.noNull(idsite);
		idsite=idsite.replaceAll("\\?cd=undefined", "");
		
		String idstep = ""+req.getParameter("idstep");
		idstep = Utility.noNull(idstep);
		idstep=idstep.replaceAll("\\?cd=undefined", "");
		
		obj.put("idsite", idsite);
		obj.put("idstep", idstep);
		
				
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String msg = "";
		
		try {
			
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			sites = Site.getSitesByIdCustomer(conn, idcus);
			steps = Step.getStepByIdCustomer(conn, idcus);
			
			obj.put("sites", sites);
			obj.put("steps", steps);
			
			
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
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/docs/documents-new", obj);
	}
	
}
