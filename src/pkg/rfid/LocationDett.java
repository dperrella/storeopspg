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
import pkg.beans.Rfid_location;
import pkg.beans.Rfid_loctype;
import pkg.beans.Rfid_warehouse;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class LocationDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		List<Rfid_warehouse> war = new ArrayList<Rfid_warehouse>();
		List<Rfid_loctype> loctype = new ArrayList<Rfid_loctype>();
		
		extra.clear();
		war.clear();
		loctype.clear();
		
		int fld_id_warehouse = 0;
		int fld_id_loctype = 0;
		String fld_description = "";
		
		Connection conn = null;
		String idlocation = ""+req.getParameter("idlocation");
		idlocation=Utility.noNull(idlocation);
		idlocation=idlocation.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		try {
			
			conn = Utility.getConnRfid();
			
			Rfid_location bean = new Rfid_location();
			
			// Warehouse 
			Rfid_warehouse w = new Rfid_warehouse();
			war = w.selectAll(conn, "");
			// Location Type
			Rfid_loctype lt = new Rfid_loctype();
			loctype = lt.selectAll(conn, "");
			
			if(idlocation.length()>0) {
				
				// Setta l'id
				bean.setIdlocation(Integer.parseInt(idlocation));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_id_warehouse = bean.getId_warehouse();
				fld_description = bean.getDescription();
				fld_id_loctype = bean.getId_loctype();
				
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
		
		obj.put("idlocation", idlocation);
		obj.put("id_warehouse", fld_id_warehouse);
		obj.put("id_loctype", fld_id_loctype);
		obj.put("description", fld_description);
		obj.put("extra", extra);
		obj.put("war", war);
		obj.put("loctype", loctype);
		
		obj.put("tipoForm", tipoForm);
		
		return new ModelAndView("/rfid/anag/location-dett", obj);
	}
	
}
