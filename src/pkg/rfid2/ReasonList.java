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
import pkg.beans.rfid.Reason;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.StepType;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReasonList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Reason> reason = new ArrayList<Reason>();
		reason.clear();
		
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
			Reason bean = new Reason();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				String id_loctype_in = req.getParameter("id_loctype_in");
				String id_loctype_out = req.getParameter("id_loctype_out");
				String sign = req.getParameter("sign");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	if(id_loctype_in.length()==0)
	            		bean.setIdLoctypeIn(-1);
	            	else
	            		bean.setIdLoctypeIn(Integer.parseInt(id_loctype_in));
	            	
	            	if(id_loctype_out.length()==0)
	            		bean.setIdLoctypeOut(-1);
	            	else
	            		bean.setIdLoctypeOut(Integer.parseInt(id_loctype_out));
	            	
	            	bean.setIdCustomer(idcus);	  
	            	bean.setSign(sign);
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setId(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	if(id_loctype_in.length()==0)
	            		bean.setIdLoctypeIn(-1);
	            	else
	            		bean.setIdLoctypeIn(Integer.parseInt(id_loctype_in));
	            	
	            	if(id_loctype_out.length()==0)
	            		bean.setIdLoctypeOut(-1);
	            	else
	            		bean.setIdLoctypeOut(Integer.parseInt(id_loctype_out));
	            	bean.setIdCustomer(idcus);
	            	bean.setSign(sign);
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
			reason = Reason.getReasonByIdCustomer(conn, idcus);
			
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
		
		obj.put("reason", reason);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/config/reason-list", obj);
	}
	
}
