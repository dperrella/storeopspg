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

import pkg.beans.rfid.Document;
import pkg.beans.rfid.Site;
import pkg.beans.rfid.Flow;
import pkg.beans.rfid.Printers;
import pkg.beans.rfid.PrintersTemplates;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DocumentsPrintSku extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Printers> printers = new ArrayList<Printers>();
		printers.clear();
		List<PrintersTemplates> templates = new ArrayList<PrintersTemplates>();
		templates.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		// Per operazioni di CRUD
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			String iddoc = ""+req.getParameter("iddoc");
			iddoc = Utility.noNull(iddoc);
			iddoc=iddoc.replaceAll("\\?cd=undefined", "");
			obj.put("iddoc", iddoc);
			String sku = ""+req.getParameter("sku");
			sku = Utility.noNull(sku);
			sku=sku.replaceAll("\\?cd=undefined", "");
			obj.put("sku", sku);
			String qty = ""+req.getParameter("qty");
			qty = Utility.noNull(qty);
			qty=qty.replaceAll("\\?cd=undefined", "");
			obj.put("qty", qty);
			
			printers = Printers.getPrintersByIdCustomer(conn, idcus);
			templates = PrintersTemplates.getTemplatesByIdCustomer(conn, idcus);
			
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
		
		obj.put("printers", printers);
		obj.put("templates", templates);
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/docs/documents-print-sku", obj);
	}
	
}
