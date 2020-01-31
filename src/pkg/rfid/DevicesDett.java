package pkg.rfid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Rfid_devices;
import pkg.beans.Rfid_device_type;
import pkg.beans.Rfid_config;
import pkg.beans.Rfid_extra;
import pkg.utility.ReadConfigFields;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class DevicesDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		extra.clear();
		List<Rfid_config> config = new ArrayList<Rfid_config>();
		config.clear();
		List<Rfid_device_type> devtype = new ArrayList<Rfid_device_type>();
		devtype.clear();
		
		String fld_name = "";
		String fld_description = "";
		String fld_ip = "";
		int fld_port = 0;
		String fld_mac = "";
		int fld_devicetype = 0;
		boolean fld_enabled = false;
		boolean fld_grouped = false;
		String fld_program_name = "";
		
		String check_enabled = "";
		String check_grouped = "";
		
		Connection conn = null;
		String iddevice = ""+req.getParameter("iddevice");
		iddevice=Utility.noNull(iddevice);
		iddevice=iddevice.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		try {
			
			conn = Utility.getConnRfid();
			
			Rfid_devices bean = new Rfid_devices();
			Rfid_device_type dt = new Rfid_device_type();
			devtype = dt.selectAll(conn, "");
			
			// Visualizzazione/modifica/cancellazione
			if(iddevice.length()>0) {
				
				// Setta l'id
				bean.setIddevice(Integer.parseInt(iddevice));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_name = bean.getName();
				fld_description = bean.getDescription();
				fld_ip = bean.getIp();
				fld_port = bean.getPort();
				fld_mac = bean.getMac();
				fld_devicetype = bean.getDevicetype();
				// Config fields
				String fld_config_device = bean.getConfig_device();
				// Li reperisce come lista
				config = ReadConfigFields.getConfigFields(fld_config_device);
				fld_enabled = bean.getEnabled();
				if(fld_enabled==true)
					check_enabled = "checked";
				fld_grouped = bean.getGrouped();
				if(fld_grouped==true)
					check_grouped = "checked";
				
				
				System.out.println("---");
				
				System.out.println(fld_enabled);
				System.out.println(check_enabled);
				System.out.println("---");
				
				fld_program_name = bean.getProgram_name();
				
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
		
		obj.put("iddevice", iddevice);
		obj.put("name", fld_name);
		obj.put("description", fld_description);
		obj.put("ip", fld_ip);
		obj.put("port", fld_port);
		obj.put("mac", fld_mac);
		obj.put("devicetype", fld_devicetype);
		obj.put("config", config);
		obj.put("enabled", fld_enabled);
		obj.put("grouped", fld_grouped);
		obj.put("program_name", fld_program_name);
		obj.put("extra", extra);
		obj.put("check_enabled", check_enabled);
		obj.put("check_grouped", check_grouped);
		
		obj.put("devtype", devtype);
		obj.put("tipoForm", tipoForm);
		
		
		return new ModelAndView("/rfid/anag/devices-dett", obj);
	}
	
}
