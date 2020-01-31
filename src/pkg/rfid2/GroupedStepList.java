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

public class GroupedStepList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Step> config = new ArrayList<Step>();
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
		String idflow = req.getParameter("idflow");
		obj.put("idflow", idflow);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			// Inizializzazione Bean
			Step s = new Step();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String description = req.getParameter("description");
				String color = req.getParameter("color");
				String valtype = req.getParameter("valtype");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	System.out.println("COLOR: "+color);
	            	s.setDescription(description);
                    s.setCanCreateDoc(false);
                    s.setIdFlow(Integer.parseInt(idflow));
                    s.setColor(color);
                    s.setType("G");
                    s.setValtype(valtype);
                    s.addGrouped(conn);;	  
	            	
                    int grouped_idstep = s.getIdStep();
                    
                    
                    
	            	try {
		            	String[] steps=req.getParameterValues("steps");
		                System.out.println("Selected steps...");    
		                for(int i=0;i<steps.length;i++)
		                {
		                   System.out.println("<li>"+steps[i]+"</li>");
		                   
		                   s.addGroupedStep(conn, grouped_idstep, Integer.parseInt(steps[i]));
		                   
		                }
	            	}
	            	catch(Exception e) {
	            		
	            	}
	            	
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	grouped_idstep = Integer.parseInt(req.getParameter("id"));
	            	
	            	s.deleteGroupedStep(conn, grouped_idstep);
	            	
	            	s.setIdStep(grouped_idstep);
	            	s.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			config = Step.getGroupedStepByIdFlow(conn, Integer.parseInt(idflow));
			
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
		
		return new ModelAndView("/rfid2/anag/grouped-step-list", obj);
	}
	
}
