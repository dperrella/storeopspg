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

import pkg.beans.rfid.Flow;
import pkg.beans.rfid.Step;
import pkg.beans.rfid.Interface;
import pkg.beans.rfid.StepType;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class FlowList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Flow> flows = new ArrayList<Flow>();
		flows.clear();
		
		List<Interface> in = new ArrayList<Interface>();
		in.clear();
		
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
			Flow bean = new Flow();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setIdCustomer(idcus);	            	
	            	int idflow = bean.insertFlow(conn);
	            	
	            	
	            	String[] nrrig=req.getParameterValues("nrrig");
	                System.out.println("Selected Steps...");    
	                for(int i=0;i<nrrig.length;i++)
	                {
	                	System.out.println("------");
		                
	                   System.out.println(nrrig[i]);
	                   System.out.println(req.getParameterValues("nrrig")[i]);
	                   System.out.println(req.getParameterValues("idstep")[i]);
	                   System.out.println(req.getParameterValues("steptype")[i]);
	                   System.out.println(req.getParameterValues("interface")[i]);
	                   System.out.println(req.getParameterValues("stepdescription")[i]);
	                   String enabled="";
	                   try {
	                	   //enabled = req.getParameterValues("enabled")[i];
	                	   System.out.println(nrrig[i]+"enabled");
	                	   enabled = ""+req.getParameter(nrrig[i]+"enabled");
	                	   enabled = Utility.noNull(enabled);
	                   }
	                   catch(Exception e) {}
	                   if(enabled.equals("on"))
	                	   enabled = "true";
	                   else
	                	   enabled = "false";
	                   System.out.println(enabled);
	                   System.out.println(req.getParameterValues("color")[i]);
	                   
	                   Step s = new Step();
	                   s.setDescription(req.getParameterValues("stepdescription")[i]);
	                   s.setCanCreateDoc(Boolean.parseBoolean(enabled));
	                   s.setIdFlow(idflow);
	                   s.setIdInterface(Integer.parseInt(req.getParameterValues("interface")[i]));
	                   s.setIdStepType(Integer.parseInt(req.getParameterValues("steptype")[i]));
	                   s.setColor(req.getParameterValues("color")[i]);
	                   s.add(conn);
	                }
	                
	            	break;
	                     
	            case "M": 
	            	
	            	
	            	bean.setIdflow(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	bean.setIdCustomer(idcus);
	            	bean.update(conn);
	            	
	            	nrrig=req.getParameterValues("nrrig");
	                System.out.println("Selected Steps...");    
	                for(int i=0;i<nrrig.length;i++)
	                {
	                	System.out.println("------");
		                   
	                   System.out.println(nrrig[i]);
	                   System.out.println(req.getParameterValues("nrrig")[i]);
	                   System.out.println(req.getParameterValues("idstep")[i]);
	                   System.out.println(req.getParameterValues("steptype")[i]);
	                   System.out.println(req.getParameterValues("interface")[i]);
	                   System.out.println(req.getParameterValues("stepdescription")[i]);
	                   String enabled="";
	                   try {
	                	   //enabled = req.getParameterValues("enabled")[i];
	                	   System.out.println(nrrig[i]+"enabled");
	                	   enabled = ""+req.getParameter(nrrig[i]+"enabled");
	                	   enabled = Utility.noNull(enabled);
	                   }
	                   catch(Exception e) {}
	                   if(enabled.equals("on"))
	                	   enabled = "true";
	                   else
	                	   enabled = "false";
	                   System.out.println(enabled);
	                   System.out.println(req.getParameterValues("color")[i]);
	                   
	                   Step s = new Step();
	                   
	                   if(req.getParameterValues("idstep")[i].trim().length()>0) {
	                	   //update
	                	   s.setIdStep(Integer.parseInt(req.getParameterValues("idstep")[i]));
	                	   s.setDescription(req.getParameterValues("stepdescription")[i]);
		                   s.setCanCreateDoc(Boolean.parseBoolean(enabled));
		                   s.setIdFlow(Integer.parseInt(id));
		                   s.setIdInterface(Integer.parseInt(req.getParameterValues("interface")[i]));
		                   s.setIdStepType(Integer.parseInt(req.getParameterValues("steptype")[i]));
		                   s.setColor(req.getParameterValues("color")[i]);
		                   s.update(conn);
	                   }
	                   else {
	                	   //insert
	                	   s.setDescription(req.getParameterValues("stepdescription")[i]);
		                   s.setCanCreateDoc(Boolean.parseBoolean(enabled));
		                   s.setIdFlow(Integer.parseInt(id));
		                   s.setIdInterface(Integer.parseInt(req.getParameterValues("interface")[i]));
		                   s.setIdStepType(Integer.parseInt(req.getParameterValues("steptype")[i]));
		                   s.setColor(req.getParameterValues("color")[i]);
		                   s.add(conn);
	                   }
	                   
	                }
	                
	            	break;
	            	
	            case "E": 
	            	
	            	Step s = new Step();
	                s.setIdFlow(Integer.parseInt(id));
	                s.deleteByFlow(conn);
	            	
	            	bean.setIdflow(Integer.parseInt(id));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			flows = Flow.getFlowByIdCustomer(conn, idcus);
			
			in = Interface.getAllInterfaces(conn);
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
		
		obj.put("flows", flows);
		
		obj.put("in", in);
		obj.put("steptype", steptype);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/flow-list", obj);
	}
	
}
