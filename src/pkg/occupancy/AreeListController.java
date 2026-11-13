package pkg.occupancy;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.occupancy.model2.Aree;
import pkg.utility.Utility;

public class AreeListController extends AbstractController {
	@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        HttpSession session = req.getSession();
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
        
        List<Aree> aree = new ArrayList<Aree>();
		aree.clear();
		
		Connection conn = null;
		
		// Per operazioni di CRUD
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnData();
			
			// Inizializzazione Bean
			Aree bean = new Aree();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				String percWarning = req.getParameter("percWarning");
				String normalColor = req.getParameter("normalColor");
				String warningColor = req.getParameter("warningColor");
				String overthresholdColor = req.getParameter("overthresholdColor");
				String threshold = req.getParameter("threshold");
				//String iprelay = req.getParameter("iprelay");
				String iprelay = "";
				
				try {
					String[] values=req.getParameterValues("iprelay");
					for(int i=0; i<values.length; i++)
					{
						iprelay+=values[i]+";";
					}
					if(iprelay.length()>0)
						iprelay=iprelay.substring(0,iprelay.length()-1);
				}
				catch(Exception e) {}
				
				String chkUseRelay = ""+req.getParameter("use_relay");
				chkUseRelay = Utility.noNull(chkUseRelay);
				
				boolean useRelay = false;
				if(chkUseRelay.length()==0)
					useRelay = false;
				else
					useRelay = true;
				
				String relayCmd = req.getParameter("relay_cmd");
				String relayType = req.getParameter("relay_type");
				
				
				String chkSubArea = ""+req.getParameter("sub_area");
				chkSubArea = Utility.noNull(chkSubArea);
				
				boolean subArea = false;
				if(chkSubArea.length()==0)
					subArea = false;
				else
					subArea = true;
				
				String chkZone = ""+req.getParameter("zone");
				chkZone = Utility.noNull(chkZone);
				
				
				
				
				boolean zone = false;
				if(chkZone.length()==0)
					zone = false;
				else
					zone = true;
				
				
//				String chkMl = ""+req.getParameter("ml");
//				chkMl = Utility.noNull(chkMl);
//				boolean ml = false;
//				if(chkMl.length()==0)
//					ml = false;
//				else
//					ml = true;
//				
//				String chkSm = ""+req.getParameter("sm");
//				chkSm = Utility.noNull(chkSm);
//				boolean sm = false;
//				if(chkSm.length()==0)
//					sm = false;
//				else
//					sm = true;
				
				boolean ml = false;
				boolean sm = false;
				
				System.out.println("chkZone: "+chkZone);
				System.out.println("zone   : "+zone);
				
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setPercWarning(Integer.parseInt(percWarning));
	            	bean.setNormalColor(normalColor);
	            	bean.setWarningColor(warningColor);
	            	bean.setOverthresholdColor(overthresholdColor);
	            	bean.setThreshold(Integer.parseInt(threshold));
	            	bean.setIprelay(iprelay);
	            	bean.setUseRelay(useRelay);
	            	bean.setRelayCmd(relayCmd);
	            	bean.setRelayType(relayType);
	            	bean.setSubArea(subArea);
	            	bean.setZone(zone);
	            	bean.setAreaMl(ml);
	            	bean.setAreaSm(sm);
	            	bean.setIdCustomer(Integer.parseInt(idcus));
	            	bean.add(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setId(Integer.parseInt(id));
	            	bean.selectRow(conn);
	            	bean.setDescription(description);
	            	bean.setPercWarning(Integer.parseInt(percWarning));
	            	bean.setNormalColor(normalColor);
	            	bean.setWarningColor(warningColor);
	            	bean.setOverthresholdColor(overthresholdColor);
	            	bean.setThreshold(Integer.parseInt(threshold));
	            	bean.setIprelay(iprelay);
	            	bean.setUseRelay(useRelay);
	            	bean.setRelayCmd(relayCmd);
	            	bean.setRelayType(relayType);
	            	bean.setSubArea(subArea);
	            	bean.setZone(zone);
	            	bean.setAreaMl(ml);
	            	bean.setAreaSm(sm);
	            	bean.setIdCustomer(Integer.parseInt(idcus));
	            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	// check if area has data
	            	// ...
	            	
	            	bean.setId(Integer.parseInt(id));
	            	bean.setIdCustomer(Integer.parseInt(idcus));
	            	
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
	            
			}
			
			// Selezione record (tutti)
			aree = Aree.getAree(conn, Integer.parseInt(idcus));
			
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
		
		obj.put("aree", aree);
		obj.put("areeSize", aree.size());
		obj.put("msg", msg);
		
		return new ModelAndView("/occupancy/anag/aree-list", obj);
	}
}
