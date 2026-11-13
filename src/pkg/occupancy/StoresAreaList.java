package pkg.occupancy;

import java.sql.Connection;
import java.text.SimpleDateFormat;
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

import pkg.beans.ccn.Stores;
import pkg.occupancy.model2.firebase.Area;
import pkg.occupancy.model2.firebase.FirebaseSettings;
import pkg.utility.Utility;

public class StoresAreaList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
	
		HttpSession session = req.getSession();
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Area> aree = new ArrayList<Area>();
		aree.clear();
		
		// Per operazioni di CRUD
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String idstore = req.getParameter("idstore");
		
		System.out.println(azione);
		System.out.println(tipoForm);
		
		
		Connection connAdmin = null;
		String msg = "";
		
		try {
			connAdmin = Utility.getConnAdmin();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				System.out.println("azione");
				
				String idArea = req.getParameter("idarea");
				String description = req.getParameter("description");
				String percWarning = req.getParameter("percWarning");
				String threshold = req.getParameter("threshold");
				
				
				switch (tipoForm) {
	            case "M": 
	            	
	            	System.out.println("tipoform");
	            	Area area = new Area();
	            	area.setId(Integer.parseInt(idArea));
	            	area.setDescription(description);
	            	area.setWarningPercentage(Integer.parseInt(percWarning));
	            	area.setThreshold(Integer.parseInt(threshold));
	            	area.setIdStore(Integer.parseInt(idstore));
	            	area.update(connAdmin);
	            	
	            	msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>Operation Successful.</strong></div></div>";
					
	            	try {
						new Thread(new FirebaseSettings(Integer.parseInt(idstore))).start();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
	            	
	            	break;
	            
				}
	            
			}
			
			aree = Area.getAree(connAdmin, Integer.parseInt(idstore));
			obj.put("aree", aree);
			obj.put("msg", msg);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
			
		}
		finally {
			try {
				if(connAdmin!=null)
					connAdmin.close();
			}
			catch(Exception e) {}
		}
		
		return new ModelAndView("/occupancy/config/stores-area-list", obj);
	}
	
	

}
