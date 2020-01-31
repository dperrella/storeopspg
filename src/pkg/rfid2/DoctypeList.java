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
import pkg.beans.rfid.DocType;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DoctypeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<DocType> doctype = new ArrayList<DocType>();
		doctype.clear();
		
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
			DocType bean = new DocType();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String id = req.getParameter("id");
				String description = req.getParameter("description");
				
				switch (tipoForm) {
	            
				case "I": 
	            	
					bean.setDescription(description);
	            	bean.setIdCustomer(idcus);
	            	
	            	bean.addDoctype(conn);
	            	id = ""+bean.getIdDoctype();
	            	
	            	bean.setIdDoctype(Integer.parseInt(id));
	            	bean.deleteRelCusrea(conn);
	            	
	            	try {
		            	String[] cusrea=req.getParameterValues("cusrea");
		                System.out.println("Selected Customer Reason...(rel_doctype_customer_reason)");    
		                for(int i=0;i<cusrea.length;i++)
		                {
		                   System.out.println("<li>"+cusrea[i]+"</li>");
		                   
		                   bean.setIdDoctype(Integer.parseInt(id));
		                   bean.insertRelCusrea(conn, cusrea[i]);
		                   
		                }
	            	}
	            	catch(Exception e) {}
	            	
	                // cancella vecchie relazioni
	                bean.setIdDoctype(Integer.parseInt(id));
	            	bean.deleteRelRea(conn);
	            	
	            	try {
		                String[] rea=req.getParameterValues("rea");
		                System.out.println("Selected Reason...(rel_doctype_reason)");    
		                for(int i=0;i<rea.length;i++)
		                {
		                   System.out.println("<li>"+rea[i]+"</li>");
		                   
		                   bean.setIdDoctype(Integer.parseInt(id));
		                   bean.insertRelRea(conn, Integer.parseInt(rea[i]));
		                   
		                }
	            	}
	            	catch(Exception e) {}
	            	
	            	break;
	            	
	            case "M": 
	            	
	            	bean.setIdDoctype(Integer.parseInt(id));
	            	bean.setDescription(description);
	            	bean.setIdCustomer(idcus);
	            	
	            	bean.update(conn);
	            	
	            	// cancella vecchie relazioni
	            	bean.setIdDoctype(Integer.parseInt(id));
	            	bean.deleteRelCusrea(conn);
	            	
	            	try {
		            	String[] cusrea=req.getParameterValues("cusrea");
		                System.out.println("Selected Customer Reason...(rel_doctype_customer_reason)");    
		                for(int i=0;i<cusrea.length;i++)
		                {
		                   System.out.println("<li>"+cusrea[i]+"</li>");
		                   
		                   bean.setIdDoctype(Integer.parseInt(id));
		                   bean.insertRelCusrea(conn, cusrea[i]);
		                   
		                }
	            	}
	            	catch(Exception e) {}
	            	
	                // cancella vecchie relazioni
	                bean.setIdDoctype(Integer.parseInt(id));
	            	bean.deleteRelRea(conn);
	            	
	            	try {
		            	String[] rea=req.getParameterValues("rea");
		                System.out.println("Selected Reason...(rel_doctype_reason)");    
		                for(int i=0;i<rea.length;i++)
		                {
		                   System.out.println("<li>"+rea[i]+"</li>");
		                   
		                   bean.setIdDoctype(Integer.parseInt(id));
		                   bean.insertRelRea(conn, Integer.parseInt(rea[i]));
		                   
		                }
	            	}
	            	catch(Exception e) {}
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdDoctype(Integer.parseInt(id));
	            	bean.deleteRelCusrea(conn);
	            	bean.deleteRelRea(conn);
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			doctype = DocType.getDoctypeByIdCustomer(conn, idcus);
			
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
		
		obj.put("doctype", doctype);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/config/doctype-list", obj);
	}
	
}
