package pkg.rfid;

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

import pkg.beans.Rfid_devices;
import pkg.properties.ReadProperties;
import pkg.utility.ReadConfigFields;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class DevicesList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_devices> dev = new ArrayList<Rfid_devices>();
		dev.clear();
		
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
			Rfid_devices bean = new Rfid_devices();
			
			// In caso di azione...
			if(azione.length()>0) {
				
				String iddevice = req.getParameter("iddevice");
				String name = req.getParameter("name");
				String description = req.getParameter("description");
				String ip = req.getParameter("ip");
				String port = req.getParameter("port");
				String mac = req.getParameter("mac");
				String devicetype = req.getParameter("devicetype");
				
				// Config Fields
				String config_device = ReadConfigFields.getConfig(req);
				
				String enabled = ""+req.getParameter("enabled");
				enabled = Utility.noNull(enabled);
				String grouped = ""+req.getParameter("grouped");
				grouped = Utility.noNull(grouped);
				
				boolean bool_enabled;
				if(enabled.equals("t"))
					bool_enabled = true;
				else
					bool_enabled = false;
				
				System.out.println(enabled);
				System.out.println(bool_enabled);
				
				
				boolean bool_grouped;
				if(grouped.equals("t"))
					bool_grouped = true;
				else
					bool_grouped = false;
				
				String program_name = req.getParameter("program_name");
				
				// Extra Fields
				String extra = ReadExtraFields.getExtra(req);
				
				switch (tipoForm) {
	            case "I": 
	            	
	            	bean.setName(name);
	            	bean.setDescription(description);
	            	bean.setIp(ip);
	            	bean.setPort(Integer.parseInt(port));
	            	bean.setMac(mac);
	            	bean.setDevicetype(Integer.parseInt(devicetype));
	            	bean.setConfig_device(config_device);
		            bean.setEnabled(bool_enabled);
		            bean.setGrouped(bool_grouped);
		            bean.setProgram_name(program_name);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.insert(conn);
	            	
	            	break;
	                     
	            case "M": 
	            	
	            	bean.setIddevice(Integer.parseInt(iddevice));
	            	
	            	bean.setName(name);
	            	bean.setDescription(description);
	            	bean.setIp(ip);
	            	bean.setPort(Integer.parseInt(port));
	            	bean.setMac(mac);
	            	bean.setDevicetype(Integer.parseInt(devicetype));
	            	bean.setConfig_device(config_device);
		            bean.setEnabled(bool_enabled);
		            bean.setGrouped(bool_grouped);
		            bean.setProgram_name(program_name);
	            	bean.setExtra(extra);
	            	bean.setExtra_language("");
	            	
	            	bean.update(conn);
	            	
	            	break;
	            	
	            case "E": 
	            	
	            	bean.setIddevice(Integer.parseInt(iddevice));
	            	bean.delete(conn);
	            	
	            	break;
	            	
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div></div>";
				
			}
			
			// Selezione record (tutti)
			dev = bean.selectAll(conn,q);
			
			
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
		
		obj.put("dev", dev);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/anag/devices-list", obj);
	}
	
}
