package pkg.rfid2;

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

import pkg.beans.rfid.Client;
import pkg.beans.rfid.CustomerReason;
import pkg.beans.rfid.DocType;
import pkg.beans.rfid.Reason;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.Step;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DoctypeDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<CustomerReason> cusrea = new ArrayList<CustomerReason>();
		cusrea.clear();
		
		List<Reason> rea = new ArrayList<Reason>();
		rea.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String msg = "";
		
		try {
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";
			}
			// Apertura connessione
			conn = Utility.getConnRfid();
						
			if(!tipoForm.equals("I")) {
				DocType doctype = new DocType();
				doctype.setIdDoctype(Integer.parseInt(id));
				doctype.selectRow(conn);
				obj.put("id", id);
				obj.put("description", doctype.getDescription());
				
				obj.put("idCusrea", DocType.selectCusreaByDoctype(conn, Integer.parseInt(id)));
				obj.put("idRea", DocType.selectReaByDoctype(conn, Integer.parseInt(id)));
			}
			
			cusrea = CustomerReason.getCustomerReasonByIdCustomer(conn, idcus);
			rea = Reason.getReasonByIdCustomer(conn, idcus);
			
			obj.put("tipoForm", tipoForm);
			obj.put("cusrea", cusrea);
			obj.put("rea", rea);
			
			
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
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/config/doctype-dett", obj);
	}
	
}
