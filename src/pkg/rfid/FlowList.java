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

import pkg.beans.Rfid_flow;
import pkg.beans.Rfid_step;
import pkg.beans.Rfid_warehouse;
import pkg.properties.ReadProperties;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class FlowList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_flow> flow = new ArrayList<Rfid_flow>();
		flow.clear();
		
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
			
			Rfid_flow bean = new Rfid_flow();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String idflow = req.getParameter("idflow");
				String description = req.getParameter("description");
				String enabled = ""+req.getParameter("enabled");
				boolean bool_enabled;
				if(enabled.equals("t"))
					bool_enabled = true;
				else
					bool_enabled = false;
				
				// Extra Fields
				String extra = ReadExtraFields.getExtra(req);
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setDescription(description);
	            	bean.setEnabled(bool_enabled);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.insert(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIdflow(Integer.parseInt(idflow));
	            	bean.setDescription(description);
	            	bean.setEnabled(bool_enabled);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	// Delete Steps
	            	Rfid_step step = new Rfid_step();
	            	step.setIdflow(Integer.parseInt(idflow));
	            	step.deleteAll(conn);
	            	
	            	bean.setIdflow(Integer.parseInt(idflow));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			flow = bean.selectAll(conn,q);
			
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
		
		obj.put("flow", flow);
		obj.put("msg", msg);
		
		
		
		return new ModelAndView("/rfid/anag/flow-list", obj);
	}
	
}
