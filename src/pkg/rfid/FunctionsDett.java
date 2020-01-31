package pkg.rfid;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Rfid_extra;
import pkg.beans.Rfid_config;
import pkg.beans.Rfid_device_type;
import pkg.beans.Rfid_functions;
import pkg.beans.Rfid_funtype;
import pkg.utility.ReadConfigFields;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class FunctionsDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_funtype> funtype = new ArrayList<Rfid_funtype>();
		funtype.clear();
		List<Rfid_config> config = new ArrayList<Rfid_config>();
		config.clear();
		
		String fld_description = "";
		int fld_type = 0;
		String fld_name = "";
		
		Connection conn = null;
		String idfunctions = ""+req.getParameter("idfunctions");
		idfunctions=Utility.noNull(idfunctions);
		idfunctions=idfunctions.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		try {
			
			conn = Utility.getConnRfid();
			
			// Dichiara il bean
			Rfid_functions bean = new Rfid_functions();
			
			// Function type
			Rfid_funtype f = new Rfid_funtype();
			funtype = f.selectAll(conn, "");
			
			if(idfunctions.length()>0) {
				
				// Setta l'id
				bean.setIdfunctions(Integer.parseInt(idfunctions));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_description = bean.getDescription();
				fld_type = bean.getType();
				fld_name = bean.getName();
				
				// Config fields
				String fld_config = bean.getParameters();
				// Li reperisce come lista
				config = ReadConfigFields.getConfigFieldsByName(fld_config, "parameters");
				
				
			}
			
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
		
		obj.put("idfunctions", idfunctions);
		obj.put("description", fld_description);
		obj.put("type", fld_type);
		obj.put("name", fld_name);
		obj.put("config", config);
		obj.put("funtype", funtype);
		
		obj.put("tipoForm", tipoForm);
		
		return new ModelAndView("/rfid/anag/functions-dett", obj);
	}
	
}
