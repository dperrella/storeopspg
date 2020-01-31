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
import pkg.beans.rfid.Step;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReaderConfigDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Site> sites = new ArrayList<Site>();
		sites.clear();
		
		List<Step> steps = new ArrayList<Step>();
		steps.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		String idreader = req.getParameter("idreader");
		idreader = Utility.noNull(idreader);
		idreader=idreader.replaceAll("\\?cd=undefined", "");
		String idsite = req.getParameter("idsite");
		idsite = Utility.noNull(idsite);
		idsite=idsite.replaceAll("\\?cd=undefined", "");
		String idstep = req.getParameter("idstep");
		idstep = Utility.noNull(idstep);
		idstep=idstep.replaceAll("\\?cd=undefined", "");
		
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		Connection conn = null;
		String msg = "";
		
		try {
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";
			}
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			if(!tipoForm.equals("I")) {
				ReaderConfig config = new ReaderConfig();
				config.setIdreader(Integer.parseInt(idreader));
				config.setIdsite(Integer.parseInt(idsite));
				config.setIdstep(Integer.parseInt(idstep));
				
				config.selectRow(conn);
				
				obj.put("dsreader", config.getDsreader());
				obj.put("dssite", config.getDssite());
				obj.put("dsstep", config.getDsstep());
						
			}
			
			sites = Site.getSitesByIdCustomer(conn, idcus);
			steps = Step.getStepByIdCustomer(conn, idcus);
			
			obj.put("tipoForm", tipoForm);
			obj.put("sites", sites);
			obj.put("steps", steps);
			
			obj.put("idreader", idreader);
			obj.put("idsite", idsite);
			obj.put("idstep", idstep);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/readers-config-dett", obj);
	}
	
}
