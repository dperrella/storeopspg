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
import pkg.beans.rfid.DocDest;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.Step;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DocDestDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Step> steps = new ArrayList<Step>();
		steps.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		String id_customer = ""+req.getParameter("id_customer");
		id_customer = Utility.noNull(id_customer);
		id_customer=id_customer.replaceAll("\\?cd=undefined", "");
		String idstep = ""+req.getParameter("idstep");
		idstep = Utility.noNull(idstep);
		idstep=idstep.replaceAll("\\?cd=undefined", "");
		String origin = ""+req.getParameter("origin");
		origin = Utility.noNull(origin);
		origin=origin.replaceAll("\\?cd=undefined", "");
		String destination = ""+req.getParameter("destination");
		destination = Utility.noNull(destination);
		destination=destination.replaceAll("\\?cd=undefined", "");
		
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
				obj.put("id_customer", id_customer);
				obj.put("idstep", idstep);
				obj.put("origin", origin);
				obj.put("destination", destination);
			}
			obj.put("tipoForm", tipoForm);
			
			steps = Step.getStepByIdCustomer(conn, idcus);
			obj.put("steps", steps);
			
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
		
		return new ModelAndView("/rfid2/docs/docdest-dett", obj);
	}
	
}
