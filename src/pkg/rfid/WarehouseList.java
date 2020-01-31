package pkg.rfid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Rfid_warehouse;
import pkg.properties.ReadProperties;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class WarehouseList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_warehouse> wa = new ArrayList<Rfid_warehouse>();
		wa.clear();
		
		HttpSession session = req.getSession();
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
			// Apre la connessione
			conn = Utility.getConnRfid();
			
			Rfid_warehouse bean = new Rfid_warehouse();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idwarehouse = req.getParameter("idwarehouse");
				String description = req.getParameter("description");
				String address = req.getParameter("address");
				
				// Extra Fields
				String extra = ReadExtraFields.getExtra(req);
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setAddress(address);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.insert(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIdwarehouse(Integer.parseInt(idwarehouse));
	            	bean.setDescription(description);
	            	bean.setAddress(address);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdwarehouse(Integer.parseInt(idwarehouse));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			wa = bean.selectAll(conn,q);
			
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
		
		obj.put("wa", wa);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/anag/warehouse-list", obj);
	}
	
}
