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

import pkg.beans.Rfid_warehouse;
import pkg.beans.Rfid_extra;
import pkg.properties.ReadProperties;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class WarehouseDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		String fld_description = "";
		String fld_address = "";
		String check = "";
		
		Connection conn = null;
		String idwarehouse = ""+req.getParameter("idwarehouse");
		idwarehouse=Utility.noNull(idwarehouse);
		idwarehouse=idwarehouse.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		try {
			
			conn = Utility.getConnRfid();
			
			Rfid_warehouse bean = new Rfid_warehouse();
			
			if(idwarehouse.length()>0) {
				
				// Setta l'id
				bean.setIdwarehouse(Integer.parseInt(idwarehouse));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_description = bean.getDescription();
				fld_address = bean.getAddress();
				
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
		
		obj.put("idwarehouse", idwarehouse);
		obj.put("description", fld_description);
		obj.put("address", fld_address);
		obj.put("check", check);
		obj.put("extra", extra);
		
		obj.put("tipoForm", tipoForm);
		
		return new ModelAndView("/rfid/anag/warehouse-dett", obj);
	}
	
}
