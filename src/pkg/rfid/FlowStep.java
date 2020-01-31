package pkg.rfid;

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

import pkg.beans.Rfid_extra;
import pkg.beans.Rfid_flow;
import pkg.beans.Rfid_location;
import pkg.beans.Rfid_step;
import pkg.beans.Rfid_warehouse;
import pkg.properties.ReadProperties;
import pkg.utility.ReadExtraFields;
import pkg.utility.ReadStepFields;
import pkg.utility.Utility;

public class FlowStep extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_step> step = new ArrayList<Rfid_step>();
		step.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		Connection conn = null;
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		String idflow = req.getParameter("idflow");
		idflow=Utility.noNull(idflow);
		String flow_description = req.getParameter("flow_description");
		flow_description=Utility.noNull(flow_description);
		
		obj.put("idflow", idflow);
		obj.put("flow_description", flow_description);
		
		// Per operazioni di CRUD
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnRfid();
			
			Rfid_step bean = new Rfid_step();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idstep = req.getParameter("idstep");
				String levelnum = req.getParameter("levelnum");
				String description = req.getParameter("description");
				String origin_location = req.getParameter("origin_location");// ReadStepFields.getWarehouses(req);
				String destination_location = req.getParameter("destination_location");// ReadStepFields.getWarehouses(req);
				
				String config_before = ReadStepFields.getConfig(req, "before_");
				String config_during = ReadStepFields.getConfig(req, "during_");
				String config_after = ReadStepFields.getConfig(req, "after_");
				
				String enabled = ""+req.getParameter("enabled");
				boolean bool_enabled;
				if(enabled.equals("t"))
					bool_enabled = true;
				else
					bool_enabled = false;
				
				// Extra Doc Top
				String extra_doc_top = ReadExtraFields.getExtra_doc_top(req);
				
				// Extra Doc Detail
				String extra_doc_detail = ReadExtraFields.getExtra_doc_detail(req);
				
				// Extra Fields
				String extra = ReadExtraFields.getExtra(req);
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setIdflow(Integer.parseInt(idflow));
	            	bean.setLevelnum(Integer.parseInt(levelnum));
	            	bean.setDescription(description);
	            	bean.setOrigin_location(origin_location);
	            	bean.setDestination_location(destination_location);
	            	bean.setConfig_before(config_before);
	            	bean.setConfig_during(config_during);
	            	bean.setConfig_after(config_after);
	            	bean.setEnabled(bool_enabled);
	            	bean.setExtra_doc_top(extra_doc_top);
	            	bean.setExtra_doc_detail(extra_doc_detail);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.insert(conn);
	            	
	            	break;
	            	
	            case "C": 
	            	
	            	bean.setIdflow(Integer.parseInt(idflow));
	            	bean.setIdstep(Integer.parseInt(idstep));
	            	bean.copy(conn);
	            	
	            	break;	            	
	                     
	            case "M": 
	            	
	            	bean.setIdflow(Integer.parseInt(idflow));
	            	bean.setIdstep(Integer.parseInt(idstep));
	            	bean.setLevelnum(Integer.parseInt(levelnum));
	            	bean.setDescription(description);
	            	bean.setOrigin_location(origin_location);
	            	bean.setDestination_location(destination_location);
	            	bean.setConfig_before(config_before);
	            	bean.setConfig_during(config_during);
	            	bean.setConfig_after(config_after);
	            	bean.setEnabled(bool_enabled);
	            	bean.setExtra_doc_top(extra_doc_top);
	            	bean.setExtra_doc_detail(extra_doc_detail);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdflow(Integer.parseInt(idflow));
	            	bean.setIdstep(Integer.parseInt(idstep));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			step = bean.selectFlowSteps(conn, Integer.parseInt(idflow));
			
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
		
		obj.put("step", step);
		obj.put("msg", msg);
		
		
		return new ModelAndView("/rfid/anag/flow-step", obj);
	}
	
}
