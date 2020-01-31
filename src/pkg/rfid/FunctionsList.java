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

import pkg.beans.Rfid_device_type;
import pkg.beans.Rfid_functions;
import pkg.properties.ReadProperties;
import pkg.utility.ReadConfigFields;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class FunctionsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_functions> fun = new ArrayList<Rfid_functions>();
		fun.clear();
		
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
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			// Inizializzazione Bean
			Rfid_functions bean = new Rfid_functions();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idfunctions = req.getParameter("idfunctions");
				String description = req.getParameter("description");
				String type = req.getParameter("type");
				String name = req.getParameter("name");
				
				// Config Fields
				String parm = ReadConfigFields.getConfig(req);
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setType(Integer.parseInt(type));
	            	bean.setName(name);
	            	bean.setParameters(parm);
	            	
	            	bean.insert(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIdfunctions(Integer.parseInt(idfunctions));
	            	bean.setDescription(description);
	            	bean.setType(Integer.parseInt(type));
	            	bean.setName(name);
	            	bean.setParameters(parm);
	            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIdfunctions(Integer.parseInt(idfunctions));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			fun = bean.selectAll(conn,q);
			
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
		
		obj.put("fun", fun);
		obj.put("msg", msg);
		
		
		
		return new ModelAndView("/rfid/anag/functions-list", obj);
	}
	
}
