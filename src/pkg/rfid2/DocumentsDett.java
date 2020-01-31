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

import pkg.beans.rfid.DocumentDetail;
import pkg.beans.rfid.Step;
import pkg.beans.rfid.Document;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DocumentsDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<DocumentDetail> doc = new ArrayList<DocumentDetail>();
		doc.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		
		String req_iddoc = ""+req.getParameter("iddoc");
		req_iddoc = Utility.noNull(req_iddoc);
		int iddoc = -1;
		if(req_iddoc.length()>0)
			iddoc = Integer.parseInt(req_iddoc);
		
		String onlyDiff = ""+req.getParameter("onlydiff");
		String type = ""+req.getParameter("type");
		
		onlyDiff = Utility.noNull(onlyDiff);
		if(onlyDiff.length()==0)
			onlyDiff = "true";
		
		type = Utility.noNull(type);
		
		// Per operazioni di CRUD
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			
			Document d = new Document();
			
			// In caso di azione...
			if(azione.length()>0) {
			
				String docRef = req.getParameter("doc_ref");
				int idstep = Integer.parseInt(req.getParameter("steps"));
				int idsite = Integer.parseInt(req.getParameter("sites"));
				
				
				
				d.setDocRef(docRef);
				d.setIdStep(idstep);
				d.setIdSite(idsite);
				iddoc = d.insert(conn);
				
			}
			
			d.setIdDocument(iddoc);
			d.selectRow(conn);
			
			obj.put("iddoc", iddoc);
			obj.put("doc_number", d.getDocNumber());
			obj.put("doc_ref", d.getDocRef());
			obj.put("doc_date", d.getDocDate());
			obj.put("dssite",d.getDsSite());
			obj.put("dsstep",d.getDsStep());
			obj.put("dstype",d.getDsType());
			
			
			obj.put("idstep",d.getIdStep());
			
			
			
			
			boolean expected = d.getExpected();
			if(expected) {
				obj.put("expected","Y");	
			}
			else {
				obj.put("expected","N");
			}
			
			System.out.println("expected: " + expected);
			
			
			
			
			boolean diff = false;
			if(onlyDiff.equals("true"))
				diff = true;
			
			if(type.length()==0)
				type = "SKU";
			
			obj.put("onlydiff", onlyDiff);
			obj.put("type", type);
			
			if(expected==false) {
				
				if(type.equals("SKU")) {
					// delete docdet + insert docdet from giacenze
					DocumentDetail.setExpected(conn, iddoc);
				}
				
			}
			
			
			
			doc = DocumentDetail.getDocumentsDetailByIdDocument(conn, iddoc, diff, type);
			
			int totTheo = 0;
			int totRead = 0;
			
			for (DocumentDetail row : doc) {
				totTheo+= row.getQty_theo();
				totRead+= row.getQty_read();
			}
			
			obj.put("totTheo", totTheo);
			obj.put("totRead", totRead);
			
			
			// controlla se lo step è inventario
			boolean isInventory = Step.isStepInventory(conn, d.getIdStep());
			if(isInventory) {
				obj.put("isInventory", "Y");
			}
			else {
				obj.put("isInventory", "N");
			}
			
			
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
		
		obj.put("doc", doc);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/docs/documents-dett", obj);
	}
	
}
