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
import pkg.utility.ReadConfigFields;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class DeviceTypeDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		extra.clear();
		List<Rfid_config> config = new ArrayList<Rfid_config>();
		config.clear();
		
		String fld_description = "";
		
		Connection conn = null;
		String iddevicetype = ""+req.getParameter("iddevicetype");
		iddevicetype=Utility.noNull(iddevicetype);
		iddevicetype=iddevicetype.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		try {
			
			conn = Utility.getConnRfid();
			
			// Dichiara il bean
			Rfid_device_type bean = new Rfid_device_type();
			
			if(iddevicetype.length()>0) {
				
				// Setta l'id
				bean.setIddevicetype(Integer.parseInt(iddevicetype));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_description = bean.getDescription();
				
				// Config fields
				String fld_config_devicetype = bean.getConfig_devicetype();
				// Li reperisce come lista
				config = ReadConfigFields.getConfigFields(fld_config_devicetype);
				
				// Extra fields
				String fld_extra = bean.getExtra();
				// Li reperisce come lista
				extra = ReadExtraFields.getExtraFields(fld_extra);
				
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
		
		obj.put("iddevicetype", iddevicetype);
		obj.put("description", fld_description);
		obj.put("config", config);
		obj.put("extra", extra);
		
		obj.put("tipoForm", tipoForm);
		
		return new ModelAndView("/rfid/anag/device_type-dett", obj);
	}
	
}
