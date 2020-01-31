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

import pkg.beans.rfid.DocType;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.StepType;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StepTypeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<StepType> steptype = new ArrayList<StepType>();
		steptype.clear();
		
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
			StepType bean = new StepType();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				String id_doctype = req.getParameter("id_doctype");
				String id_doctype2 = req.getParameter("id_doctype2");
				String power = req.getParameter("power");
				String inventory = req.getParameter("inventory");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setIdDoctypeDefault(Integer.parseInt(id_doctype));
	            	bean.setIdDoctypeDefault2(Integer.parseInt(id_doctype2));
	            	bean.setIdCustomer(idcus);	
	            	bean.setPower(Integer.parseInt(power));
	            	bean.setInventory(Boolean.parseBoolean(inventory));            	
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIdStepType(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	bean.setIdDoctypeDefault(Integer.parseInt(id_doctype));
	            	bean.setIdDoctypeDefault2(Integer.parseInt(id_doctype2));
	            	bean.setIdCustomer(idcus);
	            	bean.setPower(Integer.parseInt(power));
	            	bean.setInventory(Boolean.parseBoolean(inventory));            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdStepType(Integer.parseInt(id));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			steptype = StepType.getStepType(conn, idcus);
			
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
		
		obj.put("steptype", steptype);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/config/step-type-list", obj);
	}
	
}
