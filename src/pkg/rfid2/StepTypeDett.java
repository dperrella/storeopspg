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
import pkg.beans.rfid.DocType;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.StepType;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StepTypeDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<DocType> doctype = new ArrayList<DocType>();
		doctype.clear();
		
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
				StepType steptype = new StepType();
				steptype.setIdStepType(Integer.parseInt(id));
				steptype.selectRow(conn);
				obj.put("id", id);
				obj.put("id_doctype", steptype.getIdDoctypeDefault());
				obj.put("id_doctype2", steptype.getIdDoctypeDefault2());
				obj.put("power", steptype.getPower());
				obj.put("description", steptype.getDescription());
				
				if(steptype.getInventory()==false)
					obj.put("inventory", "false");
				else
					obj.put("inventory", "true");
				
			}
			
			obj.put("doctype", DocType.getDoctypeByIdCustomer(conn, idcus));
			obj.put("tipoForm", tipoForm);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/config/step-type-dett", obj);
	}
	
}
