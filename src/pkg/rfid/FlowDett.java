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
import pkg.beans.Rfid_flow;
import pkg.beans.Rfid_location;
import pkg.beans.Rfid_warehouse;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class FlowDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		extra.clear();
		
		String fld_description = "";
		boolean fld_enabled = false;
		String check = "";
		
		
		Connection conn = null;
		String idflow = ""+req.getParameter("idflow");
		idflow=Utility.noNull(idflow);
		idflow=idflow.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		try {
			
			conn = Utility.getConnRfid();
			
			Rfid_flow bean = new Rfid_flow();
			
			if(idflow.length()>0) {
				
				// Setta l'id
				bean.setIdflow(Integer.parseInt(idflow));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_description = bean.getDescription();
				fld_enabled = bean.getEnabled();
				if(fld_enabled==true)
					check = "checked";
				
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
		
		obj.put("idflow", idflow);
		obj.put("description", fld_description);
		obj.put("enabled", fld_enabled);
		obj.put("check", check);
		obj.put("extra", extra);
		
		obj.put("tipoForm", tipoForm);
		
		return new ModelAndView("/rfid/anag/flow-dett", obj);
	}
	
}
