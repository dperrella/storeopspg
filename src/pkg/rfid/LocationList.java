package pkg.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Rfid_location;
import pkg.beans.Rfid_warehouse;
import pkg.properties.ReadProperties;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class LocationList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_location> loc = new ArrayList<Rfid_location>();
		loc.clear();
		
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
			
			Rfid_location bean = new Rfid_location();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idlocation = req.getParameter("idlocation");
				String id_warehouse = req.getParameter("id_warehouse");
				String id_loctype = req.getParameter("id_loctype");
				String description = req.getParameter("description");
				
				// Extra Fields
				String extra = ReadExtraFields.getExtra(req);
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setId_warehouse(Integer.parseInt(id_warehouse));
	            	bean.setId_loctype(Integer.parseInt(id_loctype));
	            	bean.setDescription(description);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.insert(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIdlocation(Integer.parseInt(idlocation));
	            	bean.setId_warehouse(Integer.parseInt(id_warehouse));
	            	bean.setId_loctype(Integer.parseInt(id_loctype));
	            	bean.setDescription(description);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdlocation(Integer.parseInt(idlocation));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			loc = bean.selectAll(conn,q);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("loc", loc);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/anag/location-list", obj);
	}
	
}
