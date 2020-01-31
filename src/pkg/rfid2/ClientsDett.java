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

public class ClientsDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Site> sites = new ArrayList<Site>();
		sites.clear();
		
		List<Step> steps = new ArrayList<Step>();
		steps.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String msg = "";
		
		try {
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";
			}
			// Apertura connessione
			conn = Utility.getConnRfid();
						
			if(!tipoForm.equals("I")) {
				Client client = new Client();
				client.setIdClient(Integer.parseInt(id));
				client.selectRow(conn);
				obj.put("id", id);
				obj.put("description", client.getDescription());
				obj.put("mac", client.getMacAddress());
				obj.put("enabled", client.getIsEnabled());
				
				obj.put("idSite", Client.selectSiteByClient(conn, Integer.parseInt(id)));
				obj.put("idStep", Client.selectStepByClient(conn, Integer.parseInt(id)));
			}
			
			sites = Site.getSitesByIdCustomer(conn, idcus);
			steps = Step.getStepByIdCustomer(conn, idcus);
			
			obj.put("tipoForm", tipoForm);
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
		
		return new ModelAndView("/rfid2/anag/clients-dett", obj);
	}
	
}
