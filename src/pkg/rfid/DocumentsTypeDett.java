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
import pkg.beans.Rfid_documents_type;
import pkg.utility.ReadExtraFields;
import pkg.utility.Utility;

public class DocumentsTypeDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		String fld_description = "";
		
		Connection conn = null;
		String id = ""+req.getParameter("id");
		id=Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		try {
			
			conn = Utility.getConnRfid();
			
			// Dichiara il bean
			Rfid_documents_type bean = new Rfid_documents_type();
			
			if(id.length()>0) {
				
				// Setta l'id
				bean.setId(Integer.parseInt(id));
				
				// legge i campi del form
				bean.selectRow(conn);
				
				// fields form
				fld_description = bean.getDescription();
				
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
		
		obj.put("id", id);
		obj.put("description", fld_description);
		obj.put("extra", extra);
		
		obj.put("tipoForm", tipoForm);
		
		return new ModelAndView("/rfid/anag/documents_type-dett", obj);
	}
	
}
