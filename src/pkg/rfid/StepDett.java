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

import pkg.beans.Rfid_config;
import pkg.beans.Rfid_extra;
import pkg.beans.Rfid_step;
import pkg.beans.Tables;
import pkg.utility.ReadExtraFields;
import pkg.utility.ReadStepFields;
import pkg.utility.Utility;

public class StepDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		
		List<Tables> ol = new ArrayList<Tables>();
		ol.clear();
		List<Tables> dl = new ArrayList<Tables>();
		dl.clear();
		
		List<Rfid_config> config_before = new ArrayList<Rfid_config>();
		config_before.clear();
		List<Rfid_config> config_during = new ArrayList<Rfid_config>();
		config_during.clear();
		List<Rfid_config> config_after = new ArrayList<Rfid_config>();
		config_after.clear();
		
		List<Rfid_extra> extra_doc_top = new ArrayList<Rfid_extra>();
		extra_doc_top.clear();
		List<Rfid_extra> extra_doc_detail = new ArrayList<Rfid_extra>();
		extra_doc_detail.clear();
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		extra.clear();
		
		int fld_levelnum = 0;
		String fld_description = "";
		boolean fld_enabled = false;
		String check = "";
		String fld_origin_location="";
		String fld_destination_location="";
		
		Connection conn = null;
		String idflow = ""+req.getParameter("idflow");
		idflow=Utility.noNull(idflow);
		idflow=idflow.replaceAll("\\?cd=undefined", "");
		String idstep = ""+req.getParameter("idstep");
		idstep=Utility.noNull(idstep);
		idstep=idstep.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		try {
			
			conn = Utility.getConnRfid();
			
			Rfid_step bean = new Rfid_step();
			
			if(idflow.length()>0&&idstep.length()>0) {
				
				// Setta l'id
				bean.setIdflow(Integer.parseInt(idflow));
				bean.setIdstep(Integer.parseInt(idstep));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_levelnum = bean.getLevelnum();
				fld_description = bean.getDescription();
				
				
				fld_origin_location = bean.getOrigin_location();
				//ol = ReadStepFields.getWarehousesFields(fld_origin_location);
				fld_destination_location = bean.getDestination_location();
				//dl = ReadStepFields.getWarehousesFields(fld_destination_location);
				
				String fld_config_before = bean.getConfig_before();
				config_before = ReadStepFields.getConfigFields(fld_config_before);
				String fld_config_during = bean.getConfig_during();
				config_during = ReadStepFields.getConfigFields(fld_config_during);
				String fld_config_after = bean.getConfig_after();
				config_after = ReadStepFields.getConfigFields(fld_config_after);
				
				fld_enabled = bean.getEnabled();
				if(fld_enabled==true)
					check = "checked";
				
				// Extra_doc_top fields
				String fld_extra_doc_top = bean.getExtra_doc_top();
				// Li reperisce come lista
				extra_doc_top = ReadExtraFields.getExtraFields_doc_top(fld_extra_doc_top);
				
				// Extra_doc_detail fields
				String fld_extra_doc_detail = bean.getExtra_doc_detail();
				// Li reperisce come lista
				extra_doc_detail = ReadExtraFields.getExtraFields_doc_detail(fld_extra_doc_detail);
				
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
		obj.put("idstep", idstep);
		obj.put("levelnum", fld_levelnum);
		obj.put("description", fld_description);
		obj.put("ol", ol);
		obj.put("dl", dl);
		
		obj.put("origin_location", fld_origin_location);
		obj.put("destination_location", fld_destination_location);
		
		obj.put("config_before", config_before);
		obj.put("config_during", config_during);
		obj.put("config_after", config_after);
		obj.put("enabled", fld_enabled);
		obj.put("check", check);
		obj.put("extra_doc_top", extra_doc_top);
		obj.put("extra_doc_detail", extra_doc_detail);
		obj.put("extra", extra);
		
		obj.put("tipoForm", tipoForm);
		
		return new ModelAndView("/rfid/anag/step-dett", obj);
	}
	
}
