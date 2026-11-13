package pkg.rfid2;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ClientsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Client> clients = new ArrayList<Client>();
		clients.clear();
		
		DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String view = ""+req.getParameter("view");
		view=Utility.noNull(view);
		
		String showContract = ""+req.getParameter("showContract");
		showContract=Utility.noNull(showContract);
		
		Boolean contract = showContract.length()==0?false:true;
		
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
			Client bean = new Client();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				String enabled = req.getParameter("enabled");
				enabled = Utility.noNull(enabled);
				if(enabled.equals("on"))
					enabled = "true";
				
				String _contract = req.getParameter("contract");
				_contract = Utility.noNull(_contract);
				if(_contract.equals("on"))
					_contract = "true";
				
				String _contractDate = ""+req.getParameter("contractDate");
				_contractDate = Utility.noNull(_contractDate);
				switch (tipoForm) {
	                     
	            case "M": 
	            	
	            	bean.setIdClient(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	bean.setIsEnabled(Boolean.parseBoolean(enabled));
	            	bean.setIdCustomer(idcus);
	            	bean.setContract(Boolean.parseBoolean(_contract));
	            	
	            	if(_contractDate.length()>0) {
	            		bean.setContractDate(iso.parse(_contractDate));
	            	}
	            	bean.update(conn);
	            	
	            	// cancella vecchie relazioni
	            	bean.setIdClient(Integer.parseInt(id));
	            	bean.deleteRelSite(conn);
	            	
	            	try {
		            	String[] sites=req.getParameterValues("sites");
		                System.out.println("Selected Sites...(rel_client_site)");    
		                for(int i=0;i<sites.length;i++)
		                {
		                   System.out.println("<li>"+sites[i]+"</li>");
		                   
		                   bean.setIdClient(Integer.parseInt(id));
		                   bean.insertRelSite(conn, Integer.parseInt(sites[i]));
		                   
		                }
	            	}
	            	catch(Exception e) {
	            		
	            	}
		                
	                
	                // cancella vecchie relazioni
	            	bean.setIdClient(Integer.parseInt(id));
	            	bean.deleteRelStep(conn);
	            	
	                try {
		                String[] steps=req.getParameterValues("steps");
		                System.out.println("Selected Steps...(rel_client_step)");    
		                for(int i=0;i<steps.length;i++)
		                {
		                   System.out.println("<li>"+steps[i]+"</li>");
		                   
		                   bean.setIdClient(Integer.parseInt(id));
		                   bean.insertRelStep(conn, Integer.parseInt(steps[i]));
		                   
		                }
	                }
	                catch(Exception e) {}
	                
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdClient(Integer.parseInt(id));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				case "C": 
	            	
	            	bean.setIdClient(Integer.parseInt(id));
	            	bean.setIdCustomer(idcus);
	            	bean.setAsCustomerClient(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			if(view.equalsIgnoreCase("all")) {
				clients = Client.getClientsByIdCustomerAndNull(conn, idcus);
			}
			else {
				clients = Client.getClientsByIdCustomer(conn, idcus, contract);
			}
			
			obj.put("contract", contract);
			
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
		
		obj.put("clients", clients);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/clients-list", obj);
	}
	
}
